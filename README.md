# 📧 Validador de Email - Classificador Inteligente

![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Groq AI](https://img.shields.io/badge/AI-Llama_3.3-blueviolet?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)

> Solução desenvolvida para o Desafio Técnico.
> Uma aplicação web que utiliza Inteligência Artificial Generativa para ler, interpretar e classificar emails corporativos, sugerindo respostas automáticas e aumentando a produtividade operacional.

---

## 🚀 Live Demo
Acesse a aplicação rodando em produção:
👉 **[CLIQUE AQUI PARA ACESSAR (Render)](https://validador-emails.onrender.com/)**

*(Nota: Como o deploy é gratuito, pode levar cerca de 50 segundos para "acordar" na primeira requisição. Por favor, aguarde o carregamento).*

---

## 🧠 Sobre o Projeto

O **Validador de Email** resolve o problema de sobrecarga de caixas de entrada em setores financeiros. Ele automatiza a triagem inicial:

1.  **Entrada:** Aceita texto direto ou upload de arquivos **PDF** e **TXT**.
2.  **Processamento:** Extrai o texto e envia para uma LLM (Large Language Model).
3.  **Inteligência:** Utiliza o modelo **Llama 3.3-70b** (via Groq Cloud) para entender o contexto.
4.  **Saída:**
    * Classificação: **Produtivo** (Requer ação) ou **Improdutivo** (Spam/Social).
    * Resumo: Breve síntese do assunto.
    * Resposta: Sugestão de resposta profissional pronta para copiar.

---

## 🛠️ Tecnologias Utilizadas

### Backend (Core)
* **Java 21+ / Spring Boot 3:** API REST robusta e escalável.
* **Apache PDFBox:** Para extração de texto de arquivos PDF binários.
* **Jackson:** Processamento e validação de JSON.

### Inteligência Artificial
* **Groq API:** Utilizada pela baixíssima latência.
* **Modelo Llama 3.3-70b-versatile:** Modelo de alta performance para compreensão de contexto e *Zero-Shot Classification*.
* **Prompt Engineering:** Técnicas de "JSON Mode" e "Role Play" para garantir respostas estruturadas.

### Frontend
* **HTML5 / JavaScript (Vanilla):** Leve e rápido, sem necessidade de build steps complexos (React/Vue).
* **Tailwind CSS:** Design moderno, responsivo e limpo.
* **Fetch API:** Comunicação assíncrona com o backend.

### Infraestrutura
* **Docker:** Containerização da aplicação (Dockerfile Multi-stage build).
* **Render:** Deploy contínuo (CI/CD) conectado ao GitHub.

---

## ⚙️ Como Rodar Localmente

### Pré-requisitos
* Java JDK 21 ou superior.
* Maven instalado.
* Uma chave de API da [Groq Cloud](https://console.groq.com/) (Gratuita).

### Passo a Passo

1. **Clone o repositório**
   ```bash
   git clone [https://github.com/jeanmts/Validador-emails.git](https://github.com/jeanmts/Validador-emails.git)
   cd Validador-emails


