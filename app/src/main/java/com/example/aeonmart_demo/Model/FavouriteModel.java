package com.example.aeonmart_demo.Model;

public class FavouriteModel {
    String ImageFav;
    String NameFav;
    String id;

    public FavouriteModel() {
    }

    public FavouriteModel(String imageFav, String nameFav, String id) {
        ImageFav = imageFav;
        NameFav = nameFav;
        this.id = id;
    }

    public String getImageFav() {
        return ImageFav;
    }

    public void setImageFav(String imageFav) {
        ImageFav = imageFav;
    }

    public String getNameFav() {
        return NameFav;
    }

    public void setNameFav(String nameFav) {
        NameFav = nameFav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
