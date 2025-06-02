package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VotacaoServer {
    public static void main(String[] args) {
        try{
            VotacaoImpl obj = new VotacaoImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Votacao", obj);
            System.out.println("Servidor em atividade...");
        }catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();            
        }
    }
    
}
