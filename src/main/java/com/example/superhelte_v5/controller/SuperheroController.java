package com.example.superhelte_v5.controller;

import com.example.superhelte_v5.dto.HeroRealCreationYearDTO;
import com.example.superhelte_v5.dto.SuperheroFormDTO;
import com.example.superhelte_v5.dto.SuperheroPowerDTO;
import com.example.superhelte_v5.repositories.Irepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/superhero")
@Controller

public class SuperheroController {
    Irepository irepository;

    public SuperheroController(ApplicationContext context, @Value("${superhero.repository.impl}") String impl) {
        irepository = (Irepository) context.getBean(impl);
    }

    @GetMapping()
    public String getHeroInformation(Model model) {
        List<HeroRealCreationYearDTO> heroList = irepository.getAllSuperheroes();
        model.addAttribute("heroList", heroList);
        return "index";
    }

    @GetMapping("superpower/{name}")
    public String getHeroPowers(@PathVariable String name, Model model) {
        SuperheroPowerDTO superheroPowers = irepository.getHeroPower(name);
        model.addAttribute("name", superheroPowers.getHeroName());
        model.addAttribute("powers", superheroPowers.getHeroPowerList());
        return "powers";
    }

    @GetMapping("/create")
    public String createSuperhero(Model model){
        SuperheroFormDTO superheroForm = new SuperheroFormDTO();
        model.addAttribute("superheroForm", superheroForm);

        List<String> cityList = irepository.getCity();
        model.addAttribute("cityList", cityList);

        List<String> powerList = irepository.getPowers();
        model.addAttribute("powerList", powerList);

        return "addSuperhero";
    }

    @PostMapping("/add")
    public String createSuperhero(@ModelAttribute SuperheroFormDTO superhero){
        irepository.addSuperHero(superhero);
        return "redirect:/superhero";
    }
}
