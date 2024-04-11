<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <span>Driver Bata
						Details</span>
				</div>
				<div class="pull-right">
				<c:if test="${driver.driverStatusId != 6}">
					<c:if test="${driver.driverStatusId != 2}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
					</c:if>
					<c:if test="${driver.driverStatusId == 2}">
					<sec:authorize access="hasAuthority('INACTIVE_DRIVER_EDIT')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
						</c:if>

					<sec:authorize access="hasAuthority('VIEW_DRIVER_BATA')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#DriverFamily"> <i class="fa fa-search"></i>
							Bata Details
						</a>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('ADD_DRIVER_BATA')">
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/DriverHaltNew?driverId=${driver.driver_id}"/>"> <span
							class="fa fa-plus"></span> Add Halt Beta
						</a>
					</sec:authorize>
					</c:if>
					<sec:authorize access="hasAuthority('PRINT_DRIVER')">
						<a href="PrintDriverFamily?id=${driver.driver_id}" target="_blank"
							class="btn btn-default btn-sm"><i class="fa fa-print"></i>
							Print</a>
					</sec:authorize>

					<a class="btn btn-link"
						href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>

		</div>
	</section>
	<div class="modal fade" id="DriverFamily" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="formPart" action="<c:url value="/searchDriverBata.in"/>"
					method="post" name="formOwner" role="form" class="form-horizontal">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Bata Details</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal ">
							<input type="hidden" name="DRIVERID" value="${driver.driver_id}" />

							<!-- Date range -->
							<div class="row1">
								<label class="L-size control-label">Date range: <abbr
									title="required">*</abbr></label>
								<div class="I-size">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
										<input type="text" id="RenewalComRange" class="form-text" readonly="readonly"
											name="DRIVER_BATA_DATE" required="required"
											style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">

						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Search Driver Bata">
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.delete eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Halt Details Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.update eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Halt Details Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Halt Details Already Created.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Halt Details Not Created.
					</div>
				</c:if>

				<div class="row">
					<sec:authorize access="!hasAuthority('VIEW_DRIVER_BATA')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_DRIVER_BATA')">
						<div class="main-body">
							<c:if test="${!empty DBata}">
								<div class="box">
									<div class="box-header">
										<div class="pull-right">
											<div id="langSelect"></div>
										</div>
									</div>
									<!-- /.box-header -->
									<div class="box-body">
										<table id="DriverReminderTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th>ID</th>
													<th>TripID</th>
													<th>Vehicle</th>
													<th>Ref.</th>
													<th>Trip Date</th>
													<th>Location</th>
													<th>Amount</th>
													<th>Type</th>
													<th class="actions" class="fit ar">Actions</th>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${DBata}" var="DBata">
													<tr data-object-id="" class="ng-scope">
														<td class="fit">
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td class="icon"><c:choose>
																<c:when test="${DBata.TRIPSHEETID == 0}">
																	<c:out value="DH-${DBata.DHID}" />
																</c:when>
																<c:otherwise>
																	<a
																		href="showTripSheet.in?tripSheetID=${DBata.TRIPSHEETID}"><c:out
																			value="TS-${DBata.tripSheetNumber}" /></a>
																</c:otherwise>
															</c:choose></td>
														<td class="icon"><c:out value="${DBata.VEHICLE_NAME}" /></td>
														<td class="icon"><c:out value="${DBata.REFERENCE_NO}" /></td>
														<td class="icon"><i></i> <c:out
																value="${DBata.HALT_DATE_FROM}  to " /> <c:out
																value="${DBata.HALT_DATE_TO}" /></td>
														<td class="icon"><c:out value="${DBata.HALT_PLACE}" /></td>
														<td class="icon"><c:out value="${DBata.HALT_AMOUNT}" /></td>
														<td class="icon"><c:out
																value="${DBata.HALT_PLACE_TYPE_NAME}" /></td>
														<td class="fit ar"><c:if test="${driver.driverStatusId != 6}"><c:choose>
																<c:when test="${DBata.HALT_PLACE_TYPE_ID == 1}">
																	<div class="btn-group">
																		<a class="btn btn-default btn-sm dropdown-toggle"
																			data-toggle="dropdown" href="#"><span
																			class="fa fa-ellipsis-v"></span> </a>
																		<ul class="dropdown-menu pull-right">
																			<li><sec:authorize
																					access="hasAuthority('EDIT_DRIVER_BATA')">
																					<a
																						href="<c:url value="/editDriverHalt.in?DHID=${DBata.DHID}&DID=${driver.driver_id}"/>">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_DRIVER_BATA')">
																					<a
																						href="<c:url value="/deleteLocalDriverHalt.in?DHID=${DBata.DHID}&DID=${driver.driver_id}"/>"
																						class="confirmation"
																						onclick="return confirm('Are you sure? Delete ')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</ul>
																	</div>
																</c:when>
																<c:otherwise></c:otherwise>
															</c:choose></c:if></td>
													</tr>
												</c:forEach>
												<%-- <tr data-object-id="" class="ng-scope">
													<td class="fit ar"
														style="text-align: right; font-size: 18px; font-weight: bold;"
														colspan="5">Total Bata :</td>
													<td class="icon"
														style="font-size: 18px; font-weight: bold;"><c:out
															value="${TotalBataAmount}" /></td>
												</tr> --%>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty DBata}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</sec:authorize>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
	    var e = 25,
	        t = $(".input_fields_wrap"),
	        n = $(".add_field_button"),
	        a = 1;
	    $(n).click(function(n) {
	        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row"><div class="col-md-4"><input class="form-text" id="OwnerSerial" name="DF_NAME" type="text" maxlength="150" required="required"> </div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_SEX" required="required"><option value="MALE">MALE</option><option value="FEMALE">FEMALE</option></select></div><div class="col-md-1"><input class="form-text" id="DF_AGE" name="DF_AGE" type="number"  required="required"></div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_RELATIONSHIP"	required="required"><option value="FATHER">FATHER</option><option value="MOTHER">MOTHER</option><option value="SON">SON</option><option value="DAUGHTER">DAUGHTER</option><option value="BROTHER">BROTHER</option><option value="SISTER">SISTER</option><option value="HUSBAND">HUSBAND</option><option value="WIFE">WIFE</option></select></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'))
	    }), $(t).on("click", ".remove_field", function(e) {
	        e.preventDefault(), $(this).parent("div").remove(), a--
	    })
	});
	</script>
</div>