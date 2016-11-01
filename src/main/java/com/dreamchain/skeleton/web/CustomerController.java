package com.dreamchain.skeleton.web;

import com.dreamchain.skeleton.model.Customer;
import com.dreamchain.skeleton.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(CustomerController.URL)
@PropertySource("classpath:config.properties")

public class CustomerController {
    static final String URL = "/customer";

    @Autowired
    CustomerService customerService;
    @Autowired
    Environment environment;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map saveUser(@RequestBody Customer customer) throws ParseException {
        String successMsg = "";
        String validationError = customerService.save(customer);
        if (validationError.length() == 0) successMsg = environment.getProperty("customer.save.success.msg");
        HashMap<String, String> serverResponse = new HashMap<>();
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("validationError", validationError);
        return serverResponse;
    }


}
