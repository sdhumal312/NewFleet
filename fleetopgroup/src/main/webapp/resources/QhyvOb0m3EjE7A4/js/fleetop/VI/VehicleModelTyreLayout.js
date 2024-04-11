function createLayout(){
	var axleSize = Number($("#axle").val());
	$("#layoutTable").empty();
	if(axleSize > 0){
		for(var i = 1; i <= axleSize; i++ ){
			var columnArray = new Array();
			
			var tr = '<tr class="fit" id="axleRow'+(i)+'">'
					+'<td class="tyreSize"> <div class="column"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div><div class="column"  style="display: none;padding-right: 33px;"  id="dualLeft'+i+'"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>'
					+'<td class="steelSize2" id="steel'+i+'" style="padding-right: 20px;"> <div class="Steel"></div> <div class="SteelDown"></div> </td>'
					+'<td class="tyreSize"> <div class="column"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90"/> </div><div class="column" style="display: none;padding-right: 33px;" id="dualRight'+i+'"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>'
					+'<td class="tyreSize"></td>';
			
			if(i != 1){
				tr += '<td class="tyreCheckbox"><input type="checkbox" name="dualTyre" value="'+i+'" id="dualAxleId'+i+'" onclick="addDualTyre('+i+');">Duals</td>';
			}
			
			tr += '</tr><br><br>';
			
			columnArray.push(tr);
			
			columnArray.push('<tr class="fit" id="axle'+(i)+'"></tr>');
			$('#layoutTable').append(columnArray.join(' '));
		}
		
		$("#axle1").append('<td class="tyreSize"></td>'
				+'<td class="steelSize">'
				+'<div id="spearTyre" style="display: none;"><img src="resources/QhyvOb0m3EjE7A4/images/TyreWheel.png" class="img-rounded" alt="Tyre Photo" width="100" height="100" /></div>'
				+'</td>'
				+'<td class="tyreSize"></td>'
				+'<td class="steelSize2">'
				+'</td>'
				+'<td class="tyreSize"></td>'
				+'<td class="steelSize"></td>'
				+'<td class="tyreSize"></td>'
				+'<td class="tyreCheckbox"><input type="checkbox" id="spearTyreCheckId" onclick="addSpearTyre();">Stepney</td>');
	}
}

function addDualTyre(id){
	if($("#dualAxleId"+id+"").prop('checked')){
		$("#dualLeft"+id+"").show();
		$("#dualRight"+id+"").show();
		$("#steel"+id+"").addClass('steel');
	}else{
		$("#dualLeft"+id+"").hide();
		$("#dualRight"+id+"").hide();
		$("#steel"+id+"").removeClass('steel');
	}
}
function addSpearTyre(){
	if($("#spearTyreCheckId").prop('checked')){
		if($("#frontTryeModelId").val() != ""){
			if($("#spearTyreCheckId").prop('checked')){
				$('#spareTyreModelId').select2('data', {
					id : $("#frontTryeModelId").val(),
					text : $("#frontTryeModelId").select2('data').text
				});
			}
		}
		$("#spearTyre").show();
		$("#spareTyreModel").show();
		
	}else{
		$("#spareTyreModelId").select2("val", "");
		$("#spareTyreModel").hide();
		$("#spearTyre").hide();
	}
}

$(document).ready(function() {
    $("#frontTryeModelId,#rearTyreModelId,#spareTyreModelId").select2({
        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSubModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_MODEL_SUBTYPE, slug: a.slug, id: a.TYRE_MST_ID
                        }
                    }
                    )
                }
            }
        }
    })
});



