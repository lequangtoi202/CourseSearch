package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.User;

public interface CustomerService {
    Customer save(CustomerDto customerDto, User user);
    Customer update(Customer customer);
    Customer getByUser(User user);

}
