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
					<a href="requisition">Repair Stock</a>
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
	<input type="hidden" id="locationId" value="${repairStock.locationId}">
	<input type="hidden" id="locationName" value="${repairStock.location}">
	<input type="hidden" id="partWiseLabourCofig" value="${configuration.partWiseLabour}">
	<input type="hidden" id="repairToStockDetailsId">
	<input type="hidden" id="finalPartDiscountTaxTypId">
	<input type="hidden" id="finalLabourDiscountTaxTypId">
	<input type="hidden" id="repairStatusId"> <!-- status Id of repairToStockDetails -->
	<input type="hidden" id="fromLocationId" value="${repairStock.locationId}">
	<input type="hidden" id="toLocationId" value="${repairStock.additionalPartLocationId}">
	<input type="hidden" id="partDiscountTaxTypeId" value="${repairStock.partDiscountTaxTypeId}">
	<input type="hidden" id="labourDiscountTaxTypeId" value="${repairStock.labourDiscountTaxTypeId}">
	<input type="hidden" id="hasDiscountTaxTypeId">
	<input type="hidden" id="noAdditionalPartAndLabourFound">
	<input type="hidden" id="repairAdditionalPartId">
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
							<button type="button" class="btn btn-success float-right" id="inProcess" onclick="reopenRepair();">RE-OPEN</button>
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
									<c:if test="${repairStock.repairWorkshopId == 1}">
										<th>Action</th>
									</c:if>
									
									
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
								</tr>
							</thead>
							<tbody id="defaultLabourTableBody">

							</tbody>
						</table>	
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="row" >
			<button type="submit" onclick="completeRepair();"  class="btn btn-success" >Submit</button> &nbsp;&nbsp;
			<a class=" btn btn-info" href="repairViewList.in.in">
			<span id="Cancel">Cancel</span></a>
		</div> -->
		<br><br>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span>  </small>  | 
			<small class="text-muted"><b>Created date: </b>  <span id="createdDate">   </span></small> |
			<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy">  </span></small> |
			<small class="text-muted"><b>Last updated date:</b>  <span id="lastUpdatedDate">  </span></small>
		</div>
	</section>
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
						</tr>
					</thead>
					<tbody id="assetRepairTable">
					
					</tbody>

					</table>
				</div>
					
					<br />
				</div>
				<div class="modal-footer">
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
					
					<br />
				</div>
				<div class="modal-footer">
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/repair/ShowCompletedRepairInvoice.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
