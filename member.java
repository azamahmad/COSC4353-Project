import java.util.Scanner;

public class member {
    String name;
    int id;
    String color;

    member(){
        Scanner input = new Scanner(System.in);
        System.out.print("Input member name:");
        name = input.nextLine();
        System.out.println("Username is " + name);
        System.out.println("Input member ID:");
        id = Integer.parseInt(input.nextLine());
        System.out.println("ID is " + id);
        System.out.println("Input member color:");
        color = input.nextLine();
        System.out.println("Color is " + color);
    };

    void print(){
        System.out.println("Username is " + name);
        System.out.println("ID is " + id);
        System.out.println("Color is " + color);
    }
}
