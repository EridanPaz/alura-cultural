package com.alura.alura_cultural.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "livro")
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String titulo;
  
  @ManyToOne(cascade = CascadeType.ALL)
  private Autor autor;
  
  private String idioma;
  
  private Integer qtdeDownload;
  
  public Livro() {}
  
  public Livro(Long idApi, String titulo, Autor autor, String idioma, Integer qtdeDownload) {
    this.titulo       = titulo;
    this.autor        = autor;
    this.idioma       = idioma;
    this.qtdeDownload = qtdeDownload;
  }
  
  public Livro(LivroDTO livroDTO) {
    this.titulo       = livroDTO.titulo();
    Autor autor       = new Autor(livroDTO.authors().get(0));
    this.autor        = autor;
    this.idioma       = livroDTO.idioma().get(0);
    this.qtdeDownload = livroDTO.qtdedownload();
  }
  
  @Override
  public String toString() {
    return  "------------------ LIVRO ------------------" +
      "\nTítulo:                  " + titulo +
      "\nAutor:                   " + autor.getAutor() +
      "\nIdioma:                  " + idioma +
      "\nQuantidade de Downloads: " + qtdeDownload +
      "\n----------------------------------------------\n";
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public Autor getAutor() {
    return autor;
  }
  
  public void setAutor(Autor autor) {
    this.autor = autor;
  }
  
  public String getIdioma() {
    return idioma;
  }
  
  public void setIdioma(String idioma) {
    this.idioma = idioma;
  }
  
  public Integer getQtdeDownload() {
    return qtdeDownload;
  }
  
  public void setQtdeDownload(Integer qtdeDownload) {
    this.qtdeDownload = qtdeDownload;
  }
  
}
