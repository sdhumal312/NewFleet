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
						href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
						Inventory</a> /
					<c:out value="${Tyre.TYRE_IN_NUMBER}" />
				</div>
				<div class="pull-right">
					<a href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/resources/images/TyreWheel.png"
							class="zoom" data-title="Tyre Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/resources/images/TyreWheel.png"
							class="img-rounded" alt="Tyre Photo" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">
								<c:choose>
									<c:when test="${Tyre.TYRE_RETREAD_COUNT == 0}">
										<span class="label label-pill label-success"><c:out
												value="NT" /></span>
									</c:when>
									<c:otherwise>
										<span class="label label-pill label-warning"><c:out
												value="${Tyre.TYRE_RETREAD_COUNT}-RT" /></span>
									</c:otherwise>
								</c:choose> <c:out value=" ${Tyre.TYRE_NUMBER}" /> - <c:out
									value="${Tyre.TYRE_MANUFACTURER}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><c:choose>
										<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'OPEN'}">
											<small class="label label-info"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'REJECT'}">
											<small class="label label-danger"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'RESOLVED'}">
											<small class="label label-warning"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:otherwise>
											<small class="label label-success"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:otherwise>
									</c:choose></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="TYRE MANUFACTURER">
								</span> <span class="text-muted"><c:out
											value="${Tyre.TYRE_MANUFACTURER}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="category"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_MODEL}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Tyre Size"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_SIZE}" /></span></li>
							</ul>
						</div>
					</div>
				</div>
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
	<section class="content">
		<sec:authorize access="!hasAuthority('TRANSFER_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
					<form action="saveTITYREOPEN.in" method="post">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Transfer Inventory Tyre</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Transfer From :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input id="TYRE_ID" type="hidden" name="TYRE_ID" value="${Tyre.TYRE_ID}">
												<input type="text" class="form-text" readonly="readonly"
													name="TRA_FROM_LOCATION" required="required"
													value="${Tyre.WAREHOUSE_LOCATION}">
												<input type="hidden" name="TRA_FROM_LOCATION_ID" id="TRA_FROM_LOCATION_ID" value="${Tyre.WAREHOUSE_LOCATION_ID}" />	
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Transfer
												To:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<select class="select2" name="TRA_TO_LOCATION_ID"
													required="required" id="location" style="width: 100%;">
													<c:forEach items="${PartLocations}" var="PartLocations">
														<c:if
															test="${Tyre.WAREHOUSE_LOCATION_ID != PartLocations.partlocation_id}">
															<option value="${PartLocations.partlocation_id}"><c:out
																	value="${PartLocations.partlocation_name}" />
															</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Quantity :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input type="text" class="form-text" readonly="readonly"
													name="quantity" required="required" value="1">
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Transfer
												Quantity:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<input type="number" class="form-text" name="TRA_QUANTITY"
													min="0.0" max="1" maxlength="10" placeholder="ex: 23"
													value="0">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Transfer
												Recevied By :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">

												<input class="" placeholder="Subscribe users" id="subscribe"
													type="hidden" style="width: 100%"
													name="TRANSFER_RECEIVEDBY_ID"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> <span
													id="subscribedIcon" class=""></span>
												<div id="subscribedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer via </label>
											<div class="I-size">
												<select style="width: 100%;" name="TRANSFER_VIA_ID"
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
												<textarea class="form-text" name="TRANSFER_REASON" rows="3"
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
														value="Transfer Inventory"> <a
														href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">Cancel</a>
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
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>