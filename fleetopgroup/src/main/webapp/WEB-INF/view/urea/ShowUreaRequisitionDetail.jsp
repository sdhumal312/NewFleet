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
				</div >
				<div  class="pull-right">
				<a id="editReq" class="btn btn-success btn-sm hide">
							<span class="fa fa-plus"> Edit Urea Requisition</span></a>
				<a class="btn btn-link" href="ureaRequisitionAndTransferDetails.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<input type="hidden" id="ureaRequisitionId" value="${ureaRequisitionId}">
		<input type="hidden" id="userId" value="${userId}">
		<input type="hidden" id="ureaRequisitionStatusId" >
		<input type="hidden" id="ureaInvoiceId" >
		<input type="hidden" id="ureaInvoiceToDetailsId" >
		<input type="hidden" id="ureaRequisitionSenderId" >
		<input type="hidden" id="ureaTransferId" >
		
		<input type="hidden" id="ureaTransferFromLocationId" >
		<input type="hidden" id="transferFromLocation" >
		<input type="hidden" id="ureaRequisitionReceiverId" >
		<input type="hidden" id="validateRequisitionReceiver" >
		<input type="hidden" id="validateRequisitionSender" >
		<input type="hidden" id="transferQuantity" >
		<input type="hidden" id="pendingQuantity" >
		<input type="hidden" id="totalRequiredQuantity" >
		
		
	
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
					<div class="boxinside">
						<div class="row">
							<div class="pull-left">
								<span>Requisition Id : UR-${ureaRequisitionId}</span>
							</div>
							<div class="pull-right">
								<span>Created Date : <span id="createdDate"></span></span>
							</div>
						</div>
						<div class="row">
								<h4 align="center">
									Transfer From : <span id="ureaTransferFromLocation"></span>  &nbsp;&nbsp;&nbsp; Transfer To : <span id="requiredLocation"></span>
								</h4>
							</div>
						<div class="secondary-header-title">
							<table class="table">
								<tbody>
									<tr>
										<td>Requisition Sender : <a data-toggle="tip" data-original-title="Send By"> 
											<span id="requisitionSenderName"></span></a></td>
	
										<td>Requisition Receiver :<a data-toggle="tip" data-original-title="Requited Date">
											<span id="requisitionReceiverName"></span></a></td>
									</tr>
									<tr>
										<td>Required Date : <a data-toggle="tip" data-original-title="Fixed Point"> 
											<span id="requiredDate"></span></a></td>
										<td>Required Liters : <a data-toggle="tip" data-original-title="Volume Point"> 
											<span id="requiredQuantity"></span></a></td>
									</tr>
									<tr>
										<td rowspan="2" style="width: 50%;" >Remark : <span id="requisitionRemark"></span></td>
										<td >Status : <span id="requisitionStatusName"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
<!-- 					<fieldset id="tansferDiv"> -->
<!-- 						<legend>Transfer Urea</legend> -->
<!-- 						<div class="row"> -->
<!-- 							<div class=""> -->
<!-- 								<table class="table"> -->
<!-- 									<thead> -->
<!-- 										<tr class="breadcrumb"> -->
<!-- 											<th class="fit">Transfer Id</th> -->
<!-- 											<th class="fit ar">Required Liters</th> -->
<!-- 											<th class="fit ar">transfer Liters</th> -->
<!-- 											<th class="fit ar">Pending Liters</th> -->
<!-- 											<th id="actionId" class="fit ar">Action</th> -->
<!-- 										</tr> -->
<!-- 									</thead> -->
<!-- 									<tbody id="transferTable"> -->
										
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</fieldset> -->
					
					<div class="row hide" id="showRemark">
						<label class="L-size control-label">Requisition Remark :</label>
						<div class="I-size">
							<textarea class="form-text" id="transferRemark" style="width: 100%;" rows="3" maxlength="250" ondrop="return false;"> </textarea>
						</div>
					</div>
					<br><br>
					
					<div class="row">
						<button type="submit" id="acceptReq" onclick ="return approve_Reject_Requisition(2);" class="btn btn-success hide">Accept Requisition</button>
						<button type="submit" id="rejectReq" onclick ="return addRejectReqRemark();" class="btn btn-danger hide">Reject Requisition</button>
						<button type="submit" id="receivedUrea" onclick ="return addReceivedRejectUreaRemark(4);" class="btn btn-success hide">Received Urea</button>
						<button type="submit" id="rejectUrea" onclick ="return addReceivedRejectUreaRemark(5);" class="btn btn-danger hide">Reject Urea</button>
						<button type="submit" id="transferComplete" onclick ="return completeTransfer()" class="btn btn-info hide">Transfer Complete</button>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-offset-1 col-md-9">
			<small class="text-muted"> <b>Created by :</b>  <span id="createdByName"></span></small> | 
			<small class="text-muted"><b>Created date: </b> <span id="createdDateStr"></span></small> | 
			<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedByName"></span></small> | 
			<small class="text-muted"><b>Last updated date:</b> <span id="lastUpdatedDateStr"></span></small>
		</div>
	</section>
