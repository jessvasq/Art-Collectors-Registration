package com.nvz.art_collectors_registration.service.impl;

import com.nvz.art_collectors_registration.dto.ArtCollectorDto;
import com.nvz.art_collectors_registration.entity.ArtCollector;
import com.nvz.art_collectors_registration.entity.Role;
import com.nvz.art_collectors_registration.repository.ArtCollectorRepository;
import com.nvz.art_collectors_registration.repository.RoleRepository;
import com.nvz.art_collectors_registration.service.ArtCollectorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ArtCollectorServiceImpl implements ArtCollectorService {
    private ArtCollectorRepository artCollectorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    // Inject both repository classes and password encoder using constructor based injection
    public ArtCollectorServiceImpl(ArtCollectorRepository artCollectorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.artCollectorRepository = artCollectorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveArtCollector(ArtCollector artCollector) {
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        artCollector.setUsername(artCollectorDto.getFirstName() + " " + artCollectorDto.getLastName());
        artCollector.setEmail(artCollectorDto.getEmail());

        artCollector.setPassword(passwordEncoder.encode(artCollectorDto.getPassword()));

        // Set role based on registration
        String roleName;
        if(artCollectorDto.isAdminRegistration()){
            roleName = "ROLE_ADMIN";
        } else {
            roleName = "ROLE_USER";
        }

        // Check if the role already exists
        Role role = roleRepository.findRoleByName(roleName);
        if(role == null){
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        artCollector.setRoles(Collections.singletonList(role));
        artCollectorRepository.save(artCollector);

    }

    @Override
    public ArtCollector findArtCollectorByEmail(String email) {
        return null;
    }

    @Override
    public List<ArtCollector> findAllArtCollectors() {
        return List.of();
    }

    //convert artCollector to ArtCollectorDto object
    private ArtCollectorDto convertEntityToDto(ArtCollector artCollector) {
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        String[] name = artCollector.getUsername().split(" ");
        artCollectorDto.setFirstName(name[0]);
        artCollectorDto.setLastName(name[1]);
        artCollectorDto.setEmail(artCollector.getEmail());
        return artCollectorDto;
    }
}
