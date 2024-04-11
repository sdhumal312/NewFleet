<%@ include file="taglib.jsp" %>
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
					<a href="<c:url value="/repairViewList.in"/>">Repair Stock</a>
				</div>
				<div class="col-md-off-6">
					<div class="col-md-3">
						<div class="input-group">
						<span class="input-group-addon"> <span  aria-hidden="true">RS-</span></span>
							<input class="form-text" id="searchByNumber" name="Search" type="number" min="1" required="required" placeholder="ID eg: 2323"> 
							<span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return searchRepairByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
					<sec:authorize access="hasAuthority('CENTRALISED_REPAIR')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createRepairInvoice.in"/>"> 
							<span class="fa fa-plus"></span> Stock Repair
						</a>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('CENTRALISED_REPAIR')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('CENTRALISED_REPAIR')">
					<input type="hidden" id="companyId" value="${companyId}">	
					<input type="hidden" id="userId" value="${userId}">	
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="all">
										<a onclick="showList(0,1)">All  
											<span data-toggle="tip" id ="allCount" class="badge bg-blue" ></span>
										</a>
									</li>
									<li role="presentation" id=created>
										<a onclick="showList(1,1)">CREATED
											<span data-toggle="tip" id ="createdCount" class="badge bg-blue" ></span>
										</a>
									</li>
									<li role="presentation" id=sentToRepair>
										<a onclick="showList(2,1)">SENT TO REPAIR
											<span data-toggle="tip" id ="sentToRepairCount" class="badge bg-red" ></span>
										</a>
									</li>
									<li role="presentation" id=complete>
										<a onclick="showList(3,1)">COMPLETE
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
									<th class="fit ar">Repair Stock Number</th>
									<th class="fit ar">Repair Stock Type</th>
									<th class="fit ar">WorkShop Type</th>
									<th class="fit ar">Created Date</th>
									<th class="fit ar">Sent To Repair Date</th>
									<th class="fit ar">Completed Date</th>
									<th class="fit ar">Last ModifiedBy</th>
									<th class="fit ar">Action</th>
								</tr>
							</thead>
							<tbody id="repairStockTable">
							
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
	
	<input type="hidden" value="1" id="statues">
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/repair/RepairViewList.js"></script>	
	
</div>