package com.fmi.modbd.api.view;

import com.fmi.modbd.model.AddressAll;
import com.fmi.modbd.model.Customer;
import com.fmi.modbd.repository.AddressViewAllRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/address/all")
@AllArgsConstructor
@Transactional
public class AddressAllViewController {

    private final AddressViewAllRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AddressAll object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody AddressAll object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressAll> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
