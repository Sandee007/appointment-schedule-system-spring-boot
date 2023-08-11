package com.sandee007.appointmentScheduleSystem.entity;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = ValidationMessages.REQUIRED)
    @NotNull(message = "Required")
    @Column(name = "lastname")
    private String lastname;

    //    https://www.baeldung.com/spring-data-jpa-query-by-date
//    https://www.baeldung.com/dates-in-thymeleaf
    @NotNull(message = ValidationMessages.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    //    https://stackoverflow.com/questions/3868096/jpa-how-do-i-persist-a-string-into-a-database-field-type-mysql-text
    @NotNull(message = ValidationMessages.REQUIRED)
    @Lob // large object
    @Column(name = "description")
    private String description;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Pattern(regexp = "^[0-9]{10}", message = "Invalid Phone Number")
    @Column(name = "phone", length = 20)
    private String phone;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Min(1)
    @Column(name = "charge_per_hour")
    private int chargePerHour;

//    ******************* TODO **************************
//    @Column(name = "image")
    //    private String image;

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
