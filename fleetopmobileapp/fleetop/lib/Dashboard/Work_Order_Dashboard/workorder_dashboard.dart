import 'package:fleetop/CalenderPopUp/calenderpopupview.dart';
import 'package:fleetop/Dashboard/Work_Order_Dashboard/workorderlistview.dart';
import 'package:fleetop/Dashboard/summaryseries.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:charts_flutter/flutter.dart' as charts;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

import '../../appTheme.dart';
import '../summaryAppTheme.dart';
import '../summarybar.dart';

class WorkOrderDashboard extends StatefulWidget {
  final Map workOrderData;
  final List locationWiseWOCount;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;

  WorkOrderDashboard(
      {this.workOrderData,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.locationWiseWOCount
      });

  @override
  _WorkOrderDashboardState createState() => _WorkOrderDashboardState();
}

class _WorkOrderDashboardState extends State<WorkOrderDashboard>
    with TickerProviderStateMixin {
  Animation<double> topBarAnimation;
  DateTime startDate = DateTime.now();
  DateTime endDate = DateTime.now();
  String companyId;
  List<SummaryBar> bardata = List<SummaryBar>();
  String workOrderOpenCounts;
  String workOrderProcessCounts;
  String workOrderHoldCounts;
  String workOrderCloseCounts;
  String selectedDate;
  bool showdate = false;
  Map workOrderData = Map();
  List locationWiseWOCount = List();
  Map overDueData = Map();

  List<Widget> listViews = List<Widget>();
  var scrollController = ScrollController();
  double topBarOpacity = 0.0;

  @override
  void initState() {
    topBarAnimation = Tween(begin: 0.0, end: 1.0).animate(CurvedAnimation(
        parent: widget.mainScreenAnimation,
        curve: Interval(0, 0.5, curve: Curves.fastOutSlowIn)));
    getSessionData();
    setInitialData();
    addAllListData();

    scrollController.addListener(() {
      if (scrollController.offset >= 24) {
        if (topBarOpacity != 1.0) {
          setState(() {
            topBarOpacity = 1.0;
          });
        }
      } else if (scrollController.offset <= 24 &&
          scrollController.offset >= 0) {
        if (topBarOpacity != scrollController.offset / 24) {
          setState(() {
            topBarOpacity = scrollController.offset / 24;
          });
        }
      } else if (scrollController.offset <= 0) {
        if (topBarOpacity != 0.0) {
          setState(() {
            topBarOpacity = 0.0;
          });
        }
      }
    });
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  setInitialData() {
    setState(() {
      startDate = widget.startSelectedDate;
      endDate = widget.endSelectedDate;
      workOrderOpenCounts =
          widget.workOrderData["workOrderOpenCounts"].toString();
      workOrderProcessCounts =
          widget.workOrderData["workOrderProcessCounts"].toString();
      workOrderHoldCounts =
          widget.workOrderData["workOrderHoldCounts"].toString();
      workOrderCloseCounts =
          widget.workOrderData["workOrderCloseCounts"].toString();
      workOrderData = widget.workOrderData;
      locationWiseWOCount = widget.locationWiseWOCount;
    });
    getWorkOrderData(startDate, endDate);
  }

  void showDemoDialog({BuildContext context}) {
    showDialog(
      context: context,
      builder: (BuildContext context) => CalendarPopupView(
        barrierDismissible: true,
        maximumDate: DateTime.now(),
        initialEndDate: endDate,
        initialStartDate: startDate,
        onApplyClick: (DateTime startData, DateTime endData) {
          setState(() {
            if (startData != null && endData != null) {
              startDate = startData;
              endDate = endData;
            }
          });
          getWorkOrderData(startData, endData);
        },
        onCancelClick: () {},
      ),
    );
  }

  getWorkOrderData(startDate, endDate) async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  companyId = prefs.getString("companyId");

  await getWorkShopData(startDate, endDate);
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      setState(() {
        workOrderData = data;
        workOrderOpenCounts = data["workOrderOpenCounts"].toString();
        workOrderProcessCounts = data["workOrderProcessCounts"].toString();
        workOrderHoldCounts = data["workOrderHoldCounts"].toString();
        workOrderCloseCounts = data["workOrderCloseCounts"].toString();
        showdate = true;
      });
      listViews.clear();
      addAllListData();
    }
  }

  getWorkShopData(startDate, endDate) async {
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_LOCATION_DATA, workOder, URI.LIVE_URI, context);

    if (data != null) {
      setState(() {
        locationWiseWOCount = data["locationWiseWOCount"];
      });
      listViews.clear();
      addAllListData();
    }
  }

  setPieChartData() {
    bardata = [
      SummaryBar(
          summaryName: "Open",
          summaryCount: workOrderOpenCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.blueAccent)),
      SummaryBar(
          summaryName: "In Process",
          summaryCount: workOrderProcessCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.deepPurpleAccent)),
      SummaryBar(
          summaryName: "Hold",
          summaryCount: workOrderHoldCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.lightGreen)),
      SummaryBar(
          summaryName: "Close",
          summaryCount: workOrderCloseCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.blueGrey)),
    ];
  }

  getDateRange(value) {
    setState(() {
      showdate = true;
    });
    if (value == 'Today') {
      setState(() {
        startDate = DateTime.now();
        endDate = DateTime.now();
      });
      getWorkOrderData(startDate, endDate);
    } else if (value == 'Yesterday') {
      setState(() {
        startDate = DateTime.now().subtract(Duration(days: 1));
        endDate = DateTime.now().subtract(Duration(days: 1));
      });
      getWorkOrderData(startDate, endDate);
    } else if (value == 'Last 7 Days') {
      setState(() {
        startDate = DateTime.now().subtract(Duration(days: 6));
        endDate = DateTime.now();
      });
      getWorkOrderData(startDate, endDate);
    } else if (value == 'Last Month') {
      var day = DateTime(DateTime.now().year, DateTime.now().year - 1, 0).day == 31 ? 2 : 1;
      setState(() {
        startDate = new DateTime(DateTime.now().year, DateTime.now().month - 1, 1);
        // startDate = DateTime.now().subtract(Duration(
        //     days: DateTime.now().day +
        //         DateTime(DateTime.now().year, DateTime.now().year - 1, 0).day - day
        //         ));
        endDate = DateTime.now().subtract(Duration(days: DateTime.now().day));
      });
      getWorkOrderData(startDate, endDate);
    } else if (value == 'Choose Date') {
      FocusScope.of(context).requestFocus(FocusNode());
      showDemoDialog(context: context);
    }
  }

  addAllListData() {
    setPieChartData();
    listViews.add(Column(
      children: <Widget>[
        Container(
          height: 80,
          child: Padding(
            padding:
                const EdgeInsets.only(left: 8, right: 8, top: 4, bottom: 4),
            child: DropdownButtonHideUnderline(
              child: Card(
                  elevation: 0.5,
                  color: Colors.white70,
                  child: Container(
                      padding: EdgeInsets.all(17),
                      child: DropdownButton<String>(
                        hint: Text(
                          "${DateFormat("dd-MM-yyyy").format(startDate)} - ${DateFormat("dd-MM-yyyy").format(endDate)}",
                          style: new TextStyle(
                            fontSize: 20,
                            color: AppTheme.nearlyBlack,
                            fontWeight: FontWeight.w700,
                          ),
                        ),
                        value: selectedDate,
                        isExpanded: true,
                        onChanged: (String newValue) {
                          setState(() {
                            selectedDate = newValue;
                          });
                          getDateRange(newValue);
                        },
                        items: <String>[
                          'Today',
                          'Yesterday',
                          'Last 7 Days',
                          'Last Month',
                          'Choose Date'
                        ].map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value,
                                style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 20.0,
                                    fontFamily: "WorkSansBold")),
                          );
                        }).toList(),
                      ))),
            ),
          ),
        ),
        SizedBox(height: showdate == true ? 8 : 0),
        Visibility(
            visible: showdate,
            child: Text(
              "${DateFormat("dd-MM-yyyy").format(startDate)}   -   ${DateFormat("dd-MM-yyyy").format(endDate)}",
              style: new TextStyle(
                fontSize: 20,
                color: Colors.indigo,
                fontWeight: FontWeight.w700,
              ),
            )),
        SizedBox(height: 8),
      ],
    ));

    listViews.add(Container(
      padding: EdgeInsets.only(top: 40),
      child: WorkOrderListView(
        startSelectedDate: startDate,
        endSelectedDate: endDate,
        mainScreenAnimation: widget.mainScreenAnimation,
        mainScreenAnimationController: widget.mainScreenAnimationController,
        workOrderData: workOrderData,
      ),
    ));

    if(locationWiseWOCount.length > 0){
    for(var i = 0; i < locationWiseWOCount.length; i++){

    listViews.add(
      Container(
      padding: EdgeInsets.only(top: 10),
      child: Center(
        child: Shimmer.fromColors(
            highlightColor: Colors.black,
            baseColor: Colors.lightBlueAccent,
            period: Duration(milliseconds: 1500),
            child: Text(
              locationWiseWOCount.length > 0
                  ? locationWiseWOCount[i]["workorders_location"]
                  : " - ",
              style: new TextStyle(
                fontSize: 22,
                color: Colors.blue,
                fontWeight: FontWeight.w700,
              ),
            )),
      ),
    ));

    listViews.add(Container(
      padding: EdgeInsets.only(top: 40),
      child: WorkShopListView(
        startSelectedDate: startDate,
        endSelectedDate: endDate,
        mainScreenAnimation: widget.mainScreenAnimation,
        mainScreenAnimationController: widget.mainScreenAnimationController,
        locationWiseWOCount: locationWiseWOCount[i],
      ),
    ));
    }
    }

    listViews.add(
      Container(
      padding: EdgeInsets.only(top: 10),
      child: Text(
              "No. Of WO Yet To Complete As Per Today !",
              textAlign: TextAlign.center,
              style: new TextStyle(
                fontSize: 20,
                color: Colors.indigo,
                fontWeight: FontWeight.w700,
              ),
            )
    ));

    listViews.add(Container(
      padding: EdgeInsets.only(top: 40),
      child: WoOverdueListView(
        startSelectedDate: startDate,
        endSelectedDate: endDate,
        mainScreenAnimation: widget.mainScreenAnimation,
        mainScreenAnimationController: widget.mainScreenAnimationController,
        workOrderData: workOrderData,
      ),
    ));

    listViews.add(Center(
        child: PieChartSummary(data: bardata, title: "Work Order Status")));
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 50));
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: SummaryAppTheme.background,
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: Stack(
          children: <Widget>[
            getMainListViewUI(),
            getAppBarUI(),
            SizedBox(
              height: MediaQuery.of(context).padding.bottom,
            )
          ],
        ),
      ),
    );
  }

  Widget getMainListViewUI() {
    return FutureBuilder(
      future: getData(),
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return SizedBox();
        } else {
          return ListView.builder(
            controller: scrollController,
            padding: EdgeInsets.only(
              top: AppBar().preferredSize.height +
                  MediaQuery.of(context).padding.top +
                  24,
              bottom: 62 + MediaQuery.of(context).padding.bottom,
            ),
            itemCount: listViews.length,
            scrollDirection: Axis.vertical,
            itemBuilder: (context, index) {
              widget.mainScreenAnimationController.forward();
              return listViews[index];
            },
          );
        }
      },
    );
  }

  Widget getAppBarUI() {
    return Column(
      children: <Widget>[
        AnimatedBuilder(
          animation: widget.mainScreenAnimation,
          builder: (BuildContext context, Widget child) {
            return Container(
              decoration: BoxDecoration(
                color: SummaryAppTheme.white.withOpacity(topBarOpacity),
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(32.0),
                  bottomRight: Radius.circular(32.0),
                ),
                boxShadow: <BoxShadow>[
                  BoxShadow(
                      color:
                          SummaryAppTheme.grey.withOpacity(0.4 * topBarOpacity),
                      offset: Offset(1.1, 1.1),
                      blurRadius: 10.0),
                ],
              ),
              child: Column(
                children: <Widget>[
                  SizedBox(
                    height: MediaQuery.of(context).padding.top,
                  ),
                  Padding(
                    padding: EdgeInsets.only(
                        left: 16,
                        right: 16,
                        top: 16 - 8.0 * topBarOpacity,
                        bottom: 12 - 8.0 * topBarOpacity),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Expanded(
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Text(
                              "Work Order Summary",
                              textAlign: TextAlign.left,
                              style: TextStyle(
                                fontFamily: SummaryAppTheme.fontName,
                                fontWeight: FontWeight.w700,
                                fontSize: 18 + 6 - 6 * topBarOpacity,
                                letterSpacing: 1.2,
                                color: SummaryAppTheme.darkerText,
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            );
          },
        )
      ],
    );
  }
}
