package org.cinestash.domain.service;

import org.cinestash.domain.model.Movie;
import org.cinestash.domain.model.MovieStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MovieStatisticsCalculator {

    private static final Logger log = LoggerFactory.getLogger(MovieStatisticsCalculator.class);

    public MovieStats calculate(List<Movie> movies) {
        log.debug("Starting statistics calculation for {} movies.", movies != null ? movies.size() : 0);
        
        if (movies == null || movies.isEmpty()) {
            log.debug("Movie list is empty, returning empty statistics.");
            return new MovieStats(0, 0.0, "N/A", "N/A", Map.of());
        }

        double avg = movies.stream()
                .map(Movie::stars)
                .filter(s -> s != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        // Group by Year-Month (TreeMap keeps the chart X-axis chronological)
        Map<String, Long> perMonth = movies.stream()
                .filter(m -> m.dateOfView() != null)
                .collect(Collectors.groupingBy(
                        m -> m.dateOfView().getYear() + "-" + String.format("%02d", m.dateOfView().getMonthValue()),
                        TreeMap::new,
                        Collectors.counting()
                ));

        // Find Top Director
        String topDir = movies.stream()
                .filter(m -> m.director() != null && !m.director().isBlank())
                .collect(Collectors.groupingBy(Movie::director, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        // Find Top Actor by splitting CSV strings
        String topAct = movies.stream()
                .filter(m -> m.mainActors() != null && !m.mainActors().isBlank())
                .flatMap(m -> Arrays.stream(m.mainActors().split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        MovieStats stats = new MovieStats(movies.size(), Math.round(avg * 10.0) / 10.0, topDir, topAct, perMonth);
        log.debug("Statistics calculation complete: totalMovies={}, avgRating={}", stats.totalMovies(), stats.averageRating());
        
        return stats;
    }
}
