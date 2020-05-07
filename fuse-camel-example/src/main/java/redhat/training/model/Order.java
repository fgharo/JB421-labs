package redhat.training.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord(length=25)
@XmlRootElement
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@DataField(pos = 1,length=6)
	private Integer id;
	
	@DataField(pos = 7, length=8, pattern="MM-dd-YY")
	private Date orderDate;
	
	@DataField(pos = 15, length=5, pattern="##.##")
	private BigDecimal discount;
	
	@DataField(pos = 20, length=6)
	private Integer customerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
