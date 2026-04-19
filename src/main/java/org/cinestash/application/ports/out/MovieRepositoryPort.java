package org.cinestash.application.ports.out;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.PagedResult;

import java.util.List;

public interface MovieRepositoryPort {
    Movie save(Movie movie);

    void saveAll(List<Movie> movies);

    PagedResult<Movie> findAll(String query, int page, int size, String sortBy);

    void deleteById(Long id);

    List<Movie> findAllForExport();
}