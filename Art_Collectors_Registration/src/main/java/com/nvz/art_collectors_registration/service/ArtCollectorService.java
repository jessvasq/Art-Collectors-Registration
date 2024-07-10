package com.nvz.art_collectors_registration.service;

import com.nvz.art_collectors_registration.entity.ArtCollector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtCollectorService {
    void saveArtCollector(ArtCollector artCollector);
    ArtCollector findArtCollectorByEmail(String email);
    List<ArtCollector> findAllArtCollectors();
}
