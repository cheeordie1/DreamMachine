public class User {

    public String name;
    public String password;
    public int quizzesTaken;
    public int quizzesMade;

    public User(String username, String password, String photo) {

    }
    /* vars needed: quizes owned ~~~~~~ *** ~~~~~ */

    //TODO: everything

    public boolean save() {
        /* check for username */

        /* create password with random generated salt */
        return false;
    }

    public boolean update() {
        return false;
    }   

    public boolean drop() {
        return true;
    }

    private boolean checkNotEmpty(String str) {
        return str != null && str.length > 0;
    }
    
    private boolean checkUsernameAvailable(String username) {

    }
    
    /* ensure password is greater than 8 characters */
    private boolean checkPasswordLength(String password) {

    }

    private boolean checkUsernameValid(String username) {

    }

    private boolean checkPasswordValid
}
