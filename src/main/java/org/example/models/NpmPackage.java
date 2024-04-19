package org.example.models;

public class NpmPackage {
    private Number id;
    private String name;
    private Number weeklyDownloads;
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

    public Number getWeeklyDownloads() {
        return weeklyDownloads;
    }

    public void setWeeklyDownloads(Number weeklyDownloads) {
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

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }
}
