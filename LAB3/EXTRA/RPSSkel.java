import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

class RPSSkel extends JFrame implements ActionListener {

	Gameboard myboard, computersboard;
    int counter = 0; // To count ONE ... TWO  and on THREE you play
    // Socket socket;
    // BufferedReader in;
    // PrintWriter out;
    JButton closebutton;
    JLabel countLabel;
    Client client = new Client();
    MyButton btn;
    boolean useSound = true;
    String[] soundList = new String[] {"sound/win.wav","sound/lose.wav","sound/tie.wav"};
    Clip[] cliplist = new Clip[soundList.length];

    public static void main (String[] u) {new RPSSkel();}
    AudioInputStream audioInputStream;

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
		btn = new MyButton(Color.green, Color.red, "Sound ON", "Sound OFF");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {useSound = !useSound;}
		});

		JPanel boards = new JPanel();
		boards.setLayout(new GridLayout(1,4));
		boards.add(countLabel);
		boards.add(myboard);
		boards.add(computersboard);
		boards.add(btn);
		add(boards, BorderLayout.CENTER);
		add(closebutton, BorderLayout.SOUTH);
		add(countLabel, BorderLayout.NORTH);
		setSize(300, 550);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void playSound(String sound) {
		try {
    		audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
    		Clip clip = AudioSystem.getClip();
    		clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			System.out.println("SOUNDERROR 1");
		} catch (IOException e) {
			System.out.println("SOUNDERROR 2");
		} catch (LineUnavailableException e) {
			System.out.println("SOUNDERROR 3");
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (useSound) {
			// code for sound
		}
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
			String sound = checkWin(action, response);
			if (useSound) {
				playSound(sound);	
			}
		}
	}

	public String checkWin(String user, String computer) {
		if (user.equals(computer)) {
			countLabel.setText("INGEN VANN");
			return soundList[2];
		}
		else if ( (user.equals("STEN") && computer.equals("SAX")) || (user.equals("PASE") && computer.equals("STEN")) || (user.equals("SAX") && computer.equals("PASE")) ) {
			myboard.wins();
			countLabel.setText("DU VANN");
			return soundList[0];
		}
		else if ( (user.equals("SAX") && computer.equals("STEN")) || (user.equals("STEN") && computer.equals("PASE")) || (user.equals("PASE") && computer.equals("SAX")) ) {
			computersboard.wins();
			countLabel.setText("DU FÖRLORADE");
			return soundList[1];
		} else {
			System.out.println("ERROR");
		}
		return "ERROR";
	}



}
