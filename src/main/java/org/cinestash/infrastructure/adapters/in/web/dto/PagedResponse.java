package org.cinestash.infrastructure.adapters.in.web.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int currentPage
) {}
