package Controller;


import Model.Coche;
import Model.ModelDatabase;
import Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CocheController {
    private List<Coche> coches;
	private ModelDatabase modelDatabase;
    private Connection connection; 


    public CocheController(ModelDatabase modelDatabase) {
        this.modelDatabase = modelDatabase; 
        this.coches = new ArrayList<>(); 
        this.connection = modelDatabase.getConnection(); 

    }


    public Coche crearCoche(int id, Usuario propietarioOriginal, String marca, String modelo, String matricula, int anio) throws Exception {
        // Validar los campos obligatorios
        if (marca == null || modelo == null || matricula == null) {
            throw new Exception("Los campos marca, modelo y matrícula son obligatorios.");
        }
        if (anio < 1900 || anio > Calendar.getInstance().get(Calendar.YEAR) + 1) {
            throw new Exception("Año inválido.");
        }
        
       
        Coche coche = new Coche(id, marca.trim(), modelo.trim(), matricula.trim(), anio, propietarioOriginal);
        coches.add(coche);
        
        // Llamar al método para insertar el coche en la base de datos
        int result = modelDatabase.create(coche, propietarioOriginal);
        
        if (result != 0) {
            throw new Exception("Error al insertar el coche en la base de datos.");
        }
        
        return coche; 
    }



    public boolean agregarPropietario(Coche coche, Usuario nuevoPropietario) {
        return coche.addPropietario(nuevoPropietario);
    }

    public boolean eliminarCoche(Coche coche) {
        return coches.remove(coche);
    }

    public List<Coche> obtenerCochesPorPropietario(Usuario usuario) {
        List<Coche> resultado = new ArrayList<>();
        String query = "SELECT * FROM coches INNER JOIN Propietarios ON coches.id = Propietarios.coche_id WHERE Propietarios.usuario_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, usuario.getId()); // Assuming usuario.getId() returns the user's ID
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String matricula = rs.getString("matricula");
                int anio = rs.getInt("anio");
                // Create a new Coche object and add it to the result list
                Coche coche = new Coche(id, marca, modelo, matricula, anio, usuario);
                resultado.add(coche);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
}
