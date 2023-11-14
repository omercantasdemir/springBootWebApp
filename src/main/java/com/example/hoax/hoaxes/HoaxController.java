package com.example.hoax.hoaxes;

import com.example.hoax.shared.GenericMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HoaxController {
    HoaxService hoaxService;

    public HoaxController(HoaxService hoaxService) {
        this.hoaxService = hoaxService;
    }

    @GetMapping("/api/v1/hoaxes")
    ResponseEntity<?> getHoaxes() {
        List<Hoax> hoaxList = hoaxService.getHoaxes();
        return ResponseEntity.ok().body(hoaxList);
    }

    @PostMapping("/api/v1/hoaxes/add")
    ResponseEntity<?> createHoax(@RequestBody Hoax hoax) {
        hoaxService.create(hoax);
        return ResponseEntity.ok(new GenericMessage("Hoax is saved"));
    }
}
