package dev.prater.models;

public class UserAccount {
	public static double annualReimbursement = 1000.00;
	
	int uID; 
	boolean isFinancialManager;
	String username;
	String passkey; 
	String pers_name;
	String fam_name;
	
	UserAccount(){;}
	UserAccount(int uID, boolean iFM, String username, String passkey, String pers_name, String fam_name)
	{this.uID=uID; this.isFinancialManager=iFM; this.username = username; this.passkey = passkey; this.pers_name = pers_name; this.fam_name = fam_name;}
	
	public int getuID() {return uID;}
	public boolean isFinancialManager() {return isFinancialManager;}
	public String getUsername() {return username;}
	public String getPasskey() {return passkey;}
	public String getPers_name() {return pers_name;}
	public String getFam_name() {return fam_name;}
	
//	public void setuID(int uID) {this.uID = uID;}
	public void setFinancialManager(boolean isFinancialManager) {this.isFinancialManager = isFinancialManager;}
	public void setUsername(String username) {this.username = username;}
	public void setPasskey(String passkey) {this.passkey = passkey;}
	public void setPers_name(String pers_name) {this.pers_name = pers_name;}
	public void setFam_name(String fam_name) {this.fam_name = fam_name;}

	@Override
	public String toString() 
	{
		return "UserAccount [uID=" + uID + ", isFinancialManager=" + isFinancialManager + ", username=" + username
				+ ", passkey=" + passkey + ", pers_name=" + pers_name + ", fam_name=" + fam_name + "]";
	}

	public double reimbursementRemaining(double previousReimbursement) {return annualReimbursement - previousReimbursement;}
}
