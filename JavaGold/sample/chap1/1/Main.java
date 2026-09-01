public class Main {
    public static void main(String[] args) {
        long lValue = 10L;
        Long lWrapper = Long.valueOf(lValue);
        Double dWrapper = Double.valueOf(20.0);
        double dValue = dWrapper.doubleValue();
        Integer wrap = null;
        int iValue = wrap.intValue();
        Integer wrap1 = Integer.valueOf(200);
        Integer wrap2 = Integer.valueOf(200);
        boolean result = wrap1.equals(wrap2);   // true
    }
}