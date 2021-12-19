package development;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Button extends JButton{
    private Board board;

    public boolean lighted = false; // set true when button is colored
    
    public Button(int x, int y, Board _board) {
        int[] Coordinate = {x, y};
        board = _board;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lighted) {
                    setBackground(Color.BLACK);

                    System.out.println("Set " + x + ", " + y + " as dead");

                    lighted = false;
                }
                else {
                    setBackground(Color.RED);

                    System.out.println("Set " + x + ", " + y + " as alive");

                    lighted = true;
                }
            }
        });

        board.createCell(Coordinate, this);
    }
}
