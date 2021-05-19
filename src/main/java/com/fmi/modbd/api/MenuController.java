package com.fmi.modbd.api;

import com.fmi.modbd.dto.DriverDto;
import com.fmi.modbd.dto.MenuDto;
import com.fmi.modbd.dto.MenuDto;
import com.fmi.modbd.model.Menu;
import com.fmi.modbd.model.Menu;
import com.fmi.modbd.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/menus")
@AllArgsConstructor
@Transactional
public class MenuController {

    private final MenuRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MenuDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Menu> update(@RequestBody MenuDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private MenuDto mapToDto(Menu customer) {
        return modelMapper.map(customer, MenuDto.class);
    }

    private Menu mapFromDto(MenuDto customer) {
        return modelMapper.map(customer, Menu.class);
    }
}