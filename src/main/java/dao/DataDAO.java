// https://github.com/RahulGrover12/
// Rahul Grover
package dao;

import db.MyConnection;
import model.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getALlFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        String query = "select * from data where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Data> files = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String path = resultSet.getString(3);
            files.add(new Data(id, name, path));
        }
        return files;
    }

    public static int hideFile(Data file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        String query = "insert into data value(default,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, file.getFile_name());
        preparedStatement.setString(2, file.getPath());
        preparedStatement.setString(3, file.getEmail());
        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);

        preparedStatement.setCharacterStream(4, fr, f.length());
        int ans = preparedStatement.executeUpdate();
        fr.close();
        f.delete();
        System.out.println("File Hidden Successfully!!");
        return ans;
    }

    public static void unhideFile(int id) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        String query = "select path, bin_data from data where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String path = resultSet.getString("path");
        Clob clob = resultSet.getClob("bin_data");

        Reader r = clob.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while ((i = r.read()) != -1) {
            fw.write((char) i);
        }
        fw.close();
        preparedStatement = connection.prepareStatement("delete from data where id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        System.out.println("Successfully Unhidden!!");
    }
}
