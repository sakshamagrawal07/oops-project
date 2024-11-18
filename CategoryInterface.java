import java.io.IOException;

public interface CategoryInterface {

    public void addCategory(String category)throws IOException;

    public void displayCategory()throws IOException;

    public String getCategory(int n)throws IOException;

    public void displayTotal()throws IOException;
}
