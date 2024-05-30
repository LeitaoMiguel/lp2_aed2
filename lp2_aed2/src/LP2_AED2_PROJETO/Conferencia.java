package LP2_AED2_PROJETO;

public class Conferencia extends Publicacao {
    private int numeroEdicao;
    private String local;

    public Conferencia(String nome, int ano, int numeroEdicao, String local) {
        super(nome, ano);
        this.numeroEdicao = numeroEdicao;
        this.local = local;
    }

    // Getters e setters
    public int getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(int numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
