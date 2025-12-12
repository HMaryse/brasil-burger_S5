package com.brasilburger.repository.impl;

import com.brasilburger.config.database.Database;
import com.brasilburger.entity.Menu;
import com.brasilburger.repository.MenuRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuRepositoryImpl implements MenuRepository {

    @Override
    public Menu save(Menu menu) {
        String sql = "INSERT INTO menu (nom, prix, image_url, etat) VALUES (?, ?, ?, ?::etat_type) RETURNING id;";

        try (Connection con = Database.getDataSource().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, menu.getNom());
            stmt.setDouble(2, menu.getPrix());
            stmt.setString(3, menu.getImageUrl());
            stmt.setString(4, menu.getEtat());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                menu.setId(rs.getInt("id"));
            }

            return menu;

        } catch (Exception e) {
            throw new RuntimeException("Erreur save menu : " + e.getMessage());
        }
    }

    @Override
    public Menu update(Menu menu) {
        String sql = "UPDATE menu SET nom = ?, etat = ?::etat_type WHERE id = ?";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, menu.getNom());
            stmt.setString(2, menu.getEtat());
            stmt.setInt(3, menu.getId());

            stmt.executeUpdate();
            return menu;

        } catch (Exception e) {
            throw new RuntimeException("Erreur update menu : " + e.getMessage());
        }
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu ORDER BY id DESC";

        try (Connection con = Database.getDataSource().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setPrix(rs.getDouble("prix"));
                m.setImageUrl(rs.getString("image_url"));
                m.setEtat(rs.getString("etat"));
                list.add(m);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur findAll menu : " + e.getMessage());
        }

        return list;
    }

    @Override
    public Optional<Menu> findById(int id) {
        String sql = "SELECT * FROM menu WHERE id = ?";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setPrix(rs.getDouble("prix"));
                m.setImageUrl(rs.getString("image_url"));
                m.setEtat(rs.getString("etat"));
                return Optional.of(m);
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erreur findById menu : " + e.getMessage());
        }
    }
}
