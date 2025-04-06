package it.lessons.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import it.lessons.spring_la_mia_pizzeria_crud.model.Pizza;
import it.lessons.spring_la_mia_pizzeria_crud.repository.PizzaRepository;



@Controller
@RequestMapping("/pizzeria")

public class PizzeriaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<Pizza> result = pizzaRepository.findAll();
        model.addAttribute("list", result);

        return "index";
    }
    

}
