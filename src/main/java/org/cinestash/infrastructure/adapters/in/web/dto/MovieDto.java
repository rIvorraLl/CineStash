package org.cinestash.infrastructure.adapters.in.web.dto;

import java.time.LocalDate;

public record MovieDto(
        Long id,
        String title,
        String director,
        String mainActors,
        LocalDate dateOfView,
        String review,
        Integer stars,
        String imageData
) {}
