import configs.AppConfig;
import entities.AppInputData;
import entities.Dependency;
import services.NugetClient;
import services.NuspecParser;

import java.util.List;

class Main {
    void main(String[] args) {
        init();
        if (AppConfig.isCorrectData()) {
            System.out.println("Не было обнаружено ошибок в конфигурации в ходе первичной проверки");
        } else {
            System.out.println("Были обнаружены выше перечисленные ошибки");
            System.out.println("Работа программы прекращена");
            return;
        }

        printDependencies();
    }

    /**
     * Инициализация данных для работы программы
     */
    private void init() {
        System.out.println("-------------");
        System.out.println("Запущено MODV");
        System.out.println("-------------");
        AppConfig appConfig = new AppConfig();
    }

    /**
     * Вывод зависимостей из конфигурации
     */
    private void printDependencies() {
        AppInputData data = AppConfig.getData();
        try {
            NugetClient client = new NugetClient();
            NuspecParser parser = new NuspecParser();
            String xml = client.getNuspec(data);
            List<Dependency> dependencies = parser.parse(xml);

            System.out.println(
                    "\nПрямые зависимости пакета "
                            + data.packageName()
                            + " версии "
                            + data.packageVersion()
                            + ":");

            if (dependencies.isEmpty()) {
                System.out.println("Прямых зависимостей нет");
            }

            for (Dependency dependency : dependencies) {
                System.out.println(dependency.packageName() + " версия: " + dependency.version());
            }
        } catch (Exception e) {
            System.out.println("Ошибка получения зависимостей: " + e.getMessage());
        }
    }
}