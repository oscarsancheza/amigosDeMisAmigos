package com.mcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

  public static void main(String[] args) {

    try {
      Scanner input = new Scanner(new File("amigos.txt"));
      Map<String, Set<String>> amigos = leerArchivo(input);

      Scanner console = new Scanner(System.in);
      System.out.print("¿Persona a buscar? ");
      String nombre = console.next();
      if (!amigos.containsKey(nombre)) {
        System.out.println("no existe en el archivo esa persona.");
      } else {
        System.out.println("\n----- Amigos de amigos -----");
        amigosDeAmigos(amigos, nombre, -1);

        System.out.print("\n¿Nivel a buscar? ");
        int nivel = console.nextInt();

        System.out.println("\n----- Por nivel -----");
        amigosDeAmigos(amigos, nombre, nivel);
      }

    } catch (FileNotFoundException e) {
      System.out.println(e.toString());
    }
  }

  public static void amigosDeAmigos(Map<String, Set<String>> amigos, String nombre, int nivel) {
    Set<String> amigosVistos = new TreeSet<>();
    Set<String> grupoActual = new TreeSet<>();
    Set<String> siguienteGrupo;

    grupoActual.add(nombre);
    int nivelGrafo = 0;
    System.out.println("nivel:" + nivelGrafo + " -- Persona:" + nombre);

    while (!grupoActual.isEmpty() && nivel != nivelGrafo) {
      nivelGrafo++;
      amigosVistos.addAll(grupoActual);
      siguienteGrupo = new TreeSet<>();
      for (String amigo : grupoActual) {
        siguienteGrupo.addAll(amigos.get(amigo));
      }

      siguienteGrupo.removeAll(amigosVistos);
      grupoActual = siguienteGrupo;
      if (!grupoActual.isEmpty()) {
        System.out.println("nivel:" + nivelGrafo + " -- Amigos:" + grupoActual);
      }
    }
  }

  public static Map<String, Set<String>> leerArchivo(Scanner input) {
    Map<String, Set<String>> amigos = new TreeMap<>();
    while (input.hasNextLine()) {
      String line = input.nextLine();
      if (line.contains("-")) {
        Scanner lineData = new Scanner(line);
        String nombreUno = lineData.next();
        lineData.next();
        String nombreDos = lineData.next();
        addTo(amigos, nombreUno, nombreDos);
        addTo(amigos, nombreDos, nombreUno);
      }
    }
    return amigos;
  }

  public static void addTo(Map<String, Set<String>> amigos, String nombreUno, String nombreDos) {

    if (!amigos.containsKey(nombreUno)) {
      amigos.put(nombreUno, new TreeSet<>());
    }
    amigos.get(nombreUno).add(nombreDos);
  }
}
