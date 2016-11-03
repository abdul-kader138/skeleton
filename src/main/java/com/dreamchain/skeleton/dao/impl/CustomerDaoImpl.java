package com.dreamchain.skeleton.dao.impl;

import com.dreamchain.skeleton.dao.CustomerDao;
import com.dreamchain.skeleton.model.Customer;
import com.dreamchain.skeleton.model.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Customer get(Long id) {
        return hibernateTemplate.get(Customer.class,id);
    }

    @Override
    public void save(Customer customer) {
            hibernateTemplate.merge(customer);

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void remove(Customer customer) {

    }

    @Override
    public List<Customer> findAll(String customerName) {
        return null;
    }

    @Override
    public Customer findByCustomerCode(String customerCode) {
        Customer customer=null;
        DetachedCriteria dcr= DetachedCriteria.forClass(Customer.class);
        Criterion cr = Restrictions.eq("customerCode", customerCode);
        dcr.add(cr);
        List<Object> lst= hibernateTemplate.findByCriteria(dcr);
        if(lst.size()==0)return customer;
        return (Customer)lst.get(0);
    }

    @Override
    public Customer findByNid(Integer nidNo) {
        Customer customer=null;
        DetachedCriteria dcr= DetachedCriteria.forClass(Customer.class);
        Criterion cr = Restrictions.eq("nid", nidNo);
        dcr.add(cr);
        List<Object> lst= hibernateTemplate.findByCriteria(dcr);
        if(lst.size()==0)return customer;
        return (Customer)lst.get(0);
    }
}
