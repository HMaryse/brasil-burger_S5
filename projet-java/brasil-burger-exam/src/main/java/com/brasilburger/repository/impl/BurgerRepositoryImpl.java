package com.brasilburger.repository.impl;

import com.brasilburger.entity.Burger;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.config.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerRepositoryImpl implements BurgerRepository {

    @Override
    public Burger save(Burger burger) {
        String sql = "INSERT INTO burger (nom, prix, image_url) VALUES (?, ?, ?) RETURNING id;";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl()); // <-- image_url

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                burger.setId(rs.getInt("id"));
            }

            return burger;

        } catch (Exception e) {
            throw new RuntimeException("Erreur save burger : " + e.getMessage());
        }
    }

    @Override
    public Burger update(Burger burger) {
        String sql = "UPDATE burger SET nom=?, prix=?, image_url=?, etat=? WHERE id=?";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.setString(4, burger.getEtat());
            stmt.setInt(5, burger.getId());

            stmt.executeUpdate();
            return burger;

        } catch (Exception e) {
            throw new RuntimeException("Erreur update burger : " + e.getMessage());
        }
    }

    @Override
    public List<Burger> findAll() {
        String sql = "SELECT * FROM burger ORDER BY id DESC;";
        List<Burger> list = new ArrayList<>();

        try (Connection con = Database.getDataSource().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Burger b = new Burger();
                b.setId(rs.getInt("id"));
                b.setNom(rs.getString("nom"));
                b.setPrix(rs.getDouble("prix"));
                b.setImageUrl(rs.getString("image_url")); // <-- image_url
                b.setEtat(rs.getString("etat"));
                list.add(b);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur findAll burger : " + e.getMessage());
        }

        return list;
    }
}
