package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;

public class GrafoArtigos {
  private Digraph grafo;

  public GrafoArtigos() {
    this.grafo = new Digraph();
  }

  public void adicionarArtigo(Artigo artigo) {
    int v = this.grafo.V();
    this.grafo.addVertex(v);
    // Mapear artigo ao índice no grafo
    // Atualizar índices
  }

  public void removerArtigo(Artigo artigo) {
    int v = this.grafo.indexOf(artigo);
    this.grafo.removeVertex(v);
    // Atualizar índices
  }

  // Outros métodos relevantes
}