package com.mcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Grafo amigos = new Grafo();

    try {
      Scanner input = new Scanner(new File("amigos.txt"));
      amigos = leerArchivo(input);
    } catch (FileNotFoundException e) {
      System.out.println(e.toString());
    }

    Scanner console = new Scanner(System.in);
    int opcion = 0;
    while (opcion != 3) {
      System.out.println(
          "\n1.Buscar amigos"
              + "\n2.Agregar amigo(s)"
              + "\n3.Salir"
              + "\nIngrese el numero de la opción:");

      opcion = console.nextInt();

      switch (opcion) {
        case 1:
          console = new Scanner(System.in);
          System.out.print("¿Persona y nivel a buscar? ");
          String busqueda = console.nextLine();
          String[] nombreNivel = busqueda.split(" ");

          if (nombreNivel.length > 0 && amigos.existeVertice(nombreNivel[0])) {
            String nombre = nombreNivel[0];
            int nivel = -1;
            if (nombreNivel.length > 1) {
              nivel = Integer.parseInt(nombreNivel[1]);
            }

            amigos.imprimeAristasPorNivel(nombre, nivel);

          } else {
            System.out.println("No existe esa persona.");
          }
          break;

        case 2:
          console = new Scanner(System.in);
          System.out.print("Escribe el nombre de la persona y sus amigos (oscar - omar,cesar):");
          agregarAristasGrafo(amigos,crearPersona(console.nextLine()));
          break;
      }
    }
  }

  private static Persona crearPersona(String formato) {
    Persona[] amigosDirectos;
    Persona persona = new Persona();
    String nombre;

    if (formato.contains("-")) {
      amigosDirectos = new Persona[1];
      // se lee la linea parte por parte
      Scanner lineData = new Scanner(formato);
      nombre = lineData.next();
      // aqui se salta el caracter -
      lineData.next();
      String amigos = lineData.next();

      if (amigos.contains(",")) {
        String[] variosAmigos = amigos.split(",");
        amigosDirectos = new Persona[variosAmigos.length];
        for (int x = 0; x < variosAmigos.length; x++) {
          amigosDirectos[x] = new Persona(variosAmigos[x]);
        }
      } else {
        amigosDirectos[0] = new Persona(amigos);
      }

      persona = new Persona(nombre, amigosDirectos);
    }

    return persona;
  }

  /**
   * Método que crea la lista de amigos
   *
   * @param input es el archivo que se quiere leer donde estan todos los amigos con formato oscar -
   *     miguel
   * @return retorna un map donde el key es el nombre de la persona y el value es una lista de los
   *     amigos
   */
  private static Grafo leerArchivo(Scanner input) {
    Grafo amigos = new Grafo();

    // se lee el archivo hasta que no encuentre mas datos
    while (input.hasNextLine()) {

      // obtiene la linea en la que esta
      String line = input.nextLine();
      agregarAristasGrafo(amigos, crearPersona(line));
    }
    return amigos;
  }

  private static void agregarAristasGrafo(Grafo grafo, Persona amigos) {
    if (grafo != null && amigos != null) {
      for (Persona persona : amigos) {
        grafo.addArista(amigos.getNombre(), persona.getNombre());
      }
    }
  }
}
