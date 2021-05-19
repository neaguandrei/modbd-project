package com.fmi.modbd.api.view;

import com.fmi.modbd.dto.MenuDto;
import com.fmi.modbd.dto.OrderBrasovAllDto;
import com.fmi.modbd.dto.OrderBucurestiAllDto;
import com.fmi.modbd.model.Menu;
import com.fmi.modbd.model.OrderBucurestiAll;
import com.fmi.modbd.model.OrderBucurestiAll;
import com.fmi.modbd.repository.OrderViewBucurestiAllRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders/bucuresti")
@AllArgsConstructor
@Transactional
public class OrdersAllBucurestiViewController {

    private final OrderViewBucurestiAllRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderBucurestiAllDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<OrderBucurestiAll> update(@RequestBody OrderBucurestiAllDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBucurestiAllDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderBucurestiAllDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private OrderBucurestiAllDto mapToDto(OrderBucurestiAll customer) {
        return modelMapper.map(customer, OrderBucurestiAllDto.class);
    }

    private OrderBucurestiAll mapFromDto(OrderBucurestiAllDto customer) {
        return modelMapper.map(customer, OrderBucurestiAll.class);
    }
}
