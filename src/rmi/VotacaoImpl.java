package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class VotacaoImpl extends UnicastRemoteObject implements VotacaoInterface {

    private Map<String, Enquete> enquetes;
    private List<String> usuariosAutorizados;

    public VotacaoImpl() throws RemoteException {
        super();
        enquetes = new HashMap<>();
        usuariosAutorizados = Arrays.asList("admin", "user1", "user2"); // Exemplo de usuários
    }

    @Override
    public String criar_enquete(String usuario, String pergunta, List<String> opcoes, Date data_limite) throws RemoteException {
        if (!usuariosAutorizados.contains(usuario)) {
            return "Erro: Usuário não autorizado.";
        }
        String id = UUID.randomUUID().toString();
        Enquete enquete = new Enquete(pergunta, opcoes, data_limite);
        enquetes.put(id, enquete);
        return id;
    }

    @Override
    public String votar(String usuario, String id_enquete, String opcao) throws RemoteException {
        Enquete enquete = enquetes.get(id_enquete);
        if (enquete == null) {
            return "Erro: Enquete não encontrada.";
        }
        if (!enquete.isAtiva()) {
            return "Erro: Enquete encerrada.";
        }
        if (enquete.usuariosQueVotaram.containsKey(usuario)) {
            return "Erro: Voto já registrado.";
        }
        if (!enquete.opcoes.contains(opcao)) {
            return "Erro: Opção inválida.";
        }
        enquete.votos.put(opcao, enquete.votos.get(opcao) + 1);
        enquete.usuariosQueVotaram.put(usuario, opcao);
        return "Voto registrado com sucesso.";
    }

    @Override
    public Map<String, Integer> consultar_resultados(String id_enquete) throws RemoteException {
        Enquete enquete = enquetes.get(id_enquete);
        if (enquete == null) {
            return Collections.emptyMap();
        }
        return enquete.votos;
    }

    @Override
    public List<String> listar_enquetes_ativas() throws RemoteException {
        List<String> lista = new ArrayList<>();
        for (Map.Entry<String, Enquete> entry : enquetes.entrySet()) {
            if (entry.getValue().isAtiva()) {
                lista.add("ID: " + entry.getKey() + " | Pergunta: " + entry.getValue().pergunta + " | Data Limite: " + entry.getValue().dataLimite);
            }
        }
        return lista;
    }
}
