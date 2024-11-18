
// import java.util.*;
import java.io.*;

class Category implements CategoryInterface{
    int count = 0;
    private String dataFile;
    private String categoryFile;

    protected void setFiles(String dataFile,String categoryFile){
        this.dataFile = dataFile;
        this.categoryFile = categoryFile;
    }

    public void addCategory(String category) throws IOException {
        UpdateCategory.addCategory(category, categoryFile);
        count = UpdateCategory.countCategory(categoryFile);
    }

    public void displayCategory() throws IOException {
        UpdateCategory.displayCategory(categoryFile);
    }

    // return NOCAT if category not found
    public String getCategory(int n) throws IOException {
        return UpdateCategory.getCategory(n, categoryFile);
    }

    public void displayTotal() throws IOException {
        UpdateCategory.displayTotal(dataFile, categoryFile);
    }
}