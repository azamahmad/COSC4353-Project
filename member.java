import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class member {
    private String name;
    private int id;
    private static int currentID = 1; // keeps id's unique
    private String color;
    private byte[] passwordHash;
    private byte salt[];
    private boolean admin;
    private String additional;
    private boolean validated;

    member(){
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Input member name:");
            name = input.nextLine();
        } while(name.length() == 0);

        System.out.println("Username is " + name);
        id = currentID++;
        System.out.println("ID is " + id);
        System.out.println("Password: ");
        String password = input.next();
        setPassword(password);
        admin = false; // figure out how setting admin works
        do {
            System.out.println("Input member color:");
            color = input.nextLine();
        } while (color.length() == 0);
        System.out.println("Color is " + color);
        System.out.println("Additional member information (can leave empty):");
        additional = input.nextLine();
    }

    public void modify() {
        System.out.println("Enter a blank line to keep current value.");
        Scanner input = new Scanner(System.in);
        String str;
        System.out.printf("Member name(%s): ", name);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
            name = str;
        System.out.println("Username is " + name);
//        System.out.println("Password: ");
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

    public member(String name, String password, String color, boolean admin) {
        this.name = name;
        this.id = currentID++;
        setPassword(password);
        this.color = color;
        this.admin = admin;
        this.additional = "";
    }

    void print(){
        System.out.println("Username is " + name);
        System.out.println("ID is " + id);
        System.out.println("Color is " + color);
        System.out.println("Additional member information: " + additional);
    }

    public String toColumns() {
        // format:           "|  id  |  color  |      Name      | Admin | Additional information "
        return String.format("| % 3d | %7s | %14s |   %s   | %s",
                id,
                color,
                name,
                admin ? "*" : " ",
                additional);
    }

    public boolean authenticate(String password) {
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
        if (authenticate(oldPassword)) {
            setPassword(newPassword);
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
        return admin;
    }

    public void setAdmin(boolean admin, member other){ // Admins can set admin status of others, but not revoke themselves idk
        if (other.isAdmin() && this != other) {
            this.admin = admin;
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }


}
