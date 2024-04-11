<%@ include file="../../taglib.jsp"%>
<%-- <%@ page contentType="text/html;charset=UTF-8" language="java" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/<a href="<c:url value="/TSR.in"/>">Trip sheet Report</a> / <span>Trip
						Sheet Due Amount Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('div_print', 'TripSheet Due Amount Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm "
						onclick="saveImageToPdf('div_print')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>
					
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
			<section class="content">
					<div class="panel box box-primary">
							<div class="box-body" id="ElementDiv">
									<div class="form-horizontal ">
										
										<div class="row1">
											<label class="L-size control-label">
												<c:choose>
													<c:when test="${configuration.changedDriverFieldName}">
														Agent
													</c:when>
													<c:otherwise>
														Driver
													</c:otherwise>
												</c:choose>
												Name : </label>
											<div class="I-size">
												<input type="hidden" id="driverId" name="driverName"
													style="width: 100%;" placeholder="All" />
											</div>
										</div>
										
									</div>
									<div class="form-horizontal ">
									<div class="row1">
										<input type = "hidden" id ="showbilltypeDropdown" value = "${tripConfiguration.showbilltypeDropdown}">
										
										<c:if test="${tripConfiguration.showbilltypeDropdown}">
	                                            <div class="row1">
	                                                <label class="L-size control-label">Select Income Type <abbr title="required">*</abbr>
	                                                </label>
		                                                <div class="I-size">
			                                               <c:set var="myList" value="${fn:split('B_INCOME,E_INCOME', ',')}"/>
				                                                <select class="form-text" id="billselectionId"> 
																		<c:forEach var="item" items="${myList}">
																			    <sec:authorize access="hasAuthority('${item}')">
																			    	  <option value="${item}"><c:out value="${item}"></c:out></option>
																			    </sec:authorize>
																		</c:forEach>
																 </select>
		                                                </div>
	                                            </div>
	                                       </c:if>
                                      </div>
                                      </div>
										
									<!-- Date range -->
									<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="dueAmountRange" class="form-text"
													name="PART_RANGE_DATE" required="required"
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
						<!-- </div> -->
					</div>
	
					
			<section  id="ResultContent">
			<div class="box-body">
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
											<table id="dueAmountTable" style="width: 95%;"
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
	
	<section>
	<div class="modal fade" id="duePayment" role="dialog">
			<div class="modal-dialog modal-md" style="width:1250px;">
				<div class="modal-content">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<!-- <h4 class="modal-title" id="TripExpense">Pre EMI Settlement</h4> -->
							</div>
							<div class="modal-body">
								
								<div class="panel panel-success "  id="settle-border-boxshadow">
									<div class="panel-heading text-center">
										<h4 >Due Amount Settlement
										</h4>
									</div>
								</div>
								
								<div class="row">
								<div class="pull-left">
									<h4>
										Trip Number : 
											<span id="showTripNumber" > </span>
									</h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : <span id="createdDate"></span></h5>
								</div>
								</div>
								
								<div class="row">
									<h4 align="center">
										<a id="vehicleRegistration"></a>
										<br>
										<span id="route"></span>
									</h4>
								</div>	
								
								<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <span id="tripOpenDate"></span></a></li>
									<li>Depot : <a data-toggle="tip"
										data-original-title="Group Service"><span id="vehicleGroup"></span></a></li>
									<li>Driver: <a data-toggle="tip"
										data-original-title="Driver"> <span id="driver"></span></a></li>
									<li>
										<c:if test="${configuration.allowMultipleAddExpInDueAmtReport}">
											<button type="button" id="addExpense" class="btn btn-primary hide"><span class="fa fas fa-plus"> </span></i>Add Expense</button>
										</c:if>
									</li>
								</ul>
								
							</div>
								<input type="hidden" id="allowMultipleAddExp" value="${configuration.allowMultipleAddExpInDueAmtReport}">
								<input type="hidden" id="tripsheetDueAmountId">
								<input type="hidden" id="tripsheetId">
								<input type="hidden" id="amountDue">
								<input type="hidden" id="amountBalance">
								<input type="hidden" id="DuedriverId">
								<div class="table-responsive scroll-box" >
								<input type="hidden" id="accessToken" value="${accessToken}">
								
								<table class="table table-bordered" id="settleTable" style="width: 100%">
										<thead>
						    				<tr class="text-info text-center" style="height: 35px;">
						    					<th>Sr No</th>
						    					<th>Driver Name</th>
						    					<th>Due Date</th>
						    					<th>Due Amount</th>
						    					<th>Balance Amount</th>
						    					<th>Payment Mode</th>
						    					<th id="typePayment" class="hide typePayment">Payment Type</th>
						    					<th id="typeExpense" class="hide typeExpense">Expense Type</th>
						    					<th id="transactionType" class="hide transactionType fit ar">Transaction Mode</th>
						    					<th id="transactionNo" class="hide transactionNo fit ar">Transaction Number</th>
						    					<th>Paid Date</th>
						    					<th>Paid Amount</th>
						    					<th>Reference</th>
						    				</tr>
						    			</thead>
						    			<tbody id="settleDetails">
						    			</tbody>
						    			
									</table>
									</div>
								
							</div>
							
							<div class="modal-footer">
								<button type="submit" id="saveSettlement" class="btn btn-primary">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal" id="close">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
					  </div>
				</div>
			</div>
		</div>
	</section>	

	
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" 
			src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/tripsheet/tripsheetDueAmount.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>		

	
