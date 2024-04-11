<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<style>
body {
    font-family: Roboto,"Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 12px;
    line-height: 1.8;
    color: #394347;
    
}
.key{
	width:100%;
	text-align:right;
}


.noBackGround{
	background: none;
}

.select2-container {
	width: 80%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 50px;
}
tr:nth-child(even) {
  background-color: #f2f2f2;
}
th{
 text-align:right;
font-size: 13px;
}

td
{
font-size: 13px;
 overflow: hidden;
 text-overflow: ellipsis;
 white-space: nowrap;
 padding-left: 5px;

}
.bgWhite {
	background:white;
}

</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					 <a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> /
					<a href="<c:url value="/dealerServiceEntries.in"/>">DSE</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
						<div class="input-group">
							 <div class="input-group-prepend">
							    <span class="input-group-text btn-success" style="color:white" id="basic-addon1">DSE-</span>
							  </div>
							  <input type="text" class="form-control"id="searchByNumber" name="Search" type="number" min="1" required="required" placeholder="ID eg: 2323" aria-describedby="basic-addon1">
							<button type="submit" onclick="return searchDSEByNumber();" class="btn btn-success btn-sm"><em class="fa fa-search"></em></button>
						</div>
					</div>
					<sec:authorize access="hasAuthority('ADD_DEALER_SERVICE_ENTRIES')">
						<button class="btn btn-primary" id="downloadDocumentButton" onclick="downloadDocument();"><em class="fa fa-download"></em></button>
						<button class="btn btn-primary " id="uploadDocumentButton" onclick="uploadDocument();"><em class="fa fa-upload"></em></button>
						<a href="printDealerServiceEntries?dealerServiceEntriesId=${dealerServiceEntriesId}" target="_blank" class="btn btn-warning btn-md "><em class="fa fa-print"></em></a>
						<button type="button" data-toggle="modal" data-target="#addPartDetails" id="addPartButtonOnTop" data-whatever="@mdo" class="btn btn-info hide" style="background:lightseagreen;" ><span class="fa fa-plus"></span> Add Part</button>
						<button type="button" data-toggle="modal" data-target="#addLabourDetails" id="addLabourButtonOnTop" data-whatever="@mdo" class="btn btn-info hide" style="background:salmon;" ><span class="fa fa-plus"></span> Add Labour</button>
						<button type="button" data-toggle="modal" data-target="#extraIssueModal"  id="addExtraIssueButton" data-whatever="@mdo" class="btn btn-info" >Extra Issue</button>
