package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Date;

public interface VotacaoInterface extends Remote{
    int criar_enquete(String usuario, String pergunta, List<String> opcoes, Date data_limite) throws RemoteException;
    String votar(String usuario, int id_enquete, String opcao) throws RemoteException;
    Map<String, Integer> consultar_resultados(int id_enquete) throws RemoteException;
    List<String> listar_enquetes_ativas() throws RemoteException;
}
