package redhat.training.route;

import java.io.Serializable;

public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;
	float extPrice;
	Item item;
}
