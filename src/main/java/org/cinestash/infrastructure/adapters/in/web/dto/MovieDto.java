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

import java.time.LocalDate;

/**
 * Data Transfer Object for Movie information.
 * Used for external communication over the Web API.
 */
public record MovieDto(
        Long id,
        String title,
        String director,
        String mainActors,
        LocalDate dateOfView,
        String review,
        Integer stars,
        String imageData
) {}
