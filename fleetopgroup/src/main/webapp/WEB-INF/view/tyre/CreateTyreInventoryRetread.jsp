
<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slider.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1.in"/>">Tyre Inventory</a> / <a
						href="<c:url value="/InventoryTyreReport.in"/>">Search
						Inventory</a> / <span>Retread Filter</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/TyreInventory/1.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<div class="col-md-11">
						<div class="box">
							<div class="box-body">
								<form action="<c:url value="/SearchRetreadFilter.in"/>"
									method="post" name="vehicleStatu">
									<div class="form-horizontal ">
										<fieldset>
											<legend>Retread Tyre Filter</legend>
											<div class="row1">
												<label class="L-size control-label">Tyre Number </label>
												<div class="I-size">
													<input type="hidden" id="Tyre_ID" name="TYRE_MUITPLE"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Tyre number" />
													<p class="help-block">Select One Or More Tyre number</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre
													Manufacturer : </label>
												<div class="I-size">
													<input type="text" id="manufacurer"
														name="TYRE_MANUFACTURER_ID" style="width: 100%;"
														placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Model : </label>
												<div class="I-size">
													<input type="text" id="tyremodel" name="TYRE_MODEL_ID"
														style="width: 100%;"
														placeholder="Please select Tyre Model" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Size : </label>
												<div class="I-size">
													<input type="text" id="tyreSize" name="TYRE_SIZE_ID"
														style="width: 100%;" placeholder="Please select Tyre Size" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Warehouse
													location : </label>
												<div class="I-size">
													<input type="text" name="WAREHOUSE_LOCATION_ID"
														id="warehouselocation" style="width: 100%;"
														placeholder="Please Enter 2 or more location Name" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label " title="required">Tyre
													Usage :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<br> <input type="text" value="" name="TYRE_USAGE"
														class="slider form-text" data-slider-min="0"
														data-slider-max="150000000" data-slider-step="5"
														data-slider-value="[75000,100000]"
														data-slider-orientation="horizontal"
														data-slider-selection="before" data-slider-tooltip="show"
														data-slider-id="red">
												</div>
											</div>
										</fieldset>
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="button-success pure-button">
															<span id="subm">Filter All</span>
														</button>
														<a href="<c:url value="/TyreInventory/1"/>"
															class="btn btn-info"> <span id="Can">Cancel</span>
														</a>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<!-- Bootstrap slider -->
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap-slider.js" />"></script>
	<script>
		$(function() {
			$('.slider').slider();
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/ReportInventoryTyre.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>