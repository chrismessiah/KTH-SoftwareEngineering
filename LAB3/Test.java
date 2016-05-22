import java.net.*;
import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {
		Test obj = new Test();
		obj.run();
	}

	public void run() {
		try {
			//Socket socket=new Socket("gru-ld03.csc.kth.se",4713);
			Socket socket=new Socket("localhost",1717);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter ut=new PrintWriter(socket.getOutputStream());
			ut.println("Charlotta");
			ut.flush();
			System.out.println(in.readLine());
		} catch (UnknownHostException e){
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public Test() {

	}

}