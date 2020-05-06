package redhat.training.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord(length=25)
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@DataField(pos = 1,length=6)
	private Integer id;
	
	@DataField(pos = 2, length=8, pattern="MM-dd-YY")
	private Date orderDate;
	
	@DataField(pos = 3, length=5, pattern="##.##")
	private BigDecimal discount;
	
	@DataField(pos = 5, length=6)
	private Integer customerId;
}
