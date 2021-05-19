package com.fmi.modbd.api.view;

import com.fmi.modbd.model.OrderBrasovAll;
import com.fmi.modbd.repository.OrderViewBrasovAllRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders/brasov")
@AllArgsConstructor
@Transactional
public class OrdersAllBrasovViewController {

    private final OrderViewBrasovAllRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderBrasovAll object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<OrderBrasovAll> update(@RequestBody OrderBrasovAll object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBrasovAll> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
