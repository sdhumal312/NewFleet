var percentageType 	= 1;
var amountType 		= 2;
var partType 		= 1;
var labourType 		= 2;

var selectedServiceProgram = new Map();
var programROwMap	= new Map();
var headerRowAdded	= false;
var fromWOEdit	= false;
$(document).ready(function() {
	$("#vehicleId").select2( {
		minimumInputLength:2, minimumResultsForSearch:10, ajax: {
			url:"getVehicleListDealerEntries.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
				return {
					term: a
				}
			}
		, results:function(a) {
			return {
				results:$.map(a, function(a) {
					return {
						text: a.vehicle_registration, slug: a.slug, id: a.vid, driverId: a.driverId, driverName : a.driverFirstName
					}
				})
			}
		}
	}
}),$("#serviceReminderId").select2({
	multiple: !0,
	allowClear: !0,
    data : ""
}),$("#vendorId").select2({
		minimumInputLength: 3,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getVendorSearchListPart.in?Action=FuncionarioSelect2",
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
							text: a.vendorName + " - " + a.vendorLocation,
							slug: a.slug,
							id: a.vendorId
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
	}),$("#labourName, #editLabourId").select2({
		minimumInputLength: 3,
		minimumResultsForSearch: 10,
		ajax: {
			url: "labourAutoComplete.in?Action=FuncionarioSelect2",
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
							text: a.labourName,
							slug: a.slug,
							id: a.labourId
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
		},
	}),$("#driverId,#modalDriverId").select2({
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    })
                }
            }
        }
    }), $("#partId,#editPartId").select2({
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
		},createSearchChoice:function(term, results) {
			if ($(results).filter( function() {
				return term.localeCompare(this.text)===0; 
			}).length===0) {
				return {id:term, text:term + ' [New]'};
			}
		},
	});
});

var currentDate = new Date();
$(function() {
	$("#invoiceDate").datepicker({
		defaultDate: currentDate,
		autoclose: !0,
		todayHighlight: !0,
		format: "dd-mm-yyyy",
		setDate: "0",
		endDate: currentDate
	})

})

function sumthere(a, b, c, d, e,partLabourTypeId ) {
	var dicTaxId;
	if(partLabourTypeId == partType){
		dicTaxId = $('#finalPartDiscountTaxTypId').val();
	}else if(partLabourTypeId == labourType){
		dicTaxId = $('#finalLabourDiscountTaxTypId').val();
	}
	
	if(dicTaxId == 2){
		var f = Number(document.getElementById(a).value),
		g = Number(document.getElementById(b).value),
		h = Number(document.getElementById(c).value),
		i = Number(document.getElementById(d).value),
		j = parseFloat(f) * parseFloat(g),
		k = Number(j) - Number(h),
		m = Number(k) + Number(i);
		isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
		if(m.toFixed(2) < 0){
			showMessage('info','Discount Amount Can Not Be Greater Than Total Cost')
			$('#'+c).val('');
			$('#'+d).val('');
			$('#'+e).val('');
		}else if($('#'+d).val() > j){
			showMessage('info','Tax Amount Can Not Be Greater Than Sub-Amount: '+j.toFixed(2)+'')
			$('#'+d).val('');
			$('#'+e).val('');
		}

	} else {
		var f = Number(document.getElementById(a).value),
		g = Number(document.getElementById(b).value),
		h = Number(document.getElementById(c).value),
		i = document.getElementById(d).value,
		j = parseFloat(f) * parseFloat(g),
		k = j * h / 100,
		l = j - k,
		a = l * i / 100,
		m = l + a;
		isNaN(m) || (document.getElementById(e).value = m.toFixed(2))

	}

}

$(document).ready(function() {
	$("#lastPartOccurred").hide();
	if($('#issueId').val() != undefined && $('#issueId').val() != ""){
		vehicleOnChange($("#issueVid").val());
		vehicleOnChange3($("#issueVid").val());
	}
});

$("#vehicleId").change(function() {
	var vData = $('#vehicleId').select2('data');
	if(vData != undefined && vData != null && vData.driverId != undefined ){
		$('#driverId').select2('data', {
				id : vData.driverId,
				text : vData.driverName
		 });
		//$("#driverId").select2("readonly", true);
	}
	vehicleOnChange($("#vehicleId").val());
	vehicleOnChange3($("#vehicleId").val());
//	checkIssueDetail($("#vehicleId").val());
	backdateOdometerValidation();
	
});

$("#invoicestartDate").change(function() {
	checkIssueDetail($("#vehicleId").val());
	backdateOdometerValidation();
});


