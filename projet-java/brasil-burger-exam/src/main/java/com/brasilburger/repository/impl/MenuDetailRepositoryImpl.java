package com.brasilburger.repository.impl;

import com.brasilburger.config.database.Database;
import com.brasilburger.repository.MenuDetailRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MenuDetailRepositoryImpl implements MenuDetailRepository {

    @Override
    public void saveMenuBurgers(int menuId, List<Integer> burgerIds) {

        String sql = "INSERT INTO menu_detail (menu_id, burger_id, quantite) VALUES (?, ?, 1)";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (int burgerId : burgerIds) {
                stmt.setInt(1, menuId);
                stmt.setInt(2, burgerId);
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du menu : " + e.getMessage());
        }
    }

    @Override
    public void saveMenuComplements(int menuId, List<Integer> complementIds) {

        String sql = "INSERT INTO menu_complement (menu_id, complement_id, quantite) VALUES (?, ?, 1)";

        try (Connection con = Database.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (int complementId : complementIds) {
                stmt.setInt(1, menuId);
                stmt.setInt(2, complementId);
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des complements du menu : " + e.getMessage());
        }
    }

    @Override
    public void deleteByMenuId(int menuId) {

        String sql1 = "DELETE FROM menu_detail WHERE menu_id = ?";
        String sql2 = "DELETE FROM menu_complement WHERE menu_id = ?";

        try (Connection con = Database.getDataSource().getConnection()) {

            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, menuId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, menuId);
            ps2.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la supression du menu : " + e.getMessage());
        }
    }
}
