package com.mcc;

import java.util.*;

public class Persona implements Iterable<Persona> {
  private String nombre;
  private Set<Persona> amigos = new HashSet<>();

  public Persona() {}

  public Persona(String nombre, Persona... amigos) {

    this.nombre = nombre;

    for (Persona amigo : amigos) {
      this.amigos.add(amigo);
    }
  }

  @Override
  public Iterator<Persona> iterator() {
    return amigos.iterator();
  }

  public String getNombre() {
    return nombre;
  }
}
