<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.css" rel="stylesheet">	
  
  <style>
  
  	.card.card-cascade .view.view-cascade {
    -webkit-box-shadow: 0 5px 11px 0 rgba(0,0,0,.18),0 4px 15px 0 rgba(0,0,0,.15);
    box-shadow: 0 5px 11px 0 rgba(0,0,0,.18),0 4px 15px 0 rgba(0,0,0,.15);
    -webkit-border-radius: .25rem;
    border-radius: .25rem;
	}	
	
	.blue-gradient {
    background: -webkit-linear-gradient(50deg,#45cafc,#303f9f) !important;
    background: -o-linear-gradient(50deg,#45cafc,#303f9f) !important;
    background: linear-gradient(40deg,#45cafc,#303f9f) !important;
	}	
	
	.card.card-cascade .view.view-cascade.gradient-card-header {
    padding: 1.6rem 1rem;
    padding-top: 1.6rem;
    padding-bottom: 1.6rem;
    text-align: center;
	}	
	
	.card.card-cascade.narrower .view.view-cascade {
    margin-left: 4%;
    margin-right: 4%;
    margin-top: -2.25rem;
    height: 29px;
	}
	
	.white-text{
	color: #fff !important;
	}
  
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
			
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetPickAndDrop.in"/>">TripSheet PickAndDrop</a>
				</div>
				
				<div class="col-md-off-5">
					<div class="col-md-2">
						<%-- <form action="<c:url value="/searchTripSheetShow.in"/>"
							method="post"> --%>
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="tripStutes" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="TS-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="searchTripsheet();"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						<!-- </form> -->
					</div>
					
					<div class="pull-right">
						<sec:authorize access="hasAuthority('CREATE_PICK_OR_DROP')">
							<a class="btn btn-success btn-sm" href="createPickAndDropTrip.in"> <span
								class="fa fa-plus"></span> Create TripSheet
							</a>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('EDIT_PICK_OR_DROP')">
							<a class="btn btn-warning btn-sm"
								href="editPickAndDropTrip.in?editPickAndDropId=${dispatchPickAndDropId}"> <span
								class="fa fa-edit"></span> Edit
							</a>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('DELETE_PICK_OR_DROP')">
							<a class="btn btn-danger btn-sm"
								onclick="deleteTripsheetPickAndDrop(${dispatchPickAndDropId})"> <span
								class="fa fa-trash"></span> Delete
							</a>
						</sec:authorize>
						
					</div>
					
				</div>
			</div>
		</div>
	</section>
	
	
	<!-- Main content -->
	
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				
				<div class="box">
					<div class="boxinside">
						
						<div class="card card-cascade narrower">
							<div class="view view-cascade gradient-card-header blue-gradient narrower py-2 mx-4 mb-3 d-flex justify-content-between align-items-center">
								<a href="" class="white-text mx-3">
									<h4>Tripsheet Pick And Drop Details</h4>
								</a>
							</div>
						</div>
						
						<input type="hidden" id="dispatchPickAndDropId" value="${dispatchPickAndDropId}">
						
						<div id="div_printTripsheet">
							
							</br>
							
							<div class="row">
								<div class="pull-left">
									<h4 id="tripNumber">Trip Number : TS- </h4>
								</div>
								<div class="pull-right">
									<h4 id="createdDate">Created Date : </h4>
								</div>
							</div>
							
							<div class="row">
								<h3 align="center" id="vehicle">
								</h3>
							</div>
							
							</br>
							
							<div class="secondary-header-title">
								<table class="table">
									<tbody>
									
										<tr>
											<td>Driver :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="driver">
												</a>
											</td>
											<td>Party Name :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="vendor">
												</a>
											</td>
										</tr>
											
										<tr>
											<td>PickUp/Drop Status :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="status">
												</a>
											</td>
											<td>PickUp/Drop Point :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="location">
												</a>
											</td>
										</tr>
										
										<tr>
											<td>Rate :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="rate">
												</a>
											</td>
											<td>Total KM :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="totalKm">
												</a>
											</td>
										</tr>
											
										<tr>
											<td>Total Amount :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="totalAmount">
												</a>
											</td>
											<td>Advance :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="advance">
												</a>
											</td>
										</tr>
										<tr>
											<td>Remark :
												<a data-toggle="tip" data-original-title="Fixed Point" data-selector="remark">
												</a>
											</td>
											<td>
											</td>
										</tr>	
										
									</tbody>
								</table>
							</div>			
						
						</div>
					</div>
				</div>		
			
			</div>
		</div>
		
	</section>
	
					
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/DispatchPickAndDropTrip.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
		
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/mdb.min.js"></script>	
			
</div>