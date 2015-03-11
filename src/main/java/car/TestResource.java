package car;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.net.ftp.*;

@Path("/test")
public class TestResource {

	@GET
	@Produces("text/html")
	public String sayHello() {
		return "<h1>Test</h1>";
		
	}

	@GET
//	@Produces("application/octet-stream")
	@Path("/list")
	public String testLectureOctet() throws UnknownHostException, IOException{
		FTPClient client = new FTPClient();
		client.configure(new FTPClientConfig());
		client.connect(InetAddress.getLocalHost().getHostName(), 2000);
		System.out.println(client.getReplyString());
		client.login("user", "mdp");
		System.out.println(client.getReplyCode());
		
//		ServerSocket serv = new ServerSocket((171 * 256) + 26);
//		bw.write("PORT 127,0,0,1,171,26\r\n".getBytes());
//		System.out.println("Attente de connexion.");
//		serv.accept();
//		
//		System.out.println("Connexion trouvée.");
//		System.out.println(br.readLine());
//		bw.write("LIST\r\n".getBytes());
//		
//		String ligne;
//		System.out.println("LS : ");
//		while(!(ligne = br.readLine()) .split(" ")[0].equals("226")){
//			System.out.println(br.readLine());	
//		}
//		
//		serv.close();
		
		client.disconnect();
		return "Bonjour";
	}
}