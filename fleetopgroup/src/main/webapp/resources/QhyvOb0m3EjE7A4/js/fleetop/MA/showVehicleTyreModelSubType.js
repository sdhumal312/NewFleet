$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["companyId"]					= $('#companyId').val();
	jsonObject["TYRE_MST_ID"]				= $('#TYRE_MST_ID').val();
	
	
	$.ajax({
		url: "getVehicleTyreModelSubTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setVehicleTyreModelSubTypeDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function setVehicleTyreModelSubTypeDetails(data){
	var tyreModelDetails = data.tyreModelDetails;
	console.log("tyreModelDetails",tyreModelDetails)
	if(tyreModelDetails != null && tyreModelDetails != undefined){
		
		$("#tyreModelName").html(tyreModelDetails.tyre_MODEL_SUBTYPE);
		$("#tyreModelName1").html(tyreModelDetails.tyre_MODEL_SUBTYPE);
		$("#tyreManufacturerName").html(tyreModelDetails.tyre_MODEL);
		$("#tyreModelType").html(tyreModelDetails.tyreModelType);
		$("#tyreModelSize").html(tyreModelDetails.tyreModelSize);
		$("#gauageMeasurementLine").html(tyreModelDetails.gauageMeasurementLine);
		$("#tyreGuage").html(tyreModelDetails.tyreGauge);
		$("#tyreTubeTypeName").html(tyreModelDetails.tyreTubeType);
		$("#ply").html(tyreModelDetails.ply);
		$("#psi").html(tyreModelDetails.psi);
		$("#tyreWarranty").html(tyreModelDetails.warrantyPeriod);
		$("#tyreWarrantyTerms").html(tyreModelDetails.warrentyterm);
		$("#remark").html(tyreModelDetails.tyre_MODEL_DESCRITION);
		$("#costPerKm").html(tyreModelDetails.costPerKM);
	}
}