package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.User;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(CustomerDto customerDto, User user) {
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setGender(customerDto.isGender());
        customer.setCreated_at(LocalDateTime.now());
        customer.setUpdated_at(LocalDateTime.now());
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerSaved = customerRepository.getById(customer.getId());
        if (customerSaved != null){
            customerSaved.setFullName(customer.getFullName());
            customerSaved.setDateOfBirth(customer.getDateOfBirth());
            customerSaved.setAddress(customer.getAddress());
            customerSaved.setEmail(customer.getEmail());
            customerSaved.setPhone(customer.getPhone());
            customerSaved.setGender(customer.isGender());
            customerSaved.setUpdated_at(LocalDateTime.now());
            return customerRepository.save(customerSaved);
        }
        return null;
    }

    @Override
    public Customer getByUser(User user) {
        return customerRepository.getByUser(user);
    }
}
