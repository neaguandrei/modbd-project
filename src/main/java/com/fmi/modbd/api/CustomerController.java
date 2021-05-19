package com.fmi.modbd.api;

import com.fmi.modbd.model.Customer;
import com.fmi.modbd.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/customers")
@AllArgsConstructor
@Transactional
public class CustomerController {

    private final CustomerRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody Customer object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}