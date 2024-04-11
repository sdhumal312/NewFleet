<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/error/error.css" />">
<%@ include file="../error/error.html" %>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addCashBookName.in"/>">New CashBook Name</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addCashBook" data-whatever="@mdo">
							<span class="fa fa-plus"> Create CashBook Name</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Name Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Name Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Name Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Name Already Exists.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleGroup eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				 CashBook With Selected Vehicle Group is Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addCashBook" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveCashBookName.in" method="post"
						name="vehicleStatu">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">New CashBook</h4>
							</div>
							<div class="modal-body">

								<div class="row">
									<label class="L-size control-label">CashBook :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="CASHBOOK_NAME"
											placeholder="Enter CashBook Name" maxlength="250" id="pcName"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;" required="required" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								<br/>
									<div class="row" id="grpVehicleGroup">
												<div class="form-group">
													<label class="L-size string required control-label">Group
														Service :</label>
													<div class="I-size">
														<select class="form-text" required="required" name="VEHICLE_GROUP_ID" id="vehiclegroupid">
														<option value="0">
																		<c:out value="Select Group" />
														</option>
															<c:forEach items="${VehicleGroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
										</div>
								<br>
									<div class="row">
											<label class="L-size control-label">CashBook Code :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="CASHBOOK_CODE"
													placeholder="Enter CashBook Code"
													 maxlength="50"
													id="cashBookCode"
													ondrop="return false;" required="required" /> 
											</div>
										</div>
										<br/>
								<div class="row">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
											name="CASHBOOK_REMARKS" rows="3" maxlength="250"
											onkeypress="return ExpenseRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorExpenseRemarks" class="error"></label>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<input class="btn btn-success"
									onclick="return cashBookValidate();" name="commit"
									type="submit" value="Save CashBook Name" id="cashSubmit">
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
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="tripExpenseTable" class="table table-hover table-bordered">
										<thead>
											<tr>
												<th class="fit">ID</th>
												<th>CashBook Name</th>
												<th>Vehicle Group</th>
												<th>CashBook Code</th>
												<th>Description</th>
												<th>Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty CashBook}">
												<c:forEach items="${CashBook}" var="CashBook">
													<tr>
														<td><c:out value="${CashBook.NAMEID}" /></td>
														<td><c:out value="${CashBook.CASHBOOK_NAME}" /></td>
														<td><c:out value="${CashBook.VEHICLE_GROUP}" /></td>
														<td><c:out value="${CashBook.CASHBOOK_CODE}" /></td>
														<td><c:out value="${CashBook.CASHBOOK_REMARKS}" /></td>
														<td><c:choose>
																<c:when
																	test="${CashBook.CASHBOOK_NAME == 'MAIN-CASH-BOOK'}">
																</c:when>
																<c:otherwise>
																	<div class="btn-group">
																		<a class="btn btn-default dropdown-toggle"
																			data-toggle="dropdown" href="#"> <span
																			class="fa fa-cog"></span> <span class="caret"></span>
																		</a>
																		<ul class="dropdown-menu pull-right">
																			<li><sec:authorize
																					access="hasAuthority('EDIT_PRIVILEGE')">
																					<a href="editCashBookName.in?Id=${CashBook.NAMEID}">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_PRIVILEGE')">
																					<a
																						href="deleteCashBookName.in?Id=${CashBook.NAMEID}"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete ?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</ul>
																	</div>
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript">
			function cashBookValidate(){
				if($("#pcName").val() == undefined || ($("#pcName").val()).trim() == "" ){
					showMessage('info','Please Enter  CashBook Name')
					return false;
				}
			}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
</div>