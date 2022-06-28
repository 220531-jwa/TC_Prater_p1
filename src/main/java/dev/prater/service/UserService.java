package dev.prater.service;

import java.util.ArrayList;
import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.repository.UserDAO;

public class UserService {
	private static UserDAO uDAO;
	
	public UserService(UserDAO userdao) {UserService.uDAO=userdao;}
	
	public UserAccount login(String username, String passkey) 
	{	
		UserAccount uA = uDAO.getUserAccount(username);
		
		if (uA.getPasskey().equals(passkey)) {return uA;}
		else {return null;}
	}
	
	// register / create user
	public UserAccount createUserAcount(UserAccount uA) {
		UserAccount createdUser = uDAO.createUserAcc(uA);
		return createdUser;
	}

	public List<UserAccount> getAllUsers() 
	{
		List<UserAccount> userList = uDAO.getAllUserAccounts();
		List<UserAccount> output = new ArrayList<>();
		UserAccount temp = new UserAccount();
		
		if (userList == null) {return null;}
		else {
			for (UserAccount ua: userList) 
			{
				temp = ua;
				temp.setPasskey("omitted for security reasons");
				output.add(temp);
				temp = null;
			}
			return output;
		}
	}

	public UserAccount getUserAccount(int id) throws Exception {
		// this is where you could put some business logic 
		// for example checking if the User returned by userDao.getUserById(id) is null 
		UserAccount uA = uDAO.getUserAccount(id);
		
		if (uA == null) {
			throw new Exception("User not found");
		}
		
		return uA;
	}

	public boolean updateUserAccount(UserAccount uChanged) {
		boolean victory = false;
		UserAccount uBack = uDAO.updateUserAccount(uChanged);
		if (uBack.getuID() == uChanged.getuID()) {victory = true;}
		return victory;
	}

	public boolean deleteUserAccount(int id) {return uDAO.deleteUserAccount(id);}
}

//in update, make sure that they're not trying to change manager status