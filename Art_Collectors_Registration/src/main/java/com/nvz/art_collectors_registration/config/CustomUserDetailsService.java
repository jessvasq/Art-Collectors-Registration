package com.nvz.art_collectors_registration.config;

import com.nvz.art_collectors_registration.entity.ArtCollector;
import com.nvz.art_collectors_registration.entity.Role;
import com.nvz.art_collectors_registration.repository.ArtCollectorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private ArtCollectorRepository artCollectorRepository;

    // inject repository bean
    public CustomUserDetailsService(ArtCollectorRepository artCollectorRepository) {
        this.artCollectorRepository = artCollectorRepository;
    }

    // load art collector details from the db
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // retrieve artCollector by email
        ArtCollector artCollector = artCollectorRepository.findArtCollectorByEmail(email);

        if (artCollector == null) {
            throw new UsernameNotFoundException(email);
        } else {
            return new org.springframework.security.core.userdetails.User(artCollector.getEmail(), artCollector.getPassword(), mapRolesToAuthorities(artCollector.getRoles()));
        }
    }

    // maps the roles to 'GrantedAuthority' objects
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        // use streams to transform each 'Role' into 'SimpleGrantedAuthority'
        Collection<? extends GrantedAuthority> authorities = roles.stream().map(role->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }
}
