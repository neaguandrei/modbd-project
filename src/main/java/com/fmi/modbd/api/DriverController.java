package com.fmi.modbd.api;

import com.fmi.modbd.dto.CustomerDto;
import com.fmi.modbd.dto.DriverDto;
import com.fmi.modbd.model.Customer;
import com.fmi.modbd.model.Driver;
import com.fmi.modbd.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/drivers")
@AllArgsConstructor
@Transactional
public class DriverController {

    private final DriverRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DriverDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Driver> update(@RequestBody DriverDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private DriverDto mapToDto(Driver customer) {
        return modelMapper.map(customer, DriverDto.class);
    }

    private Driver mapFromDto(DriverDto customer) {
        return modelMapper.map(customer, Driver.class);
    }
}