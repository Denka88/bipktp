package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "committees")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Committee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "committee")
    private List<CTP> ctps;
}
