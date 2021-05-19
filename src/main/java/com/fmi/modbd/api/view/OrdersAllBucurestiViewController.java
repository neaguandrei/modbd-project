package com.fmi.modbd.api.view;

import com.fmi.modbd.model.OrderBucurestiAll;
import com.fmi.modbd.repository.OrderViewBucurestiAllRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders/bucuresti")
@AllArgsConstructor
@Transactional
public class OrdersAllBucurestiViewController {

    private final OrderViewBucurestiAllRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderBucurestiAll object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<OrderBucurestiAll> update(@RequestBody OrderBucurestiAll object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBucurestiAll> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
