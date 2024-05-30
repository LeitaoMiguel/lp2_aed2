package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Date;


public class Database {
  private RedBlackBST<String, Autor> autoresPorNome;
  private RedBlackBST<String, Artigo> artigosPorTitulo;
  private RedBlackBST<String, Publicacao> publicacoesPorNome;
  private GrafoArtigos grafoArtigos;
  private GrafoAutores grafoAutores;
  private Map<Autor, Integer> autorIndice;
  private Map<Artigo, Integer> artigoIndice;

  public Database() {
    this.autoresPorNome = new RedBlackBST<>();
    this.artigosPorTitulo = new RedBlackBST<>();
    this.publicacoesPorNome = new RedBlackBST<>();
    this.grafoArtigos = new GrafoArtigos();
    this.grafoAutores = new GrafoAutores();
    this.autorIndice = new HashMap<>();
    this.artigoIndice = new HashMap<>();
  }

  // Métodos para gerir Autores
  public void adicionarAutor(Autor autor) {
    this.autoresPorNome.put(autor.getNome(), autor);
    this.grafoAutores.adicionarAutor(autor);
  }

  public void removerAutor(String nome) {
    Autor autor = this.autoresPorNome.get(nome);
    if (autor != null) {
      // Arquivar autor
      Arquivador.arquivarAutor(autor);

      // Atualizar o grafo de autores
      this.grafoAutores.removerAutor(autor);

      // Remover o autor da BST
      this.autoresPorNome.delete(nome);

      // Atualizar os artigos do autor
      for (Artigo artigo : autor.getHistoricoArtigos()) {
        artigo.getAutores().remove(autor);
      }
    }
  }

  public Autor buscarAutor(String nome) {
    return this.autoresPorNome.get(nome);
  }

  public Iterable<String> listarAutores() {
    return this.autoresPorNome.keys();
  }

  // Métodos para gerir Artigos
  public void adicionarArtigo(Artigo artigo) {
    this.artigosPorTitulo.put(artigo.getTitulo(), artigo);
    this.grafoArtigos.adicionarArtigo(artigo);
  }

  public void removerArtigo(String titulo) {
    Artigo artigo = this.artigosPorTitulo.get(titulo);
    if (artigo != null) {
      // Arquivar artigo
      Arquivador.arquivarArtigo(artigo);

      // Atualizar o grafo de artigos
      this.grafoArtigos.removerArtigo(artigo);

      // Remover o artigo da BST
      this.artigosPorTitulo.delete(titulo);

      // Atualizar os autores do artigo
      for (Autor autor : artigo.getAutores()) {
        autor.getHistoricoArtigos().remove(artigo);
      }

      // Atualizar as referências de outros artigos
      for (Artigo refArtigo : artigo.getReferencias()) {
        refArtigo.getReferencias().remove(artigo);
      }
    }
  }

  public Artigo buscarArtigo(String titulo) {
    return this.artigosPorTitulo.get(titulo);
  }

  public Iterable<String> listarArtigos() {
    return this.artigosPorTitulo.keys();
  }

  // Métodos para gerir Publicacoes
  public void adicionarPublicacao(Publicacao publicacao) {
    this.publicacoesPorNome.put(publicacao.getNome(), publicacao);
  }

  public void removerPublicacao(String nome) {
    this.publicacoesPorNome.delete(nome);
  }

  public Publicacao buscarPublicacao(String nome) {
    return this.publicacoesPorNome.get(nome);
  }

  public Iterable<String> listarPublicacoes() {
    return this.publicacoesPorNome.keys();
  }

  public ArrayList<Artigo> artigosPorAutorNoPeriodo(String nomeAutor, Date inicio, Date fim) {
    Autor autor = buscarAutor(nomeAutor);
    ArrayList<Artigo> artigosNoPeriodo = new ArrayList<>();

    if (autor != null) {
      for (Artigo artigo : autor.getHistoricoArtigos()) {
        if (!artigo.getData().before(inicio) && !artigo.getData().after(fim)) {
          artigosNoPeriodo.add(artigo);
        }
      }
    }

    return artigosNoPeriodo;
  }

  public ArrayList<Artigo> artigosNaoVisualizadosOuDescarregadosNoPeriodo(Date inicio, Date fim) {
    ArrayList<Artigo> artigosNaoVisualizadosOuDescarregados = new ArrayList<>();

    for (String titulo : artigosPorTitulo.keys()) {
      Artigo artigo = artigosPorTitulo.get(titulo);
      if (artigo.getVisualizacoes(inicio, fim) == 0 && artigo.getDownloads(inicio, fim) == 0) {
        artigosNaoVisualizadosOuDescarregados.add(artigo);
      }
    }

    return artigosNaoVisualizadosOuDescarregados;
  }

  public ArrayList<Autor> autoresQueCitaramArtigosNoPeriodo(ArrayList<String> titulosArtigos, Date inicio, Date fim) {
    ArrayList<Autor> autores = new ArrayList<>();

    for (String titulo : artigosPorTitulo.keys()) {
      Artigo artigo = artigosPorTitulo.get(titulo);
      if (!artigo.getData().before(inicio) && !artigo.getData().after(fim)) {
        for (String tituloCitado : titulosArtigos) {
          if (artigo.getReferencias().contains(buscarArtigo(tituloCitado))) {
            for (Autor autor : artigo.getAutores()) {
              if (!autores.contains(autor)) {
                autores.add(autor);
              }
            }
          }
        }
      }
    }

    return autores;
  }

  public ArrayList<Artigo> top3ArtigosMaisUsadosNoPeriodo(Date inicio, Date fim) {
    PriorityQueue<Artigo> pq = new PriorityQueue<>(
            (a1, a2) -> Integer.compare(a2.getUsoTotal(inicio, fim), a1.getUsoTotal(inicio, fim))
    );

    for (String titulo : artigosPorTitulo.keys()) {
      Artigo artigo = artigosPorTitulo.get(titulo);
      pq.offer(artigo);
    }

    ArrayList<Artigo> top3 = new ArrayList<>();
    for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
      top3.add(pq.poll());
    }

    return top3;
  }

  public ArrayList<Citacao> citacoesDeJournalNoPeriodo(String nomeJournal, Date inicio, Date fim) {
    ArrayList<Citacao> citacoes = new ArrayList<>();

    for (String titulo : artigosPorTitulo.keys()) {
      Artigo artigo = artigosPorTitulo.get(titulo);
      if (artigo.getPublicacao() instanceof Journal && artigo.getPublicacao().getNome().equals(nomeJournal)) {
        if (!artigo.getData().before(inicio) && !artigo.getData().after(fim)) {
          for (Artigo referencia : artigo.getReferencias()) {
            if (!referencia.getData().before(inicio) && !referencia.getData().after(fim)) {
              citacoes.add(new Citacao(artigo, referencia));
            }
          }
        }
      }
    }

    return citacoes;
  }

}
