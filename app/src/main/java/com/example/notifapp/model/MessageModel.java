package com.example.notifapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Messages")
public class MessageModel {

    @PrimaryKey(autoGenerate = true)
    private Integer Sampleid;

    @SerializedName("benutzername")
    @Expose
    private String benutzername;

    @SerializedName("nachricht")
    @Expose
    private String nachricht;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("severity")
    @Expose
    private String severity;

    @SerializedName("read_time")
    @Expose
    private String read_time;


    @SerializedName("id")
    @Expose
    private Integer id;

    // Getters and Setters

    public MessageModel(String benutzername, String nachricht, String time, String severity, String read_time, Integer id) {
        this.benutzername = benutzername;
        this.nachricht = nachricht;
        this.time = time;
        this.severity = severity;
        this.read_time = read_time;
        this.id = id;
    }

    public Integer getSampleid() {
        return Sampleid;
    }

    public void setSampleid(Integer sampleid) {
        Sampleid = sampleid;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRead_time() {
        return read_time;
    }

    public void setRead_time(String read_time) {
        this.read_time = read_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
