package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabase implements ICocheModel {


    
    private Connection connection;
    
    public ModelDatabase() throws ClassNotFoundException, SQLException, IOException {
    	this.connection = DatabaseConnection.getConnection();

    }
    public Connection getConnection() {
        return this.connection; 
    }
    @Override
    public int create(Coche coche, Usuario usuario) {
        String query = "INSERT INTO coches (marca, modelo, matricula, anio) VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO Propietarios (coche_id, usuario_id) VALUES (?, ?)";
        String getUsuarioIdQuery = "SELECT id FROM usuarios WHERE uuid = ?";

        try {
            // Obtener el ID del usuario a partir de su UUID
            int usuarioId = -1;
            PreparedStatement psUsuario = connection.prepareStatement(getUsuarioIdQuery);
            psUsuario.setString(1, usuario.getUuid()); // Asegúrate de que el UUID no sea null
            ResultSet rsUsuario = psUsuario.executeQuery();
            if (rsUsuario.next()) {
                usuarioId = rsUsuario.getInt("id");
            } else {
                System.out.println("No se encontró el usuario con UUID: " + usuario.getUuid());
                return -1;
            }

            // Insertar el coche
            PreparedStatement psCoche = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            psCoche.setString(1, coche.getMarca());
            psCoche.setString(2, coche.getModelo());
            psCoche.setString(3, coche.getMatricula());
            psCoche.setInt(4, coche.getAnio());

            int rowsAffected = psCoche.executeUpdate();
            if (rowsAffected > 0) {
                // Obtener el ID del coche insertado
                ResultSet rsCoche = psCoche.getGeneratedKeys();
                if (rsCoche.next()) {
                    int cocheId = rsCoche.getInt(1);

                    // Insertar en Propietarios
                    PreparedStatement psPropietario = connection.prepareStatement(query2);
                    psPropietario.setInt(1, cocheId);
                    psPropietario.setInt(2, usuarioId);

                    int res = psPropietario.executeUpdate();
                    if (res > 0) {
                        return 0; // Éxito
                    } else {
                        System.out.println("Error al insertar en Propietarios");
                        return -1;
                    }
                } else {
                    System.out.println("No se pudo obtener el ID del coche insertado.");
                    return -1;
                }
            } else {
                System.out.println("No se insertó ningún coche.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



    @Override
    public List<Coche> list() {
        try {
            List<Coche> list = new ArrayList<>();
            String query = "SELECT id, marca, modelo, matricula, anio FROM coches";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String marca = rs.getString(2);
                String modelo = rs.getString(3);
                String matricula = rs.getString(4);
                int anio = rs.getInt(5);
                
                
                List<Usuario> propietarios = new ArrayList<>();
                
                
                if (!propietarios.isEmpty()) {
                    Usuario propietarioOriginal = propietarios.get(0); // Asignar el primer propietario
                    Coche coche = new Coche(id, marca, modelo, matricula, anio, propietarioOriginal);
                    list.add(coche);
                } else {
                    Coche coche = new Coche(id, marca, modelo, matricula, anio, null); // Sin propietario
                    list.add(coche);
                }
            }

            return list;
        } catch (Exception e) {
            return null; // Error en la obtención de la lista
        }
    }

    @Override
    public int edit(Coche coche) {
        try {
            String query = "UPDATE coches SET marca = ?, modelo = ?, matricula = ?, anio = ? WHERE id = ?";
            PreparedStatement ps1 = connection.prepareStatement(query);
            
            ps1.setString(1, coche.getMarca());
            ps1.setString(2, coche.getModelo());
            ps1.setString(3, coche.getMatricula());
            ps1.setInt(4, coche.getAnio());
            ps1.setInt(5, coche.getId());
            
            ps1.executeUpdate();
        } catch (Exception e) {
            return -1; // Error en la edición
        }
        return 0; // Edición exitosa
    }

    @Override
    public int delete(int id) {
        try {
            String query = "DELETE FROM coches WHERE id = ?";
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setInt(1, id);
            
            ps1.executeUpdate();
        } catch (Exception e) {
            return -1; // Error en la eliminación
        }
        return 0; // Eliminación exitosa
    }
    
    public boolean registrarUsuario(Usuario usuario) throws ClassNotFoundException, IOException {
        String sql = "INSERT INTO Usuarios (nombre,contraseña, uuid) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasenia());

            stmt.setString(3, usuario.getUuid());
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    


}
