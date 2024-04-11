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
					<a class="btn btn-link" href="dealerServiceEntries.in">
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
					<input type="hidden" id="dealerServiceEntriesId" value="${dealerServiceEntriesId}">
					<input type="hidden" id="id" value="${dealerServiceEntriesId}">
					<input type="hidden" id="editPaymentTypeId" >
					<input type="hidden" id="paymentType" >
					<input type="hidden" id="issueId" >
					<input type="hidden" id="serviceReminderIds" >
					<input type="hidden" id="vehicle_ExpectedOdameter" /> 
					<input type="hidden" id="editOdometer" /> 
					<input type="hidden" id="vehicle_Odameter" />
					<input type="hidden" id="backDateMaxOdo" />
					<input type="hidden" id="backDateMinOdo" />
					<input type="hidden" id="editDSE" value="true" />
					<input type="hidden" id="fromEdit" value="true" />
					<input type="hidden" id="validateOdometer" value="${configuration.validateOdometer}">
					<input type="hidden" id="showServiceProgram" value="${configuration.showServiceProgram}">
					<input type="hidden" id="showServRemindWhileCreating" value="${configuration.showServRemindWhileCreating}">
					<div class="tab-content">
							<div class="box" >
								<div class="box-body">
									 <label class="has-float-label ">
									  <span style="color: #2e74e6;font-size: 22px;">Dealer Service Entries Details [<span id="dealerServiceEntriesNumber"></span>]</span>
									</label>
									<br>
									<div id="basicInformationDiv" >
										<div class="col col-sm-1 col-md-3" id="issueVidDiv">
											 <label class="has-float-label">
											     <input type="text" id="issueVehicleNumber" class="browser-default form-control custom-select" style="line-height: 30px;font-size: 15px;height: 35px;" readonly="readonly">
											    <span style="color: #2e74e6;font-size: 18px;">Vehicle <abbr title="required">*</abbr></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-3" id="vidDiv">
											 <label class="has-float-label">
											     <input type="hidden" id="vehicleId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
											    <span style="color: #2e74e6;font-size: 18px;">Vehicle <abbr title="required">*</abbr></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												    <input type="text" class="form-control browser-default custom-select" id="vehicleOdometer"  
												     onkeypress="return isNumberKeyWithDecimal(event,this.id);">
												    <span style="color: #2e74e6;font-size: 18px;">Vehicle odometer</span>
												  </label>
											</div>
										<div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												      <input type="hidden" id="vendorId" class="browser-default" onchange="addNewVendor(this)"  style="line-height: 30px;font-size: 15px;height: 35px;">
												    <span style="color: #2e74e6;font-size: 18px;">Vendor <abbr title="required">*</abbr></span>
												  </label>
											</div>
									</div>
									
									<div id="packingDetailsDiv">
											 <div class="col col-sm-12 col-md-3" id="paymentDiv">
												  <label class="has-float-label">
												   <select id="paymentTypeId" class="browser-default custom-select">
													</select>
												    <span style="color: #2e74e6;font-size: 18px;">Payment Mode </span>
												  </label>
											  </div>
									</div>
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-control  browser-default custom-select noBackGround	" name="invoiceDate" readonly="readonly"
														id="invoicestartDate" required="required" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<button  class=" input-group-addon add-on btn btn-sm"><i class="fa fa-calendar"></i></button>
												</div>
										    <span style="color: #2e74e6;font-size: 18px;">Invoice Date <abbr title="required">*</abbr></span>
										  </label>
									  </div>
									<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" id="invoiceNumber"  >
										    <span style="color: #2e74e6;font-size: 18px;">Invoice Number</span>
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
										  <div class="col col-sm-12 col-md-3" id="editIssueDiv">
											  <label class="has-float-label">
											    <textarea  class="form-control browser-default custom-select noBackGround" id="issueDetail" style="width: 100%;" readOnly="readOnly" ></textarea>
											    <span style="color: #2e74e6;font-size: 18px;" id="Service Reminder">Issue Detail</span>
											  </label>
										 </div>
										 <div class="col col-sm-1 col-md-3">
											 <label class="has-float-label">
											      <input type="hidden" id="driverId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
											    <span style="color: #2e74e6;font-size: 18px;">Driver</span>
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
							<c:if test="${!empty dealerServiceEntriesPartList}">
								<div id="partDiv" class="box">
										<div class="btn-group float-right" data-toggle="buttons"
											id="Group" role="group"
											aria-label="Basic radio toggle button group">
											<label class="btn btn-sm btn-info btnSelect "
												id="partPercentLabelId"
												onclick="selectDiscountTaxType(1,1);"> <input
												type="hidden" class="btn-check" name="partDiscountTaxTypeId"
												id="partPercentId" autocomplete="off" value="true"
												onclick="selectDiscountTaxType(1,1);" checked>
												Percentage
											</label> <label class="btn btn-sm btn-info btnSelect"
												id="partAmountLabelId" onclick="selectDiscountTaxType(1,2);">
												<input type="hidden" class="btn-check"
												name="partDiscountTaxTypeId" id="partAmountId" value="false"
												onclick="selectDiscountTaxType(1,2);" autocomplete="off">
												Amount
											</label> <input type="hidden" id="finalPartDiscountTaxTypId">
										</div>
									<c:forEach items="${dealerServiceEntriesPartList}" var="dsePartList">
									<div class="row">
									<div class="box-body">
											<div id="basicInformationDiv">
												<div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												  <input type= "hidden" name="DealerServiceEntriesPartId" id="DealerServiceEntriesPartId" value="${dsePartList.dealerServiceEntriesPartId}" >
												 <input type="hidden" name="partId" id="partId${dsePartList.dealerServiceEntriesPartId}" class="browser-default partId" value="${dsePartList.partId}" 
 														 style="line-height: 30px;font-size: 15px;height: 35px;" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred${dsePartList.dealerServiceEntriesPartId},lastPartCost${dsePartList.dealerServiceEntriesPartId},lastPartDis${dsePartList.dealerServiceEntriesPartId},lastPartTax${dsePartList.dealerServiceEntriesPartId},true,'partEachCost${dsePartList.dealerServiceEntriesPartId}','partDiscount${dsePartList.dealerServiceEntriesPartId}','partTax${dsePartList.dealerServiceEntriesPartId}');checkWarrantyPart(${dsePartList.dealerServiceEntriesPartId});" >
												    <span style="color: #2e74e6;font-size: 18px;" >Part Name </span>
												  </label>
												   <samp id="lastPartOccurred${dsePartList.dealerServiceEntriesPartId}"> </samp>
												</div>
												<div class="col col-sm-1 col-md-2">
												 <label class="has-float-label">
												    <input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="partQty${dsePartList.dealerServiceEntriesPartId}"  value="${dsePartList.partQuantity}"
												     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty${dsePartList.dealerServiceEntriesPartId}', 'partEachCost${dsePartList.dealerServiceEntriesPartId}', 'partDiscount${dsePartList.dealerServiceEntriesPartId}', 'partTax${dsePartList.dealerServiceEntriesPartId}', 'partTotalCost${dsePartList.dealerServiceEntriesPartId}',1);">
												    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>
												  </label>
												</div>
												<div class="col col-sm-1 col-md-2">
												 <label class="has-float-label">
												    <input type="text" class="form-control browser-default custom-select noBackGround" name="partEachCost" id="partEachCost${dsePartList.dealerServiceEntriesPartId}"  value="${dsePartList.partEchCost}" maxlength="8"
												   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty${dsePartList.dealerServiceEntriesPartId}', 'partEachCost${dsePartList.dealerServiceEntriesPartId}', 'partDiscount${dsePartList.dealerServiceEntriesPartId}', 'partTax${dsePartList.dealerServiceEntriesPartId}', 'partTotalCost${dsePartList.dealerServiceEntriesPartId}',1);">
												    <span style="color: #2e74e6;font-size: 18px;" >Cost</span>
												  </label>
												   <samp id="lastPartCost${dsePartList.dealerServiceEntriesPartId}"> </samp>
												</div>
												<div class="col col-sm-1 col-md-1">
												 <label class="has-float-label">
												    <input type="text" class="form-control  browser-default  noBackGround allPartDiscount" name="partDiscount" id="partDiscount${dsePartList.dealerServiceEntriesPartId}" value="${dsePartList.partDiscount}"
												    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validatePartTaxDiscount(this.id); javascript:sumthere('partQty${dsePartList.dealerServiceEntriesPartId}', 'partEachCost${dsePartList.dealerServiceEntriesPartId}', 'partDiscount${dsePartList.dealerServiceEntriesPartId}', 'partTax${dsePartList.dealerServiceEntriesPartId}', 'partTotalCost${dsePartList.dealerServiceEntriesPartId}',1);">
												    <span style="color: #2e74e6;font-size: 18px;" >Dis<em class="fa fa-percent partPercent"></em></span>
												  </label>
												   <samp id="lastPartDis${dsePartList.dealerServiceEntriesPartId}"> </samp>
												</div>
												<div class="col col-sm-1 col-md-1">
												 <label class="has-float-label">
												    <input type="text" class="form-control  browser-default  noBackGround allPartTax" name="partTax" id="partTax${dsePartList.dealerServiceEntriesPartId}" value="${dsePartList.partTax}"
												    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validatePartTaxDiscount(this.id); javascript:sumthere('partQty${dsePartList.dealerServiceEntriesPartId}', 'partEachCost${dsePartList.dealerServiceEntriesPartId}', 'partDiscount${dsePartList.dealerServiceEntriesPartId}', 'partTax${dsePartList.dealerServiceEntriesPartId}', 'partTotalCost${dsePartList.dealerServiceEntriesPartId}',1);">
												    <span style="color: #2e74e6;font-size: 18px;" >Tax<em class="fa fa-percent partPercent"></em></span>
												  </label>
												   <samp id="lastPartTax${dsePartList.dealerServiceEntriesPartId}"> </samp>
												</div>
												<div class="col col-sm-1 col-md-2">
												 <label class="has-float-label">
												    <input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="partTotalCost${dsePartList.dealerServiceEntriesPartId}" readonly="readonly" value="${dsePartList.partTotalCost}"
												    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('partQty${dsePartList.dealerServiceEntriesPartId}', 'partEachCost${dsePartList.dealerServiceEntriesPartId}', 'partDiscount${dsePartList.dealerServiceEntriesPartId}', 'partTax${dsePartList.dealerServiceEntriesPartId}', 'partTotalCost${dsePartList.dealerServiceEntriesPartId}',1);">
												    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
												  </label>
												</div>
												<div class="col col-sm-1 col-md-1">
													<label class="has-float-label" style="color: #2e74e6;font-size: 18px;" id="warrantyStatus${dsePartList.dealerServiceEntriesPartId}" ></label> 
												</div>
											</div>
											
									</div>
									</div>
										</c:forEach>	
								</div>
							</c:if>
							<br>
							<c:if test="${!empty dealerServiceEntriesIdLabourList}">
							<div id="labourDiv" class="box">
								<div class="box-body">
										<div class="btn-group float-right" data-toggle="buttons" id ="Group" role="group" aria-label="Basic radio toggle button group">
											  <label class="btn btn-sm btn-info btnSelect " id="labourPercentLabelId" onclick="selectDiscountTaxType(2,1);">
											    <input type="hidden" class="btn-check"  name="labourDiscountTaxTypeId" id="labourPercentId" autocomplete="off" value="true"  onclick="selectDiscountTaxType(2,1);" checked > Percentage
											  </label>
											  <label class="btn btn-sm btn-info btnSelect"id="labourAmountLabelId"  onclick="selectDiscountTaxType(2,2);">
											    <input type="hidden" class="btn-check" name="labourDiscountTaxTypeId" id="labourAmountId" value="false"  onclick="selectDiscountTaxType(2,2);" autocomplete="off"> Amount
											  </label>
											  <input type="hidden" id="finalLabourDiscountTaxTypId" >
										</div>
									
								<c:forEach items="${dealerServiceEntriesIdLabourList}" var="dseLabourList">
								<div class="row">
									<div id="basicInformationDiv">
										<div class="col col-sm-1 col-md-3">
										
										 <label class="has-float-label">
											    <input type= "hidden" name="dealerServiceEntriesLabourId" id="dealerServiceEntriesLabourId" value="${dseLabourList.dealerServiceEntriesLabourId}" >
											    <input type= "hidden" name="labourName" id="labourName${dseLabourList.dealerServiceEntriesLabourId}" style="line-height: 30px;font-size: 15px;height: 35px;">
											    <span style="color: #2e74e6;font-size: 18px;">Labour Type </span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourWorkingHours" id="labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}"  value="${dseLabourList.labourWorkingHours}"
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}', 'labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}', 'labourDiscount${dseLabourList.dealerServiceEntriesLabourId}', 'labourTax${dseLabourList.dealerServiceEntriesLabourId}', 'totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Hour/KM</span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourPerHourCost" id="labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}"  value="${dseLabourList.labourPerHourCost}" maxlength="8"
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);"  onkeyup="javascript:sumthere('labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}', 'labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}', 'labourDiscount${dseLabourList.dealerServiceEntriesLabourId}', 'labourTax${dseLabourList.dealerServiceEntriesLabourId}', 'totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}',2);">
											    <span style="color: #2e74e6;font-size: 18px;">Rate/(Hour/KM)</span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default  noBackGround allLabourDiscount" name="labourDiscount" id="labourDiscount${dseLabourList.dealerServiceEntriesLabourId}" value="${dseLabourList.labourDiscount}"
											    onkeypress="return isNumberKeyWithDecimal(event,this.id);"  onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}', 'labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}', 'labourDiscount${dseLabourList.dealerServiceEntriesLabourId}', 'labourTax${dseLabourList.dealerServiceEntriesLabourId}', 'totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}',2); ">
											    <span style="color: #2e74e6;font-size: 18px;">Dis<em class="fa fa-percent labourPercent"></em></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-1">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default  noBackGround allLabourTax"  name="labourTax" id="labourTax${dseLabourList.dealerServiceEntriesLabourId}"  value="${dseLabourList.labourTax}"
											   onkeypress="return isNumberKeyWithDecimal(event,this.id);"  onkeyup=" validateLabourTaxDiscount(this.id); javascript:sumthere('labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}', 'labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}', 'labourDiscount${dseLabourList.dealerServiceEntriesLabourId}', 'labourTax${dseLabourList.dealerServiceEntriesLabourId}', 'totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}',2); ">
											    <span style="color: #2e74e6;font-size: 18px;">Tax<em class="fa fa-percent labourPercent"></em></span>
											  </label>
										</div>
										<div class="col col-sm-1 col-md-2">
										 <label class="has-float-label">
											    <input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="totalLabourCost" id="totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}"  readonly="readonly" value="${dseLabourList.labourTotalCost}"
											   onkeypress="return isNumberKeyWithDecimal(event,this.id);"  onkeyup="javascript:sumthere('labourWorkingHours${dseLabourList.dealerServiceEntriesLabourId}', 'labourPerHourCost${dseLabourList.dealerServiceEntriesLabourId}', 'labourDiscount${dseLabourList.dealerServiceEntriesLabourId}', 'labourTax${dseLabourList.dealerServiceEntriesLabourId}', 'totalLabourCost${dseLabourList.dealerServiceEntriesLabourId}',2);">
											    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
											  </label>
										</div>
									</div>
									</div>
									</c:forEach>
								</div>
							</div>
							</c:if>
						<div class="row" >
								<button type="submit" id="submit"  class="btn btn-success" >Update DSE</button> &nbsp;&nbsp;
								<a class=" btn btn-info" href="dealerServiceEntries.in">
								<span id="Cancel">Cancel</span></a>
						</div>
					</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
		</form>
		</sec:authorize>
	</section>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/EditDealerServiceEntries.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>	 	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntriesCommon.js"></script> 
</div>