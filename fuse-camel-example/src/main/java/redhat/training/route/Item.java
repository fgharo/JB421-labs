package redhat.training.route;

import java.io.Serializable;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String author;
	String category;
	String description;
	String imagePath;
	String newItem;
	float price;
	String sku;
	String title;
}
