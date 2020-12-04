import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class member{
    private String name;
    private int id;
    private static int currentID = 1; // keeps id's unique
    private String color;
    private byte[] passwordHash;
    private byte salt[];
    private boolean admin;
    private String additional;
    private boolean deleted;
    protected int assigned = 0;
    protected int completed = 0;

    member(Scanner input){
        main.skipEmptyLine(input);
        do {
            System.out.print("Input member name: ");
            name = input.nextLine();
        } while(name.length() == 0);

        System.out.println("Username is " + name);
        id = currentID++;
        System.out.println("ID is " + id);
        System.out.print("Password: ");
        String password = input.nextLine();
        setPassword(password);
        admin = false; // figure out how setting admin works
        do {
            System.out.print("Input member color: ");
            color = input.nextLine();
        } while (color.length() == 0);
        System.out.println("Color is " + color);
        System.out.println("Additional member information (optional):");
        additional = input.nextLine();
    }

    public member(String name, String password, String color, String additional, boolean admin) {
        this.name = name;
        this.id = currentID++;
        setPassword(password);
        this.color = color;
        this.admin = admin;
        this.additional = additional;
    }

    public void modify(Scanner input) {
        if (deleted) {
            System.out.println("[!] User does not exist"); // we shouldn't expect to see this, but just in case
            return;
        }
        System.out.println("Enter a blank line to keep current value.");
        String str, str2;
        System.out.printf("Member name(%s): ", name);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
            name = str;
        System.out.println("Username is " + name);
        do {
            System.out.print("Change Password? (Enter current password): ");
            str = input.nextLine();
            if (str.length() > 0) {
                if (authenticate(str)) {
                    System.out.print("Password matched, enter new password: ");
                    str2 = input.nextLine();
                    System.out.print("Re-enter new password to confirm: ");
                    if (str2.equals(input.nextLine())) {
                        System.out.println("(i) Password changed successfully!");
                        changePassword(str, str2);
                        break;
                    } else {
                        System.out.println("[!] New passwords did not match");
                    }
                } else {
                    System.out.println("[!] Incorrect password");
                }
            } else {
                break;
            }
        } while (true);
        System.out.printf("Color(%s): ", color);
        str = input.nextLine();
        if (str.length() > 0)
            color = str;
        System.out.println("Color is " + color);
        System.out.printf("Additional information( %s ): ", additional);
        str = input.nextLine();
        if (str.length() > 0)
            additional = str;
    }

    void print(){
        System.out.println("Username is " + name);
        System.out.println("ID is " + id);
        System.out.println("Color is " + color);
        System.out.println("Additional member information: " + additional);
    }

    public String toColumns() {
        // format:                      "|  Id  |      Name      | Admin |  color  | C percent | Additional information "
        return !deleted ? String.format("| % 4d | %14s |   %s   | %7s |   %,.2f    | %s\n",
                getId(),
                getName(),
                isAdmin() ? "*" : " ",
                getColor(),
                (getAssigned()==0) ? 1.00 :getCompleted() / (double) getAssigned(),
                getAdditional())
                :
                "";
    }

    public boolean authenticate(String password) {
        if (deleted)
            return false;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            byte hash[] = digest.digest(password.getBytes());
            return Arrays.equals(hash, passwordHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (deleted)
            return false;
        if (authenticate(oldPassword)) {
            setPassword(newPassword);
            return true;
        }
        return false;
    }

    private void setPassword(String password) {
        SecureRandom random = new SecureRandom();
        this.salt = new byte[16];
        random.nextBytes(this.salt);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            passwordHash = digest.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin() {
        return !deleted && admin;
    }

    public void setAdmin(boolean admin, member other){ // Admins can set admin status of others, but not revoke themselves idk
        if (other.isAdmin() && this != other) {
            this.admin = admin;
        }
    }

    public String getName() {
            return !deleted ? name : "User Deleted";
    }

    public int getId() {
        return id; 
    }

    public String getColor() {
        return !deleted ? color : "";
    }

    public int getAssigned() {
        return assigned;
    }

    public int getCompleted() {
        return completed;
    }

    public String getAdditional() {
        return !deleted ? additional : "";
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted() {
        deleted = true;
    }


}
