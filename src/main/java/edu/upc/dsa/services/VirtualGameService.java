package edu.upc.dsa.services;

import edu.upc.dsa.VirtualGame;
import edu.upc.dsa.VirtualGameImpl;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.PuntodeInteres;
import edu.upc.dsa.models.Coordenadas;
import edu.upc.dsa.models.ElementType;

import edu.upc.dsa.models.dto.UserDTO;
import edu.upc.dsa.models.dto.PuntodeInteresDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/game", description = "Endpoint to Virtual Game Service")
@Path("/game")
public class VirtualGameService {

    private VirtualGame vg;

    public VirtualGameService() {
        this.vg = VirtualGameImpl.getInstance();

        if (this.vg.getUsers().isEmpty()) {
            this.vg.addUser("user1", "Juan", "Perez", "juan@mail.com", "01/01/2000");
            this.vg.addUser("user2", "Ana", "García", "ana@mail.com", "02/02/2001");
        }
    }

    @GET
    @ApiOperation(value = "Listar todos los usuarios", notes = "Ordenados alfabéticamente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserDTO.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> usersInternos = this.vg.getUsers();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : usersInternos) {
            usersDTO.add(new UserDTO(u.getId(), u.getName(), u.getLastname(), u.getEmail(), u.getDate()));
        }

        GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(usersDTO) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Consultar información de un usuario", notes = "Por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {

        User u = this.vg.getUser(id);
        if (u == null) return Response.status(404).build();

        UserDTO userDTO = new UserDTO(u.getId(), u.getName(), u.getLastname(), u.getEmail(), u.getDate());

        return Response.status(200).entity(userDTO).build();
    }

    @POST
    @ApiOperation(value = "Añadir un nuevo usuario", notes = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful (Created)", response=UserDTO.class),
            @ApiResponse(code = 400, message = "Validation Error (Bad Request)")
    })
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {

        if (user.getId() == null || user.getName() == null || user.getLastname() == null) {
            return Response.status(400).build();
        }
        this.vg.addUser(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getDate());

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getDate());
        return Response.status(201).entity(userDTO).build();
    }

    @POST
    @ApiOperation(value = "Añadir un punto de interés", notes = "Crea un nuevo PI")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful (Created)", response=PuntodeInteresDTO.class),
            @ApiResponse(code = 400, message = "Validation Error (Bad Request)")
    })
    @Path("/pois")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPuntoInteres(PuntodeInteres poi) {

        if (poi.getCoordenadas() == null || poi.getTipo() == null) {
            return Response.status(400).build();
        }
        this.vg.addPunto(poi.getCoordenadas(), poi);

        PuntodeInteresDTO poiDTO = new PuntodeInteresDTO(poi.getTipo(), poi.getCoordenadas());
        return Response.status(201).entity(poiDTO).build();
    }

    @GET
    @ApiOperation(value = "Consultar PIs por tipo", notes = "Lista PIs de un tipo determinado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntodeInteresDTO.class, responseContainer="List"),
            @ApiResponse(code = 400, message = "Query param 'tipo' is required")
    })
    @Path("/pois")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosPorTipo(@QueryParam("tipo") ElementType tipo) {
        if (tipo == null) {
            return Response.status(400).entity("Query param 'tipo' es requerido").build();
        }

        List<PuntodeInteres> poisInternos = this.vg.getPuntosPorTipo(tipo);

        List<PuntodeInteresDTO> poisDTO = new ArrayList<>();
        for (PuntodeInteres poi : poisInternos) {
            poisDTO.add(new PuntodeInteresDTO(poi.getTipo(), poi.getCoordenadas()));
        }

        GenericEntity<List<PuntodeInteresDTO>> entity = new GenericEntity<List<PuntodeInteresDTO>>(poisDTO) {};
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "Registrar paso por PI", notes = "Un usuario pasa por un PI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful (Updated)"),
            @ApiResponse(code = 404, message = "User or POI not found"),
            @ApiResponse(code = 400, message = "Bad Request (e.g., missing coordinates)")
    })
    @Path("/users/{id}/visit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarPaso(@PathParam("id") String idUser, Coordenadas pos) {

        if (pos == null) return Response.status(400).build();

        try {
            this.vg.registrarPaso(idUser, pos);
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "Consultar historial de usuario", notes = "PIs por los que ha pasado un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntodeInteresDTO.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/users/{id}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorialUsuario(@PathParam("id") String id) {

        List<PuntodeInteres> historialInterno = this.vg.getHistorialUsuario(id);

        if (historialInterno == null) return Response.status(404).build();

        List<PuntodeInteresDTO> historialDTO = new ArrayList<>();
        for (PuntodeInteres poi : historialInterno) {
            historialDTO.add(new PuntodeInteresDTO(poi.getTipo(), poi.getCoordenadas()));
        }

        GenericEntity<List<PuntodeInteresDTO>> entity = new GenericEntity<List<PuntodeInteresDTO>>(historialDTO) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Listar usuarios en un PI", notes = "Usuarios que han pasado por un PI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserDTO.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "POI not found"),
            @ApiResponse(code = 400, message = "Missing or invalid coordinates")
    })
    @Path("/pois/visitors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuariosEnPunto(
            @QueryParam("x") Integer x,
            @QueryParam("y") Integer y) {

        if (x == null || y == null) {
            return Response.status(400).entity("Query params 'x' e 'y' son requeridos").build();
        }

        Coordenadas pos = new Coordenadas(x, y);

        List<User> visitantesInternos = this.vg.getUsuariosEnPunto(pos);

        if (visitantesInternos == null) return Response.status(404).build();

        List<UserDTO> visitantesDTO = new ArrayList<>();
        for (User user : visitantesInternos) {
            visitantesDTO.add(new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getDate()));
        }

        GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(visitantesDTO) {};
        return Response.status(200).entity(entity).build();
    }
}