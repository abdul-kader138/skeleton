package com.dreamchain.skeleton.service.impl;

import com.dreamchain.skeleton.dao.CustomerDao;
import com.dreamchain.skeleton.dao.UserDao;
import com.dreamchain.skeleton.model.Customer;
import com.dreamchain.skeleton.model.User;
import com.dreamchain.skeleton.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    UserDao userDao;

    private static String INVALID_INPUT = "Invalid input";
    private static String NID_EXISTS = "This NID/Customer already exists in the system.Please try again with new one!!!";
    private static String EMAIL_PHONE_EXISTS = "Email or Phone no already exists in the system.Please try again with new one!!!";



    @Override
    public Customer get(Long id) {
        return customerDao.get(id);
    }

    @Override
    @Transactional
    public String save(Customer customer) throws ParseException {
        String validationMsg = "";
        validationMsg = checkInput(customer);
        Customer existingCustomer = customerDao.findByNid(customer.getNid());
        if (existingCustomer != null && validationMsg=="") validationMsg = NID_EXISTS;
        existingCustomer=customerDao.findByEmailAndPhone(customer.getEmail(), customer.getPhone());
        if (existingCustomer != null && validationMsg=="") validationMsg = EMAIL_PHONE_EXISTS;
        if ("".equals(validationMsg)) {
            Customer customerObj = setAdminInfo(customer, "save");
            customerObj.setCustomerCode(createCustomerCode(customer.getName()));
            customerDao.save(customerObj);
        }
        return validationMsg;
    }

    @Override
    public List updateUser(Customer customer) throws ParseException {
        return null;
    }

    @Override
    public String delete(Long customerId) {
        return null;
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerDao.findAll();
    }


    private String checkInput(Customer customer) {
        String msg = "";
        if (customer.getName() == null || customer.getEmail() == null
                || customer.getPhone() == null || customer.getNid() == null || customer.getAddress() == null)
            msg = INVALID_INPUT;

        //server side validation check
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
        if (constraintViolations.size() > 0 && msg == "") msg = INVALID_INPUT;


        return msg;

    }

    private Customer setAdminInfo(Customer customer, String action) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentLoggedInUser = (User) auth.getPrincipal();
        User user=userDao.findByUserName(currentLoggedInUser.getEmail());
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date date = dateFormat.parse(dateFormat.format(new Date()));
        if ("save".equals(action)) {
            customer.setCreatedBy(user.getId());
            customer.setCreatedOn(date);
            customer.setUpdatedBy(0l);
        }
        if ("update".equals(action)) {
            customer.setUpdatedOn(date);
            customer.setUpdatedBy(user.getId());
        }

        return customer;

    }


    private String createCustomerCode(String customerName){

        String code="";
        boolean isMatched=true;
        Random rand = new Random();
        do{
            String name=customerName.substring(0,2).toUpperCase();
            int serial=rand.nextInt(998)+4000;
            code=name+serial;
            Customer customer=customerDao.findByCustomerCode(code);
            if(customer == null) isMatched=false;;
        }while(isMatched);

        return code;
    }

    }