function vehicleOnChange(vehicleId) {
	var flag = true;
	$.getJSON("getVehicleOdoMerete.in", {
		vehicleID: vehicleId,
		ajax: "true"
	}, function(a) {
		if(a.vStatusId != undefined ){
			if(a.vStatusId == 2){
				showMessage('info','Can Not Create Dealer Service Entries Vehicle Under In-Active Status')
				$("#vehicleId").select2("val","");
				flag	= false;
			}
			if(a.vStatusId == 3){
				showMessage('info','Can Not Create Dealer Service Entries Vehicle Under Surrender Status')
				$("#vehicleId").select2("val","");
				flag	= false;
			}
			if(a.vStatusId == 4){
				showMessage('info','Can Not Create Dealer Service Entries Vehicle Under Sold Status')
				$("#vehicleId").select2("val","");
				flag	= false;
			}
		}
		if(flag == true && a.vehicle_Odometer != undefined && a.vehicle_Odometer > 0){
			$('#vehicleOdometer').val(a.vehicle_Odometer);
		}
		$('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
		$('#vehicle_Odameter').val(a.vehicle_Odometer);
		if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
			$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
		}
		
	})
}

function vehicleOnChange3(vehicleId) {
    $.getJSON("getVehicleServiceReminderList.in", {
    	 vehicleID: vehicleId,
        ajax: "true"
    },  function(a) {
    	 for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].service_id + '","date":"' + a[e].servceDate + '","text":"' + a[e].service_NumberStr + '" }', c += ",") : c += '{"id":"' + a[e].service_id + '","text":"'+ a[e].service_NumberStr + '" }';
         var f = "[" + c + "]",
             g = JSON.parse(f);
         $("#serviceReminderId").select2({
        	 multiple: !0,
        	 allowClear: !0,
             data: g
         })
    })
}

