package LP2_AED2_PROJETO;

public class Jornal extends Publicacao{
    private String publisher;
    private String periodicidade;
    private double jcrIf;
    private double scopusIf;

    public Jornal(String nome, int ano, String publisher, String periodicidade, double jcrIf, double scopusIf) {
        super(nome, ano);
        this.publisher = publisher;
        this.periodicidade = periodicidade;
        this.jcrIf = jcrIf;
        this.scopusIf = scopusIf;
    }

    // Getters e setters
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public double getJcrIf() {
        return jcrIf;
    }

    public void setJcrIf(double jcrIf) {
        this.jcrIf = jcrIf;
    }

    public double getScopusIf() {
        return scopusIf;
    }

    public void setScopusIf(double scopusIf) {
        this.scopusIf = scopusIf;
    }
}

