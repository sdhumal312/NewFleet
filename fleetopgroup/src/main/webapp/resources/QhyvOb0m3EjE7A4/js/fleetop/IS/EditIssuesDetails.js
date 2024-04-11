$(document).ready(
		function($) {
			$("#editIssue").val(true);
			$("#fromEdit").val(true);
			 changeIssuesType();
			
			 if(Number($('#editIssueType').val()) == 6){
				 getIssueBreakDownDetails();
			 	 showHideBreakDownFeilds();
			 	 showHideVehicleReplaceFeilds();
			 	 showHideTripCancelFeilds();
				 
			 }
			 setTimeout(function(){ showOdometerRange(); // when issue type is vehicle
			 }, 300);
			if($('#subscribe').val()!=undefined && $('#subscribe').val().length > 0){
				var subscribeStringArr =  $('#subscribe').val().split(",");
				var array	 = new Array();
				for(var i = 0; i< subscribeStringArr.length ; i++){
					var locationDetails	= new Object();
				   	locationDetails.id = subscribeStringArr[i];
				   	locationDetails.text = subscribeStringArr[i];
				   	array.push(locationDetails);
				}
			$('#subscribe').data().select2.updateSelection(array);
			}
			 vehicleChange();
		});

function getIssueBreakDownDetails(){
		var jsonObject					= new Object();
		jsonObject["issuesId"]			= $('#issuesId').val();
		jsonObject["issueType"]			= $('#editIssueType').val();
	
		$.ajax({
					url: "getIssueBreakDownDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						console.log('data', data);
						setIssueBreakDownDetails(data);
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
			});
}

function setIssueBreakDownDetails(data){

	if(data.breakDownDetails != undefined){
	
		$('#breakDownDetailsId').val(data.breakDownDetails.issueBreakDownDetailsId);
		//$('#breakDownLocation').val(data.breakDownDetails.breakDownLocation);
		$('#tripSheetNumber').val(data.breakDownDetails.tripNumber);
		$('#replaceLocation').val(data.breakDownDetails.vehicleReplacedLocation);
		$('#cancelledKM').val(data.breakDownDetails.cancelledKM);
		
		if(data.breakDownDetails.replacedWithVid != null && data.breakDownDetails.replacedWithVid > 0){
			$('#replacedVehicle').select2('data', {
    		id : data.breakDownDetails.replacedWithVid,
    		text : data.breakDownDetails.vehicleNumber
    	});
		}
		if(data.breakDownDetails.isVehicleReplaced){
			$('#vehicleReplaced').prop('checked', true);
			showHideVehicleReplaceFeilds();
		}
		if(data.breakDownDetails.isTripCancelled){
			$('#tripCancelled').prop('checked', true);
			showHideTripCancelFeilds();
		}
		//$('#breakDownLocation').val(data.breakDownDetails.);
		//$('#breakDownLocation').val(data.breakDownDetails.);
	}
	
}

