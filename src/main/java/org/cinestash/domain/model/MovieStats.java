package org.cinestash.domain.model;

import java.util.Map;

public record MovieStats(
        long totalMovies,
        double averageRating,
        String topDirector,
        String topActor,
        Map<String, Long> moviesPerMonth
) {}