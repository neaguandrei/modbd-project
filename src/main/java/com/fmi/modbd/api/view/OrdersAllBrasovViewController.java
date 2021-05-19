package com.fmi.modbd.api.view;

import com.fmi.modbd.dto.AddressAllDto;
import com.fmi.modbd.dto.OrderBrasovAllDto;
import com.fmi.modbd.model.OrderBrasovAll;
import com.fmi.modbd.model.OrderBrasovAll;
import com.fmi.modbd.repository.OrderViewBrasovAllRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders/brasov")
@AllArgsConstructor
@Transactional
public class OrdersAllBrasovViewController {

    private final OrderViewBrasovAllRepository repository;

    private final ModelMapper modelMapper;
    
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderBrasovAllDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<OrderBrasovAll> update(@RequestBody OrderBrasovAllDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBrasovAllDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderBrasovAllDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private OrderBrasovAllDto mapToDto(OrderBrasovAll customer) {
        return modelMapper.map(customer, OrderBrasovAllDto.class);
    }

    private OrderBrasovAll mapFromDto(OrderBrasovAllDto customer) {
        return modelMapper.map(customer, OrderBrasovAll.class);
    }
}
