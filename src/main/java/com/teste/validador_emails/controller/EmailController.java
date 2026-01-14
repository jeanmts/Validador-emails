package com.teste.validador_emails.controller;

import com.teste.validador_emails.service.FileService;
import com.teste.validador_emails.service.GroqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmailController {

    private final GroqService groqService;

    private final FileService fileService;

    public EmailController(GroqService groqService, FileService fileService) {
        this.groqService = groqService;
        this.fileService = fileService;
    }

    @PostMapping("/analisar")
    public ResponseEntity<Map<String, String>> analisarEmail(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "text", required = false) String text) {

        String conteudoFinal = "";

        try {

            if (file != null && !file.isEmpty()) {
                conteudoFinal = fileService.extrairTexto(file);
            }
            else if (text != null && !text.isBlank()) {
                conteudoFinal = text;
            }
            else {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "Envie um arquivo PDF/TXT ou digite o texto."));
            }

            Map<String, String> resultado = groqService.analisarEmail(conteudoFinal);

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro interno: " + e.getMessage()));
        }
    }
}
