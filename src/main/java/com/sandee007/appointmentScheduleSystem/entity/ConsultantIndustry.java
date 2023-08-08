package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consultant_industries")
public class ConsultantIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    @OneToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    public ConsultantIndustry(){}

    public ConsultantIndustry(Consultant consultant, Industry industry) {
        this.consultant = consultant;
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "ConsultantIndustry{" +
                "id=" + id +
                '}';
    }
}
