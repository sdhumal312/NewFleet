<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
							
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span
						id="AllVehicl"> Close TripSheets</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchTripSheetShow.in"/>"
							method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="tripStutes" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="TS-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addTripSheetEntries.in"/>"> <span
							class="fa fa-plus"></span> Create TripSheet
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-clock-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Payment TripSheet</span> <span
								class="info-box-number">${TripSheetCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripSheet</span>
							<form action="<c:url value="/searchTSManage.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="SelectVehicle" name="vid"
										type="text" required="required"
										placeholder="Search Vehicle & Route" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>

		<sec:authorize access="!hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in"/>">Today TripSheet</a></li>
									<li role="presentation" ><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close
											TripSheet </a></li>
									
										<li role="presentation" class="active"><a
											href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment
												TripSheet <span data-toggle="tip" title=""
												class="badge bg-red"
												data-original-title="${TripSheetCount} Closed TripSheet">${TripSheetCount}</span>
										</a></li>
											<li role="presentation"><a
												href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">Account
														Closed TripSheet
										</a></li>
										
									
									
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="main-body">
					<c:if test="${!empty TripSheet}">
						<input type="hidden" value="${configuration.allowTallyIntegration}" id="allowTallyIntegration">
						<div class="box">
							<div class="box-body">
								<table id="TripSheetAccount"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="fit"></th>
											<th>TS-Id</th>
											<th>Vehicle</th>
											<th>Group</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th>B-Ref:</th>
											<th class="fit ar">Expense</th>
											<th class="fit ar">Income</th>
											<th class="fit ar">Status</th>
											<th class="fit ar">M-Expense</th>
											<th class="fit ar">M-Income</th>
											<th class="fit ar">Close A/C</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${TripSheet}" var="TripSheet">

											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="<c:url value="/copyTripSheet.in?ID=${TripSheet.tripSheetID}"/>"
													class="btn-sm btn btn-info" data-toggle="tip"
													data-original-title="Copy Old to Create New"><span
														class="fa fa-copy"></span></a></td>
												<td><a
													href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"><c:out
															value="TS-${TripSheet.tripSheetNumber}" /></a></td>
												<td><a
													href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"
													data-toggle="tip" data-original-title="Click Details">
														<c:out value="${TripSheet.vehicle_registration}" />

												</a></td>
												<td class="fit ar"><c:out
														value="${TripSheet.vehicle_Group}" /></td>
												<td><c:out value="${TripSheet.routeName}" /></td>

												<td><c:out value="${TripSheet.tripOpenDate}" /></td>

												<td class="fir ar"><c:out
														value="${TripSheet.tripBookref}" /></td>

												<td class="fir ar"><c:out
														value="${TripSheet.tripTotalexpense}" /></td>
												<td class="fir ar"><c:out
														value="${TripSheet.tripTotalincome}" /></td>
												<td class="fir ar"><c:choose>

														<c:when test="${TripSheet.tripStutesId ==3}">
															<span class="label label-pill label-warning"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:otherwise>
													</c:choose></td>

												<td class="fir ar"><c:choose>

														<c:when test="${TripSheet.tripStutesId ==3}">
															<sec:authorize
																access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
																<a class="btn btn-warning btn-sm"
																	href="<c:url value="/addExpense.in?tripSheetID=${TripSheet.tripSheetID}" />"
																	class="confirmation"
																	onclick="return confirm('Are you sure? Add Expense')"><span
																	class="fa fa-plus"></span> Expense</a>
															</sec:authorize>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:otherwise>
													</c:choose></td>

												<td class="fir ar"><c:choose>

														<c:when test="${TripSheet.tripStutesId ==3}">
															<sec:authorize
																access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
																<a class="btn btn-info btn-sm"
																	href="<c:url value="/addIncome.in?tripSheetID=${TripSheet.tripSheetID}"/>"
																	class="confirmation"
																	onclick="return confirm('Are you sure? Add Income')"><span
																	class="fa fa-plus"></span> Income</a>
															</sec:authorize>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:otherwise>
													</c:choose></td>

												<td class="fir ar"><c:choose>
														<c:when test="${TripSheet.tripStutesId ==3}">
															<sec:authorize
																access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
																<a class="btn btn-success btn-sm"
																	href="<c:url value="/addcloseAccountTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"
																	class="confirmation"
																	onclick="return closeTripSheetAccount(${TripSheet.voucherDateStr});"><span
																	class="fa fa-plus"></span> Close Account</a>
															</sec:authorize>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:otherwise>
													</c:choose></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>

						<c:url var="firstUrl" value="/newTripSheetAccount/1" />
						<c:url var="lastUrl"
							value="/newTripSheetAccount/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/newTripSheetAccount/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/newTripSheetAccount/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/newTripSheetAccount/${i}" />
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
					<c:if test="${empty TripSheet}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">
								<spring:message code="label.master.noresilts" />
							</p>
						</div>
					</c:if>
				</div>
			</div>
		</sec:authorize>

	</section>
</div>
<script type="text/javascript">
function closeTripSheetAccount(voucherDate){
	if($('#allowTallyIntegration').val() == 'true' || $('#allowTallyIntegration').val() == true){
		if(voucherDate == undefined || voucherDate == null || voucherDate == ''){
			showMessage('info', 'Voucher Date is not available for trip please save voucher date first !');
			return false;
		}
	}
	if(confirm('Are you sure? Close Account')){
			return true;
	}
	
	return false;
}
</script>