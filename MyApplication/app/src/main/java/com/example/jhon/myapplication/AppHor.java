package com.example.jhon.myapplication;

public class AppHor {
    private String name;
    private String imageUrl;
    private String size;
    private String downloadUrl ;

    public AppHor(String name, String imageUrl, String size,String downloadUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.size = size;
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSize() {
        return size;
    }
}
