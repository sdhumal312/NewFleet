<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">


<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					 <a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> /
					<a href="<c:url value="/dealerServiceEntries.in"/>">Dealer Service Entries</a>
				</div>
				<div class="col-md-off-6">
					<div class="col-md-3">
						<div class="input-group">
						<span class="input-group-addon"> <span  aria-hidden="true">DSE-</span></span>
							<input class="form-text" id="searchByNumber" name="Search" type="number" min="1" required="required" placeholder="ID eg: 2323"> 
							<span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return searchDSEByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
					<div class="col-md-3">
						<div class="input-group">
							<input class="form-text" id="searchByfilter" name="Search"+
								 type="number" required="required" min="1" placeholder="ID, V-Name, Invoice-No"> 
							<span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return searchDSEByFilter();"
									class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
							
						</div>
					</div>
					
					<sec:authorize access="hasAuthority('ADD_DEALER_SERVICE_ENTRIES')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createDealerServiceEntries?issue =0"/>"> 
							<span class="fa fa-plus"></span> Create DSE
						</a>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
						<%-- <a class="btn btn-info btn-sm" href="<c:url value="/searchDealerServiceEntries.in"/>"> 
						<span class="fa fa-search "></span> Search
						</a> --%>
						<a class="btn btn-warning btn-sm" data-toggle="modal" 
							data-target="#searchDealerServiceEntriesByDate" data-whatever="@mdo" data-toggle="tip"
							data-original-title="click this for trip Details"> 
							<span class="fa fa-search"></span> Search DSE By Date
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="searchDealerServiceEntriesByDate" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Dealer Service Entries By Date</h4>
					</div>
						<div class="modal-body">
							
							<div class="row" id="grpReportDailydate" class="form-group">
								
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="dealerServiceSearchDate" class="form-text"
										name="searchDate" required="required" readonly="readonly"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
										
								</div>
								<c:if test="${configuration.showRemark}">
						<input id="vehicleId" style="width: 100%"  placeholder="Search by Vehicle no"/></c:if>
							</div>
						</div>
						
						<div class="modal-footer">
							<button type="submit" onclick="searchDealerServiceEntriesDateWise();" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
					<input type="hidden" id="companyId" value="${companyId}">	
					<input type="hidden" id="userId" value="${userId}">	
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="all">
										<a onclick="showList(0,1,0)">All  
											<span data-toggle="tip" id ="allCount" class="badge bg-blue" ></span>
										</a>
									</li>
									<li role="presentation" id=allOpen>
										<a onclick="showList(1,1,0)">In Process
											<span data-toggle="tip" id ="allOpenCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=invoicePending>
										<a onclick="showList(1,1,1)">INVOICE PENDING
											<span data-toggle="tip" id ="invoicePendingCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=invoiceReceived>
										<a onclick="showList(1,1,2)">INVOICE RECEIVED
											<span data-toggle="tip" id ="invoiceReceivedCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=hold>
										<a onclick="showList(2,1,0)">ON HOLD
											<span data-toggle="tip" id ="holdCount" class="badge bg-yellow" ></span>
										</a>
									</li>
									<li role="presentation" id=paymentPending>
										<a onclick="showList(3,1,0)">PAYMENT PENDING
											<span data-toggle="tip" id ="paymentPendingCount" class="badge bg-green" ></span>
										</a>
									</li>
									<li role="presentation" id=accountClose>
										<a onclick="showList(4,1,0)">PAYMENT RECEIVED
											<span data-toggle="tip" id ="paymentReceivedCount" class="badge bg-green" ></span>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		
		<div class="row" id="tabsData">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<table class="table table-hover table-bordered">
							<thead>
								<tr>
									<th class="fit ar">DSE No</th>
									<th class="fit ar">Vehicle</th>
									<th class="fit ar">Vendor</th>
									<th class="fit ar">Invoice Number</th>
									<th class="fit ar">Invoice Date</th>
									<th class="fit ar">Job Number</th>
									<th class="fit ar">Cost</th>
									
									<!-- <th class="fit ar">Paid By</th>
									<th class="fit ar">Paid Date</th> -->
									<c:if test="${configuration.showLastModifiedBy}">
										<th class="fit ar">Last ModifiedBy</th>
									</c:if>
									<c:if test="${configuration.showRemark}">
										<th class="fit ar">DSE Remarks</th>
									</c:if>
									<c:if test="${configuration.showAction}">
										<th class="fit ar">Action</th>
									</c:if>
								</tr>
								
							</thead>
							<tbody id="dealerServiceEntriesTable">
							
							</tbody>
		
							</table>
						</div>
					</div>
				</div>
				<div class="text-center" id="navBarId">
					<ul id="navigationBar" class="pagination pagination-lg pager">
					</ul>
				</div>
			</div>
		</div>

		<%-- <div class="row hide filterByDateData">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<table id="VendorPaymentTable1" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row hide filterByDifferentData">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<table id="VendorPaymentTable2" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div> --%>
		
	</section>
	
	<input type="hidden" value="1" id="statues">
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntries.js"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntriesCommon.js"></script>	
	
</div>