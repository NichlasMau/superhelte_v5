package com.example.superhelte_v5.repositories;
import com.example.superhelte_v5.dto.HeroRealCreationYearDTO;
import com.example.superhelte_v5.dto.SuperheroPowerDTO;
import com.example.superhelte_v5.dto.SuperheroFormDTO;
import java.util.List;

public interface Irepository {
        List<HeroRealCreationYearDTO> getAllSuperheroes();
        SuperheroPowerDTO getHeroPower(String name);
        List<String> getCity();
        List<String> getPowers();
        void addSuperHero(SuperheroFormDTO form);
        void editHero();

    }

