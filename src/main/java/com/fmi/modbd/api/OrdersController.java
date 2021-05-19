package com.fmi.modbd.api;

import com.fmi.modbd.model.Order;
import com.fmi.modbd.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders")
@AllArgsConstructor
@Transactional
public class OrdersController {

    private final OrderRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Order object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Order> update(@RequestBody Order object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}