<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
						href="<c:url value="/newServiceEntries/1/1.in"/>"> Service
						Entries</a> / <a href="<c:url value="/ServiceEntriesReport"/>">
						Service Entries Report</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchServiceEntries.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="number" required="required" min="1"
									placeholder="ID, V-Name, Invoice-No"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/newServiceEntries/1/1.in"/>"> Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="ServiceTable"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>Vehicle</th>
											<th>Vendor</th>
											<th class="fit ar">Invoice Number</th>
											<th class="fit ar">Job Number</th>
											<th class="fit ar">Cost</th>
											<th class="fit ar">RoundCost</th>
											<th class="fit ar">Paid By</th>
											<th class="fit ar">Last Modified By</th>
											<th class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty ServiceEntries}">
											<c:forEach items="${ServiceEntries}" var="ServiceEntries">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a target="_blank"
														href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"><c:out
																value="S-${ServiceEntries.serviceEntries_Number}" /></a></td>
													<td><a href="showVehicle.in?vid=${ServiceEntries.vid}"
														target="_blank" data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${ServiceEntries.vehicle_registration}" />
													</a> <br> <c:out value="${ServiceEntries.vehicle_Group}" /></td>
													<td><c:out value="${ServiceEntries.vendor_name}" /><br>
														<c:out value="${ServiceEntries.vendor_location}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.invoiceNumber}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.jobNumber}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.totalservice_cost}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.totalserviceROUND_cost}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.service_paidby}" /></td>
													<td class="fir ar"><c:out
															value="${ServiceEntries.lastModifiedBy}" /></td>		
													<td class="fir ar">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-cog"></span> <span class="caret"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<c:choose>
																	<c:when
																		test="${ServiceEntries.serviceEntries_status != 'COMPLETE'}">
																		<li><sec:authorize
																				access="hasAuthority('EDIT_SERVICE_ENTRIES')">
																				<a
																					href="editServiceEntries.in?SEID=${ServiceEntries.serviceEntries_id}">
																					<i class="fa fa-edit"></i> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																				<a
																					href="deleteServiceEntries.in?SEID=${ServiceEntries.serviceEntries_id}"
																					class="confirmation"
																					onclick="return confirm('Are you sure? Delete ')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</c:when>
																	<c:otherwise>
																		<li><span class="label label-warning"><i
																				class="fa fa-dot-circle-o"></i> <c:out
																					value="SERVICE COMPLETED" /></span></li>
																	</c:otherwise>
																</c:choose>
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
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/Servicelanguage.js" />"></script>
</div>