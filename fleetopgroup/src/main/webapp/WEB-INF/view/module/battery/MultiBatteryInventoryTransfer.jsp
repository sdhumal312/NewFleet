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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="BatteryInventory.in">New Battery
						Inventory</a> /
				</div>
				<div class="pull-right">
					<a href="BatteryInventory.in">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('TRANSFER_BATTERY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Already Exists
		</div>
	</c:if>
	<c:if test="${param.saveTransferInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Battery Transfer Successfully.
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('TRANSFER_BATTERY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRANSFER_BATTERY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
					<form action="saveMultiTransferBatteryInventory.in" method="post">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Transfer Inventory Battery</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Transfer From :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" name="fromLocationId"
													id="fromLocationId" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<div class="row1">
												<label class="L-size control-label">Battery Number<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="batteryId" name="batteryIds"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Battery number" />
													<p class="help-block">Select One Or More Battery number</p>
												</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer
												To:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" name="toLocationId"
													id="toLocationId" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Transfer
												Recevied By :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">

												<input class="" placeholder="Subscribe users" id="subscribe"
													type="hidden" style="width: 100%"
													name="receiveById"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> <span
													id="subscribedIcon" class=""></span>
												<div id="subscribedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer via </label>
											<div class="I-size">
												<select style="width: 100%;" name="transferViaId"
													id="Transfervia" required="required">
													<option value="1">AIR</option>
													<option value="2">COURIER</option>
													<option value="3">EXPEDITED</option>
													<option value="4">GROUND</option>
													<option value="5">NEXT DAY</option>
													<option value="6">NONE</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer Reason :</label>
											<div class="I-size">
												<textarea class="form-text" name="transferReason" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>

										<fieldset class="form-actions">
											<div class="row1">
												<div class="col-md-10 col-md-offset-2">
													<input class="btn btn-success" name="commit" type="submit"
														value="Transfer Inventory" onclick="return validateTransfer();"> <a
														href="BatteryInventory.in">Cancel</a>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript">
		$('#contactTwo').hide();
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#Transfervia").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})

			$("#subscribe").select2({
				minimumInputLength : 3,
				minimumResultsForSearch : 10,
				multiple : 0,
				ajax : {
					url : "getUserEmailId_Subscrible",
					dataType : "json",
					type : "POST",
					contentType : "application/json",
					quietMillis : 50,
					data : function(e) {
						return {
							term : e
						}
					},
					results : function(e) {
						return {
							results : $.map(e, function(e) {
								return {
									text : e.firstName + " " + e.lastName,
									slug : e.slug,
									id : e.user_id
								}
							})
						}
					}
				}
			})
		});
		
		function validateTransfer(){
			
			if(Number($("#fromLocationId").val()) == 0){
				showMessage('errors','Please select Transfer From Location!');
				return false;
			}
			
			if(Number($("#batteryId").val()) == 0){
				showMessage('errors','Please enter Battery Number!');
				return false;
			}
			
			if(Number($("#toLocationId").val()) == 0){
				showMessage('errors','Please select Transfer To Location!');
				return false;
			}
			
			if(Number($("#fromLocationId").val()) == Number($("#toLocationId").val())){
				showMessage('errors','Transfer From and Transfer To Cannot be same !');
				return false;
			}
			if(Number($("#subscribe").val()) <= 0){
				showMessage('errors','Please Select Received By  !');
				return false;
			}
			
			return true;
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#Tyre_ID").select2({
				minimumInputLength : 3, 
				minimumResultsForSearch : 10,
				multiple:!0,
				ajax : {
					url : "getBatteryForTransfer.in",
					dataType : "json",
					type : "POST",
					contentType : "application/json",
					quietMillis : 50,
					data : function(e) {
						if(Number($('#fromLocationId').val()) <= 0){
							showMessage('errors','Please select Transfer From Location !');
						}
						return {
							term : e,
							fromLocation : $("#fromLocationId").val()
						}
					},
					results : function(e) {
						return {
							results : $.map(e, function(e) {
								return {
									text : e.batterySerialNumber,
									slug : e.slug,
									id : e.batteryId
								}
							})
						}
					}
				}
			})
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/BatteryTransfer.js"></script>
</div>