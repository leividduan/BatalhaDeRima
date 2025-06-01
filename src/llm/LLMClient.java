
package llm;

import okhttp3.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LLMClient {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public String invokeModel(String model, String prompt) {
        // Escape special characters in the prompt
        String escapedPrompt = prompt.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        // Create JSON with model parameter
        String requestBody = String.format("{\"model\": \"%s\", \"prompt\": \"%s\"}",
                model, escapedPrompt);

        RequestBody body = RequestBody.create(requestBody, JSON);

        Request request = new Request.Builder()
                .url("http://localhost:4444/generate-text")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                throw new IOException("Server returned error " + response.code() +
                        ": " + response.message() + "\nBody: " + errorBody);
            }

            String responseBody = response.body().string();
            String extracted = extractTextField(responseBody);
            if (extracted == null) {
                throw new IOException("Could not extract text field from response: " + responseBody);
            }
            return extracted;
        } catch (IOException e) {
            System.err.println("Error calling LLM service: " + e.getMessage());
            throw new RuntimeException("Failed to generate text: " + e.getMessage(), e);
        }
    }

    private String extractTextField(String json) {
        int startIndex = json.indexOf("\"text\":\"");
        if (startIndex == -1) return null;
        startIndex += "\"text\":\"".length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) return null;

        return json.substring(startIndex, endIndex)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }

    public void speakText(String text) {
        String escapedText = text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        String requestBody = String.format("{\"text\": \"%s\"}", escapedText);

        RequestBody body = RequestBody.create(requestBody, JSON);
        Request request = new Request.Builder()
                .url("http://localhost:4444/generate-audio") // URL da sua API de áudio
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed: " + response.code());
            }

            byte[] audioBytes = response.body().bytes();
            System.out.println(audioBytes);
            playWav(audioBytes);

        } catch (IOException e) {
            System.err.println("Erro ao gerar áudio: " + e.getMessage());
        }
    }

    // Reproduz WAV
    private void playWav(byte[] audioBytes) {
        try (InputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes)) {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Aguarda a reprodução
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(100);

            clip.close();
        } catch (Exception e) {
            System.err.println("Erro ao reproduzir WAV: " + e.getMessage());
        }
    }
}