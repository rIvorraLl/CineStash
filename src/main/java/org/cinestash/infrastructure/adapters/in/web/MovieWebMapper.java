package org.cinestash.infrastructure.adapters.in.web;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.Rating;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieDto;
import org.cinestash.infrastructure.adapters.in.web.dto.MovieStatsDto;
import org.springframework.stereotype.Component;

@Component
public class MovieWebMapper {

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
