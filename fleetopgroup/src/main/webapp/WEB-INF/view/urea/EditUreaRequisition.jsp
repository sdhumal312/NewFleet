<%@ include file="taglib.jsp"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/UreaInventory.in"/>">Urea Inventory</a> / 
					<a href="<c:url value="/ureaRequisitionAndTransferDetails.in"/>">Urea Requisition And Transfer Details</a> /
					<a href="<c:url value="/createUreaRequisition.in"/>">Create Urea Requisition </a>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="ureaRequisitionAndTransferDetails.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<input type="hidden" id="ureaRequisitionId" value="${ureaRequisitionId}">
	<input type="hidden" id="editUreaRequiredLocationId" >
	<input type="hidden" id="editUreaRequiredLocationName" >
	<input type="hidden" id="editUreaTransferFromLocationId" >
	<input type="hidden" id="editUreaTransferFromLocation" >
	<input type="hidden" id="editUreaRequisitionReceiverId" >
	<input type="hidden" id="editUreaRequisitionReceiverName" >
	<input type="hidden" id="editUreaRequiredLiters" >
	<input type="hidden" id="ureaRequisitionSenderId" >
	<section class="content">
					<fieldset>
						<legend>Urea Requisition Details</legend>
						<div class="box">
							<div class="box-body">
							   <input type="hidden" id="companyId" ">
								<div class="form-horizontal ">
			                         <div class="form-group column">
			                            <label class="col-md-4 control-label">Required Location <abbr title="required">*</abbr></label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
				                               <span class="input-group-addon">
				                              	 <i class="glyphicon glyphicon-cog"></i>
				                               </span>
			                          		<input type="hidden" id="ureaRequiredLocationId" style="width: 100%;" />
			                               </div>
			                            </div>
			                         </div>
			                          <div class="form-group column">
			                            <label class="col-md-4 control-label">From Location <abbr title="required">*</abbr></label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
				                               <span class="input-group-addon">
				                              	 <i class="glyphicon glyphicon-cog"></i>
				                               </span>
			                          		<input type="hidden" id="ureaTransferFromLocationId" style="width: 100%;" />
			                               </div>
			                            </div>
			                         </div>
			                         
			                         <div class="form-group column">
			                            <label class="col-md-4 control-label">Requisition Sender :</label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
			                               <span class="input-group-addon">
			                              	<i class="fa fa-user"></i>
			                               </span>
			                              	<input id="ureaRequisitionSender" class="form-control" type="text" style="width: 100%" readonly="readonly">
			                               </div>
			                            </div>
			                         </div>
			                         <div class="form-group column">
			                            <label class="col-md-4 control-label">Requisition Receiver :</label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
			                               <span class="input-group-addon">
			                              	<i class="fa fa-user"></i>
			                               </span>
			                              	<input id="ureaRequisitionReceiverId" type="hidden" style="width: 100%" placeholder="Please Enter User ">
			                               </div>
			                            </div>
			                         </div>
			                          <div class="form-group column">
			                            <label class="col-md-4 control-label">Requited Date  :</label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group input-append date" id="StartDate">
			                               <span class="input-group-addon">
			                              	 <i class="glyphicon glyphicon-calendar"></i>
			                               </span>
												<input type="text" class="form-control" style="width: 100%;" id="ureaRequiredDate" Required="required" 
													readonly="readonly" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
											</div>
			                            </div>
			                         </div>
			                          <div class="form-group column">
			                            <label class="col-md-4 control-label">Required Liters :</label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
			                               <span class="input-group-addon">
			                              	 <i class="fa fa-bus"></i>
			                               </span>
			                              	<input type="number" class="form-control" id="ureaRequiredQuantity" 
			                              	onkeypress="return isNumberKeyWithDecimal(event,id);" style="width: 100%;"  placeholder="Please Enter Required Quantity" />
			                               </div>
			                            </div>
			                         </div>
								</div>
							</div>
							<br><br>
							<div class="row">
								<label class="L-size control-label">Requisition Remark :</label>
								<div class="I-size">
									<textarea class="form-text" id="ureaRequisitionRemark" style="width: 100%;" rows="3" maxlength="250" ondrop="return false;"> </textarea>
								</div>
							</div>
							<br><br>
						</div>
					</fieldset>
					<div class="panel-footer">
						<div class="L-size"></div>
						<div class="I-size">
							<button type="submit" id="UpdateUreaRequisition" class="btn btn-success">Send Urea Requisition</button>
							<a class="btn btn-link" href="ureaRequisitionAndTransferDetails.in">Cancel</a>
						</div>
					</div>
		</section>	
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaRequisitionValidate.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaRequisitionEdit.js" />"></script>		
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>	
	
</div>