<!-- 						<button type="button" data-toggle="modal" data-target="#extraIssueModal"  id="addExtraIssueButton" data-whatever="@mdo" class="btn btn-info" >Edit</button> -->
						<a href="editDealerServiceEntries?dealerServiceEntriesId=${dealerServiceEntriesId}" target="_blank" id="editButton" class="btn btn-info">Edit</a>
						<a id="tyreAssignButtonOnTop" onclick="mountTyre();" class="btn btn-warning btn-md hide"><em class="fa fa-life-buoy"></em></a>
					</sec:authorize>
					<button type="button" id="completeDSEId" class="btn btn-success float-right border border-success" onclick="return completeDSE();"> Complete </button>
					<sec:authorize access="hasAuthority('REOPEN_DSE')">
						<button type="button" id="reOpenDse" class="btn btn-warning float-right border border-warning" onclick="return reopenDSE();"> Reopen </button>
					</sec:authorize>
					<div class="form-check form-switch float-right" id="labourNotCheck" style="padding-top:10px;padding-right:15px;">	
					  <input class="form-check-input" type="checkbox" id="labourNotApplicableId" />
					  <label class="form-check-label" id ="labourApplicableLabel" for="labourApplicableId" style="color: #2e74e6; font-size: 13px; wdith: 20%;">Labour Not Applicable</label>
					  </div>
					<div class="form-check form-switch float-right" style="padding-top:10px;padding-right:15px;">
					  <input class="form-check-input" type="checkbox" id="partNotApplicableId" />
					  <label class="form-check-label" id ="partApplicableLabel" for="partApplicableId" style="color: #2e74e6; font-size: 13px; wdith: 20%;">Part Not Applicable</label>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row" style="line-height: 0.4;">
			<div class="box border border-primary">
				<sec:authorize access="!hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
					<input type="hidden" id="companyId" value="${companyId}">	
					<input type="hidden" id="userId" value="${userId}">	
					<input type="hidden" id="dealerServiceEntriesId" value="${dealerServiceEntriesId}">
						<input type="hidden" id="moduleId" value="${dealerServiceEntriesId}"/> 
						<input type="hidden" id="modulePaymentTypeId"/> 
						<input type="hidden" id="moduleIdentifier"/>
						
					<input type="hidden" id="paymentTypeId" >	
					<input type="hidden" id="showVendorId" >	
					<input type="hidden" id="vid" >	
					<input type="hidden" id="dealerServiceDocumentId" >	
					<input type="hidden" id="invoiceCost" >	
					<input type="hidden" id="invoiceNumberVal" >	
					<input type="hidden" id="invoiceDateVal" >	
					<input type="hidden" id="vehicleOdometerVal" >	
					<input type="hidden" id="serviceReminderIds" >	
					<input type="hidden" id="issueId" >	
					<input type="hidden" id="invoiceDateVal" >	
					<input type="hidden" id="lastOccurredDealerServiceEntriesId" >	
					<input type="hidden" id="remarkVal" >	
					<input type="hidden" id="statusId" >	
					<input type="hidden" id="finalLabourDiscountTaxTypId" >	
					<input type="hidden" id="finalPartDiscountTaxTypId" >	
					<input type="hidden" id="editDealerServiceLabour" value="${editDealerServiceLabour}">
					<input type="hidden" id="editDealerServicePart" value="${editDealerServicePart}">
					<input type="hidden" id="editDealerServiceEntriesPartId" >	
					<input type="hidden" id="editDealerServiceEntriesLabourId" >	
					<input type="hidden" id="partWarrantyConfig" value="${configuration.partWarranty}">	
					<input type="hidden" id="validatePartWarranty" value="false">	
					<input type="hidden" id="showDriverId" >	
					<input type="hidden" id="showDriverName" >	
					<input type="hidden" id="showAssignToId" >	
					<input type="hidden" id="showAssignTo" >	
					<input type="hidden" id="serviceReminderId" class="hide" >
					<input type="hidden" id="tyreAssginFromDSEConfig" value="${configuration.tyreAssginFromDSE}">	
					 
					<div class="box-body">
						<div class="row">
							<div class="col-md-2" class="float-left">
							<h5><a id="dealerSENumber"></a></h5>
							</div>
							<div class="col-md-10">
								<button type="button" class="btn btn-success float-right" id="inProcess" onclick="changeDseStatus();">Change To Process</button>
								<button type="button" style="color: white;" class="btn btn-warning float-right" id="onHold" onclick="changeDseStatus();">Change To Hold</button>
							</div>
						</div>
						<br>
						<div class="row ">
						
							<div class="col-md-6">
								<table style="width: 100%; line-height: 2.2;">
									<tbody>
										<tr >
											<th class="bgWhite">Vendor Name: </th>
											<td class="bgWhite"> <span id="vendorName"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Vendor Add: </th>
											<td class="bgWhite"> <label style="line-height: 1.4;padding-top: 25px;"	 id="vendorAddress"></label></td>
										</tr>
										<tr >
											<th class="bgWhite">Payment Type:</th>
											<td class="bgWhite"> <span id="paymentTypeSpan"></span></td>
										</tr>
										<tr id="paidDateRow" >
											<th class="bgWhite">Paid Date:</th>
											<td class="bgWhite"> <span id="paidDate"></span></td>
										</tr>
										<tr>
											<th class="bgWhite">DSE Status: </th>
											<td class="bgWhite"> <span id="status"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Invoice Number:</th>
											<td class="bgWhite"> <span id="invoiceNumber"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Invoice Date: </th>
											<td class="bgWhite"> <span id="invoiceDate"></span></td>
										</tr>
										<tr >
											<th class="bgWhite"	>Invoice Amount:</th>
											<td class="bgWhite"> <span id="invoiceAmount"></span></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="col-md-2">
							</div>
							<div class="col-md-4">
								<table  style="width: 100%; line-height: 2.2;">
									<tbody>
										<tr>
											<th class="bgWhite">Vehicle Number:</th>
											<td class="bgWhite"> <span id="vehicleNumber"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Vehicle Odometer: </th>
											<td class="bgWhite"> <span id="vehicleOdometer"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Vehicle Chasis No:</th>
											<td class="bgWhite"> <span id="vehicleChasisNumber"></span></td>
										</tr>
										<tr >
											<th class="bgWhite">Vehicle Engine No: </th>
											<td class="bgWhite"> <span id="vehicleEngineNumber"></span></td>
											
										</tr>
										<tr id="issueRow">
											<th class="bgWhite">Issue No: </th>
											<td class="bgWhite"> <span id="issueNumber"></span></td>
											
										</tr>
										<tr >
											<th class="bgWhite">Driver: </th>
											<td class="bgWhite"> <span id="driverFullName"></span></td>
											
										</tr>
										<tr id="partApplicableStatusRow" >
											<th class="bgWhite">Part Status: </th>
											<td class="bgWhite"> <span id="dsePartStatus"></span></td>
										</tr>
										<tr id="labourApplicableStatusRow" >
											<th class="bgWhite">Labour Status: </th>
											<td class="bgWhite"> <span id="dseLabourStatus"></span></td>
										</tr>
										<tr id="assignToRow" >
											<th class="bgWhite">Assign To: </th>
											<td class="bgWhite"> <span id="assignTo"></span></td>
										</tr>
										 <sec:authorize access="hasAuthority('DSE_METER_NOT_WORKING')">
										<tr id="assignToRow" >
											<th class="bgWhite">Meter Status: </th>
											<td class="bgWhite"> <span id="meterNotWorking"></span></td>
										</tr>
										</sec:authorize>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
		<div class="row" id="serviceSchedules" style="display: none;" style="line-height: 0.2;">
			<div class="box border border-success">
				<div class="box-body">
					<div class="row">
					<button type="button"  id="reminderHeader" class="btn btn-info"  >Service Reminder</button>
					</div>
					<br>
					<div class="table-responsive" >
					
						<table class="table table-hover table-bordered" id="serviceSchedulesTable">
							<tr>
								<th>Service Number</th>
								<th>Service Schedule</th>
								<th>Due</th>
							</tr>
						</table>
						
						
					</div>
				</div>
			</div>
		</div>
		<c:if test="${configuration.tyreAssginFromDSE}">
			<div class="row" id="tyreAssignDiv" style="line-height: 0.4; display:none;">
				<div class="box border border-success">
					<div class="box-body">
						<div class="row">
						<button type="button"  id="addPartButton" class="btn btn-info" style="background:warning;" onclick="mountTyre();"><span class="fa fa-plus"></span> Tyre Assignment</button>
						</div>
						<br>
						<div class="table-responsive">
							<table class="table table-hover table-bordered">
							<thead>
								<tr>
									<th class="fit ar"  style='font-size: 15px;'>SR NO</th>
									<th class="fit ar" style='font-size: 15px;'>Tyre Position</th>
									<th class="fit ar" style='font-size: 15px;'>Tyre Number</th>
								</tr>
							</thead>
							<tbody id="tyreAssignTable">
							
							</tbody>
		
							</table>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row" id="partTable" style="line-height: 0.4;">
			<div class="box border border-success">
				<div class="box-body">
					<div class="row">
					<button type="button" data-toggle="modal" data-target="#addPartDetails" id="addPartButtonM" data-whatever="@mdo" class="btn btn-info" style="background:lightseagreen;" ><span class="fa fa-plus"></span> Add Part Details</button>
					<button type="button"  id="partHeader" class="btn btn-info" style="background:lightseagreen;" >Part Details</button>
					</div>
					<br>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar"  style='font-size: 15px;'>SR NO</th>
								<th class="fit ar" style='font-size: 15px;'>Part Name</th>
								<th class="fit ar" style='font-size: 15px;'>Quantity</th>
								<th class="fit ar" style='font-size: 15px;'>Each Cost</th>
								<th class="fit ar" style='font-size: 15px;'>Discount</th>
								<th class="fit ar" style='font-size: 15px;'>Tax</th>
								<th class="fit ar" style='font-size: 15px;'>Total Cost</th>
								<th class="fit ar"  style='font-size: 15px;' id="partAction">Action</th>
							</tr>
						</thead>
						<tbody id="dealerServiceEntriesPartTable">
						
						</tbody>
	
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="labourTable" style="line-height: 0.4;">
			<div class="box border border-warning" >
				<div class="box-body">
					<div class="row">
						<button type="button" data-toggle="modal" data-target="#addLabourDetails" id="addLabourButton" data-whatever="@mdo" class="btn btn-info" style="background:salmon;" ><span class="fa fa-plus"></span> Add Labour Details</button>
						<button type="button"  id="labourHeader" class="btn btn-info" style="background:salmon;" >Labour Details</button>
					</div>
					<br>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar" style='font-size: 15px;'>SR NO</th>
								<th class="fit ar" style='font-size: 15px;'>Labour Type</th>
								<th class="fit ar" style='font-size: 15px;'>Hour/KM</th>
								<th class="fit ar" style='font-size: 15px;'>Rate/(Hour/KM)</th>
								<th class="fit ar" style='font-size: 15px;'>Discount</th>
								<th class="fit ar" style='font-size: 15px;'>Tax</th>
								<th class="fit ar" style='font-size: 15px;'>Total Cost</th>
								<th class="fit ar" style='font-size: 15px;' id="labourAction">Action</th>
							</tr>
						</thead>
						<tbody id="dealerServiceEntriesLabourTable">
						
						</tbody>
	
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="summaryTable" style="line-height: 0.4;">
			<div class="box border border-success">
				<div class="box-body">
					<div class="row">
						<button type="button"  class="btn btn-info" style="background:green;" >Summary</button>
					</div>
					<br>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar"  style='font-size: 15px;'>Part Summary</th>
								<th class="fit ar" style='font-size: 15px;'>Labour Summary</th>
							</tr>
						</thead>
						<tbody >
							<tr>
							<td><table style="width: 100%;">
								<tbody>
									<tr >
										<th>Sub Amount: <em class="fa fa-inr"></em> <span id="partSubCost"></span></th>
									</tr>
									<tr >
										<th>Discount Amount: <em class="fa fa-inr"></em> <span id="partDiscountAmount"></span></th>
									</tr>
									<tr>
										<th>Taxable Amount: <em class="fa fa-inr"></em> <span id="partTaxableAmount"></span></th>
									</tr>
									<tr  style="background:green; color:white" >
										<th>Total Amount: <em class="fa fa-inr"></em> <span id="totalPartCost"></span></th>
									</tr>
								</tbody>
							</table></td>
							<td><table style="width: 100%;">
								<tbody>
									<tr >
										<th>Sub Amount: <em class="fa fa-inr"></em> <span id="labourSubCost"></span></th>
									</tr>
									<tr >
										<th>Discount Amount: <em class="fa fa-inr"></em> <span id="labourDiscountAmount"></span></th>
									</tr>
									<tr>
										<th>Taxable Amount: <em class="fa fa-inr"></em> <span id="labourTaxableAmount"></span></th>
									</tr>
									<tr  style="background:green;  color:white">
										<th>Total Amount: <em class="fa fa-inr"></em> <span id="totalLabourCost"></span></th>
									</tr>
								</tbody>
							</table></td>
							</tr>
						</tbody>
						</table>
					</div>
					<div class="row">
						<table style="width: 100%; ">
							<tbody>
								<tr>
									<th style="padding-right: 10%; font-size: 18px;"> Total Dealer Service Cost : <i class="fa fa-inr"></i> <span id="totalDealerServiceCost"></span></th>
								</tr>
								
							</tbody>
						</table>
					</div>
					<div class="row">
						<label><a data-toggle="collapse" href="#collapseExample1" role="label" aria-expanded="false" aria-controls="collapseExample1" title="Click here to see all Extra Issue" style="background-color: #00acd6;color: black;font-size: 18px;">Extra Issue : <span  id="dseExtraIssue"></span></a></label>
					</div>
					<br>
					<div class="row">
						<div class="collapse" id="collapseExample1">
						<ul id="extraIssueTimeLine" class="timeline">

						</ul>
						</div>
					</div>
					<div class="row">
						<label><a data-toggle="collapse" href="#collapseExample" role="label" aria-expanded="false" aria-controls="collapseExample" title="Click here to see all remarks" style="background-color: orange;color: black;font-size: 18px;">Remark : <span  id="remark"></span></a></label>
					</div>
					<br>
					<div class="row">
						<div class="collapse" id="collapseExample">
						<ul id="remarkTimeLine" class="timeline">

						</ul>
						</div>
					</div>

				</div>
			</div>
		</div>
		
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> |
			<small class="text-muted"><b>Created date: </b>  <span id="createdDate"> </span></small> |
			<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy"></span></small> |
			<small class="text-muted"><b>Last updated date:</b>  <span id="lastUpdatedDate"></span></small>
		</div>
	</section>
	<div class="modal" id="addLabourDetails" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Labour Details</h5>
				
					<!-- <div class="btn-group float-right" data-toggle="buttons" id ="labourDiscountTaxButton" role="group" aria-label="Basic radio toggle button group">
								  <label class="btn btn-md btn-info btnSelect active" id="labourPercentLabelId" onclick="selectDiscountTaxType(2,1);">
								    <input type="radio" class="btn-check"  name="labourDiscountTaxTypeId" id="labourPercentId" autocomplete="off" value="true"  onclick="selectDiscountTaxType(2,1);" checked > Percentage
								  </label>
								  <label class="btn btn-sm btn-info btnSelect"id="labourAmountLabelId"  onclick="selectDiscountTaxType(2,2);">
								    <input type="radio" class="btn-check" name="labourDiscountTaxTypeId" id="labourAmountId" value="false"  onclick="selectDiscountTaxType(2,2);" autocomplete="off"> Amount
								  </label>
								  <input type="hidden" id="finalLabourDiscountTaxTypId"  value="1">
							</div>
							 -->
							<div class="btn-group float-right" data-toggle="buttons" id ="labourDiscountTaxButton" role="group" aria-label="Basic radio toggle button group">
								  <label class="btn btn-sm btn-info btnSelect active" id="labourPercentLabelId" onclick="selectDiscountTaxType(2,1);">
								    <input type="hidden" class="btn-check"  name="labourDiscountTaxTypeId" id="labourPercentId" autocomplete="off" value="true"  onclick="selectDiscountTaxType(2,1);" checked > Percentage
								  </label>
								  <label class="btn btn-sm btn-info btnSelect"id="labourAmountLabelId"  onclick="selectDiscountTaxType(2,2);">
								    <input type="hidden" class="btn-check" name="labourDiscountTaxTypeId" id="labourAmountId" value="false"  onclick="selectDiscountTaxType(2,2);" autocomplete="off"> Amount
								  </label>
								  <input type="hidden" id="finalLabourDiscountTaxTypId"  value="1">
							</div>
							
							
							
				</div>
				<div class="modal-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<label> <span
									style="color: #2e74e6; font-size: 14px; wdith: 20%;">Labour
										Type</span>
								</label> <input type="hidden"  class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;"
								name="labourName" id="labourName" class="form-control">
								
							</div>
							<br> <br>
							<div class="row" >
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Hour/KM
									</label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Rate/(Hour/KM)
										Cost </label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Discount
									</label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">GST </label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Total
									</label>
								</div>
							</div>
							<br> <br>
							<div class="row" >
								<div class="col col-sm-1 col-md-2">
									<input type="text" class="form-control" placeholder="Qty"
										id="labourWorkingHours" maxlength="10" onpaste="return false" name="labourWorkingHours"
										onkeypress="return isNumberKeyWithDecimal(event,this.id)"
										onkeyup="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'labourTotalCost',2);"
										min="0.00">
								</div>
								<div class="col col-sm-1 col-md-2">
									<input type="text" class="form-control" placeholder="Unit Price" maxlength="8" name="labourPerHourCost"
										id="labourPerHourCost" maxlength="10" onpaste="return false"
										onkeypress="return isNumberKeyWithDecimal(event,this.id)"
										onkeyup="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'labourTotalCost',2);"
										min="0.00">
								</div>
								<div class="col col-sm-1 col-md-2">
									<div class="input-group">
										<input type="text" class="form-control allLabourDiscount" placeholder="Discount"
											onpaste="return false" id="labourDiscount" maxlength="5"
											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'labourTotalCost',2); "
											min="0.0"> <span class="input-group-addon"> <em class="fa fa-percent labourPercent"></em></span>
									</div>
								</div>
								<div class="col col-sm-1 col-md-2">
									<div class="input-group">
										<input type="text" class="form-control allLabourTax" placeholder="GST"
											onpaste="return false" id="labourTax" name="labourTax" maxlength="5"
											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'labourTotalCost',2); "
											min="0.0"> <span class="input-group-addon"> <em class="fa fa-percent labourPercent"></em></span>
									</div>
								</div>
								<div class="col col-sm-1 col-md-2">
									<input type="text" id="labourTotalCost" data-toggle="tip" name="totalLabourCost"
										data-original-title="Total cost" class="form-control allLabourTotalCost"
										required="required"  readonly="readonly">
								</div>
							</div>
							
							<div class="addMoreLabourDiv">
											
										</div>
							
						</div>
						<br>
						<div class="col col-sm-1 col-md-1">
								<button type="button" class="btn btn-info addMoreLabourButton">
									<span class="fa fa-plus">Add more</span>
								</button>
							</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="addLabourDetails();">Save Labour</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="addPartDetails" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Part Details</h5>
					<div class="btn-group float-right" data-toggle="buttons" id ="partDiscountTaxButton" role="group" aria-label="Basic radio toggle button group">
						  <label class="btn btn-sm btn-info btnSelect active" id="partPercentLabelId" onclick="selectDiscountTaxType(1,1);">
						    <input type="hidden" class="btn-check"  name="partDiscountTaxTypeId" id="partPercentId"  autocomplete="off" value="true"  onclick="selectDiscountTaxType(1,1);" checked > Percentage
						  </label>
						  <label class="btn btn-sm btn-info btnSelect" id="partAmountLabelId" onclick="selectDiscountTaxType(1,2);">
						    <input type="hidden" class="btn-check" name="partDiscountTaxTypeId" id="partAmountId"  value="false"  onclick="selectDiscountTaxType(1,2);" autocomplete="off"> Amount
						  </label>
					</div>	
					
				</div>
				<div class="modal-body">
					<div class="row">
						<label> <span
							style="color: #2e74e6; font-size: 14px; wdith: 20%;">Part
								Name</span>
						</label> <input type="hidden" name="partId" id="partId"  onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,false,partEchCost,partDiscount,partTax);"
							class="select2 form-text">
							<samp id="lastPartOccurred" > </samp>
						
					</div>
					<br> <br>
					<div class="row">
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Quantity
							</label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Unit
								Cost </label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Discount
							</label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">GST </label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Total </label>
						</div>
					</div>

					<div class="row">
						<div class="col col-sm-1 col-md-2">
							<input type="text" class="form-control" placeholder="Qty" name="partQty"
								id="partQuantity" maxlength="10" onpaste="return false"
								onkeypress="return isNumberKeyWithDecimal(event,this.id)"
								onkeyup="javascript:sumthere('partQuantity', 'partEchCost', 'partDiscount', 'partTax', 'partTotalCost',1);"
								min="0.00">
						</div>
						<div class="col col-sm-1 col-md-2">
							<input type="text" class="form-control" placeholder="Unit Price" maxlength="8"
								id="partEchCost" maxlength="10" onpaste="return false" name="partEachCost"
								onkeypress="return isNumberKeyWithDecimal(event,this.id)"
								onkeyup="javascript:sumthere('partQuantity', 'partEchCost', 'partDiscount', 'partTax', 'partTotalCost',1);"
								min="0.00">
								<samp id="lastPartCost"> </samp>
						</div>
						<div class="col col-sm-1 col-md-2">
							<div class="input-group">
								<input type="text" class="form-control allPartDiscount" placeholder="Discount"
									onpaste="return false" id="partDiscount" name="partDiscount" maxlength="5"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere('partQuantity', 'partEchCost', 'partDiscount', 'partTax', 'partTotalCost',1); "
									min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span>
								 <samp id="lastPartDis"> </samp>
							</div>
						</div>
						<div class="col col-sm-1 col-md-2">
							<div class="input-group">
								<input type="text" class="form-control allPartTax " placeholder="GST"
									onpaste="return false" id="partTax" name="partTax" maxlength="5"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									onkeyup=" validatePartTaxDiscount(this.id); javascript:sumthere('partQuantity', 'partEchCost', 'partDiscount', 'partTax', 'partTotalCost',1); "
									min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span>
							 <samp id="lastPartTax"> </samp>
							</div>
						</div>
						<div class="col col-sm-1 col-md-2">
							<input type="text" id="partTotalCost" data-toggle="tip" name="partTotalCost"
								data-original-title="Total cost" class="form-control allPartTotalCost"
								required="required" readonly="readonly">
						</div>
					</div>
					<br>
					<div id="moreParts"></div>
					<br>
					<button type="button" id="add_more" class="add_field_button btn btn-success"> 	<i class="fa fa-plus"></i> Add More </button>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="addPartDetails();">Save Part</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="invoiceModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Part Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <div class="input-group input-append date" id="StartDate">
									<input type="text" class="form-control  browser-default custom-select noBackGround	" name="invoiceDate" readonly="readonly"
										id="modalInvoiceDate" required="required" 
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
										<button type="submit"  class=" input-group-addon add-on btn btn-sm"><i class="fa fa-calendar"></i></button>
								</div>
						    <span style="color: #2e74e6;font-size: 18px;">Invoice Date </span>
						  </label>
					</div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="modalInvoiceNumber"  >
						    <span style="color: #2e74e6;font-size: 18px;">Invoice Number </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveInvoiceDetails();">Save Invoice Details</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal" id="addDealerServiceDocument" tabindex="-2">
	<form method="post" enctype="multipart/form-data" id="fileUploadForm">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">DSE Document</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
						<div class="row1">
							<div class="L-size">
								<label class="L-size control-label"> Browse: </label>
							</div>
							<div class="I-size">
								<input type="file"
									accept="image/png, image/jpeg, image/gif"
									name="input-file-preview" required="required" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<input type="button" value="Submit" id="btnSubmitDoc" class="btn btn-success"/>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<div class="modal" id="partNumberModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Part Number</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<input type="hidden" id="selectedPartId">
				<input type="hidden" id="fromEdit">
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="modalPartName" >
						    <span style="color: #2e74e6;font-size: 18px;">Part Name </span>
						  </label>
					  </div>
					  
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="modalPartNumber"  >
						    <span style="color: #2e74e6;font-size: 18px;">Part Number </span>
						  </label>
					  </div>
					  <div class="col col-sm-12 col-md-5 form-check form-switch " style="padding-left:36px;">
							  <input class="form-check-input" type="checkbox" id="isWarrantyApplicable" />
							  <label class="form-check-label" for=isWarrantyApplicable style="color: #2e74e6; font-size: 14px; wdith: 20%;">Warranty Applicable</label>
						</div>
						  <div class="col col-sm-12 col-md-5">
							  <label class="has-float-label">
							    <input type="Number" onkeypress="return isNumberKeyWithDecimal(event,this.id);" class="form-control browser-default custom-select noBackGround" id="warrantyInMonths"  >
							    <span style="color: #2e74e6;font-size: 18px;">Warranty Months </span>
							  </label>
						  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveMasterPart();">Save Part Number</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="remarkModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Remark</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					  <div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						   <input type="hidden" id="modalDriverId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
						    <span style="color: #2e74e6;font-size: 18px;">Driver </span>
						  </label>
					  </div>
					  <div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						   <input type="hidden" id="modalAssignToId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
						    <span style="color: #2e74e6;font-size: 18px;">Assign To </span>
						  </label>
					  </div>
					  <div class="col col-sm-12 col-md-10">
						  <label class="has-float-label">
						  <textarea  class="form-control browser-default custom-select noBackGround" id="completeRemarkId" rows="3" cols="" style="height: 80px;"></textarea>
						    <span style="color: #2e74e6;font-size: 18px;">Remark </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveComplete();">Complete DSE</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="reopnRemarkModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Remark</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col col-sm-12 col-md-8">
						  <label class="has-float-label">
						  <textarea  class="form-control browser-default custom-select noBackGround" id="reOpenRemarkId" rows="2" cols=""></textarea>
						    <span style="color: #2e74e6;font-size: 18px;">Remark </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveReopenDSE();">Reopen DSE</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="changeDseStatusRemarkModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Remark</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col col-sm-12 col-md-8">
						  <label class="has-float-label">
						  <textarea  class="form-control browser-default custom-select noBackGround" id="changeStatusRemarkId" rows="2" cols=""></textarea>
						    <span style="color: #2e74e6;font-size: 18px;">Remark </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveChangeStatus();">Save</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="extraIssueModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Extra Issue</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<label class="has-float-label" style="width: 100%;">
							<select style="width: 100%;" class="form-control browser-default noBackGround" id="dealerServiceExtraIssueId"   >
							</select>
						 <span style="color: #2e74e6; font-size: 18px;">Recent Message</span>
						</label>
					</div>
					<br>
					<div class="row">
						<label class="has-float-label" style="width: 100%;"> <textarea
								class="form-control browser-default custom-select noBackGround"
								id="description" rows="" cols=""></textarea> <span
							style="color: #2e74e6; font-size: 18px;">DSE Extra Issue</span>
						</label>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveDseExtraIssue();">Save Extra Issue</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="editPartDetails" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Part Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<label> <span
							style="color: #2e74e6; font-size: 14px; wdith: 20%;">Part
								Name</span>
						</label> <input type="hidden" name="partId" id="editPartId" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,editLastPartCost,editLastPartDis,editLastPartTax,false,editPartEachCostxx,editPartDiscountxx,editPartTaxxx,true);"
							class="select2 form-text" >
							<samp id="lastPartOccurred" > </samp>
						
					</div>
					<br> <br>
					<div class="row">
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Quantity
							</label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor"> Cost </label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Discount
							</label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">GST </label>
						</div>
						<div class="col col-sm-1 col-md-2">
							<label class=" control-label" for="selectVendor">Total </label>
						</div>
					</div>

					<div class="row">
						<div class="col col-sm-1 col-md-2">
							<input type="text" class="form-control" placeholder="Qty" 
								id="editPartQuantity" maxlength="10" onpaste="return false"
								onkeypress="return isNumberKeyWithDecimal(event,this.id)" 
								onkeyup="javascript:sumthere('editPartQuantity', 'editPartEachCost', 'editPartDiscount', 'editPartTax', 'editPartTotalCost',1);"
								min="0.00">
						</div>
						<div class="col col-sm-1 col-md-2">
							<input type="text" class="form-control" placeholder="Unit Price" maxlength="8"
								id="editPartEachCost" maxlength="10" onpaste="return false"
								onkeypress="return isNumberKeyWithDecimal(event,this.id)"
								onkeyup="javascript:sumthere('editPartQuantity', 'editPartEachCost', 'editPartDiscount', 'editPartTax', 'editPartTotalCost',1);"
								min="0.00">
						 <samp id="editLastPartCost"> </samp>
						 <input type ="hidden" id="editPartEachCostxx"> </samp>
						</div>
						<div class="col col-sm-1 col-md-2">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Discount"
									onpaste="return false" id="editPartDiscount" maxlength="5"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere('editPartQuantity', 'editPartEachCost', 'editPartDiscount', 'editPartTax', 'editPartTotalCost',1); "
									min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span>
							</div>
							 <samp id="editLastPartDis"> </samp>
							 <input type ="hidden" id="editPartDiscountxx"> </samp>
						</div>
						<div class="col col-sm-1 col-md-2">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="GST"
									onpaste="return false" id="editPartTax" maxlength="5"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere('editPartQuantity', 'editPartEachCost', 'editPartDiscount', 'editPartTax', 'editPartTotalCost',1); "
									min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span>
							</div>
							<samp id="editLastPartTax"> </samp>
							<input type ="hidden" id="editPartTaxxx"> 
						</div>
						<div class="col col-sm-1 col-md-2">
							<input type="text" id="editPartTotalCost" data-toggle="tip"
								data-original-title="Total cost" class="form-control"
								required="required" readonly="readonly">
								<samp id="editLastPartTotalCost"> </samp>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="updatePartDetails();">Update Part</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="modal" id="editLabourDetails" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Labour Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					
				</div>
				<div class="modal-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<label> <span
									style="color: #2e74e6; font-size: 14px; wdith: 20%;">Labour
										Type</span>
								</label> <input type="hidden"  class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;"
								name="editLabourId" id="editLabourId" class="form-control" >
								
							</div>
							<br> <br>
							<div class="row" >
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Hour/KM
									</label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Rate/(Hour/KM)
										Cost </label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Discount
									</label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">GST </label>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class=" control-label" for="selectVendor">Total
									</label>
								</div>
							</div>
							<br> <br>
							<div class="row" >
								<div class="col col-sm-1 col-md-2">
									<input type="text" class="form-control" placeholder="Qty"
										id="editLabourWorkingHours" maxlength="10" onpaste="return false"
										onkeypress="return isNumberKeyWithDecimal(event,this.id)" 
										onkeyup="javascript:sumthere('editLabourWorkingHours', 'editLabourPerHourCost', 'editLabourDiscount', 'editLabourTax', 'editLabourTotalCost',2);"
										min="0.00">
								</div>
								<div class="col col-sm-1 col-md-2">
									<input type="text" class="form-control" placeholder="Unit Price" maxlength="8"
										id="editLabourPerHourCost" maxlength="10" onpaste="return false"
										onkeypress="return isNumberKeyWithDecimal(event,this.id)"
										onkeyup="javascript:sumthere('editLabourWorkingHours', 'editLabourPerHourCost', 'editLabourDiscount', 'editLabourTax', 'editLabourTotalCost',2);"
										min="0.00">
								</div>
								<div class="col col-sm-1 col-md-2">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Discount"
											onpaste="return false" id="editLabourDiscount" maxlength="5"
											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere('editLabourWorkingHours', 'editLabourPerHourCost', 'editLabourDiscount', 'editLabourTax', 'editLabourTotalCost',2); "
											min="0.0"> <span class="input-group-addon"> <em class="fa fa-percent labourPercent"></em></span>
									</div>
								</div>
								<div class="col col-sm-1 col-md-2">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="GST"
											onpaste="return false" id="editLabourTax" maxlength="5"
											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											onkeyup=" validateLabourTaxDiscount(this.id); javascript:sumthere('editLabourWorkingHours', 'editLabourPerHourCost', 'editLabourDiscount', 'editLabourTax', 'editLabourTotalCost',2); "
											min="0.0"> <span class="input-group-addon"> <em class="fa fa-percent labourPercent"></em></span>
									</div>
								</div>
								<div class="col col-sm-1 col-md-2">
									<input type="text" id="editLabourTotalCost" data-toggle="tip"
										data-original-title="Total cost" class="form-control"
										required="required"  readonly="readonly">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="updateLabourDetails();">Update Labour</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="partWarrantyModal" role="dialog">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="currentPartId">
					<h5 class="modal-title ">Part Warranty Details For <b><span id="warrantyPartName"></span></b></h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-primary center" id="allAssignPartButton"
						onclick="showAllWarrantyPart('currentPartId','warrantyPartName');">All Assign Parts </button>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="assignPartDiv">
					<h5 class="modal-title ">Assign Part Details</h5>
						<table class="table">
							<thead style="text-align: center;">
								<tr>
									<th class='fit ar' style="text-align: center;">Sr No</th>
									<th class='fit ar' style="text-align: center;">Serial Number</th>
									<th class='fit ar' style="text-align: center;">Replace part</th>
									<th class='fit ar' style="text-align: center;">Replace Service Number</th>
									<th class='fit ar' style="text-align: center;">isOldPartReceived</th>
									<th class='fit ar' style="text-align: center;">Action</th>
								</tr>
							</thead>
							<tbody id="assignPartOfDseTable" style="text-align: center;">
	
							</tbody>
	
						</table>
					</div>
					<div id="notAssignPartDiv">
					<h5 class="modal-title ">Not Assign Part Details</h5>
						<table class="table">
							<thead>
								<tr>
									<th class='fit ar' style="text-align: center;">Sr No</th>
									<th class='fit ar' style="text-align: center;">Serial Number</th>
									<th class='fit ar' style="text-align: center;">Replace Part</th>
									<th class='fit ar' style="text-align: center;">Old Part Received</th>
								</tr>
							</thead>
							<tbody id="underWarrantyPartTable" style="text-align: center;">
	
							</tbody>
	
						</table>
					</div>
	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="updatePartWarrantyStatus"
						onclick="updatePartWarrantyStatus();">Update</button>
				</div>
			</div>
		</div>
	</div>

<div class="modal fade" id="allPartWarrantyModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Under Warranty Part Details <b><span id="allWarrantyPartName"></span></b></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table" >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">Sr No</th>
							<th class='fit ar' style="text-align: center;">Serial Number</th>
							<th class='fit ar' style="text-align: center;">Service Number</th>
						</tr>
					</thead>
					<tbody id="allPartWarrantyTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
					<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
			</div>
		</div>
	</div>
	
	<input type="hidden" value="1" id="statues">
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/ShowDealerServiceEntries.js"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntriesCommon.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  