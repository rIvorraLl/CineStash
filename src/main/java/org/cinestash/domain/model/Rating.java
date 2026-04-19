package org.cinestash.domain.model;

public record Rating(Integer value) {
    public Rating {
        if (value != null && (value < 1 || value > 10)) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
    }

    public static Rating of(Integer value) {
        return new Rating(value);
    }
}
