import edu.upc.dsa.VirtualGame;
import edu.upc.dsa.VirtualGameImpl;
import edu.upc.dsa.models.Coordenadas;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntodeInteres;
import edu.upc.dsa.models.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class VirtualGameTest {
    private VirtualGame vg;

    private final Coordenadas POS_COIN = new Coordenadas(1, 1);
    private final Coordenadas POS_DOOR = new Coordenadas(5, 5);
    private final Coordenadas POS_WALL = new Coordenadas(10, 10);

    @Before
    public void setUp() {
        vg = VirtualGameImpl.getInstance();

        ((VirtualGameImpl) vg).clearData();

        vg.addUser("U001", "Juan", "Pérez", "juan.perez@eetac.es", "1990-01-01");
        vg.addUser("U002", "Antonio", "López", "antonio.lopez@eetac.es", "1985-05-15");
        vg.addUser("U003", "Luis", "Pérez", "luis.perez@eetac.es", "1995-12-31");

        vg.addPunto(POS_COIN, new PuntodeInteres(ElementType.COIN, POS_COIN));
        vg.addPunto(POS_DOOR, new PuntodeInteres(ElementType.DOOR, POS_DOOR));
        vg.addPunto(POS_WALL, new PuntodeInteres(ElementType.WALL, POS_WALL));
    }

    @After
    public void tearDown() {
        this.vg.clearData();
        this.vg = null;
    }

    @Test
    public void testGetUsersSorted() {
        List<User> users = vg.getUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(3, users.size());

        Assert.assertEquals("López", users.get(0).getLastname());
        Assert.assertEquals("Antonio", users.get(0).getName());

        Assert.assertEquals("Pérez", users.get(1).getLastname());
        Assert.assertEquals("Juan", users.get(1).getName());

        Assert.assertEquals("Pérez", users.get(2).getLastname());
        Assert.assertEquals("Luis", users.get(2).getName());
    }

    @Test
    public void testGetUser() {
        User u = vg.getUser("U002");
        Assert.assertNotNull(u);
        Assert.assertEquals("Antonio", u.getName());

        User u_fail = vg.getUser("U999");
        Assert.assertNull(u_fail);
    }

    @Test
    public void testGetPuntosPorTipo() {
        List<PuntodeInteres> coins = vg.getPuntosPorTipo(ElementType.COIN);
        Assert.assertEquals(1, coins.size());

        List<PuntodeInteres> walls = vg.getPuntosPorTipo(ElementType.WALL);
        Assert.assertEquals(1, walls.size());

        List<PuntodeInteres> trees = vg.getPuntosPorTipo(ElementType.TREE);
        Assert.assertEquals(0, trees.size());
    }

    @Test
    public void testRegistrarPasoSuccess() {
        Assert.assertEquals(0, vg.getHistorialUsuario("U001").size());

        vg.registrarPaso("U001", POS_COIN);

        List<PuntodeInteres> historial = vg.getHistorialUsuario("U001");
        Assert.assertEquals(1, historial.size());
        Assert.assertEquals(ElementType.COIN, historial.get(0).getTipo());
    }

    @Test
    public void testRegistrarPasoFailure() {
        vg.registrarPaso("U999", POS_COIN);

        vg.registrarPaso("U001", new Coordenadas(99, 99));

        Assert.assertEquals(0, vg.getHistorialUsuario("U001").size());
    }

    @Test
    public void testHistorialOrdenYMultiplePaso() {
        vg.registrarPaso("U001", POS_DOOR);
        vg.registrarPaso("U001", POS_COIN);
        vg.registrarPaso("U001", POS_WALL);

        List<PuntodeInteres> historial = vg.getHistorialUsuario("U001");
        Assert.assertEquals(3, historial.size());

        Assert.assertEquals(ElementType.DOOR, historial.get(0).getTipo());
        Assert.assertEquals(ElementType.COIN, historial.get(1).getTipo());
        Assert.assertEquals(ElementType.WALL, historial.get(2).getTipo());
    }

    @Test
    public void testUsuariosEnPunto() {
        vg.registrarPaso("U001", POS_COIN);
        vg.registrarPaso("U002", POS_COIN);

        vg.registrarPaso("U001", POS_WALL);

        List<User> visitantes_coin = vg.getUsuariosEnPunto(POS_COIN);
        Assert.assertEquals(2, visitantes_coin.size());

        List<User> visitantes_wall = vg.getUsuariosEnPunto(POS_WALL);
        Assert.assertEquals(1, visitantes_wall.size());

        List<User> visitantes_door = vg.getUsuariosEnPunto(POS_DOOR);
        Assert.assertEquals(0, visitantes_door.size());
    }
}