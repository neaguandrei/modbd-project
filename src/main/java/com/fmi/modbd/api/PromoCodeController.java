package com.fmi.modbd.api;

import com.fmi.modbd.model.PromoCode;
import com.fmi.modbd.repository.PromoCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/promo-codes")
@AllArgsConstructor
public class PromoCodeController {

    private final PromoCodeRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PromoCode object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<PromoCode> update(@RequestBody PromoCode object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCode> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromoCode>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}