package com.juan.springmvc.web.controller;

import com.juan.springmvc.domain.Contact;
import com.juan.springmvc.domain.Customer;
import com.juan.springmvc.service.ContactService;
import com.juan.springmvc.service.CustomerService;
import com.juan.springmvc.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
@RequestMapping("/api")
@Controller
public class RestCustomerController {

    final Logger logger = LoggerFactory.getLogger(RestCustomerController.class);

    /**
     * The MessageSource interface is autowired into the controller for retrieving messages with i18n support.
     */
    @Autowired
    MessageSource messageSource;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "customers/{lastname}", method=RequestMethod.GET)
    @ResponseBody
    public List<Customer> showCustomer(@PathVariable("lastname") String lastname) {
        return customerService.findByLastName(lastname);
    }
}