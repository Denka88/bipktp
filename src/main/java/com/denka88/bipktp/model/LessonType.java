package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "lessontypes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "lessonType")
    private List<Record> records;
}
