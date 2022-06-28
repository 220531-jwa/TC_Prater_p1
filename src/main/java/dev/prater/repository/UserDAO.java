package dev.prater.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.prater.models.UserAccount;
import dev.prater.utils.ConnectionUtil;

public class UserDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	//CRUD
	
	//Create
	public UserAccount createUserAcc(UserAccount uA)
	{
		String sql = "insert into useraccounts values (default, ?,?,?,?) returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uA.getUsername());
			ps.setString(2, uA.getPasskey());
			ps.setString(3, uA.getPers_name());
			ps.setString(4, uA.getFam_name());
			ps.setBoolean(5, uA.isFinancialManager());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new UserAccount(rs.getInt("id"),rs.getBoolean("isManager"),rs.getString("username"),rs.getString("passkey"),
					rs.getString("pers_name"),rs.getString("fam_name"));}
		}
		catch(SQLException e) {e.printStackTrace();}
		return null;
	}

	//Read
	public List<UserAccount> getAllUserAccounts()
	{
		List<UserAccount> users = new ArrayList<>();
		String sql = "select * from useraccounts";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				boolean fM = rs.getBoolean("isManager");
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				String username = rs.getString("username");
				String passkey = rs.getString("passkey");
				
				UserAccount uA = new UserAccount(id,fM,username,passkey,persName,famName);
				users.add(uA);
			}
			
			return users;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public UserAccount getUserAccount(int uID)
	{
		UserAccount uA = null;
		String sql = "select * from useraccounts where id = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				boolean fM = rs.getBoolean("isManager");
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				String username = rs.getString("username");
				String passkey = rs.getString("passkey");
				
				uA = new UserAccount(id,fM,username,passkey,persName,famName);
			}
			
			return uA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public UserAccount getUserAccount(String username)
	{
		UserAccount uA = null;
		String sql = "select * from useraccounts where username = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id");
				boolean fM = rs.getBoolean("isManager");
				String persName = rs.getString("pers_name");
				String famName = rs.getString("fam_name");
				//String username = rs.getString("username"); //already have this one in inputs
				String passkey = rs.getString("passkey");
				
				uA = new UserAccount(id,fM,username,passkey,persName,famName);
			}
			
			return uA;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	//Update
	public UserAccount updateUserAccount (UserAccount uInput)
	{
		String sql = "update useraccounts set isManager = ?, username = ?, passkey = ?, pers_name = ?, fam_name = ? where id = ? returning *";
		try(Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setString(2, uInput.getUsername());
			ps.setString(3, uInput.getPasskey());
			ps.setString(4, uInput.getPers_name());
			ps.setString(5, uInput.getFam_name());
			ps.setInt(6, uInput.getuID());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {return new UserAccount(rs.getInt("id"),rs.getBoolean("isManager"),rs.getString("username"),rs.getString("passkey"),
					rs.getString("pers_name"),rs.getString("fam_name"));}
		}
		catch(SQLException e) {e.printStackTrace();}
		return null;
	}
	
	//Destroy
	public boolean deleteUserAccount(int uID) 
	{
		if (getUserAccount(uID)==null) {return false;}
		else {
			String sql = "delete from useraccounts where id = ? returning *";
			try(Connection conn = cu.getConnection();)
			{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, uID);
				ps.executeQuery();
			    return true;
			}
			catch(SQLException e) {e.printStackTrace();}
		return false;
		}
	}
}
