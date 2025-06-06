package Controller;

import Model.Coche;
import Model.DatabaseConnection;
import Model.Gasto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GastoController {
    private Connection connection; 
    private Map<Coche, List<Gasto>> gastosPorCoche; 

    public GastoController() throws ClassNotFoundException, SQLException, IOException {
        this.connection = DatabaseConnection.getConnection(); 
        gastosPorCoche = new HashMap<>();
    }

    public void añadirGasto(Coche coche, Gasto gasto) throws Exception {
        if (!Gasto.TIPOS_VALIDOS.contains(gasto.getTipo().toLowerCase())) {
            throw new Exception("Tipo de gasto inválido.");
        }

        // Add the expense to the map
        gastosPorCoche.putIfAbsent(coche, new ArrayList<>()); // Initialize the list if not present
        gastosPorCoche.get(coche).add(gasto); // Add the expense to the list for the car

     
        String query = "INSERT INTO gastos (coche_id, tipo, kilometraje, fecha, importe, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, coche.getId()); 
            ps.setString(2, gasto.getTipo());
            ps.setInt(3, gasto.getKilometraje());
            ps.setDate(4, new java.sql.Date(gasto.getFecha().getTime())); // Convierte la fecha a Date 
            ps.setDouble(5, gasto.getImporte());
            ps.setString(6, gasto.getDescripcion());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Error al insertar el gasto en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al añadir gasto: " + e.getMessage());
        }
    }

    public List<Gasto> obtenerGastos(Coche coche) {
        // Check if the expenses are already in the map
        if (gastosPorCoche.containsKey(coche)) {
            return gastosPorCoche.get(coche); // Return the list from the map
        }

        List<Gasto> gastos = new ArrayList<>();
        String query = "SELECT * FROM gastos WHERE coche_id = ?"; // Query to filter by car ID
        try (PreparedStatement ps = connection.prepareStatement(query)) { 
            ps.setInt(1, coche.getId()); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int kilometraje = rs.getInt("kilometraje");
                Date fecha = rs.getDate("fecha");
                double importe = rs.getDouble("importe");
                String descripcion = rs.getString("descripcion");
                Gasto gasto = new Gasto(tipo, kilometraje, fecha, importe, descripcion);
                gastos.add(gasto);
            }
            // Store the retrieved expenses in the map
            gastosPorCoche.put(coche, gastos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gastos;
    }

    public List<Gasto> filtrarGastos(Coche coche, Integer anio, java.util.Date fechaInicio, java.util.Date fechaFin, Integer kmMin, Integer kmMax, String tipo) throws ClassNotFoundException, IOException {
        List<Gasto> filtrados = new ArrayList<>();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM gastos WHERE coche_id = ?");
            
          
            if (anio != null) {
                queryBuilder.append(" AND YEAR(fecha) = ?");
            }
            if (fechaInicio != null) {
                queryBuilder.append(" AND fecha >= ?");
            }
            if (fechaFin != null) {
                queryBuilder.append(" AND fecha <= ?");
            }
            if (kmMin != null) {
                queryBuilder.append(" AND kilometraje >= ?");
            }
            if (kmMax != null) {
                queryBuilder.append(" AND kilometraje <= ?");
            }
            if (tipo != null && !tipo.isEmpty()) {
                queryBuilder.append(" AND LOWER(tipo) = LOWER(?)");
            }

            PreparedStatement ps = connection.prepareStatement(queryBuilder.toString());
            int paramIndex = 1;
            ps.setInt(paramIndex++, coche.getId());
            if (anio != null) {
                ps.setInt(paramIndex++, anio);
            }
            if (fechaInicio != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fechaInicio.getTime())); // Convert java.util.Date to java.sql.Date
            }
            if (fechaFin != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fechaFin.getTime())); // Convert java.util.Date to java.sql.Date
            }
            if (kmMin != null) {
                ps.setInt(paramIndex++, kmMin);
            }
            if (kmMax != null) {
                ps.setInt(paramIndex++, kmMax);
            }
            if (tipo != null && !tipo.isEmpty()) {
                ps.setString(paramIndex++, tipo);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tipoGasto = rs.getString("tipo");
                int kilometraje = rs.getInt("kilometraje");
                java.util.Date fecha = rs.getDate("fecha");
                double importe = rs.getDouble("importe");
                String descripcion = rs.getString("descripcion");
                Gasto gasto = new Gasto(tipoGasto, kilometraje, fecha, importe, descripcion);
                filtrados.add(gasto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return filtrados;
    }

}

            