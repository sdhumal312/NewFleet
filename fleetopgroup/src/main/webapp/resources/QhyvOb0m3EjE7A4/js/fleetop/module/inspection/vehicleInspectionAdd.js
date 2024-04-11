$(document).ready(function() {
	$('input[type=file]').unbind('change');
	//Result1();
	if($('#failedParameterPenalty').val() == true || $('#failedParameterPenalty').val() == "true"){
		var oldTotalPenalty= $('#oldTotalPenalty').val();
		$('#totalPenalty').val(oldTotalPenalty);
	}
});
function validateINspectionFormToSave(saveTypeId){
	var photoRequired	= "";
	var fileNames = "";
	var parameterIds	= "";
	var parameterName = "";
	var required = "YES";
	
	$('input[name*=testResult]:checked').each(function(){
		var parameterId = $( this ).attr( "id" ).split('_');
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		 photoRequired	+= vehicleVal+"," ;
		 parameterIds +=parameterId[1]+",";
	});
	
	$('input[name*=input-file-preview]').each(function(){
		
		var file = $("#"+$( this ).attr( "id" )).val();
		
		if(file.trim() != ''){
			var fileArray = file.split("\\");
			
			var parameterId = $( this ).attr( "id" ).split('_');
			fileNames	+= fileArray[fileArray.length - 1]+"," ;
		}
	});	
	
	
	var fileNotSelected = false;
	var checkallParameter = [];
	var failedParameter = [];
	var totalParam = [];
	var x = 0;
	var z = 0; 
	var y= 0;
	$('input[name*=inspectionSheetToParameterId]').each(function(){
		var parameterId = $("#"+$( this ).attr( "id" )).val();
		totalParam[y++] = parameterId;	
		
			var test1 = $('#testResultYes_'+parameterId).is(':checked');
			var test2 = $('#testResultNo_'+parameterId).is(':checked');
			if(test1 == false && test2 == false){
				checkallParameter[z++] =1;
			}
			if(test2 || test2 == 'true'){
				
				failedParameter[x++] =1;
			}
		
	});
	
	if($('#failedParameterPenalty').val() == true || $('#failedParameterPenalty').val() == "true"){
	if(failedParameter.length > 0 && $('#totalPenalty').val() <= $('#penaltyAmountDouble').val()-1){
		
		showMessage('errors', "Total penalty can not be less than 1000");
		return false;
	}
	}
	var checkallParameterLength = checkallParameter.length;
	var totalParamLength = totalParam.length;
	if(checkallParameterLength == totalParamLength){
		fileNotSelected = true;
	}
//	if(fileNotSelected){
//		showMessage('errors', "Please Provide Test Result for Successful Inspection Of vehicle");	
//		return false;
//	}
	
	
	
	var isNoFile = false;
	var fileNotSelected = false;
	$('input[name*=input-file-preview]').each(function(){
		
		var filePic = $("#"+$( this ).attr( "id" )).val();
		var photoReqId = $( this ).attr( "id" ).split("_")[1];
		var photo = $("#photo_"+photoReqId).val();
		var failedPhoto = $("#failPhoto_"+photoReqId).val();
		
		
		var p1 = $('#testResultYes_'+photoReqId).is(':checked');
		var p2 = $('#testResultNo_'+photoReqId).is(':checked');
		if(p1 == true || p2 == true){
		
		if(photo == required){
			 	
			if(filePic == ''){
				isNoFile = true;
				parameterName = $("#paramName_"+photoReqId).val();
				return false;
			}
		 }
		else if(($('#mandatoryRemarkDocIfFail').val() == true || $('#mandatoryRemarkDocIfFail').val() == 'true')&& photo != 'YES' && failedPhoto == required ){
			if(filePic == ""){
				isNoFile = true;
				parameterName = $("#paramName_"+photoReqId).val();
				return false;
			}
		}
		}
	});	
		if(isNoFile){
			showMessage('errors', "Selection of Image is Mandatory for succesfull Inspection of Vehicle. </br>Please Select Image For Parameter"+" "+parameterName+"!");	
			return false;
		}
		
		
		var isNoRemark = false;	
	$('[name*=remark]').each(function(){
		var remarkText1 = ($("#"+$( this ).attr( "id" )).val()).trim();
		var remarkTextId = $( this ).attr( "id" ).split("_")[1];
		var text = $("#remarkText_"+remarkTextId).val();
		var failedtext = $("#failRemarkText_"+remarkTextId).val();
		
		var t1 = $('#testResultYes_'+remarkTextId).is(':checked');
		var t2 = $('#testResultNo_'+remarkTextId).is(':checked');
		if(t1 == true || t2 == true){
		
		if(text == required){
			if(remarkText1 == ""){
				isNoRemark = true;
				parameterName = $("#paramName_"+remarkTextId).val();
				return false;
			}
		 }
		
		else if(($('#mandatoryRemarkDocIfFail').val() == true || $('#mandatoryRemarkDocIfFail').val() == 'true')&& text != 'YES' && failedtext == required){
			if(remarkText1 == ""){
				isNoRemark = true;
				parameterName = $("#paramName_"+remarkTextId).val();
				return false;
			}
		}
		}
		
	});	
		if(isNoRemark){
			showMessage('errors', "Selection of Text is Mandatory for succesfull Inspection of Vehicle. </br>Please Select Text For Parameter"+" "+parameterName+"!");	
			return false;
			}
		
	
		if(saveTypeId == 2){	
		var isNoButton = false;
		$('input[name*=inspectionSheetToParameterId]').each(function(){
			var parameterId = $("#"+$( this ).attr( "id" )).val();
			
			var isManadatory =  $('#isMandatory_'+parameterId).val();
			
			if(isManadatory == required){
				var test1 = $('#testResultYes_'+parameterId).is(':checked');
				var test2 = $('#testResultNo_'+parameterId).is(':checked');
				if(test1 == false && test2 == false){
					isNoButton = true;
					parameterName = $("#paramName_"+parameterId).val();
					return false;
				}
			}
			
		});
		if(isNoButton){
			showMessage('errors', 'Please Select Test Result For Parameter'+" "+parameterName+"!");	
			return false;
			}
	}	
	
	
	
		$('#fileNames').val(fileNames);
		$('#testResult').val(photoRequired);
		$('#saveTypeId').val(saveTypeId);
		$('#parameterIds').val(parameterIds);
		
	return true;

}
function validateInspectionForm(saveTypeId){
	showLayer();
	$('#saveInsp').hide();
	if(!validateINspectionFormToSave(saveTypeId)){
		$('#saveInsp').show();
		hideLayer();
		return false;
	}
	return true;
}

