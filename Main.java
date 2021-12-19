package development;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

public class Main {
	public static Game GAME;

	public static void main(String[] args){
		
		Board board = new Board();

		GAME = new Game(board);

		JButton start = new JButton("Start Loop");

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				String msg = "";
				
				board.StartLoop(GAME);
			}
		});

        JFrame window = new JFrame();
		
		window.add(start, BorderLayout.LINE_END);
		
		window.setSize(new Dimension(800, 600));
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		window.add(board, BorderLayout.CENTER);
		
		window.setVisible(true);

		System.out.println("Starting simulation...");
    }
}