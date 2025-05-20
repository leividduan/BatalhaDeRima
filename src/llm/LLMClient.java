package llm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LLMClient {
    public String invokeModel(String model, String prompt) {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("{ \"model\": \"%s\", \"prompt\": \"%s\" }", model, prompt);

        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        StringBuilder fullResponse = new StringBuilder();

        try {
            HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Ignorar linhas vazias
                    if (line.trim().isEmpty()) continue;

                    // Parse JSON para extrair "response" e "done"
                    String responsePart = extractField(line);
                    if (responsePart != null) {
                        fullResponse.append(responsePart);
                    }

                    if (line.contains("\"done\":true")) {
                        break;
                    }
                }
            }
        } catch (IOException | InterruptedException | Error e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return fullResponse.toString();
    }

    // Função simples para extrair conteúdo entre dois delimitadores
    private String extractField(String json) {
        int startIndex = json.indexOf("\"response\":\"");
        if (startIndex == -1) return null;
        startIndex += "\"response\":\"".length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) return null;
        return json.substring(startIndex, endIndex).replace("\\n", "\n").replace("\\\"", "\"");
    }
}