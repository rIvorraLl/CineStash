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

package org.cinestash.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.PagedResult;
import org.cinestash.domain.model.Rating;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Persistence adapter for Movie entities.
 * Implements the Outward Port for movie data storage using Spring Data JPA.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MovieRepositoryPort {

    private final JpaMovieRepository jpaRepository;

    @Override
    public Movie save(Movie movie) {
        log.debug("Persisting movie entity to database: {}", movie.title());
        MovieEntity entity = toEntity(movie);
        MovieEntity savedEntity = jpaRepository.save(entity);
        log.debug("Successfully saved movie with ID: {}", savedEntity.getId());
        return toDomain(savedEntity);
    }

    @Override
    public void saveAll(List<Movie> movies) {
        log.info("Batch saving {} movies.", movies.size());
        jpaRepository.saveAll(movies.stream().map(this::toEntity).toList());
        log.debug("Successfully saved {} movies.", movies.size());
    }

    @Override
    public PagedResult<Movie> findAll(String query, int page, int size, String sortBy) {
        log.debug("Querying movies with search term '{}', page {}, size {}, sort {}", query, page, size, sortBy);
        Sort sort = sortBy.equalsIgnoreCase("stars") ? Sort.by("stars").descending() : Sort.by("dateOfView").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MovieEntity> result = jpaRepository.searchMovies(query, pageable);
        log.debug("Found {} movies for search query '{}'", result.getTotalElements(), query);

        return new PagedResult<>(
                result.getContent().stream().map(this::toDomain).toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    @Override
    public void deleteById(Long id) { 
        log.debug("Deleting movie with ID: {}", id);
        jpaRepository.deleteById(id);
        log.debug("Movie with ID {} deleted from database.", id);
    }

    @Override
    public List<Movie> findAllForExport() {
        log.debug("Fetching all movies for export from database.");
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    /**
     * Maps a Movie domain object to a MovieEntity.
     *
     * @param m The domain model.
     * @return The persistence entity.
     */
    private MovieEntity toEntity(Movie m) {
        return MovieEntity.builder()
                .id(m.id()).title(m.title()).director(m.director())
                .mainActors(m.mainActors()).dateOfView(m.dateOfView())
                .review(m.review()).stars(m.stars()).imageData(m.imageData())
                .build();
    }

    /**
     * Maps a MovieEntity to a Movie domain object.
     *
     * @param e The persistence entity.
     * @return The domain model.
     */
    private Movie toDomain(MovieEntity e) {
        return new Movie(e.getId(), e.getTitle(), e.getDirector(), e.getMainActors(),
                e.getDateOfView(), e.getReview(), Rating.of(e.getStars()), e.getImageData());
    }
}
