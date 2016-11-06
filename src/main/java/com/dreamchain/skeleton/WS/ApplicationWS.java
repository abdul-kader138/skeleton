package com.dreamchain.skeleton.WS;

import com.dreamchain.skeleton.model.Customer;
import com.dreamchain.skeleton.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by LAPTOP DREAM on 11/6/2016.
 */
@WebService
public class ApplicationWS {

    @Autowired
    CustomerService customerService;

    @WebMethod(operationName="getCustomerById")
    public Customer getCustomerById(long id) {
        Customer customer = customerService.get(id);
        return customer;
    }


}
