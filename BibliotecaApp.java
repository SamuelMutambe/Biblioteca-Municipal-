import java.util.Scanner;

/**
 * Sistema de Gestão da Biblioteca Municipal
 * Disciplina: Introdução a Algoritmos e Programação
 *
 * Base de dados simulada em memória:
 * - Vetores/arrays para livros e utilizadores.
 * - Matrizes para controlar empréstimos ativos e estatísticas de empréstimos.
 */
public class BibliotecaApp {

    static final int MAX_LIVROS = 100;
    static final int MAX_UTILIZADORES = 100;

    static Scanner scanner = new Scanner(System.in);

    static Livro[] livros = new Livro[MAX_LIVROS];
    static Utilizador[] utilizadores = new Utilizador[MAX_UTILIZADORES];

    // Matriz: [utilizador][livro] -> indica se o livro está atualmente emprestado.
    static boolean[][] emprestimoAtivo =
            new boolean[MAX_UTILIZADORES][MAX_LIVROS];

    // Matriz: [utilizador][livro] -> quantidade de vezes que foi requisitado.
    static int[][] totalEmprestimos =
            new int[MAX_UTILIZADORES][MAX_LIVROS];

    static int totalLivros = 0;
    static int totalUtilizadores = 0;
    static int totalRequisicoes = 0;

