package redhat.training.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

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
		
		from("file:orders/incoming?include=order.*xml")
		.log("XML Body: ${body}")
		.marshal(new XmlJsonDataFormat())
		.log("JSON Body: ${body}")
		.filter().jsonpath("$[?(@.delivered != 'true')]")
		.wireTap("direct:jsonOrderLog")
		.log("processed delivery!");
		
		from("direct:jsonOrderLog")
		.log("Order received in jsonOrderLog: ${body}");
	}



}
