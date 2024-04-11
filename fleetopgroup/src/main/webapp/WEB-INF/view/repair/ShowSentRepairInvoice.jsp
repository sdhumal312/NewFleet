<%@ include file="../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/modalAnimation/animateModal.css" />">
<style>
@media (min-width: 768px) {
  .modal-xl {
    width: 90%;
   max-width:1200px;
  }
}
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
					<a href="repairViewList.in">Repair Stock</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	<input type="hidden" id="repairStockId" value="${repairStockId}">
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
	<input type="hidden" id="repairTypeId" value="${repairStock.repairTypeId}">
	<input type="hidden" id="repairWorkshopId" value="${repairStock.repairWorkshopId}">
	<input type="hidden" id="fromLocationId" value="${repairStock.locationId}">
	<input type="hidden" id="fromLocation" value="${repairStock.location}">
	<input type="hidden" id="locationId" value="${repairStock.additionalPartLocationId}">
	<input type="hidden" id="locationName" value="${repairStock.additionalPartLocation}">
	<input type="hidden" id="partWiseLabourCofig" value="${configuration.partWiseLabour}">
	<input type="hidden" id="repairToStockDetailsId">
	<input type="hidden" id="repairAdditionalPartId">
	<input type="hidden" id="finalPartDiscountTaxTypId">
	<input type="hidden" id="finalLabourDiscountTaxTypId">
	<input type="hidden" id="repairToStockPartId">
	<input type="hidden" id="repairStatusId"> <!-- status Id of repairToStockDetails -->
	<input type="hidden" id="fromLocationId" value="${repairStock.locationId}">
	<input type="hidden" id="toLocationId" value="${repairStock.additionalPartLocationId}">
	<input type="hidden" id="partDiscountTaxTypeId" value="${repairStock.partDiscountTaxTypeId}">
	<input type="hidden" id="labourDiscountTaxTypeId" value="${repairStock.labourDiscountTaxTypeId}">
	<input type="hidden" id="hasDiscountTaxTypeId">
	<input type="hidden" id="noAdditionalPartAndLabourFound">
	<input type="hidden" id="inventoryLocationId">
	<input type="hidden" id="inventoryLocationPartId">
	<input type="hidden" id="additionalPartQuantity" > 
	<input type="hidden" id="savedAdditionalAssetQuantity" > 
	
		<div class="row">
			<div class="box">
				<div class="boxinside">
					<div class="row">
						<div class="pull-left">
							<span style="font-size: 20px; font-weight: bolder;">Repair
								Stock Invoice Number : <a href="#" ><c:out value="${repairStock.repairNumberStr}"></c:out></a>
							
							</span>
						</div>
						<div class="col-md-12">
							<button type="button" class="btn btn-success float-right" id="inProcess" onclick="moveToCreatedRepairInvoice();">RE-ENTER</button>
						</div>
					</div>
					<br>
					<div class="secondary-header-title">
						<table class="table">
							<tbody>
								<tr>
									<td><span class="headSpan">Open Date :</span> <a href="#"> <c:out value="${repairStock.openDateStr}"></c:out></a></td>

									<td class="alignRight"><span class="headSpan">Required Date :</span><a href="#">  <c:out value="${repairStock.requiredDateStr}"></c:out></a></td>
								</tr>
								<tr>
									<td><span class="headSpan">Repair Type :</span> <a href="#"><c:out value="${repairStock.repairType}"></c:out></a></td>

									<td class="alignRight"><span class="headSpan">Repair Status :</span><a href="#"> <c:out value="${repairStock.repairStatus}"></c:out></a></td>
								</tr>
								<tr>
									<td><span class="headSpan">Repair Workshop :</span> <a href="#"> <c:out value="${repairStock.repairWorkshop}"></c:out></a></td>

									<td class="alignRight"><span class="headSpan">Reference Number :</span><a href="#"> <c:out value="${repairStock.refNumber}"></c:out></a></td>
								</tr>
								<tr>
									<td class="alignRight"><span class="headSpan">From Location :</span><a href="#"> <c:out value="${repairStock.location}"></c:out></a></td>
									<td class="alignRight"><span class="headSpan">Additional Part Location :</span><a href="#"> <c:out value="${repairStock.additionalPartLocation}"></c:out></a></td>
									
								</tr>
								<tr>
									<td><span class="headSpan">Description :</span> <a href="#"> <c:out value="${repairStock.description}"></c:out></a></td>
									<c:choose>
										<c:when test="${repairStock.repairWorkshopId == 1}">
											<td class="alignRight"></td>
										</c:when>
										<c:otherwise>
											<td class="alignRight"><span class="headSpan">Vendor :</span><a href="#"> <c:out value="${repairStock.vendorName}"></c:out></a></td>
										</c:otherwise>
									</c:choose>
								</tr>
								<c:choose>
								<c:when test="${repairStock.repairWorkshopId == 2}">
								<tr>
									<td><span class="headSpan">Part Discount/Tax Type :</span> <a href="#"> <c:out value="${repairStock.partDiscountTaxType}"></c:out></a></td>

									<td class="alignRight"><span class="headSpan">Labour Discount/Tax Type:</span><a href="#"> <c:out value="${repairStock.labourDiscountTaxType}"></c:out></a></td>
								</tr>
								</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="box">
				<div class="boxinside">
					<div class="table-responsive">
						<button type="button" class="btn btn-primary btn-lg btn-block partDiv" style="display:none">Part Details</button>
						<button type="button" class="btn btn-primary btn-lg btn-block tyreDiv" style="display:none">Tyre Details</button>
						<button type="button" class="btn btn-primary btn-lg btn-block batteryDiv" style="display:none">Battery Details</button>
						<br>
						<table class="table table-hover table-bordered styleTable" >
							<thead>
								<tr >
									<th>SR NO</th>
									<th class="partDiv" style="display:none">Part Name/Number</th>
									<th class="tyreDiv" style="display:none">Tyre Number</th>
									<th class="batteryDiv" style="display:none">Battery Number</th>
									<th>Stock Status</th>
									<th>Work Details</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="sentRepairtockTableBody">

							</tbody>
						</table>
						
					</div>
				</div>
			</div>
		</div>
		<br><br>
		
		<c:if test="${!configuration.partWiseLabour}">
		<div class="row">
			<div class="box">
				<div class="boxinside">
					<div class="table-responsive">
						<button type="button" class="btn btn-primary btn-lg btn-block partDiv" >Labour Details</button>
						<br>
						<c:choose>
						<c:when test="${repairStock.repairWorkshopId == 1}">
						<div class="box-body">
							<div id="moreOwnLabour">
								
							</div>
							<div id="basicInformationDiv">
								<div class="col col-sm-1 col-md-8">
									 <label class="has-float-label">
									 <input type="hidden" name="labourName" id="ownLabourId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,true,partEachCost,partDiscount,partTax);">
									    <span style="color: #2e74e6;font-size: 18px;" >Labour Name </span>
									  </label>
									   <samp id="lastPartOccurred"> </samp>
								</div>
								<div class="col col-sm-1 col-md-2">
								 <label class="has-float-label">
								    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourHour" id="ownLabourHour"
								     onkeypress="return isNumberKeyWithDecimal(event,this.id);" >
								    <span style="color: #2e74e6;font-size: 18px;" >Hour</span>
								  </label>
								</div>
								<div class="col col-sm-1 col-md-2">
										<button type="button"  class="btn btn-info moreOwnLabourFieldButton" ><span class="fa fa-plus"></span></button>	
								</div>
								
							</div>
						</div>
						</c:when>
						<c:otherwise>
						<div class="box-body">
							<div id="moreDealerLabour">
								
							</div>
							<div id="basicInformationDiv" >
								<div class="col col-sm-1 col-md-3">
									 <label class="has-float-label">
									 <input type="hidden" name="dealerLabourId" id="dealerLabourId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;width:100%;">
									    <span style="color: #2e74e6;font-size: 18px;" >Labour Name </span>
									  </label>
									   <samp id="lastPartOccurred"> </samp>
								</div>
								<div class="col col-sm-1 col-md-2">
								 <label class="has-float-label">
								    <input type="text" class="form-control browser-default custom-select noBackGround" name="dealerLabourHour" id="dealerLabourHour"
								    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										    onblur="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Hour</span>
								  </label>
								</div>
								<div class="col col-sm-1 col-md-2">
									 <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround" name="dealerLabourPerHourCost" id="dealerLabourPerHourCost"  maxlength="8"
										    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										    onblur="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
										    <span style="color: #2e74e6;font-size: 18px;">Rate/(Hour/KM)</span>
										  </label>
									</div>
									<div class="col col-sm-1 col-md-1">
									 <label class="has-float-label">
										    <input type="text" class="form-control browser-default  noBackGround allLabourDiscount" name="dealerLabourDiscount" id="dealerLabourDiscount" 
										   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id);  javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										    onblur="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
										    <span style="color: #2e74e6;font-size: 18px;">Dis  <em class="fa fa-percent labourPercent"></em></span>
										  </label>
									</div>
									<div class="col col-sm-1 col-md-1">
									 <label class="has-float-label">
										    <input type="text" class="form-control browser-default  noBackGround allLabourTax"  name="dealerLabourTax" id="dealerLabourTax"  
										  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id);  javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										    onblur="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
										    <span style="color: #2e74e6;font-size: 18px;">Tax  <em class="fa fa-percent labourPercent"></em></span>
										  </label>
									</div>
									<div class="col col-sm-1 col-md-2">
									 <label class="has-float-label">
										    <input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="dealerTotalLabourCost" id="dealerTotalLabourCost"  readonly="readonly"
										  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										    onblur="javascript:sumthere('dealerLabourWorkingHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
										    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
										  </label>
									</div>
								<div class="col col-sm-1 col-md-1">
										<button type="button"  class="btn btn-info moreDealerLabourFieldButton" ><span class="fa fa-plus"></span></button>	
								</div>
								
							</div>
						</div>
						</c:otherwise>
						</c:choose>
						
						<div class="modal-footer">
							<button type="button" id="addLabourDetails" class="btn btn-primary" onclick="addLabourDetails();">Save Labour</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		</c:if>
		<div class="row">
			<div class="box">
				<div class="boxinside">
					<div class="table-responsive">
						<button id="additionalParLabourId" type="button" class="btn btn-primary btn-lg btn-block " style="display:none" >Additional Part-Labour Details</button>
						<button id="additionalParId" type="button" class="btn btn-primary btn-lg btn-block " style="display:none" >Additional Part Details</button>
						<br>
						<table class="table table-hover table-bordered styleTable">	
							
							<tbody id="inprocessPartBody">

							</tbody>
						</table>	
					</div>
				</div>
			</div>
		</div>
		<div id ="defaultLabour" class="row" style="display:none">
			<div class="box">
				<div class="boxinside">
					<div class="table-responsive">
						<button  type="button" class="btn btn-primary btn-lg btn-block partDiv"  > Show Labour Details</button>
						<br>
						<table class="table table-hover table-bordered styleTable">	
							<thead>
								<tr >
									<th>SR NO</th>
									<th>Labour Name</th>
									<th>Labour Hour</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="defaultLabourTableBody">

							</tbody>
						</table>	
					</div>
				</div>
			</div>
		</div>
		<div class="row" >
			<button type="submit" onclick="completeRepairModal();"  class="btn btn-success" >Submit</button> &nbsp;&nbsp;
			<a class=" btn btn-info" href="repairViewList.in">
			<span id="Cancel">Cancel</span></a>
		</div>
		<br><br>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span>  </small>  | 
			<small class="text-muted"><b>Created date: </b>  <span id="createdDate">   </span></small> |
			<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy">  </span></small> |
			<small class="text-muted"><b>Last updated date:</b>  <span id="lastUpdatedDate">  </span></small>
		</div>
	</section>
