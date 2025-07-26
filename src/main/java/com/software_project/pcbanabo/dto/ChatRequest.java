package com.software_project.pcbanabo.dto;

import java.util.List;
import java.util.Map;

public record ChatRequest(
    String message,
    Map<String, Object> buildContext,
    List<Map<String, Object>> userBuilds,
    String currentPage,
    Map<String, Object> userContext
) { }