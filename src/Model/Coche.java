package Model;

import java.util.ArrayList;
import java.util.List;

public class Coche {
    private int id;
    private String marca;
    private String modelo;
    private String matricula;
    private int anio;
    private List<Usuario> propietarios;

   
    public Coche() {
        this.propietarios = new ArrayList<>(); 
    }

    // parametros 
    public Coche(int id, String marca, String modelo, String matricula, int anio, Usuario propietarioOriginal) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.anio = anio;
        this.propietarios = new ArrayList<>();
        if (propietarioOriginal != null) {
            this.propietarios.add(propietarioOriginal); //añdade al propietario original
        }
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnio() {
        return this.anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Usuario> getPropietarios() {
        return this.propietarios;
    }

    public boolean addPropietario(Usuario usuario) {
        for (Usuario u : propietarios) {
            if (u.getUuid().equals(usuario.getUuid())) {
                return false; 
            }
        }
        this.propietarios.add(usuario);
        return true; 
    }

    public boolean removePropietario(String uuid) {
        return this.propietarios.removeIf(u -> u.getUuid().equals(uuid));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coche{")
                .append("marca='").append(marca).append('\'')
                .append(", modelo='").append(modelo).append('\'')
                .append(", matricula='").append(matricula).append('\'')
                .append(", anio=").append(anio)
                .append(", propietarios=[");
        for (Usuario u : propietarios) {
            sb.append(u.getNombre()).append(" (").append(u.getUuid()).append("), ");
        }
        if (!propietarios.isEmpty()) sb.setLength(sb.length() - 2); 
        sb.append("]}");
        return sb.toString();
    }
}
