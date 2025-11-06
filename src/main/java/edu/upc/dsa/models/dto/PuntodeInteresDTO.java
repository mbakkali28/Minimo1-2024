package edu.upc.dsa.models.dto;

import edu.upc.dsa.models.Coordenadas;
import edu.upc.dsa.models.ElementType;

// DTO para PuntodeInteres. Solo datos, sin listas de Users (sin ciclos)
public class PuntodeInteresDTO {

    private ElementType tipo;
    private Coordenadas coordenadas;
    // No ponemos la lista de visitantes para evitar el ciclo

    // Constructor vacío (para Jackson)
    public PuntodeInteresDTO() {
    }

    // Constructor para conversión
    public PuntodeInteresDTO(ElementType tipo, Coordenadas coordenadas) {
        this.tipo = tipo;
        this.coordenadas = coordenadas;
    }

    // --- Getters y Setters para todo ---

    public ElementType getTipo() { return tipo; }
    public void setTipo(ElementType tipo) { this.tipo = tipo; }
    public Coordenadas getCoordenadas() { return coordenadas; }
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
}