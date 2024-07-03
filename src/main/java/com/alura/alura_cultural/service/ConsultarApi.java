package com.alura.alura_cultural.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarApi {
  
  public String pesquisar(String enderecoAPI){
    HttpClient client   = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(enderecoAPI))
      .build();
    HttpResponse<String> response  = null;
    
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      System.out.println("Ocorreu um erro ao pesquisar na API: " + enderecoAPI);
    }
    
    return response.body();
  }
}
