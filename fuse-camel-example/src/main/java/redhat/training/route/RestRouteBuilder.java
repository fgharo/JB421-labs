package redhat.training.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.springframework.core.annotation.Order;
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
		
		BindyFixedLengthDataFormat bindy = new BindyFixedLengthDataFormat(redhat.training.model.Order.class);
		
		// This example goes to show that a problem can have a solution that combines multiple EIPs
		// to get the proper result: Message Translator Pattern (csv->model, model->xml), Wire Tap pattern, Splitter pattern, Aggregator pattern 
		from("file:orders/?fileName=may-6-2020.csv")
		.split()
		.tokenize("\n")
		.streaming()
		.unmarshal(bindy)
		.aggregate(constant(true), new ArrayListAggregationStrategy())
		// Suppose the number of exchanges isn't a multiple of 25, say we have 7 exchanges left. Will the route hang until it gets 18?
		// TODO How to put the last 7 in a batch? There is no way to know?
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
