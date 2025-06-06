package Controller;

import Model.dtos.UserLoginDto;
import Model.dtos.UserRegisterDto;

public interface IAuthController {
	
	boolean register(UserRegisterDto user);
    boolean login(UserLoginDto user);
}
