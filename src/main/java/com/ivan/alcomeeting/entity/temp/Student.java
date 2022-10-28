package com.ivan.alcomeeting.entity.temp;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Long age;

    // no mapping
//    @Column(name = "university_id")
//    private Long universityId;

    //unidirectional from student
//    @ManyToOne
//    @JoinColumn(name = )
//    private University university;
}
