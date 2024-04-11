$(document).ready(
		function($) {
			$('button[id=saveIssue]').click(function(e) {
				showLayer();
				if(validateIssue() == true){
					if(!validateMultiIssue()){
						hideLayer();
						return false;
					}
					
				
				var jsonObject								= new Object();
			
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
				jsonObject["description"]					= $('#description').val().trim();
				jsonObject["partCategory"]					= $('#partCategory').val();
				jsonObject["FuelRouteList"]					= $('#FuelRouteList').val();
				jsonObject["breakDownLocation"]				= $('#breakDownLocation').val();
				jsonObject["userId"]						= $('#userId').val();
				
				if(Number($('#issueType').val()) == 6){
					jsonObject["tripSheetNumber"]				= $('#tripSheetNumber').val();
				
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
				jsonObject["companyId"]					= $('#companyId').val();
				
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
							subScriberIds   = subScriberIds +','+ ids;
						}
						subScriberNames = subScriberNames.substring(1,subScriberNames.length);
						subScriberIds   = subScriberIds.substring(1,subScriberIds.length);
					}
				}
				jsonObject["subscribe"]				            = subScriberNames;
				jsonObject["subscriberIds"]						= subScriberIds;
				
				var categoryArray = new Array();
				var labelArray = new Array();
				var summaryArray = new Array();
				
				
				$('input[name=categoryId]').each(function(){
					categoryArray.push($(this).val());
				})
				$('select[name=issueLabel_ID]').each(function(){
					labelArray.push($(this).val());
				})
				$('input[name=issuesSummary]').each(function(){
					summaryArray.push($(this).val())
				})
				var finalArray = new Array();
				for(var i = 0 ;i<categoryArray.length;i++){
					var object = new Object();
					object.categoryId = categoryArray[i];
					object.issueLabel_ID = labelArray[i];
					object.issuesSummary = summaryArray[i];
					finalArray.push(object);
				}
				
				jsonObject.multiIssueDetails =JSON.stringify(finalArray);
				
				$.ajax({
					url: "saveIssuesDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						if(data.sequenceNotFound != undefined && data.sequenceNotFound == true){
							showMessage('info', 'Sequence Not Found. Please Contact System Admin Support');
							hideLayer();
						}else if(data.vehicleNotActive != undefined && data.vehicleNotActive == true) {
							showMessage('info', 'Vehicle In '+data.vehicleStatus+' Status Hence You Can Not Create Issue');
							hideLayer();
							
						}else if(data.replaceVehicleNotActive != undefined && data.replaceVehicleNotActive == true) {
							showMessage('info', 'Replace Vehicle In '+data.replaceVehicleStatus+' Status Hence You Can Not Create Issue');
							hideLayer();
							
						}else if(data.replaceVehicleAndIssueVehicleSame != undefined && data.replaceVehicleAndIssueVehicleSame == true) {
							showMessage('info','Replace Vehicle Can Not Be Same As Issue Vehicle');
							hideLayer();
							
						}else {
							showMessage('success', 'Issue Saved Successfully!');
							if(($("#issueAnalysisConfig").val() == true || $("#issueAnalysisConfig").val() == 'true') && Number($('#issueType').val()) == 6 ){
								window.location.replace("addIssueAnalysis.in?issueId="+data.issueId);
							}else{
								window.location.replace("showIssues.in?Id="+data.issueId);
							}
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


$(document).ready(function() {
    var e =10,
    t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    	t = $("#moreParts");
   
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<br><div><div class="panel panel-success"><div class="panel-body"> <div class="form-group column"> <label class="col-md-4 control-label">Category :</label> <div class="col-md-8 inputGroupContainer" style="padding:; padding-top: 7px;"> <input type="hidden" id="partCategory'+a+'" name="categoryId" style="width: 80%;" value="0" required="required" placeholder="Please Select Category " /> </div> </div> <div id="labelDiv" class="form-group column" > <label class="col-md-4 control-label">Label</label> <div class="col-md-8 inputGroupContainer"> <div class="input-group" style="width:80%;"><span class="input-group-addon"><i class=" glyphicon glyphicon-list-alt "></i>  </span> <select class="form-text" name="issueLabel_ID" id="issueLabel'+a+'" onchange="return validateIssue(this);"> <option value="1">NORMAL</option>  <option value="4">URGENT</option> <option value="5">VERY URGENT</option> </select> </div> </div> </div>  <div id="summaryDiv" class="form-group column" >  <label class="col-md-4 control-label">Summary <abbr title="required">*</abbr> </label> <div class="col-md-8 inputGroupContainer">  <div class="input-group"><span class="input-group-addon"><i class="fa fa-book"></i>  </span> <input class="form-text" type="text" maxlength="150"  name="issuesSummary" id="issuesSummary'+a+'" style="width : 796px; ondrop="return false;">  </div> </div>  </div> </div></div> <a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
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

$(document).ready(function(){
	if($('#createIssueFromHealth').val() == true || $('#createIssueFromHealth').val() == 'true' ){
		var vid = $('#VID').val();
		var healthStatus = $('#healthStatus').val();
		if(vid != null && vid != '' && vid != undefined){
			$('#IssuesSelectVehicle').select2('data',{
				id : vid,
				text : $('#vehicleRegistration').val()
			})
		}
		if(healthStatus != null && healthStatus != '' && healthStatus != undefined){
			if (healthStatus === 'MAJOR'){
				$('#issueLabel').val("4");
			}else if (healthStatus === 'CRITICAL'){
				$('#issueLabel').val("5");
			}
		}
	}
})	

$('#issueType').change(function(){
	if($('#issueType').val() == 1 || $('#issueType').val() == 6){
		$('#multiDiv').show();
	}else{
		$('#multiDiv').hide();
	}
})