</div>
<c:choose>
	<c:when test="${repairStock.repairWorkshopId == 1}">
<div class="modal" id="ownPartModal" tabindex="-1">
	<div class="modal-dialog modal-lg"  style="width:1250px;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Part Details</h5>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col col-sm-1 col-md-8">
						 <label class="has-float-label">
						 <input type="hidden" name="partName" id="ownPartId" class="browser-default partId">
						    <span style="color: #2e74e6;font-size: 18px;" >Part Name </span>
						  </label>
						   <samp id="lastPartOccurred"> </samp>
					</div>
					<div class="col col-sm-1 col-md-2">
					 <label class="has-float-label">
					    <input type="text" class="form-control browser-default custom-select noBackGround" 
					    	name="partQty" id="partQty"  onkeypress="return isNumberKeyWithDecimal(event,this.id);">
					    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>
					  </label>
					</div>
				</div>
				<div id="moreOwnParts"></div>
			</div>
			<div class="col col-sm-1 col-md-2">
				<button type="button" id="add_more" class="moreOwnPartFieldButton btn btn-success"> <em class="fa fa-plus"></em></button>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" onclick="addPartDetails();">Save Part</button>
			</div>
		</div>
	</div>
</div>
</c:when>
<c:otherwise>
<div class="modal" id="dealerPartModal">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Part Details</h5>
				<c:choose>
					<c:when test="${repairStock.partDiscountTaxTypeId == 0 }">
						<div class="btn-group float-right" data-toggle="buttons" id ="partDiscountTaxButton" role="group" aria-label="Basic radio toggle button group">
							  <label class="btn btn-sm btn-info btnSelect active" id="partPercentLabelId" onclick="selectDiscountTaxType(1,1);">
							    <input type="hidden" class="btn-check"  name="partDiscountTaxTypeId" id="partPercentId"  autocomplete="off" value="true"  onclick="selectDiscountTaxType(1,1);" checked > Percentage
							  </label>
							  <label class="btn btn-sm btn-info btnSelect" id="partAmountLabelId" onclick="selectDiscountTaxType(1,2);">
							    <input type="hidden" class="btn-check" name="partDiscountTaxTypeId" id="partAmountId"  value="false"  onclick="selectDiscountTaxType(1,2);" autocomplete="off"> Amount
							  </label>	
						</div>	
					</c:when>
				</c:choose>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col col-sm-1 col-md-3">
						 <label class="has-float-label">
							 <input type="hidden" name="partName" id="dealerPartId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;width:100%" >
						    	<span style="color: #2e74e6;font-size: 18px;" >Part Name </span>
						  </label>
					</div>
					<div class="col col-sm-1 col-md-2">
					 <label class="has-float-label">
					    <input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="dealerPartQty"
					     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);"
					     onblur="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);">
					    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>
					  </label>
					</div>
					<div class="col col-sm-1 col-md-2">
					 <label class="has-float-label">
					    <input type="text" class="form-control browser-default custom-select noBackGround "  name="partEachCost" id="dealerPartEachCost" maxlength="8"
					    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);"
					     onblur="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);">
					    <span style="color: #2e74e6;font-size: 18px;" >Cost</span>
					  </label>
					  <samp id="lastPartCost"> </samp>
					</div>
					<div class="col col-sm-1 col-md-1">
					 <label class="has-float-label">
					    <input type="text" class="form-control  browser-default  noBackGround allPartDiscount" name="partDiscount" id="dealerPartDiscount"
					      onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validatePartTaxDiscount(this.id);  javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);"
					     onblur="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);">
					    <span style="color: #2e74e6;font-size: 18px;" >Dis<span class="partPercentAmountId"></span></span>
					  </label>
					   <samp id="lastPartDis"> </samp>
					</div>
					<div class="col col-sm-1 col-md-1">
					 <label class="has-float-label">
					    <input type="text" class="form-control  browser-default  noBackGround allPartTax" name="partGst" id="dealerPartTax"
					    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validatePartTaxDiscount(this.id);  javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);"
					     onblur="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);">
					    <span style="color: #2e74e6;font-size: 18px;" >Tax<span class="partPercentAmountId"></span></span>
					  </label>
					  <samp id="lastPartTax"> </samp>
					</div>
					<div class="col col-sm-1 col-md-2">
					 <label class="has-float-label">
					    <input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="dealerPartTotalCost" readonly="readonly"
					    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup=" validatePartTaxDiscount(this.id);  javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);"
					     onblur="javascript:sumthere('dealerPartQty', 'dealerPartEachCost', 'dealerPartDiscount', 'dealerPartTax', 'dealerPartTotalCost',1);">
					    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
					  </label>
					  <samp id="lastPartTotalCost"> </samp>
					</div>
				</div>
				<div id="moreDealerParts"></div>
			</div>
			<div class="col col-sm-1 col-md-2">
			<button type="button" id="add_more" class="moreDealerPartFieldButton btn btn-success"> <em class="fa fa-plus"></em></button>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary" onclick="addPartDetails();">Save Part</button>
		</div>
		</div>
	</div>
