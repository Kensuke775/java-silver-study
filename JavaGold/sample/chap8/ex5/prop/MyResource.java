package prop;
import java.util.*;

public class MyResource extends PropertyResourceBundle {        // (A)
    @Override
    protected Object[] getContents() {                          // (B)
        return new Object[] {"message", "‚±‚ñ‚É‚¿‚Í"};          // (C)
    }
}