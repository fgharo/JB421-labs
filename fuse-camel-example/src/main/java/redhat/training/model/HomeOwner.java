package redhat.training.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

@CsvRecord(separator="`")
public class HomeOwner implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@DataField(pos=1)
	String firstName;
	
	@DataField(pos=2)
	String lastName;
	
	@Link
	Address homeAddress;
	
	@DataField(pos=7, pattern="MM-dd-yyyy")
	Date movedIn;
}
