<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/TyreInventory/1.in"/>">Tyre Inventory</a> / 
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm" href="<c:url value="/TyreInventory/1.in"/>"> Cancel </a>
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
						<div class="box" id="primaryTable">
							<div class="box-body">
							<form action="<c:url value="/tyreSoldInvoice.in"/>"
									method="post" name="vehicleStatu">
								<div class="form-horizontal ">
									<fieldset>
										<legend>Sold Tyre Filter</legend>
										<div class="row1">
											<label class="L-size control-label">Sold Type </label>
											<div class="I-size">
													<select class="form-text" id="soldType" name="soldType" onchange="getTyre();">
														<option value="3">Available To Sold</option><!-- for available tyre -->
														<option value="4">Scraped To Sold</option><!-- for scraped tyre -->
													</select>
												</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Tyre Number </label>
											<div class="I-size">
												<input type="hidden" id="tyreId" name ="tyreNumber" multiple="multiple" style="width: 100%;"
													placeholder="Please Enter 2 or more Tyre number" />
											</div>
										</div>
									</fieldset>
									<fieldset class="form-actions">
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="I-size">
												<div class="pull-left">
													<button type="submit" name="commit" onclick="return validateSoldTyreFilter();"
															class="button-success pure-button">
															<span id="subm">Filter All</span>
														</button>
													<a href="<c:url value="/TyreInventory/1"/>" class="btn btn-info"> <span id="Can">Cancel</span>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/TyreInventorySoldFilter.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
		
</div>