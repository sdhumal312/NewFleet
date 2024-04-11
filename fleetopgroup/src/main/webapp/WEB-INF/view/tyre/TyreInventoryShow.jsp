<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
						Inventory</a> /
					<c:out value="${Tyre.TYRE_IN_NUMBER}" />
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_INVENTORY')">
						<c:choose>
							<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'SOLD'}"> 
								<span class="label label-pill label-warning" style="font-size: large;">
								<c:out value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
							</c:when>
							<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'SCRAPED'}"> 
								<span class="label label-pill label-danger" style="font-size: large;">
								<c:out value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-success" data-toggle="modal"
									data-target="#editTyreSerialNumber" data-whatever="@mdo">
									<span class="fa fa-plus"> Edit Tyre No</span>
								</button>
							</c:otherwise>
						</c:choose>
						
						
						<c:if test="${Tyre.TYRE_ASSIGN_STATUS == 'SCRAPED'}">
							<button type="button" class="btn btn-warning" data-toggle="modal"
								data-target="#scroptoavailable" data-whatever="@mdo">
								<span class="fa fa-plus"> Scrap To Available</span>
							</button>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a class="btn btn-warning btn-sm"
							href="transferTyreInventory.in?Id=${Tyre.TYRE_ID}"
							class="confirmation"
							onclick="return confirm('Are you sure you Want to Transfer Part?')">
							<span class="fa fa-exchange"></span> Transfer Tyre Inventory
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryTyreReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a href="transferTyreInventoryHistory.in?Id=${Tyre.TYRE_ID}"
							class="btn btn-default btn-sm">Transfer History</a>
					</sec:authorize>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/TyreWheel.png"
							class="zoom" data-title="Tyre Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/TyreWheel.png"
							class="img-rounded" alt="Tyre Photo" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">
								<c:choose>
									<c:when test="${Tyre.TYRE_RETREAD_COUNT == 0}">
										<span class="label label-pill label-success"><c:out
												value="NT" /></span>
									</c:when>
									<c:otherwise>
										<span class="label label-pill label-warning"><c:out
												value="${Tyre.TYRE_RETREAD_COUNT}-RT" /></span>
									</c:otherwise>
								</c:choose> <c:out value=" ${Tyre.TYRE_NUMBER}" /> - <c:out
									value="${Tyre.TYRE_MANUFACTURER}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><c:choose>
										<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'OPEN'}">
											<small class="label label-info"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'REJECT'}">
											<small class="label label-danger"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'RESOLVED'}">
											<small class="label label-warning"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:otherwise>
											<small class="label label-success"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:otherwise>
									</c:choose>
										<c:if test="${!empty Tyre.dismountedTyreStatus}">
											<small class="label label-info"><c:out
														value="${Tyre.dismountedTyreStatus}" /></small>
										</c:if>
									</li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="TYRE MANUFACTURER">
								</span> <span class="text-muted"><c:out
											value="${Tyre.TYRE_MANUFACTURER}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="category"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_MODEL}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Tyre Size"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_SIZE}" /></span></li>
							</ul>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<!-- Modal -->
		<div class="modal fade" id="editTyreSerialNumber" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="updateTyreSerialNo.in" method="post"
						name="vehicleType" onsubmit="return validateType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Edit Tyre Serial No</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Serial No
									:<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" class="form-text" value="${Tyre.TYRE_ID}"
										required="required" name="Id" />
									<!-- invoice Id -->
									<input type="hidden" class="form-text"
										value="${Tyre.ITYRE_INVOICE_ID}" required="required"
										name="InvoiceID" />
									<!-- Tyre Serial Num -->
									<input type="text" class="form-text"
										value="${Tyre.TYRE_NUMBER}" required="required"
										name="TyreSerialNo" maxlength="50"
										placeholder="Enter Tyre Serial Number" /> <label
										id="errorvType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save"><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Scrop Top available -->
		<div class="modal fade" id="scroptoavailable" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveTyreAvailable.in" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Scrap To Available</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Scrap To
									Available Note :<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" class="form-text" value="${Tyre.TYRE_ID}"
										required="required" name="TyreID" />
									<!-- Tyre Serial Num -->
									<textarea class="text optional form-text" maxlength="200"
										required="required" id="initial_note" name="Description"
										rows="3">
				                                 </textarea>
									<label id="errorvType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save">Change To Available</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>

			</div>
		</div>
		<c:if test="${param.AvailableSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Status Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.NoAuthen eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				No Authentication to access this Receive Tyre, Edit Tyre &amp;
				Delete Your Location is different.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-6 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Tyre Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Tyre Number :</th>
													<td class="value" style="width: 2432452px;">
													<c:out value="${Tyre.TYRE_NUMBER}" />
													<input type="hidden" id=tyreId value="${Tyre.TYRE_ID}">
													
													</td>
												</tr>
												<tr class="row">
													<th class="key">Manufacturer Name :</th>
													<td class="value"><c:out
															value="${Tyre.TYRE_MANUFACTURER}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Model :</th>
													<td class="value"><c:out value="${Tyre.TYRE_MODEL}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Size :</th>
													<td class="value"><c:out value="${Tyre.TYRE_SIZE}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Location :</th>
													<td class="value"><c:out
															value="${Tyre.WAREHOUSE_LOCATION}" /></td>
												</tr>
												<c:if test="${showSubLocation}"></c:if>
												<tr class="row">
													<th class="key">Sub Location :</th>
													<td class="value"><c:out
															value="${Tyre.subLocation}" /></td>
												</tr>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Scrap Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Scraped By :</th>
													<td class="value"><c:out
															value="${Tyre.TYRE_SCRAPED_BY}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Scraped Date :</th>
													<td class="value"><c:out
															value="${Tyre.TYRE_SCRAPED_DATE}" /></td>
												</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Vehicle Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>

												<tr class="row">
													<th class="key">Tyre Amount :</th>
													<td class="value"><c:out value="${Tyre.TYRE_AMOUNT}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value"><a
														href="<c:url value="/showVehicle?vid=${Tyre.VEHICLE_ID}"/>"
														data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${Tyre.VEHICLE_REGISTRATION}" /></a></td>
												</tr>
												<tr class="row">
													<th class="key">Open Odometer :</th>
													<td class="value"><c:out value="${Tyre.OPEN_ODOMETER}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Close Odometer :</th>
													<td class="value"><c:out
															value="${Tyre.CLOSE_ODOMETER}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Purchase Date :</th>
													<td class="value"><c:out
															value="${Tyre.TYRE_PURCHASE_DATE}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Assign Date :</th>
													<td class="value"><c:out
															value="${Tyre.TYRE_ASSIGN_DATE}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Total Tyre Running Km :</th>
													<td class="value">
													<input type="hidden" id="tyreUsage" value="${Tyre.TYRE_USEAGE}">
													<h3> <c:out value="${Tyre.TYRE_USEAGE}" /> </h3>
													
													</td>
												</tr>
												<tr class="row">
													<th class="key">Total Tyre Expense :</th>
													<td class="value">
														<h3>
														<a onclick="getAllExpensesByTyreId(${Tyre.TYRE_ID},1);"> <span id="totalTyreExpense"></span>	 </a>	
														</h3>
													</td>
												</tr>
												<tr class="row">
													<th class="key">Cost Per Km :</th>
													<td class="value">
														<h3>
														<span id="costPerKm"></span>
														</h3>
													</td>
												</tr>
												<tr class="row">
													<th class="key"></th>
													<td class="value"><a
														href="<c:url value="/TyreInventoryHistory?ID=${Tyre.TYRE_ID}"/>">View
															History</a></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li class="active"><a
							href="<c:url value="/TyreInventoryLife?ID=${Tyre.TYRE_ID}"/>">View
								Tyre Life Cycle</a></li>
						<li class="active"><a
							href="<c:url value="/TyreInventoryHistory?ID=${Tyre.TYRE_ID}"/>">View
								History</a></li>
						<li class="active"><a
							href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
								Inventory</a></li>
						<li class="active"><a
							href="<c:url value="/showTyreUsageHistory.in?ID=${Tyre.TYRE_ID}"/>">Tyre Usage History</a></li>
						<li class="active"><a onclick="getAllExpensesByTyreId(${Tyre.TYRE_ID},1);">Show Tyre Expenses</a></li>
				<%-- 	<li class="active">
						<a href="<c:url value="/showTyreExpenseDetails.in"/>">Show Tyre Expenses</a></li> --%>
					</ul>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${Tyre.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${Tyre.CREATED_DATE}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${Tyre.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${Tyre.LASTUPDATED_DATE}" /></small>
			</div>
		</sec:authorize>
	</section>
	
	<div class="modal fade" id="showTyreExpenseModal" role="dialog">
		<div class="modal-dialog" style="width:750px;" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				<center>
					<h4 class="modal-title" >Tyre Expense Details </h4>
				</center>	
				
				</div>
				<div class="modal-body">
					<table class="table" id="showTyreExpenseDetailsTable"  style="width:100%;" ></table>
					<br />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/ShowTyreExpenseDetails.js" />"></script>
</div>