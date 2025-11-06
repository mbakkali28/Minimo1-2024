package edu.upc.dsa.models;

import java.util.List;
import java.util.ArrayList;

public class PuntodeInteres {
    private ElementType tipo;
    private List<User> visitantes;
    private Coordenadas coordenadas;

    public PuntodeInteres() {
        this.visitantes = new ArrayList<>();
    }

    public PuntodeInteres(ElementType tipo, Coordenadas cords) {
        this.tipo = tipo;
        this.coordenadas = cords;
        this.visitantes = new ArrayList<>();
    }

    public void addVisitante(User user) {
        visitantes.add(user);
    }

    public List<User> getVisitantes() {
        return visitantes;
    }

    public ElementType getTipo() {
        return tipo;
    }

    public void setTipo(ElementType tipo) {
        this.tipo = tipo;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }
}
