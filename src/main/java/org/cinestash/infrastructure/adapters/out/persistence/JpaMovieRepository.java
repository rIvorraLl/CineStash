package org.cinestash.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaMovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m FROM MovieEntity m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(m.director) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(m.mainActors) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<MovieEntity> searchMovies(@Param("q") String query, Pageable pageable);

    List<MovieEntity> findAllByOrderByDateOfViewDesc();
}