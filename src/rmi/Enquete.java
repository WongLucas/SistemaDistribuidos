package rmi;

import java.io.Serializable;
import java.util.*;

public class Enquete implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pergunta;
    private Map<String, Integer> opcoes;
    private Date dataLimite;
    private Set<String> usuariosVotaram;

    public Enquete(String pergunta, List<String> opcoes, Date dataLimite){
        this.pergunta = pergunta;
        this.dataLimite = dataLimite;
        this.usuariosVotaram = new HashSet<>();
        this.opcoes = new HashMap<>();
        for (String opcao : opcoes) {
            this.opcoes.put(opcao, 0);
        }
    }

        public String getPergunta() {
        return pergunta;
    }

    public Map<String, Integer> getOpcoes() {
        return opcoes;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public Set<String> getUsuariosVotaram() {
        return usuariosVotaram;
    }

    public boolean isAtiva() {
        return new Date().before(dataLimite);
    }
}
