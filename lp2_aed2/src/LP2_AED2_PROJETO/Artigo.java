package LP2_AED2_PROJETO;


import java.text.SimpleDateFormat;
import java.util.*;

public class Artigo {
  private String titulo;
  private Date data;
  private ArrayList<Autor> autores;
  private ArrayList<Artigo> referencias;
  private Map<Date, Integer> visualizacoes;
  private Map<Date, Integer> downloads;
  private Map<Date, Integer> votos;

  public Artigo(String titulo, Date data, ArrayList<Autor> autores) {
    this.titulo = titulo;
    this.data = data;
    this.autores = autores;
    this.referencias = new ArrayList<>();
    this.visualizacoes = new HashMap<>();
    this.downloads = new HashMap<>();
    this.votos = new HashMap<>();
  }

  public String getTitulo() {
    return titulo;
  }

  public Date getData() {
    return data;
  }

  public ArrayList<Autor> getAutores() {
    return autores;
  }

  public ArrayList<Artigo> getReferencias() {
    return referencias;
  }

  public void adicionarReferencia(Artigo referencia) {
    referencias.add(referencia);
  }

  public void adicionarVisualizacao(Date data) {
    visualizacoes.put(data, visualizacoes.getOrDefault(data, 0) + 1);
  }

  public void adicionarDownload(Date data) {
    downloads.put(data, downloads.getOrDefault(data, 0) + 1);
  }

  public void adicionarVoto(Date data) {
    votos.put(data, votos.getOrDefault(data, 0) + 1);
  }

  // Métodos para obter visualizações e downloads mensais e anuais
  public Map<String, Integer> getVisualizacoesMensais() {
    Map<String, Integer> visualizacoesMensais = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    for (Date data : visualizacoes.keySet()) {
      String mes = sdf.format(data);
      visualizacoesMensais.put(mes, visualizacoesMensais.getOrDefault(mes, 0) + visualizacoes.get(data));
    }
    return visualizacoesMensais;
  }

  public Map<String, Integer> getDownloadsMensais() {
    Map<String, Integer> downloadsMensais = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    for (Date data : downloads.keySet()) {
      String mes = sdf.format(data);
      downloadsMensais.put(mes, downloadsMensais.getOrDefault(mes, 0) + downloads.get(data));
    }
    return downloadsMensais;
  }

  public Map<Integer, Integer> getVisualizacoesAnuais() {
    Map<Integer, Integer> visualizacoesAnuais = new HashMap<>();
    Calendar cal = Calendar.getInstance();
    for (Date data : visualizacoes.keySet()) {
      cal.setTime(data);
      int ano = cal.get(Calendar.YEAR);
      visualizacoesAnuais.put(ano, visualizacoesAnuais.getOrDefault(ano, 0) + visualizacoes.get(data));
    }
    return visualizacoesAnuais;
  }

  public Map<Integer, Integer> getDownloadsAnuais() {
    Map<Integer, Integer> downloadsAnuais = new HashMap<>();
    Calendar cal = Calendar.getInstance();
    for (Date data : downloads.keySet()) {
      cal.setTime(data);
      int ano = cal.get(Calendar.YEAR);
      downloadsAnuais.put(ano, downloadsAnuais.getOrDefault(ano, 0) + downloads.get(data));
    }
    return downloadsAnuais;
  }

  public Map<String, Integer> getVotosMensais() {
    Map<String, Integer> votosMensais = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    for (Date data : votos.keySet()) {
      String mes = sdf.format(data);
      votosMensais.put(mes, votosMensais.getOrDefault(mes, 0) + votos.get(data));
    }
    return votosMensais;
  }
}