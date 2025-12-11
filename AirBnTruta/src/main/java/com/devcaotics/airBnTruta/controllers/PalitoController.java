package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Palito;
import com.devcaotics.airBnTruta.model.repositories.Facade;

@Controller
@RequestMapping("/palito")
public class PalitoController {

    private String msg;

    @Autowired
    private Facade facade;

    @Autowired 
    private com.devcaotics.airBnTruta.misc.FormHandler formHandler;
    
    @PostMapping("/save")
    public String save(Model m, @RequestParam Map<String, String> formData) {
        
        /*String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        String desc = request.getParameter("desc");

        palito s = new palito();

        s.setDescricao(desc);
        s.setNome(nome);
        s.setTipo(tipo);*/

        Palito p = null;

        //try {
          //  if(s.getCodigo() == 0){
          //      Facade.getCurrentInstance().create(s); 
          //  }else{
          //      this.facade.update(s);  
           // }
           // this.msg="operação realizada com sucesso!";
       // } catch (SQLException e) {
            // TODO Auto-generated catch block
        //    e.printStackTrace();
      //  }

        // return "redirect:/palito";
        try {
        // Pega o ID (código) do formulário. Se for um formulário de edição, ele estará lá.
        String codigoString = formData.get("codigo"); 
        
        if (codigoString != null && !codigoString.isEmpty() && !codigoString.equals("0")) {
            // --- LÓGICA DE EDIÇÃO (UPDATE) ---
            int codigo = Integer.parseInt(codigoString);
            
            // 1. BUSCA o objeto EXISTENTE (sem isso, o update falha ou salva dados incompletos)
            p = this.facade.readPalito(codigo); 
            
            if (p == null) {
                throw new Exception("Serviço não encontrado para o código: " + codigo);
            }
            
            // 2. PREENCHE o objeto existente usando o método bindExisting
            // (Este método é essencial para o UPDATE genérico, pois mantém campos não-enviados intactos)
            p = formHandler.bindExisting(p, formData); 
            
            // 3. ATUALIZA
            this.facade.update(p); 
            
        } else {            
            // 1. CRIA e PREENCHE um NOVO objeto usando bind()
            p = formHandler.bind(Palito.class, formData);
            
            // 2. SALVA
            Facade.getCurrentInstance().create(p); 
        }
        
        this.msg = "Operação realizada com sucesso!";
        
    } catch (NumberFormatException e) {
        this.msg = "Erro: O código do serviço não é um número válido.";
        e.printStackTrace();
    } catch (SQLException e) {
        this.msg = "Erro de banco de dados durante a operação.";
        e.printStackTrace();
    } catch (Exception e) {
        // Captura exceções do FormHandler (Reflection/Conversão de tipo) ou a exceção de "Serviço não encontrado".
        this.msg = "Erro ao processar o serviço: " + e.getMessage();
        e.printStackTrace();
    }

    return "redirect:/palito";
    }

    @GetMapping({"","/"})
public String getMethodName(Model m) {
    
    try {
        List<String> ordemHospedagem = formHandler.getAttributeNames(Hospedagem.class);
        m.addAttribute("ordempalito", ordemHospedagem);
        m.addAttribute("palito", new Hospedagem()); 
        //List<palito> palitos = this.facade.readAllpalito();
        //m.addAttribute("palitos", palitos);
        
        m.addAttribute("msg", this.msg);
        this.msg = null;
        
    } catch (Exception e) { 
        m.addAttribute("msg", "Não foi possível recuparar a lista de serviços!");
    }

    return "palito/list";
}
    @GetMapping({"/save","/save/"})
    public String createPage(Model m){
        m.addAttribute("palito", new com.devcaotics.airBnTruta.model.entities.Palito());
    //List<String> ordempalito = List.of("nome", "tipo", "codigo", "descricao"); 
    //m.addAttribute("ordempalito", ordempalito); // Adiciona a lista ao Model

    //m.addAttribute("palito", new palito());
    return "palito/Cadastropalito";
}
    
    @GetMapping("/save/{id}")
    public String getUpdate(Model m,@PathVariable("id") int id) {

        List<Palito> palitos;
        try {
            List<String> ordempalito = formHandler.getAttributeNames(Palito.class);
            m.addAttribute("ordempalito", ordempalito);
            palitos = this.facade.readAllPalito();
            m.addAttribute("palito", this.facade.readPalito(id));
            
            m.addAttribute("palitos", palitos);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao carregar o serviço para edição: " + e.getMessage());
        }
        

        return "palito/list";
    }

    @GetMapping("/delete/")
    public String getDelete(Model m,@RequestParam int id) {

        try {
            this.facade.deletePalito(id);

            this.msg = "palito deletado com sucesso!";
        } catch (SQLException e) {
            this.msg = "Problema ao deletar o palito!";
        }

        return "redirect:/palito";
    }
    
    

}