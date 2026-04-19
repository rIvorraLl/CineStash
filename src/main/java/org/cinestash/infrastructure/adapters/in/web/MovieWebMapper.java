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

package org.cinestash.infrastructure.adapters.in.web;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.Rating;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieDto;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieStatsDto;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Web DTOs and Domain Models.
 * Decouples the API contract from the internal business logic representation.
 */
@Component
public class MovieWebMapper {

    /**
     * Converts a MovieDto to a Movie domain object.
     *
     * @param dto The DTO to convert.
     * @return The resulting Movie domain object.
     */
    public Movie toDomain(MovieDto dto) {
        return new Movie(
                dto.id(),
                dto.title(),
                dto.director(),
                dto.mainActors(),
                dto.dateOfView(),
                dto.review(),
                Rating.of(dto.stars()),
                dto.imageData()
        );
    }

    /**
     * Converts a Movie domain object to a MovieDto.
     *
     * @param movie The domain object to convert.
     * @return The resulting MovieDto.
     */
    public MovieDto toDto(Movie movie) {
        return new MovieDto(
                movie.id(),
                movie.title(),
                movie.director(),
                movie.mainActors(),
                movie.dateOfView(),
                movie.review(),
                movie.stars(),
                movie.imageData()
        );
    }

    /**
     * Converts a MovieStats domain object to a MovieStatsDto.
     *
     * @param stats The domain object to convert.
     * @return The resulting MovieStatsDto.
     */
    public MovieStatsDto toDto(MovieStats stats) {
        return new MovieStatsDto(
                stats.totalMovies(),
                stats.averageRating(),
                stats.topDirector(),
                stats.topActor(),
                stats.moviesPerMonth()
        );
    }
}
