package org.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class NpmPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Long weeklyDownloads;

    private String version;

    private String size;

    private String lastPublish;

    public NpmPackage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeeklyDownloads() {
        return weeklyDownloads;
    }

    public void setWeeklyDownloads(Long weeklyDownloads) {
        this.weeklyDownloads = weeklyDownloads;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastPublish() {
        return lastPublish;
    }

    public void setLastPublish(String lastPublish) {
        this.lastPublish = lastPublish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
