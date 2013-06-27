package com.juan.springmvc.service.jpa;

import com.google.common.collect.Lists;
import com.juan.springmvc.domain.Customer;
import com.juan.springmvc.repository.CustomerRepository;
import com.juan.springmvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
@Service("customerService")
@Repository
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly=true)
    public List<Customer> findAll() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.delete(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }
}
