package com.example.superhelte_v5.dto;

import java.util.List;

public class SuperheroPowerDTO {
    private String heroName;
    private List<String> heroPowerList;

    public SuperheroPowerDTO(String heroName, List<String> heroPowerList) {
        this.heroName = heroName;
        this.heroPowerList = heroPowerList;
    }

    public void addHeroPower(String superpower){
        heroPowerList.add(superpower);
    }

    public String getHeroName() {
        return heroName;
    }


    public List<String> getHeroPowerList() {
        return heroPowerList;
    }


}
