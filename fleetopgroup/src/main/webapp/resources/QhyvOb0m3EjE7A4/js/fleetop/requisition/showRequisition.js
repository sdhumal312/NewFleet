let SUBREQUISITION_REJECTED = 1;
let SUBREQUISITION_APPROVED = 2;

let REQUISITION_APPROVED = 3;
let REQUISITION_COMPLETE = 4;
let REQUISITION_MARKED_COMPLETE = 5;

let PART_RERUISITION = 1;
let TYRE_RERUISITION = 2;
let BATTARY_RERUISITION = 3;
let CLOTH_RERUISITION = 4;
let UREA_RERUISITION = 5;

let TRANSFER_BRANCH = 1;
let TRANSFER_PO = 2;

let APPROVAL_STATUS_CREATED = 0;
let APPROVAL_STATUS_TRANSFER_REJECTED = 1;
let APPROVAL_STATUS_TRANSFER_COMPLETE = 2;
let APPROVAL_STATUS_TRANSFER_RECEIVED = 3;
let APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED = 4;
let APPROVAL_STATUS_PO_CREATED = 5;
let APPROVAL_STATUS_PARTIALLY_RECEIVED = 6;
let APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE = 7;
let APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED = 8;
let APPROVAL_STATUS_PARTIALLY_TRANSFERED =11;
let totalTransferedQuantity	= 0;

$(document).ready(function(){
	showLayer();
	let jsonObject = new Object();
	
	jsonObject['requisitionId']		= $('#requisitionId').val();
	jsonObject['companyId']			= $('#companyId').val();

	$.ajax({
		url: "getSubRequisitionById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPartTableData(data.partList, data.approvalHashMap);
			setTyreTableData(data.tyreList,data.approvalHashMap);
			setBatteryTableData(data.battaryList,data.approvalHashMap);
			setureaTableData(data.ureaList,data.approvalHashMap);
			setclothTableData(data.clothList,data.approvalHashMap);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
});

$(document).ready(function(){
	let a =500,
	b=('.divForMore'),
	c=('.addMoreButton'),
	d=1;
	$(c).click(function(e){
		e.preventDefault(),a>d && (d++,$(b).append('<div>'
				+'<div class="box">'
				+'<div class="box-body border border-warning"><br>'
				+'		<div class="row">'
				+'			<div class="col col-md-6">'
				+'				<label class="has-float-label"> <select '
				+'					id="transferType_'+d+'" name="transferType" onchange="hideBranch(this.id)">'
				+'						<option value="0">--- Select ---</option>'
				+'						<option value="1">Branch</option>'
				+'						<option value="2">PO</option>'
				+'				</select> <span style="color: #000000; font-size: 18px;">Transfer'
				+'						Type :</span>'
				+'				</label>'
				+'			</div>'
				+'			<div id="locationDiv_'+d+'" class="col col-md-6 hide">'
				+'				<label class="has-float-label"> <input type="hidden"'
				+'					name="requisitionlocation" id="requisitionlocation_'+d+'"'
				+'					onchange="getLocationWiseStock(this.id)" style="width: 80%"> <span'
				+'					style="color: #000000; font-size: 18px;">From Branch</span>'
				+'				</label> <samp style="color: blue;font-weight: bold" id="stockQuantity_'+d+'"></samp> <br>'
				+'			<input type="hidden" id="availableStock_'+d+'" name="availableStock">'
				+'			</div>'
				+'			<div class="col col-md-6">'
				+'				<label class="has-float-label"> <input type="text"'
				+'					class="form-control browser-default noBackGround" name="quantity"'
				+'					id="quantity_'+d+'"'
				+'					onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="setBalanceStock(this.id)"'
				+'					style="width: 80%"> <span'
				+'					style="color: #000000; font-size: 18px;">Qty</span>'
				+'				</label><br>'
				+'				</div>'
				+'		</div>'
				+'		<div class="row">'
				+'		<div class="col col-md-6">'
				+'		<label class="has-float-label"> <input type="hidden"'
				+'			id="assignToApp_'+d+'" name="assignToApp" class="browser-default"'
				+'				style="line-height: 30px; font-size: 15px; height: 35px;">'
				+'		<span style="color: #000000; font-size: 18px;">Transfer By </span>'
				+'		</label>'
				+'		</div>'
				+'		<div class="col col-md-6">'
				+'		<label class="has-float-label"> <input type="text"'
				+'			class="form-control browser-default custom-select noBackGround"'
				+'				name="appRemark" id="appRemark_'+d+'" placeholder="please Enter Remark" style="width:80%"> <span'
				+'				style="color: #000000; font-size: 18px;">Remark</span>'
				+'				</label>'
				+'				</div></div>'
				+'<a class="remove col col-sm-1 col-md-3" href="javascript:void(0)"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>'
				+'</div>'
				+'	</div>'
				+'	</div>'
		),
		prepareSelect3('_'+d)
		),
		$(b).on('click','.remove',function(e){
			e.preventDefault,$(this).parent('div').remove(),d--
		})
	});
})

function prepareSelect3(d){
	if(d == undefined){
		d='';
	}
	$('#quantity'+d).attr('placeholder','Total Qty Should be less than '+$('#totalQuantitySamp').text());
	$('#transferType'+d).select2();
	$('#transferTypeId').select2();
	
	$("#requisitionlocation"+d+",#requisitionlocationId").select2({
		 minimumInputLength: 0,
	        minimumResultsForSearch: 10,
	        multiple: 0,
	        ajax: {
	            url: "getStockWiseBranchList",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    subRequisitionId: $('#subRequisitionIdStock').val()
	                }
	            },
	            results: function(a) {
	            	if(a != undefined && a.locationWisePartQuantity != undefined)
	            	a =a.locationWisePartQuantity
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                            text: a.location+" Stock-"+a.quantity,
	                            slug: a.quantity,
	                            id: a.locationId
	                        }
	                    })
	                }
	            }
	        }
   });
	
	$("#assignToApp"+d+",#assignToSingle").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getAllUserListByCompanyId",
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
    });
	
}

