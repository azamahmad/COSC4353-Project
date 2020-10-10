import java.util.Scanner;

public class member {
    String name;
    int id;
    String color;
    String additional;
    boolean validated;

    member(){
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Input member name:");
            name = input.nextLine();
        } while(name.length() == 0);

        System.out.println("Username is " + name);
        do {
            System.out.println("Input member ID:");
            try {
                id = Integer.parseInt(input.nextLine());
                validated = true;
            } catch (java.lang.NumberFormatException e) {
                System.out.println("ID must be an integer");
                validated = false;
            }
        } while (id == 0);
        System.out.println("ID is " + id);
        do {
            System.out.println("Input member color:");
            color = input.nextLine();
        } while (color.length() == 0);
        System.out.println("Color is " + color);
        System.out.println("Additional member information (can leave empty):");
        additional = input.nextLine();
    };

    void print(){
        System.out.println("Username is " + name);
        System.out.println("ID is " + id);
        System.out.println("Color is " + color);
        System.out.println("Additional member information: " + additional);
    }
}
