package com.example.config;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import car.GatewayREST;
import com.example.rs.JaxRsApiApplication;

@Configuration
public class AppConfig {	
	@Bean( destroyMethod = "shutdown" )
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean @DependsOn( "cxf" )
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		
		List<Object> serviceBeans = new ArrayList<Object>();
		try {
			serviceBeans.add(new GatewayREST());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		factory.setServiceBeans(serviceBeans);
		factory.setAddress( "/" + factory.getAddress() );
		factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
		return factory.create();
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
		
	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}
}
