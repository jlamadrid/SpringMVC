package com.juan.springmvc.web.controller;

import com.juan.springmvc.domain.Customer;
import com.juan.springmvc.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
@RequestMapping("/customers")
@Controller
public class CustomerController {

    final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    /**
     * The MessageSource interface is autowired into the controller for retrieving messages with i18n support.
     */
    @Autowired
    org.springframework.context.MessageSource messageSource;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing contacts");

        List<Customer> customers = customerService.findAll();
        uiModel.addAttribute("customers", customers);

        logger.info("No. of customers: " + customers.size());

        return "customers/list"; //logical view name defined in tiles contacts/views.xml
    }
}
