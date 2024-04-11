<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addTripIncome/1/1.in"/>">New Trip Income</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
					<c:if test="${configuration.tripIncomeWithRate}">
						<button type="button" class="btn btn-success" onclick="openModal();">
							<span class="fa fa-plus"> Create Trip Income</span>
						</button>
					</c:if>	
					<c:if test="${!configuration.tripIncomeWithRate}">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addTripIncome" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Trip Income</span>
						</button>
					</c:if>	
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveTripIncome eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Income Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadTripIncome eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Income Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTripIncome eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Income Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyTripIncome eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Income Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addTripIncome" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="<c:url value="/saveTripIncome.in"/>" method="post" name="vehicleStatu"
						onsubmit="return validateParts()">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripIncome">Trip Income</h4>
							</div>
							<div class="modal-body">

								<div class="row">
									<label class="L-size control-label">Income Type :</label>
									<div class="I-size">
										<select name="incomeType" class="form-text"
											required="required">
											<option value="1">TRIP INCOME</option>
											<option value="2">ROUTE INCOME</option>
											<option value="3">CASHBOOK INCOME</option>
										</select>
									</div>
								</div>
								<br>

								<div class="row">
									<label class="L-size control-label">Income Type :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="incomeName"
											placeholder="Enter Income Name" maxlength="200" id="pcName"
											onkeypress="return IncomeName(event);" ondrop="return false;"
											required="required" /> <label id="errorIncomeName"
											class="error"></label>
									</div>
								</div>
								<br>

								<div class="row">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text" name="incomeRemarks"
											rows="3" maxlength="200"
											onkeypress="return IncomeRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorIncomeRemarks" class="error"></label>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary" onclick="return tripIncmeValidate()">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addTripIncomeWithRate" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="form-horizontal ">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="TripIncome">Trip Income</h4>
						</div>
						<div class="modal-body">

							<div class="row">
								<label class="L-size control-label">Income Name :</label>
								<div class="I-size">
									<select name="incomeType" id="incomeType"class="form-text"
										required="required">
										<option value="1">TRIP INCOME</option>
										<option value="2">ROUTE INCOME</option>
										<option value="3">CASHBOOK INCOME</option>
									</select>
								</div>
							</div>
							<br>

							<div class="row">
								<label class="L-size control-label">Income Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" name="incomeName"
										placeholder="Enter Income Name" maxlength="200" id="incomeName"
										onkeypress="return IncomeName(event);" ondrop="return false;"
										required="required" /> <label id="errorIncomeName"
										class="error"></label>
								</div>
							</div>
							<br>
							<div class="row" >
								<label class="L-size control-label">Commision :</label>
								<div class="col-md-3" >
								<input type="text" class="form-text" name="commisionMany" 
									min="0.0" id="commision" maxlength="5" value= 0.0 
									onkeypress="return isNumberKeyWithDecimal(event,this);"
									placeholder="commision" required="required" >
								</div>
							</div>
							<br>
							<div class="row">
								<label class="L-size control-label">GST :</label>
								<div class="col-md-3" >
								<input type="text" class="form-text" name="tax_many" value="0.0" 
								onkeypress="return isNumberKeyWithDecimal(event,this);"
								id="tax" maxlength="5" min="0.0" placeholder="GST" required="required">
								</div>
							</div>
							<br>
							<div class="row">
								<label class="L-size control-label">Description :</label>
								<div class="I-size">
									<textarea class="text optional form-text" name="incomeRemarks"
										rows="3" maxlength="200" id="discription"
										onkeypress="return IncomeRemarks(event);"
										ondrop="return false;"> 
			                                </textarea>
									<label id="errorIncomeRemarks" class="error"></label>
								</div>
							</div>
							<br> <br>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick ="addTripIncomeWithRate();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="editTripIncomeWithRate" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="form-horizontal ">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="TripIncome">Trip Income</h4>
						</div>
						<div class="modal-body">

							<div class="row">
								<label class="L-size control-label">Income Name :</label>
								<div class="I-size">
									<select name="incomeType" id="editIncomeType"class="form-text"
										required="required">
										<option value="1">TRIP INCOME</option>
										<option value="2">ROUTE INCOME</option>
										<option value="3">CASHBOOK INCOME</option>
									</select>
								</div>
							</div>
							<br>

							<div class="row">
								<input type="hidden" id="incomeId">
								<label class="L-size control-label">Income Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" name="incomeName"
										placeholder="Enter Income Name" maxlength="200" id="editIncomeName"
										onkeypress="return IncomeName(event);" ondrop="return false;"
										required="required" /> <label id="errorIncomeName"
										class="error"></label>
								</div>
							</div>
							<br>
							<div class="row" >
								<label class="L-size control-label">Commision :</label>
								<div class="col-md-3" >
								<input type="text" class="form-text" name="commisionMany" 
									min="0.0" id="editCommision" maxlength="5" value= 0.0 
									onkeypress="return isNumberKeyWithDecimal(event,this);"
									placeholder="commision" required="required" >
								</div>
							</div>
							<br>
							<div class="row">
								<label class="L-size control-label">GST :</label>
								<div class="col-md-3" >
								<input type="text" class="form-text" name="tax_many" value="0.0" 
								onkeypress="return isNumberKeyWithDecimal(event,this);"
								id="editTax" maxlength="5" min="0.0" placeholder="GST" required="required">
								</div>
							</div>
							<br>
							<div class="row">
								<label class="L-size control-label">Description :</label>
								<div class="I-size">
									<textarea class="text optional form-text" name="incomeRemarks"
										rows="3" maxlength="200" id="editDiscription"
										onkeypress="return IncomeRemarks(event);"
										ondrop="return false;"> 
			                                </textarea>
									<label id="errorIncomeRemarks" class="error"></label>
								</div>
							</div>
							<br> <br>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick ="updateTripIncomeWithRate();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="row">
									<input type="hidden" value="${IncomeType}" id="statues">
									<ul class="nav nav-tabs" role="tablist">
										<li role="presentation" id="1"><a
											href="<c:url value="/addTripIncome/1/1.in"/>">TRIP INCOME</a></li>
										<li role="presentation" id="2"><a
											href="<c:url value="/addTripIncome/2/1.in"/>">ROUTE
												INCOME</a></li>
										<li role="presentation" id="3"><a
											href="<c:url value="/addTripIncome/3/1.in"/>">CASHBOOK
												INCOME</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<div class="box-body">
										<table id="tripIncomeTable"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th class="fit">ID</th>
													<th>Income Type </th>
													<th>Description</th>
													<th>Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty TripIncome}">
													<c:forEach items="${TripIncome}" var="TripIncome">
														<tr>
															<td><c:out value="${TripIncome.incomeID}" /></td>
															<td><c:out value="${TripIncome.incomeName}" /></td>
															<td><c:out value="${TripIncome.incomeRemarks}" /></td>
															<td>
																<div class="btn-group">
																	<a class="btn btn-default dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('EDIT_PRIVILEGE')">
																				<c:if test="${configuration.tripIncomeWithRate}">
																					<a onclick="editPopup(${TripIncome.incomeID});">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</c:if>
																				<c:if test="${!configuration.tripIncomeWithRate}">
																					<a href="<c:url value="/editTripIncome.in?IncomeID=${TripIncome.incomeID}"/>">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</c:if>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_PRIVILEGE')">
																				<a
																					href="<c:url value="/deleteTripIncome.in?IncomeID=${TripIncome.incomeID}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete ?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

						<c:url var="firstUrl" value="/addTripIncome/${IncomeType}/1" />
						<c:url var="lastUrl"
							value="/addTripIncome/${IncomeType}/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/addTripIncome/${IncomeType}/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/addTripIncome/${IncomeType}/${currentIndex + 1}" />
						<div class="text-center">
							<ul class="pagination pagination-lg pager">
								<c:choose>
									<c:when test="${currentIndex == 1}">
										<li class="disabled"><a href="#">&lt;&lt;</a></li>
										<li class="disabled"><a href="#">&lt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${firstUrl}">&lt;&lt;</a></li>
										<li><a href="${prevUrl}">&lt;</a></li>
									</c:otherwise>
								</c:choose>
								<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
									<c:url var="pageUrl" value="/addTripIncome/${IncomeType}/${i}" />
									<c:choose>
										<c:when test="${i == currentIndex}">
											<li class="active"><a href="${pageUrl}"><c:out
														value="${i}" /></a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${currentIndex == deploymentLog.totalPages}">
										<li class="disabled"><a href="#">&gt;</a></li>
										<li class="disabled"><a href="#">&gt;&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${nextUrl}">&gt;</a></li>
										<li><a href="${lastUrl}">&gt;&gt;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<%--<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/Triplanguage.js" />"></script> --%>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addTripIncome.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>
</div>
