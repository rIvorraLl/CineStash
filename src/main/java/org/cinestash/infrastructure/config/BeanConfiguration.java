package org.cinestash.infrastructure.config;

import org.cinestash.application.ports.in.MovieUseCase;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.application.services.MovieService;
import org.cinestash.domain.service.MovieStatisticsCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MovieStatisticsCalculator movieStatisticsCalculator() {
        return new MovieStatisticsCalculator();
    }

    @Bean
    public MovieUseCase movieUseCase(MovieRepositoryPort repositoryPort, MovieStatisticsCalculator statisticsCalculator) {
        return new MovieService(repositoryPort, statisticsCalculator);
    }
}
