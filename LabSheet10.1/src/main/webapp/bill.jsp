<%@page import="com.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/bill.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

	<h1>Bill Management </h1>

	<form id="formBill" name="formBill">
 		Bill code:
 		<input id="billCode" name="billCode" type="text" class="form-control form-control-sm">
 		<br> 
 		User ID:
		<input id="userID" name="userID" type="text" class="form-control form-control-sm">
 		<br> 
 		Due Month:
 		<input id="dueMonth" name="dueMonth" type="text" class="form-control form-control-sm">
 		<br> 
 		Units:
		<input id="units" name="units" type="text" class="form-control form-control-sm">
 		<br>
 		
 		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 		<input type="hidden" id="hidbillIDSave" name="hidbillIDSave" value="">
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>

	<br>
	<div id="divBillGrid">
 		<%
 			Bill billObj = new Bill();
 			out.print(billObj.readBill());
 		%>
	</div>
</div> </div> </div>
</body>
</html>