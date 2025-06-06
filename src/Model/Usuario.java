package Model;

public class Usuario {
	private int id;
    private String nombre;
    private String contrasenia;
    private String uuid;

    public Usuario(String nombre, String contrasenia, int id, String uuid ) {
        this.nombre = nombre;
        this.contrasenia= contrasenia;
        this.uuid =uuid;
        this.id= id;
    }
   
    public  int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	public String getContrasenia() {
		return this.contrasenia;
	}
	public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Usuario{" +"id='" + id +
                "nombre='" + nombre + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }



}
