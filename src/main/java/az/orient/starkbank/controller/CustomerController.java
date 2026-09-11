package az.orient.starkbank.controller;

import az.orient.starkbank.dto.CreateCustomerRequest;
import az.orient.starkbank.dto.CustomerDto;
import az.orient.starkbank.dto.UpdateCustomerRequest;
import az.orient.starkbank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, updateCustomerRequest));
    }

}
