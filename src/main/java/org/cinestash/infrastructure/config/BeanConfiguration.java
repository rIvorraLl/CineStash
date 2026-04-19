/*
 * CineStash
 * Copyright (C) 2026 rIvorraLl [@github.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.cinestash.infrastructure.config;

import org.cinestash.application.ports.in.MovieUseCase;
import org.cinestash.application.ports.out.MovieRepositoryPort;
import org.cinestash.application.services.MovieService;
import org.cinestash.domain.service.MovieStatisticsCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for defining beans.
 * Manages the manual instantiation of application services and domain services to keep them decoupled from Spring annotations.
 */
@Configuration
public class BeanConfiguration {

    /**
     * Creates a MovieStatisticsCalculator bean.
     *
     * @return A new instance of MovieStatisticsCalculator.
     */
    @Bean
    public MovieStatisticsCalculator movieStatisticsCalculator() {
        return new MovieStatisticsCalculator();
    }

    /**
     * Creates a MovieUseCase bean using the MovieService implementation.
     *
     * @param repositoryPort       The persistence port.
     * @param statisticsCalculator The domain statistics calculator.
     * @return A new MovieService instance.
     */
    @Bean
    public MovieUseCase movieUseCase(MovieRepositoryPort repositoryPort, MovieStatisticsCalculator statisticsCalculator) {
        return new MovieService(repositoryPort, statisticsCalculator);
    }
}
