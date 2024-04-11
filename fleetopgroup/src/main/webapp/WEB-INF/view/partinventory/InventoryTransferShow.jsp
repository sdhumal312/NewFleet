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
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>">Show
						Inventory</a> / <span id="NewVehi">Transfer Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>">Cancel</a>
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="addInventory.in" class="btn btn-success btn-sm"><span
							class="fa fa-plus"> Create Inventory</span></a>
					</sec:authorize>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getPartImage/${InventoryAll.part_photoid}.in"
							class="zoom" data-title="Part Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${InventoryAll.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${InventoryAll.partid}"
								data-toggle="tip" data-original-title="Click Part Info"> <c:out
									value="${InventoryAll.partnumber}" /> - <c:out
									value="${InventoryAll.partname}" /> - <c:out
									value="${InventoryAll.category}" /> - <c:out
									value="${InventoryAll.all_quantity}" /></a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Category">
										Category :</span> <span class="text-muted"><c:out
											value="${InventoryAll.category}" /></span></li>
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Quantity">
										Quantity :</span> <span class="text-muted"><c:out
											value="${InventoryAll.all_quantity}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<c:if test="${param.delete eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Transfer Inventory Quantity Deleted Successfully.
		</div>
	</c:if>

	<c:if test="${param.saveTransferInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Quantity Transfer Successfully.
		</div>
	</c:if>

	<!-- Modal -->
	<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form action="<c:url value="/savetransferInventoryRec.in"/>"
					method="post" name="vehicleType">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="VehicleType">Receive Transfer
							Quantity</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type">Part Name :<abbr
								title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="hidden" class="form-text" value="0"
									required="required" name="inventory_transfer_id"
									id="inventory_transfer_id" />
								<!-- Tyre Serial Num -->
								<input type="hidden" class="form-text" value=""
									required="required" name="transfer_receivedby_ID"
									id="transfer_receivedby_ID"> <input type="text"
									class="form-text" value="" id="transfer_partnumber"
									readonly="readonly" required="required" /> <label
									id="errorvType" class="error"></label>
							</div>
						</div>
						<br>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Quantity :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<!-- Amount Num -->
								<input type="text" class="form-text" value=""
									name="transfer_quantity" readonly="readonly"
									required="required"
									onkeypress="return isLabertimeKeyQut(event);"
									id="transfer_quantity">
							</div>
						</div>
						<br>
						<div class="row">
							<label class="L-size control-label" id="Type">Receive
								Remark :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<!-- Amount Num -->
								<input type="text" class="form-text" data-toggle="tip"
									data-original-title="Renewal Remark" value=""
									name="transfer_receivedReason" required="required"
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
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
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
											<th>Part</th>
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
														href="showDetailsInventory.in?inventory_id=${InventoryTransfer.transfer_inventory_id}"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${InventoryTransfer.transfer_partnumber}" /><br>
															<c:out value="${InventoryTransfer.transfer_partname}" /></a>
													</td>
													<td><c:out
															value="${InventoryTransfer.transfer_from_location}" /> <br>
														<c:out
															value="${InventoryTransfer.transfer_to_location}   " />
													</td>
													<td>
													<span class="label label-info"><c:out
														value="${InventoryTransfer.transfer_quantity}" /></span><br>
														<c:out value=" ${InventoryTransfer.transfer_description}" />
													</td>
													<td><c:out value="${InventoryTransfer.transfer_by}" />
														<c:out value=" -In- ${InventoryTransfer.transfer_via}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_date}" />
													</td>
													<td><c:out
															value="${InventoryTransfer.transfer_receivedby}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_receiveddate}" />
													</td>
													<td><sec:authorize
															access="hasAuthority('RECEIVED_PURCHASE')">
															<c:choose>
																<c:when
																	test="${InventoryTransfer.TRANSFER_STATUS =='RECEIVED'}">
																	<span class="label label-success"><c:out
																			value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
																</c:when>
																<c:otherwise>
																	<a
																		onclick="javascript:edit_RenewaLAmount('${InventoryTransfer.inventory_transfer_id}', '${InventoryTransfer.transfer_partnumber}',  '${InventoryTransfer.transfer_partname}', '${InventoryTransfer.transfer_quantity}',  '${InventoryTransfer.transfer_receivedby_ID}');"
																		id="editTyreSerialInput"
																		class="btn btn-success btn-sm"> <span
																		class="fa fa-download"></span> Receive Part
																	</a>
																	<a
																		href="transferInventoryDelete.in?TIALLID=${InventoryTransfer.transfer_inventory_all_id}&ITID=${InventoryTransfer.inventory_transfer_id}"
																		class="btn btn-danger btn-sm"> <span
																		class="fa fa-download"></span> Delete
																	</a>
																</c:otherwise>
															</c:choose>
														</sec:authorize>
														</td>
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
	function edit_RenewaLAmount(inventory_transfer_id, transfer_partnumber,
			transfer_partname, transfer_quantity, transfer_receivedby_ID) {
		
		document.getElementById("inventory_transfer_id").value = inventory_transfer_id;
		document.getElementById("transfer_partnumber").value = transfer_partnumber
				+ " " + transfer_partname;
		document.getElementById("transfer_quantity").value = transfer_quantity;
		document.getElementById("transfer_receivedby_ID").value = transfer_receivedby_ID;

		$("#editTyreRetreadNumber").modal();
		}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>