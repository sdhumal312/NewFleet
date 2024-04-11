<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendor/${SelectType}/${SelectPage}.in"/>">Vendors</a> / <a
						href="<c:url value="/${SelectType}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"> <c:out
							value="${vendor.vendorName}" />
					</a> / <span id="NewVehi">Show Vendor Approval</span>
				</div>
				<div class="pull-right">

					<a class="btn btn-link"
						href="ShowApprovalList.in?vendorId=${vendor.vendorId}">Cancel</a>
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
								<a href="<c:url value="/${SelectType}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
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
		<c:if test="${deleteapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Canceled successfully .
			</div>
		</c:if>
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
							<a class="col-md-offset-3 btn btn-info"
								data-toggle="control-sidebar"><i class="fa fa-search">
									Filter</i></a>
						</div>
						<br>
						<div class="row">
							<div class="main-body">
								<h4>Create Approval List</h4>
								<div class="box">
									<div class="box-body">
										<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
											<spring:message code="message.unauth"></spring:message>
										</sec:authorize>
										<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
											<form id="frm-example" action="ApprovalList.in" method="POST">
												<input type="hidden" name="vendorId"
													value="${vendor.vendorId}">

												<table id="VendorApplovalList"
													class="table table-bordered table-striped">
													<thead>
														<tr>
															<th class="fit"><input name="select_all" value="1"
																id="example-select-all" type="checkbox" /></th>
															<th>ID</th>
															<th>Vehicle</th>
															<th>Group</th>
															<!-- <th class="fit ar">Ownership</th> -->
															<th>Date</th>
															<th class="fit ar">Closing</th>
															<th class="fit ar">Usage</th>
															<th class="fit ar">Volume</th>
															<th class="fit ar">Amount</th>
															<th class="fit ar">Reference</th>
															<!-- <th class="fit ar">Payment</th> -->
															<th class="fit ar">Doc</th>
															<th class="fit ar">Status</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th class="fit"></th>
															<th>ID</th>
															<th>Vehicle</th>
															<th>Group</th>
															<!-- <th class="fit ar">Ownership</th> -->
															<th>Date</th>
															<th class="fit ar">Closing</th>
															<th class="fit ar">Usage</th>
															<th class="fit ar">Volume</th>
															<th class="fit ar">Amount</th>
															<th class="fit ar">Reference</th>
															<!-- <th class="fit ar">Payment</th> -->
															<th class="fit ar">Doc</th>
															<th class="fit ar">Status</th>
														</tr>
													</tfoot>
													<tbody id="vendorList">
														<c:if test="${!empty fuel}">
															<c:forEach items="${fuel}" var="fuel">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit"><c:if
																			test="${fuel.fuel_vendor_paymode != 'APPROVED'}">
																			<input name="Selectfuel_id" value="${fuel.fuel_id}"
																				id="example" type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank" href="showFuel.in?FID=${fuel.fuel_id}"
																		data-toggle="tip"
																		data-original-title="Click Fuel Details"><c:out
																				value="FT-${fuel.fuel_Number}" /> </a></td>
																	<td><a target="_blank"  href="showVehicle?vid=${fuel.vid}"
																		data-toggle="tip"
																		data-original-title="Click Fuel Details"><c:out
																				value="${fuel.vehicle_registration}" /> </a></td>
																	<td class="fit ar"><c:out
																			value="${fuel.vehicle_group}" /></td>
																	<%-- <td class="fit ar"><c:out
																			value="${fuel.vehicle_Ownership}" /></td> --%>
																	<td><c:out value="${fuel.fuel_date}" /></td>
																	<td class="fit ar"><c:out
																			value="${fuel.fuel_meter} km" /></td>
																	<td class="fit ar"><c:out
																			value="${fuel.fuel_usage} km" /></td>
																	<td class="fit ar"><abbr data-toggle="tip"
																		data-original-title="Liters"><c:out
																				value="${fuel.fuel_liters}" /></abbr> <c:if
																			test="${fuel.fuel_tank_partial==1}">
																			<abbr data-toggle="tip"
																				data-original-title="Partial fuel-up"> <i
																				class="fa fa-adjust"></i>
																			</abbr>
																		</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${fuel.fuel_amount}" /> <br> <abbr
																		data-toggle="tip" data-original-title="Price">
																			<c:out value="${fuel.fuel_price}/liters" />
																	</abbr></td>
																	<td><a><c:out value="${fuel.fuel_reference}" /></a></td>
																	<%-- <td><c:out value="${fuel.fuel_payment}" /></td> --%>
																	
																	<td>
																		<c:choose>
																			<c:when test="${fuel.fuel_document_id != null && fuel.fuel_document_id > 0}">
																				<a target='_blank' href="${pageContext.request.contextPath}/download/FuelDocument/${fuel.fuel_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</c:when>
																		</c:choose>
																	</td>
																	<td><c:choose>
																			<c:when test="${fuel.fuel_vendor_paymode == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${fuel.fuel_vendor_paymode}" /></span>
																			</c:when>
																			<c:when
																				test="${fuel.fuel_vendor_paymode == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${fuel.fuel_vendor_paymode}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${fuel.fuel_vendor_paymode}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
													</tbody>
												</table>
												<div align="center">
													<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
														<button class="btn btn-success" data-toggle="tip"
															data-original-title="Please Select Any One"
															data-toggle="modal" data-target="#processing-modal">Create</button>
													</sec:authorize>
												</div>
											</form>
										</sec:authorize>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
</div>
<aside class="control-sidebar control-sidebar-dark"
	style="padding-top: 100px;">
	<div class="row">
		<div class="box">
			<div class="box-header">
				<h4>Search</h4>
			</div>
			<div class="box-body" style="padding: 20px;">
				<form action="FilterApprovalList.in" method="POST">
					<div class="form-horizontal ">
						<div class="row1">
							<input type ="hidden" value="${invoiceId}">
							<input type="hidden" class="form-text" name="vendorId"
								value="${vendor.vendorId}" readonly="readonly"> <input
								type="hidden" class="form-text" name="vendor_name"
								value="${vendor.vendorName}" readonly="readonly">
						</div>
						<div class="row1">
							<label class="control-label">Vehicle Group :</label>
							<div class="">
								<input type="hidden" id="SelectVehicleGroupService"
									name="vehicleGroupId" style="width: 100%;"
									placeholder="Please Enter 2 or more Group Name" />
							</div>
						</div>
						<div class="row1">
							<label class="control-label">Vehicle Ownership :</label>
							<div class="">
								<select class=" select2" name="vehicle_OwnershipId"
									style="width: 100%;">
									<option value="0"></option>
									<option value="1">Owned</option>
									<option value="2">Leased</option>
									<option value="3">Rented</option>
									<option value="4">Attached</option>
									<option value="5">Customer</option>
								</select>
							</div>
						</div>
						<div class="row1">
							<label class=" control-label">Date range: <abbr
								title="required">*</abbr>
							</label>
							<div class="">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="reportrange" class="form-text"
										name="fuelRange_daterange" required="required"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
							</div>
						</div>
						<div class="row1">
							<label class="L-size control-label"></label>
							<div class="I-size">
								<button type="submit" name="Filter" class="btn btn-success">
									<i class="fa fa-search"> Filter</i>
								</button>
								<a href="<c:url value="/${SelectType}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
									class="btn btn-default"> Cancel</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</aside>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleOldlanguage.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovalValidate.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>