<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Vehicle current Route Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('ReportTripSheetTable', 'Vehicle current Route Report')" >
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div id="div_print">

				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-sm-12 col-md-12 col-xs-12"
							style="padding-right: 80px;">
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped">
									<tbody>
										<tr>
											<td><c:choose>
													<c:when test="${company.company_id != null}">
														<img
															src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
															class="img-rounded " alt="Company Logo" width="280"
															height="40" />

													</c:when>
													<c:otherwise>
														<i class="fa fa-globe"></i>
														<c:out value="${company.company_name}" />
													</c:otherwise>
												</c:choose></td>
											<td>Print By: ${company.firstName}_${company.lastName}</td>
										</tr>
										<tr>
											<td colspan="2">Branch :<c:out
													value=" ${company.branch_name}  , " /> Department :<c:out
													value=" ${company.department_name}" />
											</td>
										</tr>
									</tbody>
								</table>
								<div class="row invoice-info">
									<table id="ReportTripSheetTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="fit">TS-Id</th>
												<th>Vehicle</th>
												<th>Group</th>
												<th>Route</th>
												<th>Trip From</th>
												<th>Trip To</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty TripSheet}">
												<c:forEach items="${TripSheet}" var="TripSheet">

													<tr data-object-id="" class="ng-scope">
														<td class="fit"><a target="_blank"
															href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																	value="TS-${TripSheet.tripSheetNumber}" /></a></td>
														<td><c:out value="${TripSheet.vehicle_registration}" />
														</td>
														<td><c:out value="${TripSheet.vehicle_Group}" /></td>
														<td><c:out value="${TripSheet.routeName}" /></td>
														<td><c:out value="${TripSheet.tripOpenDate}" /></td>
														<td><c:out
																value="${TripSheet.closetripDate}" /></td>
														<td><c:out
																value="${TripSheet.tripStutes}" /></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>