package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/hospedeiro")
public class HospedeiroController {

    @Autowired
    private Facade facade;
    private String msg = null;
    @Autowired
    private HttpSession session;
    @Autowired 
    private com.devcaotics.airBnTruta.misc.FormHandler formHandler;

    @GetMapping({"/",""})
    public String init(Model m) {

        if(session.getAttribute("hospedeiroLogado") != null){
            return "hospedeiro/index";
        }

        m.addAttribute("hospedeiro", new Hospedeiro());
        m.addAttribute("msg", this.msg);
        this.msg=null;
        return "hospedeiro/login";
    }

    @PostMapping("/save")
    public String newHospedeiro(Model m, @RequestParam Map<String, String> formData) {
        //TODO: process POST request
        
        try {
            Hospedeiro h = formHandler.bind(Hospedeiro.class, formData);

            facade.create(h);
            this.msg="Parabéns! Seu cadastro foi realizado com sucesso! Agora faça o login, por favor, meu querido hospedeiro de minha vida!";

        } catch (SQLException e) {
            this.msg="Chorou! Não foi possível criar seu cadastro. Rapa daqui, fi da peste!";
        } catch (Exception e) {
        // Se a exceção for do Reflection/Bind (ex: erro de conversão de tipo)
        e.printStackTrace();
        this.msg="Erro de preenchimento do formulário: " + e.getMessage();
    }

        return "redirect:/hospedeiro";
    }

    @PostMapping("/login")
    public String login(Model m,@RequestParam String vulgo,
        @RequestParam String senha
    ) {
        //TODO: process POST request
        
        try {
            Hospedeiro logado = facade.loginHospedeiro(vulgo, senha);
            if(logado == null){
                this.msg = "Erro ao Logar";
                return "redirect:/hospedeiro";
            }
            session.setAttribute("hospedeiroLogado", logado);
            return "hospedeiro/index";
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            this.msg = "Erro ao logar!";
            return "redirect:/hospedeiro";
        }

        
    }
    
    
    @GetMapping("/logout")
    public String logout(Model m) {

        session.removeAttribute("hospedeiroLogado");;

        return "redirect:/hospedeiro";
    }
    

}
