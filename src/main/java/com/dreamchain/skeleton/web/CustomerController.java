package com.dreamchain.skeleton.web;

import com.dreamchain.skeleton.model.Customer;
import com.dreamchain.skeleton.model.User;
import com.dreamchain.skeleton.permission.CheckLoggedInUser;
import com.dreamchain.skeleton.service.CustomerService;
import com.dreamchain.skeleton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
    UserService userService;

    @Autowired
    Environment environment;

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class.getName());


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map saveUser(@RequestBody Customer customer,HttpSession httpSession) throws ParseException {
        String successMsg = "";
        String validationError = "";
        String invalidUserError = "";
        logger.info("creating new customer: >>");
//        Customer customer=new Customer();
        boolean isLoggedUserInvalid = checkLoggedInUserExistence(httpSession);
        if (isLoggedUserInvalid) invalidUserError = environment.getProperty("user.invalid.error.msg");
        if (!isLoggedUserInvalid) validationError = customerService.save(customer);
        if (validationError.length() == 0 && !isLoggedUserInvalid)
            successMsg = environment.getProperty("customer.save.success.msg");
        logger.info("creating new customer: << " + successMsg + invalidUserError + invalidUserError);
        return createServerResponse(successMsg, validationError, invalidUserError, null);
    }


    private Map createServerResponse(String successMsg, String validationError, String invalidUserError, User user) {
        HashMap serverResponse = new HashMap();
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("validationError", validationError);
        serverResponse.put("invalidUserError", invalidUserError);
        serverResponse.put("user", user);
        return serverResponse;

    }


    private boolean checkLoggedInUserExistence(HttpSession httpSession) {
        boolean isLoggedUserExists = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) auth.getPrincipal();
        User user = userService.findByUserName(loggedUser.getEmail());
        if (user == null) {
            isLoggedUserExists = true;
            httpSession.invalidate();
        }
        return isLoggedUserExists;
    }

}
