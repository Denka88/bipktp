package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Table(name = "disciplines")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Discipline {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "discipline")
    private List<CTP> ctps;
    
    @ManyToMany
    @JoinTable(
            name = "discipline_user",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
