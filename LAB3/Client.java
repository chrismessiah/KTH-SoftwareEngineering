import java.net.*;
import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter ut;

	// public static void main(String[] args) {
	// 	Client obj = new Client();
	// 	obj.run();
	// }

	public Client() {
		initConn();
	}

	private void run() {
		initConn();
		talkToServer("Christian");
		talkToServer("Wharrup?");
		disconnectServer();
	}

	public void disconnectServer() {
		talkToServer("");
	}

	public void talkToServer(String smallTalk) {
		try {
			ut.println(smallTalk);
			ut.flush();
			String response = in.readLine();
			System.out.println(response);
		} catch (UnknownHostException e){
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void initConn() {
		try {
			//Socket socket=new Socket("gru-ld03.csc.kth.se",4713);
			socket = new Socket("localhost",1717);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ut = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e){
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}