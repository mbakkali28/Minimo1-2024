package edu.upc.dsa;

import edu.upc.dsa.models.PuntodeInteres;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Coordenadas;
import edu.upc.dsa.models.ElementType;

import java.util.List;

public interface VirtualGame {

    public void addUser(String id,String name, String lastname, String email, String date);

    public List<User> getUsers();

    public List<PuntodeInteres> getHistorialUsuario(String idUser);

    public List<User> getUsuariosEnPunto(Coordenadas pos);

    public List<PuntodeInteres> getPuntosPorTipo(ElementType tipo);

    public void addPunto(Coordenadas xy, PuntodeInteres punto);

    public User getUser(String id);

    public void registrarPaso(String idUser, Coordenadas pos);

    public void clearData();
}
