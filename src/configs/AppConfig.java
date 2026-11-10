package configs;

import entities.AppInputData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

public class AppConfig {
    private static final String DATA_PATH = "settings.ini";
    private static final Set<String> ALLOWED_KEYS = Set.of(
            "package_name",
            "repo_url",
            "test_mode",
            "package_version",
            "output_file",
            "max_depth"
    );

    private static boolean readData;
    private static boolean correctData;
    private static AppInputData data;

    public static boolean isReadData() {
        return readData;
    }

    public static boolean isCorrectData() {
        return correctData;
    }

    public static AppInputData getData() {
        return data;
    }

    public AppConfig() {
        initData();
    }

    /**
     * Инициализация, получение конфигурации из файла
     */
    private void initData() {
        Properties prop = new Properties();
        try (var reader = Files.newBufferedReader(Paths.get(DATA_PATH))) {
            prop.load(reader);
        } catch (IOException e) {
            IO.println("Отсутствует файл с параметрами");
        }

        String packageName = clean(prop.getProperty("package_name"));
        String repoUrl = clean(prop.getProperty("repo_url"));
        String rawTestMode = clean(prop.getProperty("test_mode"));
        String packageVersion = clean(prop.getProperty("package_version"));
        String outputFile = clean(prop.getProperty("output_file"));
        String rawMaxDepth = clean(prop.getProperty("max_depth"));

        Boolean testMod  = Boolean.parseBoolean(rawTestMode);
        Integer maxDepth = Integer.parseInt(rawMaxDepth);

        data = new AppInputData(packageName,
                                repoUrl,
                                testMod,
                                packageVersion,
                                outputFile,
                                maxDepth);
        readData = true;
        correctData = ConfigValidator.validate(data);
    }

    /**
     * @param value Строка
     * @return Строка с удалёнными внешними кавычками
     */
    private static String clean(String value) {
        if (value == null) return null;
        value = value.trim();
        if ((value.startsWith("\"") && value.endsWith("\"")) ||
                (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
