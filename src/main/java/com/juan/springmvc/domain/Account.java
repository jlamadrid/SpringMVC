package com.juan.springmvc.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Oliver Gierke
 */
@Entity
public class Account {

	private Long id;
    private int version;

	private Customer customer;

	private Date expiryDate;

    @Id
    @GeneratedValue(strategy = IDENTITY) //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	public Long getId() {
		return id;
	}

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    @ManyToOne
	public Customer getCustomer() {
		return customer;
	}

    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
	public Date getExpiryDate() {
		return expiryDate;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
