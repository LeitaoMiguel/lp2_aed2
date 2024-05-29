package LP2_AED2_PROJETO;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivador {
    public static void arquivarAutor(Autor autor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("autores_arquivados.txt", true))) {
            writer.write("Nome: " + autor.getNomeCurto() + "\n");
            writer.write("ID: " + autor.getOrcid() + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void arquivarArtigo(Artigo artigo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("artigos_arquivados.txt", true))) {
            writer.write("Título: " + artigo.getTitulo() + "\n");
            writer.write("Ano: " + artigo.getData() + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
