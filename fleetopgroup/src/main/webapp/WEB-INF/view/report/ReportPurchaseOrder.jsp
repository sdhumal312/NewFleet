<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newPurchaseOrders/1.in"/>">Purchase
						Orders</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchPurchaseOrder.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_id"
									name="Search" type="text" required="required"
									placeholder="Po-NO, Quote, Indent,Inv, WO-No"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Purchase Order Search</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="ReportPurchaseOrder.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Quote Number </label>
													<div class="I-size">
														<input class="string required form-text"
															id="purchaseorder_quotenumber"
															name="purchaseorder_quotenumber" type="text">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Part/Service
														Vendor :<abbr title="required">*</abbr>
													</label>
													<div class="I-size" id="vendorSelect">
														<input type="hidden" id="selectVendor"
															name="purchaseorder_vendor_name" style="width: 100%;"
															placeholder="Please Select Vendor Name" /> <label
															class="error" id="errorVendorSelect"> </label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Buyer </label>
													<div class="I-size">
														<input class="string required form-text"
															id="purchaseorder_buyer" name="purchaseorder_buyer"
															type="text">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" for="priority">Terms
													</label>
													<div class="I-size">
														<div class="col-md-9">
															<select style="width: 100%;" name="purchaseorder_termsId"
																id="terms">
																<option value=""></option>
																<option value="9">C.O.D</option>
																<option value="1">CASH</option>
																<option value="2">CREDIT</option>
																<option value="3">NET30</option>
															</select>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Ship via </label>
													<div class="I-size">
														<div class="col-md-9">
															<select style="width: 100%;" name="purchaseorder_shipviaId"
																id="shipvia">
																<option value="-1"></option>
																<option value="1">AIR</option>
																<option value="2">COURIER</option>
																<option value="3">EXPEDITED</option>
																<option value="4">GROUND</option>
																<option value="5">NEXT DAY</option>
																<option value="6">NONE</option>
															</select>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Ship to </label>
													<div class="I-size">
														<input type="hidden" id="Reportpartlocation"
															name="purchaseorder_shiplocation_id" style="width: 100%;"
															placeholder="Please Select Location Name" /> <label
															class="error" id="errorVendorSelect"> </label>

													</div>
												</div>
												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label"> Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="Purchase_daterange" required="required" readonly="readonly"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																onclick="this.style.visibility = 'hidden'" name="commit"
																type="submit" value="Search All"> <a
																href="newPurchaseOrders/1.in" class="btn btn-info"> <span
																id="Can">Cancel</span>
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
					</fieldset>
					<div style="height: 200px"></div>
				</div>
			</div>
		</sec:authorize>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#terms, #shipvia , #dateRange").select2({
				placeholder : "Please Enter"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersValidate.js"/>"></script>
	<script>
		$(function() {
			function e(e, t) {
				$("#reportrange").val(
						e.format("DD-MM-YYYY") + " to "
								+ t.format("DD-MM-YYYY"))
			}
			e(moment().subtract(1, "days"), moment()), $("#reportrange")
					.daterangepicker(
							{
								maxDate: new Date(),
								format : 'DD-MM-YYYY',
								ranges : {
									Today : [ moment(), moment() ],
									Yesterday : [ moment().subtract(1, "days"),
											moment().subtract(1, "days") ],
									"Last 7 Days" : [
											moment().subtract(6, "days"),
											moment() ]
								}
							}, e)
		})
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
</div>