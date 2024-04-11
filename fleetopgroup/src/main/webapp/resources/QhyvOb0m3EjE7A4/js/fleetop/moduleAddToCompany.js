	$(document).ready(function() {

		showLayer();
		
		var jsonObject			= new Object();
		jsonObject["companyId"] =  $('#companyId').val();
		console.log('jsonObject : ', jsonObject);
		showLayer();
		$.ajax({
             url: "getCompanyModulePriviledgeDetails",
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
		var companyEncode = getUrlParameter('CID');
		var valcal = 'showMasterCompany.in?CID='+companyEncode;
		$("#cancel").prop("href", valcal);
		
		if(data.moduleprivileges != undefined && data.moduleprivileges.length > 0){
			var moduleprivileges = data.moduleprivileges;
			var div = '';
			for(var i=0; i<moduleprivileges.length; i++){
					div += '<tr style="border-bottom: 1px;"><div class="row1">'
					      + '<td><label class="L-size control-label">'+moduleprivileges[i].displayName+' : </label></td>'
					      +'<td><div>'
					      	+'<input type="checkbox" id="module_'+moduleprivileges[i].modulePrivilegesId+'" name="modulePrivileges" value="'+moduleprivileges[i].modulePrivilegesId+'">'
					      +'</div></td></div></tr>'	
			}
			$('#tableBody').append(div);
			
			if(data.companymodulePrivileges != undefined && data.companymodulePrivileges.length > 0){
				var companymodulePrivileges = data.companymodulePrivileges;
				for(var j=0 ; j<companymodulePrivileges.length; j++){
						var priviledge = Number(companymodulePrivileges[j].priviledgeId);
						$('#module_'+priviledge+'').prop('checked', true);
				}
			}
		}
	}
	
	function saveCompanyModulePrivilges(){
		var privilegesIds = '';
		$('input[name*=modulePrivileges]' ).each(function(){
			var vehicleVal = this.id;
			console.log('vehicleVal : '+vehicleVal);
			if (this.checked) {
				privilegesIds += $('#'+vehicleVal).val()+',';
	        }
		});
		console.log('privilegesIds : ', privilegesIds);
		
		var jsonObject			= new Object();
		
		jsonObject["privilegesIds"] 						=  privilegesIds;
		jsonObject["companyId"] 							=  $('#companyId').val();
		
		showLayer();
		$.ajax({
	             url: "saveCompanyModulePriviledgeDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 console.log('data : ', data);
	            	/*if(data.invoiceId != undefined) {
	            		emptyAddClothPage(data);			            		
	            	} else {
	            		showMessage('errors', data.saveMessage);			            		
	            	}*/
	            	var companyEncode = getUrlParameter('CID');
	            	console.log('companyEncode : ', companyEncode);
	            	 showMessage('success', 'Date Saved Successfully!');
	            	 window.location.replace("showMasterCompany?CID="+companyEncode+"&pSaved=true");
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
		
	}