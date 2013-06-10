package com.juan.springmvc.service;

import com.juan.springmvc.domain.Contact;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
public interface ContactService {

    public List<Contact> findAll();

    public Contact findById(Long id);

    public Contact save(Contact contact);

}
