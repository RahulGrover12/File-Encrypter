// https://github.com/RahulGrover12/
// Rahul Grover
package model;

public class Data {
    private int id;
    private String file_name;
    private String path;
    private String email;

    public Data(int id, String file_name, String path, String email) {
        this.id = id;
        this.file_name = file_name;
        this.path = path;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Data(int id, String file_name, String path) {
        this.id = id;
        this.file_name = file_name;
        this.path = path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
