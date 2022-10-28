package com.ivan.alcomeeting.entity;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_user_id")
    private User meetingOwner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meetings_users",
    joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participates;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meetings_beverages",
    joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "beverage_id")
    )
    private Set<Beverage> beverages;

    public Meeting() {
    }

    public Meeting(Long id,
                   String name,
                   LocalDate date,
                   String address,
                   User meetingOwner,
                   Set<User> participates,
                   Set<Beverage> beverages) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.address = address;
        this.meetingOwner = meetingOwner;
        this.participates = participates;
        this.beverages = beverages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getMeetingOwner() {
        return meetingOwner;
    }

    public void setMeetingOwner(User meetingOwner) {
        this.meetingOwner = meetingOwner;
    }

    public Set<User> getParticipates() {
        return participates;
    }

    public void setParticipates(Set<User> participates) {
        this.participates = participates;
    }

    public Set<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(Set<Beverage> beverages) {
        this.beverages = beverages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meeting meeting = (Meeting) o;
        return id != null && Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
