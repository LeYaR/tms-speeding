package com.tms.speeding.dto;

public class InspectorD {
    private Integer id;
    private PersonD person;
    private String badgeNumber;
    private Integer rank;
    private Integer department;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public PersonD getPerson() {
        return person;
    }
    public void setPerson(PersonD person) {
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
