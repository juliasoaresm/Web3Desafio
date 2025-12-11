package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.entities.Palito;
import java.sql.SQLException;
import java.util.List;


@org.springframework.stereotype.Repository
public class Facade {

    private static Facade myself = null;

    private Repository<Interesse, Integer> rInteresse;
    private Repository<Servico,Integer> rServico;
    private Repository<Fugitivo, Integer> rFugitivo;
    private Repository<Hospedeiro, Integer> rHospedeiro;
    private Repository<Hospedagem, Integer> rHospedagem;
    private Repository<Palito, Integer> rPalito;

    public Facade(){
        rServico = new ServicoRepository();
        this.rFugitivo = new FugitivoRepository();
        this.rHospedeiro = new HospedeiroRepository();
        this.rHospedagem = new HospedagemRepository();
        this.rInteresse = new InteresseRepository();
        this.rPalito = new PalitoRepository();
    }

    public static Facade getCurrentInstance(){

        if(myself == null)
            myself = new Facade();

        return myself;
    }

    public void create(Servico s) throws SQLException{
        this.rServico.create(s);
    }

    public void update(Servico s) throws SQLException{
        this.rServico.update(s);
    }

    public Servico readServico(int codigo) throws SQLException{
        return this.rServico.read(codigo);
    }

    public void deleteServico(int codigo) throws SQLException{
        this.rServico.delete(codigo);
    }

    public List<Servico> readAllServico() throws SQLException{
        return this.rServico.readAll();
    }

    public void create(Palito p) throws SQLException{
        this.rPalito.create(p);
    }

    public void update(Palito p) throws SQLException{
        this.rPalito.update(p);
    }

    public Palito readPalito(int codigo) throws SQLException{
        return this.rPalito.read(codigo);
    }

    public void deletePalito(int codigo) throws SQLException{
        this.rPalito.delete(codigo);
    }

    public List<Palito> readAllPalito() throws SQLException{
        return this.rPalito.readAll();
    }

    public void create(Fugitivo f) throws SQLException{
        this.rFugitivo.create(f);
    }

    public void update(Fugitivo f) throws SQLException{
        this.rFugitivo.update(f);
    }

    public List<Fugitivo> readAllFugitivo() throws SQLException{
        return this.rFugitivo.readAll();
    }

    public Fugitivo readFugitivo(int codigo) throws SQLException{
        return this.rFugitivo.read(codigo);
    }

    public void deleteFugitivo(int codigo) throws SQLException{
        this.rFugitivo.delete(codigo);
    }

    public Fugitivo loginFugitivo(String vulgo, String senha) throws SQLException{
        return ((FugitivoRepository)this.rFugitivo).login(vulgo,senha);
    }

    public void create(Hospedeiro h) throws SQLException{
        this.rHospedeiro.create(h);
    }

    public Hospedeiro loginHospedeiro(String vulgo, String senha) throws SQLException{
        return ((HospedeiroRepository)this.rHospedeiro).login(vulgo, senha);

    }

    public void create(Hospedagem h) throws SQLException{
        this.rHospedagem.create(h);
    }

    public Hospedagem readHospedagem(int codigo) throws SQLException{
        return this.rHospedagem.read(codigo);
    }

    public List<Hospedagem> filterByHospedagemAvailable() throws SQLException{
        return ((HospedagemRepository)this.rHospedagem).filterByAvailable();
    }

    public void create(Interesse i) throws SQLException{
    this.rInteresse.create(i);
    }

    public List<Interesse> readAllInteresse() throws SQLException{
    return this.rInteresse.readAll();
    }

}
