package it.lessons.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.lessons.spring_la_mia_pizzeria_crud.model.Pizza;
import it.lessons.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






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
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "create";
        }

        pizzaRepository.save(formPizza);

        redirectAttributes.addFlashAttribute("successMessage", "Pizza Created!");
        return "redirect:/pizzeria/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        //Pizza dbPizza = pizzaRepository.findById(formPizza.getId()).get(); 
        //da utilizzare se ci sono dei campi che non vogliamo che cambino e quindi si utilizza per aggiungere un errore
        //if (!dbPizza.getNome().equals(formPizza.getNome())) {
        //    bindingResult.addError(new ObjectError("nome", "Il nome non può essere cambiato"));
        //}
        
        if(bindingResult.hasErrors()){
            return "edit";
        }
        
        pizzaRepository.save(formPizza);

        return "redirect:/pizzeria/";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        
        pizzaRepository.deleteById(id);
        
        return "redirect:/pizzeria/";
    }
    
    
    
    
}