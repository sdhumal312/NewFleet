<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/UreaInventory.in"/>">Urea Inventory</a> / 
					<a href="<c:url value="/ureaRequisitionAndTransferDetails.in"/>">Urea Requisition And Transfer Details</a>
				</div>
				<div class="pull-right">
				<a class="btn btn-link" href="ureaRequisitionAndTransferDetails.in">Cancel</a>
			</div>
			</div>
		</div>
	</section>
	<section class="content">
		<input type="hidden" id="ureaTransferId" value="${ureaTransferId}">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
					<div class="boxinside">
						<div class="row">
							<div class="pull-left">
								<span>Transfer Id : UT-${ureaTransferId}</span>
							</div>
							<div class="pull-right">
								<span>Created Date : <span id="createdDate"></span></span>
							</div>
						</div>
						<div class="row">
								<h4 align="center">
									<span id="transferToLocation"></span>
								</h4>
							</div>
						<div class="secondary-header-title">
							<table class="table">
								<tbody>
									<tr>
										<td> Transfer By : <a data-toggle="tip" data-original-title="Send By"> 
											<span id="transferBy"></span></a></td>
	
										<td> Transfer To:<a data-toggle="tip" data-original-title="Requited Date">
											<span id="transferTo"></span></a></td>
									</tr>
									
									<tr>
										<td>Transfer Date : <a data-toggle="tip" data-original-title="Fixed Point"> 
											<span id="transferDate"></span></a></td>
										<td>Transfer Quantity : <a data-toggle="tip" data-original-title="Volume Point"> 
											<span id="transferQuantity"></span></a></td>
									</tr>
									<tr>
										<td rowspan="2" style="width: 50%;" >Transfer Remark : <span id="transferRemark"></span></td>
										<td >Status : <span id="transferStatus"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<fieldset >
						<legend>Transfer Urea</legend>
						<div class="row">
							<div class="">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit">NO</th>
											<th class="fit">Urea Transfer Details Id</th>
											<th class="fit ar">From Location </th>
											<th class="fit ar"> Urea Invoice Number</th>
											<th class="fit ar">Transfer Quantity</th>
										</tr>
									</thead>
									<tbody id="transferDetailsTable">
										
									</tbody>
								</table>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="col-md-offset-1 col-md-9">
				<small class="text-muted"> <b>Created by :</b>  <span id="createdByName"></span></small> | 
				<small class="text-muted"><b>Created date: </b> <span id="createdDateStr"></span></small> | 
				<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedByName"></span></small> | 
				<small class="text-muted"><b>Last updated date:</b> <span id="lastUpdatedDateStr"></span></small>
			</div>
		</div>
	</section>
</div>

<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/ShowUreaTransferDetails.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>	
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>