function checkIssueDetail(vehicleId){
	var jsonObject				= new Object();
	jsonObject["vid"] 	 		=  vehicleId;
	jsonObject["companyId"] 	=  $('#companyId').val();
	jsonObject["invoiceDate"] 	=  $('#invoicestartDate').val().split("-") .reverse().join("-").trim();
	
	$.ajax({
		url: "getVehicleWiseIssueDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.issueDetails != undefined){
				var option  = '';
				for(var i = 0; i < data.issueDetails.length; i++){
					option	+= '<option value="'+data.issueDetails[i].issues_ID+'">I-'+data.issueDetails[i].issues_NUMBER+' - '+data.issueDetails[i].issues_SUMMARY+'</option>';
				}
				$('#dealerIssueId').html(option);
				$('#dealerIssueId').val(data.issueDetails[0].issues_ID);
				$('#dealerIssueId').select2().trigger('change');
			}else{
				$('#dealerIssueId').html('');
				$('#dealerIssueId').val('');
				$('#dealerIssueId').select2().trigger('change');
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

$(document).ready(function() {
	$("#vendorId").on("change", function() {
		var a = document.getElementById("vendorId").value;
		if (0 != a) {
			var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="6">DD</option><option value="7">CHEQUE</option><option value="10">ON ACCOUNT</option>';
			$("#paymentTypeId").html(b)
		} else {
			var b = '<option value="1">CASH</option>';
			$("#paymentTypeId").html(b)
		}
	}), 

	$("#vendorId").change(), $("#paymentTypeId").on("change", function() {
		showoption()
	}), 

	$("#paymentTypeId").change()
})

function showoption() {
	var a = $("#paymentTypeId :selected"),
	b = a.text();
	"CASH" == b ? $("#transactionId").text(b + " Receipt NO : ") : $("#transactionId").text(b + " Transaction NO : ")
}

$(document).ready(function () {
	$("#searchByfilter").on('keypress', function (e) {
		if(e.which == 13) {
			searchDSEByFilter();
		}
	});
	$("#searchByNumber").on('keypress', function (e) {
		if(e.which == 13) {
			searchDSEByNumber();
		}
	});

});

function searchDSEByNumber(){

	var jsonObject									= new Object();
	jsonObject["dealerServiceEntriesNumber"]		= $("#searchByNumber").val();
	jsonObject["companyId"]							= $("#companyId").val();

	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Service Number !');
		return false;
	}

	showLayer();
	$.ajax({
		url: "searchDealerServiceEntriesByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)

			if(data.dealerServiceEntriesId != undefined ){
				window.location.replace("showDealerServiceEntries?dealerServiceEntriesId="+data.dealerServiceEntriesId+"");
			}else{
				showMessage('info', 'Please Enter valid Service Entry Number!');
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function searchDSEByFilter(){

	var jsonObject									= new Object();
	jsonObject["filter"]							= $("#searchByfilter").val();
	jsonObject["companyId"]							= $("#companyId").val();

	if( $("#searchByfilter").val() == ""){
		$('#searchByfilter').focus();
		showMessage('errors', 'Please Enter Valid Service Number !');
		return false;
	}

	showLayer();
	$.ajax({
		url: "searchDealerServiceEntriesByFilter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$("#tabsHeading").hide();
			$("#navBarId").hide();
			$("#searchDealerServiceEntriesByDate").modal('hide');
			setDSEByFilter(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function searchDealerServiceEntriesDateWise(){

	var jsonObject								= new Object();
	if( $("#ReportDailydate").val() == ""){
		$('#ReportDailydate').focus();
		showMessage('errors', 'Please Select Date !');
		return false;
	}

	jsonObject["invoiceDate"]					= ($("#dealerServiceSearchDate").val().trim()).split("-").reverse().join("-");  
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	jsonObject["vehicleId"]						= $("#vehicleId").val();

	showLayer();
	$.ajax({
		url: "searchDealerServiceEntriesDateWise",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$("#tabsHeading").hide();
			$("#navBarId").hide();
			$("#searchDealerServiceEntriesByDate").modal('hide');
			
			setDSEByFilter(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setDSEByFilter(data){

	$("#dealerServiceEntriesTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	var dealerServiceEntriesList = data.dealerServiceEntriesList;
	var configuration			= data.configuration;

	if(dealerServiceEntriesList != undefined || dealerServiceEntriesList != null){
		for(var index = 0 ; index < dealerServiceEntriesList.length; index++){

			var columnArray = new Array();
			columnArray.push("<td class='fit'><a href='showDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesList[index].dealerServiceEntriesId+"' target='_blank'>"+dealerServiceEntriesList[index].dealerServiceEntriesNumberStr+"</a></td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].vehicleNumber  +"</td>");
			columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].vendorName  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].invoiceNumber  +"</td>");
			columnArray.push("<td class='fit ar'>"+ dealerServiceEntriesList[index].invoiceDateStr  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].jobNumber  +"</td>");
			
			if(dealerServiceEntriesList[index].totalInvoiceCost != undefined )
				columnArray.push("<td class='fit ar'>  "+ addCommas((dealerServiceEntriesList[index].totalInvoiceCost).toFixed(2))  +"</td>");
			
			if(configuration.showLastModifiedBy)
				columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].lastModifiedBy  +"</td>");
			
			if(configuration.showRemark)
           	 	columnArray.push('<td class="fit ar dseRemark"> <a href="#" data-toggle="tooltip" data-placement="top" title="'+dealerServiceEntriesList[index].remark+'">'+ dealerServiceEntriesList[index].remark +'</a></td>');

			if(configuration.showAction) {
				if(dealerServiceEntriesList[index].statusId == 1)
					columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a  href='editDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesList[index].dealerServiceEntriesId+"'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' style='color:red'  class='confirmation' onclick='deleteDealerServiceEntries("+dealerServiceEntriesList[index].dealerServiceEntriesId+");'><span class='fa fa-trash'></span> Delete</a></td>");
				else
					columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].status  +"</td>");
			}
			
			$('#dealerServiceEntriesTable').append("<tr id='penaltyID"+dealerServiceEntriesList[index].vehicleModelId+"' >" + columnArray.join(' ') + "</tr>");

		}
		
		$('.dseRemark').css({"white-space" : "nowrap", "overflow" : "hidden", "text-overflow" : "ellipsis", "max-width" : "100px"});

		columnArray = [];
	}else{
		showMessage('info','No Record Found!')
	}

}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}


function getLastOccurredDsePartDetails(e,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,createDse,partCost,partDiscount,partTax,fromEdit){
	if(e.value != "" && e.value != 0){
		var jsonObject				= new Object();
		var isPartIdNan				= isNaN(Number(e.value))
		var vid					= 0;
		if(createDse){
			if($("#vendorId").val() == ""){
				$("#"+e.id+"").select2("val",'');
				showMessage('info','Please Select Vendor')
				return true;
			}
		}
		if(isPartIdNan){
			$("#selectedPartId").val(e.id);
			$("#modalPartName").val(e.value);
			if(fromEdit){
				$('#editPartDetails').modal('hide');
				$("#partNumberModal").modal('show');
				$("#fromEdit").val(true);
			}else{
				$("#partNumberModal").modal('show');
			}
		}else{
			jsonObject["partId"]						= e.value;
			jsonObject["companyId"]						= $("#companyId").val();
			jsonObject["userId"]						= $("#userId").val();
			
			if( $('#issueId').val() != "" && $('#issueId').val() != undefined &&  $('#issueId').val() > 0){
				vid						= $('#issueVid').val();
			}else{
				vid						= $('#vehicleId').val();
			}
			if(createDse){
				if(vid == "" || vid == undefined){
					showMessage('info','Please Select Vehicle First');
					$("#"+e.id+"").select2("val", "");
					hideLayer();
					return false;
				}
				
				jsonObject["vid"]						= vid;
			}else{
				jsonObject["vid"]						= $('#vid').val(); // when we add from DSE view page
				
			}
			showLayer();
			$.ajax({
				url: "getLastOccurredDsePartDetails",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					var lastOccuredPartDetails = data.lastOccuredPartDetails;
					if(lastOccuredPartDetails.dealerServiceEntriesId != undefined){
						$("#"+lastPartOccurred.id+"").show();
						$("#"+lastPartOccurred.id+"").html("<a style='color:red' href='showDealerServiceEntries?dealerServiceEntriesId="+lastOccuredPartDetails.dealerServiceEntriesId+"' target='_blank'> Part Last Occurred ON "+lastOccuredPartDetails.invoiceDateStr+" Odometer: "+lastOccuredPartDetails.vehicleOdometer+"  IN DSE-"+lastOccuredPartDetails.dealerServiceEntriesNumber+"</a>");
					}else{
						$("#"+lastPartOccurred.id+"").hide();
					}
					hideLayer();
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
			
			getVendorWisePreviousDsePartRate(e,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,createDse,partCost,partDiscount,partTax);
		}
	}else{
		$("#"+lastPartOccurred.id+"").hide();
	}
}

function getVendorWisePreviousDsePartRate(part,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,createDse,partCost,partDiscount,partTax){
	var jsonObject								= new Object();
	var vendorId								= 0;
	if(createDse){
		vendorId 						= Number($("#vendorId").val());
	}else{
		vendorId						= Number($("#showVendorId").val());
	}
	
	jsonObject["vendorId"]						= vendorId;
	jsonObject["partId"]						= part.value;
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	
	showLayer();
	$.ajax({
		url: "getVendorWisePreviousDsePartRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.lastPartRate.partEchCost != undefined){
				
				$("#"+lastPartCost.id+"").html("Previous Part Cost "+data.lastPartRate.partEchCost+"");
				$("#"+lastPartDis.id+"").html("Previous Part Dis "+data.lastPartRate.partDiscount+" in "+data.lastPartRate.partDiscountTaxType+"");
				$("#"+lastPartTax.id+"").html("Previous Part Tax "+data.lastPartRate.partTax+" in "+data.lastPartRate.partDiscountTaxType+"");
				
				$("#"+partCost.id+"").val(data.lastPartRate.partEchCost);
				$("#"+partDiscount.id+"").val(data.lastPartRate.partDiscount);
				$("#"+partTax.id+"").val(data.lastPartRate.partTax);
				
				validatePartTaxDiscount(partDiscount.id);
				validatePartTaxDiscount(partTax.id);
				
				/*if(data.lastPartRate.partDiscountTaxTypeId == 1){
					$('#finalPartDiscountTaxTypId').val(1);
					$('.partPercent').addClass('fa-percent');
					$('.partPercent').removeClass('fa-inr');
					$("#partPercentId").prop('checked',true);
					$("#partAmountId").prop('checked',false);
				}else{
					$('#finalPartDiscountTaxTypId').val(2);
					$('.partPercent').addClass('fa-inr');
					$('.partPercent').removeClass('fa-percent');
					$("#partPercentId").prop('checked',false);
					$("#partAmountId").prop('checked',true);
					
				}*/
			}else{
				$("#"+lastPartCost.id+"").html('');
				$("#"+lastPartDis.id+"").html('');
				$("#"+lastPartTax.id+"").html('');
				
				$("#"+partCost.id+"").val('');
				$("#"+partDiscount.id+"").val('');
				$("#"+partTax.id+"").val('');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function saveMasterPart(){

	var jsonObject				= new Object();
	var selectedPartId 			= $("#selectedPartId").val();
	if($("#modalPartNumber").val().trim() == ""){
		showMessage('info','Please Enter Part Number')
		hideLayer();
		return false;
	}
	if($("#isWarrantyApplicable").prop('checked')&& $("#warrantyInMonths").val() == ""){
		showMessage('info','Please Enter Warranty Monts')
		hideLayer();
		return false;
	}

	jsonObject["partName"]						= $("#modalPartName").val().trim();
	jsonObject["partNumber"]					= $("#modalPartNumber").val().trim();
	jsonObject["warranty"]						= $("#isWarrantyApplicable").prop('checked');
	jsonObject["warrantyInMonth"]				= $("#warrantyInMonths").val();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	showLayer();
	$.ajax({
		url: "saveNewDealerServicePartInMasterPart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var savedMasterPart = data.savedMasterPart;
			if(savedMasterPart != undefined){
				if($("#fromEdit").val() == 'true'){
					$('#editPartId').select2('data', {
						id : savedMasterPart.partid,
						text : savedMasterPart.partnumber +"-"+ savedMasterPart.partname,
						partNumber: savedMasterPart.partnumber,
						isWarrantyApplicable: savedMasterPart.warrantyApplicable,
						warrantyInMonths: savedMasterPart.warrantyInMonths
					});
					$("#modalPartName").val('');
					$("#modalPartNumber").val('');
					$("#partNumberModal").modal('hide');
					$("#editPartDetails").modal('show');
					$("#fromEdit").val('false');
				}else{
					$('#'+selectedPartId+'').select2('data', {
						id : savedMasterPart.partid,
						text : savedMasterPart.partnumber +"-"+ savedMasterPart.partname,
						partNumber: savedMasterPart.partnumber,
						isWarrantyApplicable: savedMasterPart.warrantyApplicable,
						warrantyInMonths: savedMasterPart.warrantyInMonths
					});
					$("#modalPartName").val('');
					$("#modalPartNumber").val('');
					$("#partNumberModal").modal('hide');
				}
			}else if (data.already == true || data.already == 'true'){
				hideLayer();
				showMessage('info', 'Part Already Exist !!!');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function addNewVendor(e){
	if(e.value != "" && e.value != 0){
		var isVendorIdNan			= isNaN(Number(e.value))
		
		if(isVendorIdNan){
			$("#modalVendorName").val(e.value);
			$("#vendorModal").modal('show');
		}
	}
}

var validateEmail = function(elementValue) {
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(elementValue);
}

$('#vendorEmail').keyup(function() {
    var value = $(this).val();
    var valid = validateEmail(value);
    if (!valid) {
        $(this).css('color', 'red');
    } else {
        $(this).css('color', '#000');
    }
});


function saveNewVendor(){
	
	if($("#vendorPhoneNumber").val() == "" ){
		showMessage('info','Please Enter Phone Number');
		hideLayer();
		return false;
	}
	if($("#vendorLocation").val() == "" ){
		showMessage('info','Please Enter Location');
		hideLayer();
		return false;
	}
	
	var jsonObject						= new Object();
	jsonObject["vendorName"]			= $("#modalVendorName").val();
	jsonObject["vendorPhoneNumber"]		= $("#vendorPhoneNumber").val();
	jsonObject["vendorTypeId"]			= 23;
	jsonObject["vendorLocation"]		= $("#vendorLocation").val();
	jsonObject["vendorEmail"]			= $("#vendorEmail").val();
	jsonObject["companyId"]				= $("#companyId").val();
	jsonObject["userId"]				= $("#userId").val();
	
	showLayer();
	$.ajax({
		url: "saveNewVendorFromDse",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var vendor = data.vendor; 
			if(vendor != undefined){
				$('#vendorId').select2('data', {
					id : vendor.vendorId,
					text : vendor.vendorName +"-"+ vendor.vendorLocation 
				});
			}
			
			$("#vendorModal").modal('hide');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	
}

function selectDiscountTaxType(partLabourTypeId,percentageAmountTypeId){
	var labourDis=0;
	
	if(confirm("Are You Sure!, Do You Want To Change Discount/Tax Type, So You Need To Enter It Again") ){
		if(partLabourTypeId == partType){
			if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
				$('#finalPartDiscountTaxTypId').val(2);
				$('.partPercent').addClass('fa-inr');
				$('.partPercent').removeClass('fa-percent');
				$("#partAmountId").prop('checked',true);
				$("#partPercentId").prop('checked',false);
				$('#partAmountLabelId').addClass('focus active')
				$('#partPercentLabelId').removeClass('focus active')
				}, 30);
			}else if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
					$('#finalPartDiscountTaxTypId').val(1);
					$('.partPercent').addClass('fa-percent');
					$('.partPercent').removeClass('fa-inr');
					$("#partPercentId").prop('checked',true);
					$("#partAmountId").prop('checked',false);
					$('#partPercentLabelId').addClass('focus active')
					$('#partAmountLabelId').removeClass('focus active')
					
					
				}, 30);
				
			}
			
			$(".allPartDiscount").val('');
			$(".allPartTax").val('');
			$(".allPartTotalCost").val('');
			
			var partQtyArr 				= new Array();
			var partRateArr 			= new Array();
			var partTotalArr 			= new Array();
			
			$("input[name=partQty]").each(function(){
				partQtyArr.push($(this).val().replace(/"/g, ""));
			});
			$("input[name=partEachCost]").each(function(){
				partRateArr.push($(this).val());
			});
			$("input[name=partTotalCost]").each(function(){
				partTotalArr.push(this.id);
			});
			
			for(var i =0 ; i< partQtyArr.length; i++){
				var totalCost = Number(partQtyArr[i])*Number(partRateArr[i]);
				$("#"+partTotalArr[i]).val(totalCost);
			}
		}else if(partLabourTypeId == labourType){
			
			console.log("percentageType",percentageType)
			if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(2);
				$('.labourPercent').addClass('fa-inr');
				$('.labourPercent').removeClass('fa-percent');
				$('#labourAmountLabelId').addClass('focus active')
				$('#labourPercentLabelId').removeClass('focus active')
				$("#labourAmountId").prop('checked',true);
				$("#labourPercentId").prop('checked',false);
				}, 30);
			}else if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(1);
				$('.labourPercent').addClass('fa-percent');
				$('.labourPercent').removeClass('fa-inr');
				$('#labourPercentLabelId').addClass('focus active')
				$('#labourAmountLabelId').removeClass('focus active')
				$("#labourPercentId").prop('checked',true);
				$("#labourAmountId").prop('checked',false);
				}, 30);
			}
			
			$(".allLabourDiscount").val('');
			$(".allLabourTax").val('');
			$(".allLabourTotalCost").val('');
			
			
			var labourHourArr 				= new Array();
			var labourRateArr 			= new Array();
			var labourTotalCostArr 			= new Array();
			
			$("input[name=labourWorkingHours]").each(function(){
				labourHourArr.push($(this).val());
			});
			$("input[name=labourPerHourCost]").each(function(){
				labourRateArr.push($(this).val());
			});
			$("input[name=totalLabourCost]").each(function(){
				labourTotalCostArr.push(this.id);
			});
			
			for(var i =0 ; i< labourHourArr.length; i++){
				var totalCost = Number(labourHourArr[i])*Number(labourRateArr[i]);
				$("#"+labourTotalCostArr[i]).val(totalCost);
			}

			
		}
		
	}else{
		if(partLabourTypeId == partType){
			if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
				$('#finalPartDiscountTaxTypId').val(2);
				$('.partPercent').addClass('fa-inr');
				$('.partPercent').removeClass('fa-percent');
				$("#partAmountId").prop('checked',true);
				$("#partPercentId").prop('checked',false);
				$('#partAmountLabelId').addClass('focus active')
				$('#partPercentLabelId').removeClass('focus active')
				}, 30);
			}else if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
					$('#finalPartDiscountTaxTypId').val(1);
					$('.partPercent').addClass('fa-percent');
					$('.partPercent').removeClass('fa-inr');
					$("#partPercentId").prop('checked',true);
					$("#partAmountId").prop('checked',false);
					$('#partPercentLabelId').addClass('focus active')
					$('#partAmountLabelId').removeClass('focus active')
					
					
				}, 30);
				
			}
		}else if(partLabourTypeId == labourType){
			
			console.log("percentageType",percentageType)
			if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(2);
				$('.labourPercent').addClass('fa-inr');
				$('.labourPercent').removeClass('fa-percent');
				$('#labourAmountLabelId').addClass('focus active')
				$('#labourPercentLabelId').removeClass('focus active')
				$("#labourAmountId").prop('checked',true);
				$("#labourPercentId").prop('checked',false);
				}, 30);
			}else if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(1);
				$('.labourPercent').addClass('fa-percent');
				$('.labourPercent').removeClass('fa-inr');
				$('#labourPercentLabelId').addClass('focus active')
				$('#labourAmountLabelId').removeClass('focus active')
				$("#labourPercentId").prop('checked',true);
				$("#labourAmountId").prop('checked',false);
				}, 30);
			}
		}
		
	}
}

