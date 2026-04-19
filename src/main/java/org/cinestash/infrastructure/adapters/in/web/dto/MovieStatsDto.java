package org.cinestash.infrastructure.adapters.in.web.dto;

import java.util.Map;

public record MovieStatsDto(
        long totalMovies,
        double averageRating,
        String topDirector,
        String topActor,
        Map<String, Long> moviesPerMonth
) {}
