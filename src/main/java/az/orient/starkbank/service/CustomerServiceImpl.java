package az.orient.starkbank.service;

import az.orient.starkbank.dto.CreateCustomerRequest;
import az.orient.starkbank.dto.CustomerDto;
import az.orient.starkbank.dto.CustomerDtoConverter;
import az.orient.starkbank.dto.UpdateCustomerRequest;
import az.orient.starkbank.exception.CustomerNotFoundException;
import az.orient.starkbank.model.City;
import az.orient.starkbank.model.Customer;
import az.orient.starkbank.repository.jpa.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    @Override
    public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setAddress(customerRequest.getAddress());
        customer.setName(customerRequest.getName());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(customerRequest.getCity().name()));

        customerRepository.save(customer);
        return customerDtoConverter.convert(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerDtoConverter::convert)
                .toList();
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(customerDtoConverter::convert)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found! ID: " + id));
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("No customer found to delete! ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(UUID id, UpdateCustomerRequest updateCustomerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found to update! ID: " + id));
        existingCustomer.setName(updateCustomerRequest.getName());
        existingCustomer.setAddress(updateCustomerRequest.getAddress());
        existingCustomer.setDateOfBirth(updateCustomerRequest.getDateOfBirth());
        existingCustomer.setCity(City.valueOf(updateCustomerRequest.getCity().name()));

        return customerDtoConverter.convert(existingCustomer);
    }
}
