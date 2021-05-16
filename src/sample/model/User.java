package sample.model;

public class User {
    private String username;
    private String password;
    private double totalFine;

    public User(String username, double totalFine) {
        this.username = username;
        this.totalFine = totalFine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(double totalFine) {
        this.totalFine = totalFine;
    }
}
