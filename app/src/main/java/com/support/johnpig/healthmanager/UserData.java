package com.support.johnpig.healthmanager;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class UserData extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String account;
    @Column
    public String sex;
    @Column
    public double temperature;
    @Column
    public double weight;
    @Column
    public double heart_rate;
    @Column
    public double high_pressure;
    @Column
    public double low_pressure;
    @Column
    public String createdTime;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeart_rate(double heart_rate) {
        this.heart_rate = heart_rate;
    }

    public void setHigh_pressure(double high_pressure) {
        this.high_pressure = high_pressure;
    }

    public void setLow_pressure(double low_pressure) {
        this.low_pressure = low_pressure;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

}
