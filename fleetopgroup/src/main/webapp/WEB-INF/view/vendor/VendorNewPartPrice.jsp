<%@ include file="taglib.jsp"%>
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
					<sec:authorize access="hasAuthority('ADDEDIT_VENDOR_FIXEDPART')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVendorPartPrice.in?VENID=${vendor.vendorId}"/>">
							<i class="fa fa-plus"></i> Fixed Vendor Part Price
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">Cancel</a>
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
								<a
									href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
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
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>

						</ul>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VENDOR')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty vendorFixed}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable" class="table table-hover table-striped">
											<thead>
												<tr>
													<th class="fit">Part Number</th>
													<th>Part Name</th>
													<th>Quantity</th>
													<th>Cost</th>
													<th>Discount</th>
													<th>GST</th>
													<th>Total</th>
													<th class="fit ar">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${vendorFixed}" var="vendorFixed">
													<tr data-object-id="" class="ng-scope">
														<td class="fit"><a target="_blank"
															href="<c:url value="/showMasterParts.in?partid=${vendorFixed.PARTID}"/>"
															data-toggle="tip" data-original-title="Click Part Info">
																<c:out value="${vendorFixed.PARTNAME}" />

														</a></td>
														<td><c:out value="${vendorFixed.PARTNUMBER}" /></td>
														<td><c:out value="${vendorFixed.PARTQUANTITY}" /></td>
														<td><c:out value="${vendorFixed.PARTEACHCOST}" /></td>

														<td><c:out value="${vendorFixed.PARTDISCOUNT}" /></td>



														<td><c:out value="${vendorFixed.PARTGST}" /></td>
														<td><c:out value="${vendorFixed.PARTTOTAL}" /></td>
														<td class="fit"><div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('DELETE_VENDOR_FIXEDPART')">
																			<a
																				href="<c:url value="/${vendor.vendorId}/${SelectPage}/deleteVendorPart?VPID=${vendorFixed.VPPID}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div></td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<c:url var="firstUrl"
									value="/VendorPartPrice/${vendor.vendorId}/1" />
								<c:url var="lastUrl"
									value="/VendorPartPrice/${vendor.vendorId}/${deploymentLog.totalPages}" />
								<c:url var="prevUrl"
									value="/VendorPartPrice/${vendor.vendorId}/${currentIndex - 1}" />
								<c:url var="nextUrl"
									value="/VendorPartPrice/${vendor.vendorId}/${currentIndex + 1}" />
								<div class="text-center">
									<ul class="pagination pagination-lg pager">
										<c:choose>
											<c:when test="${currentIndex == 1}">
												<li class="disabled"><a href="#">&lt;&lt;</a></li>
												<li class="disabled"><a href="#">&lt;</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${firstUrl}">&lt;&lt;</a></li>
												<li><a href="${prevUrl}">&lt;</a></li>
											</c:otherwise>
										</c:choose>
										<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
											<c:url var="pageUrl"
												value="/VendorPartPrice/${vendor.vendorId}/${i}" />
											<c:choose>
												<c:when test="${i == currentIndex}">
													<li class="active"><a href="${pageUrl}"><c:out
																value="${i}" /></a></li>
												</c:when>
												<c:otherwise>
													<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:choose>
											<c:when test="${currentIndex == deploymentLog.totalPages}">
												<li class="disabled"><a href="#">&gt;</a></li>
												<li class="disabled"><a href="#">&gt;&gt;</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${nextUrl}">&gt;</a></li>
												<li><a href="${lastUrl}">&gt;&gt;</a></li>
											</c:otherwise>
										</c:choose>
									</ul>
								</div>
							</c:if>
							<c:if test="${empty vendorFixed}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
								<a
									href="<c:url value="/${vendor.vendorTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
									Edit Vendor </a>
							</sec:authorize></li>
					</ul>
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


</div>