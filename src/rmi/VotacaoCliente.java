package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VotacaoCliente {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost"); // ou IP do servidor
            VotacaoInterface stub = (VotacaoInterface) registry.lookup("Votacao");

            // Exemplo: criar uma enquete
            List<String> opcoes = Arrays.asList("Java", "Python", "C++");
            int id = stub.criar_enquete("admin", "Qual sua linguagem favorita?", opcoes, new Date(System.currentTimeMillis() + 86400000)); // 1 dia à frente

            System.out.println("Enquete criada com ID: " + id);

            // Votar
            String resposta = stub.votar("usuario1", id, "Java");
            System.out.println(resposta);

            // Consultar resultados
            System.out.println(stub.consultar_resultados(id));

            // Listar enquetes ativas
            System.out.println(stub.listar_enquetes_ativas());

        } catch (Exception e) {
            System.err.println("Exceção no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
