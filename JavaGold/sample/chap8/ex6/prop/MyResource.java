package prop;
import java.util.*;

public class MyResource extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"message", "MyResource.class"}
        };
    }
}