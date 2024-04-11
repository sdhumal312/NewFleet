<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/PartRequisition/1/1.in"/>">Create Part
						Requisition</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_REQUISITION_INVENTORY')">
						<a href="<c:url value="/addPartRequisition.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Part Requisition</span></a>
					</sec:authorize>
					<%-- <sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/InventoryQRscan.in"/>"> <span
							class="fa fa-search "></span> QR Code Scan
						</a>
					</sec:authorize> --%>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a href="<c:url value="/YTIHISTORY/1.in"/>"
							class="btn btn-warning btn-sm">Your Transfer History</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_REQUISITION_INVENTORY')">
						<a href="<c:url value="/SearchPartRequisition.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Search Part Requisition</span></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Requisition</span> <span
								class="info-box-number">${InventoryCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
							<form action="<c:url value="/searchInvenPartReq.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">IR-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="SearchInveReq" type="number"
										min="1" required="required" placeholder="IR-ID">
									<span class="input-group-btn">
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
			<c:if test="${param.sequenceNotFound eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Sequence Not Defined Please Contact To System Administrator!
				</div>
			</c:if>
			<c:if test="${param.updateInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Updated Successfully.
				</div>
			</c:if>
			<c:if test="${param.deleteInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Quantity Deleted Successfully.
				</div>
			</c:if>
			<c:if test="${deleteInventoryChild}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please Delete First From Inventory Location .....
				</div>
			</c:if>
			<c:if test="${param.alreadyInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.dangerLocation eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please delete this part inside all location Quantity...
				</div>
			</c:if>
			<c:if test="${param.dangerAllInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="1"><a
										href="<c:url value="/PartRequisition/1/1.in"/>">OPEN</a></li>
									<li role="presentation" id=2><a
										href="<c:url value="/PartRequisition/2/1.in"/>">REQUISTION</a></li>
									<li role="presentation" id="3"><a
										href="<c:url value="/PartRequisition/3/1.in"/>">TRANSFERED</a></li>
									<li role="presentation" id="4"><a
										href="<c:url value="/PartRequisition/4/1.in"/>">COMPLETED</a></li>
									<li role="presentation" id="5"><a
										href="<c:url value="/RejectedPartRequisition/1.in"/>">REJECTED</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-xs-11">
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table table-hover table-bordered">
											<thead>
												<tr>
													<th class="fit ar">ID</th>
													<th class="fit ar">Location</th>
													<th class="fit ar">Required Parts</th>
													<th class="fit ar">Total Quantity</th>
													<th class="fit ar">Sent_By</th>
													<th class="fit ar">Req.Date</th>
													<th class="fit ar">Req.Assign</th>
													<th class="fit ar">Status</th>
													<th class="fit ar">Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty InvRequisition}">
													<c:forEach items="${InvRequisition}" var="InvRequisition">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/${SelectStatus}/${SelectPage}/showInventoryReq.in?ID=${InvRequisition.INVRID}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="IR-${InvRequisition.INVRID_NUMBER}" /> </a></td>
															<td class="fit ar"><c:out
																	value="${InvRequisition.REQUITED_LOCATION}" /></td>
															<td class="fit ar">
															<a href="#" onclick="showAllPart('${InvRequisition.INVRID}');">${InvRequisition.PART_NAME} </a>
															</td>
															<td class="fit ar"><c:out
																	value="${InvRequisition.PART_REQUITED_QTY}" /></td>
															<td class="fit ar"><c:out
																	value="${InvRequisition.REQUITED_SENDNAME}" /></td>
															<td class="fit ar"><c:out
																	value="${InvRequisition.REQUITED_DATE}" /></td>
															<td class="fit ar"><c:out
																	value="${InvRequisition.REQUISITION_RECEIVEDNAME}" /></td>
															<td class="fit ar"><c:choose>
																	<c:when
																		test="${InvRequisition.REQUISITION_STATUS == 'REQUISITION'}">
																		<span class="label label-default label-success">
																			<c:out value="${InvRequisition.REQUISITION_STATUS}" />
																		</span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-default label-warning">
																			<c:out value="${InvRequisition.REQUISITION_STATUS}" />
																		</span>
																	</c:otherwise>
																</c:choose></td>

															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('ADD_REQUISITION_INVENTORY')">
																				<a
																					href="<c:url value="/${SelectStatus}/${SelectPage}/showInventoryReq.in?ID=${InvRequisition.INVRID}"/>">
																					<span class="fa fa-pencil"></span> Show Quantity
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_REQUISITION_INVENTORY')">
																				<a
																					href="<c:url value="/deleteAllInventoryReq.in?ID=${InvRequisition.INVRID}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Part?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<c:url var="firstUrl" value="/PartRequisition/${SelectStatus}/1" />
							<c:url var="lastUrl"
								value="/PartRequisition/${SelectStatus}/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/PartRequisition/${SelectStatus}/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/PartRequisition/${SelectStatus}/${currentIndex + 1}" />
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
										<c:url var="pageUrl" value="/PartRequisition/${SelectStatus}/${i}" />
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
				</div>
			</div>

			<div class="modal fade" id="partModal" role="dialog">
				<div class="modal-dialog modal-lg" style="width: 1000px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn btn-danger"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Part Requisition Details</h4>
						</div>
						<div class="modal-body">
							<fieldset>
								<div class="box">
									<table class="box-body" id="modelBodyPartReqDetails" border="1" style="width:100% ">
									</table>
								</div>
								<div class="text-center">
									<ul id="navigationBar" class="pagination pagination-lg pager"> </ul>
								</div>
							</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>


		</sec:authorize>
	</section>
	
	<input type="hidden" value="${SelectStatus}" id="statues">
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>

</div>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/part/ShowPartRequisition.js" />"></script>