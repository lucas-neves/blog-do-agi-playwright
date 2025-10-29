# Blog Agibank Automation

Automação de testes para o blog do Agibank usando Playwright + TestNG.

## Pré-requisitos

- Java 11+
- Maven 3.6+

## Instalação

```bash
mvn clean install
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

## Execução

```bash
mvn test
```

## Relatório Allure

Para gerar e visualizar o relatório:

```bash
mvn allure:serve
```

Ou gerar apenas o relatório:

```bash
mvn allure:report
```

O relatório estará em `allure-report/index.html`

## Vídeos

Os vídeos das execuções são salvos automaticamente em `target/videos/`

## Estrutura do Projeto

```
├── src/test/java/com/agibank/
│   ├── pages/          # Page Objects
│   └── tests/          # Classes de teste
├── testng.xml          # Configuração TestNG
└── pom.xml             # Dependências Maven
```

## CI/CD

Os testes são executados automaticamente via GitHub Actions em pushes e pull requests.
Relatórios Allure e vídeos são disponibilizados como artifacts.

## Autor

Lucas Neves
