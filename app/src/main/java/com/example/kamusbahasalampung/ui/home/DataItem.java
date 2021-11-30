package com.example.kamusbahasalampung.ui.home;

class DataItem {
    private int imageResource;
    private String text2;
    private String text3;
    private String text4;
    private String keyFB;
    public DataItem(int imageResource, String text2, String text3, String text4, String keyFB) {
        this.imageResource = imageResource;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.keyFB = keyFB;
    }
    public int getImageResource() {
        return imageResource;
    }

    public String getText2() {
        return text2;
    }
    public String getText3() {
        return text3;
    }
    public String getText4() {
        return text4;
    }
    public String getKeyFB() {
        return keyFB;
    }

}
