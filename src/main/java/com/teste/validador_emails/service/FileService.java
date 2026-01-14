package com.teste.validador_emails.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    public String extrairTexto(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {
                return lerPdf(file);
            } else {
                return lerTxt(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    private String lerPdf(MultipartFile file) throws IOException {

        try (PDDocument document = PDDocument.load(file.getInputStream())) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
    private String lerTxt(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
