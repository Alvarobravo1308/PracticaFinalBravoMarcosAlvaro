package Model;



import Model.dtos.UserLoginDto;
import Model.dtos.UserRegisterDto;

public interface IAuthModel {
	
	public boolean register(UserRegisterDto user);

	public UserLoginDto byName(String name);

	public Usuario byUUID(String uuid);


}
