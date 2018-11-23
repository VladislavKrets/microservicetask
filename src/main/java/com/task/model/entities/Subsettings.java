package com.task.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subsettings")
public class Subsettings {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "table_item1")
    private Date tableItem1;

    @Column(name = "table_item2")
    private Date tableItem2;

    @Column(name = "setting_id")
    @JoinColumn(name = "setting_id", nullable = false, insertable = false, updatable = false)
    private Integer settingId;

    @ManyToOne
    private Settings settings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTableItem1() {
        return tableItem1;
    }

    public void setTableItem1(Date tableItem1) {
        this.tableItem1 = tableItem1;
    }

    public Date getTableItem2() {
        return tableItem2;
    }

    public void setTableItem2(Date tableItem2) {
        this.tableItem2 = tableItem2;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }
}
