<%@ include file="taglib.jsp"%>
<style>
.closeAmount td , th{
	text-align: right;
}

.closeGroupAmount td {
	text-align: right;
	font-size: 15px;
	font-weight: bold;
}

.actualkm {
	width: 0.8%;
	float: left;
}
</style>
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripCol.in"/>">Trip Collection</a> / <a
									href="<c:url value="/manageTripCol/1.in"/>">Manage Trip</a> / <a
									href="<c:url value="/closeTripCol/1.in"/>">Close Trip</a> /
								Close Daily TripCollection
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/addTripCol.in"/>"> <span
										class="fa fa-plus"></span> Create TripCollection
									</a>
								</sec:authorize>
								<%-- <sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
									<a href="showTripSheetCol.in?id=${TripCol.TRIPCOLLID}"
										target="_blank" class="btn btn-default"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize> --%>
							</div>
						</div>
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div class="row">
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="${VEHICLE_GROUP}" />

													</h4>
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td colspan="4">
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb closeAmount">
																<th class="fit">No</th>
																<th class="fit ar">Group</th>
																<th class="fit ar">Bus Name</th>
																<th class="fit ar">Driver/Conductor</th>
																<th class="fit ar">Collection</th>
																<th class="fit ar">Expense</th>
																<th class="fit ar">Diesel</th>
																<th class="fit ar">Balance</th>
																<th class="fit ar">Singl</th>
																<th class="fit ar">Run Bus</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:if test="${!empty TripGroupCol}">

																<c:forEach items="${TripGroupCol}" var="TripGroupCol">
																	<c:choose>
																		<c:when
																			test="${TripGroupCol.TRIP_CLOSE_STATUS == 'TOTAL:'}">
																			<tr data-object-id="" class="closeGroupAmount">

																				<td colspan="2" class="fit ar"><c:out
																						value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_CLOSE_STATUS}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_DRIVER_NAME}" /><br>
																					<c:out value="${TripGroupCol.TRIP_CONDUCTOR_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_SINGL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BUS}" /></td>

																			</tr>
																		</c:when>
																		<c:otherwise>
																			<tr data-object-id="" class="closeAmount">
																				<td class="fit">
																					<%
																						out.println(hitsCount);
																											hitsCount += 1;
																					%>
																				</td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																				<td class="fit ar"><a target="_blank"
																					href="<c:url value="/showTripCol.in?ID=${TripGroupCol.TRIPGROUPID}"/>"><c:out
																							value="${TripGroupCol.TRIP_CLOSE_STATUS}" /></a><br>
																					<c:out value="${TripGroupCol.TRIP_ROUTE_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_DRIVER_NAME}" /><br>
																					<c:out value="${TripGroupCol.TRIP_CONDUCTOR_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_SINGL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BUS}" /></td>

																			</tr>
																		</c:otherwise>
																	</c:choose>
																	<c:if
																		test="${vehicleGroup.vGroup == TripGroupCol.VEHICLE_GROUP}">

																	</c:if>
																</c:forEach>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="5"></td>
											</tr>
											<tr>
												<td colspan="4">
													<div class="row">
														<form id="formCloseTripClose"
															action="<c:url value="/closeDayTripCollection.in"/>"
															method="post" enctype="multipart/form-data"
															name="formCloseTripClose" role="form"
															class="form-horizontal">
															<br>
															<div class="form-horizontal ">
																<div class="row1" id="grpcloseDate" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label" for="closedate">Trip
																			Collection Close Date : <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<div class="col-md-4">
																			<input class="form-text" name="closedate" type="text"
																				value="${TRIP_OPEN_DATE}" readonly="readonly"
																				id="closeDate"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="closeDateIcon" class=""></span>
																			<div id="closeDateErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="row">
																			<div class="pull-right">

																				<label class="control-label" for="closedate">Total
																					Running Bus : </label> <label class="control-label"
																					for="closedate"><c:out
																						value="${TRUNINGBUS}"></c:out> </label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="row1" id="grptotalCollection"
																	class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Total Collection
																			: <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<div class="col-md-4">
																			<input class="form-text" name="TOTAL_COLLECTION"
																				type="text" value="${TOTAL_COLLECTION}"
																				readonly="readonly" id="totalCollection"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="totalCollectionIcon" class=""></span>
																			<div id="totalCollectionErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="row">
																			<div class="pull-right">
																				<label class="control-label" for="closedate">Total
																					Running Singl : </label> <label class="control-label"
																					for="closedate"><c:out value="${TSINGL}"></c:out>
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Per Day Bus Singl
																			: <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<div class="col-md-4">
																			<div class="input-group">
																				<input class="form-text" name="PER_DAY_SINGL"
																					type="text" maxlength="8" value="14"
																					placeholder="eg : 14" id="perSingl"
																					onkeypress="return isNumberKey(event,this);"
																					ondrop="return false;">
																				<div class="input-group-addon">per</div>
																			</div>
																			<span id="perSinglIcon" class=""></span>
																			<div id="perSinglErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="row">
																			<div class="pull-right">
																				<label class="control-label" for="closedate">Total
																					Cut Singl : </label> <label class="control-label"
																					for="closedate"><c:out value="${TCUTSINGL}"></c:out>
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="row1" id="grpsalarycost" class="form-group">
																	<div class="col-md-2">
																		<label class="control-label">Salary + Curency:<abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-8">
																		<div class="col-md-1">
																			<input class="form-text" id="actual" type="hidden"
																				value="1">
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="salarycost"
																				name="STAFF_SALARY" placeholder="salary cost"
																				onkeyup="javascript:totalCost('actual', 'salarycost','salaryTotal');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="salarycostIcon" class=""></span>
																			<div id="salarycostErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">=</label>
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="salaryTotal" type="text"
																				readonly="readonly" value="0" required="required">
																		</div>
																	</div>
																</div>
																<div class="row1" id="grprollNumber" class="form-group">
																	<div class="col-md-2">
																		<label class="control-label">Ticket Roll : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-8">
																		<div class="col-md-2">
																			<input class="form-text" id="rollNumber"
																				name="ROLL_NUMBER" placeholder="total roll"
																				onkeyup="javascript:totalCost('rollNumber', 'rollPrice','RollCost');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="rollNumberIcon" class=""></span>
																			<div id="rollNumberErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">X</label>
																		</div>
																		<div class="col-md-1">
																			<input class="form-text" id="rollPrice"
																				name="ROLL_PRICE" placeholder="roll price"
																				onkeyup="javascript:totalCost('rollNumber', 'rollPrice','RollCost');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="rollPriceIcon" class=""></span>
																			<div id="rollPriceErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">=</label>
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="RollCost"
																				name="TICKET_ROLL" type="text" required="required"
																				value="0" readonly="readonly">
																		</div>
																	</div>
																</div>
																<div class="row1" id="grpmachanicCost"
																	class="form-group">
																	<div class="col-md-2">
																		<label class="control-label">Mechanic Main : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-9">
																		<div class="col-md-1">
																			<input class="form-text" id="machanicValue"
																				type="hidden" value="1">
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="machanicCost"
																				name="MECHANIC_MAINTANCE"
																				placeholder="machanic cost"
																				onkeyup="javascript:totalCost('machanicValue', 'machanicCost','machanicTotal');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="machanicCostIcon" class=""></span>
																			<div id="machanicCostErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">=</label>
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="machanicTotal" value="0"
																				type="text" maxlength="8" min="0"
																				required="required" readonly="readonly">
																		</div>
																	</div>
																</div>
																<div class="row1" id="grpinsurenceCost"
																	class="form-group">
																	<div class="col-md-2">
																		<label class="control-label">INC+F/D+E/S : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-9">
																		<div class="col-md-1">
																			<input class="form-text" id="insuValue" type="hidden"
																				value="1">
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="insurenceCost"
																				name="INSURENCE_MAINTANCE"
																				placeholder="insurence cost"
																				onkeyup="javascript:totalCost('insuValue', 'insurenceCost','insurenceTotal');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="insurenceCostIcon" class=""></span>
																			<div id="insurenceCostErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">=</label>
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="insurenceTotal"
																				value="0" type="text" maxlength="8" min="0"
																				required="required" readonly="readonly">
																		</div>
																	</div>
																</div>
																<div class="row1" id="grpbonusCost" class="form-group">
																	<div class="col-md-2">
																		<label class="control-label">D/C Bonus : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-9">
																		<div class="col-md-1">
																			<input class="form-text" id="bonusValue"
																				type="hidden" value="1">
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="bonusCost"
																				name="DC_BONUS" placeholder="bonus cost"
																				onkeyup="javascript:totalCost('bonusValue', 'bonusCost','bonusTotal');"
																				type="text" maxlength="8" value="0"
																				onkeypress="return isNumberKey(event,this);"
																				ondrop="return false;"> <span
																				id="bonusCostIcon" class=""></span>
																			<div id="bonusCostErrorMsg" class="text-danger"></div>
																		</div>
																		<div class="actualkm">
																			<label class="control-label">=</label>
																		</div>
																		<div class="col-md-2">
																			<input class="form-text" id="bonusTotal" value="0"
																				type="text" maxlength="8" min="0"
																				required="required" readonly="readonly">
																		</div>
																	</div>
																</div>
															</div>
															<fieldset class="form-actions">

																<div class="text-left">

																	<input class="btn btn-success"
																		onclick="this.style.visibility = 'hidden'"
																		name="commit" type="submit"
																		value="Close Day Trip Collection"> <a
																		class="btn btn-info"
																		href="<c:url value="/newTripCol.in"/>"><span
																		id="Cancel">Cancel</span></a>
																</div>

															</fieldset>
														</form>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a class="btn btn-default btn-sm"
						href="newTripCol.in">Overview</a></li>

				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionClose.js" />"></script>

	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js" />"></script> -->
</div>