package org.cinestash.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cinestash.application.ports.in.MovieUseCase;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.PagedResult;
import org.cinestash.domain.service.MovieStatisticsCalculator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MovieService implements MovieUseCase {

    private final MovieRepositoryPort repository;
    private final MovieStatisticsCalculator statisticsCalculator;

    @Override
    public Movie addEntry(Movie movie) {
        log.info("Adding new movie entry: {}", movie.title());
        return repository.save(movie);
    }

    @Override
    public PagedResult<Movie> searchAndPaginate(String query, int page, int size, String sortBy) {
        log.debug("Searching movies with query: '{}', page: {}, size: {}, sortBy: '{}'", query, page, size, sortBy);
        return repository.findAll(query, page, size, sortBy);
    }

    @Override
    public List<Movie> getAllMoviesForExport() {
        log.info("Fetching all movies for export.");
        return repository.findAllForExport();
    }

    @Override
    public void importMovies(List<Movie> movies) {
        log.info("Importing {} movies.", movies.size());
        repository.saveAll(movies);
    }

    @Override
    public void deleteMovie(Long id) {
        log.info("Deleting movie with ID: {}", id);
        repository.deleteById(id);
    }

    @Override
    public MovieStats getStatistics() {
        log.debug("Calculating movie statistics.");
        List<Movie> all = repository.findAllForExport();
        return statisticsCalculator.calculate(all);
    }
}
