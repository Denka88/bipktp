package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.List;

@Table(name = "periods")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Period {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Year start;
    private Year end;
    
    @OneToMany(mappedBy = "period")
    private List<CTP> ctps;
}
