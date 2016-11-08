package com.dreamchain.skeleton.service;

import com.dreamchain.skeleton.model.Customer;

import java.text.ParseException;
import java.util.List;

/**
 * Created by LAPTOP DREAM on 10/28/2016.
 */
public interface CustomerService {

    Customer get(Long id);

    String save(Customer customer) throws ParseException;

    List updateUser(Customer customer) throws ParseException;

    String delete(Long customerId);

    List<Customer> findAllCustomer();

    Customer findByCustomerCode(String customerCode);

}
