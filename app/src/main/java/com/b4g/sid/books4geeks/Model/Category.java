package com.b4g.sid.books4geeks.Model;

import com.b4g.sid.books4geeks.R;

import java.util.ArrayList;

/**
 * Created by Sid on 22-Dec-16.
 */

public class Category {

    private String displayName;
    private String listName;
    private int imageDrawable;

    public String getDisplayName() {
        return displayName;
    }

    public String getListName() {
        return listName;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public Category(String displayName, String listName) {
        this.displayName = displayName;
        this.listName = listName;
    }

    public Category(String displayName, String listName, int imageDrawable){
        this.displayName = displayName;
        this.listName = listName;
        this.imageDrawable = imageDrawable;
    }

    private static ArrayList<Category> categoryArrayList;

    public static ArrayList<Category> getCategoryList(){
        if(categoryArrayList==null){
            categoryArrayList = new ArrayList<>();
            categoryArrayList.add(new Category("Fiction", "combined-print-and-e-book-fiction", R.drawable.icon_category_fiction));
            categoryArrayList.add(new Category("Nonfiction", "combined-print-and-e-book-nonfiction",R.drawable.icon_category_nonfiction));
            categoryArrayList.add(new Category("Crime and Punishment", "crime-and-punishment",R.drawable.icon_category_crime));
            categoryArrayList.add(new Category("Education", "education",R.drawable.icon_category_education));
            categoryArrayList.add(new Category("Sports", "sports",R.drawable.icon_category_sports));
            //categoryArrayList.add(new Category("Expeditions and Adventure", "expeditions-disasters-and-adventures",R.drawable.));
            //categoryArrayList.add(new Category("Series Books", "series-books",R.drawable.iccat));
            categoryArrayList.add(new Category("Advice and How-To", "advice-how-to-and-miscellaneous",R.drawable.icon_category_advice));
            categoryArrayList.add(new Category("Love and Relationships", "relationships",R.drawable.icon_category_love));
            categoryArrayList.add(new Category("Animals", "animals",R.drawable.icon_category_animals));
            categoryArrayList.add(new Category("Politics and History", "hardcover-political-books",R.drawable.icon_category_history));
            categoryArrayList.add(new Category("Business", "business-books",R.drawable.icon_category_business));
            categoryArrayList.add(new Category("Celebrities", "celebrities",R.drawable.icon_category_celebrities));
            categoryArrayList.add(new Category("Culture", "culture",R.drawable.icon_category_culture));
            //categoryArrayList.add(new Category("Espionage", "espionage",R.drawable));
            categoryArrayList.add(new Category("Food and Diet", "food-and-fitness",R.drawable.icon_category_food));
            categoryArrayList.add(new Category("Games and Activities", "games-and-activities",R.drawable.icon_category_games));
            categoryArrayList.add(new Category("Graphic Books", "paperback-graphic-books",R.drawable.icon_category_graphic));
            categoryArrayList.add(new Category("Health and Fitness", "health",R.drawable.icon_category_fitness));
            categoryArrayList.add(new Category("Humor", "humor",R.drawable.icon_category_humor));
            //categoryArrayList.add(new Category("Manga", "manga",R.drawable.man));
            categoryArrayList.add(new Category("Parenthood and Family", "family",R.drawable.icon_category_family));
            //categoryArrayList.add(new Category("Race and Civil Rights", "race-and-civil-rights",R.draw));
            categoryArrayList.add(new Category("Religion and Spirituality", "religion-spirituality-and-faith",R.drawable.icon_category_religion));
            categoryArrayList.add(new Category("Science and Technology", "science",R.drawable.icon_category_science));
            categoryArrayList.add(new Category("Travel", "travel",R.drawable.icon_category_travel));
            categoryArrayList.add(new Category("Young Adult", "young-adult",R.drawable.icon_category_young));
        }
        return categoryArrayList;
    }

}
