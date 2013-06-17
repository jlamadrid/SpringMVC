package com.juan.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Juan
 */
@RequestMapping("/")
@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {

        return "home"; //logical view name defined in tiles home/views.xml
    }

}
