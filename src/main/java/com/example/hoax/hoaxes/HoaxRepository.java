package com.example.hoax.hoaxes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HoaxRepository extends JpaRepository<Hoax, Long> {
}
