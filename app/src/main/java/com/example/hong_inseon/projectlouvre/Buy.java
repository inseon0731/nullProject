package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-03-06.
 */

public class Buy {
    private String nameExhibit, nameMuseum, image;
    //private int image;
    private boolean isDorok, isGuide;

    @Override
    public String toString() {
        return "Exhibition [" +
                "ex_no='" + nameExhibit + '\'' +
                ", ex_name='" + nameMuseum + '\'' +
                ", ex_theme='" + image + '\'' +
                ", ex_like='" + isDorok + '\'' +
                ", ex_img='" + isGuide + '\'' +
                ']';
    }

    public Buy(String nameExhibit, String nameMuseum, String image, boolean isDorok, boolean isGuide) {
        this.nameExhibit = nameExhibit;
        this.nameMuseum = nameMuseum;
        this.image = image;
        this.isDorok = isDorok;
        this.isGuide = isGuide;
    }
    public Buy() {}

    public void setNameExhibit(String nameExhibit) {
        this.nameExhibit = nameExhibit;
    }

    public void setNameMuseum(String nameMuseum) {
        this.nameMuseum = nameMuseum;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDorok(boolean dorok) {
        isDorok = dorok;
    }

    public void setGuide(boolean guide) {
        isGuide = guide;
    }

    public String getNameExhibit() {
        return this.nameExhibit;
    }
    public String getNameMuseum() {
        return this.nameMuseum;
    }
    public String getImage() {
        return this.image;
    }
    public boolean Dorok() {return this.isDorok;}
    public boolean Guide() {return this.isGuide;}
}
