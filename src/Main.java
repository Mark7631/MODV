import configs.AppConfig;

class Main {
    void main(String[] args) {
        init();
        if (AppConfig.isCorrectData()) {
            System.out.println("Не было обнаружено ошибок в конфигурации в ходе первичной проверки");
        } else {
            System.out.println("Были обнаружены выше перечисленные ошибки");
        }

        System.out.println("Параметры конфигурации:\n" + AppConfig.getData());
    }

    /**
     * Инициализация данных для работы программы
     */
    private void init () {
        System.out.println("-------------");
        System.out.println("Запущено MODV");
        System.out.println("-------------");
        AppConfig appConfig = new AppConfig();
    }
}
