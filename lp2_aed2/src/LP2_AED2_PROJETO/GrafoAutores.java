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
  // Método 1: Listar vértices com base em critérios
  public ArrayList<Autor> listarAutoresPorInstituicao(String instituicao, boolean operadorOu) {
    ArrayList<Autor> autores = new ArrayList<>();
    for (Autor autor : autorParaAutor2.keySet()) {
      if (operadorOu) {
        if (autor.getGoogleScholarId().contains(instituicao)) {
          autores.add(autor);
        }
      } else {
        boolean match = true;
        for (String inst :autor.getGoogleScholarId()) {
          if (!inst.equals(instituicao)) {
            match = false;
            break;
          }
        }
        if (match) {
          autores.add(autor);
        }
      }
    }
    return autores;
  }
  // Método 2: Calcular com quantos autores um dado autor trabalhou
  public int calcularColaboradores(Autor autor) {
    int v = autorParaAutor2.get(autor);
    return grafo.degree(v);
  }
  // Método 3: Calcular o número de artigos escritos entre dois autores
  public int calcularArtigosEntreAutores(Autor autor1, Autor autor2) {
    int v = autorParaAutor2.get(autor1);
    int w = autorParaAutor2.get(autor2);
    int contadorArtigos = 0;
    for (Artigo artigo : autor1.getHistoricoArtigos()) {
      if (artigo.getAutores().contains(autor2)) {
        contadorArtigos++;
      }
    }
    return contadorArtigos;
  }
  // Método 4: Calcular caminhos mais curtos entre dois autores
  public int caminhoMaisCurto(Autor autor1, Autor autor2) {
    int v = autorParaAutor2.get(autor1);
    int w = autorParaAutor2.get(autor2);
    BreadthFirstPaths bfs = new BreadthFirstPaths(grafo, v);
    if (bfs.hasPathTo(w)) {
      return bfs.distTo(w);
    }
    return -1; // Retorna -1 se não há caminho
  }
  // Método 5: Selecionar um sub-grafo contendo apenas autores de determinadas instituições
  public Graph subGrafoPorInstituicoes(ArrayList<String> instituicoes) {
    Graph subGrafo = new Graph(contadorVertice);
    for (int v = 0; v < contadorVertice; v++) {
      Autor autor = autor2ParaAutor.get(v);
      if (autor != null && instituicoes.stream().anyMatch(inst -> autor.getGoogleScholarId().contains(inst))) {
        for (int w : grafo.adj(v)) {
          Autor colaborador = autor2ParaAutor.get(w);
          if (colaborador != null && instituicoes.stream().anyMatch(inst -> colaborador.getGoogleScholarId().contains(inst))) {
            subGrafo.addEdge(v, w);
          }
        }
      }
    }
    return subGrafo;
  }


  // Método 6: Verificar se o grafo é conexo
  public boolean isConexo() {
    DepthFirstSearch dfs = new DepthFirstSearch(grafo, 0);
    for (int v = 0; v < grafo.V(); v++) {
      if (!dfs.marked(v)) {
        return false;
      }
    }
    return true;
  }
}


}