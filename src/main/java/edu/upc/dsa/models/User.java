package edu.upc.dsa.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class User {

    private String id;
    private  String name;
    private  String lastname;
    private  String email;
    private  String date;

    private List<PuntodeInteres> historialPasos;

    public User(){
        this.historialPasos = new ArrayList<>();
    }
    public User(String id,String name, String lastname, String email, String date) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.date = date;

        this.historialPasos = new ArrayList<>();
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PuntodeInteres> getHistorialPasos() {
        return historialPasos;
    }

    public void addPaso(PuntodeInteres p){
        historialPasos.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass() || this.id == null) {
            return false;
        }

        User that = (User) o;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
