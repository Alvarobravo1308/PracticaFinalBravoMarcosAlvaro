package Model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.dtos.UserLoginDto;
import Model.dtos.UserRegisterDto;

public class AuthModel implements IAuthModel {

	private Connection connection;
	
	public AuthModel() throws ClassNotFoundException, SQLException, IOException {
		
		this.connection = DatabaseConnection.getConnection();
	}
	
	public boolean register(UserRegisterDto user) {
	    String query = "INSERT INTO usuarios (nombre, contraseña, uuid) VALUES (?, ?, ?)";
	    
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, user.getUserName());
	        ps.setString(2, user.getUserPassword());
	        ps.setString(3, user.getUuid());
	        
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; // Retornar true si se insertó al menos un registro
	    } catch (SQLException e) {
	        e.printStackTrace(); // Imprimir el error para depuración
	        return false; // Retornar false en caso de error
	    }
	}


	public UserLoginDto byName(String name) {
		String query = "SELECT id, nombre, contraseña, uuid FROM usuarios WHERE nombre like ?";
		
		try {
			PreparedStatement ps2 = connection.prepareStatement(query);

			ps2.setString(1, name);
			
			ResultSet rs = ps2.executeQuery();
			
			if (rs.next()) {
	            System.out.println("Si encontrado");
				String nameDb = rs.getString(2);
				String password = rs.getString(3);
				int id= rs.getInt(1);
				String uuid= rs.getString(4);
				UserLoginDto user = new UserLoginDto(nameDb, password, id, uuid);
				return user;
			} else {
	            return null;
	        }
		} catch (Exception e) {
            return null;
		}
	}

	public Usuario byUUID(String uuid) {
	    String query = "SELECT id, nombre, contraseña FROM usuarios WHERE uuid = ?";
	    
	    try {
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setString(1, uuid);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String contrasenia = rs.getString("contraseña");
	            
				Usuario usuario = new Usuario(nombre, contrasenia, 0, uuid);
	            usuario.setUuid(uuid); 
	            return usuario;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Si no se encuentra el usuario
	}
}
