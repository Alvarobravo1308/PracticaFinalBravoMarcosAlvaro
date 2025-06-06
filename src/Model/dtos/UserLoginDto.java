package Model.dtos;


public class UserLoginDto {
	private String name;
	private String password;
	private int id;
    private String uuid;

	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserLoginDto(String name, String password, int id,String uuid) {
		super();
		this.name = name;
		this.password = password;
		this.uuid = uuid;
		this.id = id;

	}

	public void setPassword(String encrypted) {
		this.password = encrypted;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
