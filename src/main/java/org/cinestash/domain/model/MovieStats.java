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

package org.cinestash.domain.model;

import java.util.Map;

/**
 * Domain model representing aggregated statistics for the cinema diary.
 *
 * @param totalMovies     Total number of movies in the diary.
 * @param averageRating   Average star rating across all entries.
 * @param topDirector     The director with the most watched movies.
 * @param topActor        The actor appearing most frequently in watched movies.
 * @param moviesPerMonth  A map of year-month strings to movie counts.
 */
public record MovieStats(
        long totalMovies,
        double averageRating,
        String topDirector,
        String topActor,
        Map<String, Long> moviesPerMonth
) {}
