<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.E_Income{
	background-color: #c1d4f0   ;
}
/*    #c1d4f0  #c4f1db*/
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CashBookEntry/1.in"/>">Cash Book</a>
				</div>
				<div class="pull-right">

					<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/addOldCashBookEntry.in"/>"> <span
							class="fa fa-plus"></span> Add Last CashBook
						</a>
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addCashBookEntry.in"/>"> <span
							class="fa fa-plus"></span> Add Today CashBook
						</a>
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/closeCashBookEntry.in"/>"> Close CashBook
							Balance </a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_CASHBOOK')">
						<a class="btn btn-danger btn-sm"
							href="<c:url value="/missingAddCashBookEntry.in"/>"> <span
							class="fa fa-plus"></span> Missing CashBook
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/CashBookEntryReport"/>"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>

				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectCashBookName}</span> <input
								type="hidden" value="${SelectCashBook}" id="statues"> <span
								class="info-box-number">${CashBookCount}</span>
						</div>
					</div>
				</div>

				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search CashBook</span>
							<form action="<c:url value="/searchCashBookEntryShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">C-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="C-ID eg:455">
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
			</sec:authorize>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
								<c:if test="${!empty CashBookName}">
										<c:forEach items="${CashBookName}" var="CashBookName">
											<c:choose>
												<c:when test ="${Configuration.checkAuthorityForCashBook}" >
													<c:set var="cleanedCashBookName" value="${fn:replace(CashBookName.CASHBOOK_NAME, ' ', '_')}" />
													<c:set var="newCashBookName" value="${fn:replace(cleanedCashBookName, '-', '_')}" />
													<c:set var="authority" value="VIEW_${newCashBookName}" />
													<sec:authorize access="hasAuthority('${authority}')">
													    <li role="presentation" id="${CashBookName.NAMEID}">
													        <a href="<c:url value='/CashBookEntry/${CashBookName.NAMEID}/1.in'/>">${CashBookName.CASHBOOK_NAME}</a>
													    </li>
													</sec:authorize>
												</c:when>
												<c:otherwise>
													<li role="presentation" id="${CashBookName.NAMEID}"><a
													href="<c:url value="/CashBookEntry/${CashBookName.NAMEID}/1.in"/>">${CashBookName.CASHBOOK_NAME}</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="VendorTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>CashBook</th>
											<th class="fit ar">V.No</th>
											<th class="fit ar">Type</th>
											<th class="fit ar">Date</th>
											<th class="fit ar">Amount</th>
											<th class="fit ar">Status</th>
											<th>Payment</th>
											<th>Reference No.</th>
											<th>Doc</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<input type ="hidden" id= "checkAuthorityForCashBook" value="${Configuration.checkAuthorityForCashBook}">
										<input type = "hidden" id ="showCashbookTable" value ="false">
									
										<c:if test="${!empty CashBook}">
											<c:forEach items="${CashBook}" var="CashBook">
											 	
												<c:if test="${Configuration.checkAuthorityForCashBook}">
													<c:set var="oldCashBookName" value="${fn:replace(CashBook.CASH_BOOK_NO,'-', '_')}" />
													<c:set var="newCashBookName" value="${fn:replace(oldCashBookName,' ', '_')}" />
													<c:set var="authority" value="VIEW_${newCashBookName}" />
													
													<sec:authorize access="hasAuthority('${authority}')">
														<input type = "hidden" id ="showCashbookTable2" value ="true">
													</sec:authorize>	
												</c:if>	
												<%-- <tr data-object-id="" class="ng-scope showCashBookTableView ${CashBook.INCOME_TYPE eq 'E_INCOME' ? 'E_Income' : ''} "> --%>
												<tr data-object-id="" class="ng-scope showCashBookTableView ${CashBook.INCOME_TYPE eq 'E_INCOME' && CashBook.CASH_BOOK_NO eq 'COMBINED CASH BOOK' ? 'E_Income' : ''} "> 
													<td class="fit"><a
														href="<c:url value="/showCashBookEntry.in?Id=${CashBook.CASHID}"/>"><c:out
																value="C-${CashBook.CASH_NUMBER}" /></a></td>
													<td><c:out value="${CashBook.CASH_BOOK_NO}" /></td>
													<td class="fit ar"><c:out
															value="${CashBook.CASH_VOUCHER_NO}" /></td>
													<td class="fir ar"><c:choose>
															<c:when test="${CashBook.PAYMENT_TYPE_ID == 1}">
																<small class="label label-success"><c:out
																		value="${CashBook.CASH_PAYMENT_TYPE}" /></small>
															</c:when>
															<c:otherwise>
																<small class="label label-warning"><c:out
																		value="${CashBook.CASH_PAYMENT_TYPE}" /></small>
															</c:otherwise>
														</c:choose></td>
													<td class="fir ar"><c:out
															value="${CashBook.CASH_DATE}" /></td>
													<td class="fit ar"><c:out
															value="${CashBook.CASH_AMT_STR}" /></td>
													<td class="fit ar"><c:choose>
															<c:when
																test="${CashBook.CASH_APPROVAL_STATUS_ID == 2 }">
																<small class="label label-success"><c:out
																		value="${CashBook.CASH_APPROVAL_STATUS}" /></small>
															</c:when>
															<c:otherwise>
																<small class="label label-danger"><c:out
																		value="${CashBook.CASH_APPROVAL_STATUS}" /></small>
															</c:otherwise>
														</c:choose></td>
													<td class="icon"><c:out
															value="${CashBook.CASH_NATURE_PAYMENT}" />
														<ul class="list-inline no-margin">
															<li><c:out value="${CashBook.CASH_PAID_RECEIVED}" /></li>
														</ul></td>
													<td class="icon">
														<c:out value="${CashBook.CASH_REFERENCENO}" escapeXml="false"/>
													</td>	
													<td><sec:authorize
															access="hasAuthority('DOWNLOAD_CASHBOOK')">
															<c:choose>
																<c:when test="${CashBook.CASH_DOCUMENT == true}">
																	<a
																		href="${pageContext.request.contextPath}/download/CashDocument/${CashBook.CASH_DOCUMENT_ID}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</c:when>
															</c:choose>
														</sec:authorize></td>
													<td><c:if test="${CashBook.CASH_STATUS_ID == 1}">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>

																<ul class="dropdown-menu pull-right">


																	<li><sec:authorize
																			access="hasAuthority('EDIT_CASHBOOK')">
																			<a
																				href="<c:url value="/editCashBookEntry.in?Id=${CashBook.CASHID}"/>">
																				<i class="fa fa-edit"></i> Edit Cash
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_CASHBOOK')">
																			<a
																				href="<c:url value="/deleteCashBookEntry.in?Id=${CashBook.CASHID}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete Cash
																			</a>
																		</sec:authorize></li>

																</ul>
															</div>
														</c:if></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/CashBookEntry/${SelectCashBook}/1" />
					<c:url var="lastUrl" value="/CashBookEntry/${SelectCashBook}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/CashBookEntry/${SelectCashBook}/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/CashBookEntry/${SelectCashBook}/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/CashBookEntry/${SelectCashBook}/${i}" />
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
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">$(document).ready(function(){
		if($('#checkAuthorityForCashBook').val() == 'true' || $('#checkAuthorityForCashBook').val() == true){
		     if($('#showCashbookTable2').val() == 'true' || $('#showCashbookTable2').val() == true){
		    	 $('.showCashBookTableView').show();
		     }else{
		    	 $('.showCashBookTableView').hide(); 
		     }
		   }
		
			var e=document.getElementById("statues").value;
				switch(e){
					 case"ALL":document.getElementById("All").className="active";
						 break;
					 case e:document.getElementById(e).className="active";
				}
		});
	</script>

</div>
