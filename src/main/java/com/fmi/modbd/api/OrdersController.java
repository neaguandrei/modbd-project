package com.fmi.modbd.api;

import com.fmi.modbd.dto.DriverDto;
import com.fmi.modbd.dto.OrderDto;
import com.fmi.modbd.model.Order;
import com.fmi.modbd.model.Order;
import com.fmi.modbd.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders")
@AllArgsConstructor
@Transactional
public class OrdersController {

    private final OrderRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Order> update(@RequestBody OrderDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private OrderDto mapToDto(Order customer) {
        return modelMapper.map(customer, OrderDto.class);
    }

    private Order mapFromDto(OrderDto customer) {
        return modelMapper.map(customer, Order.class);
    }
}