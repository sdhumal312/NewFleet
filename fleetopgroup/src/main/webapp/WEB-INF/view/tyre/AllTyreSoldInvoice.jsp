<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadinventorytyredocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryTyreReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a href="<c:url value="/addTyreInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Tyre Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	
						<a href="<c:url value="/TyreExpenseDetails.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Tyre Expense</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Filter
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<sec:authorize access="hasAuthority('ADD_SOLD_FILTER')">
								<a href="<c:url value="/soldFilter.in"/>" class="btn btn-sm">
								<span class="fa fa-search">Sold Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_RETREAD')">
								<a href="<c:url value="/RetreadFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Retread Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_SCRAP')">
								<a href="<c:url value="/ScrapFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Scrap Filter</span></a>
							</sec:authorize>
						</li>
					</ul>
					</div>
					</sec:authorize>
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Transfer
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
					<sec:authorize access="hasAuthority('TRANSFER_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/multiTransferTyreInventory.in"/>"> <span
							class="fa fa-exchange"></span>Transfered Multiple Tyre
						</a>
					</sec:authorize>
						</li>
						<li>
					<sec:authorize access="hasAuthority('RECEIVE_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/receiveMultipleTyreTransfered.in"/>"> <span
							class="fa fa-check-circle "></span> Receive Transfered Tyre
						</a>
					</sec:authorize>
					</li>
					</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importInventoryTyre.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1">
											<div class="L-size">
												<label> Import Only xlsx File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".xlsx" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Inventory Tyre Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Inventory Tyre files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton')
												.on(
														'click',
														function() {
															//alert("hi da")
															$(".progress-bar")
																	.animate(
																			{
																				width : "100%"
																			},
																			2500);
															var $btn = $(this)
																	.button(
																			'loading')
															// business logic...

															$btn
																	.button('reset')
														})
									</script>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<sec:authorize access="!hasAuthority('VIEW_TYRE_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
			<%-- <input type="hidden" id="valuesInserted" value="<%= request.getParameter("valuesInserted")%>">
			<input type="hidden" id="valuesRejected" value="<%= request.getParameter("valuesRejected")%>"> --%>
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Tyre Sold Invoice</span> 
							<span class="info-box-number" id= "tyreSoldInvoiceCount"></span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<%-- <div class="info-box-center">
							<span class="info-box-text">Search Tyre Invoice</span>
							<form action="<c:url value="/searchInvoiceInventory.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" name="Search" type="text"
										required="required" placeholder="Only Invoice No"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div> --%>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre No</span>
							<form action="<c:url value="/searchTyreInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" name="Search" type="text"
										required="required" placeholder="Only Tyre No, Size">
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
			
			<div class="row">
				<div class="col-xs-11">
					<div class="main-tabs">
						<ul class="nav nav-pills">
							<li role="presentation" id="All" class=""><a
								href="<c:url value="/TyreInventory/1"/>">TYRE INVOICE</a></li>
							<li role="presentation" id="All" class=""><a
								href="<c:url value="/TyreRetreadNew/1"/>">TYRE RETREAD
									INVOICE</a></li>
							<li role="presentation" id="sold" class="active"><a
								href="<c:url value="/allTyreSoldInvoice"/>">TYRE SOLD INVOICE</a></li>		

							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}">
										<a class="btn btn-link" href="<c:url value="/locationTyreInventory/1.in?loc=${PartLocations.partlocation_id}"/>">${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<br>
					<div class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table table-hover table-bordered">
											<thead>
												<tr>
													<th class="fit ar">ID</th>
													<th class="fit ar">Invoice Date</th>
													<th class="fit ar">Created By</th>
													<th class="fit ar">Sold Status</th>
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											<tbody id="tyreSoldInvoiceTable">
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="text-center">
								<ul id="navigationBar" class="pagination pagination-lg pager"> </ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
</div>

<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/AllTyreSoldInvoice.js"/>"></script>	

</div>