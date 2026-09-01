package prop;

import java.util.ListResourceBundle;

public class Message extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"file.name", "Message.class"}
        };
    }
}