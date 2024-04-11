<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slider.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/BatteryInventory.in"/>">Battery Inventory</a> / <span>Scrap Filter</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/BatteryInventory.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_BATTERY_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_BATTERY_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<div class="col-md-11">
						<div class="box">
							<div class="box-body">
								<form action="<c:url value="/SearchBatteryScrapFilter.in"/>"
									method="post" name="vehicleStatu">
									<div class="form-horizontal ">
										<fieldset>
											<legend>Scrap Battery Filter</legend>
											<div class="row1">
												<label class="L-size control-label">Battery Number </label>
												<div class="I-size">
													<input type="hidden" id="batteryId"
														name="batteryIds" style="width: 100%;"
														placeholder="Please Enter 2 or more Battery number" />
													<p class="help-block">Select One Or More Battery number</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Warehouse
													location : </label>
												<div class="I-size">
													<input type="text" name="wareHouseLocationId"
														id="warehouselocation" style="width: 100%;"
														placeholder="Please Enter 2 or more location Name" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label " title="required">Battery Usage (In Days) :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<br> <input type="text" value="" name="usesNoOfTimes"
														class="slider form-text" data-slider-min="0"
														data-slider-max="15000000" data-slider-step="5"
														data-slider-value="[500,1000]"
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
														<a href="<c:url value="/BatteryInventory.in"/>"
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
		$(function() {$('.slider').slider();});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/addBatteryScrap.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>