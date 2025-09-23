package io.github.guiboava.bem_dosado.controller.dto;

import java.util.List;

public record PageDTO<T>(
        List<T> content,
        int page,
        int pageSize,
        long totalElements,
        int totalPages
) {}