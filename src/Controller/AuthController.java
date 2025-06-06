package Controller;

import java.io.IOException;
import java.sql.SQLException;
import Model.AuthModel;
import Model.IAuthModel;
import Model.Usuario;
import Model.dtos.UserLoginDto;
import Model.dtos.UserRegisterDto;

public class AuthController implements IAuthController {
    private IAuthModel authModel;

    public AuthController() throws ClassNotFoundException, SQLException, IOException {
        this.authModel = new AuthModel();
    }

    public AuthController(AuthModel authModel) throws ClassNotFoundException, SQLException, IOException {
        this.authModel = authModel; // Inicializa authModel
    }

    public boolean register(UserRegisterDto user) {
        // Validar que el nombre y la contraseña no estén vacíos
        if (user.getUserName() == null || user.getUserName().isEmpty() || 
            user.getUserPassword() == null || user.getUserPassword().isEmpty()) {
            return false; // Retornar false si los datos son inválidos
        }

        // Encriptar la contraseña usando BCrypt
        String encryptedPassword = SecurePassword.hashPassword(user.getUserPassword());
        user.setUserPassword(encryptedPassword); 

        
        return this.authModel.register(user);
    }

    public boolean login(UserLoginDto user) {
        UserLoginDto userFromDb = this.authModel.byName(user.getName());
        if (userFromDb == null) {
            return false;
        }

        // Validar la contraseña con BCrypt
        boolean valid = SecurePassword.checkPassword(user.getPassword(), userFromDb.getPassword());
        System.out.println(valid);
        
        return valid;
    }

    public Usuario obtenerUsuarioPorUUID(String uuid) {
        
        return authModel.byUUID(uuid); 
    }
}
