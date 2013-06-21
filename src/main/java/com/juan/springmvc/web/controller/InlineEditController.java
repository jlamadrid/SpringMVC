package com.juan.springmvc.web.controller;

import com.juan.springmvc.domain.Contact;
import com.juan.springmvc.domain.Customer;
import com.juan.springmvc.service.ContactService;
import com.juan.springmvc.service.CustomerService;
import com.juan.springmvc.web.form.XEditableForm;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 *
 * Controller used to support inline editing feature.
 */
@Controller
@RequestMapping(value = "/api/inline")
public class InlineEditController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.POST)
     @ResponseBody
     public String postContactEdit(@ModelAttribute XEditableForm form,
                                  @PathVariable("id") Long id) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Contact contact = contactService.findById(id);
        contact.setLastName(form.getValue());
        contactService.save(contact);
        return "";
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String postCustomerEdit(@ModelAttribute XEditableForm form,
                                 @PathVariable("id") Long id) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Customer customer = customerService.findById(id);
        customer.setLastName(form.getValue());
        customerService.save(customer);
        return "";
    }
}