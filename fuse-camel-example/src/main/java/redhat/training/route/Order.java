package redhat.training.route;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElementWrapper(name="orderItems")
	@XmlElement(name="orderItem")
	Collection<OrderItem> orderItems;
}
