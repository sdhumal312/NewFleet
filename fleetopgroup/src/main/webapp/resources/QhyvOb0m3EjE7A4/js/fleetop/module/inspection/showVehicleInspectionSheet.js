function removevehicleType(inspectionSheetID,vehicleTypeId,branchId){
	
	var jsonObject = new Object();
	jsonObject["inspectionSheetId"] = inspectionSheetID;
	jsonObject["vehicleTypeId"] =vehicleTypeId;
	jsonObject["branchId"] =branchId;
			$.ajax({
				url : "removeInspectionByVehicleType",
				type : "POST",
				datatype : "json",
				data : jsonObject,
				success : function(data){
					showMessage('success', 'Data saved successfully !');
					location.reload();
			
		},error : function(e){
			showMessage('error', 'Some error occured !');
			
		}
		
	})
	
}