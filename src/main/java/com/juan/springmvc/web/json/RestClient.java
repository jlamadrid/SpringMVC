package com.juan.springmvc.web.json;

import com.juan.springmvc.domain.Contact;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: jl25292
 */
public class RestClient {

    public RestClient(){

    }

    public Contact[] getContacts(){
        return new RestTemplate().getForObject("http://localhost:8080/api/contacts", Contact[].class);
    }

    public static void main(String[] args){

        RestClient client = new RestClient();
        Contact[] contacts = client.getContacts();
        for (Contact contact : contacts){
            System.out.println("Contact: " + contact.getLastName());
        }
    }
}
