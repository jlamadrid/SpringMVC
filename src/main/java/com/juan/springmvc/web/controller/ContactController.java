package com.juan.springmvc.web.controller;

import com.google.common.collect.Lists;
import com.juan.springmvc.domain.Contact;
import com.juan.springmvc.service.ContactService;
import com.juan.springmvc.web.form.ContactGrid;
import com.juan.springmvc.web.form.Message;
import com.juan.springmvc.web.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
@RequestMapping("/contacts")
@Controller
public class ContactController {

    final Logger logger = LoggerFactory.getLogger(ContactController.class);

    /**
     * The MessageSource interface is autowired into the controller for retrieving messages with i18n support.
     */
    @Autowired
    MessageSource messageSource;

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        logger.info("Listing contacts");

        List<Contact> contacts = contactService.findAll();
        uiModel.addAttribute("contacts", contacts);

        logger.info("No. of contacts: " + contacts.size());

        return "contacts/list"; //logical view name defined in tiles contacts/views.xml
    }

    /**
     * @RequestMapping annotation applied to the method indicates that the method
     * is to handle the URL /contacts/{id} with the HTTP GET method.
     *
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Contact contact = contactService.findById(id);
        uiModel.addAttribute("contact", contact);
        return "contacts/show";
    }

    /**
     * Update() method will be triggered when user updates contact information and clicks the Save button.
     *
     * Spring MVC will try to bind the submitted data to the Contact domain object and perform the type
     * conversion and formatting automatically. If binding errors are found (for example, the birth date
     * was entered in the wrong format), the errors will be saved into the BindingResult interface
     * (under the package org.springframework.validation), and an error message will be saved into the Model,
     * redisplaying the edit view. If the binding is successful, the data will be saved, and the logical
     * view name will be returned for the display contact view by using redirect: as the prefix. Note that we
     * want to display the message after the redirect, so we need to use the RedirectAttributes.addFlashAttribute()
     * method (an interface under the package org.springframework.web.servlet.mvc.support) for displaying the
     * success message in the show contact view. In Spring MVC, flash attributes are saved temporarily before
     * the redirect (typically in the session) to be made available to the request after the redirect and removed
     * immediately.
     *
     * @param contact
     * @return
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody Contact update(Contact contact) {

        logger.info("Updating contact");

        contactService.save(contact);

        return contact;
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contact", contactService.findById(id));
        return "contacts/update";
    }

    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Contact create(Contact contact) {

        logger.info("Creating contact");

        contactService.save(contact);

        return contact;
    }

    /**
     * Responds to http://localhost:8080/contacts?form
     * @param uiModel
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Contact contact = new Contact();
        uiModel.addAttribute("contact", contact);
        return "contacts/create";
    }

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ContactGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "rows", required = false) Integer rows,
                                @RequestParam(value = "sidx", required = false) String sortBy,
                                @RequestParam(value = "sord", required = false) String order) {

        logger.info("Listing contacts for grid with page: {}, rows: {}", page, rows);
        logger.info("Listing contacts for grid with sort: {}, order: {}", sortBy, order);

        // Process order by
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("birthDateString"))
            orderBy = "birthDate";

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<Contact> contactPage = contactService.findAllByPage(pageRequest);

        // Construct the grid data that will return as JSON data
        ContactGrid contactGrid= new ContactGrid();

        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());

        contactGrid.setContactData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {

        logger.info("Delete contact for id:", id);
        contactService.deleteById(id);

        return "redirect:/contacts";
    }
}
