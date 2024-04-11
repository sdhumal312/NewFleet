$(document).ready(function(){
	$("#tripIncomeTable").DataTable({
		sScrollX:"100%",
		bScrollcollapse:!0,
		dom:"Blfrtip",
		buttons:["excel","print"]}
	)
});


function openModal(){
	$('#addTripIncomeWithRate').modal('show');
	
	$('#incomeName').val('');            
	$('#discription').val('');    
	$('#commision').val(0.0);             
	$('#tax').val(0.0);   
}


function editPopup(id){
	var jsonObject							= new Object();

	jsonObject["incomeID"]					= id;
	
	$.ajax({
		url: "/getTripIncomeById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#incomeId').val(id);  
			$('#editIncomeName').val(data.tripIncome.incomeName);            
			$('#editDiscription').val(data.tripIncome.incomeRemarks);    
			$('#editCommision').val(data.tripIncome.commission);             
			$('#editTax').val(data.tripIncome.gst);   
			$('#editTripIncomeWithRate').modal('show');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function addTripIncomeWithRate(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["incomeType"]				= $('#incomeType').val();
	jsonObject["incomeName"]				= $('#incomeName').val();
	jsonObject["discription"]				= $('#discription').val().trim();
	jsonObject["commision"]					= $('#commision').val();
	jsonObject["gst"]						= $('#tax').val();
	
	if($('#incomeName').val().trim() == ''){
		$('#incomeName').focus();
		showMessage('errors', 'Please Select Tyre Income Name !');
		hideLayer();
		return false;
	}
	
	if($('#commision').val() >= 100){
		showMessage('errors', 'Please Check The Commision !');
		return false;
	}
	if($('#tax').val() >= 100){
		showMessage('errors', 'Please Check The Commision !');
		return false;
	}
	
	$.ajax({
		url: "/addTripIncomeWithRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success', 'New Tyre Expense Saved Successfully!');
			location.reload(); 
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function updateTripIncomeWithRate(){
	
	
	showLayer();
	var jsonObject							= new Object();
	
	jsonObject["incomeID"]					= $('#incomeId').val();  
	jsonObject["incomeType"]				= $('#editIncomeType').val();  
	jsonObject["incomeName"]				= $('#editIncomeName').val();            
	jsonObject["discription"]				= $('#editDiscription').val();   
	jsonObject["commision"]					= $('#editCommision').val();             
	jsonObject["gst"]						= $('#editTax').val();  
	
	$.ajax({
		url: "/updateTripIncomeWithRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success', 'New Tyre Expense Saved Successfully!');
			location.reload(); 
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


function tripIncmeValidate(){
	
if($("#pcName").val() == undefined || ($("#pcName").val()).trim() == "" ){
		showMessage('info','Please Enter Income Name')
		return false;
	}
}