function setVehicleInspectedDetails(data){
	
	showLayer();
	var inspectedData = JSON.parse(data);
	
	for(var i = 0; i< inspectedData.length; i++){
		console.log('data : ',inspectedData[i] );
		if(inspectedData[i].isInspectionSuccess == 1){
				$('#testResultYes_'+inspectedData[i].inspectionParameterId).prop("checked", true);
			
		}else if(inspectedData[i].isInspectionSuccess == 2){
			$('#testResultNo_'+inspectedData[i].inspectionParameterId).prop("checked", true);
		}
		
		if(inspectedData[i].documentUploaded == true || inspectedData[i].documentUploaded == 'true'){
			console.log(inspectedData[i].documentUploaded ,"inspectedData[i].documentUploaded ")
			/*$('#file_'+inspectedData[i].inspectionParameterId).hide();*/
			if($('#mandatoryRemarkDocIfFail').val() == true || $('#mandatoryRemarkDocIfFail').val() == 'true'){
					$('#alredyUpload_'+inspectedData[i].inspectionParameterId).val("YES");
			}
			$('#photo_'+inspectedData[i].inspectionParameterId).val("NO");
			 $('#file_'+inspectedData[i].inspectionParameterId).attr('readonly','readonly');
			 $('#fileDiv'+inspectedData[i].inspectionParameterId).append('<div>file Uploaded !</div>');//'<input type="text" readonly="readonly" value="Uploaded..">'
			 
		}
		
		$('#remark_'+inspectedData[i].inspectionParameterId).val(inspectedData[i].description);
		$('#completionToParameterId_'+inspectedData[i].inspectionParameterId).val(inspectedData[i].completionToParameterId);
		
		
	}
	
	hideLayer();
}

