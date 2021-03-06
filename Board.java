package development;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

public class Board extends JPanel{
    public HashMap<Coordinate, Button> alive = new HashMap<>();
    public HashMap<Coordinate, Button> dead = new HashMap<>();

    public boolean simulation_started = false;

    public Board() {
        this.setBackground(Color.BLACK);
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

        // ORIGIN POINT - (0, 0)

        for (int y = 0; y < 600 / 25; y++) {
            for (int x = 0; x < 900 / 25; x++) {
                Button BlackButton = new Button(x, y, this);
                
                BlackButton.setPreferredSize(new Dimension(23, 23));
                
                BlackButton.setBackground(Color.BLACK);

                dead.put(new Coordinate(x, y), BlackButton);

                add(BlackButton);
            };
        }
    }

    public void createCell(Coordinate Coordinate, Button button) {    
        if (button == null) {
            System.out.println("Button at coordinate " + Coordinate.x + "," + Coordinate.y + " is null");
        }
        
        alive.put(Coordinate, button);
        dead.remove(Coordinate);

        button.setBackground(Color.RED);
    }

    public void killCell(Coordinate Coordinate, Button button) {
        alive.remove(Coordinate);
        dead.put(Coordinate, button);

        button.setBackground(Color.BLACK);
    }

    public void StartLoop(Game GAME) {
        if (this.simulation_started) return;
        
        this.simulation_started = true;
        
        GAME.start();
    }
}
