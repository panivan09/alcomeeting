package com.ivan.alcomeeting.entity.temp;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "university")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    //unidirectional from university
//    @OneToMany
//    @JoinColumn(name = )
//    private List<Student> students;
}
