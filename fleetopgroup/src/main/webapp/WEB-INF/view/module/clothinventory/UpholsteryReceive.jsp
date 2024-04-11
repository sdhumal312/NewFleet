<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="ClothInventory.in">New Upholstery Inventory</a>
					/ <span>Upholstery Receive Details</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/ClothInventory.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('TRANSFER_MULTI_TYRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
		</div>
		</section>
		
		<section>
			<div class="content" >
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<input type="hidden" id="startPage" value="${SelectPage}">
								<table id="VendorPaymentTable4" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>	
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
				</div>	
			</div>
			
			<div class="modal fade" id="receive" role="dialog">
			<div class="modal-dialog" style="width:750px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Receive Upholstery 
								Details</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
									
									<input type="hidden" id="receiveUpholsteryTransferId">
									<input type="hidden" id="receiveClothTypeId">
									<input type="hidden" id="receivefromLocationId">
									<input type="hidden" id="receivetoLocationId">
									<input type="hidden" id="receiveStockTypeId">
									<input type="hidden" id="receiveTransferViaId">
									<input type="hidden" id="receiveById">
								
								
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Upholstery 
											Name : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="receiveClothName" class="form-text"
													id="receiveClothNameId" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>
											
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Receive 
											Location : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="receiveToLocationName" class="form-text"
													id="receiveToLocationNameId" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Quantity 
											 : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="receiveQuantity" class="form-text"
													id="receiveQuantityId" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Receive 
										   By : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="receiveByName" class="form-text"
													id="receiveByNameId" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Description 
										    : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<textarea class="form-text" id="receiveDescriptionId" 
													name="receiveDescription" rows="3" maxlength="240">	
												</textarea>
											</div>
										  </br>		
										</div>
									</div>		
									
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save" onclick="saveReceiveUpholstery();">Receive Upholstery</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
					   </div>
				  </div>
			  </div>
		  </div>
		  
		  
			<div class="modal fade" id="reject" role="dialog">
			<div class="modal-dialog" style="width:750px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Reject Upholstery 
								Details</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
								
									<input type="hidden" id="rejectUpholsteryTransferId">
									<input type="hidden" id="rejectClothTypeId">
									<input type="hidden" id="rejectfromLocationId">
									<input type="hidden" id="rejecttoLocationId">
									<input type="hidden" id="rejectStockTypeId">
									<input type="hidden" id="rejectTransferViaId">
									<input type="hidden" id="rejectById">
								
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Upholstery 
											Name : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="rejectClothType" class="form-text"
													id="rejectClothTypeName" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>
											
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Receive 
											Location : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="rejectToLocation" class="form-text"
													id="rejectToLocationName" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Quantity 
											 : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="rejectQuantity" class="form-text"
													id="rejectQuantityId" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Receive 
										   By : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<input type="text" name="rejectBy" class="form-text"
													id="rejectByName" readonly="readonly" /> 
											</div>
										  </br>		
										</div>
									</div>	
										
									<div class="row1" class="form-group">
										<label class="L-size control-label" for="from">Description 
										    : <abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="row">
												<textarea class="form-text" id="rejectDescriptionId" 
													name="rejectDescription" rows="3" maxlength="240">	
												</textarea>
											</div>
										  </br>		
										</div>
									</div>		
									
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save" onclick="saveRejectUpholstery();">Reject Upholstery</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
					   </div>
				  </div>
			  </div>
		  </div>
			
		</section>					
	
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/UpholsteryReceive.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	