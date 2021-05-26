package com.tms.speeding.domain.dto;

public class InspectorDto {
    private Integer id;
    private PersonDto person;
    private String badgeNumber;
    private Integer rank;
    private Integer department;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public PersonDto getPerson() {
        return person;
    }
    public void setPerson(PersonDto person) {
        this.person = person;
    }
    public String getBadgeNumber() {
        return badgeNumber;
    }
    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Integer getDepartment() {
        return department;
    }
    public void setDepartment(Integer department) {
        this.department = department;
    }

    
}
