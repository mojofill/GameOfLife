package development;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Board extends JPanel{
    public HashMap<int[], Button> data = new HashMap<>();

    public HashMap<int[], Button> alive = new HashMap<>();
    public HashMap<int[], Button> dead = new HashMap<>();

    public Board() {
        this.setBackground(Color.BLACK);
        
        this.setLayout(new GridLayout(10, 10));

        ArrayList<Button> buttons = new ArrayList<Button>();

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                Button BlackButton = new Button(x, y, this);
                
                BlackButton.setPreferredSize(new Dimension(50, 50));
                
                BlackButton.setBackground(Color.BLACK);

                add(BlackButton);
            };
        }
    }

    public void createCell(int[] Coordinate, Button button) {    
        alive.put(Coordinate, button);
        dead.remove(Coordinate);

        button.setBackground(Color.RED);
    }

    public void killCell(int[] Coordinate, Button button) {
        alive.remove(Coordinate);
        dead.put(Coordinate, button);

        button.setBackground(Color.BLACK);
    }

    public Button getButtonatCoordinate(int x, int y) {
        int[] coord = {x, y};
        
        return data.get(coord);
    }

    public void StartLoop(Game GAME) {
        GAME.start();
    }
}
