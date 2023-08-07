package com.sandee007.appointmentScheduleSystem.base.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//this replicates the default spring-security db table -> users
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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "integer default 1")
    private Integer enabled;

    public User() {
    }

    public User(int id, String username, String password, int enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String username, String password, int enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
