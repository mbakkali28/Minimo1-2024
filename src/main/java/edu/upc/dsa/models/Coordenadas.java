package edu.upc.dsa.models;
import java.util.Objects;

public class Coordenadas {

    private  float x;
    private  float y;

    public Coordenadas() {

    }
    public Coordenadas(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {return x;}
    public float getY() {return y;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordenadas that = (Coordenadas) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
