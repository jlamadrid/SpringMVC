package com.juan.springmvc.service;

import com.juan.springmvc.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
public interface ContactService {

    public List<Contact> findAll();

    public Contact findById(Long id);

    public void deleteById(Long id);

    public Contact save(Contact contact);

    public Page<Contact> findAllByPage(Pageable pageable);

}
