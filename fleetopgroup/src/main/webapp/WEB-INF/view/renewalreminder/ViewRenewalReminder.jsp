<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" 
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" 
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">	
<style>
.tooltip-inner {
    background-color: #00cc00;
}
.tooltip.bs-tooltip-right .arrow:before {
    border-right-color: #00cc00 !important;
}
.tooltip.bs-tooltip-left .arrow:before {
    border-right-color: #00cc00 !important;
}
.tooltip.bs-tooltip-bottom .arrow:before {
    border-right-color: #00cc00 !important;
}
.tooltip.bs-tooltip-top .arrow:before {
    border-right-color: #00cc00 !important;
}
.shadow {
  border: 1px solid;
  padding: 10px;
  box-shadow: 5px 10px #888888;
}

</style>	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/viewRenewalReminder.in"/>"> RenewalReminder</a>
				</div>
				
				<div class="col-md-off-5">
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<a class="btn btn-info btn-sm"
								href="<c:url value="/renewalReminderAjaxAdd.in?vid=0&renewalSubTypeId=0"/>"> <span
								class="fa fa-plus"></span> Add Renewal
							</a>
						</sec:authorize>
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">RR-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="ID eg: 2323"> <span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachRenewalReminderByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
					
					<div class="col-md-2">
						<div class="input-group">
							<input class="form-text" id="searchByDifferentFilter"
								name="Search" type="number" required="required" min="1"
								placeholder="RR-ID, V-Name, Receipt-No"> <span
								class="input-group-btn">
								<button type="submit" name="search" id="searchInDiff" onclick="return searchRRByDifferentFilter();"
									class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
					
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/3.in">
							<i class="fa fa-download"></i>
						</a>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/viewRenewalReminderReport.in"/>"> <span
							class="fa fa-search"></span> Search
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#searchRRByDate" data-whatever="@mdo"> <span
							class="fa fa-search"></span> Search Date
						</a>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<a class="btn btn-success btn-sm" onclick="email();"> <span
							class="fa fa-plus"></span> Configure E-mail
						</a>
					</sec:authorize> 
					
				</div>
			</div>
		</div>
	</section>
	
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class='col-sm-3'>
						<input type="text" id="vehicleId" name="vehicleId" style="width: 100%;" required="required" placeholder="Search Vehicle Name" /> 
					</div>
					<div>
						<button type="submit" class="btn btn-primary" onclick="searchByVehicle();">
							<span>Search</span>
						</button>
					</div>
				</div>
			</div>
		</div>	
	
	<section class="content">
		
		<div class="modal fade" id="searchRRByDate" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Renewal Date</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="input-group input-append date"
								id="vehicle_RegisterDate">
								<input class="form-text" id="ApprovalPaidDate" name="RRDate"
									required="required" type="text"
									data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""> <span
									class="input-group-addon add-on"> <span
									class="fa fa-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" onclick="return searchRRDateWise();" class="btn btn-success">Search</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Import Driver CSV File</h3>
							</div>

							<div class="panel-body">
								<div class="form-horizontal">
									<br>
									<div class="row1">
										<div class="L-size">
											<label> Import Only CSV File: </label>
										</div>
										<div class="I-size">
											<input type="file" id="renewalFile" accept=".csv" name="input-file-preview" required="required" />
										</div>
									</div>
								</div>
								
								<div class="modal-footer">
									<div class="col-sm-offset-4 I-size">
										<input type="button" value="Submit" id="btnSubmit" class="btn btn-success" />	
										<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="configureEmail" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Configure E-Mail</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label">E-mail Id</label>
							<div class="I-size">
								<input type="hidden" value="0" id="configId" /> <input
									type="text" class="form-text" id="emailId"
									onchange="$('#saveEmail').removeAttr('disabled');"
									name="unitCost" placeholder="Enter E-mail Id" /> <label
									id="errorvStatus" class="error"></label>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="submit" onclick="addEmail()" class="btn btn-success"
							disabled="" id="saveEmail">Save</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>
		
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<div class="row">
						<input type="hidden" id="inactiveVehicleInRR"value="${configuration.inactiveVehicleInRR}">
						<input type="hidden" id="vehicleExcludingSurrenderAndInactive"value="${configuration.vehicleExcludingSurrenderAndInactive}">
						<input type="hidden" id="ignoreRenewal" value="${reConfiguration.ignoreRenewal}">
						
							<div class="col-md-2 col-sm-2 col-xs-2 " >
								<div class="info-box info-box-text shadow"  >
									${todaysDate} <br /><span class="info-box-text" style="color: blue;" >Today</span> 
									<span class="info-box-number"> <a
										href="#" data-toggle="tooltip" title="&#8377;${todaysAmount}"  data-placement="right" onclick="getRRListByDate('${todaysDate}','Today','${todaysCount}',1);" id="todayCount">${todaysCount}</a></span>
								</div>
							</div>
							<div class="col-md-2 col-sm-2 col-xs-2  ">
								<div class="info-box info-box-text shadow">
									${tomorrowDate} <br /><span class="info-box-text" style="color: blue;" > Tomorrow</span> 
									<span class="info-box-number"><a
										href="#" data-toggle="tooltip" title="&#8377;${tomorrowAmount}" data-placement="right" onclick="getRRListByDate('${tomorrowDate}','Tomorrow','${tomorrowCount}',1);" id="tomorrowCount">${tomorrowCount}</a></span>
								</div>
							</div>
							<div class="col-md-2 col-sm-2 col-xs-2">
								<div class="info-box info-box-text shadow">
									${nextSeventDay} <br /> <span class="info-box-text" style="color: blue;">Next 7 Days</span> <span
										class="info-box-number"><a
										href="#" data-toggle="tooltip" title="&#8377;${nextSeventDayAmount}" data-placement="right" onclick="getRRListByDate('${nextSeventDay}','Next 7 Days','${nextSeventDayCount}',1);" id="sevenDaysCount">${nextSeventDayCount}</a></span>
								</div>
							</div>
							<div class="col-md-2 col-sm-2 col-xs-2">
								<div class="info-box info-box-text shadow">
									${nextFifteenDay} <br /> <span class="info-box-text" style="color: blue;"> Next Fifteen Days</span> <span
										class="info-box-number"><a
										href="#" data-toggle="tooltip" title="&#8377;${nextFifteentDayAmount}" data-placement="right" onclick="getRRListByDate('${nextFifteenDay}','Next Fifteen Days','${nextFifteentDayCount}',1);" id="fifteenDaysCount"> ${nextFifteentDayCount}</a></span>
								</div>
							</div>
							<div class="col-md-2 col-sm-2 col-xs-2">
								<div class="info-box info-box-text shadow">
									${nextMonth} <br /> <span class="info-box-text"  style="color: blue;"> Next Month</span> <span
										class="info-box-number"><a
										href="#" data-toggle="tooltip" title="&#8377;${nextMonthAmount}" data-placement="right" onclick="getRRListByDate('${nextMonth}','Next Month','${nextMonthCount}',1);" id="nextMonthCount">${nextMonthCount}</a></span>
								</div>
							</div>
						</div>
						<div class="box-body">
							<input type="hidden" id="createApprovalPermission" value="${createApprovalPermission}">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="all">
										<a onclick="showList(0,1,0)" id="allClr" >ALL </a></li>
									<li role="presentation" id=overDue>
										<a onclick="showList(7,1,1)" id="overDueClr"><span  style="color: red;" >OverDue</span>
										<span class="info-box-number">${totalOverDueCount}</span></a></li>
									<li role="presentation" id="dueSoon">
										<a onclick="showList(8,1,1)"  id="dueSoonClr"><span  style="color: #FF8C00;" >DueSoon</span>
										<span class="info-box-number">${totalDueSoonCount}</span></a></li>
									<c:if test="${configuration.inactiveVehicleInRR}">
										<li role="presentation" id="inactiveOverdue">
											<a onclick="showList(7,1,2)"  id="inactiveOverdueClr"><span  style="color: red;" >InActive Vehicle OverDue</span>
											<span class="info-box-number">${inactiveOverDueCount}</span></a></li>	
										<li role="presentation" id="inactiveOueSoon">
											<a onclick="showList(8,1,2)"  id="inactiveOueSoonClr"><span  style="color: #FF8C00;" >InActive Vehicle DueSoon</span>
											<span class="info-box-number">${inactiveDueSoonCount}</span></a></li>	
									</c:if>
									<li role="presentation" id="dateWiseList" class="hide">
										<a  id="dateWiseListClr"> <span id="dateStatus" style="color: blue;" ></span>
										<span class="info-box-number" id="dateWiseCount"></span></a></li>	
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
		
		<div class="row hide filterByDifferentData">
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
		
		<div class="row hide filterByDateData">
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
		<div class="modal fade" id="createApprovalModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="renewalId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Create Approval</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Approval Remark <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<textarea class="form-text" id="approvalRemark" name="approvalRemark" rows="3" maxlength="240">	
												</textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Create Approval" id="createApproval" class="btn btn-success" />
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
				<div class="modal fade" id="ignoreWithRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Ignore With Remark</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
							<input type="hidden" id="ignoreRenewalReminderId">
											<label class="L-size control-label" for="issue_description">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="igRemark" name="igRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="ignoreWithRemark();" id="btnSubmit" class="btn btn-primary">
								<span>Ignore</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		
	</section>
	
	<input type="hidden" value="1" id="statues">
	
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderShow.js" />"></script>	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/ViewRenewalReminder.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>			
	
</div>