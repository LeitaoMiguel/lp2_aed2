package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;

import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

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

  // Listar artigos por publicação e período
  public ArrayList<Artigo> listarArtigosPorPublicacaoEPeriodo(String nomePublicacao, Date inicio, Date fim) {
    ArrayList<Artigo> resultado = new ArrayList<>();
    for (Artigo artigo : artigoParaIndice.keySet()) {
      if (artigo.getPublicacao().getNome().equals(nomePublicacao) &&
              !artigo.getData().before(inicio) && !artigo.getData().after(fim)) {
        resultado.add(artigo);
      }
    }
    return resultado;
  }

  // Calcular citações de 1ª ordem
  public int calcularCitacoesPrimeiraOrdem(Artigo artigo) {
    int v = artigoParaIndice.getOrDefault(artigo, -1);
    if (v == -1) return 0;
    return grafo.indegree(v);
  }

  // Calcular citações de 2ª ordem
  public int calcularCitacoesSegundaOrdem(Artigo artigo) {
    int v = artigoParaIndice.getOrDefault(artigo, -1);
    if (v == -1) return 0;
    int citacoesSegundaOrdem = 0;
    for (int w : grafo.adj(v)) {
      citacoesSegundaOrdem += grafo.indegree(w);
    }
    return citacoesSegundaOrdem;
  }

  // Calcular autocitações
  public int calcularAutocitacoes(Artigo artigo) {
    int autocitacoes = 0;
    ArrayList<Autor> autores = artigo.getAutores();
    for (Artigo referencia : artigo.getReferencias()) {
      for (Autor autor : referencia.getAutores()) {
        if (autores.contains(autor)) {
          autocitacoes++;
          break;
        }
      }
    }
    return autocitacoes;
  }

  // Selecionar sub-grafo por tipo de publicação
  public GrafoArtigos selecionarSubGrafoPorTipo(String tipoPublicacao) {
    GrafoArtigos subGrafo = new GrafoArtigos();
    for (Artigo artigo : artigoParaIndice.keySet()) {
      if (artigo.getPublicacao().getTipo().equals(tipoPublicacao)) {
        subGrafo.adicionarArtigo(artigo);
      }
    }
    return subGrafo;
  }

  // Verificar se o grafo é conexo
  public boolean isConexo() {
    edu.princeton.cs.algs4.ConnectedComponents cc = new edu.princeton.cs.algs4.ConnectedComponents(grafo);
    return cc.count() == 1;
  }

}