package com.example.minerz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfitQuery {



    public void saveProfit(int userId, int totalProfit, String name) {
        try (Connection connection = Register.getConnection();
             PreparedStatement createTableStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS tblprofits (" +
                             "id INT AUTO_INCREMENT PRIMARY KEY," +
                             "user_id INT NOT NULL," +
                             "name VARCHAR (50) NOT NULL," +
                             "total_profit INT NOT NULL)")) {

            createTableStatement.executeUpdate();


            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO tblprofits (user_id,name, total_profit) VALUES (?, ?,?)")) {
                insertStatement.setInt(1, userId); // Set the user ID
                insertStatement.setString(2,name);
                insertStatement.setInt(3, totalProfit); // Set the total profit
                insertStatement.executeUpdate();
                System.out.println("Profit data saved to database successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getUsernameByUserId(int userId) {
        String username = null;
        try (Connection connection = Register.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT username FROM tbluseraccount WHERE id = ?")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }


}
