package development;

import java.util.ArrayList;

public class Test {
    public static ArrayList<Coordinate> getNeighbors(Coordinate coordinate) {
        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
        
        int start = coordinate.x - 1;
        int end = coordinate.x + 1;

        for (int x = start; x <= end; x++) { // top and bottom 3
            neighbors.add(new Coordinate(x, coordinate.y + 1));
            neighbors.add(new Coordinate(x, coordinate.y - 1));
        }

        neighbors.add(new Coordinate(coordinate.x - 1, coordinate.y));
        neighbors.add(new Coordinate(coordinate.x + 1, coordinate.y));

        return neighbors;
    }

    public static void main(String[] args) {
        ArrayList<Coordinate> neighbors = getNeighbors(new Coordinate(5, 5));
        
        for (Coordinate i: neighbors) {
            System.out.println(i.x + ", " + i.y);
        }
    }
}