function saveVehicleModelTyreLayout(){
	var numberOfAxle 			= Number($("#axle").val());
	var dualTyreArray	 		= new Array();
	var jsonObject				= new Object();
	var positionArray	 		= new Array();
	var modelIdArray	 		= new Array();
	if(numberOfAxle == 0){
		showMessage('info','Please Select Axle')
		return false;
	}
	
	if($("#frontTryeModelId").val() == "" ){
		showMessage('info','Please Select Front Tyre Model ')
		return false;
	}
	if($("#rearTyreModelId").val() == "" ){
		showMessage('info','Please Select Rear Tyre Model ')
		return false;
	}
	if($("#spearTyreCheckId").prop('checked')){
		if($("#spareTyreModelId").val() == "" ){
			showMessage('info','Please Select Spare Tyre Model ')
			return false;
		}
	}
	
	//dualTyre
	$('input[name*=dualTyre]:checked').each(function(){
		console.log(">>>",$(this).val());
		dualTyreArray.push($(this).val());
	});
	
	
	if($("#spearTyreCheckId").prop('checked')){
		jsonObject["spearTyre"]				= true;
	}else{
		jsonObject["spearTyre"]				= false;
	}
	jsonObject["vehicleModelId"]				= $('#vehicleModelId').val();
	jsonObject["numberOfAxle"]					= numberOfAxle;
	jsonObject["frontTryeModelId"]				= $('#frontTryeModelId').val();
	jsonObject["rearTyreModelId"]				= $('#rearTyreModelId').val();
	jsonObject["spareTyreModelId"]				= $('#spareTyreModelId').val();
	jsonObject["userId"]						= $('#userId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	for(var i = 1; i <= numberOfAxle; i++){// axle wise position every axle has 2 position left and right
		
		positionArray.push("LO-"+i)
		positionArray.push("RO-"+i)
		if(i == 1){
			modelIdArray.push($('#frontTryeModelId').val()); // because 1 axle has two tyre thats why modelid push 2 times
			modelIdArray.push($('#frontTryeModelId').val());
			
		}else{
			modelIdArray.push($('#rearTyreModelId').val());
			modelIdArray.push($('#rearTyreModelId').val());
		}
		for(var j = 0; j<dualTyreArray.length; j++){// if axle has dual-checked so that particular axle having 2 more tyre (total 4 tyre as per axle position) 
		
			switch (i) {
			case 1:
				if(dualTyreArray[j] == 1){
					positionArray.push("LI-1")
					positionArray.push("RI-1")
					modelIdArray.push($('#frontTryeModelId').val());
					modelIdArray.push($('#frontTryeModelId').val());
				}
				
				break;
			case 2:
				
				if(dualTyreArray[j] == 2){
					positionArray.push("LI-2")
					positionArray.push("RI-2")
					modelIdArray.push($('#rearTyreModelId').val());
					modelIdArray.push($('#rearTyreModelId').val());
				}
				
				break;
			case 3:
				
				if(dualTyreArray[j] == 3){
					positionArray.push("LI-3")
					positionArray.push("RI-3")
					modelIdArray.push($('#rearTyreModelId').val());
					modelIdArray.push($('#rearTyreModelId').val());
				}
				
				break;
			case 4:
				
				if(dualTyreArray[j] == 4){
					positionArray.push("LI-4")
					positionArray.push("RI-4")
					modelIdArray.push($('#rearTyreModelId').val());
					modelIdArray.push($('#rearTyreModelId').val());
				}
				break;
			case 5:
				if(dualTyreArray[j] == 5){
					positionArray.push("LI-5")
					positionArray.push("RI-5")
					modelIdArray.push($('#rearTyreModelId').val());
					modelIdArray.push($('#rearTyreModelId').val());
				}
				break;

			default:
				break;
			}
		}
	}
	
	if($("#spearTyreCheckId").prop('checked')){ // if spare part present 
		positionArray.push("SP-0");
		modelIdArray.push($('#spareTyreModelId').val());
		jsonObject["isSpareTyrePresent"]					= true;
	}else{
		jsonObject["isSpareTyrePresent"]					= false;
	}
	jsonObject["position"]					= positionArray.toString();
	jsonObject["positionModelId"]			= modelIdArray.toString();
	jsonObject["dualTyrePresentAxle"]		= dualTyreArray.toString();
	
	showLayer();
	$.ajax({
		url: "saveVehicleModelTyreLayout",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			if(data.vehicleModelTyreLayoutId != undefined){
				window.location.replace("showVehicleModelTyreLayout.in?id="+$('#vehicleModelId').val()+"");
			}else if(data.alredyExist != undefined && (data.alredyExist == true || data.alredyExist == 'true')){
				showMessage('Info', 'Already Exist');
				hideLayer();
			}else{
				showMessage('errors', 'Please Contact To System Admin');
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}



function setAllModel(){
	var flag = false;
	if($("#rearTyreModelId").val() > 0 || $("#spearTyreCheckId").val() > 0){
		flag = true;
	}
	console.log("flag",flag)
	if(flag){
		if(confirm('Are You Sure! Do You Want To Change All Tyre Model')){
			if($("#frontTryeModelId").val() != ""){
				$('#rearTyreModelId').select2('data', {
					id : $("#frontTryeModelId").val(),
					text : $("#frontTryeModelId").select2('data').text
				});
				
				if($("#spearTyreCheckId").prop('checked')){
					$('#spareTyreModelId').select2('data', {
						id : $("#frontTryeModelId").val(),
						text : $("#frontTryeModelId").select2('data').text
					});
				}
			}
		}
	}else{
		if($("#frontTryeModelId").val() != ""){
			$('#rearTyreModelId').select2('data', {
				id : $("#frontTryeModelId").val(),
				text : $("#frontTryeModelId").select2('data').text
			});
			
			if($("#spearTyreCheckId").prop('checked')){
				$('#spareTyreModelId').select2('data', {
					id : $("#frontTryeModelId").val(),
					text : $("#frontTryeModelId").select2('data').text
				});
			}
		}
	}
}