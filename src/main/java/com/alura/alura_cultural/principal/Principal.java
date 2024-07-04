package com.alura.alura_cultural.principal;

import com.alura.alura_cultural.modelo.Autor;
import com.alura.alura_cultural.modelo.Livro;
import com.alura.alura_cultural.modelo.LivroDTO;
import com.alura.alura_cultural.repository.Repository;
import com.alura.alura_cultural.service.ConverterDado;
import com.alura.alura_cultural.service.ConsultarApi;

import java.time.Year;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
  
  private Scanner leitor              = new Scanner(System.in);
  private ConsultarApi consultarApi   = new ConsultarApi();
  private ConverterDado converterDado = new ConverterDado();
  private String enderecoAPI          = "https://gutendex.com/books/?search=";
  private Repository repository;
  
  public Principal(){}
  
  public Principal(Repository repository){
    this.repository = repository;
  }
  
  public void menu(){
    int opcao = -1;
    while (opcao != 0){
      String opcoes = """
                \n---------------------------------------------------------
                       - Escolha uma das opções
                -----------------------------------------------------------
                0 - Sair
                1 - Pesquisar livro por título no ws
                2 - Listar livros gravados na base de dados local
                3 - Listar autores
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em determinado Idioma
                6 - Exibir a quantidade de livros em um determinado idioma
                7 - Listar os 10 livros mais baixados
                8 - Listar autor pelo nome
                -----------------------------------------------------------
                """;
      try {
        System.out.println(opcoes);
        opcao = leitor.nextInt();
        leitor.nextLine();
        
        switch (opcao){
          case 0:
            System.out.println("Saindo do aplicativo...");
            break;
          case 1:
            buscarLivro();
            break;
          case 2:
            listarLivros();
            break;
          case 3:
            listarAutores();
            break;
          case 4:
            listarAutoresVivosNoAno();
            break;
          case 5:
            listarLivrosPorIdioma();
            break;
          case 6:
            qtdeLivrosPorIdioma();
            break;
          case 7:
            DezLivrosMaisBaixados();
            break;
          case 8:
            atorPeloNome();
            break;
          default:
            System.out.println("Opção inválida.");
        }
      }catch (InputMismatchException e){
        System.out.println("Deverá ser informado um número inteiro.");
        leitor.nextLine();
      }
    }
  }
  
  private void buscarLivro() {
    System.out.println("Informe o nome do Livro: ");
    var nomeLivro = leitor.nextLine();
    System.out.println("Buscando...");
    
    String enderecoBusca = enderecoAPI.concat(nomeLivro.replace(" ", "+").toLowerCase().trim());
    String json          = consultarApi.pesquisar(enderecoBusca);
    String jsonLivro     = converterDado.extraiObjetoJson(json, "results");
    
    List<LivroDTO> livrosDTO = converterDado.obterLista(jsonLivro, LivroDTO.class);
    
    if (livrosDTO.size() > 0) {
      Livro livro = new Livro(livrosDTO.get(0));
      
      Autor autor = repository.buscarAutorPeloNome(livro.getAutor().getAutor());
      //Grava o autor caso ele ainda não esteja cadastrado
      if (autor != null) {
        System.out.println("Gravando informações do autor...");
        
        livro.setAutor(null);
        repository.save(livro);
        livro.setAutor(autor);
      }
      
      System.out.println("Gravando livro...");
      
      livro = repository.save(livro);
      System.out.println(livro);
    } else {
      System.out.println("Livro não encontrado");
    }
  }
  
  private void listarLivros() {
    List<Livro> listaLivros = repository.findAll();
    listaLivros.forEach(System.out::println);
  }
  
  private void listarAutores() {
    List<Autor> autores = repository.buscarAutor();
    autores.forEach(System.out::println);
  }
  
  private void listarAutoresVivosNoAno() {
    try {
      System.out.println("Digite o ano:");
      int ano = leitor.nextInt();
      leitor.nextLine();
      
      List<Autor> autores = repository.buscarAutorVivoNoAno(Year.of(ano));
      autores.forEach(System.out::println);
    }catch (InputMismatchException e){
      System.out.println("Entrada inválida. Informe um válido.");
      leitor.nextLine();
    }
  }
  
  private void listarLivrosPorIdioma() {
    System.out.println("""
                Informe o idioma para busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
    
    String idioma      = leitor.nextLine();
    List<Livro> livros = repository.findByIdioma(idioma);
    
    if (!livros.isEmpty()){
      livros.forEach(System.out::println);
    }else{
      System.out.println("Não foram encontrados livros cadastrados nesse idioma na base de dados local.");
    }
  }
  
  private void qtdeLivrosPorIdioma() {
    System.out.println("""
                Informe o idioma para busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
    String idioma                   = leitor.nextLine();
    Integer quantidadeObraPorIdioma = repository.countByIdioma(idioma);
    
    System.out.printf("Há %s %d livros cadastrados no idioma informado.\n", idioma, quantidadeObraPorIdioma);
  }
  
  private void DezLivrosMaisBaixados(){
    List<Livro> livros = repository.topLivroPorQtdeDownload();
    livros.forEach(System.out::println);
  }
  
  private void atorPeloNome() {
    System.out.println("Informe o nome do autor para a pesquisa:");
    String nomeAutor = leitor.nextLine();
    Autor autor = repository.buscarAutorPeloNome(nomeAutor);
    System.out.println(autor);
  }
}
