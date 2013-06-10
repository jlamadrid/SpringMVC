package com.juan.springmvc.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private Long id;
    private int version;
    private String firstName;
    private String lastName;
    private DateTime birthDate;
    private String description;
    private byte[] photo;

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @Column(name = "BIRTH_DATE")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso=ISO.DATE)
    public DateTime getBirthDate() {
        return birthDate;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    /**
     * Should be fetched lazily in order to avoid a performance impact when loading a class that does not require
     * photo information.
     * @return
     */
    @Basic(fetch=FetchType.LAZY)
    @Lob @Column(name = "PHOTO")
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Transient allows us to use this method in presentation tier while not
     * having to manage this property as a persistent one.
     *
     * @return
     */
    @Transient
    public String getBirthDateString() {
        String birthDateString = "";
        if (birthDate != null)
            birthDateString = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(birthDate);
        return birthDateString;
    }

    public String toString() {
        return "Contact - Id: " + id + ", First name: " + firstName
                + ", Last name: " + lastName + ", Birthday: " + birthDate
                + ", Description: " + description;
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

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}