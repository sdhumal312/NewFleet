<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/FuelVendor/1.in"/>">Fuel Vendor Entry</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="!hasAuthority('ADD_FUELVENDOR')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_FUELVENDOR')">

						<button type="button" class="btn btn-info btn-sm"
							data-toggle="modal" data-target="#SearchFuelEntries"
							data-whatever="@mdo">
							<span class="fa fa-search"> Search</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_FUELVENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUELVENDOR')">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-tint"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Fuel Entries</span> <span
								class="info-box-number">${FuelCount}</span>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize access="!hasAuthority('ADD_FUELVENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_FUELVENDOR')">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Trip Sheet</span>
							<form action="<c:url value="/searchFuelVendor.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">TS-</span> <input
										class="form-text" id="vehicle_registrationNumber"
										name="tripSheetID" type="number" min="1" required="required"
										placeholder="Please enter TripSheet ID"> <span
										class="input-group-btn">
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
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>
		<c:if test="${param.Success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Data Successfully Created.
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel Entries Fuel Reference ,Vehicle Name, Vendor Name Already
				created. Or This Fuel Entries Can't be Edit or Delete .. This Fuel
				Entry is Approved or Payment Mode
			</div>
		</c:if>
		<c:if test="${param.duplicateFuelEntries eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Fuel EntriesFuel Reference ,Vehicle Name, Vendor Name Already
				created. (or) This Duplicate Entries Reference Number.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="SearchFuelEntries" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post" action="<c:url value="/searchFuelVendor.in"/>"
						enctype="multipart/form-data">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Search TripSheet</h3>
							</div>
							<div class="panel-body">
								<div class="form-horizontal">
									<br>

									<div class="row1">
										<div class="L-size">
											<label> Search TripSheet: </label>
										</div>
										<div class="I-size">
											<div class="input-group">
												<span class="input-group-addon" id="basic-addon1">TS-</span>
												<input class="form-text" id="vehicle_registrationNumber"
													name="tripSheetID" type="text" required="required"
													placeholder="Please enter TripSheet ID"> <span
													class="input-group-btn"> </span>
											</div>
										</div>
									</div>
								</div>
								<div class="row1 progress-container">
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 0%">Please wait..</div>
									</div>
								</div>
								<div class="modal-footer">
									<input class="btn btn-success"
										onclick="this.style.visibility = 'hidden'" name="commit"
										type="submit" value="Search" id="myButton"
										data-loading-text="Loading..." class="btn btn-primary"
										autocomplete="off" id="js-upload-submit" value="Add Document"
										data-toggle="modal" data-target="#processing-modal">
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
								<script>
									$('#myButton').on('click', function() {
										//alert("hi da")
										$(".progress-bar").animate({
											width : "100%"
										}, 2500);
										var $btn = $(this).button('loading')
										// business logic...
										$btn.button('reset')
									})
								</script>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('VIEW_FUELVENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_FUELVENDOR')">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="FuelTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th>ID</th>
											<th>Vehicle</th>
											<th>Driver</th>
											<th>Date</th>
											<th>Close(Km)</th>
											<th>Volume</th>
											<th>Amount</th>
											<th>Doc</th>
											<th class="actions" class="icon">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty fuel}">
											<c:forEach items="${fuel}" var="fuel">
												<tr data-object-id="" class="ng-scope">
													<td><a
														href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
														data-toggle="tip" data-original-title="Click Fuel Details"><c:out
																value="FT-${fuel.fuel_Number}" /></a></td>
													<td><a href="#" data-toggle="tip"
														data-original-title="Click Vehicle Details"><c:out
																value="${fuel.vehicle_registration}" /> </a><br> <c:choose>
															<c:when test="${fuel.fuel_TripsheetID != 0 && fuel.fuel_TripsheetNumber != null}">
																<a target="_blank"
																	href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Tripsheet Details"><c:out
																		value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
															</c:when>
															<%-- <c:otherwise>
																<c:out value="TS-${fuel.fuel_TripsheetNumber}" />
															</c:otherwise> --%>
														</c:choose></td>
													<td><a href="#" data-toggle="tip"
														data-original-title="Driver empnumber"><c:out
																value="${fuel.driver_empnumber}" /><br> </a> <c:out
															value="${fuel.driver_name}" /></td>
													<td><c:out value="${fuel.fuel_date}" /><br>
														<h6>
															<a data-toggle="tip" data-original-title="Vendor Name">
																<c:out value="${fuel.vendor_name}" />-( <c:out
																	value="${fuel.vendor_location}" /> )
															</a>
														</h6></td>
													<td><c:out value="${fuel.fuel_meter}" /></td>
													<td><abbr data-toggle="tip"
														data-original-title="Liters"><c:out
																value="${fuel.fuel_liters}" /></abbr> <c:if
															test="${fuel.fuel_tank_partial==1}">
															<abbr data-toggle="tip"
																data-original-title="Partial fuel-up"> <i
																class="fa fa-adjust"></i>
															</abbr>
														</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
													<td><i class="fa fa-inr"></i> <c:out
															value="${fuel.fuel_amount}" /> <br> <abbr
														data-toggle="tip" data-original-title="Price"> <c:out
																value="${fuel.fuel_price}/liters" />
													</abbr></td>
													<td><c:choose>
															<c:when test="${fuel.fuel_document == true}">
																<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">

																	<a
																		href="${pageContext.request.contextPath}/download/FuelDocument/${fuel.fuel_document_id}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td>
													<td class="fit">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-ellipsis-v"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('EDIT_FUELVENDOR')">
																		<a
																			href="<c:url value="/FuelEntriesEdit?Id=${fuel.fuel_id}"/>">
																			<i class="fa fa-edit"></i> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_FUELVENDOR')">
																		<a
																			href="<c:url value="/deleteFuel.in?FID=${fuel.fuel_id}"/>">
																			<i class="fa fa-trash"></i> Delete
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
					<c:url var="firstUrl" value="/FuelVendor/1" />
					<c:url var="lastUrl"
						value="/FuelVendor/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/FuelVendor/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/FuelVendor/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/FuelVendor/${i}" />
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
				</sec:authorize>
			</div>
		</div>
	</section>
</div>