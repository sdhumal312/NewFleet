<%@ include file="taglib.jsp"%>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-10 col-xs-10">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <a
								href="<c:url value="/VendorApprovalCreated/1.in"/>">Vendor
								Approvals</a> / <span>Approval - ${approval.approvalNumber} </span>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
									<a href="PrintApproval?id=${approval.approvalId}"
										target="_blank" class="btn btn-default btn-sm"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize>

								<a class="btn btn-link btn-sm"
									href="<c:url value="/VendorApprovalCreated/1.in"/>">Cancel</a>
							</div>
						</div>
						<div id="div_print">
							<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
								<div class="row">
									<div class="pull-left">
										<span>Approval Number : A-${approval.approvalNumber}</span>
									</div>
									<div class="pull-right">
										<span>Created Date : ${approval.created}</span>
									</div>
								</div>
								<h4 align="center">
									<a href="showVehicle.in?vid=${approval.approvalId}"
										data-toggle="tip" data-original-title="Click Vehicle Info">
										<c:out value="${approval.approvalvendorName}" />
									</a>
								</h4>
								<div class="row">
									<h4 align="center">${approval.approvalvendorLocation }</h4>
								</div>
								<div class="secondary-header-title">
									<ul class="breadcrumb">
										<li>Vendor Type : <a data-toggle="tip"
											data-original-title="Vendor Type "> <c:out
													value="${vendor.vendorType}" /></a></li>
										<li>Phone : <a data-toggle="tip"
											data-original-title="Phone"><c:out
													value="${vendor.vendorPhone}" /></a></li>
										<li>PAN No : <a data-toggle="tip"
											data-original-title="PAN No"><c:out
													value="${vendor.vendorPanNO}" /></a></li>
										<li>Service GST NO : <a data-toggle="tip"
											data-original-title="GST NO"> <c:out
													value="${vendor.vendorGSTNO}" /></a></li>
										<li>GST Registered : <a data-toggle="tip"
											data-original-title="GST NO"> <c:choose>
													<c:when
														test="${vendor.vendorGSTRegisteredId == 1}">

																Turnover Below 25 lakhs GST
															</c:when>
													<c:otherwise>
																Turnover Above 25 lakhs GST

															</c:otherwise>
												</c:choose></a></li>
									</ul>
								</div>
								<div class="breadcrumb">
									<h5 align="center">${vendor.vendorAddress1},
										${vendor.vendorAddress2}, ${vendor.vendorCity},
										${vendor.vendorState}, ${vendor.vendorCountry}, Pin-
										${vendor.vendorPincode}</h5>
								</div>
								<br>
								<fieldset>
									<div class="row">
										<div class="table-responsive">

											<c:if test="${!empty ServiceEntries}">
													<table class="table">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">ID</th>
																<th class="fit ar">Invoice/Job Number</th>
																<th class="fit ar">Invoice Date</th>
																<th class="fit ar">Vendor/Vehicle</th>
																<th class="fit ar">Invoice Cost</th>
																<th class="fit ar">Approved Cost</th>
																<th class="fit ar">Payment Status</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${ServiceEntries}" var="ServiceEntries">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fir ar"><a href="${ServiceEntries.transactionUrl}" target="_blank">${ServiceEntries.transactionNumber}</a></td>
																	<td class="fir ar">${ServiceEntries.invoiceNumber}</td>
																	<td class="fir ar">${ServiceEntries.invoiceDateStr}</td>
																	
																	<c:choose>
																		<c:when test="${ServiceEntries.vehicleNumber != null}">
																			<td class="fir ar">${ServiceEntries.vehicleNumber}</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${approval.approvalvendorName}" />
																			</td>
																		</c:otherwise>
																	</c:choose>	
																	<td class="fir ar">${ServiceEntries.subApprovalTotal}</td>
																	<td class="fir ar">${ServiceEntries.subApprovalPaidAmount}</td>
																	<c:choose>
																		<c:when test="${ServiceEntries.approvedPaymentStatus == 'PAID'}">
																			<td class="fir ar">CLEAR</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${ServiceEntries.approvedPaymentStatus}" />
																			</td>
																		</c:otherwise>
																	</c:choose>		
																	
																</tr>
															</c:forEach>
														</tbody>
														<tfoot>
															<tr class="breadcrumb">
															</tr>
														</tfoot>
													</table>
												</c:if>
										</div>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-offset-8">

													<table class="table">
														<tbody>
															<tr class="row">
																<th class="key"><h4>Total :</h4></th>
																<td class="value"><h4>
																		<i class="fa fa-inr"></i> ${totalPaidApprovalAmount}
																	</h4></td>
															</tr>

														</tbody>
													</table>
												</div>
											</div>
										</div>
										<fieldset>
											<legend>Approved Details</legend>
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Approved By :</th>
														<td class="value"><c:out
																value="${approval.approvalCreateBy}" /></td>
													</tr>

													<tr class="row">
														<th class="key">Approved Date :</th>
														<td class="value"><c:out
																value="${approval.approvalCreateDate}" /></td>
													</tr>

												</tbody>
											</table>
										</fieldset>
										<c:if test="${approval.approvalStatusId == 3}">
												<div class="row">
												<a class="btn btn-success btn-sm" href="/fleetopgroup/approvedPayment.in?approvalId=1418" onclick="return confirm('Are you sure? Payment  ')">
													<span class="fa fa-cog"> Make Payment</span>
												</a>
												</div>
												</c:if>
									</div>

								</fieldset>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${approval.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${approval.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${approval.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${approval.lastupdated}" /></small>
		</div>
	</section>
</div>