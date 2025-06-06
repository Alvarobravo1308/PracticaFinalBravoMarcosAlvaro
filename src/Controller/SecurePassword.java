package Controller;

import org.mindrot.jbcrypt.BCrypt;

public class SecurePassword {

	public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

	public static boolean checkPassword(String plainPassword, String hashedPassword) {
	    if (hashedPassword == null) {
	        throw new IllegalArgumentException("El hash no puede ser null");
	    }
	    return BCrypt.checkpw(plainPassword, hashedPassword);
	}

}
