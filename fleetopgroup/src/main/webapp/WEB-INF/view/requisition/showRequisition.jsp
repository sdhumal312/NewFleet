<%@ include file="../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/modalAnimation/animateModal.css" />">
<style>
body {
	font-family: Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 12px;
	line-height: 0.8;
	color: #394347;
}

.key {
	width: 100%;
	text-align: right;
}

.noBackGround {
	background: none;
}

.select2-container .select2-choice {
	height: 31px;
	font-size: 15px;
}

.select2-container {
	width: 80%;
	padding: 0;
}

.select2-container .select2-choice {
	padding: 5px 0 0 8px;
}
.select2-container-multi .select2-choices {
	min-height: 38px;
}
.select2-results .select2-result-label{
min-height: 2em;
padding: 6px 7px 4px;
}

.select2-container .select2-choice {
	height: 36px;
}

.styleTable {
	width: 98%;
	margin: 0 auto;
	box-shadow: 5px 10px 5px #2c2c2c;
	padding: 10px 20px;
	line-height: 0.8;
}

.styleTable td, .styleTable th {
	line-height: 0.6;
	vertical-align: middle;
}

.bgWhite {
	background: white;
}
.headSpan{
font-weight: bolder;
font-size: 15px;
}

.showError{
box-shadow: 0px 1px 5px red;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					 <a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> /
					<a href="requisition">Requisition</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	<input type="hidden" id="requisitionId" value="${requisitionId}">
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="requisitionStatusId" value="${requisitionDetails.requisitionStatusId}">
	<input type="hidden" id="allowToEdit" value="${allowToEdit}">
	<input type="hidden" id="allPermission" value="${allPermission}">
	<input type="hidden" id="requisitionAssignTo" value="${requisitionDetails.assignTo}">
	<input type="hidden" id="requisitionLocation" value="${requisitionDetails.location}">
	<input type="hidden" id="userId" value="${userId}">
	<input type ="hidden" id="subRequisitionIdStock" value="0">
	<input type="hidden" id="showManufacturerAndSize" value="${configuration.showManufacturerAndSize}">
	<input type="hidden" id="showUpholstery" value="${configuration.showUpholstery}">
	<input type="hidden" id="showPartUOM" value="${configuration.showPartUOM}">
		<div class="row">
				<div class="box">
					<div class="boxinside">
					<div class="row">
						<div class="col col-sm-6">
							<div >
								<span class="btn btn-outline-dark" style="font-size: 20px; font-weight: bolder;">Requisition
									Id : <a href="#">R-${requisitionDetails.requisitionNum} </a>
								</span>
							</div>
						</div>
								<c:if test="${requisitionDetails.requisitionStatusId == 3 && (allPermission|| markAsComplete)}">
						<div class="col col-sm-6">
							<div class="pull-right">
								<button type="button" onclick="markAsComplete(${requisitionId})"
									class="btn btn-warning btn-lg">Mark As Complete</button>
									
									
									<sec:authorize access="hasAuthority('JOB_CARD_PRINT')">
						<button class="btn btn-info btn-lg" id="partPrint" style="display: none;"
							href="javascript:void(0)" onclick="printPartPurchargeOrder(${requisitionId});"> <span
							class="fa fa-print"></span>  Print
						</a>
						</sec:authorize>
						
						
							</div>
						</div>
						</c:if>
					</div>
					<br>
						<div class="secondary-header-title">
							<table class="table">
								<tbody>
									<tr>
										<td><span class="headSpan">Requisition Location :</span> <a href="#"> 
											<span id="requisitionSenderName">${requisitionDetails.locationName}</span></a></td>
	
										<td class="alignRight"> <span class="headSpan">Assigned to :</span><a href="#">
											<span id="requisitionReceiverName">${requisitionDetails.assignToName}</span></a></td>
									</tr>
									<tr>
										<td><span class="headSpan">Required Date :</span> <a href="#">
											<span id="requiredDate">${requisitionDetails.requireOnStr}</span></a></td>
										<td><span class="headSpan">Reference No. : </span><a href="#">
											<span id="requiredQuantity">${requisitionDetails.refNumber}</span></a></td>
									</tr>
									<tr>
										<td rowspan="2" style="width: 50%;" ><span class="headSpan">Remark : </span><span id="requisitionRemark">${requisitionDetails.remark}</span></td>
										<td ><span class="headSpan">Status : </span><a href="#"> ${requisitionDetails.requisitionStatusName}<span id="requisitionStatusName"></span></a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
			</div>
		</div>
		
		<div class="row" id="partShowDiv" style="display:none;">
				<div class="box">
					<div class="boxinside">
					<div class="table-responsive">
					<div class="row">
					<div class="col col-sm-6">
					<button type="button" class="btn btn-primary btn-lg btn-block" style="width: 40%">Part Details</button>
					</div>
					<c:if test="${requisitionDetails.requisitionStatusId == 3}">
					<div class="col col-sm-6 hide" id="receiveAllButton">
					<button type="button" class="fa fa-get-pocket btn btn-success btn-block btn-lg pull-right" onclick="receiveAllParts(${requisitionId})" style="width: 23%">Receive All Parts</button>
					</div>
					</c:if>
					</div>
					<br>
					<table id="styled" class="table table-hover table-bordered styleTable"><caption>
				
						</caption>
						<tbody id="partTableForBranch">
						</tbody>
						</table>
						<table id="styled" class="table table-hover table-bordered styleTable"><caption>
						</caption>
						<thead id="partHead">
							<tr>
								<th>SR NO</th>
								<th>Part Name</th>
								<th>Quantity</th>
								<th id="partAction">Action</th>
							</tr>
						</thead>
						<tbody id="partTable">
						
						</tbody>
						</table>
						
					</div>
				</div>
					
			</div>
		</div>
		
			<div class="row" id="clothShowDiv"  style="display:none;">
				<div class="box">
					<div class="boxinside">
					<div class="table-responsive">
					<button type="button" class="btn btn-primary btn-lg btn-block" style="width: 20%">Upholstery Details</button><br>
						<table class="table table-hover table-bordered styleTable">
						<caption>
				.
						</caption>
						<thead id="clothHead">
							<tr>
								<th>SR NO</th>
								<th>Upholstery</th>
								<th>Quantity</th>
								<th id="clothAction">Action</th>
							</tr>
						</thead>
						<tbody id="clothTable">
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row" id="ureaShowDiv"  style="display:none;">
				<div class="box">
					<div class="boxinside">
					<button type="button" class="btn btn-primary btn-lg btn-block" style="width: 20%">Urea Details</button><br>
					<div class="table-responsive">
					
					
						<table class="table table-hover table-bordered styleTable">
						<caption>
				.
						</caption>
						<thead id="ureaHead">
							<tr>
								<th>SR NO</th>
<!-- 								<th>Urea Manufacturer</th> -->
								<th>Quantity</th>
								<th id="ureaAction">Action</th>
							</tr>
						</thead>
						<tbody id="ureaTable">
						
						</tbody>
						</table>
						<table class="table table-hover table-bordered styleTable">
						<caption>
				.
						</caption>
						<tbody id="ureaTable4">
						
						</tbody>
						</table>
					</div>
				</div>
					
			</div>
		</div>
		
			<div class="row" id="tyreShowDiv"  style="display:none;">
				<div class="box">
					<div class="boxinside">
					<button type="button" class="btn btn-primary btn-lg btn-block" style="width: 20%">Tyre Details</button><br>
					<div class="table-responsive">
					<table class="table table-hover table-bordered styleTable">
						<caption>
				.
						</caption>
						
						<tbody id="tyreTable1">
						
						</tbody>
						</table>
						<table class="table table-hover table-bordered styleTable">
						<caption>
				.
						</caption>
						<thead id="tyreHead">
							<tr>
								<th>SR NO</th>
								<th>Tyre Model</th>
<%-- 								<c:if test="${configuration.showManufacturerAndSize}"> --%>
<!-- 								<th>Tyre Manufacturer</th> -->
<!-- 								<th>Tyre Size</th> -->
<%-- 								</c:if> --%>
								<th>Quantity</th>
								<th id="tyreAction">Action</th>
							</tr>
						</thead>
						<tbody id="tyreTable">
						
						</tbody>
						</table>
					</div>
				</div>
					
				</div>
		</div>
		
				<div class="row" id="batteryShowDiv"  style="display:none;">
				<div class="box">
					<div class="boxinside">
					<div class="table-responsive">
					<button type="button" class="btn btn-primary btn-lg btn-block" style="width: 20%">Battery Details</button><br>
					
					<table class="table table-hover table-bordered styleTable">
							<caption>
				.
						</caption>
						
						<tbody id="batteryTable3">
						
						</tbody>
						</table>
					
						<table class="table table-hover table-bordered styleTable">
							<caption>
				.
						</caption>
						<thead id="BatteryHead">
							<tr>
								<th>SR NO</th>
<%-- 									<c:if test="${configuration.showManufacturerAndSize}"> --%>
<!-- 								<th>Battery Manufacturer</th> -->
<%-- 								</c:if> --%>
<%-- 								<c:if test="${configuration.showManufacturerAndSize}"> --%>
<!-- 								<th>Battery Model</th> -->
<%-- 								</c:if> --%>
								<th>Battery Capacity</th>
								<th>Quantity</th>
								<th id="batteryAction">Action</th>
							</tr>
						</thead>
						<tbody id="batteryTable">
						</tbody>
						</table>
					</div>
				</div>
					
			</div>
		</div>
		<c:if test="${requisitionDetails.requisitionStatusId < 3 && (requisitionDetails.assignTo == userId || allPermission)}">
		<div class="row">
			<div class="box">
				<div class="boxinside" style="text-align:center;">
				  <button type="button"  onclick="finalApproval()" class="btn btn-success btn-lg "><em class="fa fa-check-circle fa-spin"></em> Final Approval</button>
				</div>
			</div>
		</div>
		</c:if>
	</section>
		</div>
<!-- 		<div class="row"> -->
<!-- 			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | -->
<!-- 			<small class="text-muted"><b>Created date: </b>  <span id="createdDate"> </span></small> | -->
<!-- 			<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy"></span></small> | -->
<!-- 			<small class="text-muted"><b>Last updated date:</b>  <span id="lastUpdatedDate"></span></small> -->
<!-- 		</div> -->
	
	
		<div class="modal fade" id="editSubReqModal" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Edit</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<input type="hidden" id="requisitionEditId">
									 <div class="row">
									 	<div class="col col-sm-6">
										 <label class="has-float-label">
										    <select id="requisitionType"
													style="line-height: 30px;font-size: 15px;height: 35px" name="requisitionType" onchange="hideDiv(this.id)" >
														<option value="0">Select Type</option>
														<option value="1">Part</option>
														<option value="2">Tyre</option>
														<option value="3">Battery</option>
														<option value="4">Upholstery</option>
														<option value="5">Urea</option>
												</select>
										    
										    <span style="color: #2e74e6;font-size: 16px;">Requisition type <abbr title="required">*</abbr></span>
										  </label>
									</div>
									<div id="partDiv" class="col col-sm-6">
										 <label class="has-float-label">
										 <input type="hidden" name="partId" id="partId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;">
										    <span style="color: #2e74e6;font-size: 18px;" >Part Name </span>
										  </label>
										</div>
									<div id="tyreModelDiv" class="col col-sm-6">
										 <label class="has-float-label">
										 <select style="line-height: 30px;font-size: 15px;height: 35px;" id="tyremodel"
																			name="TYRE_MODEL_ID" required="required"  onchange="tyreModelChange();" >
																		</select>
										    <span style="color: #2e74e6;font-size: 18px;" >Model </span>
										  </label>
										</div>
										<div id="batteryManuDiv" class="col col-sm-6">
										 <label class="has-float-label">
										 	<input type="hidden" id="batteryManufacturer"
																name="batteryManufacturer" style="line-height: 30px;font-size: 15px;height: 35px;"
																placeholder="Please Enter 2 or more Battery Manufacturer Name" />
										    <span style="color: #2e74e6;font-size: 18px;" >Manufacturer </span>
										  </label>
										</div>
										<div id="upholsteryDiv"class="col col-sm-6">
										 <label class="has-float-label">
										<input type="hidden" id="clothTypes"
															name="clothTypes" style="line-height: 30px;font-size: 15px;height: 35px;"
															placeholder="Please Enter 2 or more Cloth Types Name" />
										    <span style="color: #2e74e6;font-size: 18px;" >Upholstery </span>
										  </label>
										</div>
<!-- 										<div id="ureaDiv" class="col col-sm-6"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 											<input type="hidden" id="ureaManufacturer" -->
<!-- 															name="ureaManufacturer" style="line-height: 30px;font-size: 15px;height: 35px;" -->
<!-- 															placeholder="Please Enter 2 or more Manufacturer Name" /> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Manufacturer</span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
										</div>
										<div class="row">
										<br>
									<div id="tyreManuDiv" class="col col-sm-6">
									<br>
										 <label class="has-float-label">
										 <input type="hidden" id="manufacurer" style="line-height: 30px;font-size: 15px;height: 35px;" name="manufacurer" style="width: 100%;"
																			required="required"
																			placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
										    <span style="color: #2e74e6;font-size: 18px;" >Manufacturer </span>
										  </label>
										</div>
									<div id="tyreSizeDiv" class="col col-sm-6">
									<br>
										 <label class="has-float-label">
										 <input type="hidden" id="tyreSize" name="tyreSize"
																			style="line-height: 30px;font-size: 15px;height: 35px;" required="required"
																			placeholder="Please select Tyre Size" />
										    <span style="color: #2e74e6;font-size: 18px;" >Size </span>
										  </label>
										</div>
									
									<div id="batteryTypeDiv" style="padding: 10px" class="col col-sm-6">
										 <label class="has-float-label">
										<select style="line-height: 30px;font-size: 15px;height: 35px;" id="batterryTypeId"
															 name="batteryTypeId"></select>
										    <span style="color: #2e74e6;font-size: 18px;" > Model </span>
										  </label>
										</div>
									<div id="batteryCapaDiv" style="padding: 10px" class="col col-sm-6">
										 <label class="has-float-label">
										<input type="hidden" id="batteryCapacityId" name="batteryCapacityId"
																style="line-height: 30px;font-size: 15px;height: 35px;"
																placeholder="Please select Battery Capacity" />
										    <span style="color: #2e74e6;font-size: 18px;" > Capacity</span>
										  </label>
										</div>
									
									</div>
									<div class="row">
									 <div class="col col-sm-6">
									<br>
										 <label class="has-float-label">
										    <input type="text" class="form-control browser-default noBackGround" name="Qty" id="Qty"
										     onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										     style="width: 80%"
										     >
										    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>
										  </label>
										</div>
									 
									 </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveEditSubRequisition();">Update</button>
				</div>
			</div>
		</div>
	</div>



	<div id="rejectModal" class="w3-modal">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom"
			style="max-width: 600px">
			
			<div class="modal-header">
					<h5 class="modal-title"> Reject With Remark :</h5>
					<button type="button" class="close" onclick="document.getElementById('rejectModal').style.display='none'"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

			<div class="modal-body" >
				<div class="w3-section">
				<input type="hidden" id="reqIdForReject" value="0">
					<label><b>Rejection Remark</b></label> <input
						class="w3-input w3-border w3-margin-bottom" type="text"
						placeholder="Please enter mandatory Remark" name="rejectRemark" id="rejectRemark"> 
					<button id="rejectSubReqButtton" class="w3-button w3-block w3-green w3-section w3-padding"
						type="button" onclick="rejectSubRequisition()">Reject</button>
						
						<button id="rejectAppButtton" class="w3-button w3-block w3-green w3-section w3-padding"
						type="button" onclick="rejectApprovalWithRemark()">Reject</button>
						
							<button id="rejectReceiveButtton" class="w3-button w3-block w3-green w3-section w3-padding"
						type="button" onclick="rejectApprovalReceive()">Reject</button>
					
				</div>
			</div>

			<div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
				<button
					onclick="document.getElementById('rejectModal').style.display='none'"
					type="button" class="w3-button w3-red">Cancel</button>
			</div>

		</div>
	</div>
	
	<div id="stockModal" class="w3-modal">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom"
			style="max-width: 800px">
				<div class="modal-content">
			<div class="modal-header">
					<h5 class="modal-title"> Stock Details </h5><h5 class="modal-title" id="partNameId">:</h5>
					<button type="button" class="close" onclick="document.getElementById('stockModal').style.display='none'"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			
			<div class="modal-body" >
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-resposive">
							<table id="stockTable" style="width: 100%;box-shadow: 5px 10px 5px #2c2c2c;" class="table table-hover table-bordered">
							</table>
							<br>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
	</div>
	
		<div id="approvalModal" class="w3-modal" role="dialog">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom modal-dialog"
			style="max-width: 1600px">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<h4 class="modal-title"
						style="font-weight: bold; text-align: right;">Approval
						Details: &nbsp</h4>
				</div>
				<h5 class="modal-title" style="text-align: center;">
					&nbsp &nbsp Quantity :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalQuantitySamp"></samp>
					&nbsp &nbsp Approved Quantity :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="balanceQuantitySamp"></samp>
				</h5>
				<button type="button" class="close"
					onclick="document.getElementById('approvalModal').style.display='none'"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" >
			 <samp  style="color:blue; font-weight: bolder ;" id="approvalDetailsSamp"></samp>
			 <br>
			<div class="main-body">
			<br>
					<div class="box border border-warning">
					<br>
					<div class="box-body">
						
							<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <select
										id="transferType" name="transferType" onchange="hideBranch(this.id)" style="line-height: 30px; font-size: 15px; height: 35px;">
											<option value="0">--- Select ---</option>
											<option value="1">Branch</option>
											<option value="2">PO</option>
									</select> <span style="color:#000000; font-size: 18px;">Transfer
											Type :</span>
									</label>
								</div>
								<div id="locationDiv" class="col col-md-6 hide">
									<label class="has-float-label"> <input type="hidden"
										name="requisitionlocation" id="requisitionlocation" onchange="getLocationWiseStock(this.id)"
										style="line-height: 30px; font-size: 15px; height: 35px;"> <span
										style="color: #000000; font-size: 18px;">From Branch</span>
									</label>
									<samp style="color: blue;font-weight: bold" id="stockQuantity"></samp>
									<input type="hidden" id="availableStock" name="availableStock">
								<br>
								</div>
								
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="text"
										class="form-control browser-default noBackGround" name="quantity"
										id="quantity"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="setBalanceStock(this.id)"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Qty</span>
									</label>
									<br>
								</div>
							</div>
							<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="hidden"
										id="assignToApp" name="assignToApp" class="browser-default"
										style="line-height: 30px; font-size: 15px; height: 35px;">
										<span style="color: #000000; font-size: 18px;">Transfer By </span>
									</label>
								</div>
										<div class="col col-md-6">
											<label class="has-float-label"> <input type="text"
												class="form-control browser-default custom-select noBackGround"
												name="appRemark" id="appRemark" placeholder="please Enter Remark" style="width:80%"> <span
												style="color: #000000; font-size: 18px;">Remark</span>
											</label>
										</div>
										</div>
				</div>
						</div>
							<div class="divForMore">
							
							</div>
							<br>
					<br>
							<button type="button"  class="btn btn-info addMoreButton" style="background:lightseagreen;"><span class="fa fa-plus"></span></button>
			</div>
		</div>
			<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								onclick="saveApprovalDetails()" >Approve</button>
							<button type="button" class="btn btn-link" onclick="document.getElementById('approvalModal').style.display='none'">Cancel</button>
						</div>
		</div>
		</div>
	</div>
	
	
		
		<div id="showApprovalModal" class="w3-modal">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom modal-dialog "
			style="max-width: 1600px">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<h4 class="modal-title"
						style="font-weight: bold; text-align: right;">Approval
						Details: &nbsp</h4>
				</div>
				<h5 class="modal-title" style="text-align: center;">
					&nbsp &nbsp Quantity :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalQuantitySampApp"></samp>
					&nbsp &nbsp Approved Quantity :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="balanceQuantitySampApp"></samp>
						<input type="hidden" id="balanceQuantityApp" name="quantityId">
				</h5>
				<button type="button" class="close"
					onclick="closeApprovalModal();"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" >
			 <samp  style="color:blue; font-weight: bolder ;" id="approvalDetailsSamp"></samp>
			 <br>
			<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-resposive">
								<table id="approvedTable"
									style="width: 100%; box-shadow: 5px 10px 5px #2c2c2c;"
									class="table table-hover table-bordered">
								</table>
								<br>
							</div>
						</div>
					</div>

					<button id="collapseButton" type="button" data-toggle="collapse" data-target="#collapseDiv" class="btn btn-info" onclick="prepareSelectFields()" style="background:lightseagreen;"><span class="fa fa-plus"></span> Add Approval</button>
					<br>
					<br>
					<div id="collapseDiv"  class="box collapse" >
					<br>
								<div class="box-body">
								<br>
								<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <select
										id="transferTypeId" name="transferTypeId" onchange="hideBranch(this.id)" style="line-height: 30px; font-size: 15px; height: 35px;">
											<option value="0">--- Select ---</option>
											<option value="1">Branch</option>
											<option value="2">PO</option>
									</select> <span style="color:#000000; font-size: 18px;">Transfer
											Type :</span>
									</label>
								</div>
								<div id="locationDivId" class="col col-md-6 hide">
									<label class="has-float-label"> <input type="hidden"
										name="requisitionlocationId" id="requisitionlocationId" onchange="getLocationWiseStock(this.id,true)"
										style="line-height: 30px; font-size: 15px; height: 35px;"> <span
										style="color: #000000; font-size: 18px;">Branch</span>
									</label>
									<samp style="color: blue;font-weight: bold" id="stockQuantityId"></samp>
									<input type="hidden" id="availableStockId" name="availableStockId">
								<br>
								</div>
								
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="text"
										class="form-control browser-default noBackGround" name="quantityId"
										id="quantityId"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateQuantitySingle(this.id)"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Qty</span>
									</label>
								</div>
								
							</div>
								<br>
								<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="hidden"
										id="assignToSingle" name="assignToSingle" class="browser-default"
										style="line-height: 30px; font-size: 15px; height: 35px;">
										<span style="color: #000000; font-size: 18px;">Transfer By </span>
									</label>
								</div>
										<div class="col col-md-6">
											<label class="has-float-label"> <input type="text"
												class="form-control browser-default custom-select noBackGround"
												name="remarkSingle" id="remarkSingle" placeholder="please Enter Remark" style="width:80%"> <span
												style="color: #000000; font-size: 18px;">Remark</span>
											</label>
										</div>
										</div>
							<div class="row">
							<div class="col col-md-6">
								<button type="button"  class="btn btn-info" onclick="saveSingleApprovalDetails()" style="background:lightseagreen;"><span class="fa fa-plus"></span> Submit </button>
								</div>
							</div>
								</div>
							</div>
			</div>
		</div>
			<div class="modal-footer">
							<button type="button" class="btn btn-link" onclick="closeApprovalModal();">Cancel</button>
						</div>
		</div>
		</div>
	</div>
	
	<div id="transferModal" class="w3-modal">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom modal-dialog "
			style="max-width: 1600px">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<h4 class="modal-title"
						style="font-weight: bold; text-align: right;">Transfer Details : &nbsp;</h4>
				</div>
				<h5 class="modal-title" style="text-align: center;">
					&nbsp; Approved Quantity :&nbsp;
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalQuantitySampTrn"></samp>
						&nbsp;Transfered Quantity :
						<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="alreadtTransferedSampTrn"></samp>
							&nbsp;Pending Quantity :
						<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="pendingTransferedSampTrn"></samp>
						</h5>
						<input type="hidden" id="approvalIdTransfer" >
						<input type="hidden" id="transferLocation" >
						<input type="hidden" id="transferPartId" >
						<input type="hidden" id="branchStockTransfer" >
						<input type="hidden" id="tManufacturer" >
						<input type="hidden" id="tModel" >
						<input type="hidden" id="tSize" >
						<input type="hidden" id="tRType" >
				
				<button type="button" class="close"
					onclick="document.getElementById('transferModal').style.display='none'"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" >
			 <samp  style="color:blue; font-weight: bolder ;" id="transferDetailsSamp"></samp>
			 <br>
			  <br>
			   <br>
			<div class="main-body">
					<div>
					<br>
								<div class="box-body">
								<span style="color:black; font-weight: bolder ;">From Location :  </span><span  style="color:black; font-weight: bold ;" id="transferBranchQtySamp"></span>
								<br>
								<br><br>
								<div class="row">
								<div id="ureaManDiv" class="col col-md-6">
									<label class="has-float-label"> <input type="hidden"
										id="tUreaManufacturer" name="ureaManufacturer"
										style="line-height: 30px; font-size: 15px; height: 35px;" onchange="setBranchAvailableQty()"
										placeholder="Please Enter 2 or more Manufacturer Name" /> <span
										style="color: #000000; font-size: 18px;">Urea Manufacturer : </span>
									</label>
									<br>
									<br>
								</div>
								<div class="col col-md-6" id="quantityDivT">
									<label class="has-float-label"> <input type="text"
										class="form-control browser-default noBackGround" name="transferQty"
										id="transferQty"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										onkeyup="return validateTransferQuantity();"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Qty<abbr title="required">*</abbr></span>
									</label>
								</div>
									<div class="col col-md-6" id="battarySelectDiv">
									<label class="has-float-label"> <input type="hidden"
										class="form-control browser-default noBackGround" name="batteryId"
										id="batteryId"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Battary<abbr title="required">*</abbr></span>
									</label>
								</div>
								
									<div class="col col-md-6" id="tyreSelectDiv">
									<label class="has-float-label"> <input type="hidden"
										class="form-control browser-default noBackGround" name="tyreId"
										id="tyreId"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Tyre<abbr title="required">*</abbr></span>
									</label>
								</div>
								
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="hidden"
										id="assignToRec" name="assignToRec" class="browser-default"
										style="line-height: 30px; font-size: 15px; height: 35px;">
										<span style="color: #000000; font-size: 18px;">Receive By<abbr title="required">*</abbr>
											</span>
									</label>
								</div>
								
							</div>
							<div class="row">
							<div class="col col-md-6">
								<button type="button"  class="btn btn-info" onclick="saveTransferQuantity()" style="background:lightseagreen;"><span class="fa fa-plus"></span> Submit </button>
								</div>
							</div>
								</div>
							</div>
			
			</div>
		</div>
			<div class="modal-footer">
							<button type="button" class="btn btn-link" onclick="document.getElementById('transferModal').style.display='none'">Cancel</button>
						</div>
		</div>
		</div>
	</div>


<div id="poModal" class="w3-modal">
	<div class="w3-modal-content w3-card-4 w3-animate-zoom modal-dialog "
		style="max-width: 1600px">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<h4 class="modal-title"
						style="font-weight: bold; text-align: right;">Create PO :
						&nbsp</h4>
				</div>
				<button type="button" class="close"
					onclick="document.getElementById('poModal').style.display='none'"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<samp style="color: blue; font-weight: bolder;"
					id="transferDetailsSamp"></samp>
				<br> 
				<div class="main-body">
					<div>
						<br>
						<div class="box-body">
							<samp style="color: blue; font-weight: bolder;"
								id="transferBranchQtySamp"></samp>
								<input type="hidden" id="approvalIdPo">
								<input type="hidden" id="poApprovalQty">
							<br> 
							<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <input type="hidden"
										id="selectVendor" name="purchaseorder_vendor_id"
										style="width: 100%;" required="required"
										placeholder="Please Select Vendor Name" /><span
										style="color: #000000; font-size: 18px;">Vendor<abbr
											title="required">*</abbr></span>
									</label>
								</div>
								<div class="col col-md-6">
									<label class="has-float-label"> <select
										style="width: 100%;" name="purchaseorder_termsId" id="terms"
										required="required">
											<option value="1">CASH</option>
											<option value="9">C.O.D</option>
											<option value="2">CREDIT</option>
											<option value="3">NEFT</option>
											<option value="4">RTGS</option>
											<option value="5">IMPS</option>
											<option value="6">DD</option>
											<option value="7">CHEQUE</option>
											<option value="8">BANK DRAFT</option>
											<option value="9">COD</option>
											<option value="10">ON ACCOUNT</option>
									</select> <span style="color: #000000; font-size: 18px;">Terms<abbr
											title="required">*</abbr>
									</span>
									</label>
								</div>
							</div>
							<br>
							<div class="row">
								<div class="col col-md-6">
									<label class="has-float-label"> <select
										style="width: 100%;" name="purchaseorder_shipviaId"
										id="shipvia" required="required">
											<option value="1">AIR</option>
											<option value="2">COURIER</option>
											<option value="3">EXPEDITED</option>
											<option value="4">GROUND</option>
											<option value="5">NEXT DAY</option>
											<option value="6">NONE</option>
											<option selected="selected" value="7">BY ROAD</option>
									</select> <span style="color: #000000; font-size: 18px;">Ship
											Via:<abbr title="required">*</abbr>
									</span>
									</label>
								</div>
								<div class="col col-md-6">
									<button type="button" class="btn btn-info"
										onclick="savePO()"
										style="background: lightseagreen;">
										<span class="fa fa-plus"></span> Submit
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-link"
					onclick="document.getElementById('poModal').style.display='none'">Cancel</button>
			</div>
		</div>
	</div>
</div>

	<div id="receiveModal" class="w3-modal">
		<div class="w3-modal-content w3-card-4 w3-animate-zoom modal-dialog "
			style="max-width: 1600px">
		<div class="modal-content">
			<div class="modal-header">
				<div class="row">
					<h4 class="modal-title"
						style="font-weight: bold; text-align: right;">Receive Details : &nbsp</h4>
				</div>
				<h5 class="modal-title" style="text-align: center;">
					&nbsp &nbsp Transfered Qnty :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalTransferedQuantitySamp"></samp>
				</h5>
				<h5 id="receivedQuantityH5" class="modal-title" style="text-align: center;">
					&nbsp &nbsp Received Qnty :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalreceivedQuantitySamp"></samp>
				</h5>

				<h5 id="pendingQuantityH5" class="modal-title" style="text-align: center;">
					&nbsp &nbsp Pending Qnty :&nbsp
					<samp style="color: black; font-weight: bolder;"
						class="modal-title" id="totalPendingQuantitySamp"></samp>
				</h5>
				<button type="button" class="close"
					onclick="document.getElementById('receiveModal').style.display='none'"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" >
			 <samp  style="color:blue; font-weight: bolder ;" id="receiveDetailsSamp"></samp>
			 <input type="hidden" id="approvalIdReceive">
			 <input type="hidden" id="RRType">
			 <input type="hidden" id="fromPartial" value="false">
			 <input type="hidden" id="maxReceiveAllow">
			 <br>
			  <br>
			   <br>
			<div class="main-body">
			
				<div class="box" id="partialTableBox">
						<div class="box-body">
							<div class="table-resposive">
								<table id="partialTable"
									style="width: 100%; box-shadow: 5px 10px 5px #2c2c2c;"
									class="table table-hover table-bordered">
								</table>
								<br>
							</div>
						</div>
					</div>

					<div class="box" id="receiveTableBox">
						<div class="box-body">
							<div class="table-resposive">
								<table id="receiveTable"
									style="width: 100%; box-shadow: 5px 10px 5px #2c2c2c;"
									class="table table-hover table-bordered">
								</table>
								<br>
							</div>
						</div>
					</div>
					<div id="partiallyReceiveDiv">
					<br>
								<div class="box-body">
								<div class="row">
								<div class="col col-md-6" id="receiveQtyDiv">
									<label class="has-float-label"> <input type="text"
										class="form-control browser-default noBackGround" name="receiveQty"
										id="receiveQty" placeholder="Please Enter Quantity"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										style="width: 80%"> <span
										style="color: #000000; font-size: 18px;">Qty <abbr title="required">*</abbr></span>
									</label>
								</div>
								<div class="col col-md-6">
											<label class="has-float-label"> <input type="text"
												class="form-control browser-default custom-select noBackGround"
												name="receiveRemark" id="receiveRemark" placeholder="Please Enter Remark" style="width:80%"> <span
												style="color: #000000; font-size: 18px;">Remark </span>
											</label>
										</div>
							</div>
							<div class="row">
							<div class="col col-md-6">
								<button type="button"  class="btn btn-info" onclick="saveReceiveWithQuantity()" style="background:lightseagreen;"><span class="fa fa-plus"></span> Submit </button>
								</div>
							</div>
								</div>
							</div>
			
			</div>
		</div>
			<div class="modal-footer">
							<button type="button" class="btn btn-link" onclick="document.getElementById('receiveModal').style.display='none'">Cancel</button>
						</div>
		</div>
		</div>
	</div>




<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/showRequisition.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/requisitionCommon.js"></script> 
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/transferRequisition.js"></script> 