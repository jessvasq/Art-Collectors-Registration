package com.nvz.art_collectors_registration.repository;

import com.nvz.art_collectors_registration.entity.ArtCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtCollectorRepository extends JpaRepository<ArtCollector, Long> {
    ArtCollector findArtCollectorByEmail(String email);
}
