package car;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.*; 
@Path("/test")
public class GatewayREST {
	private FTPClient client;

	/**
	 * Initialise la connexion au Serveur FTP.
	 * 
	 * @throws SocketException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public GatewayREST() throws SocketException, UnknownHostException,
			IOException {
		client = new FTPClient();
		client.configure(new FTPClientConfig());
		client.connect(InetAddress.getLocalHost().getHostName(), 2000);
		// System.out.println("Réponse connexion : " + client.getReplyString());
	}

	/**
	 * Cette fonction affiche un formulaire de connexion sur la page d'accueil de 
	 * l'application.
	 * Les données du formulaires sont transmise à une page de login (voir fonction login)
	 * 
	 * @return le code html du formulaire à remplir
	 */
	@GET
	@Produces("text/html")
	public String getHome() {
		String html = "";
		html += "<html><body>" +
				"<h1>Bonjour</h1>" +
				"<form action=login method=\"POST\">" +
				"User : <input name=\"user\" type=\"text\"/></br>" +
				"Pass : <input name=\"pass\" type=\"password\"/></br>" +
				"<input type=\"submit\" type=\"send\" />" +
				"</form>" +
				"</body></html>";
				
		return html;
		
	}
	
	
	/**
	 * Affiche le formulaire permettantd e transmettre par le méthode DELETE
	 * le nom du fichier à supprimer sur le serveur FTP.
	 * 
	 * @return le code html du formulaire
	 */
	@GET
	@Path("/del")
	@Produces("text/html")
	public String formDel(){
		String html = "";
		html += "<html><body>" +
				"<h1>Suppresion de fichier</h1>" +
				"<form action=delete method=\"DELETE\">" +
				"Fichier à supprimer : <input name=\"file\" type=\"text\"/></br>" +
				"<input type=\"submit\" value=\"Delete\"/> " +
				"</form>" +
				"</html></body";
		
		return html;
	}

	/**
	 * Méthode permettant de supprimer un fichier sur le serveur
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@DELETE
	@Path("/delete/{file}")
	@Produces("text/html")
	public String delete(@PathParam("file") String file) throws IOException{
		System.out.println(file + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		client.rmd(file);
		
		return client.getReplyString();
	}
	
	
	/**
	 * Envoi les informations de connexions au Serveur et affiche dans la page HTML le code correspondant.
	 * 
	 * @param user le nom d'utilisateur passé par le formulaire
	 * @param pass le mot de passe
	 * @return la valeur de retour du serveur
	 * @throws IOException
	 */
	@POST
	@Path("/login")
	@Produces("text/html")
	public String login(@FormParam("user") String user, @FormParam("pass") String pass) throws IOException{
		client.login(user, pass);
		
		return client.getReplyString();
	}
	
	/**
	 * Cette fonction offre au client la possibilité de télécharger un fichier du serveur.
	 * 
	 * @param file 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces("application/octet-stream")
	@Path("/get/{file}")
	public Response getFile(@PathParam("file") String file) throws IOException {
		// Ouverture du socket d'écoute
		
		ServerSocket serv = new ServerSocket(60000);
		client.port(InetAddress.getLocalHost(), 60000);
		Socket socket = serv.accept();
		
		
		int reply = client.retr(file);
		System.out.println("1 " + client.getReplyCode());
		if(reply != 125){
			serv.close();
			return Response.ok("Pas de chance, le fichier demandé n'est pas disponible.").build();
		}
		
		client.getReply();
		System.out.println("2 " + client.getReplyCode());
		
		Response r = Response.ok(socket.getInputStream()).build();
		serv.close();
		
			return r;
	}

	/**
	 * Cette fonction permet la passerelle avec la fonction LIST du serveur FTP.
	 * 
	 * @return le code HTML de la page de réponse
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@GET
	@Path("/list")
	public String listerFichiers() throws UnknownHostException, IOException {
		// Ouverture du socket d'écoute
		ServerSocket serv = new ServerSocket(60000);
		client.port(InetAddress.getLocalHost(), 60000);
		Socket socket = serv.accept();
		InputStreamReader is = new InputStreamReader(socket.getInputStream());

		// Réception de la liste
		client.list();

		int c;
		String liste = "";

		// Lecture de la liste
		while ((c = is.read()) != -1) {
			// On utilise un retour à la ligne HTML
			if ((char)c == '\n')
				liste += "</br>";
			else
				liste += (char)c;
		}

		serv.close(); // Fermeture du serveur d'écoute inutile

		// Envoi de la liste au client
		return liste;
	}
}