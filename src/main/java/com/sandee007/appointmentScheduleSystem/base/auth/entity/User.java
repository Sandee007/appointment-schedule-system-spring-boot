package com.sandee007.appointmentScheduleSystem.base.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.UniqueEmail;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

//this replicates the default spring-security db table -> users
@Entity
@Table(name = "users")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users SET enabled=0 WHERE id=? ",check = ResultCheckStyle.COUNT)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @UniqueEmail
    @NotNull(message = ValidationMessages.REQUIRED)
    //    @Email // EMAIL DOMAIN IS APPENDED MANUALLY
    @Pattern(regexp = "^[A-Za-z0-9_]*$", message = "Should only contain letters, numbers and underscores")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "integer default 1")
    private Integer enabled;

    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Role role;

    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Consultant consultant;

    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Seeker seeker;



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

    public User(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }

}
