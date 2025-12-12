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
            throw new RuntimeException("Erreur saveMenuBurgers : " + e.getMessage());
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
            throw new RuntimeException("Erreur saveMenuComplements : " + e.getMessage());
        }
    }
}
