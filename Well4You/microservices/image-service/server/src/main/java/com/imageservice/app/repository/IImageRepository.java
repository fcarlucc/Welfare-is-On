package com.imageservice.app.repository;

import com.imageservice.app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
