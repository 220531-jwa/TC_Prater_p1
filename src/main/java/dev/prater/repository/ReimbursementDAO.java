package dev.prater.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import dev.prater.models.ReimbursementRequest;
import dev.prater.utils.ConnectionUtil;

public class ReimbursementDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	public int goblinTest() { System.out.println("arrived at goblin"); return 123;}
	
	//Create 
	public ReimbursementRequest createRequest(ReimbursementRequest rr) {
		String sql = "insert into reimbursementrequest values (default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning *;";
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1,rr.getUserID());
			ps.setInt(2,rr.getFinanceID());
			ps.setInt(3,rr.getStatusCode());
			ps.setDouble(4, rr.getEventCost());
			ps.setDouble(5, rr.getReqAmount());
			ps.setDouble(6, rr.getApprovedAmount());
			ps.setDouble(7, rr.getAmountExceeded());
			ps.setString(8, rr.getDescription());
			ps.setString(9, rr.getLocation());
			ps.setString(10, rr.getJustification());
			ps.setString(11, rr.getEvent_Type());
			ps.setString(12, rr.getProofType());
			ps.setString(13, rr.getProofGrade());
			ps.setString(14, rr.getFinanceComment());
			ps.setTimestamp(15, Timestamp.valueOf(rr.getEventDate()));
			ps.setTimestamp(16, Timestamp.valueOf(rr.getOpenDate()));
			ps.setTimestamp(17, Timestamp.valueOf(rr.getCloseDate()));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uID = rs.getInt("uid");
				int fID = rs.getInt("financeid");
				int sCode = rs.getInt("statuscode");
				double eCost = rs.getDouble("eventcost");
				double rAmount = rs.getDouble("reqamount");
				double aAmount = rs.getDouble("approvedamount");
				double aExceeded = rs.getDouble("amountexceeded");
				String desc = rs.getString("description");
				String location = rs.getString("reqlocation");
				String justification = rs.getString("justification");
				String eType = rs.getString("eventtype");
				String pType = rs.getString("prooftype");
				String pGrade = rs.getString("proofgrade");
				String fComment = rs.getString("financecomment");
				LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
				LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
				LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
				
				return new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
						pType,pGrade,fComment,eDate,oDate,cDate);
			}
			
		}
		catch (SQLException e){e.printStackTrace();}
		return null;
	}
	
	//Read 
	public ReimbursementRequest getOneRequest(int rID)
	{
		String sql = "select * from reimbursementrequest where id = ?";
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, rID);
			ResultSet rs = ps.executeQuery();
			ReimbursementRequest rR = null;
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uID = rs.getInt("uid");
				int fID = rs.getInt("financeid");
				int sCode = rs.getInt("statuscode");
				double eCost = rs.getDouble("eventcost");
				double rAmount = rs.getDouble("reqamount");
				double aAmount = rs.getDouble("approvedamount");
				double aExceeded = rs.getDouble("amountexceeded");
				String desc = rs.getString("description");
				String location = rs.getString("reqlocation");
				String justification = rs.getString("justification");
				String eType = rs.getString("eventtype");
				String pType = rs.getString("prooftype");
				String pGrade = rs.getString("proofgrade");
				String fComment = rs.getString("financecomment");
				LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
				LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
				LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
				
				rR = new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
						pType,pGrade,fComment,eDate,oDate,cDate);
			}
			
			return rR;
		}
		catch(SQLException e) {e.printStackTrace(); return null;}
	}	
	
	public List<ReimbursementRequest> getAllRequests()
	{
		List<ReimbursementRequest> requests = new ArrayList<>();
		String sql = "select * from reimbursementrequest";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uID = rs.getInt("uid");
				int fID = rs.getInt("financeid");
				int sCode = rs.getInt("statuscode");
				double eCost = rs.getDouble("eventcost");
				double rAmount = rs.getDouble("reqamount");
				double aAmount = rs.getDouble("approvedamount");
				double aExceeded = rs.getDouble("amountexceeded");
				String desc = rs.getString("description");
				String location = rs.getString("reqlocation");
				String justification = rs.getString("justification");
				String eType = rs.getString("eventtype");
				String pType = rs.getString("prooftype");
				String pGrade = rs.getString("proofgrade");
				String fComment = rs.getString("financecomment");
				LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
				LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
				LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
				
				ReimbursementRequest rR = new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
						pType,pGrade,fComment,eDate,oDate,cDate);
				requests.add(rR);
			}
			
			return requests;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public List<ReimbursementRequest> getUsersRequests(int uID) 
	{
		List<ReimbursementRequest> requests = new ArrayList<>();
		String sql = "select * from reimbursementrequest where uid = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				//int uID = rs.getInt("uid");
				int fID = rs.getInt("financeid");
				int sCode = rs.getInt("statuscode");
				double eCost = rs.getDouble("eventcost");
				double rAmount = rs.getDouble("reqamount");
				double aAmount = rs.getDouble("approvedamount");
				double aExceeded = rs.getDouble("amountexceeded");
				String desc = rs.getString("description");
				String location = rs.getString("reqlocation");
				String justification = rs.getString("justification");
				String eType = rs.getString("eventtype");
				String pType = rs.getString("prooftype");
				String pGrade = rs.getString("proofgrade");
				String fComment = rs.getString("financecomment");
				LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
				LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
				LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
				
				ReimbursementRequest rR = new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
						pType,pGrade,fComment,eDate,oDate,cDate);
				requests.add(rR);
			}
			
			return requests;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}
	
	public List<ReimbursementRequest> getManagersRequests(int fID) 
	{
		List<ReimbursementRequest> requests = new ArrayList<>();
		String sql = "select * from reimbursementrequest where financeid = ?";
		
		try (Connection conn = cu.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, fID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt("id"); 
				int uID = rs.getInt("uid");
//				int fID = rs.getInt("financeid");
				int sCode = rs.getInt("statuscode");
				double eCost = rs.getDouble("eventcost");
				double rAmount = rs.getDouble("reqamount");
				double aAmount = rs.getDouble("approvedamount");
				double aExceeded = rs.getDouble("amountexceeded");
				String desc = rs.getString("description");
				String location = rs.getString("reqlocation");
				String justification = rs.getString("justification");
				String eType = rs.getString("eventtype");
				String pType = rs.getString("prooftype");
				String pGrade = rs.getString("proofgrade");
				String fComment = rs.getString("financecomment");
				LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
				LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
				LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
				
				ReimbursementRequest rR = new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
						pType,pGrade,fComment,eDate,oDate,cDate);
				requests.add(rR);
			}
			
			return requests;
		}
		catch(SQLException e){e.printStackTrace();}		
		return null;
	}

	//Update 
	public ReimbursementRequest updateRequest(int rID, ReimbursementRequest rr)
	{
	String sql = "update reimbursementrequest set uid=?,financeid=?,statuscode=?,eventcost=?,reqamount=?,approvedamount=?,amountexceeded=?,"
			+ "description=?,reqlocation=?,justification=?,eventtype=?,prooftype=?,proofgrade=?,financecomment=?,"
			+ "eventdate=?,opendate=?,closedate=? where id=? returning *;";
	try (Connection conn = cu.getConnection();)
	{
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setInt(1,rr.getUserID());
		ps.setInt(2,rr.getFinanceID());
		ps.setInt(3,rr.getStatusCode());
		ps.setDouble(4, rr.getEventCost());
		ps.setDouble(5, rr.getReqAmount());
		ps.setDouble(6, rr.getApprovedAmount());
		ps.setDouble(7, rr.getAmountExceeded());
		ps.setString(8, rr.getDescription());
		ps.setString(9, rr.getLocation());
		ps.setString(10, rr.getJustification());
		ps.setString(11, rr.getEvent_Type());
		ps.setString(12, rr.getProofType());
		ps.setString(13, rr.getProofGrade());
		ps.setString(14, rr.getFinanceComment());
		ps.setTimestamp(15, Timestamp.valueOf(rr.getEventDate()));
		ps.setTimestamp(16, Timestamp.valueOf(rr.getOpenDate()));
		ps.setTimestamp(17, Timestamp.valueOf(rr.getCloseDate()));
		ps.setInt(18, rID);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			int id = rs.getInt("id"); 
			int uID = rs.getInt("uid");
			int fID = rs.getInt("financeid");
			int sCode = rs.getInt("statuscode");
			double eCost = rs.getDouble("eventcost");
			double rAmount = rs.getDouble("reqamount");
			double aAmount = rs.getDouble("approvedamount");
			double aExceeded = rs.getDouble("amountexceeded");
			String desc = rs.getString("description");
			String location = rs.getString("reqlocation");
			String justification = rs.getString("justification");
			String eType = rs.getString("eventtype");
			String pType = rs.getString("prooftype");
			String pGrade = rs.getString("proofgrade");
			String fComment = rs.getString("financecomment");
			LocalDateTime eDate = rs.getTimestamp("eventdate").toLocalDateTime();
			LocalDateTime oDate = rs.getTimestamp("opendate").toLocalDateTime();
			LocalDateTime cDate = rs.getTimestamp("closedate").toLocalDateTime();
			
			return new ReimbursementRequest(id,uID,fID,sCode,eCost,rAmount,aAmount,aExceeded,desc,location,justification,eType,
					pType,pGrade,fComment,eDate,oDate,cDate);
		}
		
	}
	catch (SQLException e){e.printStackTrace();}
	return null;
}	
	//Destroy
}

/*
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
 */