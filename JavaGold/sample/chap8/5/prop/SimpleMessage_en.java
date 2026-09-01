package prop;

import java.util.ListResourceBundle;

public class SimpleMessage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"yes", "Yes"},
                {"no", "No"}
        };
    }
}