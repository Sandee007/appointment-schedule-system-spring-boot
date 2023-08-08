package com.sandee007.appointmentScheduleSystem.entity;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "consultants")
@Getter
@Setter
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    //    https://www.baeldung.com/spring-data-jpa-query-by-date
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    //    https://stackoverflow.com/questions/3868096/jpa-how-do-i-persist-a-string-into-a-database-field-type-mysql-text
    @Lob // large object
    @Column(name = "description")
    private String description;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "charge_per_hour")
    private int chargePerHour;

    @OneToMany(mappedBy = "consultant" , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ConsultantScheduleDate> consultantScheduleDates;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "consultant_industries",
            joinColumns = @JoinColumn(name = "consultant_id"),
            inverseJoinColumns = @JoinColumn(name = "industry_id")
    )
    private List<Industry> industries;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "consultant_countries",
            joinColumns = @JoinColumn(name = "consultant_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    public Consultant() {
    }

    public Consultant(String firstname, String lastname, Date birthday, String description, String phone, int chargePerHour) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.description = description;
        this.phone = phone;
        this.chargePerHour = chargePerHour;
    }

    @Override
    public String toString() {
        return "Consultant{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", chargePerHour=" + chargePerHour +
                '}';
    }
}
