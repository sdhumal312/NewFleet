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
	font-size: 15px;
	font-weight: bold;
}

.closeGroup span {
	text-align: right;
	font-size: 16px;
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
											<tr class="closeGroup">
												<td>Collection Date :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TRIP_OPEN_DATE}" /> </span></td>
												<td>BUS Collection :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.BUSCOLLECTION}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>RFID RCG :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.RFIDRCG}" /> </span></td>
												<td>RFID CARD :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.RFIDCARD}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>RFID USAGE :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.RFIDUSAGE}" /> </span></td>
												<td>BOOKING :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.BOOKING}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>TOTAL DIESEL :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_DIESEL}" /> </span></td>
												<td>TOTAL USAGE KM :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_USAGE_KM}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>TOTAL PASSENGER :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_TOTALPASSNGER}" /> </span></td>
												<td>TOTAL DIESEL EXPENSE :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_DIESELEXPENSE}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>TOTAL OVERTIME :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_OVERTIME}" /> </span></td>
												<td>ADVANCE :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.ADVANCE}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td>TOTAL WT :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_WT}" /> </span></td>
												<td>MILAGE :</td>
												<td><span class="I-size"><c:out
															value="${TDALLGROUP.TOTAL_DIESEL_MILAGE}" /> </span></td>
											</tr>
											<tr class="closeGroup">
												<td colspan="4">
													<div class="row">
														<div class="form-horizontal ">
															<div class="row1" class="form-group">
																<div class="col-md-10">
																	<label class="control-label L-size">COLLECTION BALANCE:</label> <span class="I-size"><c:out
																			value="${TDALLGROUP.COLLECTION_BALANCE}" /></span>
																</div>
															</div>
															<div class="row1" class="form-group">
																<div class="col-md-10">
																	<label class="control-label L-size">EXPENSE OF
																		DAY : </label> <span class="I-size"><c:out
																			value="${TDALLGROUP.EXPENSE_DAY}" /></span>
																</div>
															</div>
															<div class="row1" class="form-group">
																<div class="col-md-10">
																	<label class="control-label L-size">TOTAL
																		BALANCE : </label> <span class="I-size"><c:out
																			value="${TDALLGROUP.TOTAL_BALANCE}" /></span>
																</div>
															</div>

														</div>
													</div>
												</td>
											</tr>
											<tr class="">
												<td>Closed By:</td>
												<td><c:out value="${TDALLGROUP.LASTMODIFIEDBY}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="">
												<td>Closed Date:</td>
												<td><c:out value="${TDALLGROUP.LASTUPDATED}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="">
												<td>Closed Remark :</td>
												<td><c:out value="${TDALLGROUP.TRIP_REMARKS}" /></td>
												<td colspan="2"></td>
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