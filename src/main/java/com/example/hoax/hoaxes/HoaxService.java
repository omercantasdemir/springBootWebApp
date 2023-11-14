package com.example.hoax.hoaxes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaxService {
    HoaxRepository hoaxRepository;


    public HoaxService(HoaxRepository hoaxRepository) {
        this.hoaxRepository = hoaxRepository;
    }

    public void create(Hoax hoax) {
        try {
            hoaxRepository.save(hoax);
        } catch (Exception e) {
            throw e;

        }
    }

    public List<Hoax> getHoaxes() {
        return hoaxRepository.findAll();
    }
}
