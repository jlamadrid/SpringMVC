package com.juan.springmvc.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 */
@Entity
public class Account extends AbstractEntity {

	private Customer customer;

	private Date expiryDate;

    @ManyToOne
	public Customer getCustomer() {
		return customer;
	}

    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
	public Date getExpiryDate() {
		return expiryDate;
	}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
