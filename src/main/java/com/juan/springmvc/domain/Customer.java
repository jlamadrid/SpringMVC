package com.juan.springmvc.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Oliver Gierke
 */
@Entity
public class Customer extends AbstractEntity {

	private String firstName;
	private String lastName;

    @Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

    @Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
