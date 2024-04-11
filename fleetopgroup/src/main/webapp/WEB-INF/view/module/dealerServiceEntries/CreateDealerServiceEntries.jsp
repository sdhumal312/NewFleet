<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.label_font{
	font-weight: bold;
	font-size: larger;
}
.noBackGround{
	background: none;
}


.col{
	margin-top: 20px;
}
.custom-select{
	height: 42px;
    font-size: 15px;
 }
.select2-container {
	width: 100%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 38px;
}


.select2-container .select2-choice {
   height: 36px;
}


</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="<c:url value="/dealerServiceEntries.in"/>">Dealer Service Entries</a>/
					<span id="NewVehicle">Create Dealer Service Entries</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="dealerServiceEntries.in.in">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_DEALER_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_DEALER_SERVICE_ENTRIES')">
		<form method="post" enctype="multipart/form-data" id="fileUploadForm">
			<div class="row">
				<div class="col-sm-8 col-md-12">
					<input type="hidden" id="companyId" value="${companyId}">
					<input type="hidden" id="userId" value="${userId}">
					<input type="hidden" id="issueId" value="${issueId}">
					<input type="hidden" id="issueVid" value="${issueVid}">
					<input type="hidden" id="srVid" value="${srVid}">
					<input type="hidden" id="srVehicleNumber" value="${srVehicleNumber}">
					<input type="hidden" id="srNumber" value="${srNumber}">
					<input type="hidden" id="programId" value="${programId}">
					<input type="hidden" id="programName" value="${programName}">
					<input type="hidden" id="partWarranty" value="${configuration.partWarranty}">
					<input type="hidden" id="validateOdometer" value="${configuration.validateOdometer}">
					<input type="hidden" id="showServiceProgram" value="${configuration.showServiceProgram}">
					<input type="hidden" id="showServRemindWhileCreating" value="${configuration.showServRemindWhileCreating}">
					<input type="hidden" id="vehicle_ExpectedOdameter" /> 
					<input type="hidden" id="vehicle_Odameter" />
					<input type="hidden" id="backDateMaxOdo" />
					<input type="hidden" id="backDateMinOdo" />
					<input type="hidden" id="accidentId">
					<div class="tab-content">
							<div class="box" >
								<div class="box-body">
									 <label class="has-float-label ">
									  <span style="color: #2e74e6;font-size: 24px;">Dealer Service Entries Details</span>
									</label>
									<br>
									<c:choose>
										<c:when test="${issueId > 0}">
											<div class="col col-sm-1 col-md-3">
											 <label class="has-float-label">
											     <input type="text" class="form-control browser-default custom-select noBackGround" style="line-height: 30px;font-size: 15px;height: 35px;" value="${issueVehicleNumber}" readonly="readonly">
											    <span style="color: #2e74e6;font-size: 18px;">Vehicle <abbr title="required">*</abbr></span>
											  </label>
										</div>
										</c:when>
										<c:otherwise>
											<div class="col col-sm-1 col-md-3">
											 <label class="has-float-label">
											     <input type="hidden" id="vehicleId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
											    <span style="color: #2e74e6;font-size: 18px;">Vehicle <abbr title="required">*</abbr></span>
											  </label>
										</div>
										</c:otherwise>
									</c:choose>
									
									<div class="col col-sm-1 col-md-3">
											 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select" id="vehicleOdometer"
											     onkeypress="return isNumberKeyWithDecimal(event,this.id);">
											    <span style="color: #2e74e6;font-size: 18px;">Vehicle odometer</span>
											  </label>
										</div>
									<div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										      <input type="hidden" id="vendorId" class="browser-default"  onchange="addNewVendor(this)" style="line-height: 30px;font-size: 15px;height: 35px;">
										    <span style="color: #2e74e6;font-size: 18px;">Vendor <abbr title="required">*</abbr></span>
										  </label>
									</div>
									
									 <div class="col col-sm-12 col-md-3" id="paymentDiv">
										  <label class="has-float-label">
										   <select id="paymentTypeId" class="browser-default custom-select">
										    	<option value="1">Cash</option>
											</select>
										    <span style="color: #2e74e6;font-size: 18px;">Payment Mode </span>
										  </label>
									  </div>
									
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
														id="invoicestartDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
												</div>
										    <span style="color: #2e74e6;font-size: 18px;">Invoice Date <abbr title="required">*</abbr></span>
										  </label>
									</div>
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" id="invoiceNumber"  >
										    <span style="color: #2e74e6;font-size: 18px;">Invoice Number </span>
										  </label>
									  </div>
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" id="jobNumber"  >
										    <span style="color: #2e74e6;font-size: 18px;">Job Number</span>
										  </label>
									  </div>
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" id="transactionNumber"  >
										    <span style="color: #2e74e6;font-size: 18px;" id="transactionId"></span>
										  </label>
									 </div>
									 <div class="row">
										<div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											    <input type="file" accept="image/png, image/jpeg, image/gif, image/pdf" name="input-file-preview" required="required" />
											    <span style="color: #2e74e6;font-size: 18px;" id="Document"></span>
											  </label>
										  </div>
										 <c:choose>
										 	<c:when test="${issueId > 0}">
										 		<div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <textarea  class="form-control browser-default custom-select noBackGround" style="width: 100%;" readOnly="readOnly" >${issueNumber}, ${issueSummary}</textarea>
												    <span style="color: #2e74e6;font-size: 18px;" id="Service Reminder">Issue Detail</span>
												  </label>
											 </div>
										 	</c:when>
										 	<c:otherwise>
										 		<div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <select class="form-control select2" multiple="multiple" id="dealerIssueId" style="width: 100%;">
													 	<option value="-1"></option>
													 </select>
												    <span style="color: #2e74e6;font-size: 18px;" id="Service Reminder">Issue Detail</span>
												  </label>
											 </div>
										 	</c:otherwise>
										 </c:choose>
										 <div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										      <input type="hidden" id="driverId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
										    <span style="color: #2e74e6;font-size: 18px;">Driver </span>
										  </label>
										</div>
										<div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										     <input type="hidden" id="assignToId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
						   						 <span style="color: #2e74e6;font-size: 18px;">Assign To </span>
										  </label>
										</div>
										<div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											    <input type="hidden" id="serviceReminderId" name="service_id" style="width: 100%;" onchange="getServiceSecheduleList(this);" />
											    <span style="color: #2e74e6;font-size: 18px;" id="Service Reminder">
											    <c:if test="${configuration.showServiceProgram}">
											    Service Program
											   	</c:if>
											    <c:if test="${configuration.showServRemindWhileCreating}">
											    Service Reminder
											   	</c:if>
											    </span>
											  </label>
										 </div>
										 <sec:authorize access="hasAuthority('DSE_METER_NOT_WORKING')">
										 <div class="col col-sm-1 col-md-3">
											 <label class="has-float-label">
											      <input type="checkbox" id="meterNotWorkingId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
											    <span style="color: #2e74e6;font-size: 18px;">Meter Not Workng </span>
											  </label>
										</div>	
										</sec:authorize>
									 </div>
									 <div class="row" id="serviceSchedules" style="display: none;">
											<table class="table" id="serviceSchedulesTable">
												<tr>
													<th>Select</th>
													<th>Service Schedule</th>
													<th>Due</th>
												</tr>
											</table>
										</div>
								</div>
							</div>
							
							<br>
							<button type="button" data-toggle="collapse" data-target="#partDiv" class="btn btn-info" style="background:lightseagreen;"><span class="fa fa-plus"></span> Add Part Details</button>
							<div class="btn-group float-right" data-toggle="buttons" id ="Group" role="group" aria-label="Basic radio toggle button group">
								  <label class="btn btn-sm btn-info btnSelect active" id="partPercentLabelId" onclick="selectDiscountTaxType(1,1);">
								    <input type="hidden" class="btn-check"  name="partDiscountTaxTypeId" id="partPercentId"  autocomplete="off" value="true"  onclick="selectDiscountTaxType(1,1);" checked > Percentage
								  </label>
								  <label class="btn btn-sm btn-info btnSelect" id="partAmountLabelId" onclick="selectDiscountTaxType(1,2);">
								    <input type="hidden" class="btn-check" name="partDiscountTaxTypeId" id="partAmountId"  value="false"  onclick="selectDiscountTaxType(1,2);" autocomplete="off"> Amount
								  </label>
								  <input type="hidden" id="finalPartDiscountTaxTypId"  value="1">
							</div>
							<br>
							<br>
							<!-- <br> -->
							<div id="partDiv"  class="box collapse" >
								<!-- <label class="has-float-label ">
								 	 <span style="color: #2e74e6;font-size: 22px;">Part Details</span>
								</label> -->
								<div class="box-body">
									<div class="row1">
											<div class="addMorePartDiv">
												
											</div>
										</div>
									<div id="basicInformationDiv">
										<div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										 <input type="hidden" name="partId" id="partId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,true,partEachCost,partDiscount,partTax);">
										    <span style="color: #2e74e6;font-size: 18px;" >Part Name </span>
										  </label>
										   <samp id="lastPartOccurred"> </samp>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="partQty"
										     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);"
										     onblur="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);">
										    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>
										  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround "  name="partEachCost" id="partEachCost" maxlength="8"
										   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);"
										   onblur="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);">
										    <span style="color: #2e74e6;font-size: 18px;" >Cost</span>
										  </label>
										  <samp id="lastPartCost"> </samp>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
										    <input type="text" class="form-control  browser-default  noBackGround allPartDiscount" name="partDiscount" id="partDiscount"
										    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1); "
										    onblur="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);" >
										    <span style="color: #2e74e6;font-size: 18px;" >Dis <em class="fa fa-percent partPercent"></em></span>
										  </label>
										   <samp id="lastPartDis"> </samp>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
										    <input type="text" class="form-control  browser-default  noBackGround allPartTax" name="partTax" id="partTax"
										    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartTaxDiscount(this.id); javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1); "
										    onblur="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);">
										    <span style="color: #2e74e6;font-size: 18px;" >Tax <em class="fa fa-percent partPercent"></em></span>
										  </label>
										  <samp id="lastPartTax"> </samp>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="partTotalCost" readonly="readonly"
										    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);"
										    onblur="javascript:sumthere('partQty', 'partEachCost', 'partDiscount', 'partTax', 'partTotalCost',1);">
										    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
										  </label>
										  <samp id="lastPartTotalCost"> </samp>
										</div>
										<div class="col col-sm-1 col-md-1">
										<!--  <a class="addMorePartButton btn btn-info "
											data-toggle="tip"
											data-original-title="Click add one more part">
											<i class="fa fa-plus"></i> </a> -->
											<button type="button"  class="btn btn-info addMorePartButton" ><span class="fa fa-plus"></span></button>
										</div>
										
									</div>
								</div>
							</div>
							
							
							<br>
							<button type="button" data-toggle="collapse" data-target="#labourDiv" class="btn btn-info" style="background:salmon;"><span class="fa fa-plus"></span> Add Labour Details</button>
							<div class="btn-group float-right" data-toggle="buttons" id ="Group" role="group" aria-label="Basic radio toggle button group">
								  <label class="btn btn-sm btn-info btnSelect active" id="labourPercentLabelId" onclick="selectDiscountTaxType(2,1);">
								    <input type="hidden" class="btn-check"  name="labourDiscountTaxTypeId" id="labourPercentId" autocomplete="off" value="true"  onclick="selectDiscountTaxType(2,1);" checked > Percentage
								  </label>
								  <label class="btn btn-sm btn-info btnSelect"id="labourAmountLabelId"  onclick="selectDiscountTaxType(2,2);">
								    <input type="hidden" class="btn-check" name="labourDiscountTaxTypeId" id="labourAmountId" value="false"  onclick="selectDiscountTaxType(2,2);" autocomplete="off"> Amount
								  </label>
								  <input type="hidden" id="finalLabourDiscountTaxTypId"  value="1">
							</div>
							<br>
							<br>	
							<!-- <br> -->
							<div id="labourDiv"  class="box collapse">
								<div class="box-body">
									<div class="row1">
										<div class="addMoreLabourDiv">
											
										</div>
									</div>
									<div id="basicInformationDiv">
										<div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										  <input type="hidden" name="labourName" id="labourName" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;" >
											    <span style="color: #2e74e6;font-size: 18px;">Labour Type </span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourWorkingHours" id="labourWorkingHours"  
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);"
											    onblur="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Hour/KM</span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourPerHourCost" id="labourPerHourCost"  maxlength="8"
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);"
											    onblur="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Rate/(Hour/KM)</span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default  noBackGround allLabourDiscount" name="labourDiscount" id="labourDiscount" 
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2); "
											    onblur="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Dis  <em class="fa fa-percent labourPercent"></em></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default  noBackGround allLabourTax"  name="labourTax" id="labourTax"  
											   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2); "
											   onblur="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Tax  <em class="fa fa-percent labourPercent"></em></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="totalLabourCost" id="totalLabourCost"  readonly="readonly"
											   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);"
											   onblur="javascript:sumthere('labourWorkingHours', 'labourPerHourCost', 'labourDiscount', 'labourTax', 'totalLabourCost',2);">
											    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-1">
											<!-- <a class="addMoreLabourButton btn btn-info "
												data-toggle="tip"
												data-original-title="Click add one more part">
												<i class="fa fa-plus"></i></a> -->
												<button type="button"  class="btn btn-info addMoreLabourButton" ><span class="fa fa-plus"></span></button>	
										</div>
										
									</div>
								</div>
							</div>
							
							<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
						
						<div class="row" >
							<button type="submit" id="submit"  class="btn btn-success" >Save DSE</button> &nbsp;&nbsp;
							<a class=" btn btn-info" href="dealerServiceEntries.in">
							<span id="Cancel">Cancel</span></a>
						</div>
					</div>
				</div>
			</div>
		</form>
		</sec:authorize>
		
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
					<%--   <c:if test="${configuration.partWarranty}"> --%>
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
					 <%--  </c:if> --%>
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
	
	<div class="modal" id="vendorModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Vendor</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<input type="hidden" id="selectedPartId">
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="modalVendorName" readonly="readonly" >
						    <span style="color: #2e74e6;font-size: 18px;">Vendor Name</span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="vendorPhoneNumber" 
						    onkeypress="return isNumberKey(event);" onblur="return isMobileNum(this);" >
						    <span style="color: #2e74e6;font-size: 18px;">Phone Number <abbr title="required">*</abbr></span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="vendorLocation"  >
						    <span style="color: #2e74e6;font-size: 18px;">Location <abbr title="required">*</abbr></span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="vendorEmail"  >
						    <span style="color: #2e74e6;font-size: 18px;">Email </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveNewVendor();">Save Vendor</button>
				</div>
			</div>
		</div>
	</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/CreateDealerServiceEntries.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>	 	
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntriesCommon.js"></script>
	<script type="text/javascript">
	setTimeout(() => {
		setAccidentDetails('${detailsDto}');
	}, 200);
	</script>
</div>