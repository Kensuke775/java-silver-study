package prop;

import java.util.ListResourceBundle;

public class SimpleMessage_fr_FR extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"yes", "Oui"},
                {"no", "Non"}
        };
    }
}