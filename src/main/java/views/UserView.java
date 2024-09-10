// https://github.com/RahulGrover12/
// Rahul Grover
package views;

import dao.DataDAO;
import dao.UserDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private final Scanner sc = new Scanner(System.in);
    private String email;
    private String name;

    UserView(String email) {
        this.email = email;
        this.name = nameByEmail(email);
    }

    private String nameByEmail(String email) {
        UserDAO ud = new UserDAO();
        return ud.getNameFromEmail(email);
    }

    public void home() {
        do {
            System.out.println("Welcome " + this.name);
            System.out.println("Press 1 to Show Hidden Files");
            System.out.println("Press 2 to Hide a New File");
            System.out.println("Press 3 to Unhide a File");
            System.out.println("Press 0 to Exit");
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getALlFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFile_name());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.print("Enter the file Path: ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    try {
                        List<Data> files = DataDAO.getALlFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFile_name());
                        }
                        System.out.print("Enter the id of file to unhide: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidID = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidID = true;
                                break;
                            }
                        }
                        if (isValidID) {
                            DataDAO.unhideFile(id);
                        } else {
                            System.out.println("ID is Incorrect!!");
                        }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> System.exit(0);
            }
            System.out.println();
        } while (true);
    }
}
