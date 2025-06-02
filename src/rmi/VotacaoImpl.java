package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class VotacaoImpl extends UnicastRemoteObject implements VotacaoInterface{
    
    private static final long serialVersionUID = 1L;

    private Map<Integer, Enquete> enquetes;
    private Set<String> usuariosAutorizados;
    private int contadorEnquetes;

    protected VotacaoImpl() throws RemoteException{
        super();
        enquetes = new HashMap<>();
        usuariosAutorizados = new HashSet<>(Arrays.asList("admin", "user1", "user2"));
        contadorEnquetes = 0;
    }

    @Override
    public synchronized int criar_enquete(String usuario, String pergunta, List<String> opcoes, Date data_limite) throws RemoteException {
        if(!usuariosAutorizados.contains(usuario)){
            throw new RemoteException("Usuário não autorizado.");
        }
        contadorEnquetes++;
        Enquete enquete = new Enquete(pergunta, opcoes, data_limite);
        enquetes.put(contadorEnquetes, enquete);
        return contadorEnquetes;
    }

    @Override
    public synchronized String votar(String usuario, int id_enquete, String opcao) throws RemoteException {
        Enquete enquete = enquetes.get(id_enquete);
        if(enquete==null){
            return "Enquete não encontrada";
        }
        if(!enquete.isAtiva()) {
            return "Enquete encerrada";
        }
        if(enquete.getUsuariosVotaram().contains(usuario)) {
            return "Usuário já votou";
        }
        Map<String, Integer> opcoes = enquete.getOpcoes();
        if (!opcoes.containsKey(opcao)){
            return "Opção inválida";
        }
        opcoes.put(opcao, opcoes.get(opcao) + 1);
        enquete.getUsuariosVotaram().add(usuario);
        return "Voto registrado com sucesso";
    }

    @Override
    public synchronized Map<String, Integer> consultar_resultados(int id_enquete) throws RemoteException{
        Enquete enquete = enquetes.get(id_enquete);
        if(enquete == null){
            throw new RemoteException("Enquete não encontrada.");
        }
        return enquete.getOpcoes();
    }

    @Override
    public synchronized List<String> listar_enquetes_ativas() throws RemoteException{
        List<String> lista = new ArrayList<>();
        for(Map.Entry<Integer, Enquete> entry : enquetes.entrySet()) {
            Enquete e = entry.getValue();
            if(e.isAtiva()) {
               lista.add("ID: " + entry.getKey() + " | Pergunta: " + e.getPergunta() + " | Encerramento: " + e.getDataLimite());
            }
        }
        return lista;
    }
}
