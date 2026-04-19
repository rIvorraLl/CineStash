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

package org.cinestash.application.ports.out;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.PagedResult;

import java.util.List;

/**
 * Outbound port (Output Port) for movie persistence.
 * Defines the contract that persistence adapters must implement to satisfy domain needs.
 */
public interface MovieRepositoryPort {
    /**
     * Persists a single movie entry.
     *
     * @param movie The movie domain object to save.
     * @return The saved movie domain object.
     */
    Movie save(Movie movie);

    /**
     * Persists multiple movie entries in a single operation.
     *
     * @param movies The list of movies to save.
     */
    void saveAll(List<Movie> movies);

    /**
     * Retrieves a paginated list of movies based on search and sort criteria.
     *
     * @param query  The search query.
     * @param page   The page index.
     * @param size   The page size.
     * @param sortBy The sort field.
     * @return A PagedResult of movies.
     */
    PagedResult<Movie> findAll(String query, int page, int size, String sortBy);

    /**
     * Deletes a movie from the repository by its ID.
     *
     * @param id The ID of the movie.
     */
    void deleteById(Long id);

    /**
     * Retrieves all movies from the repository without pagination.
     *
     * @return A list of all movies.
     */
    List<Movie> findAllForExport();
}
