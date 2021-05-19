package com.fmi.modbd.api;

import com.fmi.modbd.dto.CategoryDto;
import com.fmi.modbd.model.Category;
import com.fmi.modbd.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/categories")
@AllArgsConstructor
@Transactional
public class CategoryController {

    private final CategoryRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CategoryDto object) {
        repository.save(modelMapper.map(object, Category.class));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(modelMapper.map(object, Category.class));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(category -> modelMapper.map(category, CategoryDto.class)).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