function setPartTableData(data,approvalHashMap){
	var partList 		= data;
	$("#partTable").empty();
	
	totalTransferedQuantity	= 0;
	
	if(partList != undefined && partList != null && partList.length > 0){
		var approvedArray = new Array();
		var approvedArray1 = new Array();
		
		var hideHead = false;
		for(var index = 0 ; index < partList.length; index++){
			if(partList[index].subRequisitionStatus == SUBREQUISITION_APPROVED){
				var singleSubPart =$('<tr>');
				var td1 =$('<td>')
				singleSubPart.append(td1.append(index+1));
				var td =  $("<td colspan='3'>");
				//<a href='javascript:void(0)'  onclick='showStock("+partList[index].subRequisitionId+","+partList[index].requisitionType+",\""+partList[index].partName+"\")'>"+partList[index].partName+"</a>
				singleSubPart.append(td.append("<span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;Part Name : </span> <a data-toggle='tooltip' data-placement='top' title='"+partList[index].pmuName+"' href='javascript:void(0)'>"+partList[index].partName+"</a>"));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Total Quantity :  </span> "+partList[index].quantity));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Approved Quantity :  </span> "+partList[index].approvedQuantity));
				var diff= Number(partList[index].quantity) - Number(partList[index].approvedQuantity);
				if(diff>0 && $('#requisitionStatusId').val() < REQUISITION_APPROVED) // before approval
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%;color:red'> 	&#160; 	&#160;   Pending Quantity : "+diff.toFixed(2)+" </span> "));
				if($('#requisitionStatusId').val() < REQUISITION_APPROVED){ // before approval
					singleSubPart.append(td.append("	&#160; 	&#160; 	&#160; 	&#160;  &#160; 	&#160; 	&#160; 	&#160; 	&#160; 	&#160;	&#160; 	&#160;<a class='btn btn-warning' onclick='getApprovalDetails("+partList[index].subRequisitionId+")'>Alter Approval</a>"));
				}
				
				if(approvalHashMap != undefined && approvalHashMap != null)
					var approvalList= approvalHashMap[partList[index].subRequisitionId] ;
			
				if(approvalList != undefined && approvalList != null && approvalList.length > 0) {
					var poList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_PO;
					});
					
					var branchList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_BRANCH;
					});
					
					if(poList.length > 0) {
						var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
						singleSubPart.append(td.append('<br><br><br>'));
						singleSubPart.append(td.append(table.append(prepareTableHeadForPo())));
						singleSubPart.append(td.append(table.append(prepareTableBody(poList, partList[index].subRequisitionId, PART_RERUISITION))));
						//singleSubPart.append(td.append('<br><br><br>'))
						approvedArray.push(singleSubPart);
					}
					
					if(branchList.length > 0) {
						var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
						singleSubPart.append(td.append('<br><br><br>'));
						singleSubPart.append(td.append(table.append(prepareTableHead())));
						singleSubPart.append(td.append(table.append(prepareTableBody(branchList, partList[index].subRequisitionId, PART_RERUISITION))));
						//singleSubPart.append(td.append('<br><br><br>'))
						approvedArray1.push(singleSubPart);
					}
				}
			}else{
				hideHead = true;
				var columnArray = new Array();
				var newTr = $('<tr>');
				columnArray.push("<td>"+(index+1)+"</td>");
				columnArray.push("<td style='color : blue'> <a href='javascript:void(0)' data-toggle='tooltip' data-placement='top' title='"+partList[index].pmuName+"' onclick='showStock("+partList[index].subRequisitionId+","+partList[index].requisitionType+",\""+partList[index].partName+"\")'>"+partList[index].partName+"</a></td>");
				columnArray.push("<td>"+ partList[index].quantity  +"</td>");

				var actionButton ="<td style='vertical-align: middle;'> ";
				if(partList[index].subRequisitionStatus != null && partList[index].subRequisitionStatus != undefined && partList[index].subRequisitionStatus > 0){
					if(partList[index].subRequisitionStatus == SUBREQUISITION_REJECTED){
						actionButton+= '<a href="javascript:void(0)" style="color:red" data-toggle="tooltip" data-placement="top" title="'+partList[index].remark+'">';
						actionButton+= partList[index].subRequisitionStatusName+'</a></td>';
						}
				}else{
					var checkAuth = true;
					if(($('#allowToEdit').val() == true || $('#allowToEdit').val() === 'true') || ($('#allPermission').val() == true || $('#allPermission').val() === 'true')){
						actionButton += "<button type='button' class='btn btn-info btn-sm'  onclick='editPartReqDetails("+partList[index].subRequisitionId+","+partList[index].requisitionType+","+partList[index].partId+",\""+partList[index].partName+"\","+ partList[index].quantity+");'><i class='fa fa-pencil'></i></button>&nbsp" ;
						checkAuth = false;
					}
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || ($('#requisitionAssignTo').val() == $('#userId').val())){
						 actionButton += "<button class='btn btn-success btn-sm' onclick='showApprovalModal("+partList[index].subRequisitionId+","+partList[index].requisitionType+","+ partList[index].quantity+",\""+partList[index].partName+"\")'><em class='fa fa-check' aria-hidden='true'></em></button>&nbsp<button class='btn btn-danger btn-sm' onclick='rejectionModal("+partList[index].subRequisitionId+")'><em class='fa fa-ban'> </em></button>";
					}else{
						if(checkAuth)
						actionButton +="unauthrised !";
					}
				}
				actionButton +=	"</td>";
				columnArray.push(actionButton);
				$('#partTable').append(newTr.append(columnArray));
			}
		}
		if(!hideHead){
			$('#partHead').hide();
		}
		$('#partTable').append(approvedArray);
		$('#partTableForBranch').append(approvedArray1);
		console.log(approvedArray1.length,"************")
		if(approvedArray1.length >0)
		$('#receiveAllButton').removeClass('hide');
		$('#partShowDiv').show();
		
		if(totalTransferedQuantity > 0) {
			$('#partPrint').css('display' , 'inline-block');
		}
		
		}else{
		$('#partShowDiv').hide();
		}
}
function setTyreTableData(data,approvalHashMap){
	var dataList 		= data;
	$("#tyreTable").empty();
	if(dataList != undefined && dataList != null && dataList.length > 0){
		var approvedArray = new Array();
		var approvedArray2 = new Array();
		var hideHead = false;
		for(var index = 0 ; index < dataList.length; index++){
			if(dataList[index].subRequisitionStatus == SUBREQUISITION_APPROVED){
				var singleSubPart =$('<tr>');
				var td1 =$('<td>')
				singleSubPart.append(td1.append(index+1));
				var td =  $("<td colspan='5'>");
				var tyreName ="<span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;Tyre Model : </span> "+dataList[index].tyreModelName+" ";
//				if($('#showManufacturerAndSize').val() == 'true')
//				tyreName+=" <span style='font-weight:bolder;font-size:110%'>&#160; ManuFacturer : </span>"+dataList[index].tyreManufacturerName+"<span style='font-weight:bolder;font-size:110%'> &#160; Size : </span> "+dataList[index].tyreSizeName;
				singleSubPart.append(td.append(tyreName));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Total Quantity :  </span> "+dataList[index].quantity));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Approved Quantity :  </span> "+dataList[index].approvedQuantity));
				var diff= Number(dataList[index].quantity) - Number(dataList[index].approvedQuantity);
				if(diff>0 && $('#requisitionStatusId').val() < REQUISITION_APPROVED)
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%;color:red'> 	&#160; 	&#160;   Pending Quantity : "+diff.toFixed(2)+" </span> "));
				if($('#requisitionStatusId').val() < REQUISITION_APPROVED){ // before approval
				singleSubPart.append(td.append("	&#160; 	&#160; 	&#160; 	&#160;  &#160; 	&#160; 	&#160; 	&#160; 	&#160; 	&#160;	&#160; 	&#160;<a class='btn btn-warning' onclick='getApprovalDetails("+dataList[index].subRequisitionId+")'>Alter Approval</a>"));
				}
				if(approvalHashMap != undefined && approvalHashMap != null)
				var approvalList= approvalHashMap[dataList[index].subRequisitionId] ;
				if(approvalList != undefined && approvalList != null && approvalList.length>0){
					
					
					var poList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_PO;
					});
					var branchList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_BRANCH;
					});
					
					if(poList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHeadForPo())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,TYRE_RERUISITION))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray.push(singleSubPart);
					}
					if(branchList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHead())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,TYRE_RERUISITION))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray2.push(singleSubPart);
					}
				}
			}else{
				hideHead = true;
				var columnArray = new Array();
				columnArray.push("<td class='fit'>"+(index+1)+"</td>");
				columnArray.push("<td style='color : blue'> <a href='javascript:void(0)' onclick='showStock("+dataList[index].subRequisitionId+","+TYRE_RERUISITION+",\""+dataList[index].tyreModelName+"\")'>"+dataList[index].tyreModelName+"</a></td>");
//				 if($('#showManufacturerAndSize').val() == 'true'){
//					 columnArray.push("<td class='fit'>"+dataList[index].tyreManufacturerName+"</td>");
//					 columnArray.push("<td class='fit'>"+dataList[index].tyreSizeName+"</td>");
//				 }
				columnArray.push("<td class='fit'>"+ dataList[index].quantity+"</td>");
				var actionButton ="<td class='fit'>";
				if(dataList[index].subRequisitionStatus != null && dataList[index].subRequisitionStatus != undefined && dataList[index].subRequisitionStatus > 0){
					if(dataList[index].subRequisitionStatus == SUBREQUISITION_REJECTED){
					actionButton+= '<a href="javascript:void(0)" style="color:red" data-toggle="tooltip" data-placement="top" title="'+dataList[index].remark+'">';
					actionButton+= dataList[index].subRequisitionStatusName+'</a></td>';
					} 
				}else{
					var checkAuth = true;
					if(($('#allowToEdit').val() == true || $('#allowToEdit').val() === 'true')||($('#allPermission').val() == true || $('#allPermission').val() === 'true')){
						actionButton+= "<button type='button' class='btn btn-info btn-sm'  onclick='editTyreAndBatteryReq("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].tyreManufacturer+",\""+dataList[index].tyreManufacturerName+"\","+dataList[index].tyreModel+",\""+dataList[index].tyreModelName+"\","+dataList[index].tyreSize+",\""+dataList[index].tyreSizeName+"\","+dataList[index].quantity+");'><em class='fa fa-pencil'></em></button>&nbsp" ;
						checkAuth = false;
					}
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || ($('#requisitionAssignTo').val() == $('#userId').val())){
						actionButton+=	"<button class='btn btn-success btn-sm' onclick='showApprovalModal("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].quantity+",\""+dataList[index].tyreManufacturerName+"\",\""+dataList[index].tyreModelName+"\",\""+dataList[index].tyreSizeName+"\")'><em class='fa fa-check' aria-hidden='true'></em></button>&nbsp<button class='btn btn-danger btn-sm' onclick='rejectionModal("+dataList[index].subRequisitionId+")'><em class='fa fa-ban'> </em></button> ";
					}else{
						if(checkAuth)
							actionButton +="unauthrised !";
					}
				}
				actionButton+="</td>";
				columnArray.push(actionButton);
				$('#tyreTable').append("<tr>" + columnArray.join(' ') + "</tr>");
				
			}
		}
		if(!hideHead){
			$('#tyreHead').hide();
		}
		$('#tyreTable').append(approvedArray);
		$('#tyreTable1').append(approvedArray2);
			$('#tyreShowDiv').show();
		}else{
			$('#tyreShowDiv').hide();
		}
}

