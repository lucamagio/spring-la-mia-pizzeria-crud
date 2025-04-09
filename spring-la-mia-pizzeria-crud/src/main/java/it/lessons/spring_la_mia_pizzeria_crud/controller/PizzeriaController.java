package it.lessons.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.lessons.spring_la_mia_pizzeria_crud.model.Pizza;
import it.lessons.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/pizzeria")

public class PizzeriaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(name="keyword", required = false) String nome) {

        List<Pizza> result;
        if (nome != null && !nome.isBlank()) {
            result = pizzaRepository.findByNomeContainingIgnoreCase(nome);
        } else{
            result = pizzaRepository.findAll();
        }

        model.addAttribute("list", result);
        return "index";
    }
    
    @GetMapping("/{id}")
    public String getPizzaById(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> optPizza = pizzaRepository.findById(id);
        if (optPizza.isPresent()) {
            model.addAttribute("pizza", pizzaRepository.findById(id).get());
            return "dettaglioPizze";
        }
        
        model.addAttribute("errorCause", "La pizza ricercata non è presente!");
        return "/error/genericError";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "create";
    }

    @PostMapping("/create")
    public String store(@ModelAttribute("pizza") Pizza formPizza, Model model) {

        pizzaRepository.save(formPizza);

        return "redirect:/pizzeria/";
    }
    
    
}