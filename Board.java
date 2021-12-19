package development;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Board extends JPanel{
    public HashMap<Coordinate, Button> data = new HashMap<>();

    public HashMap<Coordinate, Button> alive = new HashMap<>();
    public HashMap<Coordinate, Button> dead = new HashMap<>();

    public boolean simulation_started = false;

    public Board() {
        this.setBackground(Color.BLACK);
        
        this.setLayout(new GridLayout(10, 10));

        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 12; x++) {
                Button BlackButton = new Button(x, y, this);
                
                BlackButton.setPreferredSize(new Dimension(50, 50));
                
                BlackButton.setBackground(Color.BLACK);

                dead.put(new Coordinate(x, y), BlackButton);

                add(BlackButton);
            };
        }
    }

    public void createCell(Coordinate Coordinate, Button button) {    
        alive.put(Coordinate, button);
        dead.remove(Coordinate);

        button.setBackground(Color.RED);
    }

    public void killCell(Coordinate Coordinate, Button button) {
        alive.remove(Coordinate);
        dead.put(Coordinate, button);

        button.setBackground(Color.BLACK);
    }

    public Button getButtonatCoordinate(int x, int y) {
        Coordinate coord = new Coordinate(x, y);
        
        return data.get(coord);
    }

    public void StartLoop(Game GAME) {
        GAME.start();
    }
}
