package com.tms.speeding.dbo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sv_vehicle_models")
public class VehicleModelDbo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_id", referencedColumnName = "id", nullable = false)
    private VehicleMarkDbo mark;

    @OneToMany(mappedBy = "model")
    private List<VehicleDbo> vehicles;

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

    public VehicleMarkDbo getMark() {
        return mark;
    }

    public void setMark(VehicleMarkDbo mark) {
        this.mark = mark;
    }

    public List<VehicleDbo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDbo> vehicles) {
        this.vehicles = vehicles;
    }

    
}
