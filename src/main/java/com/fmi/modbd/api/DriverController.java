package com.fmi.modbd.api;

import com.fmi.modbd.model.Driver;
import com.fmi.modbd.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/drivers")
@AllArgsConstructor
@Transactional
public class DriverController {

    private final DriverRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Driver object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Driver> update(@RequestBody Driver object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Driver>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}