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
@Table(name="sv_departments")
public class DepartmentDbo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title", length = 100, nullable = false)
    private String title;

    @Column(name="address", length = 100)
    private String address;

    @OneToMany(mappedBy = "department")
    private List<InspectorDbo> inspectors;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<InspectorDbo> getInspectors() {
        return inspectors;
    }

    public void setInspectors(List<InspectorDbo> inspectors) {
        this.inspectors = inspectors;
    }

    
}
