<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/newPurchaseOrders/1.in"/>">Purchase
							Orders</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PURCHASE')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addPurchaseOrder.in"/>"> <span
							class="fa fa-plus"></span> Create Purchase Order
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/PurchaseOrderReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
			<div class="row">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-folder-open-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">ORDERED</span> <span
								class="info-box-number">${StatuesCount}</span>

						</div>
					</div>
				</div>
              	<div class="col-md-3 col-sm-3 col-md-6">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Purchase Orders</span>
							<form action="<c:url value="/searchPurchaseOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">PO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="Po-NO eg:74654"> <span
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
				<div class="col-md-3 col-sm-3 col-md-6">
                  	<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Purchase Orders</span>
							<form action="<c:url value="/searchPurchaseOrder.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_id" name="Search"
										type="text" required="required"
										placeholder="Po-NO, Quote, Indent,Inv, WO-No"> <span
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
			<c:if test="${savePurchaseOrder}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Purchase Order Created successfully.
				</div>
			</c:if>
			<c:if test="${deletePurchaseOrder}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Purchase Order Deleted successfully.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Purchase Order Already Updated.
				</div>
			</c:if>
			<c:if test="${param.deleteAllTask eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please Delete all Ordered Parts &amp; DebitNote...
				</div>
			</c:if>
			<!-- alert in delete messages -->
			<c:if test="${updateIssue}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Purchase Order Updated successfully.
				</div>
			</c:if>
			<c:if test="${param.deletedanger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Purchase Order Not Deleted.
				</div>
			</c:if>

			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" ><a
										href="<c:url value="/newPurchaseOrders/1.in"/>">REQUISITION
										<span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="${REQUISITION} requistion">${REQUISITION}</span>
										</a></li>
								 	<c:if test="${configuration.requisitionApproved}"> 
										<li role="presentation"><a
											href="<c:url value="/PurchaseOrders_APPROVED/1.in"/>">REQUISITION APPROVED
											<span data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="${REQUISITIONAPPROVED} requistionapproved">${REQUISITIONAPPROVED}</span>
											</a></li>
									 </c:if>
									 <li role="presentation" class="active"><a
										href="<c:url value="/PurchaseOrders_ORDERED/1.in"/>"
										style="background-color: #87CEFA; color: #000;"
										>ORDERED
										<span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="${ORDERED} ordered">${ORDERED}</span>
										</a></li>
									<li role="presentation"><a
										href="<c:url value="/PurchaseOrders_RECEIVED/1.in"/>">RECEIVED
										<span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="${RECEIVED} received">${RECEIVED}</span>
										</a></li>
									<li role="presentation"><a
										href="<c:url value="/PurchaseOrders_COMPLETED/1.in"/>">COMPLETED
										<span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="${COMPLETED} ordered">${COMPLETED}</span>
										</a></li>		
								</ul>
							</div>
						</div>
					</div>
					<c:if test="${!empty PurchaseOrder}">
						<div class="box">
							<div class="box-body">
								<table id="PurchaseOrdersTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit ar">ID</th>
											<th class="fit ar">Date Opened</th>
											<th class="fit ar">Date Required</th>
											<th>Vendor</th>
											<th class="fit ar">Buyer</th>
											<th class="fit ar">Terms</th>
											<th class="fit ar">Indent</th>
											<th class="fit ar">Document</th>
											<sec:authorize access="hasAuthority('EDIT_AFTER_ORDERED_PURCHASE')">
												<th class="fit ar">Action</th>
											</sec:authorize>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${PurchaseOrder}" var="PurchaseOrder">

											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><a
													href="<c:url value="/PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}"/>"
													data-toggle="tip"
													data-original-title="Click Purchase Order Info"> <c:out
															value="PO-${PurchaseOrder.purchaseorder_Number}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_created_on}" /></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_requied_on}" /></td>

												<td><a
													href="<c:url value="/PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}"/>"
													data-toggle="tip"
													data-original-title="Click Purchase Order Info"> <c:out
															value="${PurchaseOrder.purchaseorder_vendor_name}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_buyer}" /></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_terms}" /></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_indentno}" /></td>
												<td><c:choose>
														<c:when
															test="${PurchaseOrder.purchaseorder_document == true}">
															<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
																<a
																	href="${pageContext.request.contextPath}/download/PurchaseorderDocument/${PurchaseOrder.purchaseorder_document_id}.in">
																	<span class="fa fa-download"> Doc</span>
																</a>
															</sec:authorize>
														</c:when>
													</c:choose></td>
												<sec:authorize access="hasAuthority('EDIT_AFTER_ORDERED_PURCHASE')">
													<c:if test="${PurchaseOrder.purchaseorder_statusId == 2}">		
														<td class="icon">
															<div class="btn-group ">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
		
																<ul class="dropdown-menu pull-right">
																	<li>
																		<a
																			href="<c:url value="/editPurchaseOrder.in?ID=${PurchaseOrder.purchaseorder_id}"/>"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Edit ')"> <span
																			class="fa fa-pencil"></span> Edit
																		</a>
																	</li>
																	<c:if test="${PurchaseOrder.purchaseorder_status == 'REQUISITION'}">
																		<li><sec:authorize
																				access="hasAuthority('DELETE_PURCHASE')">
																				<a
																					href="<c:url value="/deletePurchaseOrder.in?ID=${PurchaseOrder.purchaseorder_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure? Delete ')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</c:if>
																</ul>
															</div>
														</td>
													</c:if>	
												</sec:authorize>	
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<c:url var="firstUrl" value="/PurchaseOrders_ORDERED/1" />
						<c:url var="lastUrl"
							value="/PurchaseOrders_ORDERED/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/PurchaseOrders_ORDERED/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/PurchaseOrders_ORDERED/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/PurchaseOrders_ORDERED/${i}" />
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
					</c:if>
					<c:if test="${empty PurchaseOrder}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">
								<spring:message code="label.master.noresilts" />
							</p>
						</div>
					</c:if>
				</div>
			</div>
		</sec:authorize>
	</section>
</div>
