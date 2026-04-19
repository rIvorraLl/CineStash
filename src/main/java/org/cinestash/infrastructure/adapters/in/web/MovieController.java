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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MovieUseCase movieUseCase;
    private final MovieWebMapper mapper;

    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody MovieDto movieDto) {
        log.info("Received request to add movie: {}", movieDto.title());
        Movie movie = mapper.toDomain(movieDto);
        Movie addedMovie = movieUseCase.addEntry(movie);
        log.info("Movie added successfully with ID: {}", addedMovie.id());
        return ResponseEntity.ok(mapper.toDto(addedMovie));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<MovieDto>> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "date") String sort) {
        log.info("Received search request: query='{}', page={}, sort='{}'", q, page, sort);
        PagedResult<Movie> domainResult = movieUseCase.searchAndPaginate(q, page, 20, sort);
        
        PagedResponse<MovieDto> response = new PagedResponse<>(
                domainResult.content().stream().map(mapper::toDto).toList(),
                domainResult.totalPages(),
                domainResult.totalElements(),
                domainResult.currentPage()
        );
        log.debug("Returning {} movies for search query '{}'", response.content().size(), q);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<MovieStatsDto> getStats() {
        log.info("Received request for movie statistics.");
        MovieStatsDto stats = mapper.toDto(movieUseCase.getStatistics());
        log.debug("Returning movie statistics: totalMovies={}", stats.totalMovies());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/export")
    public ResponseEntity<List<MovieDto>> export() {
        log.info("Received request to export all movies.");
        List<MovieDto> dtos = movieUseCase.getAllMoviesForExport().stream()
                .map(mapper::toDto)
                .toList();
        log.info("Exported {} movies.", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importData(@RequestBody List<MovieDto> dtos) {
        log.info("Received request to import {} movies.", dtos.size());
        List<Movie> movies = dtos.stream().map(mapper::toDomain).toList();
        movieUseCase.importMovies(movies);
        log.info("Successfully imported {} movies.", dtos.size());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received request to delete movie with ID: {}", id);
        movieUseCase.deleteMovie(id);
        log.info("Movie with ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
