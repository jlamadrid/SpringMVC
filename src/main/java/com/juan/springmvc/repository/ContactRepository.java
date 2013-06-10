package com.juan.springmvc.repository;

import com.juan.springmvc.domain.Contact;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */

public interface ContactRepository extends CrudRepository<Contact, Long> {

}
