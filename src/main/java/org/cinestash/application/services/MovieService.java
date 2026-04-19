package org.cinestash.application.services;

import org.cinestash.application.ports.in.MovieUseCase;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.cinestash.domain.model.PagedResult;
import org.cinestash.domain.service.MovieStatisticsCalculator;

import java.util.List;

public class MovieService implements MovieUseCase {

    private final MovieRepositoryPort repository;
    private final MovieStatisticsCalculator statisticsCalculator;

    public MovieService(MovieRepositoryPort repository, MovieStatisticsCalculator statisticsCalculator) {
        this.repository = repository;
        this.statisticsCalculator = statisticsCalculator;
    }

    @Override
    public Movie addEntry(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public PagedResult<Movie> searchAndPaginate(String query, int page, int size, String sortBy) {
        return repository.findAll(query, page, size, sortBy);
    }

    @Override
    public List<Movie> getAllMoviesForExport() {
        return repository.findAllForExport();
    }

    @Override
    public void importMovies(List<Movie> movies) {
        repository.saveAll(movies);
    }

    @Override
    public void deleteMovie(Long id) {
        repository.deleteById(id);
    }

    @Override
    public MovieStats getStatistics() {
        List<Movie> all = repository.findAllForExport();
        return statisticsCalculator.calculate(all);
    }
}
