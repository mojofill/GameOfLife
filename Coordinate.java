package development;

import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Coordinate)) {
            return false;
        }

        Coordinate cast = (Coordinate) obj;

        return cast.x == this.x && cast.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
