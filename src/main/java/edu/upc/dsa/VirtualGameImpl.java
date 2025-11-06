package edu.upc.dsa;

import edu.upc.dsa.models.Coordenadas;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntodeInteres;
import edu.upc.dsa.models.User;

import java.util.*;

import org.apache.log4j.Logger;

public class VirtualGameImpl implements VirtualGame {

    private static final Logger logger = Logger.getLogger(VirtualGameImpl.class);

    private HashMap<String, User> users;
    private Map<Coordenadas, PuntodeInteres> puntos;

    private VirtualGameImpl() {
        logger.info("Inicializando edu.upc.dsa.VirtualGameImpl...");
        users = new HashMap<>();
        puntos = new HashMap<>();
        logger.info("edu.upc.dsa.VirtualGameImpl inicializado.");
    }

    private static VirtualGameImpl instance;

    public static VirtualGameImpl getInstance() {
        if (instance == null) {
            instance = new VirtualGameImpl();
        }
        return instance;
    }

    @Override
    public void addUser(String id,String name, String lastname, String email, String date) {

        logger.info("Inicio addUser: id=" + id + ", name=" + name + ", lastname=" + lastname);

        users.put(id, new User(id, name, lastname, email, date));

        logger.info("Fin addUser: Usuario " + id + " añadido.");
    }

    @Override
    public List<User> getUsers() {
        logger.info("Inicio getUsers: Listando y ordenando usuarios.");

        List<User> listaUsuarios = new ArrayList<>(users.values());

        listaUsuarios.sort(Comparator.comparing(User::getLastname).thenComparing(User::getName));

        logger.info("Fin getUsers: Devueltos " + listaUsuarios.size() + " usuarios.");
        return listaUsuarios;
    }

    @Override
    public void addPunto(Coordenadas xy, PuntodeInteres punto) {
        logger.info("Inicio addPunto: Coordenadas=" + xy + ", Tipo=" + punto.getTipo());

        puntos.put(xy, punto);

        logger.info("Fin addPunto: Punto añadido en " + xy);
    }

    public void registrarPaso(String idUser, Coordenadas pos){
        logger.info("Inicio registrarPaso: idUser=" + idUser + ", pos=" + pos);

        User user = users.get(idUser);
        PuntodeInteres punto = puntos.get(pos);

        if(user == null || punto == null){
            logger.error("Error al registrar paso: Usuario " + idUser + " o Punto " + pos + " no existen.");
            return;
        }

        user.addPaso(punto);
        punto.addVisitante(user);

        logger.info("Fin registrarPaso: Paso registrado para " + idUser + " en " + pos);
    }

    public List<PuntodeInteres> getHistorialUsuario(String idUser) {
        logger.info("Inicio getHistorialUsuario: idUser=" + idUser);

        User user = users.get(idUser);

        if (user == null) {
            logger.error("Error al obtener historial: Usuario " + idUser + " no encontrado.");
            return null;
        }

        List<PuntodeInteres> historial = user.getHistorialPasos();
        logger.info("Fin getHistorialUsuario: Devueltos " + historial.size() + " puntos para " + idUser);
        return historial;
    }

    public List<User> getUsuariosEnPunto(Coordenadas pos) {
        logger.info("Inicio getUsuariosEnPunto: Coordenadas=" + pos);

        PuntodeInteres punto = puntos.get(pos);

        if (punto == null) {
            logger.error("Error al obtener visitantes: Punto " + pos + " no encontrado.");
            return null;
        }

        List<User> visitantes = punto.getVisitantes();
        logger.info("Fin getUsuariosEnPunto: Devueltos " + visitantes.size() + " visitantes para " + pos);
        return visitantes;
    }

    public List<PuntodeInteres> getPuntosPorTipo(ElementType tipo) {
        logger.info("Inicio getPuntosPorTipo: Tipo=" + tipo);

        List<PuntodeInteres> puntosDelTipo = new ArrayList<>();

        for (PuntodeInteres punto : puntos.values()) {
            if (punto.getTipo() == tipo) {
                puntosDelTipo.add(punto);
            }
        }

        logger.info("Fin getPuntosPorTipo: Devueltos " + puntosDelTipo.size() + " puntos de tipo " + tipo);
        return puntosDelTipo;
    }

    @Override
    public User getUser(String id) {
        logger.info("Inicio getUser: id=" + id);
        User user = users.get(id);

        if (user == null) {
            logger.warn("Usuario " + id + " no encontrado.");
        } else {
            logger.info("Fin getUser: Usuario " + id + " encontrado.");
        }

        return user;
    }

    public void clearData() {
        this.users.clear();
        this.puntos.clear();
        logger.warn("Data reset.");
    }
}