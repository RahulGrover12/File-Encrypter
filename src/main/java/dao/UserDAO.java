// https://github.com/RahulGrover12/
// Rahul Grover
package dao;
//DAO = Data Access Object

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Agar user exist karta hai to
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        String query = "select email from users";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String mail = resultSet.getString(1);
            if (mail.equals(email))
                return true;
        }
        return false;
    }

    // Agar user exist nahi krta to use insert karege

    public static int saveUser(User user) {
        Connection connection = MyConnection.getConnection();
        String checkQuery = "SELECT * FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (name, email) VALUES (?, ?)";

        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, user.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return 1;
            } else {
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, user.getName());
                insertStmt.setString(2, user.getEmail());
                insertStmt.executeUpdate();
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String getNameFromEmail(String email) {
        String name = "";
        Connection connection = MyConnection.getConnection();
        String query = "select name from users where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