function setBatteryTableData(data,approvalHashMap){
	var dataList 		= data;
	$("#batteryTable").empty();
	if(dataList != undefined && dataList != null && dataList.length > 0){
		var approvedArray = new Array();
		var approvedArray3 = new Array();
		var hideHead = false;
		for(var index = 0 ; index < dataList.length; index++){
			if(dataList[index].subRequisitionStatus == SUBREQUISITION_APPROVED){
				var singleSubPart =$('<tr>');
				var td1 =$('<td>')
				singleSubPart.append(td1.append(index+1));
				var td =  $("<td colspan='5'>");
				//"<span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;Battery Model : </span> "+dataList[index].batteryModelName+"  <span style='font-weight:bolder;font-size:110%'>&#160; ManuFacturer : </span>"+dataList[index].batteryManufacturerName+
				singleSubPart.append(td.append("<span style='font-weight:bolder;font-size:110%'> &#160;Battery Capacity : </span> "+dataList[index].batteryCapacityName));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Total Quantity :  </span> "+dataList[index].quantity)); 
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Approved Quantity :  </span> "+dataList[index].approvedQuantity));
				var diff= Number(dataList[index].quantity) - Number(dataList[index].approvedQuantity);
				if(diff>0 && $('#requisitionStatusId').val() < REQUISITION_APPROVED)
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%;color:red'> 	&#160; 	&#160;   Pending Quantity : "+diff.toFixed(2)+" </span> "));
				if($('#requisitionStatusId').val() < REQUISITION_APPROVED){
				singleSubPart.append(td.append("	&#160; 	&#160; 	&#160; 	&#160;  &#160; 	&#160; 	&#160; 	&#160; 	&#160; 	&#160;	&#160; 	&#160;<a class='btn btn-warning' onclick='getApprovalDetails("+dataList[index].subRequisitionId+")'>Alter Approval</a>"));
				}
				if(approvalHashMap != undefined && approvalHashMap != null)
					var approvalList= approvalHashMap[dataList[index].subRequisitionId] ;
				if(approvalList != undefined && approvalList != null && approvalList.length>0){
					
						
					var poList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_PO;
					});
					var branchList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_BRANCH;
					});
					if(poList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHeadForPo())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,BATTARY_RERUISITION ))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray.push(singleSubPart);
					}
					if(branchList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHead())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,BATTARY_RERUISITION ))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray3.push(singleSubPart);
					
					}
					
				}
			}else{
				hideHead = true;
				var columnArray = new Array();
				columnArray.push("<td>"+(index+1)+"</td>");
				columnArray.push("<td style='color : blue'><a href='javascript:void(0)' onclick='showStock("+dataList[index].subRequisitionId+","+BATTARY_RERUISITION+",\""+dataList[index].batteryCapacityName+"\")'>"+dataList[index].batteryCapacityName+"</a></td>");
//				columnArray.push("<td>"+dataList[index].batteryModelName+"</td>");
//				columnArray.push("<td>"+dataList[index].batteryCapacityName+"</td>");
				columnArray.push("<td>"+ dataList[index].quantity  +"</td>");
				var actionButton ="<td style='vertical-align: middle;'>";
				if(dataList[index].subRequisitionStatus != null && dataList[index].subRequisitionStatus != undefined && dataList[index].subRequisitionStatus > 0){
					if(dataList[index].subRequisitionStatus == SUBREQUISITION_REJECTED){
						actionButton+= '<a href="javascript:void(0)" style="color:red" data-toggle="tooltip" data-placement="top" title="'+dataList[index].remark+'">';
						actionButton+= dataList[index].subRequisitionStatusName+'</a></td>';
						}
				}else{
					var checkAuth = true;
					if(($('#allowToEdit').val() == true || $('#allowToEdit').val() === 'true')||($('#allPermission').val() == true || $('#allPermission').val() === 'true')){
						actionButton+="<button type='button' class='btn btn-info btn-sm'  onclick='editTyreAndBatteryReq("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].batteryManufacturer+",\""+dataList[index].batteryManufacturerName+"\","+dataList[index].batteryModel+", \""+dataList[index].batteryModelName+"\","+dataList[index].batteryCapacity+",\""+dataList[index].batteryCapacityName+"\","+dataList[index].quantity+");'><em class='fa fa-pencil'></em></button>&nbsp";
						checkAuth = false;
					}
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || ($('#requisitionAssignTo').val() == $('#userId').val())){
						actionButton+="<button class='btn btn-success btn-sm' onclick='showApprovalModal("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].quantity+",\""+dataList[index].batteryManufacturerName+"\", \""+dataList[index].batteryModelName+"\",\""+dataList[index].batteryCapacityName+"\")'><em class='fa fa-check' aria-hidden='true'></em></button>&nbsp<button class='btn btn-danger btn-sm' onclick='rejectionModal("+dataList[index].subRequisitionId+")'><em class='fa fa-ban'> </em></button>";
					}else{
						if(checkAuth)
							actionButton +="unauthrised !";
					}
				}
				actionButton+="</td>";
				columnArray.push(actionButton);
				$('#batteryTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}
		}
		if(!hideHead){
			$('#BatteryHead').hide();
		}
		$('#batteryTable').append(approvedArray);
		$('#batteryTable3').append(approvedArray3);
		$('#batteryShowDiv').show();
	}else{
		$('#batteryShowDiv').hide();
	}
}
function setureaTableData(data,approvalHashMap){
	var dataList 		= data;
	$("#ureaTable").empty();
	if(dataList != undefined && dataList != null && dataList.length > 0){
		var approvedArray = new Array();
		var approvedArray4 = new Array();
		var hideHead = false;
		for(var index = 0 ; index < dataList.length; index++){
			if(dataList[index].subRequisitionStatus == SUBREQUISITION_APPROVED){
				var singleSubPart =$('<tr>');
				var td1 =$('<td>')
				singleSubPart.append(td1.append(index+1));
				var td =  $("<td colspan='3'>");
//				singleSubPart.append(td.append("<span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;Urea Name : </span>"+dataList[index].ureaName));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Total Quantity :  </span> "+dataList[index].quantity)); 
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Approved Quantity :  </span> "+dataList[index].approvedQuantity));
				var diff= Number(dataList[index].quantity) - Number(dataList[index].approvedQuantity);
				if(diff>0 && $('#requisitionStatusId').val() < REQUISITION_APPROVED)
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%;color:red'> 	&#160; 	&#160;   Pending Quantity : "+diff.toFixed(2)+" </span> "));
				if($('#requisitionStatusId').val() < REQUISITION_APPROVED){
				singleSubPart.append(td.append("	&#160; 	&#160; 	&#160; 	&#160;  &#160; 	&#160; 	&#160; 	&#160; 	&#160; 	&#160;	&#160; 	&#160;<a class='btn btn-warning' onclick='getApprovalDetails("+dataList[index].subRequisitionId+")'>Alter Approval</a>"));
				}
				if(approvalHashMap != undefined && approvalHashMap != null)
					var approvalList= approvalHashMap[dataList[index].subRequisitionId] ;
				if(approvalList != undefined && approvalList != null && approvalList.length>0){
					
						
					var poList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_PO;
					});
					var branchList = approvalList.filter(function (el) {
					  return el.approvedTypeId == TRANSFER_BRANCH;
					});
					if(poList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHeadForPo())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,UREA_RERUISITION ))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray.push(singleSubPart);
					}
					if(branchList.length > 0) {
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHead())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,UREA_RERUISITION ))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray4.push(singleSubPart);
					}
				}
				
			}else{
				hideHead = true;
				var columnArray = new Array();
				columnArray.push("<td>"+(index+1)+"</td>");
//				columnArray.push("<td style='color : blue'> <a onclick='showStock("+dataList[index].ureaId+","+dataList[index].requisitionType+",\""+dataList[index].ureaName+"\")' >"+dataList[index].ureaName+"</a></td>");
				columnArray.push("<td style='color : blue'><a href='javascript:void(0)' onclick='showStock("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+",\" \")'>"+ dataList[index].quantity  +"</a></td>");
				var actionButton ="<td style='vertical-align: middle;'>";
				if(dataList[index].subRequisitionStatus != null && dataList[index].subRequisitionStatus != undefined && dataList[index].subRequisitionStatus > 0){
					if(dataList[index].subRequisitionStatus == SUBREQUISITION_REJECTED){
						actionButton+= '<a href="javascript:void(0)" style="color:red" data-toggle="tooltip" data-placement="top" title="'+dataList[index].remark+'">';
						actionButton+= dataList[index].subRequisitionStatusName+'</a></td>';
						}
				}else{
					var checkAuth = true;
					if(($('#allowToEdit').val() == true || $('#allowToEdit').val() === 'true')||($('#allPermission').val() == true || $('#allPermission').val() === 'true')){
						actionButton+="<button type='button' class='btn btn-info btn-sm'  onclick='editPartReqDetails("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].ureaId+",\""+dataList[index].ureaName+"\","+dataList[index].quantity+");'><em class='fa fa-pencil'></em></button>&nbsp ";
						checkAuth = false;
					}
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || ($('#requisitionAssignTo').val() == $('#userId').val())){
						actionButton+=	" <button class='btn btn-success btn-sm' onclick='showApprovalModal("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].quantity+")'><em class='fa fa-check' aria-hidden='true'></em></button>&nbsp<button class='btn btn-danger btn-sm' onclick='rejectionModal("+dataList[index].subRequisitionId+")'><em class='fa fa-ban'> </em></button>";
					}else{
						if(checkAuth)
						actionButton +="unauthrised !"
					}
				}
				actionButton+="</td>";
				columnArray.push(actionButton);
				$('#ureaTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}
		}
		if(!hideHead){
			$('#ureaHead').hide();
		}
		$('#ureaTable').append(approvedArray);
		$('#ureaTable4').append(approvedArray4);
		$('#ureaShowDiv').show();
	}else{
			$('#ureaShowDiv').hide();
		}
}

