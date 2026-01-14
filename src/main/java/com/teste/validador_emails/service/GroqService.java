package com.teste.validador_emails.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqService {


    private static final String ENV_KEY = System.getenv("GROQ_API_KEY");
    private static final String API_KEY = "Bearer " + (ENV_KEY != null ? ENV_KEY : "ERRO_CHAVE_NAO_CONFIGURADA");
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public Map<String, String> analisarEmail(String textoEmail) {
        Map<String, String> resultado = new HashMap<>();

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("response_format", Map.of("type", "json_object"));

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", """
                Você é um classificador de emails via JSON.
                Responda APENAS com o JSON cru, sem markdown (```json).
                Chaves obrigatórias (todas minúsculas):
                {
                    "classificacao": "Produtivo" ou "Improdutivo",
                    "resumo": "Resumo curto",
                    "resposta_sugerida": "Texto da resposta"
                }
                """);
            messages.add(systemMessage);

            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", textoEmail);
            messages.add(userMessage);

            body.put("messages", messages);
            body.put("temperature", 0.3);

            String jsonRequest = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Erro API: " + response.body());
                throw new RuntimeException("Erro na API Groq: " + response.statusCode());
            }

            JsonNode rootResponse = objectMapper.readTree(response.body());
            String conteudoIaString = rootResponse.get("choices").get(0).get("message").get("content").asText();

            conteudoIaString = conteudoIaString.replace("```json", "").replace("```", "").trim();

            System.out.println("JSON Recebido da IA: " + conteudoIaString);

            JsonNode jsonIa = objectMapper.readTree(conteudoIaString);

            String classif = jsonIa.path("classificacao").asText("Indefinido");
            String resp = jsonIa.path("resposta_sugerida").asText("Não foi possível gerar resposta.");
            String resumo = jsonIa.path("resumo").asText("");

            resultado.put("classificacao", classif);
            resultado.put("resposta_sugerida", resp);
            resultado.put("resumo", resumo);

        } catch (Exception e) {
            e.printStackTrace();
            resultado.put("classificacao", "Erro");
            resultado.put("resposta_sugerida", "Erro técnico ao processar retorno da IA. Verifique o console.");
            resultado.put("debug_erro", e.getMessage());
        }

        return resultado;
    }
}