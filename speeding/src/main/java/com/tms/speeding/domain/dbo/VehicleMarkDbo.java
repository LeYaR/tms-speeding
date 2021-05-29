package com.tms.speeding.domain.dbo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sv_vehicle_marks")
public class VehicleMarkDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @OneToMany(mappedBy = "mark")
    private List<VehicleModelDbo> models;

    public VehicleMarkDbo() {

    }

    public VehicleMarkDbo(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VehicleModelDbo> getModels() {
        return models;
    }

    public void setModels(List<VehicleModelDbo> models) {
        this.models = models;
    }

}
