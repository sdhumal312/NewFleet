<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span>
						Dispatch TripSheet</span>
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
							<span class="info-box-text">Dispatch TripSheet</span> <span
								class="info-box-number">${TripDispatch}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripSheet</span>
							<form action="<c:url value="/searchTSDispatch.in"/>"
								method="post">
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
		<c:if test="${saveTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Created successfully .
			</div>
		</c:if>
		<c:if test="${deleteTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Canceled successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Already Updated.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Updated successfully .
			</div>
		</c:if>
		<c:if test="${dangerTripSheet}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Not Deleted.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in"/>">Today TripSheet</a></li>
									<li role="presentation" class="active"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch
											TripSheet <span data-toggle="tip" title=""
											class="badge bg-red"
											data-original-title="${TripDispatch} Dispatch TripSheet">${TripDispatch}</span>
									</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close
											TripSheet</a></li>
									<c:if test="${configuration.hideClosedTripSheet}">	
										<sec:authorize access="hasAuthority('SHOW_TRIPSHEET_CLOSE_STATUS')">
											<li role="presentation"><a
												href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment
													TripSheet</a></li>
											<li role="presentation"><a
												href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">Account
													Closed TripSheet</a></li>
										</sec:authorize>
									</c:if>	
									<c:if test="${!configuration.hideClosedTripSheet}">	
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">Account
											Closed TripSheet</a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="main-body">
					<c:if test="${!empty TripSheet}">
						<div class="box">
							<div class="box-body">
								<table id="TripSheetDisptch" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">TripSheet Id</th>
											<th>Vehicle</th>
											<th>Group</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th class="fit ar">Booking-Ref:</th>
											<th class="fit ar">Dispatch</th>
											<th class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${TripSheet}" var="TripSheet">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
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
												<td><sec:authorize
														access="hasAuthority('EDIT_TRIPSHEET')">
														<a class="btn btn-success btn-sm"
															href="<c:url value="/dispatchTripSheet.in?ID=${TripSheet.tripSheetID}"/>">
															<span class="fa fa-rocket"> </span> Dispatch
														</a>
													</sec:authorize></td>
												<td><sec:authorize
														access="hasAuthority('DELETE_TRIPSHEET')">
														<a class="btn btn-danger btn-sm"
															href="<c:url value="/deleteTripSheet.in?ID=${TripSheet.tripSheetID}"/>"
															class="confirmation"
															onclick="return confirm('Are you sure? Delete ')"> <span
															class="fa fa-trash"></span> Delete
														</a>
													</sec:authorize></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
						<c:url var="firstUrl" value="/dispatchTripSheet/1" />
						<c:url var="lastUrl"
							value="/dispatchTripSheet/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/dispatchTripSheet/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/dispatchTripSheet/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/dispatchTripSheet/${i}" />
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