	$(document).ready(function() {

		showLayer();
		
		var jsonObject			= new Object();
		jsonObject["companyId"] =  $('#companyId').val();
		console.log('jsonObject : ', jsonObject);
		showLayer();
		$.ajax({
             url: "getCompanyPriviledgeDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 console.log('data : ', data);
            	 setData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	// window.location.replace("ClothInventory.in");
            	 hideLayer();
             }
	});
		
	
	});
	
	function setData(data){
		
		if(data.companyModulePrivileges != undefined && data.companyModulePrivileges.length >0){
			var companyModulePrivileges	= data.companyModulePrivileges;
			for(var i = 0; i<companyModulePrivileges.length;i++ ){
				$('#module_'+companyModulePrivileges[i].priviledgeId).show();
			}
		}
	}