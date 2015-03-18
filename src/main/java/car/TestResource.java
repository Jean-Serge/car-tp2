package car;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.net.ftp.*;

@Path("/test")
public class TestResource {
	private FTPClient client;
	
	
	public TestResource() throws SocketException, UnknownHostException, IOException{
		client = new FTPClient();
		client.configure(new FTPClientConfig());
		client.connect(InetAddress.getLocalHost().getHostName(), 2000);
//		System.out.println("Réponse connexion : " + client.getReplyString());
		
		client.login("user", "mdp");
//		System.out.println("Réponse authentification : " + client.getReplyCode());

	}
	
	
	@GET
	@Produces("text/html")
	public String sayHello() {
		return "<h1>Test</h1>";
		
	}

	
//	@Produces("application/octet-stream")
	
	
	/**
	 * Cette fonction permet la passerelle avec la fonction LIST du serveur FTP.
	 *
	 * @return le code HTML de la page de réponse
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@GET
	@Path("/list")
	public String listerFichiers() throws UnknownHostException, IOException{
		// Ouverture du socket d'écoute 
		ServerSocket serv = new ServerSocket(60000);
		client.port(InetAddress.getLocalHost(), 60000);
		Socket socket = serv.accept();
		InputStreamReader is = new InputStreamReader(socket.getInputStream());
		
		// Réception de la liste
		client.list();
		
		char c;
		String liste = "";
		
		// Lecture de la liste
		while((c = (char)is.read()) != -1){
			// On utilise un retour à la ligne HTML
			if(c == '\n')
				liste += "</br>";
			else
				liste += c;
		}
		
		serv.close(); // Fermeture du serveur d'écoute inutile
		
		// Envoi de la liste au client
		return liste;
	}
}