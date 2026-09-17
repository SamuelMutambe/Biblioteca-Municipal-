## Sistema de Gestão da Biblioteca Municipal

Projeto desenvolvido para a disciplina de **Introdução a Algoritmos e Programação**.

## Objetivo

Desenvolver uma aplicação Java baseada em consola para gerir livros, utilizadores, empréstimos, devoluções e estatísticas de uma Biblioteca Municipal.

## Funcionalidades

1. Registo de livros.
2. Listagem do catálogo.
3. Pesquisa de livros por título ou autor.
4. Registo de utilizadores.
5. Listagem de utilizadores.
6. Empréstimo de livros.
7. Devolução de livros.
8. Estatísticas de empréstimos.

## Estruturas de dados utilizadas

- `Livro[]`: vetor para armazenar os livros.
- `Utilizador[]`: vetor para armazenar os utilizadores.
- `boolean[][] emprestimoAtivo`: matriz que controla empréstimos atualmente ativos.
- `int[][] totalEmprestimos`: matriz que regista o número de vezes que cada utilizador requisitou cada livro.

## Tecnologias utilizadas

- Java JDK 8;
- IntelliJ IDEA;
- GitHub.

Como executar

Abra o terminal na pasta do projeto e execute:

```bash
javac BibliotecaApp.java
java BibliotecaApp
```

Dados de teste

Ao iniciar, o programa carrega três livros e dois utilizadores de exemplo:

- U001 — Ana
- U002 — Carlos
- L001 — Java para Iniciantes
- L002 — Algoritmos e Estruturas de Dados
- L003 — Programação em Java

## GitHub
https://github.com/SamuelMutambe/Biblioteca-Municipal-

## Autor
Samuel Joao Mutambe

## Observação

A aplicação utiliza armazenamento em memória. Ao fechar o progr
