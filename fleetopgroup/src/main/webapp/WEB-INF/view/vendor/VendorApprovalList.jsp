<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendor/1.in"/>">Vendors</a> / <a
						href="ShowVendor.in?vendorId=${vendor.vendorId}"> <c:out
							value="${vendor.vendorName}" />
					</a> / <span id="NewVehi">Filter Vendor Approval List</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="ShowVendor.in?vendorId=${vendor.vendorId}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a href="ShowVendor.in?vendorId=${vendor.vendorId}"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorType}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>Service GST NO : <a data-toggle="tip"
								data-original-title="GST NO"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>
							<li>GST Registered : <a data-toggle="tip"
								data-original-title="GST NO"> <c:choose>
										<c:when test="${vendor.vendorGSTRegisteredId == 1}">

																Turnover Below 25 lakhs GST
															</c:when>
										<c:otherwise>
																Turnover Above 25 lakhs GST

															</c:otherwise>
									</c:choose></a></li>
						</ul>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<c:if test="${param.saveapproval eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${saveapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${param.deleteapproval eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Canceled successfully .
			</div>
		</c:if>
		<script type="text/javascript" src="<c:url value="/resources/js/tabsShowVehicle.js" />"></script>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
				<div class="col-md-11">
					<div class="main-body">
						<div class="row">
							<a class="btn btn-default"
								href="ShowVendorFuelCredit.in?vendorId=${vendor.vendorId}"><i
								class="fa fa-credit-card"></i> Credit History</a> <a
								class="btn btn-default"
								href="ShowVendorFuelCash.in?vendorId=${vendor.vendorId}"> <i
								class="fa fa-money"></i> Paid History
							</a>
							<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
								<a class="btn btn-success"
									href="ShowApprovalList.in?vendorId=${vendor.vendorId}"><i
									class="fa fa-thumbs-o-up"></i> Create Approval List </a>
							</sec:authorize>
						</div>
						<br>
						<div class="row">
							<div class="main-body">
								<h4>Create Approval List</h4>
								<div class="row">
									<div class="col-md-offset-1 col-md-9">
										<c:choose>
											<c:when test="${vendor.vendorType == 'FUEL-VENDOR'}">
												<!-- Your Authorize Access -->
												<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
													<div class="row">
														<div class="col-md-12">
															<div class="box box-success">
																<div class="box-header">
																	<h3 class="box-title">Create Fuel Approval List</h3>
																</div>
																<div class="box-body no-padding">
																	<c:if test="${!configuration.directPayment}">
																		<form
																			action="<c:url value="/FilterApprovalList.in "/>"
																			method="POST">
																	</c:if>
																	<c:if test="${configuration.directPayment}">
																		<form
																			action="<c:url value="/FilterFuelApprovalList.in "/>"
																			method="POST">
																	</c:if>
																	<div class="form-horizontal ">
																		<div class="row1">
																			<input type="hidden" class="form-text"
																				name="vendorId" value="${vendor.vendorId}"
																				readonly="readonly"> <input type="hidden"
																				class="form-text" name="vendor_name"
																				value="${vendor.vendorName}" readonly="readonly">

																		</div>
																		<div class="row1">
																			<label class="L-size control-label">Vehicle
																				Group :</label>
																			<div class="I-size">
																				<input type="hidden" id="SelectVehicleGroupService"
																					name="vehicleGroupId" style="width: 100%;"
																					placeholder="Please Enter 2 or more Group Name" />
																			</div>
																		</div>
																		<div class="row1">
																			<label class="L-size control-label">Vehicle
																				Ownership :</label>
																			<div class="I-size">
																				<select class="form-text select2"
																					name="vehicle_OwnershipId" style="width: 100%;">
																					<option value="0"></option>
																					<option value="1">Owned</option>
																					<option value="2">Leased</option>
																					<option value="3">Rented</option>
																					<option value="4">Attached</option>
																					<option value="5">Customer</option>

																				</select>
																			</div>
																		</div>
																		<!-- Date range -->
																		<div class="row1">
																			<label class="L-size control-label">Date
																				range: <abbr title="required">*</abbr>
																			</label>
																			<div class="I-size">
																				<div class="input-group">
																					<div class="input-group-addon">
																						<i class="fa fa-calendar"></i>
																					</div>
																					<input type="text" id="reportrange"
																						class="form-text" name="fuelRange_daterange"
																						required="required"
																						style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
																				</div>
																			</div>
																		</div>
																		<div class="row1">
																			<label class="L-size control-label"></label>
																			<div class="I-size">
																				<button type="submit" name="Filter"
																					class="btn btn-success">
																					<i class="fa fa-search"> Find Fuel</i>
																				</button>
																				<a href="ShowVendor.in?vendorId=${vendor.vendorId}"
																					class="btn btn-default"> Cancel</a>
																			</div>
																		</div>
																	</div>
																	</form>
																</div>
															</div>
														</div>
													</div>
												</sec:authorize>
											</c:when>
											<c:otherwise>
												<!-- Your Authorize Access -->
												<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
													<div class="row">
														<div class="col-md-12">
															<div class="box box-success">
																<div class="box-header">
																	<h3 class="box-title">Create Approval List</h3>
																</div>
																<div class="box-body no-padding">
																	<c:if test="${!configuration.directPayment}">
																		<form
																			action="<c:url value="/FilterAllApprovalList.in "/>"
																			method="POST">
																	</c:if>
																	<c:if test="${configuration.directPayment}">
																		<form
																			action="<c:url value="/FilterAllApprovalPaymentList.in "/>"
																			method="POST">
																	</c:if>
																	<div class="form-horizontal ">
																			<div class="row1">
																				<input type="hidden" class="form-text"
																					name="vendorId" value="${vendor.vendorId}"
																					readonly="readonly"> <input type="hidden"
																					class="form-text" name="vendor_name"
																					value="${vendor.vendorName}" readonly="readonly">
																			</div>
																			<div class="row1">
																				<label class="L-size control-label"> Search
																					:<abbr title="required">*</abbr>
																				</label>
																				<div class="I-size">
																					<select class="form-text select2" name="AllSEARCH"
																						style="width: 100%;">
																						<option value="-1">ALL</option>
																						<option value="2">Service Entries</option>
																						<option value="3">Purchase Order</option>
																						<option value="4">Tyre Inventory Entries</option>
																						<option value="5">Tyre Retread Bill Entries</option>
																						<option value="6">Battery Inventory Entries</option>
																						<option value="7">Part Inventory Entries</option>
																						<option value="8">Upholstery Inventory Entries</option>
																					</select>
																				</div>
																			</div>
																			<!-- Date range -->
																			<div class="row1">
																				<label class="L-size control-label">Date
																					range: <abbr title="required">*</abbr>
																				</label>
																				<div class="I-size">
																					<div class="input-group">
																						<div class="input-group-addon">
																							<i class="fa fa-calendar"></i>
																						</div>
																						<input type="text" id="reportrange"
																							class="form-text" name="fuelRange_daterange"
																							required="required"
																							style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
																					</div>
																				</div>
																			</div>
																			<div class="row1">
																				<label class="L-size control-label"></label>
																				<div class="I-size">
																					<button type="submit" name="Filter"
																						class="btn btn-success">
																						<i class="fa fa-search"> Find Entries</i>
																					</button>
																					<a href="ShowVendor.in?vendorId=${vendor.vendorId}"
																						class="btn btn-default"> Cancel</a>
																				</div>
																			</div>
																		</div>
																	</form>
																</div>
															</div>
														</div>
													</div>
													<div style="height: 400px"></div>
												</sec:authorize>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div style="height: 400px"></div>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovalValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>