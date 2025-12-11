package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Palito;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class PalitoRepository implements Repository<Palito,Integer>{

    protected PalitoRepository(){}

    @Override
    public void create(Palito p) throws SQLException {
        String sql = "INSERT INTO Palito (cumprimento, especie, teste, duracao) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setString(1, p.getCumprimento());
        stmt.setString(2, p.getEspecie());
        stmt.setString(3, p.getTeste());
        stmt.setInt(4, p.getDuracao());

        stmt.execute();
    }

    @Override
    public void update(Palito p) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Palito read(Integer p) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void delete(Integer p) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Palito> readAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

}
