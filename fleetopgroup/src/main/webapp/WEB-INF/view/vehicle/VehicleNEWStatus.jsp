<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">New Vehicle</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('IMPORT_VEHICLE')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/1.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_VEHICLE')">
					<c:if test="${configuration.addVehicleDetailsAjax}">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVehicleDetails"/>"
							data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
							<span class="fa fa-plus"> Create Vehicle</span>
						</a>
					</c:if>
					<c:if test="${!configuration.addVehicleDetailsAjax}">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVehicle"/>"
							data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
							<span class="fa fa-plus" id="AddVehicle"> Create Vehicle</span>
						</a>
					</c:if>
					</sec:authorize>
					<sec:authorize access="hasAuthority('BUS_BOOKING_LOCATION')">
					<a class="btn btn-success btn-sm"
							href="<c:url value="/vehicleLocationStatus"/>"
							data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
							<span class="fa fa-plus"> Bus Location Status</span>
						</a>
						</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/VehicleReport"/>"
							class="btn btn-primary btn-lg "
							data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Search..">
							<span class="fa fa-search"> Search</span>
						</a>
					</sec:authorize>
					<%-- <sec:authorize access="hasAuthority('VIEW_VEHICLE')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/LinkVehicleToTollAccount"/>"
							class="btn btn-primary btn-lg "
							data-loading-text="<i class='fa fa-plus'></i>">
							<span class="fa fa-search"> Link Vehicle To Toll Account</span>
						</a>
					</sec:authorize> --%>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div class="row">
			<c:if test="${configuration.showTotalVehicleCount}">
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-bus"></i></span>
							<div class="info-box-content">
							<span class="info-box-text">Total Vehicle</span>
							<span class="info-box-number">${totalVehicleCount}</span>
						</div>
					</div>
				</div>
				</c:if>
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-bus"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectStatus} Vehicle</span>
							<input type="hidden" value="${Status}" id="statues">
							<span class="info-box-number">${VehicleCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-5 col-sm-5 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Vehicle</span>
							<form action="<c:url value="/searchVehicleAll.in"/>"
								method="post">
								<div class="input-group">
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input class="form-text"
										placeholder="VID, RegNo, Chassis No"
										id="vehicle_registrationNumber" name="searchvehicle"
										type="text" data-mask="" required="required"> <span
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
			</div>
		<%-- <c:if test="${inWO}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					${inWOMessage} Please check..
				</div>
			</c:if>
			<c:if test="${inTriproute}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					${inTriprouteMessage} Please check..
				</div>
			</c:if> --%>
			<c:if test="${param.InAnotherTrip eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					${InAnother} Please check..
				</div>
			</c:if>
			<c:if test="${saveVehicle}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Created successfully .
				</div>
			</c:if>
			<c:if test="${importSave}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Imported Successfully ${CountSuccess} Vehicle Data.
				</div>
			</c:if>
			<c:if test="${importSaveError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Vehicle can not be created with this Import CSV File. <br /> Do
					not Import empty CSV File for Vehicle_Reg_NO, Vehicle_Chassis,
					Vehicle_ Engine No, Vehicle_odd Meter Or Required
				</div>
			</c:if>
			<c:if test="${importSaveAlreadyError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					<c:if test="${!empty Duplicate}">
						<c:forEach items="${Duplicate}" var="Duplicate">
				
				      ${CountDuplicate} Duplicate entry Please Check First <c:out
								value="${Duplicate}" /> Vehicle_Reg_NO, Vehicle_Chassis, Vehicle_ Engine No  .
				</c:forEach>
					</c:if>
				</div>
			</c:if>
			<c:if test="${uploadVehicle}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Updated successfully .
				</div>
			</c:if>
			<c:if test="${param.deleteVehicle eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Deleted successfully .
				</div>
			</c:if>
			<c:if test="${alreadyVehicle}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Already Exists.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Already Vehicle_Reg_NO, Vehicle_Chassis, Vehicle_
					Engine No Exists .
				</div>
			</c:if>
			<c:if test="${param.deleteInside eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Should be delete inside Documents, Photos, purchase
					info, Renewal Reminder, Service Entries, Fuel Entries, Issues,
					WorkOrders etc... .
				</div>
			</c:if>
			<c:if test="${vehicleGroupNotExist}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Some of Vehicle not created because Vehicle Group
					not exists..
				</div>
			</c:if>
			<c:if test="${vehicleStatusNotExist}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Some Of Vehicle not created because Vehicle Status
					not exists..
				</div>
			</c:if>
			<!-- Modal  and create the javaScript call modal -->
			<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importVehicle.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1">
											<div class="L-size">
												<label> Import Only CSV File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".csv" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Vehicle Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Vehicle load files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton')
												.on(
														'click',
														function() {
															//alert("hi da")
															$(".progress-bar")
																	.animate(
																			{
																				width : "100%"
																			},
																			2500);
															var $btn = $(this)
																	.button(
																			'loading')
															// business logic...

															$btn
																	.button('reset')
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

					<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
						<div class="box">
							<div class="box-body">
								<div class="row">
									<ul class="nav nav-tabs" role="tablist">
										<c:if test="${!empty vehiclestatus}">
											<c:forEach items="${vehiclestatus}" var="vehiclestatus">
												<li role="presentation" id="${vehiclestatus.sid}" class=""><a
													href="<c:url value="/vehicle/${vehiclestatus.sid}/1.in"/>">${vehiclestatus.vStatus}</a></li>
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</div>
						</div>
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>RegNo</th>
												<th id="Group" class="fit ar">Group</th>
												<th id="Make" class="fit ar">Make</th>
												<th id="Type">Type</th>
												<th id="currentMeter" class="fit ar">Current Meter</th>
												<th id="currentMeter" class="fit ar">Current</th>
												<th id="location" class="fit ar">Location</th>
												<th id="Ownership" class="fit ar">Ownership</th>
												<!-- <th id="Status" class="fit ar">Status</th> -->
												<th id="Actions" class="fit">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehicles}">
												<c:forEach items="${vehicles}" var="vehicle">
													<tr>
														<td><a
															href="<c:url value="/showVehicle?vid=${vehicle.vid}"/>"
															data-toggle="tip"
															data-original-title="Click vehicle Details"><c:out
																	value="${vehicle.vehicle_registration}" /></a></td>
														<td class="fit ar"><c:out
																value="${vehicle.vehicle_Group}" /></td>
														<td class="fit ar"><c:out
																value="${vehicle.vehicle_maker}" /></td>
														<td class="fit ar"><c:out
																value="${vehicle.vehicle_Type}" /></td>
														<td class="fit ar"><span class="badge"><c:out
																	value="${vehicle.vehicle_Odometer}" /></span></td>
														<td class="fit ar"><c:choose>
																<c:when test="${vehicle.vStatusId == 5}">
																	<c:if test="${vehicle.tripSheetID != 0}">
																		<a
																			href="<c:url value="/getTripsheetDetails.in?tripSheetID=${vehicle.tripSheetID}" />"><c:out
																				value="TS-${vehicle.tripSheetNumber}" /></a>
																	</c:if>
																</c:when>
																<c:when test="${vehicle.vStatusId == 6}">
																	<c:if test="${vehicle.tripSheetID != 0}">
																		<a
																			href="<c:url value="/showWorkOrder?woId=${vehicle.tripSheetID}"/>">
																			<c:out value="WO-${vehicle.workOrder_Number}" />

																		</a>
																	</c:if>
																</c:when>
															</c:choose></td>
														<td class="fit ar"><c:out
																value="${vehicle.vehicle_Location}" /></td>
														<td class="fit ar"><c:out
																value="${vehicle.vehicle_Ownership}" /></td>
														<%-- <td class="fit ar"><c:out
																value="${vehicle.vehicle_Status}" /></td> --%>
														<td class="fit"><div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_VEHICLE')">
																			<a
																				href="<c:url value="/editVehicle?vid=${vehicle.vid}"/>">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_VEHICLE')">
																			<a
																				href="<c:url value="/deleteVehicle?vid=${vehicle.vid}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<c:url var="firstUrl" value="/vehicle/${Status}/1" />
						<c:url var="lastUrl"
							value="/vehicle/${Status}/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/vehicle/${Status}/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/vehicle/${Status}/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/vehicle/${Status}/${i}" />
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
		</sec:authorize>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active";
			}
		});
	</script>

</div>