package com.fmi.modbd.api;

import com.fmi.modbd.dto.OrderDto;
import com.fmi.modbd.dto.PromoCodeDto;
import com.fmi.modbd.dto.PromoCodeDto;
import com.fmi.modbd.model.PromoCode;
import com.fmi.modbd.model.PromoCode;
import com.fmi.modbd.model.PromoCode;
import com.fmi.modbd.repository.PromoCodeRepository;
import com.fmi.modbd.repository.PromoCodeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/promo-codes")
@AllArgsConstructor
@Transactional
public class PromoCodeController {

    private final PromoCodeRepository repository;

    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PromoCodeDto object) {
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<PromoCode> update(@RequestBody PromoCodeDto object) throws Exception {
        repository.findById(object.getId())
                .orElseThrow((() -> new Exception("Not Found")));
        repository.save(mapFromDto(object));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).map(this::mapToDto).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromoCodeDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private PromoCodeDto mapToDto(PromoCode customer) {
        return modelMapper.map(customer, PromoCodeDto.class);
    }

    private PromoCode mapFromDto(PromoCodeDto customer) {
        return modelMapper.map(customer, PromoCode.class);
    }
}