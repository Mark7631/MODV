package services;

import entities.AppInputData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class NugetClient {
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * @param data параметры из конфигурации
     * @return xml ответ сервера
     */
    public String getNuspec(AppInputData data) throws IOException, InterruptedException {
        if (data.testMod() != null && data.testMod()) {
            return Files.readString(Path.of(data.repoUrl()));
        }
        String packageName = data.packageName().toLowerCase();
        String version = data.packageVersion().toLowerCase();

        String baseUrl = data.repoUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        String url = baseUrl + packageName + "/" + version + "/" + packageName + ".nuspec";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new IOException("Не удалось получить пакет. HTTP-код: " + response.statusCode());
            }

            return response.body();
        } catch (InterruptedException e) {
            throw new InterruptedException("Не удалось получить ответ от API");
        }
    }
}