function setclothTableData(data,approvalHashMap){
	var dataList 		= data;
	$("#clothTable").empty();
	if(dataList != undefined && dataList != null && dataList.length > 0){
		var approvedArray = new Array();
		var hideHead = false;
		for(var index = 0 ; index < dataList.length; index++){
			if(dataList[index].subRequisitionStatus == SUBREQUISITION_APPROVED){
				var singleSubPart =$('<tr>');
				var td1 =$('<td>')
				singleSubPart.append(td1.append(index+1));
				var td =  $("<td colspan='3'>");
				singleSubPart.append(td.append("<span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;Upholstery Name : </span>"+dataList[index].clothName));
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Total Quantity :  </span> "+dataList[index].quantity)); 
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%'> 	&#160; 	&#160;   Approved Quantity :  </span> "+dataList[index].approvedQuantity));
				var diff= Number(dataList[index].quantity) - Number(dataList[index].approvedQuantity);
				if(diff>0 && $('#requisitionStatusId').val() < REQUISITION_APPROVED)
				singleSubPart.append(td.append(" <span style='font-weight:bolder;font-size:110%;color:red'> 	&#160; 	&#160;   Pending Quantity : "+diff.toFixed(2)+" </span> "));
				if($('#requisitionStatusId').val() < REQUISITION_APPROVED){
				singleSubPart.append(td.append("	&#160; 	&#160; 	&#160; 	&#160;  &#160; 	&#160; 	&#160; 	&#160; 	&#160; 	&#160;	&#160; 	&#160;<a class='btn btn-warning' onclick='getApprovalDetails("+dataList[index].subRequisitionId+")'>Alter Approval</a>"));
				}
				if(approvalHashMap != undefined && approvalHashMap != null)
					var approvalList= approvalHashMap[dataList[index].subRequisitionId] ;
				if(approvalList != undefined && approvalList != null && approvalList.length>0){
					var table=$('<table class="table-resposive table table-hover table-bordered" style="width : 100%">');
					singleSubPart.append(td.append('<br><br><br>'));
					singleSubPart.append(td.append(table.append(prepareTableHead())));
					singleSubPart.append(td.append(table.append(prepareTableBody(approvalList,dataList[index].subRequisitionId,CLOTH_RERUISITION ))));
					//singleSubPart.append(td.append('<br><br><br>'))
					approvedArray.push(singleSubPart);
				}
			}else{
				hideHead = true;
			var columnArray = new Array();
			columnArray.push("<td>"+(index+1)+"</td>");
			columnArray.push("<td style='color : blue'> <a onclick='showStock("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+",\""+dataList[index].clothName+"\")'>"+dataList[index].clothName+"</a></td>");
			columnArray.push("<td>"+ dataList[index].quantity  +"</td>");
			var actionButton ="<td style='vertical-align: middle;'>";
			if(dataList[index].subRequisitionStatus != null && dataList[index].subRequisitionStatus != undefined && dataList[index].subRequisitionStatus > 0){
				if(dataList[index].subRequisitionStatus == SUBREQUISITION_REJECTED){
					actionButton+= '<a href="javascript:void(0)" style="color:red" data-toggle="tooltip" data-placement="top" title="'+dataList[index].remark+'">';
					actionButton+= dataList[index].subRequisitionStatusName+'</a></td>';
					}
			}else{
				var checkAuth = true;
			if(($('#allowToEdit').val() == true || $('#allowToEdit').val() === 'true')||($('#allPermission').val() == true || $('#allPermission').val() === 'true')){
				actionButton+="<button type='button' class='btn btn-info btn-sm'  onclick='editPartReqDetails("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].clothId+",\""+dataList[index].clothName+"\","+dataList[index].quantity+");'><em class='fa fa-pencil'></em></button>&nbsp "; 
				checkAuth = false;
			}
			if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || ($('#requisitionAssignTo').val() == $('#userId').val())){
				actionButton+=	"<button class='btn btn-success btn-sm' onclick='showApprovalModal("+dataList[index].subRequisitionId+","+dataList[index].requisitionType+","+dataList[index].quantity+",\""+dataList[index].clothName+"\")'><em class='fa fa-check' aria-hidden='true'></em></button>&nbsp<button class='btn btn-danger btn-sm' onclick='rejectionModal("+dataList[index].subRequisitionId+")'><em class='fa fa-ban'> </em></button>";
			}else{
				if(checkAuth)
					actionButton +="unauthrised !"
				}
			}
			actionButton+="</td>";
			columnArray.push(actionButton);
			$('#clothTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
			}
		if(!hideHead){
			$('#clothHead').hide();
		}
		$('#clothTable').append(approvedArray);
			$('#clothShowDiv').show();
		}else{
			$('#clothShowDiv').hide();
		}
}

function editPartReqDetails(reqId,reqType,id,name,qnty){
	$('#editSubReqModal').modal('show');
	$('#requisitionType').select2('readonly',true);
	$('#requisitionEditId').val(reqId)
	if(reqType == 1){
		$('#requisitionType').val(1);
		$('#requisitionType').trigger('change');
		$('#partId').select2('data',{
			id : id,
			text : name
		});
	}else if(reqType == 5){
		$('#requisitionType').val(5);
		$('#requisitionType').trigger('change');
		$('#ureaManufacturer').select2('data',{
			id : id,
			text : name
		})
	}else if (reqType == 4){
		$('#requisitionType').val(4);
		$('#requisitionType').trigger('change');
		$('#clothTypes').select2('data',{
			id : id,
			text : name
		})
	}
	$('#Qty').val(qnty);
}
function editTyreAndBatteryReq(reqId,reqType,manId,manName,modelId,modelName,sizeId,sizeName,qnty){
	$('#editSubReqModal').modal('show');
	$('#requisitionType').select2('readonly',true);
	
	$('#requisitionEditId').val(reqId)
	
	if(reqType == 2){
		$('#requisitionType').val(2);
		$('#requisitionType').trigger('change');
		setTimeout(() => {
			$('#tyremodel').select2('data',{
				id : modelId,
				text : modelName
			});
			$('#manufacurer').select2('data',{
				id : manId,
				text : manName
			});
			$('#tyreSize').select2('data',{
				id : sizeId,
				text : sizeName
			});
		}, 200);
	
	}else if(reqType == 3){
		$('#requisitionType').val(3);
		$('#requisitionType').trigger('change');
			$('#batteryManufacturer').select2('data',{
				id : manId,
				text : manName
			});
				$("#batterryTypeId").append($("<option>").text(modelName).attr("value",modelId));
				$('#batterryTypeId').select2('data',{
					id : modelId,
					text : modelName,
					val : modelId
				});
			$('#batteryCapacityId').select2('data',{
				id : sizeId,
				text : sizeName
			});
	}
	$('#Qty').val(qnty);
}

