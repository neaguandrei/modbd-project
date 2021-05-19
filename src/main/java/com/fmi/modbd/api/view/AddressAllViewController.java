package com.fmi.modbd.api.view;

import com.fmi.modbd.dto.AddressAllDto;
import com.fmi.modbd.dto.RestaurantDto;
import com.fmi.modbd.model.AddressAll;
import com.fmi.modbd.model.Customer;
import com.fmi.modbd.model.AddressAll;
import com.fmi.modbd.repository.AddressViewAllRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/address/all")
@AllArgsConstructor
@Transactional
public class AddressAllViewController {

    private final AddressViewAllRepository repository;

    private final ModelMapper modelMapper;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AddressAllDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<AddressAll> update(@RequestBody AddressAllDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressAllDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressAllDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private AddressAllDto mapToDto(AddressAll customer) {
        return modelMapper.map(customer, AddressAllDto.class);
    }

    private AddressAll mapFromDto(AddressAllDto customer) {
        return modelMapper.map(customer, AddressAll.class);
    }
}
