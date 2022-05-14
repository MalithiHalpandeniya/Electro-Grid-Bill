package com;

import java.sql.*; 
public class Bill { 
//A common method to connect to the DB
private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //Provide the correct details: DBServer/DBName, username, password 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
 } 
 catch (Exception e) 
 {e.printStackTrace();} 
 return con; 
 } 

public String insertBill (String billCode, String userID, String dueMonth, int units) 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for inserting."; } 
 
	double amount = 0.00;

	amount = 7.00 * units;
 
 
 // create a prepared statement
 String query = " insert into bill (`billID`,`billCode`,`userID`,`dueMonth`,`units`,`amount`)"
 + " values (?, ?, ?, ?, ?, ?)"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 
 // binding values
 preparedStmt.setInt(1, 0); 
 preparedStmt.setString(2, billCode); 
 preparedStmt.setString(3, userID); 
 preparedStmt.setString(4, dueMonth); 
 preparedStmt.setInt(5, units); 
 preparedStmt.setDouble(6, amount);
 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 
 String newBill =readBill();
 output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}";

 } 
 catch (Exception e) 
 { 
	 output = "{\"status\":\"error\", \"data\":\"Error while inserting the bill.\"}";
	 System.err.println(e.getMessage());
 } 
 return output; 
 } 

public String readBill() 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for reading."; } 
 
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Bill Code</th><th>User ID</th>" +
 "<th>Due Month</th>" + 
 "<th>Units</th>" +
 "<th>Amount</th>" +
 "<th>Update</th><th>Remove</th></tr>"; 
 
 String query = "select * from bill"; 
 Statement stmt = con.createStatement(); 
 ResultSet rs = stmt.executeQuery(query); 
 
 // iterate through the rows in the result set
 while (rs.next()) 
 { 
 String billID = Integer.toString(rs.getInt("billID")); 
 String billCode = rs.getString("billCode"); 
 String userID = Integer.toString(rs.getInt("userID")); 
 String dueMonth = rs.getString("dueMonth");
 String units = rs.getString("units"); 
 String amount = Double.toString(rs.getDouble("amount"));
 
 // Add into the html table
 
 output += "<tr><td><input id='hidbillIDUpdate'name='hidbillIDUpdate'type='hidden' value='" + billID+ "'>" + billCode + "</td>";
 output += "<td>" + userID + "</td>"; 
 output += "<td>" + dueMonth + "</td>"; 
 output += "<td>" + units + "</td>";
 output += "<td>" + amount + "</td>";
 
 // buttons
 output += "<td><input name='btnUpdate' type='button' value='Update' "
			+ "class='btnUpdate btn btn-secondary' data-billID='" + billID + "'></td>"
			+ "<td><input name='btnRemove' type='button' value='Remove' "
			+ "class='btnRemove btn btn-danger' data-billID='" + billID + "'></td></tr>";
 } 
 con.close(); 
 
 // Complete the html table
 output += "</table>"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while reading the bill."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 

public String updateBill (String billID, String billCode, String userID, String dueMonth, int units)
{
	 String output = "";
	 
	 try
	 {
		 
		 Connection con = connect();
		 
		 if (con == null)
		 {return "Error while connecting to the database for updating."; }
		 
			double amount = 0.00;

			amount = 7.00 * units;
		 
		 // create a prepared statement
		 String query = "UPDATE bill SET billCode=?,userID=?,dueMonth=?,units=?,amount=?WHERE billID=?";
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setString(1, billCode);
		 preparedStmt.setString(2, userID);
		 preparedStmt.setString(3, dueMonth);
		 preparedStmt.setInt(4, units);
		 preparedStmt.setDouble(5, amount);
		 preparedStmt.setInt(6, Integer.parseInt(billID));
		 
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 
		 
		 String newBill = readBill();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newBill + "\"}";
	 }
	 catch (Exception e)
	 {
		 output = "{\"status\":\"error\", \"data\": \"Error while updating the bill.\"}";
		 System.err.println(e.getMessage());
	 }
	 
	 return output;
	 
}	public String deleteBill(String billID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 
	 // create a prepared statement
	 String query = "delete from bill where billID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(billID)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 

	
	 String newBill = readBill();
	 output = "{\"status\":\"success\", \"data\": \"" +
	 newBill + "\"}";
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the bill.\"}";
		 System.err.println(e.getMessage());  
	 } 
	 return output; 
	 } 

public String getBillDetails(String billID)

{
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database for reading";
		}
		
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>billCode</th>"
				+"<th>userID</th>"
				+ "<th>dueMonth</th>"
				+ "<th>units</th>"
				+ "<th>amount</th>";
		String query = "select * from bill where billID='"+billID+"'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		// iterate through the rows in the result set
		while (rs.next())
		{ 
			//String billID = Integer.toString(rs.getInt("billID"));
			 String billCode = rs.getString("billCode");
			 String userID = Integer.toString(rs.getInt("userID"));
			 String dueMonth = rs.getString("dueMonth");
			 String units = rs.getString("units");
			 String amount = Double.toString(rs.getDouble("amount"));
			 
			// Add a row into the html table
			 output += "<tr><td>" + billCode + "</td>";
			 output += "<td>" + userID + "</td>";
			 output += "<td>" + dueMonth + "</td>";
			 output += "<td>" + units + "</td>";
			 output += "<td>" + amount + "</td>";

			// buttons
			/*output += "<input name='billID' type='hidden' "
					+ " value='" + billID + "'>"
					+ "</form></td></tr>";*/
			 
			 output += "<td><input name='btnUpdate' type='button' value='Update' "
					 + "class='btnUpdate btn btn-secondary' data-billID='" + billID + "'></td>"
					 + "<td><input name='btnRemove' type='button' value='Remove' "
					 + "class='btnRemove btn btn-danger' data-billID='" + billID + "'></td></tr>";
			 
			 
		}
		con.close();
		// Complete the html table
		output += "</table>";

	}
	catch (Exception e)
	{
		output = "Error while reading the bill details";
		System.err.println(e.getMessage());
	}
	return output;
}

}