<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
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
						href="<c:url value="/vendor/${SelectType}/${SelectPage}.in"/>">Vendors</a> / <span
						id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('VIEW_VENDOR')">
						<a href="<c:url value="/PrintVendor?id=${vendor.vendorId}" />" target="_blank"
							class="btn btn-default btn-sm"><i class="fa fa-print"></i>
							Print</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_VENDOR')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/${SelectType}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"> <i
							class="fa fa-plus"></i> Edit Vendor
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="<c:url value="/vendor/${SelectType}/${SelectPage}.in"/>">Cancel</a>
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
						<div class="col-md-4">
							<h4>
								<c:if test="${!isLorryHireVendor}">
									You Have to Pay : <i class="fa fa-inr"></i>
									<c:out value="${fuelPayVendorTotal}" />
								</c:if>
								<c:if test="${isLorryHireVendor}">
									You Have to Get : <i class="fa fa-inr"></i>
									<c:out value="${amountToGet}" />
								</c:if>
							</h4>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorType}" /></a></li>
							<c:if test="${vendor.ownPetrolPump == 1}">
								<li> <a data-toggle="tip"
									data-original-title="Vendor Type "> <c:out
											value="Own Petrol Pump" /></a></li>
							</c:if>			
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No"> <c:out
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
		<c:if test="${saveVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle was successfully Created.
			</div>
		</c:if>
		<c:if test="${uploadVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor was successfully Updated.
			</div>
		</c:if>
		<c:if test="${deleteVehicle}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor was successfully Deleted.
			</div>
		</c:if>
		<c:if test="${alreadyVehicle}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor was Already created.
			</div>
		</c:if>
		<c:if test="${emptyNotRange}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Approval List Filter date Range is empty ...
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VENDOR')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<ul class="tabs">
							<li class=" current" data-tab="tab-1">Vendor Info</li>
							<li class="tab-link" data-tab="tab-2">Order History</li>
						</ul>
						<div id="tab-1" class="tab-content2 current">
							<div class="row">
								<div class="col-md-5 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Vendor Profile Information</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Vendor Name :</th>
														<td class="value"><c:out value="${vendor.vendorName}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Vendor Type :</th>
														<td class="value"><c:out value="${vendor.vendorType}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Phone :</th>
														<td class="value"><c:out
																value="${vendor.vendorPhone}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Location :</th>
														<td class="value"><c:out
																value="${vendor.vendorLocation}" /></td>
													</tr>
													<c:if test="${vendorConfig.showWebsite }">
														<tr class="row">
															<th class="key">WebSite :</th>
															<td class="value"><a href="${vendor.vendorWebsite}">
																	<c:out value="${vendor.vendorWebsite}" />
															</a></td>
														</tr>
													</c:if>
												</tbody>
											</table>
										</div>
									</div>
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Primary Contact Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Address :</th>
														<td class="value">
															<address class="no-margin">
																<c:out value="${vendor.vendorAddress1}" />
																,
																<c:out value="${vendor.vendorAddress2}" />
																<br>
																<c:out value="${vendor.vendorCity}" />
																,
																<c:out value="${vendor.vendorState}" />
																<br>
																<c:out value="${vendor.vendorCountry}" />
																-Pin :
																<c:out value="${vendor.vendorPincode}" />
															</address>
														</td>
													</tr>
													<tr class="row">
														<th class="key">Contact Person :</th>
														<td class="value">
															<div class="t-padded">
																<c:out value="${vendor.vendorFirName}" />
															</div>
															<div class="t-padded">
																<c:out value="${vendor.vendorFirPhone}" />
															</div>
															<c:if test="${vendorConfig.showWebsite }">
																<div class="t-padded">
																	<c:out value="${vendor.vendorWebsite}" />
																</div>
															</c:if>
														</td>
													</tr>
													<tr class="row">
														<th class="key">Secondary Contact Person :</th>
														<td class="value">
															<div class="t-padded">
																<c:out value="${vendor.vendorSecName}" />
															</div>
															<div class="t-padded">
																<c:out value="${vendor.vendorSecPhone}" />
															</div>
															<div class="t-padded">
																<c:out value="${vendor.vendorSecEmail}" />
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-5 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Vendor Payment Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
												<c:if test="${vendorConfig.showPaymentTerm }">
													<tr class="row">
														<th class="key">Term :</th>
														<td class="value"><c:out value="${vendor.vendorTerm}" /></td>
													</tr>
													</c:if>
													<tr class="row">
														<th class="key">PAN Card No :</th>
														<td class="value"><c:out
																value="${vendor.vendorPanNO}" /></td>
													</tr>
													<tr class="row">
														<th class="key">GST No :</th>
														<td class="value"><c:out
																value="${vendor.vendorGSTNO}" /></td>
													</tr>
													<tr class="row">
														<th class="key">GST Registered :</th>
														<c:choose>
															<c:when test="${vendor.vendorGSTRegisteredId == 1}">

																<td class="value">Turnover Below 25 lakhs</td>
															</c:when>
															<c:otherwise>
																<td class="value">Turnover Above 25 lakhs</td>

															</c:otherwise>
														</c:choose>

													</tr>
													<tr class="row">
														<th class="key">Credit Limit No :</th>
														<td class="value"><c:out
																value="${vendor.vendorCreditLimit}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Advance Paid :</th>
														<td class="value"><c:out
																value="${vendor.vendorAdvancePaid}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Vendor Bank Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Bank Name :</th>
														<td class="value"><c:out
																value="${vendor.vendorBankName}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Bank Branch :</th>
														<td class="value"><c:out
																value="${vendor.vendorBankBranch}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Bank A/c No :</th>
														<td class="value"><c:out
																value="${vendor.vendorBankAccno}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Bank IFSC No :</th>
														<td class="value"><c:out
																value="${vendor.vendorBankIfsc}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="tab-2" class="tab-content2">
						<div class="row">
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Contact Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Reference Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="VendorSideMenu.jsp"%>
				</div>
			</div>
		</sec:authorize>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${vendor.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${vendor.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${vendor.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${vendor.lastupdated}" /></small>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
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