</div>
</c:otherwise>
</c:choose>

	<!-- ********************* End Part Modal ******************* -->
	
	<!-- *************** Labour modal ********************* -->


<c:if test="${configuration.partWiseLabour}">
	<c:choose>
		<c:when test="${repairStock.repairWorkshopId == 1}">
			<div class="modal" id="ownLabourModal" tabindex="-1">
				<div class="modal-dialog modal-lg" style="width: 1250px;">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Labour Details</h5>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col col-sm-1 col-md-8">
									<label class="has-float-label"> <input type="hidden"
										name="labourName" id="ownLabourId"
										class="browser-default partId"
										style="line-height: 30px; font-size: 15px; height: 35px;"
										onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,true,partEachCost,partDiscount,partTax);">
										<span style="color: #2e74e6; font-size: 18px;">Labour
											Name </span>
									</label>
									<samp id="lastPartOccurred"> </samp>
								</div>
								<div class="col col-sm-1 col-md-2">
									<label class="has-float-label"> <input type="text"
										class="form-control browser-default custom-select noBackGround"
										name="labourHour" id="ownLabourHour"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);">
										<span style="color: #2e74e6; font-size: 18px;">Hour</span>
									</label>
								</div>

							</div>
							<div id="moreOwnLabour"></div>
							<br>

						</div>
						<div class="col col-sm-1 col-md-2">
							<button type="button" id="add_more"
								class="moreOwnLabourFieldButton btn btn-success">
								<i class="fa fa-plus"></i>
							</button>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="button" id="addLabourDetails"
								class="btn btn-primary" onclick="addLabourDetails();">Save
								Labour</button>
						</div>
					</div>
				</div>
			</div>

		</c:when>
		<c:otherwise>
			<div class="modal" id="dealerLabourModal" tabindex="-1">
				<div class="modal-dialog modal-xl" >
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Labour Details</h5>
							<c:choose>
								<c:when test="${repairStock.labourDiscountTaxTypeId == 0 }">
									<div class="btn-group float-right" data-toggle="buttons" id ="labourDiscountTaxButton" role="group" aria-label="Basic radio toggle button group">
									  <label class="btn btn-sm btn-info btnSelect active" id="labourPercentLabelId" onclick="selectDiscountTaxType(2,1);">
									    <input type="hidden" class="btn-check"  name="labourDiscountTaxTypeId" id="labourPercentId"  autocomplete="off" value="true"  onclick="selectDiscountTaxType(2,1);" checked > Percentage
									  </label>
									  <label class="btn btn-sm btn-info btnSelect" id="labourAmountLabelId" onclick="selectDiscountTaxType(2,2);">
									    <input type="hidden" class="btn-check" name="labourDiscountTaxTypeId" id="labourAmountId"  value="false"  onclick="selectDiscountTaxType(2,2);" autocomplete="off"> Amount
									  </label>
									</div>		
								</c:when>
							</c:choose>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col col-sm-1 col-md-3">
									 <label class="has-float-label">
										 <input type="hidden" name="labourName" id="dealerLabourId" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;width:100%" >
									    	<span style="color: #2e74e6;font-size: 18px;" >Labour Name </span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-2">
								 <label class="has-float-label">
								    <input type="text" class="form-control browser-default custom-select noBackGround" name="labourHour" id="dealerLabourHour"
								    onkeyup="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										onblur="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Hour</span>
								  </label>
								</div>
								<div class="col col-sm-1 col-md-2">
								 <label class="has-float-label">
								    <input type="text" class="form-control browser-default custom-select noBackGround "  name="labourUnitCost" id="dealerLabourPerHourCost" maxlength="8"
								    onkeyup="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										onblur="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Rate</span>
								  </label>
								  <samp id="lastPartCost"> </samp>
								</div>
								<div class="col col-sm-1 col-md-1">
								 <label class="has-float-label">
								    <input type="text" class="form-control  browser-default  noBackGround allPartDiscount" name="labourDiscount" id="dealerLabourDiscount"
								     onkeyup="  validatePartTaxDiscount(this.id);  javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										onblur="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Dis<span class="labourPercentAmountId"></span></span>
								  </label>
								   <samp id="lastPartDis"> </samp>
								</div>
								<div class="col col-sm-1 col-md-1">
								 <label class="has-float-label">
								    <input type="text" class="form-control  browser-default  noBackGround allPartTax" name="labourTax" id="dealerLabourTax"
								  onkeyup="  validatePartTaxDiscount(this.id);  javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										onblur="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Tax<span class="labourPercentAmountId"></span></span>
								  </label>
								  <samp id="lastPartTax"> </samp>
								</div>
								<div class="col col-sm-1 col-md-2">
								 <label class="has-float-label">
								    <input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="labourTotalCost" id="dealerTotalLabourCost" readonly="readonly"
								    onkeyup="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);"
										onblur="javascript:sumthere('dealerLabourHour', 'dealerLabourPerHourCost', 'dealerLabourDiscount', 'dealerLabourTax', 'dealerTotalLabourCost',2);">
								    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>
								  </label>
								</div>
							</div>
							<div id="moreDealerLabour"></div>
						</div>
						<div class="col col-sm-1 col-md-2">
							<button type="button"
								class="moreDealerLabourFieldButton btn btn-success">
								<em class="fa fa-plus"></em>
							</button>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="button" id="addLabourDetails"
								class="btn btn-primary" onclick="addLabourDetails();">Save
								Labour</button>
						</div>
					</div>
				</div>
			</div>


			<!-- ********************* End Labour Modal ******************* -->
		</c:otherwise>
	</c:choose>
