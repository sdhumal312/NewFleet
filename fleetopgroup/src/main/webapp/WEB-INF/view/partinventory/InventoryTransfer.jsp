<%@ include file="taglib.jsp"%>
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
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/showInventory.in?inventory_all_id=${Inventory.inventory_all_id}"/>">Show
						Inventory</a> / <span id="NewVehi">Edit Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/NewInventory.in"/>">Cancel</a>
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="addInventory.in" class="btn btn-success"><span
							class="fa fa-plus"> Create Inventory</span></a>
					</sec:authorize>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getPartImage/${Inventory.part_photoid}.in"
							class="zoom" data-title="Part Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${Inventory.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${Inventory.partid}"
								data-toggle="tip" data-original-title="Click Part Info"> <c:out
									value="${Inventory.partnumber}" /> - <c:out
									value="${Inventory.partname}" /> - <c:out
									value="${Inventory.category}" /> - <c:out
									value="${Inventory.quantity}" /></a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Category">
										Category :</span> <span class="text-muted"><c:out
											value="${Inventory.category}" /></span></li>
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Quantity">
										Quantity :</span> <span class="text-muted"><c:out
											value="${Inventory.quantity}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
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
	<section class="content">
		<sec:authorize access="!hasAuthority('TRANSFER_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
					<form action="saveTIOPEN.in" method="post">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Transfer Inventory Parts</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Transfer From :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input type="hidden" name="transfer_inventory_id"
													value="${Inventory.inventory_id}"> <input
													type="text" class="form-text" readonly="readonly"
													name="transfer_from_location" required="required"
													value="${Inventory.location}">
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Transfer
												To:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<input type="hidden" name="INVRID" value="${REQLOC.INVRID}">
												<input type="hidden" name="INVRPARTID" value="${REQLOC.INVRPARTID}">
												<input type="text" class="form-text" readonly="readonly"
													name="transfer_to_location" required="required"
													value="${REQLOC.REQUITED_LOCATION}">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Quantity :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input type="text" class="form-text" readonly="readonly"
													name="quantity" required="required"
													value="${Inventory.quantity}">
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Transfer
												Quantity:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<input type="text" class="form-text"
													name="transfer_quantity" id="transferQuantity" 
													maxlength="10"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													placeholder="ex: 23.78" value="0">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Transfer
												Recevied By :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" placeholder="Subscribe users" type="text"
													style="width: 100%" readonly="readonly" 
													name="transfer_receivedbyEmail" value="${REQLOC.REQUITED_SENDNAME}"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;">
												<!-- <input class="" placeholder="Subscribe users" id="subscribe"
													type="hidden" style="width: 100%"
													name="transfer_receivedbyEmail"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> -->
												<span id="subscribedIcon" class=""></span>
												<div id="subscribedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer via </label>
											<div class="I-size">
												<select style="width: 100%;" name="transfer_via_ID"
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
												<textarea class="form-text" name="transfer_description"
													rows="3" maxlength="150"
													onkeypress="return IsVendorRemark(event);"
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
														value="Transfer Inventory"> <a
														class="btn btn-link" href="<c:url value="/transferInventoryReq.in?PARTID=${REQLOC.PART_ID}&REQPID=${REQLOC.INVRPARTID}"/>">Cancel</a>
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
			$("#location").select2();
			$("#Transfervia").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			});
			
			 $('#transfer_to_location').val($("#location option:selected").text());
			 $("#location").change(function() {
				 $('#transfer_to_location').val($("#location option:selected").text());
			 });
				
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
									id : e.user_email
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
</div>