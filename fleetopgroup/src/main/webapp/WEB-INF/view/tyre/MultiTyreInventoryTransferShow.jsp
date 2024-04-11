<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
						Inventory</a> /<span id="NewVehi">Transfer
						Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/TyreInventoryNew/1.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('RECEIVE_MULTI_TYRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			
		</div>
	</section>
	<c:if test="${param.deleteInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Tyre Deleted Successfully.
		</div>
	</c:if>
	
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
			This Inventory Tyre Received Successfully.
		</div>
	</c:if>

	<!-- Modal -->
	<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form action="<c:url value="/saveMultiTransferTyreInventory.in"/>"
					method="post" name="vehicleType">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="VehicleType">Receive Transfer
							Tyre</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type">Tyre Name :<abbr
								title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="hidden" class="form-text" value=""
									required="required" name="TYRE_ID" id="TYRE_ID" />
								<!-- Tyre Serial Num -->
								<input type="hidden" class="form-text" value=""
									required="required" name="ITTID" id="inventory_transfer_id" />
								<!-- Tyre Serial Num -->
								<input type="text" class="form-text" value=""
									id="transfer_partnumber" readonly="readonly"
									required="required" /> <label id="errorvType" class="error"></label>
							</div>
						</div>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Tyre :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" value=""
									name="transfer_quantity" readonly="readonly"
									required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="transfer_quantity">
							</div>
						</div>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive By
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" data-toggle="tip"
									data-original-title="Renewal Cost" value=""
									name="TRANSFER_RECEIVEDBYEMAIL" readonly="readonly"
									required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="transfer_receivedbyEmail">
							<input type="hidden" name ="TRANSFER_RECEIVEDBYID" id="TRANSFER_RECEIVEDBYID" value="0" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Remark :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" data-toggle="tip"
									data-original-title="Renewal Remark" value=""
									name="TRANSFER_RECEIVEDREASON" required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="transfer_receivedReason">
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
		<sec:authorize access="!hasAuthority('RECEIVE_MULTI_TYRE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('RECEIVE_MULTI_TYRE')">
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
											<th>Tyre</th>
											<th>Transfer From &amp; To</th>
											<th>Quantity</th>
											<th>Transfer_By / T-Date</th>
											<th>Received By</th>
											<th>Status</th>

										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty InventoryTransfer}">
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${InventoryTransfer}"
												var="InventoryTransfer">
												<tr>
													<td>
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>

													<td><a
														href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${InventoryTransfer.TYRE_NUMBER}" /></a></td>
													<td><c:out
															value="${InventoryTransfer.TRA_FROM_LOCATION}" /> <br>
														<c:out value="${InventoryTransfer.TRA_TO_LOCATION}   " /></td>

													<td><span class="label label-info"><c:out
																value="${InventoryTransfer.TRA_QUANTITY}" /></span><br> <c:out
															value=" ${InventoryTransfer.TRANSFER_REASON}" /></td>
													<td><c:out value="${InventoryTransfer.TRANSFER_BY}" />
														<c:out value=" -In- ${InventoryTransfer.TRANSFER_VIA}" /><br>
														<c:out value=" ${InventoryTransfer.TRANSFER_DATE}" /></td>
													<td><c:out
															value="${InventoryTransfer.TRANSFER_RECEIVEDBY}" /><br>
														<c:out value=" ${InventoryTransfer.TRANSFER_RECEIVEDDATE}" /></td>
													<td><sec:authorize
															access="hasAuthority('RECEIVED_PURCHASE')">
															<c:choose>
																<c:when
																	test="${InventoryTransfer.TRANSFER_STATUS_ID == 2}">
																	<span class="label label-success"><c:out
																			value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
																</c:when>
																<c:otherwise>
																	<a
																		onclick="javascript:edit_RenewaLAmount('${InventoryTransfer.TYRE_ID}','${InventoryTransfer.ITTID}', '${InventoryTransfer.TYRE_NUMBER}',   '${InventoryTransfer.TRA_QUANTITY}',  '${InventoryTransfer.TRANSFER_RECEIVEDBYEMAIL}','${InventoryTransfer.TRANSFER_RECEIVEDBY_ID}');"
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
		function edit_RenewaLAmount(TYRE_ID, inventory_transfer_id, transfer_partnumber,
				 transfer_quantity, transfer_receivedbyEmail, TRANSFER_RECEIVEDBYID) {
			document.getElementById("TYRE_ID").value = TYRE_ID;
			document.getElementById("inventory_transfer_id").value = inventory_transfer_id;
			document.getElementById("transfer_partnumber").value = transfer_partnumber;
			document.getElementById("transfer_quantity").value = transfer_quantity;
			document.getElementById("transfer_receivedbyEmail").value = transfer_receivedbyEmail;
			document.getElementById("TRANSFER_RECEIVEDBYID").value = TRANSFER_RECEIVEDBYID;

			$("#editTyreRetreadNumber").modal();
		}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>