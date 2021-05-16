package sample.model;

public class User {
    private String username;
    private String password;
    private double totalFinedPayed;

    public User(String username, double totalFinedPayed) {
        this.username = username;
        this.totalFinedPayed = totalFinedPayed;
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

    public double getTotalFinedPayed() {
        return totalFinedPayed;
    }

    public void setTotalFinedPayed(double totalFinedPayed) {
        this.totalFinedPayed = totalFinedPayed;
    }
}
