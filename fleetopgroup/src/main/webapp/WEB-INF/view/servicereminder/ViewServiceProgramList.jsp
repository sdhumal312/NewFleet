<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div style="font-size: 16px;" class="pull-left">
					<a style="font-size: 16px;" href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a style="font-size: 16px;" href="<c:url value="/serviceProgram.in"/>"> Service Programs</a>
				</div>
				<div class="pull-right">
					<div class="col-md-off-5">
					<a class="btn btn-default btn-sm" data-toggle="tip" data-original-title="Download Import XLSX Format. When Import Don't Remove the header" href="/downloadServiceProgramSheet.in">
							<i class="fa fa-download"></i>
						</a>
					<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
						<sec:authorize access="hasAuthority('ADD_SERVICE_PROGRAM')">
						<a class="btn btn-success btn-sm"
							href="#" onclick="addServiceProgram();"> 
							<span class="fa fa-plus"></span> Create Service Program
						</a>
					</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_PROGRAM')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_PROGRAM')">
				
				<div class="col-md-4 col-sm-4 col-xs-12" id="totalSE">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-safari"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Service Programs</span> 
							<span id="count" class="info-box-number"></span>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-bell-slash"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total</span> <a data-toggle="tip"
								data-original-title="Total Service Reminder Count"
								href="#" ><span
								class="info-box-number" id="allServiceCount"></span></a>
						</div>
					</div>
				</div>
				
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-red"><i
							class="fa fa-bell-slash"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Overdue</span> <a data-toggle="tip"
								data-original-title="Click OverDue Details" target="_blank"
								href="viewOverDueGroupedList.in" ><span
								class="info-box-number" id="overeDueCount"></span></a>
						</div>
					</div>
				</div>
				
								<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-orange"><i
							class="fa fa-volume-up"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Due Soon</span> <a data-toggle="tip"
								data-original-title="Click DueSoon Details" target="_blank"
								href="viewDueSoonGroupedList.in"  ><span
								class="info-box-number" id="DueSoonCount"></span></a>

						</div>
					</div>
				</div>
				
			</sec:authorize>
		</div>
		
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_SERVICE_PROGRAM')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_PROGRAM')">
						
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
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
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
	
	<div class="modal fade" id="addServiceProgram" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Service Program</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<fieldset>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
											<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label"
													for="ServiceSelectVehicle">Name<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" id="programName"
														name="programName" style="width: 100%;"
														placeholder="Please Enter Programme Name " /> 
												</div>
											</div>
												<div class="row1">
													<label class="L-size control-label" for="issue_description">Description
														</label>
													<div class="I-size">
													<script language="javascript" src="jquery.maxlength.js"></script>
						                                 <textarea class="text optional form-text"
																		id="description" name="initial_note"
																		rows="3" maxlength="250"></textarea>
													</div>
												</div>
										<c:if test="${configuration.allowToAddVendorProgram}">
											<div class="row1" id="ShowVendorOption">
												<label class="L-size control-label" for="payMethod">Is
													Vendor Program :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="">
														<div class="btn-group" id="status" data-toggle="buttons">
															<label class="btn btn-default btn-on btn-lg">
																<input type="radio" value="1" name="vendorProgram" id="vendorProgramYes"
																>YES
															</label> <label class="btn btn-default btn-off btn-lg active"> <input
																type="radio" checked="checked" value="2" name="vendorProgram" id="vendorProgramNo">NO
															</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>		
											</div>
										</div>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">
							<span id="Save" onclick="saveServiceProgram();">Save Service Program</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="EditServiceProgram" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit Service Program</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="vehicleServiceProgramId">
						<div class="row">
							<fieldset>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
											<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label"
													for="ServiceSelectVehicle">Name<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" id="programNameEdit"
														name="programName" style="width: 100%;"
														placeholder="Please Enter Programme Name " /> 
												</div>
											</div>
												<div class="row1">
													<label class="L-size control-label" for="issue_description">Description
														</label>
													<div class="I-size">
													<script language="javascript" src="jquery.maxlength.js"></script>
						                                 <textarea class="text optional form-text"
																		id="descriptionEdit" name="initial_note"
																		rows="3" maxlength="250"></textarea>
													</div>
												</div>
										<c:if test="${configuration.allowToAddVendorProgram}">
											<div class="row1" id="ShowVendorOption">
												<label class="L-size control-label" for="payMethod">Is
													Vendor Program :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="">
														<div class="btn-group" id="status" data-toggle="buttons">
															<label id="vendorEditYes">
																<input type="radio" value="1" name="vendorProgram" id="vendorProgramYesEdit"
																>YES
															</label> <label id="vendorEditNo"> <input
																type="radio" checked="checked" value="2" name="vendorProgram" id="vendorProgramNoEdit">NO
															</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>		
											</div>
										</div>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">
							<span id="Save" onclick="updateServiceProgram();">Update Service Program</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importServiceProgram.in"/>"
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
												<label> Import Only CSV File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".csv" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Vehicle Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Vehicle load files"
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
			
			<div class="modal fade" id="serviceScheduleList" role="dialog">
			<div class="modal-dialog modal-xl" style="width:90%;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Service schedule Program</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<fieldset>
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">

											<div class="row">
												<div class="col-md-11 col-sm-11 col-xs-11">
													<div class="table-responsive">
														<table class="table table-hover table-bordered" id="dataTable" style="display: none;">
															<thead>
																<tr class="breadcrumb">
																	<th class="fit ar">SR</th>
																	<th class="fit ar">Job Type</th>
																	<th class="fit ar">Job SubType</th>
																	<th class="fit ar">Intervals</th>
																	<th class="fit ar">Threshold Details</th>
																	<th class="fit ar">Service Type</th>
																	<th class="fit ar">Service Reminder Count</th>
																</tr>
															</thead>
															<tbody id="serviceScheduleListBody">

															</tbody>

														</table>
													</div>
												</div>
												<br />
												<br />
											</div>


										</div>
										</div>
								</div>
							</fieldset>
					</div>				
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
		
		
<div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document" style="width: 900px;" >
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title" id="exampleModalLongTitle">Service Reminder</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table table-hover table-bordered" id="sRdataTable"
									style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit ar">SR</th>
											<th class="fit ar">Service Reminder</th>
											<th class="fit ar">Service Task And Schedule</th>
											<th class="fit ar">Next Due</th>
											<th class="fit ar">Vehicle</th>
										</tr>
									</thead>
									<tbody id="serviceReminderListBody">

									</tbody>

								</table>
							</div>
						</div>
						<br /> <br />
					</div>
				</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

	<div class="modal fade" id="exampleModalLong1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle1" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document" style="width: 1280px;" >
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title" id="exampleModalLongTitle1">Service Reminder</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
					<div id="div_print">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
											<br/>
										<div class="row invoice-info">
											<table width="100%">
												  <tr>
												    <td valign="top" width="100%">
												    
														<div id="gridContainer">
														      <div id="myGrid"></div>
														      <div id="pager" style="width:100%;height:20px;"></div>
														      
														 </div>     
												    </td>
												   
												  </tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
				</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
		
	
	<input type="hidden" value="1" id="statues">
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/ViewServiceProgramList.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>

<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>
	
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	
</div>