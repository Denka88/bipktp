package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "chapters")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "ctp_id")
    private CTP ctp;
    
    @OneToMany(mappedBy = "chapter")
    private List<Record> records;
}
