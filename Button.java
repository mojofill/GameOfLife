package development;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Button extends JButton{
    private Board board;

    public boolean lighted = false; // set true when button is colored
    
    public Button(int x, int y, Board _board) {
        Coordinate coordinate = new Coordinate(x, y);
        board = _board;

        Button button = this;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_board.simulation_started) {
                    return;
                }

                if (lighted) {
                    setBackground(Color.BLACK);

                    lighted = false;

                    _board.killCell(coordinate, button);
                }
                else {
                    setBackground(Color.RED);

                    lighted = true;
                    _board.createCell(coordinate, button);
                }
            }
        });
    }
}
