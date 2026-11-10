package configs;

import entities.AppInputData;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigValidator {
    private static final int MIN_DEPTH = 0;
    private static final int MAX_DEPTH = 100;

    /**
     * @param data параметры из конфигурации
     * @return валидность всей поступившей конфигурации
     */
    public static boolean validate (AppInputData data) {
        boolean t1 = validatePackageName(data.packageName());
        boolean t2 = validateTestMode(data.testMod());
        boolean t3 = validateRepository(data.repoUrl(), data.testMod());
        boolean t4 = validateVersion(data.packageVersion());
        boolean t5 = validateOutputFile(data.outputFile());
        boolean t6 = validateMaxDepth(data.maxDepth());
        return (t1 && t2 && t3 && t4 && t5 && t6);
    }

    /**
     * @param name параметр из конфигурации
     * @return валидность
     */
    private static boolean validatePackageName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("Некорректное значение package_name");
            return false;
        }
        return true;
    }

    /**
     * @param repository параметр из конфигурации
     * @param testMode в зависимости от значения 1ый параметр ссылка или файл
     * @return валидность
     */
    private static boolean validateRepository(String repository, Boolean testMode) {
        try {
            if (testMode == null) {
                System.out.println("Невозможно проверить repo_url из-за test_mode");
                return false;
            }
            if (repository == null || repository.isBlank()) {
                System.out.println("Некорректное значение repo_url");
                return false;
            }
            if (testMode) {
                if (!Files.isReadable(Path.of(repository))) {
                    System.out.println("Тестовый файл(repo_url) не существует или недоступен");
                    return false;
                }
            } else {
                URI uri = URI.create(repository);
                if (!uri.isAbsolute() || uri.getHost() == null) {
                    System.out.println("Некорректный URL репозитория(repo_url)");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Некорректное значение repo_url");
            return false;
        }
    }

    /**
     * @param mode параметр из конфигурации
     * @return валидность
     */
    private static boolean validateTestMode(Boolean mode) {
        if (mode == null) {
            System.out.println("Некорректное значение test_mode");
            return false;
        }
        return true;
    }

    /**
     * @param version параметр из конфигурации
     * @return валидность
     */
    private static boolean validateVersion(String version) {
        if (version == null || version.isBlank()) {
            System.out.println("Некорректное значение package_version");
            return false;
        }
        return true;
    }

    /**
     * @param fileName параметр из конфигурации
     * @return валидность
     */
    private static boolean validateOutputFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            System.out.println("Некорректное значение output_file");
            return false;
        }
        return true;
    }

    /**
     * @param depth параметр из конфигурации
     * @return валидность
     */
    private static boolean validateMaxDepth(Integer depth) {
        if (depth == null) {
            System.out.println("Некорректное значение max_depth");
            return false;
        }
        if (depth < MIN_DEPTH || depth > MAX_DEPTH) {
            System.out.println("Параметр max_depth должен быть > " + MIN_DEPTH + " и < " + MAX_DEPTH);
            return false;
        }
        return true;
    }
}