</div>

<div class="modal fade" id="addRejctReqRemarkModal" role="dialog">
		<div class="modal-dialog " >
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="text-align: center;"> Enter Reject Remark :</h4>
					</div>
					<div class="modal-body">
					<div class="row">
						<label class="L-size control-label">Remark :</label>
						<div class="I-size">
							<textarea class="form-text" id="ureaRequisitionRejectRemark" style="width: 100%;" rows="3" maxlength="250" ondrop="return false;"> </textarea>
						</div>
					</div>
					<br><br><br>
					<div class="modal-footer">
						<button type="submit" id="saveRejectRemark"  onclick ="return approve_Reject_Requisition(3);" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addReceivedRejectUreaRemarkModal" role="dialog">
		<div class="modal-dialog " >
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="text-align: center;"> Enter Remark :</h4>
					</div>
					<div class="modal-body">
					<div class="row">
						<label class="L-size control-label"> Remark :</label>
						<div class="I-size">
							<textarea class="form-text" id="remark" style="width: 100%;" rows="3" maxlength="250" ondrop="return false;"> </textarea>
						</div>
					</div>
					<br><br><br>
					<div class="modal-footer">
					<button type="submit" id="receivedUreaRemark" onclick ="return receivedRejcetUrea(4);" class="btn btn-success hide">Received Urea</button>
					<button type="submit" id="rejectdUreaRemark" onclick ="return receivedRejcetUrea(5);" class="btn btn-danger hide">Reject Urea</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="transferUreaModal" role="dialog">
		<div class="modal-dialog " style="width:1200px;">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="text-align: center;"> Urea Transfer To : <b><span id="toLocationModal"></span> </b> &nbsp;&nbsp;&nbsp; Required Liters : <b><span id="requiredQuantityModal"></span></b> &nbsp;&nbsp;&nbsp; Pending Liters : <b><span id="pendingQuantityModal"></span></b></h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit">NO</th>
											<th class="fit">Urea Transfer Details Id</th>
											<th class="fit ar">From Location </th>
											<th class="fit ar"> Urea Invoice Number</th>
											<th class="fit ar">Transfer Liters</th>
											<th class="fit ar">Action</th>
										</tr>
									</thead>
									<tbody id="transferDetailsTable">
										
									</tbody>
								</table>
							</div>
						</div>
						<hr>
						<br>
					<div class = "hide" id="saveEntries">	
						<div class="form-group column">
							<label class="L-size control-label" > From Location :</label>
							<div class="I-size">
								<input type="hidden" id="ureaFromLocation" style="width: 100%;" 
								required="required" placeholder="Please Enter Urea Location name" />
							</div>
						</div>		
						<div class="form-group column">
							<label class="L-size control-label" > To Location :</label>
							<div class="I-size">
							<input type="hidden" id="ureaToLocationId" >
								<input type="text" id="ureaToLocation" class="form-control" style="width: 100%;" 
								readonly= "readonly" required="required" placeholder="Please Enter Urea Location name" />
							</div>
						</div>
						 <div class="form-group column">
	                           <label class="L-size control-label" >Stock Liters :</label>
							<div class="I-size">
	                             	<input type="text" class="form-control" id="ureaStockQuantity" readonly="readonly" 
	                             	onkeypress="return isNumberKeyWithDecimal(event,id);" style="width: 100%;"  placeholder="Please Enter Required Quantity" />
	                           </div>
	                      </div>
                        <div class="form-group column">
                           <label class="L-size control-label" >Transfer Liters :</label>
							<div class="I-size">
                             	<input type="number" class="form-control" id="ureaTransferQuantity" 
                             	onkeypress="return isNumberKeyWithDecimal(event,id);" style="width: 100%;"  placeholder="Please Enter Required Quantity" />
                           </div>
                        </div>
						</div>	
					</div>
					<br><br><br>
					<div class="modal-footer">
						<button type="submit" id="saveTransfer" class="btn btn-primary hide">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/ShowUreaRequisition.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>	
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>