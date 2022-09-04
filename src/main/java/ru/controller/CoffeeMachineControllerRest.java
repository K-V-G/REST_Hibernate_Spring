package ru.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import ru.models.coffeeMachine;
import ru.service.coffeeMachineService;

import javax.validation.Valid;
import java.beans.IntrospectionException;
import java.util.List;


@RestController
public class CoffeeMachineControllerRest {

    @Autowired
    private coffeeMachineService coffeeMachineService;

    @RequestMapping(value = "/coffeeMachine", method = RequestMethod.POST,
            produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody coffeeMachine coffeeMachine,
                                     UriComponentsBuilder uriComponentsBuilder) {
        coffeeMachineService.saveCoffeeMachine(coffeeMachine);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("coffeeMachine/{id}")
                .buildAndExpand(coffeeMachine.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/coffeeMachine", method = RequestMethod.GET)
    public ResponseEntity<List<coffeeMachine>> listCoffeeMachine() {
        List<coffeeMachine> coffeeMachineList = coffeeMachineService.findAll();
        if(coffeeMachineList.isEmpty()) {
            return new ResponseEntity<>((HttpStatus.NO_CONTENT));
        }
        return new ResponseEntity<List<coffeeMachine>>(coffeeMachineList, HttpStatus.OK);
    }
    @RequestMapping(value = "/coffeeMachine/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<coffeeMachine> get(@PathVariable("id") Integer id) {
        coffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachine(id);
        if (coffeeMachine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coffeeMachine, HttpStatus.OK);
    }

    @RequestMapping(value = "/coffeeMachine/{id}", method = RequestMethod.PUT,
            produces="application/json", consumes="application/json")
    public ResponseEntity<coffeeMachine> update(@PathVariable("id")Integer id,
                                                @RequestBody coffeeMachine coffeeMachine) {
        coffeeMachine coffeeMachine1 = coffeeMachineService.getCoffeeMachine(id);
        coffeeMachine1.setAmountOfCoffee(coffeeMachine.getAmountOfCoffee());
        coffeeMachine1.setSortOfCoffee(coffeeMachine.getSortOfCoffee());
        coffeeMachine1.setKindOfMilk(coffeeMachine.getKindOfMilk());
        coffeeMachine1.setNameOfTheDrink(coffeeMachine.getNameOfTheDrink());
        coffeeMachine1.setAmountOfDrink(coffeeMachine.getAmountOfDrink());

        coffeeMachineService.saveCoffeeMachine(coffeeMachine1);
        return new ResponseEntity<coffeeMachine>(coffeeMachine1, HttpStatus.OK);
    }

    @RequestMapping(value = "/coffeeMachine/{id}", method = RequestMethod.DELETE,
            produces="application/json")
    public ResponseEntity<coffeeMachine> delete(@PathVariable("id") Integer id) {
        coffeeMachineService.deleteCoffeeMachine(id);
        return new ResponseEntity<coffeeMachine>(HttpStatus.NO_CONTENT);

    }

/*    @GetMapping("/coffeeMachine/mainPage")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @GetMapping("/coffeeMachine/index")
    public ModelAndView listCoffeeMachine(Model model) {
        List<coffeeMachine> coffeeMachineList = coffeeMachineService.findAll();
        model.addAttribute("coffeeMachine", coffeeMachineList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/coffeeMachine/indexRequest")
    public ModelAndView listCoffeeMachineIndexRequest(Model model) {
        List<coffeeMachine> coffeeMachineList = coffeeMachineService.findAllReqest();
        model.addAttribute("coffeeMachine", coffeeMachineList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("indexReqest");
        return modelAndView;
    }

    @GetMapping("/coffeeMachine/index/{id}")
    public ModelAndView showOneCoffeeMachine(@PathVariable("id") int id, Model model) {
        coffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachine(id);
        model.addAttribute("coffeeMachine", coffeeMachine);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("show");
        return modelAndView;
    }

    @GetMapping("/coffeeMachine/new")
    public ModelAndView saveCoffeeMachine(@ModelAttribute("coffeeMachine") coffeeMachine coffeeMachine) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new");
        return modelAndView;
    }

    @RequestMapping(value = "/coffeeMachine/mainPage", method = RequestMethod.POST)
    public String create(@ModelAttribute("coffeeMachine") @Valid coffeeMachine coffeeMachine,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";

        coffeeMachineService.saveCoffeeMachine(coffeeMachine);
        return "redirect:/coffeeMachine/mainPage";
    }*/

}
