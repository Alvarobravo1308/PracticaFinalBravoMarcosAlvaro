package Model.dtos;


public class UserRegisterDto {
	private String userName;
	private String userPassword;
	  private String uuid;
	public UserRegisterDto(String userName, String userPassword,  String uuid) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUuid() {
		// TODO Auto-generated method stub
		return uuid;
	}

}
