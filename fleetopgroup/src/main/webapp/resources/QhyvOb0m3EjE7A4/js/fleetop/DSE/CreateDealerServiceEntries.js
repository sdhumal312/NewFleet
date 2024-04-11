var partType 			= 1;
var labourType 			= 2;
$(document).ready(function() {
	$(".collapse:not(#collapse2)").collapse("show");
	var a = 500,
	b = $(".addMoreLabourDiv"),
	c = $(".addMoreLabourButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row"><div class="col col-sm-1 col-md-3">'
				+'<label class="has-float-label">'
				+'<input type="text" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;" name="labourName"  id="labourName'+d+'"  >'
				+'<span style="color: #2e74e6;font-size: 18px;">Labour Type</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="labourWorkingHours"  id="labourWorkingHours'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Hours</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="labourPerHourCost"  id="labourPerHourCost'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Rate/Hour</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allLabourDiscount" id="labourDiscount'+d+'" name="labourDiscount"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' ); ">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Dis <i class="fa fa-percent labourPercent"></i></span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allLabourTax " id="labourTax'+d+'" name="labourTax"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' ); ">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Tax <i class="fa fa-percent labourPercent"></i></span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="totalLabourCost"  id="totalLabourCost'+d+'"  readonly="readonly" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
				+'</label>'
				+'</div>'
				+'<a href="#" class="removeLabour col col-sm-1 col-md-1"><font color="FF00000"><i class="fa fa-trash"></i> Remove </a></font>'
				+'</div>'),
				$("#labourName"+d).select2({
					minimumInputLength: 3,
					minimumResultsForSearch: 10,
					ajax: {
						url: "labourAutoComplete.in?Action=FuncionarioSelect2",
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
										text: e.labourName,
										slug: e.slug,
										id: e.labourId
									}
								})
							}
						}
					},createSearchChoice:function(term, results) {
					if ($(results).filter( function() {
						return term.localeCompare(this.text)===0; 
					}).length===0) {
						return {id:term, text:term + ' [New]'};
					}
				}
				
				})
		)
		if($('#finalLabourDiscountTaxTypId').val() == 2){
			$('.labourPercent').removeClass('fa-percent');
			$('.labourPercent').addClass('fa-inr');
		}else{
			$('.labourPercent').addClass('fa-percent');
			$('.labourPercent').removeClass('fa-inr');
		}
	}), $(b).on("click", ".removeLabour", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});

$(document).ready(function() {
	var a = 500,
	b = $(".addMorePartDiv"),
	c = $(".addMorePartButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row"><div class="col col-sm-1 col-md-3">'
				+'<label class="has-float-label">'
				+' <input type="hidden" name="partId" id="partId'+d+'" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred'+d+',lastPartCost'+d+',lastPartDis'+d+',lastPartTax'+d+',true,partEachCost'+d+',partDiscount'+d+',partTax'+d+');">'
				+'<span style="color: #2e74e6;font-size: 18px;">Part Name</span>'
				+'<samp id="lastPartOccurred'+d+'" > </samp>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="partQty'+d+'"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d +"','"+partType+ '\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Qty</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="partEachCost" id="partEachCost'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Cost</span>'
				+'</label>'
				+'<samp id="lastPartCost'+d+'"> </samp>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allPartDiscount" id="partDiscount'+d+'" name="partDiscount"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d +"','"+partType+ '\' ); ">'
				+'<span style="color: #2e74e6;font-size: 18px;">Dis <i class="fa fa-percent partPercent"></i></span>'
				+'</label>'
				+'<samp id="lastPartDis'+d+'"> </samp>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allPartTax" id="partTax'+d+'"  name="partTax" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d +"','"+partType+ '\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Tax <i class="fa fa-percent partPercent"></i></span>'
				+'</label>'
				+'<samp id="lastPartTax'+d+'"> </samp>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="partTotalCost'+d+'"  readonly="readonly"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
				+'</label>'
				+'</div>'
				+'<a href="#" class="removePart col col-sm-1 col-md-1"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), 
				$("#partId"+d).select2({
					minimumInputLength: 3,
					minimumResultsForSearch: 10,
					ajax: {
						url: "searchAllMasterParts.in?Action=FuncionarioSelect2",
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
										text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
										slug: e.slug,
										id: e.partid,
										partNumber: e.partnumber,
										isWarrantyApplicable: e.isWarrantyApplicable,
										warrantyInMonths: e.warrantyInMonths
									}
								})
							}
						}
					},
					//Allow manually entered text in drop down.
					createSearchChoice:function(term, results) {
						if ($(results).filter( function() {
							return term.localeCompare(this.text)===0; 
						}).length===0) {
							return {id:term, text:term + ' [New]'};
						}
					},
				})
		)
		if($('#finalPartDiscountTaxTypId').val() == 2){
			$('.partPercent').removeClass('fa-percent');
			$('.partPercent').addClass('fa-inr');
		}else{
			$('.partPercent').addClass('fa-percent');
			$('.partPercent').removeClass('fa-inr');
		}
	}), $(b).on("click", ".removePart", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});