$(document).ready(function() {
	$("#warrantyInMonths").prop('readonly',true);

	$('#isWarrantyApplicable').change(function() {
		if(this.checked) {
			$("#warrantyInMonths").prop('readonly',false);
		}else{
			$("#warrantyInMonths").prop('readonly',true);
		}

	});
});


$(document).ready(function() {

	$("#assignToId,#modalAssignToId").select2( {
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
                            text: a.firstName + " " + a.lastName,
                            slug: a.slug,
                            id: a.user_id
                        }
                    })
                }
            }
        }
    })
});

function validatePartTaxDiscount(part_Tax_discountId){
	if($("#finalPartDiscountTaxTypId").val()  == 1){
		if($("#"+part_Tax_discountId).val() >= 100){
			showMessage('info','Tax And Discount Can Not Be Greater Than 100');
			$("#"+part_Tax_discountId).val('');
		}
	}
}
function validateLabourTaxDiscount(labour_Tax_discountId){
	if($("#finalLabourDiscountTaxTypId").val()  == 1){
		if($("#"+labour_Tax_discountId).val() >= 100){
			showMessage('info','Tax And Discount Can Not Be Greater Than 100');
			$("#"+labour_Tax_discountId).val('');
		}
	}
}

function getServiceSecheduleList(evt){
	if(!fromWOEdit && $('#showServiceProgram').val() == 'true'){
		var jsonObject						= new Object();
		jsonObject["serviceProgramId"]	= $('#'+evt.id+'').val();
		jsonObject["vid"]				= $('#vehicleId').val();

		if($('#'+evt.id+'').val().trim() == ''){
			setServiceScheduleData(null);
		}
		getScheduleData(jsonObject);		
	}		
}

