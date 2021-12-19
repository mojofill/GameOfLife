package development;

import java.util.*;

public class Game extends Thread{
    public Board board;

    public Game(Board _board) {
        board = _board;
    }

    public ArrayList<Coordinate> getNeighbors(Coordinate coordinate) {
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

    public boolean deadCellState(Board board, Coordinate coordinate) {
        // get all alive neighbors surrounding coordinate

        ArrayList<Coordinate> neighbors = getNeighbors(coordinate);

        int liveCells = 0;

        for (Coordinate neighbor: neighbors) {
        
            Button button = board.alive.get(neighbor);
            
            if (button != null) {
                liveCells += 1;
            }
        }

        return liveCells == 3;
    }

    public boolean liveCellState(Board board, Coordinate coordinate) {
        // ONLY checks if cell is alive

        // get all alive neighbors surrounding coordinate

        int alive_neighbors = 0;
        
        ArrayList<Coordinate> neighbors = getNeighbors(coordinate);

        for (Coordinate neighbor: neighbors) {
            // neighbor is a coordinate, x and y

            Button button = board.alive.get(neighbor);

            if (button != null) {
                alive_neighbors += 1;
            }
        }

        if (alive_neighbors < 2) {
            return false;
        }

        else if (alive_neighbors == 2 || alive_neighbors == 3) {
            return true;
        }

        else {
            return false;
        }
    }

    @Override
    public void run(){
        while (true) {
            try {Thread.sleep(800);}
            catch (Exception e) {}

            ArrayList<Coordinate> cells_to_kill = new ArrayList<Coordinate>(); // kill these cells
            ArrayList<Coordinate> dead_cells_to_revive = new ArrayList<Coordinate>(); // these cells have exactly 3 neighbors, bring them back to life

            // iterate through each living cell
            for (Coordinate coordinate: board.alive.keySet()){
                boolean state = liveCellState(board, coordinate);

                // state == false => dead, does not move on
                // state == true => alive, moves on

                if (state) { // cell stays alive
                    // go through each neighbor, figure out which dead one can be revived

                    ArrayList<Coordinate> neighbors = getNeighbors(coordinate);
                    
                    // neighbors: ArrayList of neighbors, shown in Coordinate objects
                    for (Coordinate neighbor: neighbors) {
                        
                        Button button = board.dead.get(neighbor);
                    
                        if (button != null) {
                            // code reaches here, neighbor is dead which is what matters
                            boolean revive_dead_cell = deadCellState(board, neighbor);

                            if (revive_dead_cell) {
                                System.out.println("reviving " + neighbor.x + ", " + neighbor.y + "...");
                                dead_cells_to_revive.add(neighbor);
                            }
                        }
                    }
                }

                else { // state = false => kill cell
                    System.out.println("Killing cell at " + coordinate.x + ", " + coordinate.y);
                    cells_to_kill.add(coordinate); // kill cell
                }
            }

            for (Coordinate coord: cells_to_kill) {
                // kill these cells
                Button button = board.alive.get(coord);

                board.killCell(coord, button);

                System.out.println("Killed cell at " + coord.x + ", " + coord.y);
            }

            for (Coordinate coord: dead_cells_to_revive) {
                Button button = board.dead.get(coord);

                System.out.println("Trying to revive dead cell " + coord.x + ", " + coord.y);

                if (button == null) {
                    System.out.println("button for " + coord.x + ", " + coord.y + " is null.");
                }

                board.createCell(coord, button);
            }
        }
    }
}
