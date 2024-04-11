$(document).ready(function() { $("#typeTable").DataTable({ sScrollX: "100%", bScrollcollapse: !0, dom: "Blfrtip", buttons: ["excel", "print"] }) })

function submitVehicleBodyMaker() {
	showLayer();
	let bodyMaker = $('#bodyMaker').val();
	if (bodyMaker.trim() == '') {
		hideLayer();
		showMessage('info', 'Please Add Vehicle Maker Name');
		return false;
	}
	var jsonObject = new Object();
	jsonObject["bodyMaker"] = bodyMaker;
	jsonObject["companyId"] = $("#companyId").val();
	showLayer();
	$.ajax({
		url: "saveVehicleBodyMaker",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function(data) {
			if (data.saved == true) {
				showMessage('success', 'Saved Successfully !!!');
				setTimeout(() => {
					location.reload();
					hideLayer();
				}, 2000);
			} else if (data.duplicate == true) {
				showMessage('info', 'Duplicate entry , please enter unique name !');
				hideLayer();
			} else if (data.invalidName == true) {
				showMessage('info', 'Please Add Vehicle Maker Name');
				hideLayer();
			} else {
				hideLayer();
			}
		},
		error: function() {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function deleteVehicleBodyMaker(id) {
	if (confirm('Are you sure ? ')) {
		showLayer();
		var jsonObject = new Object();
		jsonObject['id'] = id;
		jsonObject['companyId'] = $('#companyId').val();
		$.ajax({
			url: 'deleteVehicleBodyMaker',
			type: 'POST',
			dataType: 'json',
			data: jsonObject,
			success: function(data) {
				if (data.success == true) {
					showMessage('success', ' Record deleted successfully !');
					setTimeout(() => {
						location.reload();
						hideLayer();
					}, 2000)
				}else if(data.used == true ){
					showMessage('info','Can not deleted used Vehicle Body Maker ');
					hideLayer();
				}
				else{
					hideLayer();	
				}
			},
			error: function(e) {
				hideLayer();
				console.log('error : ', e);
				showMessage('error', 'some error occured');
			}
		});
	}
}
function editVehicleBodyMaker(id, name) {
	$('#headerText').text('');
	$('#headerText').text('Update Vehicle Body Maker');
	$('#editBodyMakerId').val(id);
	$('#bodyMaker').val(name);
	$('#updateButton').show();
	$('#addButton').hide();
	$('#addBodyMaker').modal('show');
}

function showCreateBodyMaker() {
	$('#headerText').text('');
	$('#headerText').text('Add Vehicle Body Maker');
	$('#bodyMaker').val('');
	$('#addButton').show();
	$('#updateButton').hide();
	$('#addBodyMaker').modal('show');
}

function updateVehicleBodyMaker(){
	showLayer();
	
	if($('#bodyMaker').val().trim() ==''){
		showMessage('Please Enter Vehicle body Maker Name to Process Further !! ');
		hideLayer();
		return false;
	}
	var jsonObject = new Object();
	
	jsonObject['editBodyMakerId'] 	= $('#editBodyMakerId').val();
	jsonObject['bodyMaker'] 		= $('#bodyMaker').val();
	$.ajax({
		url :'updateVehicleBodyMaker',
		type :'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			if(data.success == true){
				showMessage('success','Record Upadted Successfully !');
				setTimeout(()=>{
					location.reload();
					hideLayer();
				},2000);
			}else if(data.notFound == true){
				showMessage('info','Record Not Found !! ');
				setTimeout(()=>{
					location.reload();
					hideLayer();
				},1000)
			}else{
				hideLayer();
			}
		},
		error: function(e){
		console.log(e);
		showMessage('error','Some error occured');	
		hideLayer();
		}
	})
	
}