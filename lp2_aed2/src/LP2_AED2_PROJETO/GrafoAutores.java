package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrafoAutores {

  private Graph grafo;
  private Map<Autor, Integer> autorParaAutor2;
  private Map<Integer, Autor> autor2ParaAutor;
  private int contadorVertice;

  public GrafoAutores() {
    this.grafo = new Graph(0);
    this.autorParaAutor2 = new HashMap<>();
    this.autor2ParaAutor = new HashMap<>();
    this.contadorVertice = 0;
  }

  public void adicionarAutor(Autor autor) {
    if (!autorParaAutor2.containsKey(autor)) {
      autorParaAutor2.put(autor, contadorVertice);
      autor2ParaAutor.put(contadorVertice, autor);
      contadorVertice++;
      // Expandir o grafo para acomodar novos vértices
      Graph novoGrafo = new Graph(contadorVertice);
      for (int v = 0; v < grafo.V(); v++) {
        for (int w : grafo.adj(v)) {
          novoGrafo.addEdge(v, w);
        }
      }
      grafo = novoGrafo;
    }
  }

  public void adicionarColaboracao(Autor autor1, Autor autor2) {
    if (autorParaAutor2.containsKey(autor1) && autorParaAutor2.containsKey(autor2)) {
      int v = autorParaAutor2.get(autor1);
      int w = autorParaAutor2.get(autor2);
      grafo.addEdge(v, w);
    }
  }
}