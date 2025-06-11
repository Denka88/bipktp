package com.denka88.bipktp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "records")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "lessontype_id")
    private LessonType lessonType;
    
    @ManyToMany
    @JoinTable(
            name = "record_teachmethod",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "teachmethod_id"))
    private List<TeachMethod> teachMethods;
    
    @OneToOne
    private Homework homework;
    
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
