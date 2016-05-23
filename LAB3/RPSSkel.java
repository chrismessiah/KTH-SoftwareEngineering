import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

class RPSSkel extends JFrame implements ActionListener {
	Gameboard myboard, computersboard;
    int counter = 0; // To count ONE ... TWO  and on THREE you play
    // Socket socket;
    // BufferedReader in;
    // PrintWriter out;
    JButton closebutton;
    JLabel countLabel;
    Client client = new Client();

    public static void main (String[] u) {new RPSSkel();}

    RPSSkel() {
    	setTitle("Lab3");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	closebutton = new JButton("Close");
    	closebutton.addActionListener(new ActionListener() { 
    		public void actionPerformed(ActionEvent e) { 
		    	System.exit(0);
			}
		});
    	countLabel = new JLabel(" ", JLabel.CENTER);

		myboard = new Gameboard("Myself", this); // Must be changed later!
		computersboard = new Gameboard("Computer");


		JPanel boards = new JPanel();
		boards.setLayout(new GridLayout(1,3));
		boards.add(countLabel);
		boards.add(myboard);
		boards.add(computersboard);
		add(boards, BorderLayout.CENTER);
		add(closebutton, BorderLayout.SOUTH);
		add(countLabel, BorderLayout.NORTH);
		setSize(300, 550);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		counter += 1;
		if (counter != 3) {
			if (counter == 1) {
				countLabel.setText("ETT");
				myboard.resetColor();
				computersboard.resetColor();
			}
			if (counter == 2) {countLabel.setText("TVÅ");}
		} else {
			counter = 0;
			countLabel.setText(" ");
			String action = e.getActionCommand();
			String response = client.talkToServer(action);
			myboard.markPlayed(action);
			computersboard.markPlayed(response);
			checkWin(action, response);
		}
	}

	public void checkWin(String user, String computer) {
		if (user.equals(computer)) {
			countLabel.setText("INGEN VANN");
		}
		else if ( (user.equals("STEN") && computer.equals("SAX")) || (user.equals("PASE") && computer.equals("STEN")) || (user.equals("SAX") && computer.equals("PASE")) ) {
			myboard.wins();
			countLabel.setText("DU VANN");
		}
		else if ( (user.equals("SAX") && computer.equals("STEN")) || (user.equals("STEN") && computer.equals("PASE")) || (user.equals("PASE") && computer.equals("SAX")) ) {
			computersboard.wins();
			countLabel.setText("DU FÖRLORADE");
		} else {
			System.out.println("ERROR");
		}
	}



}
