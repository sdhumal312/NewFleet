<%@ include file="../taglib.jsp"%>
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
					<a href="<c:url value="/requisition"/>">Requisition</a>
				</div>
					<div class="col-md-off-6">
					<div class="col-md-3">
						<div class="input-group">
						<span class="input-group-addon"> <span  aria-hidden="true">R-</span></span>
							<input class="form-text" id="searchByNumber" style="height: 26px" name="Search" type="number" min="1" required="required" placeholder="ID eg: 2323"> 
							<span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="searchReqByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div></div>
				<div class="pull-right">
				<div class="col-md-off-6">
				<div class="col-md-3">
				<sec:authorize access="hasAuthority('CREATE_REQUISITION')"> 
				<a class="btn btn-success btn-sm" 
							href="<c:url value="/createRequisition"/>"> 
							<span class="fa fa-plus"></span> Create Requisition
						</a>
				</sec:authorize>
				</div>
				</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_REQUISITION')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_REQUISITION')">
					<input type="hidden" id="companyId" value="${companyId}">	
					<input type="hidden" id="userId" value="${userId}">	
					<input type="hidden" id="allowDelete" value="${allowDelete}">	
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="all">
										<a onclick="showList(0,1)">All  
											<span data-toggle="tip" id ="allCount" class="badge bg-blue" ></span>
										</a>
									</li>
									<li role="presentation" id=created>
										<a onclick="showList(1,1)">Created
											<span data-toggle="tip" id ="createdCount" class="badge bg-yellow" ></span>
										</a>
									</li>
									<li role="presentation" id=approved>
										<a onclick="showList(3,1)">Approved
											<span data-toggle="tip" id ="approvedCount" class="badge bg-yellow" ></span>
										</a>
									</li>
									
									<li role="presentation" id=yourAssigned>
										<a onclick="showList(6,1)">Assigned to you
											<span data-toggle="tip" id ="assignedToYouCount" class="badge bg-red" ></span>
										</a>
									</li>
									
										<li role="presentation" id=pendingTransfer>
										<a onclick="showList(4,1)">Your Pending Transfer
											<span data-toggle="tip" id ="yourPendingTCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=pendingReceival>
										<a onclick="showList(5,1)">Your Pending Receival
											<span data-toggle="tip" id ="yourPendingRCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=completeRequisition>
										<a onclick="showList(7,1)">Complete Requisition
											<span data-toggle="tip" id ="completeCount" class="badge bg-green" ></span>
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
									<th class="fit ar">Req. No</th>
									<th class="fit ar">Req. Location</th>
									<th class="fit ar">Req. Date</th>
									<th class="fit ar">Ref. Number</th>
									<th class="fit ar">Assignee</th>
									<th id="actionId" style="display: none;" class="fit ar">Action</th>
								</tr>
							</thead>
							<tbody id="requisitionTable">
							
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
	</section>

	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/requisitionList.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceEntriesCommon.js"></script>

</div>