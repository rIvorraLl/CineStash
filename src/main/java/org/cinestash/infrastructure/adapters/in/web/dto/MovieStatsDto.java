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

package org.cinestash.infrastructure.adapters.in.web.dto;

import java.util.Map;

/**
 * Data Transfer Object for aggregated Movie statistics.
 * Used to transmit calculation results to the frontend.
 */
public record MovieStatsDto(
        long totalMovies,
        double averageRating,
        String topDirector,
        String topActor,
        Map<String, Long> moviesPerMonth
) {}
