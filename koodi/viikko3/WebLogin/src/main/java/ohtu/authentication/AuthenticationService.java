package ohtu.authentication;

import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }
        
        if (username.length() < 3) {
            status.addError("username should have at least 3 characters");
        }
        
        if (password.length() < 8) {
            status.addError("password should have at least 8 characters");
        }

        if (!password.equals(passwordConfirmation)) {
            status.addError("password and password confirmation do not match");
        }
        
        

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }

        return status;
    }

//    private void invalid(String username, String password, CreationStatus status) {
//        // validity check of username and password
//        if (username.length() < 3) {
//            status.addError("username should have at least 3 characters");
//        }
//
//        if (password.length() < 8) {
//            status.addError("password should have at least 8 characters");
//        }
//
//        int numbers = 0;
//
//        for (int i = 0; i < password.length(); i++) {
//            if (!Character.isLetter(password.charAt(i))) {
//                numbers++;
//            }
//        }
//        if (numbers == 0) {
//            return true;
//        }
//
//        for (int i = 0; i < username.length(); i++) {
//            if (!Character.isLetter(username.charAt(i))) {
//                return true;
//            }
//
//            if (!Character.isLowerCase(username.charAt(i))) {
//                return true;
//            }
//        }
//
//        return false;
//    }

}