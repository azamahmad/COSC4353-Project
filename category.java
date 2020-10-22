
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
        System.out.println("Choose from the following:\n 1: Create a New Category\n 2: Edit an existing category\n 3: Delete a Category\n 4: View List of Categories\n 5: Return to main\n");

        choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter new category name: ");
                categoryName = sc.next();
                //categories.add(categoryName);
                System.out.println(categoryName + " category created");

                System.out.println("Enter color for new category: ");
                color = sc.next();
                System.out.println("category color: " + color); 

                System.out.println("Enter description for category: ");
                description = sc.next();
                System.out.println(categoryName + " description: " + description);
                break;
            case 2:
                System.out.println("Enter name of category to be edited: ");
                editCategory = sc.next();
                /*for (int i = 0; i < categories.size(); i++) {
                    if (!categories.get(i).equals(editCategory)) {
                        System.out.println("Invalid category name...Please try again");
                    } else {
                        System.out.println("what would you like to change? ");
                    }
                }*/
                break;
            case 3:
                System.out.println("Enter name of category to delete: ");
                deleteCategory = sc.next();
                //categories.remove(deleteCategory);
                break;
            case 4:
                System.out.println("Existing categories:\n");
                /*Iterator it = categories.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }*/
                break;
            case 5:
                System.out.println("Returning to main...");
                break;
        }
    }
    void print(){
        System.out.println("Category name is: " + categoryName);
        System.out.println("Category color: " + color);
        System.out.println("Description: " + description);
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
