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


  public void removerAutor(Autor autor) {
    // Verifica se o autor existe no grafo
    if (autorParaAutor2.containsKey(autor)) {
      int indiceARemover = autorParaAutor2.get(autor);

      // Remove o autor dos mapas
      autorParaAutor2.remove(autor);
      autor2ParaAutor.remove(indiceARemover);

      // Criar um novo grafo com um vértice a menos
      Graph novoGrafo = new Graph(contadorVertice - 1);

      // Mapeamento de antigos índices para novos índices
      Map<Integer, Integer> novoIndice = new HashMap<>();
      int novoIndiceContador = 0;
      for (int i = 0; i < contadorVertice; i++) {
        if (i != indiceARemover) {
          novoIndice.put(i, novoIndiceContador);
          novoIndiceContador++;
        }
      }

      // Copia as arestas para o novo grafo, excluindo o vértice removido
      for (int v = 0; v < grafo.V(); v++) {
        if (v == indiceARemover) continue;
        for (int w : grafo.adj(v)) {
          if (w != indiceARemover) {
            novoGrafo.addEdge(novoIndice.get(v), novoIndice.get(w));
          }
        }
      }

      // Atualiza a referência do grafo
      grafo = novoGrafo;

      // Atualiza o contador de vértices
      contadorVertice--;

      // Atualiza os mapas autorParaIndice e indiceParaAutor com novos índices
      Map<Autor, Integer> novoAutorParaIndice = new HashMap<>();
      Map<Integer, Autor> novoIndiceParaAutor = new HashMap<>();

      for (Map.Entry<Autor, Integer> entry : autorParaAutor2.entrySet()) {
        Autor a = entry.getKey();
        int antigoIndice = entry.getValue();
        if (antigoIndice != indiceARemover) {
          int novoIndiceValor = novoIndice.get(antigoIndice);
          novoAutorParaIndice.put(a, novoIndiceValor);
          novoIndiceParaAutor.put(novoIndiceValor, a);
        }
      }

      autorParaAutor2 = novoAutorParaIndice;
      autor2ParaAutor = novoIndiceParaAutor;
    }
  }

}