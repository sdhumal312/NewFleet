$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["subCompanyId"]					= $('#subCompanyId').val();
	$.ajax({
		url: "getSubCompanyById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setSubCompanyDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setSubCompanyDetails (data){
	var subCompany = data.subCompany;
	
	if(subCompany != null){
		$('#subCompanyName').val(subCompany.subCompanyName);               
		$('#subCompanyType').val(subCompany.subCompanyType);               
		$('#subCompanyAddress').val(subCompany.subCompanyAddress);
		$("#subCompanyCountry").append($("<option>").text(subCompany.subCompanyCountry).attr("value",subCompany.subCompanyCountry));
		$('#subCompanyCountry').val(subCompany.subCompanyCountry);            
		$("#subCompanyState").append($("<option>").text(subCompany.subCompanyState).attr("value",subCompany.subCompanyState));
		$('#subCompanyState').val(subCompany.subCompanyState);            
		$("#subCompanyCity").append($("<option>").text(subCompany.subCompanyCity).attr("value",subCompany.subCompanyCity));
		$('#subCompanyCity').val(subCompany.subCompanyCity);            
		$('#subCompanyPinCode').val(subCompany.subCompanyPinCode);            
		$('#subCompanyWebsite').val(subCompany.subCompanyWebsite);            
		$('#subCompanyEmail').val(subCompany.subCompanyEmail);              
		$('#subCompanyMobileNumber').val(subCompany.subCompanyMobileNumber);       
		$('#subCompanyTanNo').val(subCompany.subCompanyTanNo);              
		$('#subCompanyPanNo').val(subCompany.subCompanyPanNo);              
		$('#subCompanyTaxNo').val(subCompany.subCompanyTaxNo);              
		$('#subCompanyTinNo').val(subCompany.subCompanyTinNo);              
		$('#subCompanyCinNo').val(subCompany.subCompanyCinNo);              
		$('#subCompanyAbout').val(subCompany.subCompanyAbout);              
	}
}

$(document).ready(
		function($) {
			$('button[id=updateSubCompany]').click(function(e) {
	
				showLayer();
				if($('#subCompanyName').val().trim() == ''){
					$('#subCompanyName').focus();
					showMessage('errors', 'Please Enter Sub Company Name !');
					hideLayer();
					return false;
				}
				
				var jsonObject								= new Object();
			
				jsonObject["subCompanyId"]						= $('#subCompanyId').val();
				jsonObject["subCompanyName"]					= $('#subCompanyName').val().trim();
				jsonObject["subCompanyType"]					= $('#subCompanyType').val();
				jsonObject["subCompanyAddress"]					= $('#subCompanyAddress').val();
				jsonObject["subCompanyCountry"]					= $('#subCompanyCountry').val();
				jsonObject["subCompanyState"]					= $('#subCompanyState').val();
				jsonObject["subCompanyCity"]					= $('#subCompanyCity').val();
				jsonObject["subCompanyPinCode"]					= $('#subCompanyPinCode').val();
				jsonObject["subCompanyWebsite"]					= $('#subCompanyWebsite').val();
				jsonObject["subCompanyEmail"]					= $('#subCompanyEmail').val();
				jsonObject["subCompanyMobileNumber"]			= $('#subCompanyMobileNumber').val();
				jsonObject["subCompanyTanNo"]					= $('#subCompanyTanNo').val();
				jsonObject["subCompanyPanNo"]					= $('#subCompanyPanNo').val();
				jsonObject["subCompanyTaxNo"]					= $('#subCompanyTaxNo').val();
				jsonObject["subCompanyTinNo"]					= $('#subCompanyTinNo').val();
				jsonObject["subCompanyCinNo"]					= $('#subCompanyCinNo').val();
				jsonObject["subCompanyAbout"]					= $('#subCompanyAbout').val();
				
				$.ajax({
					url: "updateSubCompanyDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.alreadyExist == true || data.alreadyExist == 'true'){
							showMessage('info','Already Exist')
						}else{
							showMessage('success','Sub Company Updated Successfully')
						}
						window.location.replace("newCompany");
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	
