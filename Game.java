package development;

import java.util.*;

public class Game extends Thread{
    public Board board;

    public Game(Board _board) {
        board = _board;
    }

    public ArrayList<int[]> getNeighbors(Board board, int[] coordinate) {
        ArrayList<int[]> neighbors = new ArrayList<int[]>();
        
        int x = coordinate[0];
        int y = coordinate[1];
        
        int start = x - 1;
        int end = x + 1;

        if (start <= 0) {
            start = 0;
        }

        for (int _x = start; _x <= end; _x++) { // top and bottom 3
            int _y = y + 1;
            int[] _coord = {_x, _y};

            neighbors.add(_coord);

            _y = y - 1;
            _coord = new int[] {_x, _y};

            neighbors.add(_coord);
        }

        neighbors.add(new int[] {x - 1, y});
        neighbors.add(new int[] {x + 1, y});

        return neighbors;
    }

    public boolean deadCellState(Board board, int[] coordinate) {
        // get all alive neighbors surrounding coordinate

        ArrayList<int[]> neighbors = getNeighbors(board, coordinate);

        int liveCells = 0;

        for (int[] neighbor: neighbors) {
            try {
                Button button = board.alive.get(neighbor);

                liveCells += 1;
            }

            catch (NullPointerException e) {} // dead cell
        }

        return liveCells == 3;
    }

    public boolean liveCellState(Board board, int[] coordinate) {
        // ONLY checks if cell is alive

        // get all alive neighbors surrounding coordinate

        int alive_neighbors = 0;
        
        ArrayList<int[]> neighbors = getNeighbors(board, coordinate);

        for (int[] neighbor: neighbors) {
            // neighbor is a coordinate, x and y

            try {
                Button button = board.alive.get(neighbor);

                alive_neighbors += 1;
            }

            catch (NullPointerException e) {}
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

            ArrayList<int[]> cells_to_kill = new ArrayList<int[]>(); // kill these cells
            ArrayList<int[]> dead_cells_to_revive = new ArrayList<int[]>(); // these cells have exactly 3 neighbors, bring them back to life

            for (int[] coordinate: board.alive.keySet()){
                boolean state = liveCellState(board, coordinate);

                // state == false => dead, does not move on
                // state == true => alive, moves on

                if (state) { // cell stays alive


                    // go through each neighbor, figure out which dead one can be revived

                    ArrayList<int[]> neighbors = getNeighbors(board, coordinate);

                    for (int[] neighbor: neighbors) {
                        try {
                            Button button = board.alive.get(neighbor);

                            // code reaches here, neighbor is alive, dont care about live cells right now
                        }

                        catch (NullPointerException e) {
                            // code reaches here, neighbor is dead which is what matters

                            boolean revive_dead_cell = deadCellState(board, coordinate);

                            if (revive_dead_cell) {
                                cells_to_kill.add(coordinate);
                            }
                        }
                    }
                }

                else {
                    cells_to_kill.add(coordinate); // kill cell
                }
            }

            for (int[] coord: cells_to_kill) {
                // kill these cells
                Button button = board.alive.get(coord);

                board.killCell(coord, button);

                System.out.println("Killed cell at " + coord.toString());
            }

            for (int[] coord: dead_cells_to_revive) {
                Button button = board.dead.get(coord);

                board.createCell(coord, button);

                System.out.println("Created cell at " + coord);
            }

            System.out.println("Iteration Ended");
        }
    }
}
