package LP2_AED2_PROJETO;

import edu.princeton.cs.algs4.*;
import java.util.HashMap;
import java.util.Map;

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

  public Iterable<Autor> listarAutores() {
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

  public Iterable<Artigo> listarArtigos() {
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

  public Iterable<Publicacao> listarPublicacoes() {
    return this.publicacoesPorNome.keys();
  }
}
