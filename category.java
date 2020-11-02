
import java.util.Date;
import java.util.Scanner;

  //ArrayList<category> categories = new ArrayList<category>();  --From Main.java

public class category {
    private String categoryName, color, description;
    private int id;
    private static int currentID = 1; // keeps id's unique
    private member createdBy;
    private Date createdOn;

    category(member currentUser) {
        Scanner sc = new Scanner(System.in);
        createdBy = currentUser;
        createdOn = new Date();
        System.out.println("Enter new category name: ");
        categoryName = sc.nextLine();
        id = currentID++;
        System.out.println("ID is " + id);
        //categories.add(categoryName);
        System.out.println(categoryName + " category created");

        System.out.println("Enter color for new category: ");
        color = sc.nextLine();
        System.out.println("category color: " + color);

        System.out.println("Enter description for category: ");
        description = sc.nextLine();
        System.out.println(categoryName + " description: " + description);
    }

    public void modify() {
        System.out.println("Enter a blank line to keep current value.");
        Scanner input = new Scanner(System.in);
        String str;
        System.out.printf("Category name(%s): ", categoryName);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
            categoryName = str;
        System.out.printf("Category name is %s: ", categoryName);
        System.out.printf("Category color(%s): ", color);
        str = input.nextLine();
        if (str.length() > 0)
            color = str;
        System.out.println("Color is " + color);
        System.out.printf("Additional information( %s ): ", description);
        str = input.nextLine();
        if (str.length() > 0)
            description = str;
        System.out.println("Description is " + description);
    }
    void print(){
        System.out.println("Category name is: " + categoryName);
        System.out.println("Category color: " + color);
        System.out.println("Description: " + description);
    }

    public String toColumns() {
        // format:           "|  id  |      Name      |  Color  |   Created By   |          Created On          | Description"
        return String.format("| % 3d | %14s | %7s | %14s | %28s | %s",
                id,
                categoryName,
                color,
                createdBy.getName(),
                createdOn,
                description);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public member getCreatedBy() {
        return createdBy;
    }

    public int getId() {
        return id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
}
