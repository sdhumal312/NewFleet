<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<section class="content">

		<c:if test="${param.InAnotherTrip eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				${InAnother} Please Check..
			</div>
		</c:if>


		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<div class="box">

						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active"><a
										href="<c:url value="/YTIHISTORY/1.in"/>"> TRANSFER PARTS</a></li>

									<li role="presentation"><a
										href="<c:url value="/YTIRECEIVEDHISTORY/1.in"/>"> RECEIVED
											PARTS</a></li>

								</ul>
							</div>
						</div>
					</div>
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
														href="<c:url value="/showDetailsInventory.in?inventory_id=${InventoryTransfer.transfer_inventory_id}"/>"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"><c:out
																value="${InventoryTransfer.transfer_partnumber}" /><br>
															<c:out value="${InventoryTransfer.transfer_partname}" /></a></td>
													<td><c:out
															value="${InventoryTransfer.transfer_from_location}" /> <br>
														<c:out
															value="${InventoryTransfer.transfer_to_location}   " /></td>


													<td><span class="label label-info"><c:out
																value="${InventoryTransfer.transfer_quantity}" /></span><br>
														<c:out value=" ${InventoryTransfer.transfer_description}" /></td>
													<td><c:out value="${InventoryTransfer.transfer_by}" />
														<c:out value=" -In- ${InventoryTransfer.transfer_via}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_date}" /></td>
													<td><c:out
															value="${InventoryTransfer.transfer_receivedby}" /><br>
														<c:out value=" ${InventoryTransfer.transfer_receiveddate}" /></td>
													<td><sec:authorize
															access="hasAuthority('RECEIVED_PURCHASE')">
															<c:choose>
																<c:when
																	test="${InventoryTransfer.TRANSFER_STATUS =='RECEIVED'}">
																	<span class="label label-success"><c:out
																			value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
																</c:when>
																<c:when
																	test="${InventoryTransfer.TRANSFER_STATUS =='REJECTED'}">
																	<span class="label label-danger"><c:out
																			value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-danger"><c:out
																			value="NOT RECEIVED" /></span>
																	<a
																		href="<c:url value="/transferInventoryDelete.in?TIALLID=${InventoryTransfer.transfer_inventory_all_id}&ITID=${InventoryTransfer.inventory_transfer_id}"/>"
																		class="btn btn-danger btn-sm"> <span
																		class="fa fa-download"></span> Delete
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
					<c:url var="firstUrl" value="/YTIHISTORY/1" />
					<c:url var="lastUrl"
						value="/YTIHISTORY/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/YTIHISTORY/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/YTIHISTORY/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/YTIHISTORY/${i}" />
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
				</div>
			</div>
		</sec:authorize>
	</section>
</div>