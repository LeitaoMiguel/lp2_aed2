package LP2_AED2_PROJETO;

public class Citacao {
    private Artigo artigo;
    private Artigo referencia;

    public Citacao(Artigo artigo, Artigo referencia) {
        this.artigo = artigo;
        this.referencia = referencia;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public Artigo getReferencia() {
        return referencia;
    }

    @Override
    public String toString() {
        return artigo.getTitulo() + " citou " + referencia.getTitulo();
    }
}

