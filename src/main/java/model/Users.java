package model;

public class Users {
    //Mapear colunas com o banco de dados
    private int id_user;
    private String name;
    private String user;
    private String password;
    private String curs;


    public int getId() {return id_user; }

    public void setId(int Id) { this.id_user = id_user; }

    public String getName() { return name; }

    public void setName(String Name) {
        this.name = Name;
    }


    public String getLogin() {
        return user;
    }

    public void setLogin(String login) {
        this.user = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }
}
