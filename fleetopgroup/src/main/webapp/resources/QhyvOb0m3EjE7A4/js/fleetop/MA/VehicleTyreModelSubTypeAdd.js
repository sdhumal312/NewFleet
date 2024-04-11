
$(document).ready(function() {
	$(".select2").select2();
	$("#tagPicker").select2({
		closeOnSelect : !1
	});
	$("#tyreModelSizeId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.TYRE_SIZE,
                            slug: a.slug,
                            id: a.TS_ID
                        }
                    })
                }
            }
        }
    });
	if($("#editTyreSizeId").val() != undefined ){
		$('#tyreModelSizeId').select2('data', {
			id : $("#editTyreSizeId").val(),
			text : $("#editTyreSize").val()
		});
	}
});
function editPerKmCost(cost, vehicleCostFixingId, TYRE_MST_ID){
	$('#editCost').modal('show');
	$('#vehicleCostFixingId').val(vehicleCostFixingId);
	$('#costPerKMEdit').val(cost);
	$('#TYRE_MST_ID').val(TYRE_MST_ID);
}

function validateTyreModeltype(){
	
	if($("#SubReType").val() == undefined || ($("#SubReType").val()).trim() == "" ){
		showMessage('info','Please Enter Model Name');
		return false;
	}
	if(($("#tyreModelSizeId").val() == undefined || $("#tyreModelSizeId").val() == "" || $("#tyreModelSizeId").val() == 0)&&( $("#tyreModelSizeIdConfig").val() == true || $("#tyreModelSizeIdConfig").val() == 'true') ){
		showMessage('info','Please Select Tyre Size');
		return false;
	}
	if(($("#gauageMeasurementLine").val() == undefined || $("#gauageMeasurementLine").val().trim() == "") && ($("#gauageMeasurementLineConfig").val() == true || $("#gauageMeasurementLineConfig").val() == 'true')){
		showMessage('info','Please Enter Gauge Measurement Line');
		return false;
	}
	if(($("#tyreGauge").val() == undefined || $("#tyreGauge").val().trim() == "" )&& ($("#tyreGaugeConfig").val() == true || $("#tyreGaugeConfig").val() == 'true' )){
		showMessage('info','Please Enter Tyre Guage');
		return false;
	}
	if(($("#ply").val() == undefined || $("#ply").val().trim() == "") && ($("#tyrePlyConfig").val() == true || $("#tyrePlyConfig").val() == 'true')){
		showMessage('info','Please Enter Ply');
		return false;
	}
	if(($("#psi").val() == undefined || $("#psi").val().trim() == "") && ($("#tyrePsiConfig").val() == true ||  $("#tyrePsiConfig").val() == 'true')){
		showMessage('info','Please Enter Psi');
		return false;
	}

}
