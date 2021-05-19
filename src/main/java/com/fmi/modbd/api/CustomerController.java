package com.fmi.modbd.api;

import com.fmi.modbd.dto.AddressDto;
import com.fmi.modbd.dto.CustomerDto;
import com.fmi.modbd.model.Address;
import com.fmi.modbd.model.Customer;
import com.fmi.modbd.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/customers")
@AllArgsConstructor
@Transactional
public class CustomerController {

    private final CustomerRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CustomerDto object) {
        Customer customer = mapFromDto(object);
        customer.getAddress().setCustomer(customer);
        repository.save(customer);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody CustomerDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private CustomerDto mapToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private Customer mapFromDto(CustomerDto customer) {
        return modelMapper.map(customer, Customer.class);
    }

}