package com.sandee007.appointmentScheduleSystem.entity;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "address")
    private String address;

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
}
