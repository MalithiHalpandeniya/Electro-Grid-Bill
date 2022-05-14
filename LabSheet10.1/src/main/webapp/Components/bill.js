$(document).ready(function()
{
	 $("#alertSuccess").hide();
 	 $("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();

	// Form validation-------------------
	var status = validateBillForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 return;
 	}
 	
	// If valid-------------------------
 	var type = ($("#hidbillIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
 	{
 		url : "BillAPI",
 		type : type,
 		data : $("#formBill").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onBillSaveComplete(response.responseText, status);
 		}
 	}); 
 });

function onBillSaveComplete(response, status)
	{
		if (status == "success")
		{
			 var resultSet = JSON.parse(response);
 			 if (resultSet.status.trim() == "success")
			 {
 				$("#alertSuccess").text("Successfully saved.");
 				$("#alertSuccess").show();
 				$("#divBillGrid").html(resultSet.data);
 			 } 
 			 else if (resultSet.status.trim() == "error")
			 {
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			 }
 		} 
 		else if (status == "error")
 		{
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 		} 
 		else
 		{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
 		}
		$("#hidbillIDSave").val("");
 		$("#formBill")[0].reset();
}

	// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		 $("#hidbillIDSave").val($(this).data("billID"));
		
		 $("#billCode").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#userID").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#dueMonth").val($(this).closest("tr").find('td:eq(2)').text());
 		 $("#units").val($(this).closest("tr").find('td:eq(3)').text());
	});
	
	
	
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "BillAPI",
 			type : "DELETE",
 			data : "billID=" + $(this).data("billID"),
 			dataType : "text",
 			complete : function(response, status)
 			{
 				onBillDeleteComplete(response.responseText, status);
 			}
 		});
	});


	function onBillDeleteComplete(response, status)
	{
		if (status == "success")
 		{
 			var resultSet = JSON.parse(response);
 			if (resultSet.status.trim() == "success")
 			{
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				$("#divBillGrid").html(resultSet.data);
 			} 
 			else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} 
 		else if (status == "error")
 		{
 				$("#alertError").text("Error while deleting.");
 				$("#alertError").show();
 		} 
 		else
 		{
 				$("#alertError").text("Unknown error while deleting..");
 				$("#alertError").show();
 		}
}
	

	// CLIENT-MODEL================================================================
	function validateBillForm()
	{
		// Bill Code
		
		if ($("#billCode").val().trim() == "")
		{
 			return "Insert Bill Code.";
 		}

		// User ID
		if ($("#userID").val().trim() == "")
 		{
 			return "Insert User ID.";
 		}

		// Due Month
		if ($("#dueMonth").val().trim() == "")
 		{
 			return "Insert Due Month.";
 		}
 		
		// Units ---- is numerical value
		var units = $("#units").val().trim();
		if (!$.isNumeric(units))
		{
 			return "Insert a numerical value for Units.";
 		}
 		
		// convert to decimal price
		//$("#units").val(parseFloat(tmpPrice).toFixed(2));*/


		return true;
	}
	