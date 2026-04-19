package org.cinestash.application.ports.in;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.PagedResult;
import java.util.List;

public interface MovieUseCase {
    Movie addEntry(Movie movie);
    PagedResult<Movie> searchAndPaginate(String query, int page, int size, String sortBy);
    List<Movie> getAllMoviesForExport();
    void importMovies(List<Movie> movies);
    void deleteMovie(Long id);
    MovieStats getStatistics();
}