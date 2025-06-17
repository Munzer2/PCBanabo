package com.software_project.pcbanabo.dto;
import java.util.List;

public record OpenRouterResponse(
    List<Choice> choices
) {
  public record Choice(Message message) { }
  public record Message(String role, String content) { }
}