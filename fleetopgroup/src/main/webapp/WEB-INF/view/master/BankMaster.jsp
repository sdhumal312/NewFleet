<%@ include file="../taglib.jsp"%>
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
						href="<c:url value="/addBank.in"/>">New Bank Name</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addCashBook" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Bank</span>
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
		
		<c:if test="${param.deleteSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Bank Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Bank Already Exists.
			</div>
		</c:if>
		
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addCashBook" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveBank.in" method="post"
						name="vehicleStatu">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">New Bank</h4>
							</div>
							<div class="modal-body">

								<div class="row">
									<label class="L-size control-label">Bank Name :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="bankName"
											placeholder="Enter Bank Name" maxlength="250" id="bankName"
											ondrop="return false;" required="required" /> 
									</div>
								</div>
								<br/>
									<div class="row">
									<label class="L-size control-label">Abbreviation :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="abbreviation"
											placeholder="Enter Abbreviation " maxlength="250" id="pcName"
											ondrop="return false;" /> 
									</div>
								</div>
								<br>
								<div class="row">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
											name="description" rows="3" maxlength="250"
											ondrop="return false;"> 
				                                </textarea>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<input class="btn btn-success"
									onclick="return validateBank();" name="commit"
									type="submit" value="Save Bank" id="cashSubmit">
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
												<th class="fit">Id</th>
												<th>Bank Name</th>
												<th>Description</th>
												<th>Abbreviation</th>
												<th>Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty bankList}">
												<c:forEach items="${bankList}" var="BankMaster">
													<tr>
														<td><c:out value="${BankMaster.bankId}" /></td>
														<td><c:out value="${BankMaster.bankName}" /></td>
														<td><c:out value="${BankMaster.abbreviation}" /></td>
														<td><c:out value="${BankMaster.description}" /></td>
														<td>
																	<%-- <div class="btn-group">
																		<a class="btn btn-default dropdown-toggle"
																			data-toggle="dropdown" href="#"> <span
																			class="fa fa-cog"></span> <span class="caret"></span>
																		</a>
																		<ul class="dropdown-menu pull-right">
																			<li><sec:authorize
																					access="hasAuthority('EDIT_PRIVILEGE')">
																					<a href="editCashBookName.in?Id=${BankMaster.bankId}">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_PRIVILEGE')">
																					<a
																						href="deleteCashBookName.in?Id=${BankMaster.bankId}"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete ?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</ul>
																	</div> --%>
															</td>
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
			<div class="col-sm-1 col-md-2">
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript">
			function validateBank(){
				$('#cashSubmit').hide();
				if($('#bankName').val() == null || $('#bankName').val() == ''){
					$('#bankName').focus();
					showMessage('errors', 'Please Enter Bank Name !');
					$('#cashSubmit').show();
					return false;
				}else{
					return true;
				}
			}
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
</div>