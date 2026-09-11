package az.orient.starkbank.service;

import az.orient.starkbank.dto.CreateCustomerRequest;
import az.orient.starkbank.dto.CustomerDto;
import az.orient.starkbank.dto.UpdateCustomerRequest;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    public CustomerDto createCustomer(CreateCustomerRequest customerRequest);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(UUID id);

    void deleteCustomer(UUID id);

    CustomerDto updateCustomer(UUID id, UpdateCustomerRequest updateCustomerRequest);
}
