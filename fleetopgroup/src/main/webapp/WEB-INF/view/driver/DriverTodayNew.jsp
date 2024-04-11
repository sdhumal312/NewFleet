<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('IMPORT_DRIVER')">
						<a class="btn btn-default" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/2.in">
							<i class="fa fa-download"></i>
						</a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_DRIVER')">
						<button type="button" class="btn btn-default" data-toggle="modal"
							data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>

					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_DRIVER')">
						<a class="btn btn-success" href="addDriver.in"> <span
							class="fa fa-plus"></span> Add Driver
						</a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_DRIVER')">
						<a class="btn btn-info" href="DriverReport"> <span
							class="fa fa-search"></span> Search
						</a>

					</sec:authorize>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<!-- <script type="text/javascript" src="resources/js/DriverNewlanguage.js" />"></script> -->
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-user"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Driver</span> <span
								class="info-box-number">${DriverCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Today's DL
								Renewal</span> <span class="info-box-number"><a
								href="TodayDLRenewal.in" data-toggle="tip"
								data-original-title="Click Calendar Icon">${TodayDLRenewalcount}</a></span>


						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Tomorrow DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/TomorrowDLRenewal.in"/>">${TomorrowDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next 7 Days DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextSevenDLRenewal.in"/>">${NextSevenDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next 15 Days DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextFifteenDLRenewal.in"/>">${NextFifteenDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Next Month DL
								Renewal</span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/NextMonthDLRenewal.in"/>">${NextMonthDLRenewalcount}</a></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Driver</span>
							<form action="searchDriver.in" method="post">
								<div class="input-group">
									<input class="form-text" name="driver_firstname" type="text"
										required="required" placeholder="EMP-NO, Name, DL, Badge">
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
		</sec:authorize>
		<br>

		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post" action="importDriver.in"
						enctype="multipart/form-data">

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
											<input type="file" accept=".csv" name="import"
												required="required" />
										</div>
									</div>
								</div>
								<div class="row1 progress-container">
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 0%">Upload Your Driver Entries Please
											wait..</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" id="myButton"
										data-loading-text="Loading..." class="btn btn-primary"
										 id="js-upload-submit" value="Add Document"
										data-toggle="modal" data-target="#processing-modal">Import
										load files</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
								<script>
									$('#myButton').on('click', function() {
										//alert("hi da")
										$(".progress-bar").animate({
											width : "100%"
										}, 2500);
										var $btn = $(this).button('loading')
										// business logic...

										$btn.button('reset')
									})
								</script>

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="RenewalReminder" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Renewal Date</h4>
					</div>
					<form action="<c:url value="/DriverR.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="RRDate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="main-body">
					<h4>Today Driver Renewal Validity List</h4>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="DriverTable" class="table">
									<thead>
										<tr>
											<th>EMP-No</th>
											<th>Name</th>
											<th>Type</th>
											<th>Number</th>
											<th>Validity_From</th>
											<th>Validity_To</th>
											<th>Download</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty driverReminder}">
											<c:forEach items="${driverReminder}" var="driverReminder">

												<tr data-object-id="" class="ng-scope">
													<td><a
														href="showDriver.in?driver_id=${driverReminder.driver_id}"
														data-toggle="tip" data-original-title="Click driver"><c:out
																value="${driverReminder.driver_empnumber}" /> </a></td>
													<td><a
														href="showDriver.in?driver_id=${driverReminder.driver_id}"
														data-toggle="tip" data-original-title="Click driver"><c:out
																value="${driverReminder.driver_firstname} " /> <c:out
																value="${driverReminder.driver_Lastname}" /> </a></td>
													<td><c:out
															value="${driverReminder.driver_remindertype}" /></td>
													<td><i></i> <c:out
															value="${driverReminder.driver_dlnumber}" /></td>
													<td><c:out
															value="${driverReminder.driver_dlfrom_show}" /></td>

													<td><c:out
															value="${driverReminder.driver_dlto_show}" /><font
														color="#FF6666"> ( <c:out
																value="${driverReminder.driver_dueDifference}" /> )
													</font>
														<ul class="list-inline no-margin">
															<li><font color="#999999"> Due soon on <c:out
																		value="${driverReminder.driver_renewaldate}" />
															</font></li>

														</ul></td>
													<td><sec:authorize
															access="hasAuthority('DOWNLOND_DRIVER')">
															<a
																href="${pageContext.request.contextPath}/download/driverReminder/${driverReminder.driver_remid}.in">
																<span class="fa fa-download"> Download</span>
															</a>
														</sec:authorize></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".nav-tabs a").click(function() {
				$(this).button("loading").delay(500).queue(function() {
					$(this).button("reset"), $(this).dequeue()
				})
			})
		});
	</script>
</div>