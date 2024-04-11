<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/viewServiceEntries.in"/>"> Service Entries</a>
				</div>
				
				<div class="col-md-off-5">
				
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">SE-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="ID eg: 2323"> <span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachServiceEntryByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
					
					<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createServiceEntries?issue=0,0"/>"> 
							<span class="fa fa-plus"></span> Create Service Entries
						</a>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/searchServiceEntries.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#searchServiceEntriesByDate" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-search"></span> Search Service Entries By Date
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
				
				<div class="col-md-4 col-sm-4 col-xs-12" id="totalSE">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-safari"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Service Entries</span> 
							<span id="count" class="info-box-number">${totalserviceentriescount}</span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12" id="filter">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search ServiceEntries</span>
								<div class="input-group">
									<input class="form-text" id="searchByDifferentFilter"
										name="Search" type="number" required="required" min="1"
										placeholder="ID, V-Name, Invoice-No"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn" onclick="return searchSEByDifferentFilter();"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
				
			</sec:authorize>
		</div>
		
		<div class="modal fade" id="searchServiceEntriesByDate" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Service Entries By Date</h4>
					</div>
						<div class="modal-body">
							
							<div class="row" id="grpReportDailydate" class="form-group">
								
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="ReportDailydate" class="form-text"
										name="searchDate" required="required" readonly="readonly"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
								
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="return searchServiceEntriesDateWise();" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
		
		
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="open"><a
										onclick="showList(1,1)">OPEN<span data-toggle="tip" id ="allCount" class="badge bg-blue" ></span></a>
										</li>
									<li role="presentation" id=inProcess><a
										onclick="showList(2,1)">INPROCESS<span data-toggle="tip" id ="inProcessCount" class="badge bg-blue" ></span></a></li>
									<li role="presentation" id="completed"><a
										onclick="showList(3,1)">COMPLETED<span data-toggle="tip" id ="completeCount" class="badge bg-blue" ></span></a></li>
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
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<table id="VendorPaymentTable" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
				<div class="text-center">
					<ul id="navigationBar" class="pagination pagination-lg pager">
					</ul>
				</div>
			</div>
		</div>

		<div class="row hide filterByDateData">
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
		</div>
		
	</section>
	
	<input type="hidden" value="1" id="statues">
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ViewServiceEntries.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	
	
</div>