function saveEditSubRequisition(){
	showLayer();
	let returnVal= validateMultiType('requisitionType',$('#requisitionType').val());
	if(!returnVal){
		hideLayer();
		showMessage('info', 'Please Fill Highlighted Fields to process !!!');
		return false
	}
	var jsonObject = prepareObject('requisitionType',$('#requisitionType').val());
	jsonObject['requisitionEditId'] =$('#requisitionEditId').val();
	$.ajax({
		url: "updateSubRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.save != undefined && (data.save == true || data.save === 'true')){
				window.location.reload();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function rejectionModal(subRequisitionId){
	$('#rejectModal').show();
	$('#rejectRemark').val("");
	$('#reqIdForReject').val(subRequisitionId);
	$('#rejectSubReqButtton').show()
	$('#rejectAppButtton').hide()
	$('#rejectReceiveButtton').hide();
	
}
function rejectSubRequisition(){
	showLayer();
	var jsonObject = new Object() ;
	
	if($('#rejectRemark').val() == undefined || ($('#rejectRemark').val()).trim() == ''){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	jsonObject['subRequisitionId'] =$('#reqIdForReject').val();
	jsonObject['rejectRemark'] =$('#rejectRemark').val();
	
	
	$.ajax({
		url: "rejectSubRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			console.log('data show',data)
			if(data.save != undefined && (data.save == true || data.save === 'true')){
				showMessage('success', 'Approved Successfull !!');
				window.location.reload();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function showStock(subRequisitionId,transactionType,name){
	showLayer();
	var jsonObject = new Object() ;
	jsonObject['subRequisitionId'] =subRequisitionId;
	jsonObject['getSameLocation'] =true;

	$.ajax({
		url: "getTransactionStock",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			showStocktable(data,name)
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function showStocktable(data,partName){
	console.log("Error : ", data);
	
	document.getElementById('stockModal').style.display='block';
	$('#partNameId').text(': '+partName);
	
	$('#stockTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th6 =	$('<th>');
	var th7 =	$('<th>');
	var th8 =	$('<th>');
	var totalQuantity = 0;
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Stock"));
	tr1.append(th6.append("Asile"));
	tr1.append(th7.append("Row"));
	tr1.append(th8.append("Bin"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	var tFoot = $('<tfoot>');
	if(data.locationWisePartQuantity != undefined  && data.locationWisePartQuantity.length > 0) {
		var list= data.locationWisePartQuantity;
		var tr2		= $('<tr class="ng-scope">');
		var td4		= $('<td colspan ="2">');
		var td5		= $('<td>');
	for(var i = 0; i < list.length; i++) {
		var tr1		= $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td6	    = $('<td>');
		var td7		= $('<td>');
		var td8	= $('<td>');
		tr1.append(td1.append(i+1));
		tr1.append(td2.append(list[i].location))
		tr1.append(td3.append(list[i].quantity))
		tr1.append(td6.append(list[i].aisle))
		tr1.append(td7.append(list[i].row))
		tr1.append(td8.append(list[i].bin))
		tbody.append(tr1);
		totalQuantity+=list[i].quantity;
	}

	tr2.append(td4.append("Total"));
	tr2.append(td5.append(totalQuantity));
	tFoot.append(tr2);
	}else{
		//showMessage('info','No record found !')
		document.getElementById('stockModal').style.display='none';
	}
	$('#stockTable').append(thead);
	$('#stockTable').append(tbody);
	$('#stockTable').append(tFoot);
}

function showApprovalModal(...arg){

	$('#subRequisitionIdStock').val(arg[0]);
	let reqType = arg[1];
	let transactionName ='';
	if(reqType == 1 || reqType == 4 || reqType == 5){
		if(arg[3] != undefined)
		transactionName=arg[3];
	}else if(reqType == 2 || reqType == BATTARY_RERUISITION ){
		if(reqType == BATTARY_RERUISITION){
			transactionName=arg[5];
		}else{
			transactionName=arg[4];
		}
	}
	$('#approvalDetailsSamp').text(' '+transactionName)
	$('#totalQuantitySamp').text(arg[2])
	$('#balanceQuantitySamp').text(0);
	$('#approvalModal').show();
	$('#transferType').select2();
	prepareSelect3('');
	emptyApprovalModal();
	$('#quantity').attr('placeholder','Should be less than '+arg[2]);
	hideBranch('transferType');
}

function hideBranch(id){
	let rerSplit = id.split('_');
	let d ="";
	if(rerSplit.length > 1){
		d ='_'+rerSplit[1];
	}
	if($('#'+id).val() == 1){
		$('#locationDiv'+d).removeClass('hide');
		$('#locationDivId'+d).removeClass('hide');
	}else{
		$('#locationDiv'+d).addClass('hide');
		$('#locationDivId'+d).addClass('hide');
		$('#requisitionlocation'+d).select2('data',{id:0,text:''});
		$('#stockQuantity'+d).text('');
	}
}

function saveApprovalDetails(){
	showLayer();
	let jsonObject 	= new Object();
	let dataArray = new Array();
	
	jsonObject['subRequisitionId'] 		= $('#subRequisitionIdStock').val();
	jsonObject['approvedQuantity'] 		=  calculateTotalQuantity();
	
	let validateReturn = false;
	$('select[name=transferType]').each(function(){
		validateReturn =validateApprovalDetails(this.id,$(this).val());
	});
	if(!validateReturn){
		hideLayer();
		return false;
	}
	
	$('select[name=transferType]').each(function(){
		dataArray.push(prepareValueObject(this.id,$(this).val()));
	})
	jsonObject.dataObject =JSON.stringify(dataArray); 
	$.ajax({
		url: "approveSubRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saved == true || data.saved === 'true' ){
				window.location.reload();
			}else if (data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
}

function validateApprovalDetails(id,thisVal){
	var validateReturn= true; 
	if(thisVal <= 0 || thisVal == ''){
		console.log('shoul');
		hideLayer();
		$('#'+id).addClass('showError');
		$('#s2id_'+id).addClass('showError');
		validateReturn = false;
	}
	let splitId = (id).split('_');
	let finalId ='';
	if(splitId.length > 1 && splitId[1] != undefined){
		finalId ='_'+splitId[1];
	}
	if(thisVal == 1){
		if($('#requisitionlocation'+finalId).val() == null || $('#requisitionlocation'+finalId).val() == '' || $('#requisitionlocation'+finalId).val() <= 0){
			$('#s2id_requisitionlocation'+finalId).addClass('showError');
			$('#requisitionlocation'+finalId).addClass('showError');
			validateReturn = false;
			hideLayer();
		}
	}
	
	if($('#quantity'+finalId).val() ==''|| $('#quantity'+finalId).val() <= 0){
		$('#quantity'+finalId).addClass('showError');
		validateReturn = false;
		hideLayer();
	}
	
	if($('#assignToApp'+finalId).val() == null || $('#assignToApp'+finalId).val() == '' || $('#assignToApp'+finalId).val() <= 0){
		$('#s2id_assignToApp'+finalId).addClass('showError');
		$('#assignToApp'+finalId).addClass('showError');
		validateReturn = false;
		hideLayer();
	}
	
//	if($('#appRemark'+finalId).val() ==''|| $('#appRemark'+finalId).val() <= 0){
//		$('#appRemark'+finalId).addClass('showError');
//		validateReturn = false;
//		hideLayer();
//	}
	if(!setBalanceStock('quantity'+finalId)){
		validateReturn = false;
		hideLayer();
	}
	$('.showError').on('click',function(){
		$(this).removeClass('showError');
	});
	$('.showError').on('change',function(){
		$(this).removeClass('showError');
		var removeCls =$(this).prev();
		removeCls.removeClass('showError');
	});
	
	if(!validateReturn){
		showMessage('info','Please Fill highlighted Details to process !!');
	}
	return validateReturn;
}

function prepareValueObject(id,value){
	var splitId = id.split('_');
	let finalId ='';
	var object = new Object();
	if(splitId.length > 1 && splitId[1] != undefined){
		finalId ='_'+splitId[1];
	}
	object['transferType'] =value;
	if(value == 1){
		object['branch'] = $('#requisitionlocation'+finalId).val();	
	}
	object['quantity'] = $('#quantity'+finalId).val();
	object['assignedTo'] = $('#assignToApp'+finalId).val();
	object['remark'] = $('#appRemark'+finalId).val();
	return object;
}
function setBalanceStock(id){
	let valNum=Number($('#'+id).val());
	let totalQty = Number($('#totalQuantitySamp').text());
	if(valNum>totalQty){
		showMessage('info', 'Quantity Can not be greater than Total Quantity !');
		$('#'+id).val('0');	
		var temp=calculateTotalQuantity();
		$('#balanceQuantitySamp').text(temp);	
		return false;
	}else{
		var temp=calculateTotalQuantity();
		if(temp > totalQty){
			showMessage('info', 'Total Quantity Can not be greater than ! '+totalQty);
			$('#'+id).val('0');
			let temp2 =calculateTotalQuantity();
			$('#balanceQuantitySamp').text(temp2);
			return false;
		}else{
			$('#balanceQuantitySamp').text(temp);
		}
	}
	if(validateLocationWiseStock())
	return false;
	
	return true;
}

function validateLocationWiseStock(){
	let valReturn = false;
	$('select[name=transferType]').each(function(){
		let thisVal = $(this).val();
		if(thisVal == 1){
			let splitId = (this.id).split('_');
			let finalId ='';
			if(splitId.length > 1 && splitId[1] != undefined){
				finalId ='_'+splitId[1];
			}
			let quantity = Number($('#quantity'+finalId).val());
			let availableStock =Number($('#availableStock'+finalId).val());
			if(quantity > availableStock){
				$('#quantity'+finalId).val('0');
				valReturn = true;
			}
		}
	})
	if(valReturn){
		showMessage('info', 'Quantity Can not be greater than Location Stock ! ');
		$('#balanceQuantitySamp').text(calculateTotalQuantity());	
	}
	return valReturn;
}

function calculateTotalQuantity(){
	let totalQuantity = 0;
	//argumnet object is used 
	if(arguments[0] == true){
		$('input[name=quantityId]').each(function(){
			if($(this).val() >0)
				totalQuantity+= Number($(this).val());
		})
	}else{
		$('input[name=quantity]').each(function(){
			if($(this).val() >0)
				totalQuantity+= Number($(this).val());
		})
	}
	
	return totalQuantity;
}
function getApprovalDetails(id){
	$('#showApprovalModal').show();
	
	emptyApprovalModal(true);
	
	let jsonObject = new Object();
	
	jsonObject["subRequisitionId"] = id;
	
	$.ajax({
		url: "getApprovalDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setApprovedDetails(data,id);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}


function setApprovedDetails(data,id){
	var subData = data.SubRequisition;
	if(subData != undefined || subData != null){
		$('#totalQuantitySampApp').text(subData.quantity);
		$('#balanceQuantitySampApp').text(subData.approvedQuantity);
		$('#balanceQuantityApp').val(subData.approvedQuantity);
		if(subData.quantity<=subData.approvedQuantity){
			$('#collapseButton').hide();
		}else{
			$('#collapseButton').show();
		}
	}
	$('#subRequisitionIdStock').val(id);
	
	$('#approvedTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	var th6 =	$('<th>');
	var th7 =	$('<th>');
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Approval Type"));
	tr1.append(th3.append("Approved Quantity"));
	tr1.append(th4.append("From Branch"));
	tr1.append(th5.append("Assignee"));
	tr1.append(th6.append("Remark"));
	tr1.append(th7.append("Action"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.approvalList != undefined  && data.approvalList.length > 0) {
		var list= data.approvalList;
	for(var i = 0; i < list.length; i++) {
		var tr1		= $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		tr1.append(td1.append(i+1));
		tr1.append(td2.append(list[i].approvedTypeName))
		tr1.append(td3.append(list[i].approvedQuantity))
		tr1.append(td4.append(list[i].branchName))
		tr1.append(td5.append(list[i].userName))
		tr1.append(td6.append(list[i].remark))
		var	actionLink ='<a href="javascript:void(0)" onclick=deleteApproval('+list[i].approvedRequisitionId+')><em class="fa fa-trash" style="color:red"> Delete</em></a>';
		tr1.append(td7.append(actionLink));
		tbody.append(tr1);
	}

//	tr2.append(td4.append("Total"));
//	tFoot.append(tr2);
	}else{
		showMessage('info','No record found !')
		document.getElementById('showApprovalModal').style.display='none';
		window.location.reload();
	}
	$('#approvedTable').append(thead);
	$('#approvedTable').append(tbody);
}

function prepareTableHead(){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	var th6 =	$('<th>');
	var th7 =	$('<th>');
	var th8 =	$('<th>');
	var th9 =	$('<th>');
	var th10 =	$('<th>');
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Approval Type"));
	tr1.append(th4.append("From Branch/PO"));
//	if(fromUrea && $('#requisitionStatusId').val() >= REQUISITION_APPROVED)
//	tr1.append(th11.append("Manufacturer"));
	tr1.append(th3.append("Approved Qnty"));
	
	if( $('#requisitionStatusId').val() >= REQUISITION_APPROVED){
		tr1.append(th7.append("Transfer Qnty"));
		tr1.append(th8.append("Received Qnty"));
	}
	if($('#requisitionStatusId').val()==REQUISITION_MARKED_COMPLETE){
		tr1.append(th10.append("Returned Qnty"));
	}
	tr1.append(th5.append("Sender"));
	if($('#requisitionStatusId').val() >= REQUISITION_APPROVED){
	tr1.append(th9.append("Receiver"));
	tr1.append(th6.append("Action"));
	}
	thead.append(tr1);
	
	return thead;
}

function prepareTableBody(approvalList,subRequisitionId,subReqType){

	var tbody = $('<tbody>');
	if(approvalList != undefined  && approvalList.length > 0) {
		var list= approvalList;
		for(var i = 0; i < list.length; i++) {
			var tr1		= $('<tr class="ng-scope">');
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10		= $('<td>');
			tr1.append(td1.append(i+1));
			tr1.append(td2.append(list[i].approvedTypeName))
			if(list[i].approvedTypeId == TRANSFER_PO){
				var polink ="-";
				if(list[i].poNumber != null && list[i].poNumber != undefined)
					polink= "<a style='color:blue;' href='PurchaseOrders_Parts.in?ID="+list[i].poId+"&subRequisitionId="+list[i].subRequisitionId+"&fromTransfer="+1+"&approvalQuantity="+list[i].approvedQuantity+"'>PO- "+list[i].poNumber+"</a>";
					tr1.append(td4.append(polink)); 
				}else{
					tr1.append(td4.append(list[i].branchName));
				}
			
/*			if(subReqType == UREA_RERUISITION && $('#requisitionStatusId').val() >= REQUISITION_APPROVED){
				if(list[i].manufacturerId != null && list[i].manufacturerId > 0){
					tr1.append(td11.append(list[i].manufacturerName));
				}else{
					tr1.append(td11.append("--"));
				}
			} 
*/

			totalTransferedQuantity	+= list[i].transferedQuantity;
			
			tr1.append(td3.append(list[i].approvedQuantity))
			if(list[i].approvedTypeId == TRANSFER_BRANCH){
			if($('#requisitionStatusId').val() >= REQUISITION_APPROVED){
				tr1.append(td7.append(list[i].transferedQuantity));
				tr1.append(td8.append(list[i].receivedQuantity));
			}
			}
			if($('#requisitionStatusId').val()==REQUISITION_MARKED_COMPLETE){
				tr1.append(td10.append(list[i].returnedQuantity));
			}
			tr1.append(td5.append('<a href="javascript:void(0)" data-toggle="tooltip" data-placement="top" title="'+list[i].remark+'">'+list[i].userName))
			console.log(" list[i].approvedStatus  ",list[i].approvedStatus );
			if($('#requisitionStatusId').val() >= REQUISITION_APPROVED){
				if(list[i].approvedTypeId == TRANSFER_BRANCH)
				tr1.append(td9.append(list[i].receiverName))
				var receiverName =('\''+list[i].receiverName+'\'').split(' ').join('-');
				var actionLink ='';
				if(list[i].approvedStatus == APPROVAL_STATUS_TRANSFER_REJECTED){
					actionLink = '<a href="javascript:void(0)" class="fa fa-ban" aria-hidden="true" style="color:red;"  data-toggle="tooltip" data-placement="top" title="'+list[i].rejectRemark+'"> Rejected </a>';
				}else if (list[i].approvedStatus == APPROVAL_STATUS_TRANSFER_COMPLETE){
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || (list[i].receiverId == Number($('#userId').val()))){
						actionLink ='<a class="fa fa-get-pocket btn btn-success"  onclick=receiveTransfer('+list[i].approvedRequisitionId+','+list[i].transferedQuantity+','+subRequisitionId+','+true+','+undefined+','+undefined+',\''+list[i].manufacturerName+'\')></a>&ensp; <a class="fa fa-ban btn btn-danger" onclick=rejectReceive('+list[i].approvedRequisitionId+')></a>';
					}else{
						actionLink +="unauthrised !";
					}
				}else if(list[i].approvedStatus == APPROVAL_STATUS_TRANSFER_RECEIVED){
					actionLink = '<a href="javascript:void(0)" class="fa fa-check" style="color : green;" data-toggle="tooltip" data-placement="top" title="'+list[i].finalRemark+'" aria-hidden="true"> Received </a>';
				}else if(list[i].approvedStatus == APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED){
					actionLink = '<a href="javascript:void(0)" class="fa fa-ban" aria-hidden="true" style="color:red;"  data-toggle="tooltip" data-placement="top" title="'+list[i].rejectRemark+'"> Receival Rejected </a>';
				}else if(list[i].approvedStatus == APPROVAL_STATUS_PO_CREATED){
					actionLink = '<i class="fa fa-check" style="color : green;" aria-hidden="true"> Po Created </i>';
				}else if(list[i].approvedStatus == APPROVAL_STATUS_PARTIALLY_RECEIVED){
					let booleanVal = false;
					if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || (list[i].receiverId == Number($('#userId').val()))){
						booleanVal=true;
					}
					if ((($('#allPermission').val() == true || $('#allPermission').val() === 'true') || list[i].assignToId == Number($('#userId').val())) && list[i].approvedQuantity > list[i].transferedQuantity) {
						actionLink = '<a class="fa fa-exchange btn btn-light " style="color : blue;" onclick=transferProcessed(' + list[i].approvedRequisitionId + ',' + list[i].approvedQuantity + ',' + list[i].transferedQuantity + ',' + list[i].branchId + ',' + subRequisitionId + ',\''+ list[i].branchName + '\','+list[i].receiverId+','+receiverName+')></a>';
					}
					actionLink += '<a href="javascript:void(0)" style="color : blue;" aria-hidden="true" onclick=receiveTransfer('+list[i].approvedRequisitionId+','+list[i].transferedQuantity+','+subRequisitionId+','+booleanVal+','+true+','+list[i].receivedQuantity+',\''+list[i].manufacturerName+'\')><em class="fa fa-spinner fa-pulse fa-fw"></em> Partially Received </a>';
				}else if(list[i].approvedStatus ==	APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE){
					actionLink = '<a href="javascript:void(0)" style="color : green;" aria-hidden="true" onclick=receiveTransfer('+list[i].approvedRequisitionId+','+list[i].transferedQuantity+','+subRequisitionId+','+false+','+true+','+list[i].receivedQuantity+',\''+list[i].manufacturerName+'\')><em class="fa fa-check"></em> Partially Received Complete</a>';
				}
				else if (list[i].approvedStatus == APPROVAL_STATUS_PARTIALLY_TRANSFERED) {
					if ((($('#allPermission').val() == true || $('#allPermission').val() === 'true') || list[i].assignToId == Number($('#userId').val())) && list[i].approvedQuantity > list[i].transferedQuantity) {
						actionLink = '<a class="fa fa-exchange btn btn-light " style="color : blue;" onclick=transferProcessed(' + list[i].approvedRequisitionId + ',' + list[i].approvedQuantity + ',' + list[i].transferedQuantity + ',' + list[i].branchId + ',' + subRequisitionId + ',\'' + list[i].branchName + '\','+list[i].receiverId+','+receiverName+')></a>';
					}
					if (($('#allPermission').val() == true || $('#allPermission').val() === 'true') || (list[i].receiverId == Number($('#userId').val()))) {
						actionLink += '<a class="fa fa-get-pocket btn btn-success"  onclick=receiveTransfer(' + list[i].approvedRequisitionId + ',' + list[i].transferedQuantity + ',' + subRequisitionId + ',' + true + ',' + undefined + ',' + undefined + ',\'' + list[i].manufacturerName + '\')></a>&ensp; <a class="fa fa-ban btn btn-danger" onclick=rejectReceive(' + list[i].approvedRequisitionId + ')></a>';
					}
				}
				else if(list[i].approvedStatus ==	APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED){
					actionLink = '<a href="javascript:void(0)" style="color : green;" aria-hidden="true" onclick=receiveTransfer('+list[i].approvedRequisitionId+','+list[i].transferedQuantity+','+subRequisitionId+','+false+','+true+','+list[i].receivedQuantity+',\''+list[i].manufacturerName+'\')> Partially Received & Closed</a>';
				}else{
					if(list[i].approvedTypeId == TRANSFER_BRANCH ){
						if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || (list[i].assignToId == Number($('#userId').val()))&& list[i].approvedQuantity > list[i].transferedQuantity)  {
							actionLink ='<a class="fa fa-exchange btn btn-light " style="color : blue;" onclick=transferProcessed('+list[i].approvedRequisitionId+','+list[i].approvedQuantity+','+list[i].transferedQuantity+','+list[i].branchId+','+subRequisitionId+',\''+list[i].branchName+'\','+list[i].receiverId+','+receiverName+')></a>';
							actionLink +='  &ensp; <a class="fa fa-ban btn btn-danger" onclick=rejectApproval('+list[i].approvedRequisitionId+')></a>'
						}else{
							actionLink +="unauthrised !";
						}
					}else if(list[i].approvedTypeId == TRANSFER_PO ){
						if(($('#allPermission').val() == true || $('#allPermission').val() === 'true') || (list[i].assignToId == Number($('#userId').val()))){
						actionLink ='<a class="fa fa-plus" href="javascript:void(0)" onclick=createPO('+list[i].approvedRequisitionId+','+list[i].approvedQuantity+','+subReqType+')> Create PO</a>';
						}else{
							actionLink +="unauthrised !";
						}
					}
				}
				tr1.append(td6.append(actionLink));
			}
			tbody.append(tr1);
		}
	}
	return tbody;
}

function prepareSelectFields(){
	prepareSelect3('');
}
function validateQuantitySingle(id){
	$('#'+id).val();
	let mainQunatity = Number($('#totalQuantitySampApp').text());
	let totalQuantity = 0;
	let availableStock = Number($('#availableStockId').val());
	if($('#transferTypeId').val() == 1){
		if(availableStock < $('#'+id).val()){
			$('#'+id).val('0');
			showMessage('info', 'Quantity Can not be greater than Location Stock ! ');
			$('#balanceQuantitySampApp').text(calculateTotalQuantity(true));
			return false;
		}
	}
	$('input[name=quantityId]').each(function(){
		if($(this).val() >0)
			totalQuantity+= Number($(this).val());
	})
	if(totalQuantity > mainQunatity){
		$('#'+id).val('0');
		showMessage('info', 'Quantity Can not be greater than Total Quantity ! ');
		$('#balanceQuantitySampApp').text(calculateTotalQuantity(true));
		return false;
	}
	$('#balanceQuantitySampApp').text(calculateTotalQuantity(true));
	return true;
}

function saveSingleApprovalDetails(){
	showLayer();
	if(!validateQuantitySingle('quantityId')){
		hideLayer();
	return false;	
	}
	let jsonObject = new Object();
	let subRequisitionId = $('#subRequisitionIdStock').val();
	jsonObject['subRequisitionId'] =subRequisitionId;
	jsonObject['approvedQuantity'] = calculateTotalQuantity(true);
	
	var validate = true;
	var value = $('#transferTypeId').val();
	
	 if(value <=0 || value == ''){
		 $('#transferTypeId').addClass('showError');
		 $('#s2id_transferTypeId').addClass('showError');
			validate = false; 
	 }
	if(value == 1){
		let branchId= $('#requisitionlocationId').val();
		if(branchId <= 0){
			$('#s2id_requisitionlocationId').addClass('showError');
			$('#requisitionlocationId').addClass('showError');
			validate = false;
		}
		jsonObject['branch'] = branchId;	
	}
	if($('#quantityId').val() <= 0){
		$('#quantityId').addClass('showError');
		validate = false;
	}
	 var assignee = $('#assignToSingle').val();
	 if(assignee <=0){
		 $('#assignToSingle').addClass('showError');
			$('#s2id_assignToSingle').addClass('showError');
		 validate = false; 
	 }
	 var remark = $('#remarkSingle').val();
//	 if(remark.trim() == ''){
//		 $('#remarkSingle').addClass('showError');
//		 validate = false;  
//	 }
	 if(!validate){
		 showMessage('info', 'Please Fill highlighted Fields To process !!');
			$('.showError').on('click',function(){
				$(this).removeClass('showError');
			});
			$('.showError').on('change',function(){
				var removeCls =$(this).prev();
				$(this).removeClass('showError');
				removeCls.removeClass('showError');
			});
			hideLayer();
		 return false;
	 }
	 jsonObject['transferType'] =value;
	 jsonObject['quantity'] 	= $('#quantityId').val();
	 jsonObject['assignedTo'] 	= assignee;
	 jsonObject['remark']	 	= remark;
	
	$.ajax({
		url: "addToapproveRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saved == true || data.saved === 'true' ){
				showMessage('Success', 'Approval Added Succesfully !!!');
				$('#requisitionlocationId').select2('val','');
				$('#transferTypeId').select2('val','');
				$('#stockQuantityId').text('');
				$('#collapseDiv').collapse('toggle');
				getApprovalDetails(subRequisitionId);
			}else if (data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
}



function deleteApproval(id){
	showLayer();
	var jsonObject = new Object();
	jsonObject['approvalId'] =id;
	jsonObject['companyId'] =$('#companyId').val();
	$.ajax({
		url: "deleteApprovalById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.success == true || data.success === 'true' ){
				showMessage('success', 'Approval Deleted Successsfully !!! ');
				getApprovalDetails($('#subRequisitionIdStock').val());
			}else if (data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
}

function emptyApprovalModal(){
	if(arguments[0] == true || arguments[0] == 'true' ){
		$('select[name=transferTypeId]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
			$(this).trigger('change');
		});
		$('input[name=requisitionlocationId]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
		$(this).trigger('change');
		});
		$('input[name=assignToSingle]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
		});
		$('input[name=quantityId]').each(function(){
		$(this).val('');
		});
		$('input[name=remarkSingle]').each(function(){
			$(this).val('');
		});

	}else{
		$('select[name=transferType]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
			$(this).trigger('change');
		});
		$('input[name=requisitionlocation]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
			$(this).trigger('change');
		});
		$('input[name=assignToApp]').each(function(){
			$(this).select2('data',{id : 0,text : ''});
		});
		$('input[name=quantity]').each(function(){
			$(this).val('');
		});
		$('input[name=appRemark]').each(function(){
			$(this).val('');
		});
	}
}

function finalApproval(){
	if(confirm("Once final approved you will not be able to alter approvals , Sure you want to continue ?")){

	showLayer();
	var jsonObject = new Object();
	jsonObject['requisitionId'] = $('#requisitionId').val();
	$.ajax({
		url : 'changeRequisitionStatus',
		type : 'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			if(data.status == true || data.status === 'true' ){
				showMessage('success', 'Approval  Successsfull !!! ');	
				hideLayer();
				window.location.reload();
			}else if(data.inCompleteDetails != undefined){
				showMessage('info', 'Please Complete Approval For '+data.inCompleteDetails+' Requision to Process !!');
				hideLayer();
			}
			hideLayer();
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');	
		}
		
	})
	
	}
}

function rejectApproval(approvalId){
	$('#rejectModal').show();
	$('#rejectRemark').val("");
	$('#reqIdForReject').val(approvalId);
	$('#rejectSubReqButtton').hide();
	$('#rejectReceiveButtton').hide();
	$('#rejectAppButtton').show();
}

function rejectApprovalWithRemark(){
	showLayer();
	var jsonObject = new Object();
	if($('#rejectRemark').val() == undefined || ($('#rejectRemark').val()).trim() == ''){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	jsonObject['approvalId']	= $('#reqIdForReject').val();
	jsonObject['rejectRemark'] 	= $('#rejectRemark').val();
	jsonObject['companyId'] 	= $('#companyId').val();
	
	$.ajax({
		url: "rejectApprovalById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.success == true || data.success === 'true' ){
				showMessage('success', 'Approval Rejected Successsfully !!! ');
				window.location.reload();
			}else if (data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			} 
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
	
}

function createPO(id,quantity,type){
	preparePo(type); // called function is in transferRequisition.js
	$('#approvalIdPo').val(id);
	$('#poApprovalQty').val(quantity);
}

function validateRepeatation(id){
	$('input[name=requisitionlocation]').each(function(){
		if(this.id != id && $(this).val() > 0 && $('#'+id).val()>0 && $(this).val() == $('#'+id).val()){
			$('#'+id).select2('data',{id : 0,text : ''});
			showMessage('info', 'Can not use same Branch !!');
			return;
		}
	})
}

function closeApprovalModal(){
	$('#showApprovalModal').hide();
	window.location.reload();
}





function getLocationWiseStock(id){
	if(arguments[1] != true)
	validateRepeatation(id);
	let locationId =$('#'+id).select2('data');
	//argument object is used to to retrieve second functional parameter value  
	let argumentsCheck = arguments[1];
	let rerSplit = id.split('_');
	let d ="";
	if(rerSplit.length > 1){
		d ='_'+rerSplit[1];
	}
	if(locationId != undefined && locationId.id >0){
		if(argumentsCheck== true || argumentsCheck === 'true'){
			$('#stockQuantityId').text('Available Stock : '+locationId.slug);
			$('#quantityId').val('0');
			$('#availableStockId').val(locationId.slug);
			$('#balanceQuantitySampApp').text(calculateTotalQuantity(true));
		}else{
			$('#stockQuantity'+d).text('Available Stock : '+locationId.slug);
			$('#availableStock'+d).val(locationId.slug);
			$('#quantity'+d).val('0');
			$('#balanceQuantitySamp').text(calculateTotalQuantity());
		}
	}else{
		if(argumentsCheck== true || argumentsCheck === 'true'){
			$('#stockQuantityId').text('');
			$('#quantityId').val('0');
			$('#availableStockId').val('0');
			$('#balanceQuantitySampApp').text(calculateTotalQuantity(true));
		}else{
			$('#stockQuantity'+d).text('');
			$('#availableStock'+d).val('0');
			$('#quantity'+d).val('0');
			$('#balanceQuantitySamp').text(calculateTotalQuantity());
		}
	}
}
function prepareTableHeadForPo(){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	var th6 =	$('<th>');
	var th7 =	$('<th>');
	var th8 =	$('<th>');
	var th9 =	$('<th>');
	var th10 =	$('<th>');
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Approval Type"));
	tr1.append(th4.append("PO No."));
	tr1.append(th3.append("Approved Qnty"));
	if($('#requisitionStatusId').val()==REQUISITION_MARKED_COMPLETE){
		tr1.append(th10.append("Returned Qnty"));
	}
	tr1.append(th5.append("Assign To "));
	if($('#requisitionStatusId').val() >= REQUISITION_APPROVED){
	tr1.append(th6.append("Action"));
	}
	thead.append(tr1);
	return thead;
}

function printPartPurchargeOrder(requisitionId) {
	window.open('showPartRequisitionPrint?requisitionId=' + requisitionId, 'window', "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
}