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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for MovieEntity.
 * Provides standard CRUD and custom search capabilities for SQLite persistence.
 */
@Repository
public interface JpaMovieRepository extends JpaRepository<MovieEntity, Long> {

    /**
     * Searches for movies by title, director, or actors with case-insensitive partial match.
     *
     * @param query    The search term.
     * @param pageable The pagination information.
     * @return A page of MovieEntity matching the query.
     */
    @Query("SELECT m FROM MovieEntity m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(m.director) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(m.mainActors) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<MovieEntity> searchMovies(@Param("q") String query, Pageable pageable);

    /**
     * Retrieves all movies ordered by view date in descending order.
     *
     * @return A list of movie entities.
     */
    List<MovieEntity> findAllByOrderByDateOfViewDesc();
}
