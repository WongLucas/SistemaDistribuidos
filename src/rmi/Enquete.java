package rmi;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Enquete implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String pergunta;
    List<String> opcoes;
    Date dataLimite;
    Map<String, Integer> votos;
    Map<String, String> usuariosQueVotaram;

    public Enquete(String pergunta, List<String> opcoes, Date dataLimite) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.dataLimite = dataLimite;
        this.votos = new HashMap<>();
        for (String opcao : opcoes) {
            votos.put(opcao, 0);
        }
        this.usuariosQueVotaram = new HashMap<>();
    }

    public boolean isAtiva() {
        return new Date().before(dataLimite);
    }
}
