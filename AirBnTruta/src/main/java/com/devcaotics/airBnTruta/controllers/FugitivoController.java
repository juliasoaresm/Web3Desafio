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
import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.repositories.Facade;

@Controller
@RequestMapping("/fugitivo")
public class FugitivoController {

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

        fugitivo s = new fugitivo();

        s.setDescricao(desc);
        s.setNome(nome);
        s.setTipo(tipo);*/

        Fugitivo s = null;

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

        // return "redirect:/fugitivo";
        try {
        // Pega o ID (código) do formulário. Se for um formulário de edição, ele estará lá.
        String codigoString = formData.get("codigo"); 
        
        if (codigoString != null && !codigoString.isEmpty() && !codigoString.equals("0")) {
            // --- LÓGICA DE EDIÇÃO (UPDATE) ---
            int codigo = Integer.parseInt(codigoString);
            
            // 1. BUSCA o objeto EXISTENTE (sem isso, o update falha ou salva dados incompletos)
            s = this.facade.readFugitivo(codigo); 
            
            if (s == null) {
                throw new Exception("Serviço não encontrado para o código: " + codigo);
            }
            
            // 2. PREENCHE o objeto existente usando o método bindExisting
            // (Este método é essencial para o UPDATE genérico, pois mantém campos não-enviados intactos)
            s = formHandler.bindExisting(s, formData); 
            
            // 3. ATUALIZA
            this.facade.update(s); 
            
        } else {            
            // 1. CRIA e PREENCHE um NOVO objeto usando bind()
            s = formHandler.bind(Fugitivo.class, formData);
            
            // 2. SALVA
            Facade.getCurrentInstance().create(s); 
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

    return "redirect:/fugitivo";
    }

    @GetMapping({"","/"})
public String getMethodName(Model m) {
    
    try {
        List<String> ordemHospedagem = formHandler.getAttributeNames(Hospedagem.class);
        m.addAttribute("ordemfugitivo", ordemHospedagem);
        m.addAttribute("fugitivo", new Hospedagem()); 
        //List<fugitivo> fugitivos = this.facade.readAllfugitivo();
        //m.addAttribute("fugitivos", fugitivos);
        
        m.addAttribute("msg", this.msg);
        this.msg = null;
        
    } catch (Exception e) { 
        m.addAttribute("msg", "Não foi possível recuparar a lista de serviços!");
    }

    return "fugitivo/list";
}
    @GetMapping({"/save","/save/"})
    public String createPage(Model m){
        m.addAttribute("fugitivo", new com.devcaotics.airBnTruta.model.entities.Fugitivo());
    //List<String> ordemfugitivo = List.of("nome", "tipo", "codigo", "descricao"); 
    //m.addAttribute("ordemfugitivo", ordemfugitivo); // Adiciona a lista ao Model

    //m.addAttribute("fugitivo", new fugitivo());
    return "fugitivo/Cadastrofugitivo";
}
    
    @GetMapping("/save/{id}")
    public String getUpdate(Model m,@PathVariable("id") int id) {

        List<Fugitivo> fugitivos;
        try {
            List<String> ordemfugitivo = formHandler.getAttributeNames(Fugitivo.class);
            m.addAttribute("ordemfugitivo", ordemfugitivo);
            fugitivos = this.facade.readAllFugitivo();
            m.addAttribute("fugitivo", this.facade.readFugitivo(id));
            
            m.addAttribute("fugitivos", fugitivos);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao carregar o serviço para edição: " + e.getMessage());
        }
        

        return "fugitivo/list";
    }

    @GetMapping("/delete/")
    public String getDelete(Model m,@RequestParam int id) {

        try {
            this.facade.deleteFugitivo(id);

            this.msg = "Fugitivo deletado com sucesso!";
        } catch (SQLException e) {
            this.msg = "Problema ao deletar o fugitivo!";
        }

        return "redirect:/fugitivo";
    }
    
    

}