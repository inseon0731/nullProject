package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-03-06.
 */

public class Buy {
    private String nameExhibit, nameMuseum;
    private int image;
    private boolean isDorok, isGuide;

    public Buy(String nameExhibit, String nameMuseum, int image, boolean isDorok, boolean isGuide) {
        this.nameExhibit = nameExhibit;
        this.nameMuseum = nameMuseum;
        this.image = image;
        this.isDorok = isDorok;
        this.isGuide = isGuide;
    }

    public String getNameExhibit() {
        return this.nameExhibit;
    }
    public String getNameMuseum() {
        return this.nameMuseum;
    }
    public int getImage() {
        return this.image;
    }
    public boolean Dorok() {return this.isDorok;}
    public boolean Guide() {return this.isGuide;}
}
