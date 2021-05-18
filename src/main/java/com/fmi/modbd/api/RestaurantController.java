package com.fmi.modbd.api;

import com.fmi.modbd.model.Restaurant;
import com.fmi.modbd.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Restaurant object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Restaurant> update(@RequestBody Restaurant object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}