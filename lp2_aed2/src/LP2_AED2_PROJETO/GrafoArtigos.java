package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;

import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.Map;

public class GrafoArtigos {
  private Digraph grafo;
  private Map<Artigo, Integer> artigoParaIndice;
  private Map<Integer, Artigo> indiceParaArtigo;
  private int contadorVertice;

  public GrafoArtigos() {
    this.grafo = new Digraph(0);
    this.artigoParaIndice = new HashMap<>();
    this.indiceParaArtigo = new HashMap<>();
    this.contadorVertice = 0;
  }

  public void adicionarArtigo(Artigo artigo) {
    if (!artigoParaIndice.containsKey(artigo)) {
      artigoParaIndice.put(artigo, contadorVertice);
      indiceParaArtigo.put(contadorVertice, artigo);
      contadorVertice++;
      // Expandir o grafo para acomodar novos vértices
      Digraph novoGrafo = new Digraph(contadorVertice);
      for (int v = 0; v < grafo.V(); v++) {
        for (int w : grafo.adj(v)) {
          novoGrafo.addEdge(v, w);
        }
      }
      grafo = novoGrafo;
    }
  }

  public void adicionarReferencia(Artigo artigo1, Artigo artigo2) {
    if (artigoParaIndice.containsKey(artigo1) && artigoParaIndice.containsKey(artigo2)) {
      int v = artigoParaIndice.get(artigo1);
      int w = artigoParaIndice.get(artigo2);
      grafo.addEdge(v, w);
    }
  }

  public void removerArtigo(Artigo artigo) {
    // Verifica se o artigo existe no grafo
    if (artigoParaIndice.containsKey(artigo)) {
      int indiceARemover = artigoParaIndice.get(artigo);

      // Remove o artigo dos mapas
      artigoParaIndice.remove(artigo);
      indiceParaArtigo.remove(indiceARemover);

      // Criar um novo grafo com um vértice a menos
      Digraph novoGrafo = new Digraph(contadorVertice - 1);

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

      // Atualiza os mapas artigoParaIndice e indiceParaArtigo com novos índices
      Map<Artigo, Integer> novoArtigoParaIndice = new HashMap<>();
      Map<Integer, Artigo> novoIndiceParaArtigo = new HashMap<>();

      for (Map.Entry<Artigo, Integer> entry : artigoParaIndice.entrySet()) {
        Artigo a = entry.getKey();
        int antigoIndice = entry.getValue();
        if (antigoIndice != indiceARemover) {
          int novoIndiceValor = novoIndice.get(antigoIndice);
          novoArtigoParaIndice.put(a, novoIndiceValor);
          novoIndiceParaArtigo.put(novoIndiceValor, a);
        }
      }

      artigoParaIndice = novoArtigoParaIndice;
      indiceParaArtigo = novoIndiceParaArtigo;
    }
  }

}