function getScheduleData(jsonObject){

	$.ajax({
		url: "getServiceReminderByProgramIdVid",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setServiceScheduleData(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceScheduleData(data){
	if(data != null && data.serviceSchedules != undefined && data.serviceSchedules.length > 0){
		$('#serviceSchedules').show();
		var table = "";
		for (var key of Object.keys(data.scheduleHashMap)) {
			var serviceSchedulesList	= data.scheduleHashMap[key];
			if(!(key in programROwMap)){
				programROwMap[key] = key;
				table += '<tr id="'+key+'">'
				+'<td colspan="4"><a style="font-size:16px;" href="#">'+key+'</a></td>'
				+'</tr>';
			}
			for(var i= 0; i<serviceSchedulesList.length; i++){
				if(!(serviceSchedulesList[i].service_id in selectedServiceProgram)){
					selectedServiceProgram[serviceSchedulesList[i].service_id] = serviceSchedulesList[i].service_id;
					table += '<tr id="row_'+serviceSchedulesList[i].service_id+'">'
					+'<td><input id="'+serviceSchedulesList[i].service_id+'" name="selectedSchedule" type="checkbox" class="se" onclick="handleCheckboxClick('+serviceSchedulesList[i].service_id+','+serviceSchedulesList[i].serviceTypeId+', '+serviceSchedulesList[i].serviceSubTypeId+')"/> </td>'
					+'<td>'+serviceSchedulesList[i].taskAndSchedule+'</td>'
					+'<td>'+serviceSchedulesList[i].nextDue+'</td>'
					+'</tr>';
				}
			}
		}

		$('#serviceSchedulesTable').append(table);   

		for (var key of Object.keys(selectedServiceProgram)) {
			var keyFound = false;
			for(var j= 0; j<data.serviceSchedules.length; j++){
				if((data.serviceSchedules[j].service_id == key)){
					keyFound = true;
				}
			}
			if(!keyFound){
				$('#row_'+key+'').remove();
				delete selectedServiceProgram[key];
			}
		}

		for (var key of Object.keys(programROwMap)) {
			if(!(key in data.scheduleHashMap)){
				$('#'+key+'').remove();
				delete programROwMap[key];
			}
		}

	}else{
		$('#serviceSchedulesTable').empty(); 
		selectedServiceProgram = new Map();
		programROwMap	= new Map();
		$('#serviceSchedules').hide();
	}
}

function handleCheckboxClick(serviceId,serviceTypeId,serviceSubTypeId) {
	   
	var jsonObject			   = new Object();
	jsonObject["vid"]	           = $("#vehicleId").val();
	jsonObject["serviceId"]		   = serviceId;
	jsonObject["ServiceTypeId"]	   = serviceTypeId;
	jsonObject["ServiceSubTypeId"]     = serviceSubTypeId;
	jsonObject["companyId"]            = $('#companyId').val();;
	    
	 $.ajax({
		url : "checkDueAndOverDueStatus",
		type : "POST",
		dataType : "json",
		data :jsonObject ,
		success : function(data){
			
			if ($('#'+serviceId).is(':checked')) {
				if(data.warningMsg == true ||  data.warningMsg == "true"){
				
					showMessage('warning', 'JobType & SubType, Not In Due/Overdue. Do you want to continue ??');
        
				}
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
			
		}
		
	});
}
			

function setServiceProgramList(data){
	if(data != undefined)
	var serviceProgramList = JSON.parse(data);
	var array	 = new Array();
	if(serviceProgramList != undefined)
	for(var i = 0; i< serviceProgramList.length ; i++){
		var locationDetails	= new Object();
			locationDetails.id = serviceProgramList[i].serviceProgramId;
			locationDetails.text = serviceProgramList[i].serviceProgram;
		array.push(locationDetails);
	}
	$('#serviceReminderId').data().select2.updateSelection(array);

	if($('#showServiceProgram').val() == 'true'){
		
	var jsonObject						= new Object();
	jsonObject["serviceProgramId"]	= $('#serviceReminderId').val();
	jsonObject["vid"]				= $('#vehicleId').val();

	getScheduleData(jsonObject);
	setServiceReminderCheckBox();
	}
}

function setServiceReminderCheckBox(){
	var serviceReminderIds	= $('#serviceReminderIds').val().split(',');
	setTimeout(function(){ 
		for(var i=0; i<serviceReminderIds.length;i++){
			$('#'+serviceReminderIds[i]+'').prop('checked', true);
			$('#'+serviceReminderIds[i]+'').attr('disabled', true);
		}
	}, 500);

}
//function validateOdometer(){ 
//	var expectedOdo		 = Number($('#vehicle_ExpectedOdameter').val()) + Number($('#vehicle_Odameter').val());
//	var Odometer		 = Number($('#vehicle_Odameter').val());
//	var tripOpeningKM    = Number($('#vehicleOdometer').val());
//	
//	if($('#vehicle_ExpectedOdameter').val() == undefined || $('#vehicle_ExpectedOdameter').val() == '' || $('#vehicle_ExpectedOdameter').val() == null){
//		return true;
//	}
//	
//	if($('#editDSE').val() == undefined){
//		if(tripOpeningKM > 0 && tripOpeningKM < Odometer){
//			$('#tripOpeningKM').focus();
//			showMessage('errors', ' Odometer Should Not Be Less Than '+Odometer);
//			return false;
//		}
//	}
//	
//	if(tripOpeningKM > 0 && tripOpeningKM > expectedOdo){
//		$('#vehicle_Odometer').focus();
//		showMessage('errors', ' Odometer cannot be greater than '+expectedOdo );
//		return false;
//	}
//	return true;
//}

function validateOdometer(){ 
	var maxOdometer= Number($("#backDateMaxOdo").val());
	var minOdometer =Number($("#backDateMinOdo").val());
	
	var Odometer		 = Number($('#vehicle_Odameter').val());
	var tripOpeningKM    = Number($('#vehicleOdometer').val());
	
	if($('#meterNotWorkingId').is(':checked')){
		return true;
	}
	
	if (tripOpeningKM == 0){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer should be greater than 0 ' );
		return false;
	}
	
	if(tripOpeningKM < minOdometer){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer should be greater than '+minOdometer );
		return false;
	}
	if($('#vehicle_ExpectedOdameter').val() == undefined || $('#vehicle_ExpectedOdameter').val() == '' || $('#vehicle_ExpectedOdameter').val() == null){
		return true;
	}
	
	if(tripOpeningKM > maxOdometer){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer cannot be greater than '+maxOdometer );
		return false;
		
	}
	return true;
}

function backdateOdometerValidation(){
	var jsonObject = new Object();
	if(($('#validateOdometer').val() == true || $('#validateOdometer').val() == 'true') && $('#vehicleId').val() >0 && $('#invoicestartDate').val() != ""){
		var date = ($('#invoicestartDate').val()).trim();
	jsonObject['vid'] 								= $('#vehicleId').val();
	jsonObject['invoiceDate'] 						= date.split('-').reverse().join("-");
	jsonObject['fromEdit'] 							= $("#fromEdit").val();
	jsonObject['id'] 								= $("#id").val();
	
	$.ajax({
		url : "backdateDSEOdometerValidation",
		type : "POST",
		dataType : "json",
		data :jsonObject ,
		success : function(data){
			
			if(data.minOdometer > 0 && data.maxOdometer >0){
				$("#backDateMinOdo").val(data.minOdometer);
				$("#backDateMaxOdo").val(data.maxOdometer);
				if($("#fromEdit").val() == undefined ){
				$('#vehicleOdometer').val('');
				}
				}else if(data.minOdometer == 0 && data.maxOdometer == 0){
					$("#backDateMinOdo").val($('#vehicle_Odameter').val());
					$("#backDateMaxOdo").val(Number($('#vehicle_ExpectedOdameter').val()) + Number($('#vehicle_Odameter').val()));
					document.getElementById("vehicleOdometer").placeholder = ''+$('#vehicle_Odameter').val()+' ';
				}else{
					$("#backDateMinOdo").val(0);
					$("#backDateMaxOdo").val(Number($('#vehicle_ExpectedOdameter').val()) + Number($('#vehicle_Odameter').val()));
				}
			document.getElementById("vehicleOdometer").placeholder = ' Enter between '+$("#backDateMinOdo").val()+' to '+$("#backDateMaxOdo").val()+' ';

		}
	})
	}	
}

