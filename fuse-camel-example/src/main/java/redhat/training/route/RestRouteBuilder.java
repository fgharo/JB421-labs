package redhat.training.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

import redhat.training.model.HomeOwner;

@Component
public class RestRouteBuilder extends RouteBuilder{

	/**
	 * Can call with 
	 * $ curl -si    http://localhost:8080/camel/hello/Developer
	 * {
  		greeting: Hello, Developer
  		server: workstation.lab.example.com
		}
	 */
	@Override
	public void configure() throws Exception {
		rest("/hello").get("{name}").produces("application/json").to("direct:sayHello");
		
		from("direct:sayHello")
		.routeId("HelloREST")
		.setBody()
		.simple("{\n"
		    + "  greeting: Hello, ${header.name}\n"
		    + "  server: " + System.getenv("HOSTNAME") + "\n"
		    + "}\n");
		
		BindyCsvDataFormat bindy = new BindyCsvDataFormat(HomeOwner.class);
		
		from("file:files/?fileName=homeowners.csv")
		.transform(body().regexReplaceAll(",", "`"))
		.unmarshal(bindy)
		.to("mock:orderQueue","direct:orderLog");
		
		from("direct:orderLog")
		  .split(body())
		  .log("${body}")
		  .to("mock:orderLoggingSystem");
	}



}