    public static void main(String[] args) {
        carregarDadosExemplo();

        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    registarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    pesquisarLivro();
                    break;
                case 4:
                    registarUtilizador();
                    break;
                case 5:
                    listarUtilizadores();
                    break;
                case 6:
                    realizarEmprestimo();
                    break;
                case 7:
                    realizarDevolucao();
                    break;
                case 8:
                    mostrarEstatisticas();
                    break;
                case 0:
                    System.out.println("\nPrograma encerrado. Obrigado!");
                    break;
                default:
                    System.out.println("\nOpção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("       BIBLIOTECA MUNICIPAL - MENU");
        System.out.println("==============================================");
        System.out.println("1. Registar livro");
        System.out.println("2. Listar catálogo");
        System.out.println("3. Pesquisar livro");
        System.out.println("4. Registar utilizador");
        System.out.println("5. Listar utilizadores");
        System.out.println("6. Efetuar empréstimo");
        System.out.println("7. Efetuar devolução");
        System.out.println("8. Ver estatísticas");
        System.out.println("0. Sair");
        System.out.println("==============================================");
    }

    static void registarLivro() {
        if (totalLivros >= MAX_LIVROS) {
            System.out.println("Limite de livros atingido.");
            return;
        }

        System.out.println("\n--- REGISTO DE LIVRO ---");
        String id;

        do {
            id = lerTexto("Identificador único: ");
            if (buscarLivroPorId(id) != -1) {
                System.out.println("Esse identificador já existe.");
            }
        } while (buscarLivroPorId(id) != -1);

        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        int ano = lerInteiro("Ano de publicação: ");
        int quantidade;

        do {
            quantidade = lerInteiro("Quantidade disponível: ");
            if (quantidade < 0) {
                System.out.println("A quantidade não pode ser negativa.");
            }
        } while (quantidade < 0);

        livros[totalLivros] = new Livro(id, titulo, autor, ano, quantidade);
        totalLivros++;

        System.out.println("Livro registado com sucesso!");
    }

    static void listarLivros() {
        System.out.println("\n--- CATÁLOGO DE LIVROS ---");

        if (totalLivros == 0) {
            System.out.println("Não existem livros registados.");
            return;
        }

        for (int i = 0; i < totalLivros; i++) {
            Livro l = livros[i];
            System.out.printf(
                "%d. ID: %s | Título: %s | Autor: %s | Ano: %d | Disponível: %d%n",
                i + 1, l.id, l.titulo, l.autor, l.ano, l.quantidadeDisponivel
            );
        }
    }

    static void pesquisarLivro() {
        System.out.println("\n--- PESQUISA DE LIVRO ---");
        System.out.println("1. Pesquisar por título");
        System.out.println("2. Pesquisar por autor");

        int opcao = lerInteiro("Escolha: ");
        String termo = lerTexto("Digite o termo de pesquisa: ").toLowerCase();

        boolean encontrou = false;

        for (int i = 0; i < totalLivros; i++) {
            Livro l = livros[i];

            boolean corresponde = false;

            if (opcao == 1) {
                corresponde = l.titulo.toLowerCase().contains(termo);
            } else if (opcao == 2) {
                corresponde = l.autor.toLowerCase().contains(termo);
            } else {
                System.out.println("Opção inválida.");
                return;
            }

            if (corresponde) {
                System.out.printf(
                    "ID: %s | Título: %s | Autor: %s | Ano: %d | Disponível: %d%n",
                    l.id, l.titulo, l.autor, l.ano, l.quantidadeDisponivel
                );
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum livro encontrado.");
        }
    }

    static void registarUtilizador() {
        if (totalUtilizadores >= MAX_UTILIZADORES) {
            System.out.println("Limite de utilizadores atingido.");
            return;
        }

        System.out.println("\n--- REGISTO DE UTILIZADOR ---");
        String id;

        do {
            id = lerTexto("Identificador do utilizador: ");
            if (buscarUtilizadorPorId(id) != -1) {
                System.out.println("Esse identificador já existe.");
            }
        } while (buscarUtilizadorPorId(id) != -1);

        String nome = lerTexto("Nome: ");

        utilizadores[totalUtilizadores] = new Utilizador(id, nome);
        totalUtilizadores++;

        System.out.println("Utilizador registado com sucesso!");
    }

    static void listarUtilizadores() {
        System.out.println("\n--- UTILIZADORES ---");

        if (totalUtilizadores == 0) {
            System.out.println("Não existem utilizadores registados.");
            return;
        }

        for (int i = 0; i < totalUtilizadores; i++) {
            System.out.printf(
                "%d. ID: %s | Nome: %s%n",
                i + 1, utilizadores[i].id, utilizadores[i].nome
            );
        }
    }

    static void realizarEmprestimo() {
        System.out.println("\n--- EMPRÉSTIMO ---");

        if (totalUtilizadores == 0 || totalLivros == 0) {
            System.out.println("É necessário ter pelo menos um utilizador e um livro.");
            return;
        }

        String idUtilizador = lerTexto("ID do utilizador: ");
        int indiceUtilizador = buscarUtilizadorPorId(idUtilizador);

        if (indiceUtilizador == -1) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        String idLivro = lerTexto("ID do livro: ");
        int indiceLivro = buscarLivroPorId(idLivro);

        if (indiceLivro == -1) {
            System.out.println("Livro não encontrado.");
            return;
        }

        Livro livro = livros[indiceLivro];

        if (livro.quantidadeDisponivel <= 0) {
            System.out.println("Não existem exemplares disponíveis.");
            return;
        }

        if (emprestimoAtivo[indiceUtilizador][indiceLivro]) {
            System.out.println("Este utilizador já possui este livro emprestado.");
            return;
        }

        livro.quantidadeDisponivel--;
        emprestimoAtivo[indiceUtilizador][indiceLivro] = true;
        totalEmprestimos[indiceUtilizador][indiceLivro]++;
        totalRequisicoes++;

        System.out.println("Empréstimo efetuado com sucesso!");
    }

    static void realizarDevolucao() {
        System.out.println("\n--- DEVOLUÇÃO ---");

        String idUtilizador = lerTexto("ID do utilizador: ");
        int indiceUtilizador = buscarUtilizadorPorId(idUtilizador);

        if (indiceUtilizador == -1) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        String idLivro = lerTexto("ID do livro: ");
        int indiceLivro = buscarLivroPorId(idLivro);

        if (indiceLivro == -1) {
            System.out.println("Livro não encontrado.");
            return;
        }

        if (!emprestimoAtivo[indiceUtilizador][indiceLivro]) {
            System.out.println("Não existe empréstimo ativo para este utilizador e livro.");
            return;
        }

        livros[indiceLivro].quantidadeDisponivel++;
        emprestimoAtivo[indiceUtilizador][indiceLivro] = false;

        System.out.println("Devolução registada com sucesso!");
    }

    static void mostrarEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");
        System.out.println("Número total de livros registados: " + totalLivros);
        System.out.println("Número total de utilizadores: " + totalUtilizadores);
        System.out.println("Número total de livros requisitados: " + totalRequisicoes);

        int maior = 0;
        int indiceLivroMaisEmprestado = -1;

        for (int j = 0; j < totalLivros; j++) {
            int soma = 0;

            for (int i = 0; i < totalUtilizadores; i++) {
                soma += totalEmprestimos[i][j];
            }

            if (soma > maior) {
                maior = soma;
                indiceLivroMaisEmprestado = j;
            }
        }

        if (indiceLivroMaisEmprestado == -1) {
            System.out.println("Livro mais emprestado: ainda não existem empréstimos.");
        } else {
            Livro l = livros[indiceLivroMaisEmprestado];
            System.out.println(
                "Livro mais emprestado: " + l.titulo +
                " (" + maior + " empréstimo(s))"
            );
        }
    }

    static int buscarLivroPorId(String id) {
        for (int i = 0; i < totalLivros; i++) {
            if (livros[i].id.equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    static int buscarUtilizadorPorId(String id) {
        for (int i = 0; i < totalUtilizadores; i++) {
            if (utilizadores[i].id.equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduza um número inteiro válido.");
            }
        }
    }

    static void carregarDadosExemplo() {
        livros[0] = new Livro("L001", "Java para Iniciantes",
                "Herbert Schildt", 2021, 3);
        livros[1] = new Livro("L002", "Algoritmos e Estruturas de Dados",
                "Robert Lafore", 2019, 2);
        livros[2] = new Livro("L003", "Programação em Java",
                "Deitel", 2020, 4);
        totalLivros = 3;

        utilizadores[0] = new Utilizador("U001", "Ana");
        utilizadores[1] = new Utilizador("U002", "Carlos");
        totalUtilizadores = 2;
    }

    static class Livro {
        String id;
        String titulo;
        String autor;
        int ano;
        int quantidadeDisponivel;

        Livro(String id, String titulo, String autor, int ano, int quantidadeDisponivel) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.ano = ano;
            this.quantidadeDisponivel = quantidadeDisponivel;
        }
    }

    static class Utilizador {
        String id;
        String nome;

        Utilizador(String id, String nome) {
            this.id = id;
            this.nome = nome;
        }
    }
}