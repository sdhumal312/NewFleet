$(function() {

	$(document).ready(
			function() {
				function t(start, end) {
					getBarGraph(start.format('YYYY-MM-DD'), end
							.format('YYYY-MM-DD'));
				}
				t(moment().subtract(15, "days"), moment()), $("#reportrange")
						.daterangepicker(
								{
									ranges : {
										Today : [ moment(), moment() ],
										Yesterday : [
												moment().subtract(1, "days"),
												moment().subtract(1, "days") ],
										"Last 7 Days" : [
												moment().subtract(6, "days"),
												moment() ],
										"Last 30 Days" : [
												moment().subtract(29, "days"),
												moment() ],
										"This Month" : [
												moment().startOf("month"),
												moment().endOf("month") ],
										"Last Month" : [
												moment().subtract(1, "month")
														.startOf("month"),
												moment().subtract(1, "month")
														.endOf("month") ]
									}
								}, t)

			});
});

function getBarGraph(e, r) {
	var jsonObj = [], i = [];
	$
			.getJSON(
					"getBarGraph.in",
					{
						dateFrom : e,
						dateTo : r,
						ajax : "true"
					},
					function(e) {
						if (null != e) {
							t = e

							for (var a = e.length, r = a - 1; a > r && -1 != r; r--) {
								item = {}
								item["name"] = e[r].name;
								item["y"] = e[r].amount;
								jsonObj.push(item);
							}
							$("#BarGraph")
									.highcharts(
											{
												chart : {
													type : 'column'
												},
												title : {
													text : ''
												},
												xAxis : {
													type : 'category'
												},
												yAxis : {
													title : {
														text : 'Total Amount of expence'
													}

												},
												legend : {
													enabled : false
												},
												plotOptions : {
													series : {
														borderWidth : 0,
														dataLabels : {
															enabled : true,
															format : '{point.y:.1f}'
														}
													}
												},

												tooltip : {
													headerFormat : '<span style="font-size:11px">{series.name}</span><br>',
													pointFormat : '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> of total<br/>'
												},

												series : [ {
													name : 'Amount',
													colorByPoint : true,
													data : jsonObj
												} ],
												credits : {
													enabled : !1
												}
											})

							// Build the chart
							$('#PieGraph')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													type : 'pie'
												},
												title : {
													text : ''
												},
												tooltip : {
													pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
														dataLabels : {
															enabled : false
														},
														showInLegend : true
													}
												},
												series : [ {
													name : 'Percentage',
													colorByPoint : true,
													data : jsonObj
												} ],
												credits : {
													enabled : !1
												}
											});
						} else
							$("#container").html("Odometer is Empty")
					})
}

$(function() {
	$('.daterange')
			.daterangepicker(
					{
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ],
							'Last 30 Days' : [ moment().subtract(29, 'days'),
									moment() ],
							'This Month' : [ moment().startOf('month'),
									moment().endOf('month') ],
							'Last Month' : [
									moment().subtract(1, 'month').startOf(
											'month'),
									moment().subtract(1, 'month')
											.endOf('month') ]
						},
						startDate : moment().subtract(29, 'days'),
						endDate : moment()
					},
					function(start, end) {
						getBarGraph(start.format('YYYY-MM-DD'), end
								.format('YYYY-MM-DD'));
					});

});
$(document).ready(
		function() {
			var today = new Date();
			var d = new Date();

			var month = d.getMonth()+1;
			var day = d.getDate();

			var output = d.getFullYear() + '-' +
			    (month<10 ? '0' : '') + month + '-' +
			    (day<10 ? '0' : '') + day;
			$('#todayRenewal').attr('href','DATERR.in?DATE='+output+'');
		});