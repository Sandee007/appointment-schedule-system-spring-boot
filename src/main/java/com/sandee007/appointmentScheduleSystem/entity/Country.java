//package com.sandee007.appointmentScheduleSystem.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import java.util.List;
//
//@Entity
//@Table(name = "countries")
//@Getter
//@Setter
//public class Country {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToMany( cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(
//            name = "consultant_countries",
//            joinColumns = @JoinColumn(name = "country_id"),
//            inverseJoinColumns = @JoinColumn(name = "consultant_id")
//    )
//    private List<Consultant> consultants;
//
//    public Country(){}
//
//    public Country(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Country{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
//}
