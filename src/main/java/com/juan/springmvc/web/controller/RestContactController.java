package com.juan.springmvc.web.controller;

import com.google.common.collect.Lists;
import com.juan.springmvc.domain.Contact;
import com.juan.springmvc.service.ContactService;
import com.juan.springmvc.web.form.ContactGrid;
import com.juan.springmvc.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller exposes a web services api that handles JSON request/response interaction,
 * e.g. AJAX Forms, UI Components, Backbone MVC, etc.
 *
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
@RequestMapping("/api")
@Controller
public class RestContactController {

    final Logger logger = LoggerFactory.getLogger(RestContactController.class);

    /**
     * The MessageSource interface is autowired into the controller for retrieving messages with i18n support.
     */
    @Autowired
    MessageSource messageSource;

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "contacts", method = RequestMethod.GET)
    @ResponseBody
    public List listContacts(Model uiModel) {
        logger.info("Listing contacts");
        return contactService.findAll();
    }

    /**
     * curl http://localhost:8080/api/contacts/12
     * {"id":12,"version":0,"firstName":"Paul","lastName":"Simon","birthDate":1014872400000,"description":null,"photo":null,"birthDateString":"2002-02-28"}
     */
    @RequestMapping(value = "contacts/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Contact showContact(@PathVariable("id") Long id) {
        return contactService.findById(id);
    }

    /**
     * Updates a contact.
     *
     * @RequestMapping annotation here is almost the same as the one used with the showContact() method.
     * The only difference is that the method attribute is set to handle HTTP PUT requests instead of GET requests.
     *
     * The @ResponseStatus annotation defines the HTTP status that should be set on the response to the client.
     * In this case, HttpStatus.NO_CONTENT indicates that the response status should be set to the HTTP status
     * code 204. That status code means that the request was processed successfully, but nothing is returned in
     * the body of the response.
     *
     * @param contact
     */
    @RequestMapping(value="contacts/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContact(@PathVariable("id") long id, @RequestBody Contact contact) {
        contactService.save(contact);
    }

    /**
     * @RequestMapping annotation looks like the ones we used before.
     * It only varies in that this method’s @Request-Mapping has its method attribute set to handle DELETE requests.
     * *
     * @param id
     */
    @RequestMapping(value="contacts/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") long id) {
        contactService.deleteById(id);
    }

    /**
     * This method is annotated with @ResponseStatus to set the HTTP status code in the request.
     * The status will be set to 201 (Created) to indicate that a resource was successfully created.
     * When an HTTP 201 response is returned to the client, the URL of the new resource should be
     * sent along with it. So one of the last things that createContact() does is set the Location
     * header to contain the resource’s URL.
     *
     * @param contact
     * @param result
     * @param response
     * @return
     * @throws BindException
     */
    @RequestMapping(value="contacts", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody JsonResponse createContact(@Valid Contact contact,
                                               BindingResult result,
                                               HttpServletResponse response) {

        JsonResponse jsonResponse = new JsonResponse();
        List<Contact> contactList = new ArrayList<Contact>();

        if(!result.hasErrors()){

            contactService.save(contact);

            jsonResponse.setStatus("SUCCESS");
            contactList.add(contact);
            jsonResponse.setResult(contactList);

        }else{
            jsonResponse.setStatus("FAIL");
            jsonResponse.setResult(result.getAllErrors());
        }

        return jsonResponse;
    }


    /**
     * Demonstrates using JSON response to feed client side table UI w/ pagination.
     * @param page
     * @param rows
     * @param sortBy
     * @param order
     * @return
     */
    @RequestMapping(value = "/contacts/listgrid", method = RequestMethod.GET, produces="application/json")
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
}