$(document).ready(
		function($) {
			$('button[id=updateIssue]').click(function(e) {
				showLayer();
				if(validateIssue() == true){
					
				var jsonObject								= new Object();
				
				jsonObject["issuesId"]						= $('#issuesId').val();
				jsonObject["issuesNumber"]					= $('#issuesNumber').val();
				jsonObject["issueType"]						= $('#issueType').val();
				jsonObject["vid"]							= $('#IssuesSelectVehicle').val();
				jsonObject["vGroup"]						= $('#vGroup').val();
				jsonObject["driverId"]						= $('#driverId').val();
				jsonObject["customerName"]					= $('#customerName').val();
				jsonObject["issuesBranch"]					= $('#issuesBranch').val();
				jsonObject["issuesDepartnment"]				= $('#issuesDepartnment').val();
				jsonObject["odometer"]						= $('#Issues_Odometer').val();
				jsonObject["gpsOdometer"]					= $('#GPS_ODOMETER').val();
				jsonObject["reportdDate"]					= $('#reportdDate').val();
				jsonObject["issueStartTime"]				= $('#issueStartTime').val();
				jsonObject["issuesSummary"]					= $('#issuesSummary').val();
				jsonObject["issueLabel"]					= $('#issueLabel').val();
				jsonObject["reportedById"]					= $('#reportedById').val();
				jsonObject["subscribe"]						= $('#subscribe').val();
				jsonObject["description"]					= $('#description').val().trim();
				jsonObject["issueStatusID"]					= $('#issueStatusID').val();
				jsonObject["vehicleCurrentOdometer"]		= $('#odometerWhileCreatingIssue').val(); // wouldn't change, its a current vehicle Odometer while creating issue. 
				jsonObject["partCategory"]					= $('#partCategory').val(); // wouldn't change, its a current vehicle Odometer while creating issue. 
				jsonObject["FuelRouteList"]					= $('#FuelRouteList').val(); // wouldn't change, its a current vehicle Odometer while creating issue. 
				jsonObject["breakDownLocation"]				= $('#breakDownLocation').val();
				
				if(Number($('#issueType').val()) == 6){
					jsonObject["tripSheetNumber"]				= $('#tripSheetNumber').val();
					jsonObject["breakDownDetailsId"]			= $('#breakDownDetailsId').val();
				
					if($('#vehicleReplaced').prop('checked')){
						jsonObject["replacedVehicle"]				= $('#replacedVehicle').val();
						jsonObject["replaceLocation"]				= $('#replaceLocation').val();
						jsonObject["vehicleReplaced"]				= true;
					}
					if($('#tripCancelled').prop('checked')){
						jsonObject["tripCancelled"]					= true;
						jsonObject["cancelledKM"]					= $('#cancelledKM').val();
					}
				}
				
				var subScriberNames                         = '';
				var subScriberIds                           = '';
				if($('#subscribe').val()!=undefined && $('#subscribe').val().length > 0){
					var subscribeString    = $('#subscribe').val();
					var subscribeStringArr =  subscribeString.split(",");
					if(subscribeStringArr!=undefined && subscribeStringArr.length > 0){
						for(var i = 0;i<subscribeStringArr.length;i++){
							var string = subscribeStringArr[i].split("_")[0];
							var ids    = subscribeStringArr[i].split("_")[1];
							subScriberNames = subScriberNames +','+ string;
							if(ids!=undefined){
								subScriberIds   = subScriberIds +','+ ids;
							}
						}
						subScriberNames = subScriberNames.substring(1,subScriberNames.length);
						subScriberIds   = subScriberIds.substring(1,subScriberIds.length);
					}
				}
				jsonObject["subscribe"]				            = subScriberNames;
				jsonObject["subscriberIdsForMob"]				= subScriberIds;
				
//				var categoryArray = new Array();
//				var labelArray = new Array();
//				var summaryArray = new Array();
//				
//				
//				$('input[name=categoryId]').each(function(){
//					categoryArray.push($(this).val());
//				})
//				$('select[name=issueLabel_ID]').each(function(){
//					labelArray.push($(this).val());
//				})
//				$('input[name=issuesSummary]').each(function(){
//					summaryArray.push($(this).val())
//				})
//				var finalArray = new Array();
//				for(var i = 0 ;i<categoryArray.length;i++){
//					var object = new Object();
//					object.categoryId = categoryArray[i];
//					object.issueLabel_ID = labelArray[i];
//					object.issuesSummary = summaryArray[i];
//					finalArray.push(object);
//				}
//				
//				jsonObject.multiIssueDetails =JSON.stringify(finalArray);
				
				
				$.ajax({
					url: "updateIssuesDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.replaceVehicleNotActive != undefined && data.replaceVehicleNotActive == true) {
							showMessage('info', 'Replace Vehicle In '+data.replaceVehicleStatus+' Status Hence You Can Not Create Issue');
							hideLayer();
							
						}else if(data.replaceVehicleAndIssueVehicleSame != undefined && data.replaceVehicleAndIssueVehicleSame == true) {
							showMessage('info','Replace Vehicle Can Not Be Same As Issue Vehicle');
							hideLayer();
							
						}else{
							showMessage('success', 'Issue Saved Successfully!');
							window.location.replace("showIssues.in?Id="+data.issueId);
							hideLayer();
							
						}
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
				
			}
			})
		});	
		
function showOdometerRange(){
	if($('#IssuesSelectVehicle').val() == undefined || $('#IssuesSelectVehicle').val() == ""){
		return true;
	}
	
	var jsonObject				= new Object();
	var expectedOdometer 		= Number($('#vehicle_ExpectedOdameter').val());
	var vehicleOdometer  		= Number($('#vehicle_Odameter').val());
	
	jsonObject["issuesId"]						= $('#issuesId').val();
	jsonObject["vid"]							= $('#IssuesSelectVehicle').val();
	jsonObject["issueOdometer"]					= $('#editIssueOdometer').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	
	$.ajax({
		url: "getIssueMaxOdometer",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.issueMaxOdomter != undefined && data.issueMaxOdomter > 0 ){
				$("#issueMaxOdomter").val(data.issueMaxOdomter);
			}else{
				$("#issueMaxOdomter").val(expectedOdometer+vehicleOdometer);
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	
}
$(document).ready(function() {
    var e =10, addMorePartsAtBottom = true,
    t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    if(addMorePartsAtBottom == true || addMorePartsAtBottom == 'true'){
    	t = $("#moreParts");
    }  
   
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<br><div><div class="panel panel-success"><div class="panel-body"> <div class="form-group column"> <label class="col-md-4 control-label">Category :</label> <div class="col-md-8 inputGroupContainer" style="padding:; padding-top: 7px;"> <input type="hidden" id="partCategory'+a+'" name="categoryId" style="width: 80%;" value="0" required="required" placeholder="Please Select Category " /> </div> </div> <div id="labelDiv" class="form-group column" > <label class="col-md-4 control-label">Label</label> <div class="col-md-8 inputGroupContainer"> <div class="input-group" style="width:80%;"><span class="input-group-addon"><i class=" glyphicon glyphicon-list-alt "></i>  </span> <select class="form-text" name="issueLabel_ID" id="issueLabel'+a+'" onchange="return validateIssue(this);"> <option value="1">NORMAL</option> <option value="2">HIGH</option> <option value="3">LOW</option> <option value="4">URGENT</option> <option value="5">VERY URGENT</option><option value="6">BREAKDOWN</option> </select> </div> </div> </div>  <div id="summaryDiv" class="form-group column" >  <label class="col-md-4 control-label">Summary <abbr title="required">*</abbr> </label> <div class="col-md-8 inputGroupContainer">  <div class="input-group"><span class="input-group-addon"><i class="fa fa-book"></i>  </span> <input class="form-text" type="text" maxlength="150"  name="issuesSummary" id="issuesSummary'+a+'" style="width : 796px; ondrop="return false;">  </div> </div>  </div> </div></div> <a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#partCategory"+a).select2({
                minimumInputLength: 0,
                ajax: {
                    url: "searchPartCategory.in?Action=FuncionarioSelect2",
                    dataType: "json",
                    type: "POST",
                    contentType: "application/json",
                    quietMillis: 50,
                    data: function(e) {
                        return {
                            term: e
                        }
                    },
                    results: function(e) {
                        return {
                            results: $.map(e, function(e) {
                                return {
                                    text: e.pcName,
                                    slug: e.slug,
                                    id: e.pcid
                                }
                            })
                        }
                    }
                }
            })
        }))
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault();
        $(this).parent("div").remove(); a--;
    })
})

