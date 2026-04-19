package org.cinestash.domain.model;

import java.util.List;

public record PagedResult<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int currentPage
) {}