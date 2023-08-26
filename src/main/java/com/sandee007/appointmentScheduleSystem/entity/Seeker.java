package com.sandee007.appointmentScheduleSystem.entity;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "seekers")
@Getter
@Setter
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Column(name = "lastname")
    private String lastname;

    @NotNull(message = ValidationMessages.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Column(name = "phone")
    private String phone;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Lob
    @Column(name = "address")
    private String address;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Seeker() {
    }

    public Seeker(
            String firstname,
            String lastname,
            Date birthday,
            String phone,
            String address,
            String description,
            String image,
            Date deletedAt
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.image = image;
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Seeker{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }

    public String getFullName() {
        return this.firstname + ' ' + this.lastname;
    }
}
