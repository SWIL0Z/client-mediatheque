package appliClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGénéral {

	private static int PORT = 2700;
	private static final String HOST = "localhost";

	private static BufferedReader clientReader;

	private static Socket socket;
	private static BufferedReader socketIn;
	private static PrintWriter socketOut;

	public static void main(String[] args) {

		if (args.length > 0)
			PORT = Integer.parseInt(args[0]);

		clientReader = new BufferedReader(new InputStreamReader(System.in));

		try {
			socket = new Socket(HOST, PORT);
		} catch (Exception e) {
			System.err.println("La connexion n'a pas pu être établie :");
			System.err.println(e.getMessage());
			return;
		}

		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException ioe1) {
			System.err.println("La connexion n'a pas pu être établie :");
			System.err.println(ioe1.getMessage());
			try {
				socket.close();
			} catch (IOException ioe2) {}
			return;
		}

		String servMsg, réponseClient;
		String[] splittedMsg;
		while(true) {
			try {
				servMsg = socketIn.readLine();
				splittedMsg = readingProtocol(servMsg);

				if(splittedMsg[0].equals("endsservice")) {
					System.out.println(splittedMsg[1]);
					break;
				}
				if (splittedMsg[0].equals("needsanswer")){
					System.out.print(splittedMsg[1]);
					réponseClient = clientReader.readLine();
					socketOut.println(réponseClient);
				}
				if (splittedMsg[0].equals("error")){
					System.err.println(splittedMsg[1]);
					break;
				}
			} catch (IOException ioe) {
				System.err.println("La transmission a été interrompue :");
				System.err.println(ioe.getMessage());
				break;
			} catch (WrongProtocol wp) {
				System.err.println("La transmission a été interrompue :");
				System.err.println(wp.getMessage());
				break;
			}
		}

	}

	private static String[] readingProtocol(String st) throws WrongProtocol {
		String[] elts = st.split("\\$");
		if (elts.length != 2)
			throw new WrongProtocol("Les informations transmises sont corrompues.");
		else
			return elts;
	}

	@Override
	public void finalize() {
		try {
			clientReader.close();
		} catch (IOException ioe) {}
		try {
			socketIn.close();
		} catch (IOException ioe) {}
		socketOut.close();
		try {
			socket.close();
		} catch (IOException ioe) {}
	}

}
