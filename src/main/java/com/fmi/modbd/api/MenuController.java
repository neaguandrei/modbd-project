package com.fmi.modbd.api;

import com.fmi.modbd.model.Menu;
import com.fmi.modbd.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuRepository repository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Menu object) {
        repository.save(object);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Menu> update(@RequestBody Menu object) throws Exception {
        Menu optional = repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(optional);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}