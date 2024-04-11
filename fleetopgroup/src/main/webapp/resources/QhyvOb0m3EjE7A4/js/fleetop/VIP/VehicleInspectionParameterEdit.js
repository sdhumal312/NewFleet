$(document).ready(function() { 
	var ISID 			= Number($("#ISID_id").val());// Inspection Sheet Id
	var jsonObject		= new Object();
	var parametereList	= new Array();
	showLayer();
	$.getJSON("getInspectionParameterList.in", function(e) {
		parametereList	= e;//To get All Inspection Parameter 
		
	});
	
	jsonObject["vehicleInspectionSheetId"]			= ISID;
	
	$.ajax({
		url: "getInspectionSheetDetailsById.do",//get All Inspection Parameter Details Which Are Assign to Perticulat  the sheet 
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#inspectionSheetName').val(data.inspectionSheetDetails.inspectionSheetName);//Inspection Sheet Name
			
			var table = $("<table border='0' width='100%' style='margin-top:20px;' id ='inspectionParameterTable'> ");
			var sheetToParameterDtoList	= data.sheetToParameterDtoList;
			if(sheetToParameterDtoList != undefined){
				
				for(var i = 0; i < sheetToParameterDtoList.length; i++ ) {
					
					var tr = $("<tr id = 'inspectionSheet_"+i+"'>")
					
					var td1	= $("<td width='20%'>");
					var td2	= $("<td width='20%'>");
					var td3	= $("<td width='20%'>");
					var td4	= $("<td width='20%'>");
					var td5	= $("<td width='20%'>");
					var td6	= $("<td width='20%'>");
					
					td1.append('<select class="form-text select2" name="inspectionParameter" id="Expense_'+i+'" onchange="validateInspectionParameter(this)" ></select>')
					td2.append('<label>Frequency </label><input type="number" class="form-text" name="frequency" id="frequency_'+i+'" placeholder="Frequency in Days" min="1"><input type="hidden" class="form-text" name="ISD" id="inspectionSheetToParameterId_'+i+'">')
					td3.append('<label>is Mandatory :</label><select class="form-text select2" name="requiredType" id="requiredType_'+i+'" ><option value="false">NO</option><option value="true">YES</option> </select>')
					td4.append('<label class="control-label">Photo Needed : </label> <label class="radio-inline"> <input name="photoGroup_'+i+' " id="inputPhotoGroupYes_'+i+'"  value="true" type="radio" checked  />Yes </label> <label class="radio-inline"><input name="photoGroup_'+i+' " id="inputPhotoGroupNo_'+i+'"  value="false" type="radio" checked  />No</label>')
					td5.append('<label class="control-label">Text Needed : </label> <label class="radio-inline"> <input name="textGroup_'+i+' " id="textGroupYes_'+i+'"  value="true" type="radio" checked  />Yes </label> <label class="radio-inline"><input name="textGroup_'+i+' " id="textGroup_'+i+'"  value="false" type="radio" checked  />No</label>')
					td6.append('<a onclick="return remove('+sheetToParameterDtoList[i].inspectionSheetToParameterId+','+i+');" id ="remove_'+i+'"class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>')
					
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
					tr.append(td5);
					tr.append(td6);
					
					table.append(tr);
					$("#addParameter").append(table);
					$("#Expense_"+i).empty();
					$("#Expense_"+i).append($("<option>").text("Please Select ").attr("value", 0));// Default 
					
					var ISD		=	sheetToParameterDtoList[i].inspectionParameterId;
					for(var k = 0; k < parametereList.length; k++){// Append Inspection parameter to the (Inspection Parameter DropDown)
						if(parametereList[k].inspectionParameterId == ISD){
							$("#Expense_"+i).append($("<option selected='selected'>").text(parametereList[k].parameterName).attr("value", parametereList[k].inspectionParameterId));
						} else {
							$("#Expense_"+i).append($("<option>").text(parametereList[k].parameterName).attr("value", parametereList[k].inspectionParameterId));
						}
						
					}
					
					$("#frequency_"+i).val(sheetToParameterDtoList[i].frequency)
					
					$("#requiredType_"+i+ " option[value="+sheetToParameterDtoList[i].mandatory+"]").attr('selected', true);
					
					$("#inspectionSheetToParameterId_"+i).val(sheetToParameterDtoList[i].inspectionSheetToParameterId)
					
					if(sheetToParameterDtoList[i].photoRequired == true || sheetToParameterDtoList[i].photoRequired == 'true'){
						$("#inputPhotoGroupYes_"+i).prop("checked", true);
						$("#inputPhotoGroupNo_"+i).prop("checked", false);
					}else {
						$("#inputPhotoGroupYes_"+i).prop("checked", false);
						$("#inputPhotoGroupNo_"+i).prop("checked", true);
					}
					
					if(sheetToParameterDtoList[i].textRequired == true || sheetToParameterDtoList[i].textRequired == 'true'){
						$("#textGroupYes_"+i).prop("checked", true);
						$("#textGroupNo_"+i).prop("checked", false);
					}else {
						$("#textGroupYes_"+i).prop("checked", false);
						$("#textGroupNo_"+i).prop("checked", true);
					}
					
				}
			}
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});
	hideLayer();
});

