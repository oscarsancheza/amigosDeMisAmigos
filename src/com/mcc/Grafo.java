package com.mcc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {
  private Map<String, Set<String>> listaDatos;

  public Grafo() {
    listaDatos = new HashMap<>();
  }

  public void addArista(String verticeUno, String verticeDos) {

    listaDatos.putIfAbsent(verticeUno, new HashSet<>());
    listaDatos.putIfAbsent(verticeDos, new HashSet<>());

    listaDatos.get(verticeUno).add(verticeDos);
    listaDatos.get(verticeDos).add(verticeUno);
  }

  public boolean existeVertice(String vertice) {
    return (listaDatos != null && listaDatos.containsKey(vertice));
  }

  public void imprimeAristasPorNivel(String vertice, int nivel) {
    Set<String> amigosVistos = new HashSet<>();
    Set<String> grupoActual = new HashSet<>();
    Set<String> siguienteGrupo;
    boolean tieneAmigos = false;

    grupoActual.add(vertice);
    int nivelGrafo = 0;
    System.out.println("-- Persona:" + vertice + " --");

    while (!grupoActual.isEmpty() && nivel != nivelGrafo) {
      nivelGrafo++;
      amigosVistos.addAll(grupoActual);
      siguienteGrupo = new HashSet<>();
      for (String amigo : grupoActual) {
        if (listaDatos != null) {
          siguienteGrupo.addAll(listaDatos.get(amigo));
        }
      }

      siguienteGrupo.removeAll(amigosVistos);
      grupoActual = siguienteGrupo;
      if ((!grupoActual.isEmpty() && nivel == nivelGrafo)
          || (!grupoActual.isEmpty() && nivel == -1)) {
        tieneAmigos = true;
        System.out.println("nivel:" + nivelGrafo + " -- Amigos:" + grupoActual);
      }
    }

    if (!tieneAmigos && nivel != 0) {
      System.out.println("No cuenta con ese nivel de amigos");
    }
  }
}
