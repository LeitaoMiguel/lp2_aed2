package LP2_AED2_PROJETO;

import java.util.*;

public abstract class Publicacao {
  private String nome;
  private int ano;

  public Publicacao(String nome, int ano) {
    this.nome = nome;
    this.ano = ano;
  }

  // Getters e setters
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public abstract String getTipo();
}