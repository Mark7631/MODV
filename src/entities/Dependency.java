package entities;

public record Dependency(
        String packageName,
        String version
) {}