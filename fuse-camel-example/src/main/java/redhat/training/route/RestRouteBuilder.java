package redhat.training.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
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
		
		BindyFixedLengthDataFormat bindy = new BindyFixedLengthDataFormat();
		
		from("file:orders/may-6-2020.csv")
		.split().tokenize("\n").streaming()
		.unmarshal(bindy)
		.aggregate(constant(true), new ArrayListAggregationStrategy())
			.completionSize(25)
			.completeAllOnStop()
		.process(new BatchXMLProcessor())
		.wireTap("direct:orderLogger")
		.to("file:orders/outgoing?fileName=output.xml&fileExist=Append", "mock:result");
		
		from("direct:orderLogger")
			.split()
			.tokenizeXML("order")
			.log("${body}");
		
	}



}
