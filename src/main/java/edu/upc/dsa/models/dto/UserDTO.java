package edu.upc.dsa.models.dto;

// DTO para User. Solo datos, sin listas de POIs (sin ciclos)
public class UserDTO {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private String date; // Añadido según el enunciado
    // No ponemos el historial de pasos para evitar el ciclo

    // Constructor vacío (para Jackson)
    public UserDTO() {
    }

    // Constructor para conversión
    public UserDTO(String id, String name, String lastname, String email, String date) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.date = date;
    }

    // --- Getters y Setters para todo ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}