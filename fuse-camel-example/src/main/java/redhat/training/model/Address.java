package redhat.training.model;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Link
public class Address {
	@DataField(pos=3)
	String line1;
	
	@DataField(pos=4)
	String city;
	
	@DataField(pos=5)
	String state;
	
	@DataField(pos=6)
	String zipcode;
}
