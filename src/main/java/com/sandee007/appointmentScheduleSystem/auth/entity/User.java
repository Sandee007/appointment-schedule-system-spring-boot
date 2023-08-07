package com.sandee007.appointmentScheduleSystem.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//this replicates the default spring-security db table -> users (email column has been added)
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled", nullable = false, columnDefinition = "integer default 1")
    private Integer active;

    public User() {
    }

    public User(int id, String username, String password, int active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public User(String username, String password, int active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }
}
