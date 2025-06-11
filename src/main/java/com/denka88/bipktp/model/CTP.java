package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "ctps")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "period_id")
    private Period period;
    
    @ManyToOne
    @JoinColumn(name = "committee_id")
    private Committee committee;
    
    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;
    
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    
    @OneToMany(mappedBy = "ctp")
    private List<Chapter> chapters;
}
