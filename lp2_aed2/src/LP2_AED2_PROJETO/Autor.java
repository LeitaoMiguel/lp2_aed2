package LP2_AED2_PROJETO;



import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class Autor {
  private String nome;
  private String nomeCurto;
  private String filiacao;
  private String orcid;
  private String cienciaId;
  private String googleScholarId;
  private String scopusAuthorId;
  private List<String> areasDeInvestigacao;
  private List<Artigo> historicoArtigos;

  public Autor(String nome, String nomeCurto, String filiacao, String orcid, String cienciaId, String googleScholarId, String scopusAuthorId) {
    this.nome = nome;
    this.nomeCurto = nomeCurto;
    this.filiacao = filiacao;
    this.orcid = orcid;
    this.cienciaId = cienciaId;
    this.googleScholarId = googleScholarId;
    this.scopusAuthorId = scopusAuthorId;
    this.areasDeInvestigacao = new ArrayList<>();
    this.historicoArtigos = new ArrayList<>();
  }

  // Getters e setters


  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNomeCurto() {
    return nomeCurto;
  }

  public void setNomeCurto(String nomeCurto) {
    this.nomeCurto = nomeCurto;
  }

  public String getFiliacao() {
    return filiacao;
  }

  public void setFiliacao(String filiacao) {
    this.filiacao = filiacao;
  }

  public String getOrcid() {
    return orcid;
  }

  public void setOrcid(String orcid) {
    this.orcid = orcid;
  }

  public String getCienciaId() {
    return cienciaId;
  }

  public void setCienciaId(String cienciaId) {
    this.cienciaId = cienciaId;
  }

  public String getGoogleScholarId() {
    return googleScholarId;
  }

  public void setGoogleScholarId(String googleScholarId) {
    this.googleScholarId = googleScholarId;
  }

  public String getScopusAuthorId() {
    return scopusAuthorId;
  }

  public void setScopusAuthorId(String scopusAuthorId) {
    this.scopusAuthorId = scopusAuthorId;
  }

  public List<String> getAreasDeInvestigacao() {
    return areasDeInvestigacao;
  }

  public void setAreasDeInvestigacao(List<String> areasDeInvestigacao) {
    this.areasDeInvestigacao = areasDeInvestigacao;
  }

  public List<Artigo> getHistoricoArtigos() {
    return historicoArtigos;
  }

  public void setHistoricoArtigos(List<Artigo> historicoArtigos) {
    this.historicoArtigos = historicoArtigos;
  }

  // Método para adicionar artigo ao histórico
  public void adicionarArtigo(Artigo artigo) {
    if (!historicoArtigos.contains(artigo)) {
      historicoArtigos.add(artigo);
      artigo.adicionarAutor(this); // Garantir a relação bidirecional
    }
  }
}