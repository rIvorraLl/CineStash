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

import java.util.List;

/**
 * Generic domain model for paginated query results.
 *
 * @param <T>           The type of the content in the page.
 * @param content       The list of items in the current page.
 * @param totalPages    The total number of pages available.
 * @param totalElements The total number of elements across all pages.
 * @param currentPage   The current page index (0-based).
 */
public record PagedResult<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int currentPage
) {}
