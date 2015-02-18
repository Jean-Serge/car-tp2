package car;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
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
	@Produces("application/octet-stream")
	@Path("/list")
	public String testLectureOctet() throws UnknownHostException, IOException{
		Socket s = new Socket(InetAddress.getLocalHost(), 2000);
		DataOutputStream bw = new DataOutputStream(s.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		System.out.println(br.readLine());	
		bw.write("USER user\r\n".getBytes());
		System.out.println(br.readLine());
		
		bw.write("PASS mdp\r\n".getBytes());
		System.out.println(br.readLine());
		
		bw.write("PORT 127,0,0,1,171,26\r\n".getBytes());
		System.out.println(br.readLine());
//		bw.write("LIST\r\n".getBytes());
//		System.out.println(br.readLine());
//		System.out.println(br.readLine());

		return "Bonjour";
	}
}