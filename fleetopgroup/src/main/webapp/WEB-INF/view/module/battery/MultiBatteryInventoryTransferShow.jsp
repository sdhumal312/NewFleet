<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="BatteryInventory.in">New Battery
						Inventory</a> /<span id="NewVehi">Transfer
						Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="BatteryInventory.in">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('TRANSFER_BATTERY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			
		</div>
	</section>
	
	
	<c:if test="${param.danger eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Some Error Occered!.
		</div>
	</c:if>

	<c:if test="${param.saveTransferInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Tyre Transfer Successfully.
		</div>
	</c:if>
	<c:if test="${param.received eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Battery Received Successfully.
		</div>
	</c:if>

	<!-- Modal -->
	<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form action="<c:url value="/receiveTransferBatteryInventory.in"/>"
					method="post" name="vehicleType">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="VehicleType">Receive Transfered
							Battery</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type">Battery Name :<abbr
								title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="hidden" class="form-text" value=""
									required="required" name="batteryId" id="batteryId" />
								<!-- Tyre Serial Num -->
								<input type="hidden" class="form-text" value=""
									required="required" name="batteryTransferId" id="batteryTransferId" />
								<!-- Tyre Serial Num -->
								<input type="text" class="form-text" value=""
									id="transfer_partnumber" readonly="readonly"
									required="required" /> <label id="errorvType" class="error"></label>
							</div>
						</div>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Battery :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" value=""
									name="transferQuantity" readonly="readonly"
									required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="transferQuantity">
							</div>
						</div>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive By
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" data-toggle="tip"
									data-original-title="Renewal Cost" value=""
									name="receiveBy" readonly="readonly"
									required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="receiveBy">
							<input type="hidden" name ="receiveById" id="receiveById" value="0" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Remark :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" data-toggle="tip" maxlength="50"
									data-original-title="Renewal Remark" value=""
									name="receiveRemark" required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="receiveRemark">
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-success">
							<span id="Save">Receive</span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<section class="content-body">
		<sec:authorize access="!hasAuthority('TRANSFER_BATTERY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRANSFER_BATTERY')">
			<div class="row1" style="padding: 25px;">
				<div class="">
					<h4>Transfer Quantity History</h4>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="InventoryTable_Location" class="table table-striped">
									<thead>
										<tr>
											<th>Id</th>
											<th>Battery</th>
											<th>Transfer From &amp; To</th>
											<th>Quantity</th>
											<th>Transfer_By / T-Date</th>
											<th>Received By</th>
											<th>Status</th>

										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty batteryTransfer}">
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${batteryTransfer}"
												var="batteryTransfer">
												<tr>
													<td>
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>

													<td><a
														href="<c:url value="/showBatteryInformation.in?Id=${batteryTransfer.batteryId}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${batteryTransfer.batterySerialNumber}" /></a></td>
													<td><c:out
															value="${batteryTransfer.fromLocationName}" /> <br>
														<c:out value="${batteryTransfer.toLocationName}   " /></td>

													<td><span class="label label-info"><c:out
																value="${batteryTransfer.transferQuantity}" /></span><br> <c:out
															value=" ${batteryTransfer.transferReason}" /></td>
													<td><c:out value="${batteryTransfer.transferBy}" />
														<c:out value=" -In- ${batteryTransfer.transferVia}" /><br>
														<c:out value=" ${batteryTransfer.transferDateStr}" /></td>
													<td><c:out
															value="${batteryTransfer.receiveBy}" /><br>
														<c:out value=" ${batteryTransfer.receiveDateStr}" /></td>
													<td><sec:authorize
															access="hasAuthority('TRANSFER_BATTERY')">
															<c:choose>
																<c:when
																	test="${batteryTransfer.transferStausId == 2}">
																	<span class="label label-success"><c:out
																			value="${batteryTransfer.transferStaus}" /></span>
																</c:when>
																<c:otherwise>
																	<a
																		onclick="javascript:edit_RenewaLAmount('${batteryTransfer.batteryId}','${batteryTransfer.batteryTransferId}', '${batteryTransfer.batterySerialNumber}',   '${batteryTransfer.transferQuantity}',  '${batteryTransfer.receiveBy}','${batteryTransfer.receiveById}');"
																		id="editTyreSerialInput"
																		class="btn btn-success btn-sm"> <span
																		class="fa fa-download"></span> Receive Part
																	</a>
																</c:otherwise>
															</c:choose>
														</sec:authorize></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript">
		function edit_RenewaLAmount(batteryId, batteryTransferId, transfer_partnumber,
				transferQuantity, receiveBy, receiveById) {
			document.getElementById("batteryId").value = batteryId;
			document.getElementById("batteryTransferId").value = batteryTransferId;
			document.getElementById("transfer_partnumber").value = transfer_partnumber;
			document.getElementById("transferQuantity").value = transferQuantity;
			document.getElementById("receiveBy").value = receiveBy;
			document.getElementById("receiveById").value = receiveById;

			$("#editTyreRetreadNumber").modal();
		}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>