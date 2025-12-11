package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Interesse; 
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/interesse")
public class InteresseController { 

    @Autowired
    private Facade facade;
    private String msg = null;
    @Autowired
    private HttpSession session;
    @Autowired 
    private com.devcaotics.airBnTruta.misc.FormHandler formHandler;

    @GetMapping({"/",""})
    public String init(Model m) {

        if(session.getAttribute("interesseLogado") != null){
            return "interesse/index";
        }
        
        try {
            List<String> ordemInteresse = formHandler.getAttributeNames(Interesse.class);
            m.addAttribute("ordeminteresse", ordemInteresse); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        m.addAttribute("interesse", new Interesse());
        m.addAttribute("msg", this.msg);
        this.msg=null;
        return "interesse/login";
    }

    @PostMapping("/save")
    public String newinteresse(Model m, @RequestParam Map<String, String> formData) {
        //TODO: process POST request
        
        try {
            Interesse i = formHandler.bind(Interesse.class, formData); 

            facade.create(i);
            this.msg="Parabéns! Seu cadastro foi realizado com sucesso! Agora faça o login, por favor, meu querido hospedeiro de minha vida!";

        } catch (SQLException e) {
            this.msg="Chorou! Não foi possível criar seu cadastro. Rapa daqui, fi da peste! (Erro de Banco de Dados)";
        } catch (Exception e) {
        e.printStackTrace();
        this.msg="Erro de preenchimento do formulário: " + e.getMessage();
    }

        return "redirect:/interesse";
    }
    

}