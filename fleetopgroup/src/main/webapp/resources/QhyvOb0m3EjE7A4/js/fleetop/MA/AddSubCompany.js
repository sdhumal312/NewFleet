$(document).ready(
		function($) {
			$('button[id=saveSubCompany]').click(function(e) {
	
				showLayer();
				if($('#subCompanyName').val().trim() == ''){
					$('#subCompanyName').focus();
					showMessage('errors', 'Please Enter Sub Company Name !');
					hideLayer();
					return false;
				}
				
				var jsonObject								= new Object();
			
				jsonObject["subCompanyName"]					= $('#subCompanyName').val();
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
					url: "saveSubCompanyDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.alreadyExist == true || data.alreadyExist == 'true'){
							showMessage('info','Already Exist')
						}else{
							showMessage('success','Sub Company Added Successfully')
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

