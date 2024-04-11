<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="searchTripSheet" name="tripStutes"
									type="number" min="1" required="required" onkeyup="searchTripSheetShow(event);"
									placeholder="TS-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm" onclick="searchTripSheet();">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
					</div>
				<div class="col-md-2">
								<div class="input-group">
									<input class="form-text" id="searchStatus" name=""searchStatus""
										type="text" required="required" onkeyup="searchTripSheetStatus(event);"
										placeholder="Search Vehicle & Route" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" onclick="searchTripSheetDiffStatus();">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
				</div>
					<div class="col-md-2">
							<div class="input-group">
								<input type="hidden" id="TripSelectVehicle_ID" name="vid"
									style="width: 100%;" required="required" onkeyup="searchVehCurTSShowEvt(event);" 
									placeholder="Search Vehicle Name" /> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm" onclick="searchVehCurTSShow();">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
					</div>
					<%-- <a class="btn btn-default btn-sm" data-toggle="tip" data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
						href="${pageContext.request.contextPath}/downloadTripsheetdocument.in">
						<i class="fa fa-download"></i>
					</a> --%>
					<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
						<span class="fa fa-file-excel-o">Import</span>
					</button>
						
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#CloseTripCollection" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-search"></span> Search Trip By Date
						</a>
						<a class="btn btn-success btn-sm" href="addTripSheetEntries.in"> <span
							class="fa fa-plus"></span> Create TripSheet
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/TripSheetReport"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IVCARGO_INTEGRATION_NEEDED')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/SyncVehicleLhpvData"/>"> <span
							class="fa fa-plus "></span> Sync LHPV Date From IVCARGO
						</a>
					</sec:authorize>
					<c:if test="${configuration.superUserDelete && companyId == 4}">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#superUserDelete" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-trash"></span> Delete Other Company TripSheet
						</a>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="modal fade" id="CloseTripCollection" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search TripSheet By Date</h4>
					</div>
 						<div class="modal-body">
							
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group input-append date"
									id="tripCloseDate">
									<input class="form-text" id="SearchTripDate" name="searchDate"
										required="required" type="text" readonly="readonly"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="searchTripSheetByDate();" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
	
		<div class="modal fade" id="superUserDelete" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Delete TripSheet</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<fieldset>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Company
														<abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="tripCompanyId" id="tripCompanyId"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Select Company" /> 
															</div>
													</div>
												</div>
											
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">TS Number <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="selectTripSheetNumber" id="selectTripSheetNumber"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Select TripSheet" /> 
															</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" for="issue_description">Reason<abbr title="required">*</abbr></label>
													<div class="I-size">
													<script language="javascript" src="jquery.maxlength.js"></script>
						                                 <textarea class="text optional form-text" placeholder="Please enter reason with open project ticket number"
																		id="reason" name="reason"
																		rows="3" maxlength="250"></textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">
							<span id="Save" onclick="superUserTripSheetDelete();">Delete</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="row">
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayOne} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="#" onclick="getTodayTripSheetList('${DayOne}');">${DayOne_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayTwo} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="#" onclick="getTodayTripSheetList('${DayTwo}');">${DayTwo_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayThree} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="#" onclick="getTodayTripSheetList('${DayThree}');">${DayThree_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFour} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="#" onclick="getTodayTripSheetList('${DayFour}');">${DayFour_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFive} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="#" onclick="getTodayTripSheetList('${DayFive}');">${DayFive_Count}</a></span>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<div class="box-body">
							<input type="hidden" id="loadTypeId" value="${loadTypeId}">
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
							<input type="hidden" id="hideAdvanceAndExpenseAddIfAccClose" value="${configuration.hideAdvanceAndExpenseAddIfAccClose}">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="todaysTrip" class="active"><a
										href="#" onclick="getTodayTripSheetList('${DayOne}');">Today TripSheet</a></li>
									<li role="presentation" id="dispatchTrip"><a
										href="#" onclick="getDispatchTripSheetList(1);">Dispatch
											TripSheet</a></li>
									<li role="presentation" id="manageTrip"><a
										href="#" onclick="getManageTripSheetList(1);">Manage
											TripSheet</a></li>
									<li role="presentation" id="advCloseTrip"><a
										href="#" onclick="getAdvCloseTripSheetList(1);">Advance Close
											TripSheet</a></li>
									<c:if test="${configuration.hideClosedTripSheet}">	
										<sec:authorize access="hasAuthority('SHOW_TRIPSHEET_CLOSE_STATUS')">
											<li role="presentation" id="tripAccList"><a
												href="#" onclick="getTripSheetAccountList(1);">Payment
													TripSheet</a></li>
											<li role="presentation" id="tripAccClose"><a
												href="#" onclick="getTripSheetAccountCloseList(1);">Account
													Closed TripSheet</a></li>
										</sec:authorize>
									</c:if>	
									<c:if test="${!configuration.hideClosedTripSheet}">	
									<li role="presentation" id="tripAccList"><a
										href="#" onclick="getTripSheetAccountList(1);">Payment
											TripSheet</a></li>
									<li role="presentation" id="tripAccClose"><a
										href="#" onclick="getTripSheetAccountCloseList(1);">Account
											Closed TripSheet</a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<input type="hidden" id="showTripdateWithTime" value="${configuration.showTripdateWithTime}">
							<div class="table-responsive">
								<table id="TripSheetTable" class="table table-hover table-bordered">
									<thead id="tableHeader">
									</thead> 
									<tbody id="TripSheetTableBody">
									</tbody>
								</table>
							</div>
							<div class="text-center">
								<ul id="navigationBar" class="pagination pagination-lg pager"></ul>
							</div>
							
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<div class="modal fade" id="addImport" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
			<form method="post" action="<c:url value="/importTripsheet.in"/>"
							enctype="multipart/form-data">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<h3 class="panel-title">Import File</h3>
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<br>
							<div class="row1">
								<div  class="I-size">
									<label> Import Only XLSX File : </label>
								</div>
								<div class="I-size">
									<input type="file" accept=".xlsx" name="import" required="required" />
								</div>
							</div>
						</div>
						
						<div class="row1 progress-container">
							<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" style="width: 0%">
								Upload Your Tripsheet Entries Please wait..</div>
							</div>
						</div>
						<div class="modal-footer">
							<input class="btn btn-success" onclick="this.style.visibility = 'hidden'" name="commit"
								type="submit" value="Import TripSheet file" class="btn btn-primary" id="myButton"
								data-loading-text="Loading..." class="btn btn-primary" onclick="saveExcelSheet();"
								autocomplete="off" id="js-upload-submit" value="Add Document" data-toggle="modal">
							<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
						</div>
						<script>
							$('#myButton').on('click',
								function() {
									$(".progress-bar").animate(
										{
											width : "100%"
										},2500);
									var $btn = $(this).button('loading')
									$btn.button('reset')
								})
						</script>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSearchVehicle.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetListDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetDetails.js" />"></script>	
	<script type="text/javascript">
		$(function() {
			$("#SearchTripDate").datepicker({
			       defaultDate: new Date(),                                       
			       autoclose: !0,
			       todayHighlight: !0,
			       format: "dd-mm-yyyy",
			       setDate: "0",
			       endDate: "currentDate"
			   })
		}); 
	</script>
</div>