$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
			
				if(($('#issueId').val() == undefined ||  $('#issueId').val() == "") && ($('#vehicleId').val() == "" || $('#vehicleId').val() == undefined)){
					showMessage('info','Please Select Vehicle');
					hideLayer();
					return false;
				}
				if($('#vendorId').val() == "" || $('#vendorId').val() == undefined){
					showMessage('info','Please Select Vendor');
					hideLayer();
					return false;
				}
				
				if(($('#invoicestartDate').val() == undefined ||  $('#invoicestartDate').val() == "") ){
					showMessage('info','Please Select Invoice Date');
					hideLayer();
					return false;
				}
				
				if($('#validateOdometer').val() == true || $('#validateOdometer').val() === 'true' ){
					if(!validateOdometer()){
						hideLayer();
						return false;
					}
				}
				
				var isVendorIdNan			= isNaN(Number($('#vendorId').val()))
				
				if(isVendorIdNan == true){
					$("#modalVendorName").val($('#vendorId').val());
					hideLayer();
					$("#vendorModal").modal('show');
					return false;
				}
				if (!validateBankPayment($('#bankPaymentTypeId').val())) {
					hideLayer();
					return false;
				}
				
				var jsonObject			= new Object();
				var labourArray	 		= new Array();
				var partArray	 		= new Array();
				var totalPartCost		= 0;
				var totalLabourCost		= 0;

				var labourNameArr 			= new Array();
				var labourHourArr 			= new Array();
				var labourRateArr 			= new Array();
				var labourDisArr 			= new Array();
				var labourTaxArr 			= new Array();
				var labourTotalCostArr 		= new Array();

				var selectedPartIdArr 		= new Array();
				var partNameArr 			= new Array();
				var partNumberArr 			= new Array();
				var partWarrantyArr 		= new Array();
				var partWarrantyMonthArr 	= new Array();
				var partQtyArr 				= new Array();
				var partRateArr 			= new Array();
				var partDisArr 				= new Array();
				var partTaxArr 				= new Array();
				var partTotalCostArr 		= new Array();
				var DSE_OPEN_STATUS_ID		= 1;

				$("input[name=labourName]").each(function(){
					labourNameArr.push($(this).val().replace(/"/g, ""));
				});
				$("input[name=labourWorkingHours]").each(function(){
					labourHourArr.push($(this).val());
				});
				$("input[name=labourPerHourCost]").each(function(){
					labourRateArr.push($(this).val());
				});
				$("input[name=labourDiscount]").each(function(){
					labourDisArr.push($(this).val());
				});
				$("input[name=labourTax]").each(function(){
					labourTaxArr.push($(this).val());
				});
				$("input[name=totalLabourCost]").each(function(){
					labourTotalCostArr.push($(this).val());
					totalLabourCost += Number($(this).val());
				});

				$("input[name=partId]").each(function(){
					selectedPartIdArr.push(this.id)
					partNameArr.push($(this).val());
					if($(this).val() != "" && $(this).val() != null && $(this).val() != undefined){
						partNumberArr.push($('#'+this.id).select2('data').partNumber);
						partWarrantyArr.push($('#'+this.id).select2('data').isWarrantyApplicable);
						partWarrantyMonthArr.push($('#'+this.id).select2('data').warrantyInMonths);
					}
				});
				$("input[name=partQty]").each(function(){
					partQtyArr.push($(this).val().replace(/"/g, ""));
				});
				$("input[name=partEachCost]").each(function(){
					partRateArr.push($(this).val());
				});
				$("input[name=partDiscount]").each(function(){
					partDisArr.push($(this).val());
				});
				$("input[name=partTax]").each(function(){
					partTaxArr.push($(this).val());
				});
				$("input[name=partTotalCost]").each(function(){
					partTotalCostArr.push($(this).val());
					totalPartCost += Number($(this).val());
				});

				for(var i =0 ; i< labourNameArr.length; i++){
					var labourDetails					= new Object();
					if(labourNameArr[i] != ""){
						if(labourNameArr[i] > 0 && (labourTotalCostArr[i] == '' || labourTotalCostArr[i] == 0)){
							showMessage('info','Please Enter Labour Details');
							hideLayer();
							return false;
						}
						
						var isLabourNan			= isNaN(labourNameArr[i])
						
						
						if(isLabourNan){
							labourDetails.labourName				= labourNameArr[i];
						}else{
							labourDetails.labourId					= labourNameArr[i];
						}
						labourDetails.labourWorkingHours		= labourHourArr[i];
						labourDetails.labourPerHourCost			= labourRateArr[i];
						if($('#finalLabourDiscountTaxTypId').val() == 1){
							if(labourTaxArr[i] > 100 || labourDisArr[i] > 100){
								showMessage('info','Please Enter Valid Discount/Tax');
								hideLayer();
								return false;
							}
						}
						labourDetails.labourTax					= labourTaxArr[i];
						labourDetails.labourDiscount			= labourDisArr[i];
						labourDetails.totalLabourCost			= labourTotalCostArr[i];
						
						labourArray.push(labourDetails);
					}
				}

				for(var i =0 ; i< partNameArr.length; i++){
					var partDetails					= new Object();
					var partId 						= 0;
					var partName 					= "";
					if(partNameArr[i] != ""){	
						//If part is new then have to add part number (logic in else statement)
						if(Number(partNameArr[i]) > 0){
							partDetails.partId					= partNameArr[i];
						}else{
							showMessage('info','You have Not Entred Part Number For '+partNameArr[i]+'');
							hideLayer();
							$("#selectedPartId").val(selectedPartIdArr[i]); // this is the dynamic id of part autocomplete
							$("#modalPartName").val(partNameArr[i]); // value display on modal
							$("#partNumberModal").modal('show');
							return false;
							
						}
						if(partNameArr[i] > 0 && (partTotalCostArr[i] == '' || partTotalCostArr[i] == 0)){
							showMessage('info','Please Enter Part Details');
							hideLayer();
							return false;
						}
						partDetails.partNumber				= partNumberArr[i];
						partDetails.isWarrantyApplicable	= partWarrantyArr[i];
						
						partDetails.warrantyInMonths		= partWarrantyMonthArr[i];
						partDetails.partQty					= partQtyArr[i];
						partDetails.partCost				= partRateArr[i];
						
						if($('#finalPartDiscountTaxTypId').val() == 1){
							if(partTaxArr[i] > 100 || partDisArr[i] > 100){
								showMessage('info','Please Enter Valid Discount/Tax');
								hideLayer();
								return false;
							}
						}
						partDetails.partTax					= partTaxArr[i];
						partDetails.partDiscount			= partDisArr[i];
						partDetails.totalPartCost			= partTotalCostArr[i];
	
						partArray.push(partDetails);
					}
				}

				jsonObject.labourDetails 				= JSON.stringify(labourArray);
				jsonObject.partDetails 					= JSON.stringify(partArray);
				
				if( $('#issueId').val() != "" && $('#issueId').val() != undefined &&  $('#issueId').val() > 0){
					jsonObject["vid"]						= $('#issueVid').val();
				}else{
					jsonObject["vid"]						= $('#vehicleId').val();
					
				}
				jsonObject["vehicleOdometer"]			= $('#vehicleOdometer').val();
				jsonObject["invoiceNumber"]				= $('#invoiceNumber').val();
				jsonObject["jobNumber"]					= $('#jobNumber').val();
				jsonObject["vendorId"]					= $('#vendorId').val();
				jsonObject["paymentTypeId"]				= $('#paymentTypeId').val();
				jsonObject["invoiceDate"]				= $('#invoicestartDate').val();
				jsonObject["transactionNumber"]			= $('#transactionNumber').val();
				jsonObject["totalDSE_Cost"]				= Number(totalPartCost+totalLabourCost);
				jsonObject["statusId"]					= DSE_OPEN_STATUS_ID;
				jsonObject["companyId"]					= $('#companyId').val();
				jsonObject["userId"]					= $('#userId').val();
		
				if($('#issueId').val() != "" && $('#issueId').val() != undefined && $('#issueId').val() > 0){
					jsonObject["issueId"]					= $('#issueId').val();// DSE from issue
				}else{
					if($('#dealerIssueId').val() != null && $('#dealerIssueId').val() != ""){
						jsonObject["issueId"]					= $('#dealerIssueId').val().toString();// DSE Include issue
					}
				}
				jsonObject["isPartApplicable"]				= false; // used while complete DSE
				jsonObject["driverId"]						= $('#driverId').val();
				jsonObject["partDiscountTaxTypeId"]			= $('#finalPartDiscountTaxTypId').val();
				jsonObject["labourDiscountTaxTypeId"]		= $('#finalLabourDiscountTaxTypId').val();
				jsonObject["assignToId"]					= $('#assignToId').val();
				jsonObject["accidentId"]					= $('#accidentId').val();
				jsonObject["meterNotWorkingId"]				= $('#meterNotWorkingId').prop('checked');
				
				var serviceIds = '';
				if($('#showServiceProgram').val() == 'true'){
				$("input[name=selectedSchedule]").each(function(){
					if($('#'+this.id+'').prop('checked')){
						serviceIds += this.id+',';
						console.log("serviceIds"+serviceIds)
					}
				});
				} 
				if($('#showServRemindWhileCreating').val() == 'true' && $('#serviceReminderId').val() != ''){
						serviceIds=$('#serviceReminderId').val().toString();
				}
				jsonObject["serviceReminderId"]	=  serviceIds;	
				
				var form = $('#fileUploadForm')[0];
				var data = new FormData(form);
				
				if (allowBankPaymentDetails) {
					prepareObject(jsonObject)
				}

				data.append("dealerServiceEntryData", JSON.stringify(jsonObject)); 
				
				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "saveDealerServiceEntries",
					data: data,
					processData: false, //prevent jQuery from automatically transforming the data into a query string
			        contentType: false,
			        cache: false,
					success: function (data) {
						if(data.sequenceCounterNotFound != undefined && (data.sequenceCounterNotFound == true || data.sequenceCounterNotFound == 'true' )){
							showMessage('info','Sequence Not Found Please Contact To System Administrator');
							hideLayer
						}else if(data.alreadyExist != undefined && (data.alreadyExist == true || data.alreadyExist == 'true' )){
							showMessage('info','Already Exist');
							hideLayer
						}else if(data.inSold != undefined && (data.inSold == true || data.inSold == 'true' )){
							showMessage('info','Vehicle Is In Sold Status');
							hideLayer
						} else if(data.inActive != undefined && (data.inActive == true || data.inActive == 'true' )){
							showMessage('info','Vehicle Is In In-Active Status');
							hideLayer
						}else if(data.inSurrender != undefined && (data.inSurrender == true || data.inSurrender == 'true' )){
							showMessage('info','Vehicle Is In Surrneder Status');
							hideLayer
						}else{
							showMessage('success','Save Successfully');
							if(data.dealerServiceEntriesId != undefined){
								window.location.replace("showDealerServiceEntries?dealerServiceEntriesId="+data.dealerServiceEntriesId+"");
							}else{
								showMessage('info', 'Please Contact To Administrtor!');
								hideLayer();
							}
						}
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			});
		});
$(document).ready(function(){
if($('#srNumber').val()>0){
	setTimeout(function(){
		$('#vehicleId').select2('data',{
			id : $('#srVid').val(),
			text : $('#srVehicleNumber').val()
		});
		$('#vehicleId').trigger('change');
		
		$('#vehicleId').select2("readonly", true);
		$('#serviceReminderId').select2('data',{
			id : $('#programId').val(),
			text : $('#programName').val()
		});
		$('#serviceReminderId').trigger('change');
	},100)
	setTimeout(function(){
		var srNumber = document.getElementById($("#srNumber").val());
		console.log("srNumber ",srNumber);
		$('#'+$("#srNumber").val()).prop('checked',true);
	},2000)
}
});
function setAccidentDetails(detailsDto){
	if(!jQuery.isEmptyObject(detailsDto)){
		var accidentDetailsDto = JSON.parse(detailsDto);
		if(accidentDetailsDto != undefined){
			$('#accidentId').val(accidentDetailsDto.accidentDetailsId);
			$('#vehicleId').select2('data', {
	    		id : accidentDetailsDto.vid,
	    		text : accidentDetailsDto.vehicleNumber
	    	});
			$('#vehicleId').select2('readonly', true);
			
			$('#driverId').select2('data', {
	    		id : accidentDetailsDto.driverId,
	    		text : accidentDetailsDto.driverName
	    	});
			$('#driverId').select2('readonly', true);
			$('#vehicleOdometer').val(accidentDetailsDto.vehicleOdometer);
			$('#vehicle_Odometer_Old').val(accidentDetailsDto.vehicleOdometer);
			
			vehicleOnChange($("#vehicleId").val());
			vehicleOnChange3($("#vehicleId").val());
			backdateOdometerValidation();
		}
	}
}
