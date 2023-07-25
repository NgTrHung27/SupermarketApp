package com.example.aeonmart_demo.Model;

public class SlideViewModel {
    String Name;
    String Pic;
    public SlideViewModel(){}

    //Contructor

    public SlideViewModel(String name, String pic) {
        Name = name;
        Pic = pic;
    }

    //Get and Set
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }
}