function updateInspectionSheet(){
	var 	jsonArr							= new Array();
	var 	array							= new Array();
	var 	newArray						= new Array();
	var		jsonObject						= new Object();
	var		jsonObjectNew					= new Object();
	var 	inspectionParameter				= null;
	var 	IPD								= null;
	var 	inspectionSheetParameterId		= null;
	var 	frequency						= null; 	
	var 	requiredType					= null; 	
	var 	requiredTypeId					= null;
	var 	inputPhotoYes					= null; 	
	var 	inputPhotoNo					= null; 	
	var 	inputPhotoTypeId				= null;
	var 	textGroupYes					= null; 	
	var 	textGroupNo						= null; 	
	var 	textGroupTypeId					= null; 
	
	$('select[name*=inspectionParameter]').each(function(obj){
		IPD = this.id.split("_")[1];
		jsonArr.push(this.id.split("_")[1]);
	});
	
	var ISID 			= Number($("#ISID_id").val());
	for(var i =0 ; i <= IPD ; i++){// For Updation 
		jsonObject					= new Object();
		inspectionParameter 		= $("#Expense_"+i).val();
		inspectionSheetParameterId 	= $("#inspectionSheetToParameterId_"+i).val();
		frequency 					= $("#frequency_"+i).val();
		requiredType 				= $("#requiredType_"+i).val();
		inputPhotoYes				= $("#inputPhotoGroupYes_"+i).is(':checked');
		inputPhotoNo				= $("#inputPhotoGroupNo_"+i).is(':checked');
		textGroupYes				= $("#textGroupYes_"+i).is(':checked');
		textGroupNo					= $("#textGroupNo_"+i).is(':checked');
	
	
		if(inspectionParameter == 0  ){
			showMessage('errors', "Please Select Inspection Parameter Name");	
			return false;
		}
		if(frequency == 0  || frequency == ""){
			showMessage('errors', "Please Enter Frequency");	
			return false;
		}
		
		
		if(requiredType != undefined){
			if(requiredType == true ||requiredType == 'true' ){
				requiredTypeId = 1;
			}else {              
				requiredTypeId = 0;
			}
		}
		
		if((inputPhotoYes == true || inputPhotoYes =='true') && (inputPhotoNo == false || inputPhotoNo =='false') ){
			inputPhotoTypeId =1	;
		}else{
			inputPhotoTypeId =0	;
		}
		if((textGroupYes == true || textGroupYes =='true') && (textGroupNo == false || textGroupNo =='false') ){
			textGroupTypeId =1;
		}else{
			textGroupTypeId =0;
		}
		
			jsonObject.parameterSheetId 				= ISID;
			jsonObject.inspectionParameter 				= inspectionParameter;
			jsonObject.frequency 						= frequency;
			jsonObject.requiredTypeId  					= requiredTypeId;
			jsonObject.inputPhotoTypeId					= inputPhotoTypeId;
			jsonObject.textGroupTypeId					= textGroupTypeId;
			jsonObject.inspectionSheetParameterId 		= inspectionSheetParameterId;
			
			array.push(jsonObject);
	}
	
	
	var parameterId = parseInt(document.getElementById('btn').value, 10);//get the value of new field while clicking on Add button
	for(var j = 1 ; j <= parameterId ; j++){//for Adding New Fields in Json Object
		jsonObjectNew					= new Object();
	var newRequiredTypeId			= null;
	var	newInputPhotoGroupID		= null;
	var newTextGroupID				= null;	
	var ISID 						= Number($("#ISID_id").val());
	var	inspectionParameterID 		= $("#inspectionParameterID_"+j).val();
	var	frequencyID 				= $("#frequencyID_"+j).val();
	var	requiredTypeID 				= $("#requiredTypeID_"+j).val();
	var	inputPhotoGroupYesID 		= $("#inputPhotoGroupYesID_"+j).is(':checked');
	var	inputPhotoGroupNoID			= $("#inputPhotoGroupNoID_"+j).is(':checked');
	var	textGroupYesID				= $("#textGroupYesID_"+j).is(':checked');
	var	textGroupNoID 				= $("#textGroupNoID_"+j).is(':checked');
	
	
	if(inspectionParameterID == 0 ){
			showMessage('errors', "Please Select Inspection Parameter Name");	
			return false;
		}
		if(frequencyID <= 0 || frequencyID == ""){
			showMessage('errors', "Please Enter Frequency ");	
			return false;
		}
		

		if(requiredTypeID != undefined){
			if(requiredTypeID == true || requiredTypeID == 'true' ){
				newRequiredTypeId = true;
			}else {              
				newRequiredTypeId = false;
			}
		}
		

		if((inputPhotoGroupYesID == true || inputPhotoGroupYesID =='true') && (inputPhotoGroupNoID == false || inputPhotoGroupNoID =='false') ){
			newInputPhotoGroupID = true	;
		}else{
			newInputPhotoGroupID = false;
		}
		if((textGroupYesID == true || textGroupYesID =='true') && (textGroupNoID == false || textGroupNoID =='false') ){
			newTextGroupID = true;
		}else{
			newTextGroupID = false;
		}

		jsonObjectNew.inspectionParameterID 			= inspectionParameterID;
		jsonObjectNew.parameterSheetId 					= ISID;
		jsonObjectNew.newFrequency 						= frequencyID;
		jsonObjectNew.newRequiredTypeId  				= newRequiredTypeId;
		jsonObjectNew.newInputPhotoGroupID				= newInputPhotoGroupID;
		jsonObjectNew.newTextGroupID					= newTextGroupID;
		
		newArray.push(jsonObjectNew);
	}

	var finalJson			= new Object();//final Json Object Carried Both Updated And Added Values
	finalJson.Parameter 	= JSON.stringify(array);
	finalJson.newParameter 	= JSON.stringify(newArray);
	
	
	$.ajax({
		url: "updateInspectionSheetDetailsWS.do",//For Updating And Adding New fields
		type: "POST",
		dataType: 'json',
		data: finalJson,
		success: function (data) {
			showMessage('info','Successfully Updated..');
			window.location.replace("ViewInspectionSheet.in&page=1#!");
			hideLayer();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
}

function addNewField(){//Adding new fields 
	var parametereList	= null;
	var parameterId = parseInt(document.getElementById('btn').value, 10);
	parameterId = isNaN(parameterId) ? 0 : parameterId;
	parameterId++;
	document.getElementById('btn').value = parameterId;// length Of New Fields

	var tr = $("<tr id = 'tr1_"+parameterId+"'>")

	var td1	= $("<td width='20%'>");
	var td2	= $("<td width='20%'>");
	var td3	= $("<td width='20%'>");
	var td4	= $("<td width='20%'>");
	var td5	= $("<td width='20%'>");
	var td6	= $("<td width='20%'>");

	td1.append('<select class="form-text select2" name ="inspectionParameter" id="inspectionParameterID_'+parameterId+'" data-attr="addedInspectionParameter" onchange="validateInspectionParameter(this)"></select>')
	td2.append('<label>Frequency </label><input type="number" class="form-text" name="frequency" id="frequencyID_'+parameterId+'" value ="1" placeholder="Frequency in Days" min="1" data-attr="addedFrequency"><input type="hidden" class="form-text" name="ISD" >')
	td3.append('<label>is Mandatory :</label><select class="form-text select2" name="requiredType" data-attr="addedRequiredType" id="requiredTypeID_'+parameterId+'"  ><option value="false">NO</option><option value="true">YES</option> </select>')
	td4.append('<label class="control-label">Photo Needed : </label> <label class="radio-inline"> <input name="photoGroupID_'+parameterId+' " id="inputPhotoGroupYesID_'+parameterId+'" data-attr="addedPhotoTypeYes" value="true" type="radio" checked  />Yes </label> <label class="radio-inline"><input name="photoGroupID_'+parameterId+' " id="inputPhotoGroupNoID_'+parameterId+'" data-attr="addedPhotoTypeNo" value="false" type="radio" checked  />No</label>')
	td5.append('<label class="control-label">Text Needed : </label> <label class="radio-inline"> <input name="textGroup_'+parameterId+'" id="textGroupYesID_'+parameterId+'" data-attr="addedTextTypeYes" value="true" type="radio" checked  />Yes </label> <label class="radio-inline"><input name="textGroup_'+parameterId+'" id="textGroupNoID_'+parameterId+'" data-attr="addedTextTypeNo" value="false" type="radio" checked  />No</label>')
	td6.append('<a onclick = "return removefield('+parameterId+');" class="remove_field" data-attr="removeNew" id ="removeId_'+parameterId+'" ><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>')


	tr.append(td1);
	tr.append(td2);
	tr.append(td3);
	tr.append(td4);
	tr.append(td5);
	tr.append(td6);
	
	if(document.getElementById("inspectionParameterTable")){
		$('#inspectionParameterTable').append(tr);
	} else {//After Removing all Fields, new field  should append to the following table
		var table = $("<table width='100%' id='inspectionParameterTable'>");
		
		table.append(tr);
		$('#addParameter').append(table);
	}

	parametereList	= new Array();
	$.getJSON("getInspectionParameterList.in", function(e) {
	parametereList	= e;

		$("#inspectionParameterID_"+parameterId).empty();
		$("#inspectionParameterID_"+parameterId).append($("<option>").text("Please Select ").attr("value", 0));
		for(var k = 0; k <parametereList.length; k++){//parameters
			$("#inspectionParameterID_"+parameterId).append($("<option>").text(parametereList[k].parameterName).attr("value", parametereList[k].inspectionParameterId));
		}

	});	

}

function remove( id,trId ){//removing From Database(MarkForDelete=1)
	var 	inspectionSheetParameterId 	= null;
	var 	array						= new Array();
	var		jsonObject					= new Object();
	jsonObject.inspectionSheetParameterId = id;
	alert("Do you Want To Remove This Parameter")
	$.ajax({
		url: "removeInspectionSheetDetailsWS.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info',' Successfully Removed ..');
			$('#inspectionSheet_'+trId).remove();
			//location.reload();
			hideLayer();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
}
var parameterId = 1;
function removefield(a){//removing new fields without reloading the page
	
	$("#tr1_"+a).remove();
	//a--;
	document.getElementById('btn').value = a;
	
	parameterId	 = 1;
	$('select[data-attr*=addedInspectionParameter]').each(function(obj){//after removing Any of the Field  ..
		this.id = "inspectionParameterID_"+parameterId;					//..id should be change as per the fields for validation purpose 
		$(this).parent().parent()[0].id = "tr1_"+parameterId;
		parameterId++;
		
	});
	parameterId	 = 1;
	$('input[data-attr*=addedFrequency]').each(function(obj){// same as above
		this.id = "frequencyID_"+parameterId;
		parameterId++;
	});
	parameterId	 = 1;
	$('select[data-attr*=addedRequiredType]').each(function(obj){// same as above
		this.id = "requiredTypeID_"+parameterId;
		parameterId++;
	});
	parameterId	 = 1;
	$('input[data-attr*=addedPhotoTypeYes]').each(function(obj){// same as above
		this.id = "inputPhotoGroupYesID_"+parameterId;
		this.name = "photoGroupID_"+parameterId;
		parameterId++;
	});
	parameterId	 = 1;
	$('input[data-attr*=addedPhotoTypeNo]').each(function(obj){// same as above
		this.id = "inputPhotoGroupNoID_"+parameterId;
		this.name = "photoGroupID_"+parameterId;
		parameterId++;
	});
	parameterId	 = 1;
	$('input[data-attr*=addedTextTypeYes]').each(function(obj){// same as above
		this.id 	= "textGroupYesID_"+parameterId;
		this.name 	= "textGroup_"+parameterId;
		parameterId++;
	});
	
	parameterId	 = 1;
	$('input[data-attr*=addedTextTypeNo]').each(function(obj){// same as above
		this.id = "textGroupNoID_"+parameterId;
		this.name 	= "textGroup_"+parameterId;
		parameterId++;
	});
	parameterId	 = 1;
	$('a[data-attr*=removeNew]').each(function(obj){// same as above
		this.id = "removeId_"+parameterId;
		parameterId++;
	});
	
	
}

function validateInspectionParameter(object){// Do Not allow Parameters Which are already In used
	$('select[name*=inspectionParameter]').each(function(obj){
		if(this.value == object.value && object.id != this.id){
			showMessage('info','Already Selected !');
			object.value = 0;
			return false;
		}
		
	});
}
