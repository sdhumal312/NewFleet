<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
	
 <head>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.0.1/css/buttons.dataTables.css"> 
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
      <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script> 
     <script src="https://cdn.datatables.net/buttons/2.0.1/js/dataTables.buttons.js"></script>
     <script src="https://cdn.datatables.net/buttons/2.0.1/js/buttons.html5.js"></script> 
     <script src="https://cdn.datatables.net/buttons/2.0.1/js/buttons.print.js"></script> 
     <script src="https://cdn.datatables.net/buttons/2.0.1/js/buttons.colVis.js"></script> 
</head> 
	
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newMasterParts.in"/>">Parts</a> / <a
						href="<c:url value="/PartReport.in"/>">Parts Report</a>
				</div>
				<div class="pull-right">
					<a href="newMasterParts/1.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<div class="main-body">
					<h4>TripSheets Report</h4>
					<div class="box">
						<div class="box-body">
							<table id="MasterpartTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>Part Number</th>
										<th>Part Name</th>
										<th class="fit ar">Category</th>
										<th class="fit ar">Make</th>
										<th class="fit ar">Desciption</th>

									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty MasterParts}">
										<c:forEach items="${MasterParts}" var="MasterParts">
											<tr>
												<td><a target="_blank"
													href="showMasterParts.in?partid=${MasterParts.partid}"
													data-toggle="tip"
													data-original-title="Last Update:<c:out value="${MasterParts.lastupdated}"/>"><c:out
															value="${MasterParts.partnumber}" /></a></td>
												<td><c:out value="${MasterParts.partname}" /></td>
												<td class="fit ar"><c:out
														value="${MasterParts.category}" /></td>
												<td class="fit ar"><c:out value="${MasterParts.make}" /></td>
												<td><c:out value="${MasterParts.description}" /></td>

											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
    <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script> 
	
	<script src="https://cdn.datatables.net/buttons/2.0.1/js/buttons.colVis.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.3.1/js/buttons.html5.min.js"></script>
	

	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#MasterpartTable").DataTable({
				
				sScrollX : "100%",
				oLanguage : {
					sEmptyTable : "Empty Trip Route.."
				},
				bScrollcollapse : !0,
				dom : "Blfrtip",
				
				buttons : [
                    {
                        extend: 'excel',
                        text: 'Export Excel',
                        exportOptions: {
                            columns: ':visible'
                        }
                    },
                    'print'
                ],
                
				order : [ 0, "desc" ],
				lengthMenu: [[10, 25, 50, -1], ["10", "25", "50", "Give All"]], // Customize the available options with "All"
		        pageLength: 10
			})
		});
	</script>
	
</div>
