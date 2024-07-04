package com.alura.alura_cultural.repository;

import com.alura.alura_cultural.modelo.Autor;
import com.alura.alura_cultural.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface Repository extends JpaRepository<Livro, Long> {
  @Query("SELECT a FROM Livro l JOIN l.autor a")
  List<Autor> buscarAutor();
  
  @Query ("SELECT a FROM Livro l JOIN l.autor a WHERE a.anoNascimento <= :ano and a.anoFalecimento >= :ano")
  List<Autor> buscarAutorVivoNoAno(Year ano);
  
  List<Livro> findByIdioma(String idioma);
  
  @Query ("SELECT a FROM Livro l JOIN l.autor a WHERE a.autor = :autor")
  Autor buscarAutorPeloNome(String autor);
  
  Integer countByIdioma(String idioma);
  
  @Query(value = "SELECT * FROM livro ORDER BY qtde_download DESC LIMIT 10", nativeQuery = true)
  List<Livro> topLivroPorQtdeDownload();
}
