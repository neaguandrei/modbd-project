package com.fmi.modbd.api;

import com.fmi.modbd.dto.PromoCodeDto;
import com.fmi.modbd.dto.RestaurantDto;
import com.fmi.modbd.model.Restaurant;
import com.fmi.modbd.model.Restaurant;
import com.fmi.modbd.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/restaurants")
@AllArgsConstructor
@Transactional
public class RestaurantController {

    private final RestaurantRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody RestaurantDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Restaurant> update(@RequestBody RestaurantDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private RestaurantDto mapToDto(Restaurant customer) {
        return modelMapper.map(customer, RestaurantDto.class);
    }

    private Restaurant mapFromDto(RestaurantDto customer) {
        return modelMapper.map(customer, Restaurant.class);
    }
}