</c:if>
<div class="modal fade" id="remarkModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<label class="L-size control-label" id="Type"> Completed Date :</label>
						
					    <div class=" I-size input-group input-append date" id="StartDate1">
								<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
									id="completedDate" required="required"
									data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
									<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
							</div>
						<!-- <div class="I-size">
							<input type="hidden" id="editVehicleModelId"> 
							<input type="text" class="form-text" required="required"
								maxlength="150"  id="editVehicleModelName"
								placeholder="Enter  Name" />
						</div> -->
					</div>
					<br>
					<div class="row">
						<label class="L-size control-label" id="Type">Remark :</label>
						<div class="I-size">
							<input type="text" class="form-control browser-default custom-select noBackGround" id="remark"
								maxlength="249" name="description"
								placeholder="Enter description" />
						</div>
					</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="submit" id="submit"  class="btn btn-success" onclick="completeRepair();"  >Complete Repair</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="rejectModal" role="dialog">
	<input type="hidden" id="repairStockPartId">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<c:if test="${repairStock.repairTypeId != 1 }">
					<div class="row">
						<label class="L-size control-label" id="Type"> Stock Move To :</label>
						
					    <div class=" I-size" >
								<select id="rejectStockMoveTo" class="form-control">
								<c:choose>
									<c:when test="${repairStock.repairTypeId == 2 }">
										<option value="1" >Retread</option>
										<option value="2">Scraped</option>
									</c:when>
									<c:when test="${repairStock.repairTypeId == 3 }">
										<option value="2" >Scraped</option>
									</c:when>
								</c:choose>
								</select>
							</div>
					</div>
				</c:if>	
					<br>
					<div class="row">
						<label class="L-size control-label" id="Type">Remark :</label>
						<div class="I-size">
							<input type="text" class="form-control browser-default custom-select noBackGround" id="rejectRemark"
								maxlength="249" name="description"
								placeholder="Enter description" />
						</div>
					</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="submit" id="submit"  class="btn btn-success" onclick="saveRejectStock();"  >Reject</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="assetModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<br>
					<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Asset Number</th>
							<th class="fit ar">Action</th>
						</tr>
					</thead>
					<tbody id="assetRepairTable">
					
					</tbody>

					</table>
				</div>
					<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Asset Number</th>
							<th class="fit ar">Select</th>
						</tr>
					</thead>
					<tbody id="assetTable">
					
					</tbody>

					</table>
				</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="submit" onclick="sentAssetNumber();" class="btn btn-success" >Sent To Repair</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>	
	
	
	<div class="modal fade" id="additionalAssetModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<br>
					<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Asset Number</th>
							<th class="fit ar">Action</th>
						</tr>
					</thead>
					<tbody id="additionalPartAssetRepairTable">
					
					</tbody>

					</table>
				</div>
					<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Asset Number</th>
							<th class="fit ar">Select</th>
						</tr>
					</thead>
					<tbody id="additionalPartAssetTable">
					
					</tbody>

					</table>
				</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="submit" onclick="saveAdditionalAsset();" class="btn btn-success" >Sent To Repair</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/repair/ShowSentRepairInvoice.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
