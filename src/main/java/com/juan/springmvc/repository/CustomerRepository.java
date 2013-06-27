package com.juan.springmvc.repository;

import com.juan.springmvc.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 *
 * http://static.springsource.org/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

}
