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

package org.cinestash.application.ports.in;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.PagedResult;
import java.util.List;

/**
 * Inbound port (Input Port) for movie-related use cases.
 * Defines the contract for external triggers (like Web adapters) to interact with the application.
 */
public interface MovieUseCase {
    /**
     * Adds a new movie entry to the diary.
     *
     * @param movie The movie domain object to add.
     * @return The saved movie domain object.
     */
    Movie addEntry(Movie movie);

    /**
     * Searches for movies based on a query and returns a paginated result.
     *
     * @param query  The search query string.
     * @param page   The page number to retrieve.
     * @param size   The number of items per page.
     * @param sortBy The field name to sort by.
     * @return A PagedResult containing the requested page of movies.
     */
    PagedResult<Movie> searchAndPaginate(String query, int page, int size, String sortBy);

    /**
     * Retrieves all movies in the system for export purposes.
     *
     * @return A list of all movies.
     */
    List<Movie> getAllMoviesForExport();

    /**
     * Bulk imports a list of movies into the system.
     *
     * @param movies The list of movies to import.
     */
    void importMovies(List<Movie> movies);

    /**
     * Deletes a movie entry by its unique identifier.
     *
     * @param id The ID of the movie to delete.
     */
    void deleteMovie(Long id);

    /**
     * Calculates and retrieves movie statistics.
     *
     * @return A MovieStats object containing aggregated data.
     */
    MovieStats getStatistics();
}
