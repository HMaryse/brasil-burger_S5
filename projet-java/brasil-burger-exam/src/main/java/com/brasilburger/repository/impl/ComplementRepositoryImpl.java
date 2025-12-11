package com.brasilburger.repository.impl;

import com.brasilburger.config.database.Database;
import com.brasilburger.entity.Complement;
import com.brasilburger.repository.ComplementRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplementRepositoryImpl implements ComplementRepository {

    @Override
    public Complement save(Complement complement) {
        String sql = "INSERT INTO complement (nom, prix, image_url, etat) VALUES (?, ?, ?, 'DISPONIBLE') RETURNING id";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                complement.setId(rs.getInt("id"));
            }
            return complement;

        } catch (Exception e) {
            throw new RuntimeException("Erreur save complement : " + e.getMessage());
        }
    }

    @Override
    public Complement update(Complement complement) {
        String sql = "UPDATE complement SET nom=?, prix=?, image_url=?, etat=?::etat_type WHERE id=?";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());
            stmt.setString(4, complement.getEtat());
            stmt.setInt(5, complement.getId());

            stmt.executeUpdate();
            return complement;

        } catch (Exception e) {
            throw new RuntimeException("Erreur update complement : " + e.getMessage());
        }
    }

    @Override
    public List<Complement> findAll() {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT * FROM complement ORDER BY id DESC";

        try (Connection con = Database.getDataSource().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setEtat(rs.getString("etat"));
                list.add(complement);
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException("Erreur findAll complement : " + e.getMessage());
        }
    }

    @Override
    public Optional<Complement> findById(int id) {
        String sql = "SELECT * FROM complement WHERE id=?";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setEtat(rs.getString("etat"));
                return Optional.of(complement);
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erreur findById complement : " + e.getMessage());
        }
    }
}
