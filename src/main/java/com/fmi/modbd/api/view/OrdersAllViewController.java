package com.fmi.modbd.api.view;

import com.fmi.modbd.dto.OrderAllDto;
import com.fmi.modbd.model.OrderAll;
import com.fmi.modbd.repository.OrderViewAllRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders/all")
@AllArgsConstructor
@Transactional
public class OrdersAllViewController {

    private final OrderViewAllRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderAllDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<OrderAllDto> update(@RequestBody OrderAllDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderAllDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderAllDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private OrderAllDto mapToDto(OrderAll customer) {
        return modelMapper.map(customer, OrderAllDto.class);
    }

    private OrderAll mapFromDto(OrderAllDto customer) {
        return modelMapper.map(customer, OrderAll.class);
    }
}
