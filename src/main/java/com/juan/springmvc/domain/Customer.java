package com.juan.springmvc.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Oliver Gierke
 */
@Entity
public class Customer {

	private Long id;
    private int version;

	private String firstName;
	private String lastName;

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

    @Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

    @Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
