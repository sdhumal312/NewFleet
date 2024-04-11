<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendor/${vendor.vendorTypeId}/1.in"/>">Vendors</a> / <span
						id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_VENDOR')">
						<a class="btn btn-success"
							href="<c:url value="/${vendor.vendorTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=1"/>"> <i
							class="fa fa-plus"></i> Edit Vendor
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="<c:url value="/vendor/${vendor.vendorTypeId}/1.in"/>">Cancel</a>
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
								<a href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=1"/>"
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
		<c:if test="${saveVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle was successfully Created.
			</div>
		</c:if>
		<c:if test="${uploadVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle was successfully Updated.
			</div>
		</c:if>
		<c:if test="${deleteVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle was successfully Deleted.
			</div>
		</c:if>
		<c:if test="${alreadyVehicle}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle was Already created.
			</div>
		</c:if>
		<c:if test="${param.SequenceNotFound eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Sequence Not Defined Please Contact To System Administrator!
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
								href="<c:url value="/ShowVendorFuelCredit.in?vendorId=${vendor.vendorId}" /> "><i
								class="fa fa-credit-card"></i> Credit History</a> <a
								class="btn btn-default"
								href="<c:url value="/ShowVendorFuelCash.in?vendorId=${vendor.vendorId}" /> "><i
								class="fa fa-money"></i> Paid History</a>
							<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
								<a class="btn btn-success"
									href="<c:url value="/ShowApprovalList.in?vendorId=${vendor.vendorId}" /> "><i
									class="fa fa-thumbs-o-up"></i> Create Approval List </a>
							</sec:authorize>
						</div>
						<br>
						<div class="row">
							<div class="main-body">
								<h4>Recent Fuel Entries Transactions</h4>
								<div class="box">
									<div class="box-body">
										<table id="FuelTable"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th class="fit ar">ID</th>
													<th>Vehicle</th>
													<th>Group</th>
													<th class="fit ar">Ownership</th>
													<th>Date</th>
													<th class="fit ar">Closing</th>
													<th class="fit ar">Usage</th>
													<th class="fit ar">Volume</th>
													<th class="fit ar">Amount</th>
													<th class="fit ar">Reference</th>
													<th class="fit ar">Payment</th>
													<th class="fit ar">Status</th>
												</tr>
											</thead>
											<tbody id="vendorList">

												<c:if test="${!empty fuel}">
													<c:forEach items="${fuel}" var="fuel">

														<tr data-object-id="" class="ng-scope">
															<td class="fit ar"><a
																href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}" /> "
																data-toggle="tip"
																data-original-title="Click Fuel Details"><c:out
																		value="FT-${fuel.fuel_Number}" /></a></td>

															<td><a href="<c:url value="/showVehicle.in?vid=${fuel.vid}"/>"
																data-toggle="tip"
																data-original-title="Click Vehicle Details"><c:out
																		value="${fuel.vehicle_registration}" /> </a></td>

															<td><c:out value="${fuel.vehicle_group}" /></td>
															<td class="fit ar"><c:out
																	value="${fuel.vehicle_Ownership}" /></td>
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
																data-toggle="tip" data-original-title="Price"> <c:out
																		value="${fuel.fuel_price}/liters" />
															</abbr></td>
															<td><c:out value="${fuel.fuel_reference}" /></td>
															<td><c:out value="${fuel.fuel_payment}" /></td>
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
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
</div>