function Result1(){
	var arr = [];
	var arrYes = [];
	var arrNo = [];
	var arrNotSelected = [];
	var totalPenalty = 0;
	i = 0;
	j=0;
	k=0;
	l=0;
	
	$('input[name*=inspectionSheetToParameterId]').each(function(){
		
		var parameterId1 = $("#"+$( this ).attr( "id" )).val();
		arr[i++] = parameterId1;
		
		var test1 = $('#testResultYes_'+parameterId1).is(':checked');
		var test2 = $('#testResultNo_'+parameterId1).is(':checked');
		
		if(test1 == false && test2 == false){
			arrNotSelected[j++] = 1;
		}
		
		if((test1 == true && test2 == false) || (test1 == false && test2 == true)){
			if(test1 == true){
				arrYes[k++] = 1;
				$('#penalty_'+parameterId1).text("0");
				$('#penalty_'+parameterId1).hide()
				
				if($('#mandatoryRemarkDocIfFail').val() == true || $('#mandatoryRemarkDocIfFail').val() == 'true'){
					if($('#photo_'+parameterId1).val() != 'YES' && $('#alredyUpload_'+parameterId1).val() != 'YES'  )
					{
						$('#failPhoto_'+parameterId1).val($('#photo_'+parameterId1).val());
						$('#requiredFile_'+parameterId1).html('');
					}
					$('#failRemarkText_'+parameterId1).val($('#remarkText_'+parameterId1).val());
					if($('#remarkText_'+parameterId1).val() != 'YES' )
					{
						$('#requiredText_'+parameterId1).html('');
					}
				}
				
			} else{
				arrNo[l++] = 1;
				$('#penalty_'+parameterId1).text($('#penaltyAmountDouble').val());
				$('#penalty_'+parameterId1).hide()
				totalPenalty =$('#penaltyAmountDouble').val();
				
				if($('#mandatoryRemarkDocIfFail').val() == true || $('#mandatoryRemarkDocIfFail').val() == 'true'){
						if($('#photo_'+parameterId1).val() != 'YES' && $('#alredyUpload_'+parameterId1).val() != 'YES' ){
							$('#failPhoto_'+parameterId1).val('YES');
							$('#requiredFile_'+parameterId1).html('*');
						}
					if($('#remarkText_'+parameterId1).val() != 'YES' )
					{
						$('#failRemarkText_'+parameterId1).val('YES');
						$('#requiredText_'+parameterId1).html('*');
					}
				}
			}
		}
		if($('#failedParameterPenalty').val() == true || $('#failedParameterPenalty').val() == "true"){
			$('#totalPenalty').val(totalPenalty);
		}
		
	});
	
	var yesParameter = arrYes.length;
	var noParameter = arrNo.length;
	var totalParameter = arr.length;
	var notSelectedParameter = arrNotSelected.length;
	var selected = totalParameter - notSelectedParameter ;
	
	
	var yesPercent = (yesParameter / selected) * 100;
	var noPercent =  (noParameter / selected) * 100;
	var notTestedPercent = (notSelectedParameter / totalParameter) * 100;
	
	if($('#failedParameterPenalty').val() == true || $('#failedParameterPenalty').val() == "true" ){
		console.log("inside main if ",noParameter)
	if(noParameter <= 0 ){
		$('#totalPenalty').val(0);
		$('#failedParameterPenaltyBox').hide();
	}else{
		$('#failedParameterPenaltyBox').show();
	}
	}
	
	$("#passPercent").html(yesPercent.toFixed(2));
	$("#failPercent").html(noPercent.toFixed(2));
	$("#notTestResultPercent").html(notTestedPercent.toFixed(2));
	
}

$(document).ready(function() {
	
	$('input[name*=inspectionSheetToParameterId]').each(function(){
		var parameterId = $("#"+$( this ).attr( "id" )).val();
		
		var isManadatory =  $('#isMandatory_'+parameterId).val();
		var photo 		 =  $("#photo_"+parameterId).val();
		var text         =  $("#remarkText_"+parameterId).val();
		
		if(isManadatory == 'YES'){
			$("#required_"+parameterId).removeClass("hide");
		}
		
		if(photo != 'YES'){
		$("#requiredFile_"+parameterId).html("");
		}
		
		if(text != 'YES'){
			$("#requiredText_"+parameterId).html("");
		}
		
	})
   
});



function showImageList(id){
	
	showLayer();
	var jsonObject = new Object();
	jsonObject["completionToParameterId"] = id;


	$.ajax({
		url : "getParameterDocumentList",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			hideLayer();
			setparameterImageList(data,id);
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}


	});

}


function setparameterImageList(data,id){
	
	if(data.documents != undefined && data.documents.length > 0){
		$('#wotaskTopartId').val(id);
		var documentList = data.documents;
		$('#Popup').modal('show');
		$('#documentTable').empty();
		var thead 	=$('<thead>');
		var tr1 	= $('<tr>');
		
		var th1		= $('<th class="fit ar">');
		var th2     =$('<th class="fit ar">');
		var th3     =$('<th class="fit ar">');
		var th4     =$('<th class="fit ar">');
		
		tr1.append(th1.append("Document name"));
		tr1.append(th3.append("Document"));
		
		thead.append(tr1);
		var tbody =$('<tbody>');
		
		for(var i=0;i<documentList.length;i++){
			
			var atag=' <a target="_blank" data-toggle="tip" data-original-title="Click Download" href="/downloadParameterDocument/'+documentList[i]._id +'.in "> <i class="fa fa-download"></i> Download  </a>';
			var adelete=' <a  data-toggle="tip" data-original-title="Delete File" onclick ="deleteWorkOTaskToPartDoc('+documentList[i]._id +') "> <i class="fa fa-trash"></i> Remove </a>';
			var tr1 =$('<tr>');
			var td1 =$('<td class="fit ar">');
			var td2 =$('<td class="fit ar">');
			var td3 =$('<td class="fit ar">');
			var td4 =$('<td class="fit ar">');
			tr1.append(td1.append(documentList[i].name));
			tr1.append(td2.append(atag));
			
			tbody.append(tr1);
			
		}
		$("#documentTable").append(thead);
		$("#documentTable").append(tbody);
		$("#documentTable").show();
	}else{
		hideLayer();
		$('#Popup').modal('hide');
		location.reload();
	}

	
}



