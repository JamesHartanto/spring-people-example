package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesHartanto on 4/13/17.
 */
@Controller
public class PeopleController {

    @Autowired
    PeopleRepository peopleRepository;

    @GetMapping("/")
    public String listPeople(Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("search",search);
        model.addAttribute("listPeople",peopleRepository.listPeople(search));
        return "index";
    }

    @GetMapping("/personForm")
    public String personForm(Model model, Integer personId){
        if (personId == null){
            // create a person - from the add person button
            model.addAttribute("person",new Person());
        } else{
            // edit a person - from the action edit button
            model.addAttribute("person",peopleRepository.findPerson(personId));
        }

        return "personForm";
    }

    @GetMapping("/sortFirstName")
    public String sortFirstName(List<Person> sortList){
        List<String> name = new ArrayList<>();
        for (int x = 0; x < sortList.size(); x = x + 1){
            name.add(sortList.get(x).getFirstName());
        }
        java.util.Collections.sort(name);
        return "redirect:/";
    }

    @GetMapping("/sortLastName")
    public String sortLastName(List<Person> sortList){
        List<String> name = new ArrayList<>();
        for (int x = 0; x < sortList.size(); x = x + 1){
            name.add(sortList.get(x).getLastName());
        }
//        java.util.Collections.sort(name);
        return "redirect:/";
    }

    @PostMapping("/savePerson")
    public String savePerson(Person person){

        peopleRepository.savePerson(person);

        return "redirect:/";
    }
}
