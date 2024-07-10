package com.nvz.art_collectors_registration.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity (name ="roles")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<ArtCollector> artCollectors;

    public Role() {}

    public Role(Long id, String name, List<ArtCollector> artCollectors) {
        this.id = id;
        this.name = name;
        this.artCollectors = artCollectors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtCollector> getArtCollectors() {
        return artCollectors;
    }

    public void setArtCollectors(List<ArtCollector> artCollectors) {
        this.artCollectors = artCollectors;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artCollectors=" + artCollectors +
                '}';
    }
}
