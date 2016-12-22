package com.b4g.sid.books4geeks.Model;

import java.util.ArrayList;

/**
 * Created by Sid on 22-Dec-16.
 */

public class Category {

    private String displayName;
    private String listName;

    public String getDisplayName() {
        return displayName;
    }

    public String getListName() {
        return listName;
    }

    public Category(String displayName, String listName) {
        this.displayName = displayName;
        this.listName = listName;
    }

    private static ArrayList<Category> categoryArrayList;

    public static ArrayList<Category> getCategoryList(){
        if(categoryArrayList==null){
            categoryArrayList = new ArrayList<>();
            categoryArrayList.add(new Category("Fiction", "combined-print-and-e-book-fiction"));
            categoryArrayList.add(new Category("Nonfiction", "combined-print-and-e-book-nonfiction"));
            categoryArrayList.add(new Category("Advice and How-To", "advice-how-to-and-miscellaneous"));
            categoryArrayList.add(new Category("Animals", "animals"));
            categoryArrayList.add(new Category("Business", "business-books"));
            categoryArrayList.add(new Category("Celebrities", "celebrities"));
            categoryArrayList.add(new Category("Crime and Punishment", "crime-and-punishment"));
            categoryArrayList.add(new Category("Culture", "culture"));
            categoryArrayList.add(new Category("Education", "education"));
            categoryArrayList.add(new Category("Espionage", "espionage"));
            categoryArrayList.add(new Category("Expeditions and Adventure", "expeditions-disasters-and-adventures"));
            categoryArrayList.add(new Category("Food and Diet", "food-and-fitness"));
            categoryArrayList.add(new Category("Games and Activities", "games-and-activities"));
            categoryArrayList.add(new Category("Graphic Books", "paperback-graphic-books"));
            categoryArrayList.add(new Category("Health and Fitness", "health"));
            categoryArrayList.add(new Category("Humor", "humor"));
            categoryArrayList.add(new Category("Love and Relationships", "relationships"));
            categoryArrayList.add(new Category("Manga", "manga"));
            categoryArrayList.add(new Category("Parenthood and Family", "family"));
            categoryArrayList.add(new Category("Politics and History", "hardcover-political-books"));
            categoryArrayList.add(new Category("Race and Civil Rights", "race-and-civil-rights"));
            categoryArrayList.add(new Category("Religion and Spirituality", "religion-spirituality-and-faith"));
            categoryArrayList.add(new Category("Science and Technology", "science"));
            categoryArrayList.add(new Category("Series Books", "series-books"));
            categoryArrayList.add(new Category("Sports", "sports"));
            categoryArrayList.add(new Category("Travel", "travel"));
            categoryArrayList.add(new Category("Young Adult", "young-adult"));
        }
        return categoryArrayList;
    }

}
