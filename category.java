
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Iterator;
import java.util.ArrayList;

  //ArrayList<category> categories = new ArrayList<category>();  --From Main.java

public class category {
    private String categoryName, color, description;
    private String editCategory, deleteCategory;
    private int choice;

    category() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new category name: ");
        categoryName = sc.nextLine();
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
        // format:           "|      Name      |  Color  | Description
        return String.format("| %14s | %7s | %s",
                categoryName,
                color,
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
}
