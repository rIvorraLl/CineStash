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

import java.time.LocalDate;

/**
 * Domain model representing a movie entry in the cinema diary.
 *
 * @param id          Unique identifier of the movie entry.
 * @param title       Title of the movie.
 * @param director    Director of the movie.
 * @param mainActors  Comma-separated list of main actors.
 * @param dateOfView  The date when the movie was watched.
 * @param review      Personal review or notes about the movie.
 * @param rating      The star rating assigned to the movie (1-10).
 * @param imageData   Base64 encoded image data for the movie poster.
 */
public record Movie(
        Long id,
        String title,
        String director,
        String mainActors,
        LocalDate dateOfView,
        String review,
        Rating rating,
        String imageData
) {
    /**
     * Helper method to retrieve the numerical star value from the Rating object.
     *
     * @return The integer value of the rating, or null if no rating is assigned.
     */
    public Integer stars() {
        return rating != null ? rating.value() : null;
    }
}
