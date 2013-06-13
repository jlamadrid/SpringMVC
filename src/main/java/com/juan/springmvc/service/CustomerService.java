package com.juan.springmvc.service;

import com.juan.springmvc.domain.Customer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
public interface CustomerService {

    public List<Customer> findAll();

    public Customer findById(Long id);

    public void deleteById(Long id);

    public Customer save(Customer contact);
}
