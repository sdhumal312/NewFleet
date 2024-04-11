<%@ include file="taglib.jsp"%>
<style>
.closeAmount td {
	text-align: right;
}

.closeRouteAmount td {
	text-align: right;
	font-size: 15px;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: right;
	font-size: 18px;
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
									href="<c:url value="/TDAllGroup/1.in"/>">ALL Day Collection</a>
								/ <a href="<c:url value="/closeTDAllGroup/1.in"/>">Closed ALL Day
									Collection</a> / Close Day TripCollection
							</div>
							<div class="pull-right">
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
														<c:out value="${VEHICLEGROUP}" />

													</h4>
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td colspan="4">
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Trip Day</th>
																<th class="fit ar">Department Name</th>
																<th class="fit ar">Total KM</th>
																<th class="fit ar">Diesel</th>
																<th class="fit ar">Total PSNGR</th>
																<th class="fit ar">RFID</th>
																<th class="fit ar">Collection</th>
																<th class="fit ar">Expense</th>
																<th class="fit ar">OT</th>
																<th class="fit ar">Balance</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:if test="${!empty GROUPCOL}">

																<c:forEach items="${GROUPCOL}" var="GROUPCOL">

																	<tr data-object-id="" class="closeRouteAmount">

																		<td class="fit">
																			<%
																				out.println(hitsCount);
																							hitsCount += 1;
																			%>
																		</td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TRIP_OPEN_DATE}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.VEHICLE_GROUP}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_USAGE_KM}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_DIESEL}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_TOTALPASSNGER}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_RFIDPASS}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_COLLECTION}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_EXPENSE}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_OVERTIME}" /></td>
																		<td class="fit ar"><c:out
																				value="${GROUPCOL.TOTAL_BALANCE}" /></td>
																	</tr>
																</c:forEach>
																<tr data-object-id="" class="closeGroupAmount">
																	<td colspan="3" class="fit ar"><c:out
																			value="Total:" /></td>
																	<td class="fit ar"><c:out
																			value="${TDALLGROUP.TOTAL_USAGE_KM}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDALLGROUP.TOTAL_DIESEL}" /></td>

																	<td class="fit ar"><c:out
																			value="${TDALLGROUP.TOTAL_TOTALPASSNGER}" /></td>
																	<td class="fit ar"></td>
																	<td class="fit ar"></td>
																	<td class="fit ar"></td>
																	<td class="fit ar"><c:out
																			value="${TDALLGROUP.TOTAL_OVERTIME}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDALLGROUP.BUSCOLLECTION}" /></td>

																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<div class="row">
														<%-- <c:if test="${true}">
															<div class="alert alert-info">
																<h5>
																	Please Check before closing the day Trip Collection
																	Amount Total and Balance.<br> This Amount transfer
																	to Location wise CashBook in the day
																</h5>
															</div>
														</c:if> --%>
														<form id="formCloseTripClose"
															action="<c:url value="/savecloseAllGroupTDCollection.in"/>"
															method="post" enctype="multipart/form-data"
															name="formCloseTripClose" role="form"
															class="form-horizontal">
															<br>
															<div class="form-horizontal ">
																<input class="form-text" name="TRIPALLGROUPID"
																	type="hidden" value="${TDALLGROUP.TRIPALLGROUPID}"
																	required="required">
																<input class="form-text" name="TRIPALLGROUPNUMBER"
																	type="hidden" value="${TDALLGROUP.TRIPALLGROUPNUMBER}"
																	required="required">
																<input class="form-text" name="TRIP_STATUS_ID"
																	type="hidden" value="${TDALLGROUP.TRIP_STATUS_ID}"
																	required="required">
																<input class="form-text" name="TRIP_CLOSE_STATUS"
																	type="hidden" value="${TDALLGROUP.TRIP_CLOSE_STATUS}"
																	required="required">
																<div class="row1" id="grpcloseDate" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label" for="closedate">Trip
																			Collection Close Date : <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TRIP_OPEN_DATE"
																			type="text" value="${TDALLGROUP.TRIP_OPEN_DATE}"
																			readonly="readonly" id="closeDate"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="closeDateIcon" class=""></span>
																		<div id="closeDateErrorMsg" class="text-danger"></div>
																	</div>
																</div>

																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Bus Collection :
																			<abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="BUSCOLLECTION"
																			type="text" value="${TDALLGROUP.BUSCOLLECTION}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Total Diesel QTY
																			: <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_DIESEL"
																			type="text" value="${TDALLGROUP.TOTAL_DIESEL}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">TOTAL KM : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_USAGE_KM"
																			type="text" value="${TDALLGROUP.TOTAL_USAGE_KM}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">TOTAL Passenger :
																			<abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_TOTALPASSNGER"
																			type="text" value="${TDALLGROUP.TOTAL_TOTALPASSNGER}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">OT : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_OVERTIME"
																			type="text" value="${TDALLGROUP.TOTAL_OVERTIME}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">RFID RCG : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="RFIDRCG" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">RFID CARD : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="RFIDCARD" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">RFID Usage : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="RFIDUSAGE" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Booking : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="RFIDUSAGE" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Diesel Expense :
																			<abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_DIESELEXPENSE"
																			type="text" required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Advance : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="ADVANCE" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">TOTAL WT : </label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="TOTAL_WT" type="text"
																			required="required" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
																<div class="row1" id="grpbonusCost" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Day Remarks : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<textarea rows="3" cols="5" class="form-text"
																			name="TRIP_REMARKS" required="required"></textarea>
																		<span id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
															</div>
															<fieldset class="form-actions">
																<div class="text-left">
																	<input class="btn btn-success"
																		onclick="this.style.visibility = 'hidden'"
																		name="commit" type="submit"
																		value="Close All Location TripCollection"> <a
																		class="btn btn-info"
																		href="<c:url value="/TDAllGroup/1.in"/>"><span
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
						href="newTripDaily.in">Overview</a></li>

				</ul>
			</div>
		</div>
	</section>
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionClose.js"/>"></script>
 -->
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js"/>"></script> -->
</div>