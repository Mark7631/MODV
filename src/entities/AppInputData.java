package entities;

public record AppInputData (
    String packageName,
    String repoUrl,
    Boolean testMod,
    String packageVersion,
    String outputFile,
    Integer maxDepth
) {}
