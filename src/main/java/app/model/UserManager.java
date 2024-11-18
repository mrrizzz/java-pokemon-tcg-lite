// UserManager.java
package app.model;

public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void login(String username) {
        User newUser = new User();
        newUser.setUsername(username);
        setCurrentUser(newUser);
    }

    public void logout() {
        setCurrentUser(null);
    }
}