package com.devcaotics.airBnTruta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.airBnTruta.misc.GenericFormHandler;
import com.devcaotics.airBnTruta.model.repositories.Facade;

@Controller
@RequestMapping("/crud")
public class GenericController {

    private final String BASE_PACKAGE = "com.devcaotics.airBnTruta.model.entities.";

    @Autowired
    private Facade facade;

    @Autowired
    private GenericFormHandler FormHandler;
    
    private String msg;    


    @GetMapping({"/{className}/list", "/{className}"})
    public String listGeneric(Model m, @PathVariable("className") String className) {

        try {
            // 💡 Passo 1: Converter o nome da string para a classe Java
            // Assume que as entidades (Servico, Fugitivo) estão em 'com.devcaotics.airBnTruta.model.entities'
            Class<?> entityClass = Class.forName(BASE_PACKAGE + className);
            
            // 💡 Passo 2: Buscar a lista de entidades (Você precisará de um método genérico no Facade ou Repository)
            // Por enquanto, usaremos um placeholder. Você precisará adaptar isso!
            // Exemplo: List<?> entities = this.facade.readAll(entityClass); 
            
            // Usando o readAllServico como placeholder temporário até ter o Facade genérico:
            if (className.equalsIgnoreCase("servico")) {
                List<?> entities = this.facade.readAllServico();
                m.addAttribute("entities", entities);
                // Cria um objeto vazio para o formulário (necessário para o modal)
                m.addAttribute("entity", entityClass.getDeclaredConstructor().newInstance()); 
            } else {
                // ... Lógica para outras entidades ...
                m.addAttribute("msg", "Entidade " + className + " não implementada no Facade.");
            }
            
            // 💡 Passo 3: Adicionar o GenericFormHandler e a ClassName para o Thymeleaf
            m.addAttribute("formHandler", this.FormHandler); 
            m.addAttribute("className", className);

        } catch (ClassNotFoundException e) {
            m.addAttribute("msg", "Erro: Classe " + className + " não encontrada.");
            return "error";
        } catch (Exception e) {
            m.addAttribute("msg", "Erro ao carregar dados: " + e.getMessage());
        }

        // Deve retornar o template genérico que criaremos no Dia 2
        return "crud/generic_list"; 
    }




}
