package org.cinestash.infrastructure.adapters.in.web;

import lombok.RequiredArgsConstructor;
import org.cinestash.application.ports.in.MovieUseCase;
import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.PagedResult;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieDto;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieStatsDto;
import org.cinestash.infrastructure.adapters.in.web.dto.PagedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieUseCase movieUseCase;
    private final MovieWebMapper mapper;

    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody MovieDto movieDto) {
        Movie movie = mapper.toDomain(movieDto);
        return ResponseEntity.ok(mapper.toDto(movieUseCase.addEntry(movie)));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<MovieDto>> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "date") String sort) {
        PagedResult<Movie> domainResult = movieUseCase.searchAndPaginate(q, page, 20, sort);
        
        PagedResponse<MovieDto> response = new PagedResponse<>(
                domainResult.content().stream().map(mapper::toDto).toList(),
                domainResult.totalPages(),
                domainResult.totalElements(),
                domainResult.currentPage()
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<MovieStatsDto> getStats() {
        return ResponseEntity.ok(mapper.toDto(movieUseCase.getStatistics()));
    }

    @GetMapping("/export")
    public ResponseEntity<List<MovieDto>> export() {
        List<MovieDto> dtos = movieUseCase.getAllMoviesForExport().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importData(@RequestBody List<MovieDto> dtos) {
        List<Movie> movies = dtos.stream().map(mapper::toDomain).toList();
        movieUseCase.importMovies(movies);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieUseCase.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
