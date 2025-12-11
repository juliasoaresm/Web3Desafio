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

import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;

@Controller
@RequestMapping("/servico")
public class ServicoController {

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

        Servico s = new Servico();

        s.setDescricao(desc);
        s.setNome(nome);
        s.setTipo(tipo);*/

        Servico s = null;

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

        // return "redirect:/servico";
        try {
        // Pega o ID (código) do formulário. Se for um formulário de edição, ele estará lá.
        String codigoString = formData.get("codigo"); 
        
        if (codigoString != null && !codigoString.isEmpty() && !codigoString.equals("0")) {
            // --- LÓGICA DE EDIÇÃO (UPDATE) ---
            int codigo = Integer.parseInt(codigoString);
            
            // 1. BUSCA o objeto EXISTENTE (sem isso, o update falha ou salva dados incompletos)
            s = this.facade.readServico(codigo); 
            
            if (s == null) {
                throw new Exception("Serviço não encontrado para o código: " + codigo);
            }
            
            // 2. PREENCHE o objeto existente usando o método bindExisting
            // (Este método é essencial para o UPDATE genérico, pois mantém campos não-enviados intactos)
            s = formHandler.bindExisting(s, formData); 
            
            // 3. ATUALIZA
            this.facade.update(s); 
            
        } else {
            // --- LÓGICA DE CRIAÇÃO (CREATE) ---
            
            // 1. CRIA e PREENCHE um NOVO objeto usando bind()
            s = formHandler.bind(Servico.class, formData);
            
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

    return "redirect:/servico";
    }

    @GetMapping({"","/"})
public String getMethodName(Model m) {
    
    try {
        List<String> ordemTabelaServico = List.of("codigo", "nome", "tipo", "descricao");
        Object entidadeParaFormulario = new com.devcaotics.airBnTruta.model.entities.Hospedeiro();
        m.addAttribute("servico", entidadeParaFormulario);
        List<String> ordemParaFormulario = formHandler.getAttributeNames(entidadeParaFormulario.getClass());        
        m.addAttribute("ordemHospedeiroTeste", ordemParaFormulario);
        m.addAttribute("ordemServico", ordemTabelaServico); 
        List<Servico> servicos = this.facade.readAllServico();
        m.addAttribute("servicos", servicos);
        
        m.addAttribute("msg", this.msg);
        this.msg = null;
        
    } catch (SQLException e) {
        m.addAttribute("msg", "Não foi possível recuparar a lista de serviços!");
    }

    return "servico/list";
}
    @GetMapping({"/save","/save/"})
    public String createPage(Model m){
    // ...
    List<String> ordemServico = List.of("nome", "tipo", "codigo", "descricao"); 
    m.addAttribute("ordemServico", ordemServico); // Adiciona a lista ao Model

    m.addAttribute("servico", new Servico());
    return "servico/CadastroServico";
}
    
    @GetMapping("/save/{id}")
    public String getUpdate(Model m,@PathVariable("id") int id) {

        List<String> ordemServico = List.of("nome", "tipo", "codigo", "descricao"); 
        m.addAttribute("ordemServico", ordemServico);

        List<Servico> servicos;
        try {
            servicos = this.facade.readAllServico();
            m.addAttribute("servico", this.facade.readServico(id));
            
            m.addAttribute("servicos", servicos);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        return "servico/list";
    }

    @GetMapping("/delete/")
    public String getDelete(Model m,@RequestParam int id) {

        try {
            this.facade.deleteServico(id);

            this.msg = "Serviço deletado com sucesso!";
        } catch (SQLException e) {
            this.msg = "Problema ao deletar o serviço!";
        }

        return "redirect:/servico";
    }
    
    

}
