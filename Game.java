package development;

import java.util.*;

public class Game extends Thread{
    public Board board;

    public Game(Board _board) {
        board = _board;
    }

    public ArrayList<Coordinate> getNeighbors(Board board, Coordinate coordinate) {
        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
        
        int x = coordinate.x;
        int y = coordinate.y;
        
        int start = x - 1;
        int end = x + 1;

        if (start <= 0) {
            start = 0;
        }

        for (int _x = start; _x <= end; _x++) { // top and bottom 3
            int _y = y + 1;
            Coordinate _coord = new Coordinate(_x, _y);

            neighbors.add(_coord);

            _y = y - 1;
            _coord = new Coordinate(_x, _y);

            neighbors.add(_coord);
        }

        int _x = x - 1;
        
        if (_x < 0) {
            _x = 0;
        }

        neighbors.add(new Coordinate(_x, y));
        neighbors.add(new Coordinate(x + 1, y));

        return neighbors;
    }

    public boolean deadCellState(Board board, Coordinate coordinate) {
        // get all alive neighbors surrounding coordinate

        ArrayList<Coordinate> neighbors = getNeighbors(board, coordinate);

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
        
        ArrayList<Coordinate> neighbors = getNeighbors(board, coordinate);

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

            for (Coordinate coordinate: board.alive.keySet()){
                boolean state = liveCellState(board, coordinate);

                // state == false => dead, does not move on
                // state == true => alive, moves on

                if (state) { // cell stays alive
                    // go through each neighbor, figure out which dead one can be revived

                    ArrayList<Coordinate> neighbors = getNeighbors(board, coordinate);

                    for (Coordinate neighbor: neighbors) {
                        
                        Button button = board.dead.get(neighbor);
                    
                        if (button != null) {
                            // code reaches here, neighbor is dead which is what matters
                            boolean revive_dead_cell = deadCellState(board, neighbor);

                            if (revive_dead_cell) {
                                dead_cells_to_revive.add(neighbor);
                            }
                        }
                    }
                }

                else {
                    cells_to_kill.add(coordinate); // kill cell
                }
            }

            for (Coordinate coord: cells_to_kill) {
                // kill these cells
                Button button = board.alive.get(coord);

                board.killCell(coord, button);
            }

            for (Coordinate coord: dead_cells_to_revive) {
                Button button = board.dead.get(coord);

                board.createCell(coord, button);
            }
        }
    }
}
