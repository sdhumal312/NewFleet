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
					<a href="repairViewList.in">Repair Stock</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	<input type="hidden" id="repairStockId" value="${repairStockId}">
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="repairTypeId" value="${repairStock.repairTypeId}">
	<input type="hidden" id="locationName" value="${repairStock.location}">
	<input type="hidden" id="repairWorkshopId" value="${repairStock.repairWorkshopId}">
	<input type="hidden" id="fromLocationId" value="${repairStock.locationId}">
		<div class="row">
			<div class="box">
				<div class="boxinside">
					<div class="row">
						<div class="pull-left">
							<span style="font-size: 20px; font-weight: bolder;">Repair
								Stock Invoice Number : <a href="#" ><c:out value="${repairStock.repairNumberStr}"></c:out></a>
							</span>
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
									<td class="alignRight"><span class="headSpan">To Location :</span><a href="#"> <c:out value="${repairStock.additionalPartLocation}"></c:out></a></td>
									
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
								
								<tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
						<!-- <button type="button" class="btn btn-primary btn-lg btn-block" >Part Details</button> -->
		<div class="row" id="partShowDiv">
			<div class="box">
				<div class="boxinside">
					<div class="addMoreLabourDiv">
						
					</div>
						<br>
						<div class="row">
						<div class="col col-sm-12 col-md-3 partDiv"  style="display:none">
						   <label class="has-float-label">
							 <input type="hidden" name="partId" id="partId"  class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;width:100%;">
							    <span style="color: #2e74e6;font-size: 18px;" >Part Number </span>
							</label>
						</div>
						<div class="col col-sm-12 col-md-3 tyreDiv"  style="display:none">
						   <label class="has-float-label">
							 <select  name="tyreId" id="tyreId" onchange="changeTyre(this.id,dismountDate);" class="form-control" style="line-height: 30px;font-size: 15px;height: 35px;width:100%;" ></select>
							    <span style="color: #2e74e6;font-size: 18px;" >Tyre Number </span>
							</label>
						</div>
						<div class="col col-sm-12 col-md-3 batteryDiv" style="display:none">
						   <label class="has-float-label">
							 <select  name="batteryId" id="batteryId" onchange="changeBattery(this.id,dismountDate);"  class="form-control" style="line-height: 30px;font-size: 15px;height: 35px;width:100%;" ></select>
							    <span style="color: #2e74e6;font-size: 18px;" >Battery Number </span>
							</label>
						</div>
						<div class="col col-sm-12 col-md-3 dateOfRemoveDiv" >
							 <label class="has-float-label">
							    <div class="input-group input-append date" id="StartDate">
										<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="dismountDate" readonly="readonly"
											id="dismountDate" required="required"
											data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
											<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
									</div>
							    <span style="color: #2e74e6;font-size: 18px;">Date Of Remove <abbr title="required">*</abbr></span>
							  </label>
						</div>
						<!-- <div class="col col-sm-12 col-md-2">
							  <label class="has-float-label">
							   <select id="warrantyStatusId" name="warrantyStatusId" class="browser-default custom-select">
							    	<option value="0">Not Under Warranty</option>
							    	<option value="1">Under Warranty</option>
								</select>
							    <span style="color: #2e74e6;font-size: 18px;">Warranty Status </span>
							  </label>
						  </div> -->
						<div class="col col-sm-12 col-md-5">
							 <label class="has-float-label">
							    <input type="text" class="form-control browser-default custom-select noBackGround" id="workDetail" name="workDetail"  >
							    <span style="color: #2e74e6;font-size: 18px;">Issue Detail </span>
							  </label>
						  </div>
						  <div class="col col-sm-1 col-md-1">
								<button type="button"  class="btn btn-info addMoreLabourButton" ><span class="fa fa-plus"></span></button>
							</div>
						  <br>
						  <br>
					</div>
				</div>
			</div>
		</div>
		<br><br>
		<div class="row" >
			<button type="submit" onclick="saveRemark();"  class="btn btn-success" >Submit</button> &nbsp;&nbsp;
			<a class=" btn btn-info" href="repairViewList.in">
			<span id="Cancel">Cancel</span></a>
		</div>
		<br><br>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out value="${repairStock.createdBy}"></c:out>  </small>  | 
			<small class="text-muted"><b>Created date: </b> <c:out value="${repairStock.creationDate}"></c:out></small> |
			<small class="text-muted"><b>Last updated by :</b> <c:out value="${repairStock.lastModifiedBy}"></c:out></small> |
			<small class="text-muted"><b>Last updated date:</b> <c:out value="${repairStock.lastUpdatedDate}"></c:out></small>
		</div>
	</section>
</div>
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
					<!-- <div class="row">
						<label class="L-size control-label" id="Type"> Sent Date :</label>
						
					    <div class=" I-size input-group input-append date" id="StartDate1">
								<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
									id="sentDate" required="required"
									data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
									<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
							</div>
						<div class="I-size">
							<input type="hidden" id="editVehicleModelId"> 
							<input type="text" class="form-text" required="required"
								maxlength="150"  id="editVehicleModelName"
								placeholder="Enter  Name" />
						</div>
					</div> -->
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
					<button type="submit" id="submit"  class="btn btn-success" >Sent To Repair</button> &nbsp;&nbsp;
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>

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
					<!-- <div class="row">
						<label class="L-size control-label" id="Type"> Sent Date :</label>
						
					    <div class=" I-size input-group input-append date" id="StartDate1">
								<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
									id="sentDate" required="required"
									data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
									<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
							</div>
						<div class="I-size">
							<input type="hidden" id="editVehicleModelId"> 
							<input type="text" class="form-text" required="required"
								maxlength="150"  id="editVehicleModelName"
								placeholder="Enter  Name" />
						</div>
					</div> -->
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
					<button type="submit" id="submit"  class="btn btn-success" >Sent To Repair</button> &nbsp;&nbsp;
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/repair/ShowCreatedRepairInvoice.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
