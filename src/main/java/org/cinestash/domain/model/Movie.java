package org.cinestash.domain.model;

import java.time.LocalDate;

public record Movie(
        Long id,
        String title,
        String director,
        String mainActors,
        LocalDate dateOfView,
        String review,
        Rating rating,
        String imageData
) {
    public Integer stars() {
        return rating != null ? rating.value() : null;
    }
}
