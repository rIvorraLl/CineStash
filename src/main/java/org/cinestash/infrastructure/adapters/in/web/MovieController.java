/*
 * CineStash
 * Copyright (C) 2026 rIvorraLl [@github.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * REST controller for movie-related operations.
 * Acts as a primary adapter in the hexagonal architecture, exposing use cases over HTTP.
 */
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MovieUseCase movieUseCase;
    private final MovieWebMapper mapper;

    /**
     * Adds a new movie entry.
     *
     * @param movieDto The data transfer object containing movie details.
     * @return The created movie as a DTO.
     */
    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody MovieDto movieDto) {
        log.info("Received request to add movie: {}", movieDto.title());
        Movie movie = mapper.toDomain(movieDto);
        Movie addedMovie = movieUseCase.addEntry(movie);
        log.info("Movie added successfully with ID: {}", addedMovie.id());
        return ResponseEntity.ok(mapper.toDto(addedMovie));
    }

    /**
     * Searches and paginates movies based on criteria.
     *
     * @param q    The search query.
     * @param page The page index.
     * @param sort The sorting field.
     * @return A paginated response of movie DTOs.
     */
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

    /**
     * Retrieves overall movie statistics.
     *
     * @return A DTO containing aggregated stats.
     */
    @GetMapping("/stats")
    public ResponseEntity<MovieStatsDto> getStats() {
        log.info("Received request for movie statistics.");
        MovieStatsDto stats = mapper.toDto(movieUseCase.getStatistics());
        log.debug("Returning movie statistics: totalMovies={}", stats.totalMovies());
        return ResponseEntity.ok(stats);
    }

    /**
     * Exports all movie entries.
     *
     * @return A list of all movie DTOs.
     */
    @GetMapping("/export")
    public ResponseEntity<List<MovieDto>> export() {
        log.info("Received request to export all movies.");
        List<MovieDto> dtos = movieUseCase.getAllMoviesForExport().stream()
                .map(mapper::toDto)
                .toList();
        log.info("Exported {} movies.", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Imports a list of movies.
     *
     * @param dtos The list of movie DTOs to import.
     * @return An empty successful response.
     */
    @PostMapping("/import")
    public ResponseEntity<Void> importData(@RequestBody List<MovieDto> dtos) {
        log.info("Received request to import {} movies.", dtos.size());
        List<Movie> movies = dtos.stream().map(mapper::toDomain).toList();
        movieUseCase.importMovies(movies);
        log.info("Successfully imported {} movies.", dtos.size());
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a movie entry by ID.
     *
     * @param id The ID of the movie to delete.
     * @return An empty successful response (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received request to delete movie with ID: {}", id);
        movieUseCase.deleteMovie(id);
        log.info("Movie with ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
