<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addBankAccount/1.in"/>">New Trip Expense</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addTripExpense" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Bank Account</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveAccount eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Branch Details Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadTripExpense eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Expense Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Bank Account Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Branch Details Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addTripExpense" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="<c:url value="/saveBankaccount.in"/>" method="post" name="vehicleStatu"
						onsubmit="return validateParts()">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">Bank Account</h4>
							</div>
							<div class="modal-body">

								<div class="row1" id="grpjobType" class="form-group">
									<label class="L-size control-label" for="jobType">Bank
										 :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<select id="bankId" class="form-text selectType select2" name="bankId"
											style="width: 100%;" id="jobType">
											<option value="0">Select Bank</option>
											<c:forEach items="${bankList}" var="bankList">
												<option value="${bankList.bankId}">${bankList.bankName}
												</option>
											</c:forEach>
										</select><span id="jobTypeIcon" class=""></span>
										<div id="jobTypeErrorMsg" class="text-danger"></div>
										<label id="errorReType" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Branch Name :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" name="name"
											placeholder="Enter Branch Name" maxlength="200" id="name"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;" required="required" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Account Number :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" name="accountNumber"
											placeholder="Enter Account Number" maxlength="200" id="accountNumber"
											onkeypress="return isNumberKey(event,this);"
											ondrop="return false;" required="required" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> IFSC Code :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="IFSCCode"
											placeholder="Enter IFSC Code" maxlength="200" id="IFSCCode"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;"/> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> MICR Code :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="MICRCode"
											placeholder="Enter MICR Code" maxlength="200" id="MICRCode"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Contact :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="contactNumber"
											placeholder="Enter Contact Details" maxlength="15" id="contactNumber"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
											name="description" rows="3" maxlength="200"
											onkeypress="return ExpenseRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorExpenseRemarks" class="error"></label>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<button type="submit" onclick="return validateBankAccount();" class="btn btn-primary">
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
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">

					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="tripExpenseTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="fit">ID</th>
												<th>Name</th>
												<th>Bank Name</th>
												<th>Account Number</th>
												<th>IFSC</th>
												<th>Contact</th>
												<th>MICR</th>
												<th>Description</th>
												<th>Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty bankAccount}">
												<c:forEach items="${bankAccount}" var="bankAccount">
													<tr>
														<td><c:out value="${bankAccount.bankAccountId}" /></td>
														<td><c:out value="${bankAccount.name}" /></td>
														<td><c:out value="${bankAccount.bankName}" /></td>
														<td><c:out value="${bankAccount.accountNumber}" /></td>
														<td><c:out value="${bankAccount.IFSCCode}" /></td>
														<td><c:out value="${bankAccount.contactNumber}" /></td>
														<td><c:out value="${bankAccount.MICRCode}" /></td>
														<td><c:out value="${bankAccount.description}" /></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<%-- <li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="<c:url value="/editTripExpense.in?ExpenseID=${bankAccount.bankAccountId}"/>" >
																				<span class="fa fa-pencil"></span> Edit
																				
																			</a>
																		</sec:authorize></li> --%>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																				<a
																					href="<c:url value="/deleteBankAccount.in?bankAccountId=${bankAccount.bankAccountId}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete ?')">
																					<span class="fa fa-trash"></span> Delete
																		</a>
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
						<c:url var="firstUrl" value="/addBankAccount/1" />
						<c:url var="lastUrl"
							value="/addBankAccount/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/addBankAccount/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/addBankAccount/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/addBankAccount/${i}" />
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/master/BankValidate.js" />"></script>
</div>