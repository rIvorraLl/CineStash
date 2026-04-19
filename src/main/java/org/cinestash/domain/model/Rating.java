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

/**
 * Value Object representing a movie rating.
 * Enforces business rules: ratings must be between 1 and 10 stars.
 *
 * @param value The numerical star value (1-10).
 */
public record Rating(Integer value) {
    /**
     * Compact constructor to validate rating value.
     *
     * @throws IllegalArgumentException if value is not null and outside the 1-10 range.
     */
    public Rating {
        if (value != null && (value < 1 || value > 10)) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
    }

    /**
     * Factory method to create a Rating instance.
     *
     * @param value The star value.
     * @return A new Rating instance.
     */
    public static Rating of(Integer value) {
        return new Rating(value);
    }
}
