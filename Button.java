package development;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Button extends JButton{
    public boolean lighted = false; // set true when button is colored
    
    public Button(int x, int y, Board board) {
        Coordinate coordinate = new Coordinate(x, y);

        Button button = this;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.simulation_started) {
                    return;
                }

                if (lighted) {
                    setBackground(Color.BLACK);

                    lighted = false;

                    board.killCell(coordinate, button);
                }
                else {
                    setBackground(Color.RED);

                    lighted = true;
                    board.createCell(coordinate, button);
                }
            }
        });
    }
}
