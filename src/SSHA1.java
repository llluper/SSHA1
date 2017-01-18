import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * This program finds a pair of integers (x,x’) such that x ≠ x’
 * but the SSHA-1 hash values of the following two messages are the same.
 * m and m’, m ≠ m’, such that H(m) = H(m’)
 *
 * SSHA1 is a simplified version of SHA-1 which only outputs the first 16 bits of SHA-1 when hashing a message.
 *
 * @author Mitchell Stanford
 */
public class SSHA1 {

    /**
     * @param args the command line arguments
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException{
            final int MAX = 100;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            String initialText = "The Cat-In-The-Hat owes Mitchell ";
            StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder(),
                    sb3 = new StringBuilder(), sb4 = new StringBuilder();
            String str1 = "", str2 = "", result1 = "", result2 = "";
            byte[] byteResult1, byteResult2;
            boolean noCollision = true;
            int count = 0, i = 0;

            while (noCollision) {
                sb1.append(initialText);
                sb1.append(Integer.toString(i));
                sb1.append(" dollars");                         // = "The Cat-In-The-Hat owes Mitchell i dollars"
                str1 = sb1.toString();

                for (int j = 0; j < MAX; j++) {
                    if (i != j) {
                        count++;
                        sb2.append(initialText);
                        sb2.append(Integer.toString(j));
                        sb2.append(" dollars");
                        str2 = sb2.toString();

                        byteResult1 = md.digest(str1.getBytes());
                        byteResult2 = md.digest(str2.getBytes());

                        for (int k = 0; k < byteResult1.length; k++) {
                            sb3.append(Integer.toString((byteResult1[k] & 0xff) + 0x100, 16).substring(1));
                        }
                        for (int l = 0; l < byteResult2.length; l++) {
                            sb4.append(Integer.toString((byteResult2[l] & 0xff) + 0x100, 16).substring(1));
                        }

                        result1 = sb3.toString();
                        result1 = result1.substring(0, 4);
                        result2 = sb4.toString();
                        result2 = result2.substring(0, 4);

                        if (result1.equals(result2))
                            noCollision = false;

                    }

                    if (!noCollision)
                        j = MAX;

                    sb1.setLength(0);
                    sb2.setLength(0);
                    sb3.setLength(0);
                    sb4.setLength(0);
                }
                i++;
            }
            System.out.println(str1);
            System.out.println("Hash value(16 bits) = " + result1 + "\n");
            System.out.println(str2);
            System.out.println("Hash value(16 bits) = " + result2);
            System.out.print("\nNumber of trials before collision was found: " + count + "\n");
	}
}
