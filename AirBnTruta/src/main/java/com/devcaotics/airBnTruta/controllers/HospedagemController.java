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

import com.devcaotics.airBnTruta.model.entities.Hospedagem; 
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/hospedagem")
public class HospedagemController { 

    @Autowired
    private Facade facade;
    private String msg = null;
    @Autowired
    private HttpSession session;
    @Autowired 
    private com.devcaotics.airBnTruta.misc.FormHandler formHandler;

    @GetMapping({"/",""})
    public String init(Model m) {

        if(session.getAttribute("hospedagemLogado") != null){
            return "hospedagem/index";
        }
        
        try {
            List<String> ordemHospedagem = formHandler.getAttributeNames(Hospedagem.class);
            m.addAttribute("ordemHospedagem", ordemHospedagem); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        m.addAttribute("hospedagem", new Hospedagem());
        m.addAttribute("msg", this.msg);
        this.msg=null;
        return "hospedagem/login";
    }

    @PostMapping("/save")
    public String newHospedagem(Model m, @RequestParam Map<String, String> formData) {
        //TODO: process POST request
        
        try {
            Hospedagem h = formHandler.bind(Hospedagem.class, formData); 

            facade.create(h);
            this.msg="Parabéns! Seu cadastro foi realizado com sucesso! Agora faça o login, por favor, meu querido hospedeiro de minha vida!";

        } catch (SQLException e) {
            this.msg="Chorou! Não foi possível criar seu cadastro. Rapa daqui, fi da peste! (Erro de Banco de Dados)";
        } catch (Exception e) {
        e.printStackTrace();
        this.msg="Erro de preenchimento do formulário: " + e.getMessage();
    }

        return "redirect:/hospedagem";
    }

    // @PostMapping("/login")
    // public String login(Model m,@RequestParam String vulgo,
    //     @RequestParam String senha
    // ) {
    //     //TODO: process POST request
        
    //     try {
    //         // 🚨 CORREÇÃO 7: Nome do método na Facade deve ser camelCase
    //         Hospedagem logado = facade.loginHospedagem(vulgo, senha); 
    //         if(logado == null){
    //             this.msg = "Erro ao Logar";
    //             return "redirect:/hospedagem";
    //         }
    //         session.setAttribute("hospedagemLogado", logado);
    //         return "hospedagem/index";
            
    //     } catch (SQLException e) {
    //         this.msg = "Erro ao logar!";
    //         return "redirect:/hospedagem";
    //     }

        
    // }
    
    
    // @GetMapping("/logout")
    // public String logout(Model m) {

    //     session.removeAttribute("hospedagemLogado");;

    //     return "redirect:/hospedagem";
    // }
    

}