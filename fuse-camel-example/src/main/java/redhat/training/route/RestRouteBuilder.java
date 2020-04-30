package redhat.training.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

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
		
		
		// CBR Route builder
		final var GET_VENDORNAME_HEADER = header("vendorName");
		final var GET_SKIPORDER_HEADER = header("skipOrder");
		
		final var GET_ORDERITEMPUBLISHERNAME_XPATH = xpath("/order/orderItems/orderItem/orderItemPublisherName/text()");
		final var GET_ORDERID_XPATH = xpath("/order/orderId/text()");
		
		from("file:orders/incoming?include=order.*xml")
		.process((Exchange exchange)->{
			NodeList test = XPathBuilder.xpath("/order/test").evaluate(exchange, NodeList.class);
			if(test.getLength() > 0) {
				log.info("Adding skipOrder header");
				exchange.getIn().setHeader("skipOrder", "Y");
			}
		})
		.filter(GET_SKIPORDER_HEADER.isNull())
		.setHeader("orderId", GET_ORDERID_XPATH)
		.setHeader("vendorName", GET_ORDERITEMPUBLISHERNAME_XPATH)
		.choice()
			.when(GET_VENDORNAME_HEADER.isEqualTo("ABC Company"))
				.log("sending order ${header.orderId} to folder abc.")
				.to("file:orders/outgoing/abc")
			.when(GET_VENDORNAME_HEADER.isEqualTo("ORly"))
				.log("sending order ${header.orderId} to folder orly.")
				.to("file:orders/outgoing/orly")
			.when(GET_VENDORNAME_HEADER.isEqualTo("Namming"))
				.log("sending order ${header.orderId} to folder namming.")
				.to("file:orders/outgoing/namming");
	}



}
