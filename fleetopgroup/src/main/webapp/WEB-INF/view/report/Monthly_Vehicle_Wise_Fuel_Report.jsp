<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">	
	<<style>
/*  td{
margin-left:5px;
font-size:12pt;
text-align:left;
} */  
#issue, #comment td {
 font-size:10pt;
 text-align: left;
 border="1";
 /* padding-left: 10px; */

 
}

#tripCollExpenseName td{
 font-size:10pt;
 padding-left: 10px;
 }
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a> / <span>Monthly Vehicle Wise Fuel Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('trip', 'Monthly Vehicle Wise Fuel Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm "
						onclick="saveImageToPdf('trip')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
<section class="content">
					<div class="panel box box-primary">
					<div class="box-header">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTwo"> Monthly Vehicle Wise Fuel Report </a>
								</h4>
							</div> 	
							<div class="box-body">
									<div class="form-horizontal ">
									<!-- Month range -->
										<div class="row1">
											<label class="L-size control-label">Month: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="monthRangeSelector" class="form-text"
														name="FROM_DATERANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
											
										</div>
										
										<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success" id="btn-save">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
										</div>
									</div>
							</div>
						<!-- </div> -->
					</div>
					
					
					
					
						<%-- <table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<td><c:choose>
															<c:when test="${company.company_id != null}">
																<img
																	src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
																	class="img-rounded " alt="Company Logo" width="280"
																	height="40" />

															</c:when>
															<c:otherwise>
																<i class="fa fa-globe"></i>
																<c:out value="${company.company_name}" />
															</c:otherwise>
														</c:choose></td>
													<td>Print By: ${company.firstName}_${company.lastName}</td>
												</tr>
												<tr>
													<td colspan="2">Branch :<c:out
															value=" ${company.branch_name}  , " /> Department :<c:out
															value=" ${company.department_name}" />
													</td>
												</tr>
											</tbody>
										</table> --%>
				
					
					
		<section class="content hide" id="ResultContent">
			<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="trip" style="width: 95%;"
																class="table-hover table-bordered">
												
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</section>
				</div>
			</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>	
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>		
		
	<script type="text/javascript">
		$(document).ready(function() {
			$('#monthRangeSelector').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
		});
	</script>
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/Monthly_Vehicle_Fuel_Report.js" />"></script>
	
</div>