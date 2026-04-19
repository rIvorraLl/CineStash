package org.cinestash.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.PagedResult;
import org.cinestash.domain.model.Rating;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MovieRepositoryPort {

    private final JpaMovieRepository jpaRepository;

    @Override
    public Movie save(Movie movie) {
        MovieEntity entity = toEntity(movie);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public void saveAll(List<Movie> movies) {
        jpaRepository.saveAll(movies.stream().map(this::toEntity).toList());
    }

    @Override
    public PagedResult<Movie> findAll(String query, int page, int size, String sortBy) {
        Sort sort = sortBy.equalsIgnoreCase("stars") ? Sort.by("stars").descending() : Sort.by("dateOfView").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MovieEntity> result = jpaRepository.searchMovies(query, pageable);

        return new PagedResult<>(
                result.getContent().stream().map(this::toDomain).toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    @Override
    public void deleteById(Long id) { jpaRepository.deleteById(id); }

    @Override
    public List<Movie> findAllForExport() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private MovieEntity toEntity(Movie m) {
        return MovieEntity.builder()
                .id(m.id()).title(m.title()).director(m.director())
                .mainActors(m.mainActors()).dateOfView(m.dateOfView())
                .review(m.review()).stars(m.stars()).imageData(m.imageData())
                .build();
    }

    private Movie toDomain(MovieEntity e) {
        return new Movie(e.getId(), e.getTitle(), e.getDirector(), e.getMainActors(),
                e.getDateOfView(), e.getReview(), Rating.of(e.getStars()), e.getImageData());
    }
}
