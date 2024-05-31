package LP2_AED2_PROJETO;

import java.util.ArrayList;
import java.util.Date;

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

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }

    public void setReferencia(Artigo referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return artigo.getTitulo() + " citou " + referencia.getTitulo();
    }

    public Date getData() {
        return this.referencia.getData();
    }

    public ArrayList<Autor> getAutores() {
        return this.referencia.getAutores();
    }
}

