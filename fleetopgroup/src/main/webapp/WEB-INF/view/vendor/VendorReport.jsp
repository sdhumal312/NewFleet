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
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <a
						href="<c:url value="/VendorReport.in"/>">Vendors Report</a> / <span>
						Search Report</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VENDOR')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="VendorTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">Id</th>
										<th>Vendor Name</th>
										<th class="fit ar">Vendor Type</th>
										<th class="fit ar">Location</th>
										<th class="fit ar">Contact Name</th>
										<th class="fit ar">Phone</th>
										<th class="fit ar">Email</th>
										<th class="actions">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty vendor}">
										<c:forEach items="${vendor}" var="vendor">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a target="_blank" 
													href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"><c:out
															value="VEN-${vendor.vendorNumber}" /></a></td>
												<td class="fit ar"><a target="_blank" 
													href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
													data-toggle="tip"
													data-original-title="Click this Vendor Details"> <c:out
															value="${vendor.vendorName}" />
												</a></td>
												<td class="fit ar"><c:out value="${vendor.vendorType}" /></td>
												<td class="fir ar"><a target="_blank" 
													href="http://maps.google.com/?q=${vendor.vendorLocation}"><c:out
															value="${vendor.vendorLocation}" /></a></td>
												<td class="fit ar"><i class="fa fa-male"></i> <c:out
														value="${vendor.vendorFirName}" />
													<ul class="list-inline no-margin">
														<li><i class="fa fa-male"></i> <c:out
																value="${vendor.vendorSecName}" /></li>
													</ul></td>
												<td class="fit ar"><i class="fa fa-phone-square"></i> <c:out
														value="${vendor.vendorFirPhone}" />
													<ul class="list-inline no-margin">
														<li><i class="fa fa-phone-square"></i> <c:out
																value="${vendor.vendorSecPhone}" /></li>
													</ul></td>
												<td class="fit ar"><i class="fa fa-envelope"></i> <a
													href="mailto:${vendor.vendorFirEmail}"> <c:out
															value="${vendor.vendorFirEmail}" />
												</a>
													<ul class="list-inline no-margin">
														<li><i class="fa fa-envelope"></i> <a
															href="mailto:${vendor.vendorSecEmail}"> <c:out
																	value="${vendor.vendorSecEmail}" />
														</a></li>
													</ul></td>
												<td class="actions">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
																		<a
																			href="<c:url value="/${vendor.vendorTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
																			<i class="fa fa-edit"></i> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_VENDOR')">
																		<a
																			href="<c:url value="/${vendor.vendorTypeId}/deleteVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
																			<span class="fa fa-trash"></span> Delete
																		</a>
																	</sec:authorize></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
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