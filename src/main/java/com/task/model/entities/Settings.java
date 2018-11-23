package com.task.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @Column(name = "setting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "item1")
    private Double item1;

    @OneToMany(mappedBy = "settings")
    private Set<Subsettings> subsettings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getItem1() {
        return item1;
    }

    public void setItem1(Double item1) {
        this.item1 = item1;
    }


    public Set<Subsettings> getSubsettings() {
        return subsettings;
    }

    public void setSubsettings(Set<Subsettings> subsettings) {
        this.subsettings = subsettings;
    }
}
