package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.*;

public class VotacaoCliente {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            VotacaoInterface stub = (VotacaoInterface) registry.lookup("Votacao");

            Scanner scanner = new Scanner(System.in);
            String usuario;

            System.out.println("Informe seu nome de usuário:");
            usuario = scanner.nextLine();

            while (true) {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Criar enquete");
                System.out.println("2. Votar");
                System.out.println("3. Consultar resultados");
                System.out.println("4. Listar enquetes ativas");
                System.out.println("5. Sair");

                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.println("Pergunta da enquete:");
                        String pergunta = scanner.nextLine();

                        System.out.println("Digite as opções separadas por vírgula:");
                        String[] opcoesArray = scanner.nextLine().split(",");
                        List<String> opcoes = new ArrayList<>();
                        for (String op : opcoesArray) {
                            opcoes.add(op.trim());
                        }

                        System.out.println("Data limite (dd/MM/yyyy):");
                        String dataStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataLimite = sdf.parse(dataStr);

                        String id = stub.criar_enquete(usuario, pergunta, opcoes, dataLimite);
                        System.out.println("Enquete criada. ID: " + id);
                        break;

                    case "2":
                        System.out.println("ID da enquete:");
                        String idEnquete = scanner.nextLine();
                        System.out.println("Opção:");
                        String opcaoVoto = scanner.nextLine();

                        String resposta = stub.votar(usuario, idEnquete, opcaoVoto);
                        System.out.println(resposta);
                        break;

                    case "3":
                        System.out.println("ID da enquete:");
                        String idConsulta = scanner.nextLine();

                        Map<String, Integer> resultados = stub.consultar_resultados(idConsulta);
                        if (resultados.isEmpty()) {
                            System.out.println("Enquete não encontrada.");
                        } else {
                            System.out.println("Resultados:");
                            resultados.forEach((k, v) -> System.out.println(k + ": " + v));
                        }
                        break;

                    case "4":
                        List<String> enquetes = stub.listar_enquetes_ativas();
                        if (enquetes.isEmpty()) {
                            System.out.println("Nenhuma enquete ativa.");
                        } else {
                            enquetes.forEach(System.out::println);
                        }
                        break;

                    case "5":
                        System.out.println("Saindo...");
                        return;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Exceção no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
