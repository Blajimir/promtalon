package ru.promtalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by blajimir on 08.02.2017.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(){
      return "index.html";
    }
}
