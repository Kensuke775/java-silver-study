import sun.misc.BASE64Encoder;

/*
 * >jdeps -jdkinternals LegacyEncoder.class の動作確認用
 * JDK 9で削除されたsun.misc.BASE64Encoderクラスを使用しています。
 * 以降のバージョンではjava.util.Base64クラスを使用します。
 */
public class LegacyEncoder {
    public static void main(String... args) {
        byte[] data = "Java SE 17".getBytes();
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));
        }
}
