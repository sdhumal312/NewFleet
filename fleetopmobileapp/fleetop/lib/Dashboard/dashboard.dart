import 'dart:ui';

import 'package:fleetop/CalenderPopUp/calenderpopupview.dart';
import 'package:fleetop/Dashboard/summaryListView.dart';
import 'package:fleetop/Dashboard/summarybar.dart';
import 'package:fleetop/Dashboard/summaryseries.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/custom_dialogue/custom_loader_dialogue.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:charts_flutter/flutter.dart' as charts;

class Dashboard extends KFDrawerContent {
  Dashboard({
    Key key,
  });

  @override
  _DashboardState createState() => _DashboardState();
}

class _DashboardState extends State<Dashboard> with TickerProviderStateMixin {
  AnimationController animationController;
  double _width;
  Size size;
  double _height;
  int time = 1000;
  DateTime startDate = DateTime.now();
  DateTime endDate = DateTime.now();
  String companyId;
  var tripSheetCount = '0';
  var fuelCount = '0';
  var workOrderCounts = '0';
  var serviceReminderCount = '0';
  var renewalCounts = '0';
  var issuesCount = '0';
  var serviceEntryCount = '0';
  List<Widget> listViews = List<Widget>();
  var scrollController = ScrollController();
  List<SummaryBar> bardata = List<SummaryBar>();
  String selectedDate;
  bool showdate = false;

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);

    super.initState();
    addAllListData();
    getAllData(startDate, endDate);
    setChartData();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  addAllListData() {
    var count = 3;
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
      child: SummaryListView(
        tripSheetCount: tripSheetCount,
        fuelCount: fuelCount,
        workOrderCounts: workOrderCounts,
        serviceReminderCount: serviceReminderCount,
        renewalCounts: renewalCounts,
        issuesCount: issuesCount,
        serviceEntryCount: serviceEntryCount,
        startSelectedDate: startDate,
        endSelectedDate: endDate,
        mainScreenAnimation: Tween(begin: 1.0, end: 2.0).animate(
            CurvedAnimation(
                parent: animationController,
                curve: Interval((1 / count) * 3, 1.0,
                    curve: Curves.fastOutSlowIn))),
        mainScreenAnimationController: animationController,
      ),
    ));

    listViews.add(Center(child: SummarySeries(data: bardata)));
  }

  getDateRange(value) {
    setState(() {
      showdate = true;
    });
    if (value == 'Today') {
      setState(() {
        startDate = DateTime.now();
        endDate = DateTime.now();
        getAllData(startDate, endDate);
      });
    } else if (value == 'Yesterday') {
      setState(() {
        startDate = DateTime.now().subtract(Duration(days: 1));
        endDate = DateTime.now().subtract(Duration(days: 1));
        getAllData(startDate, endDate);
      });
    } else if (value == 'Last 7 Days') {
      setState(() {
        startDate = DateTime.now().subtract(Duration(days: 6));
        endDate = DateTime.now();
        getAllData(startDate, endDate);
      });
    } else if (value == 'Last Month') {
      var day =
          DateTime(DateTime.now().year, DateTime.now().year - 1, 0).day == 31
              ? 2
              : 1;
      setState(() {
        startDate =
            new DateTime(DateTime.now().year, DateTime.now().month - 1, 1);
        // startDate = DateTime.now().subtract(Duration(
        //     days: DateTime.now().day +
        //         DateTime(DateTime.now().year, DateTime.now().year - 1, 0).day - day
        //         ));
        endDate = DateTime.now().subtract(Duration(days: DateTime.now().day));
        getAllData(startDate, endDate);
      });
    } else if (value == 'Choose Date') {
      FocusScope.of(context).requestFocus(FocusNode());
      showDemoDialog(context: context);
    }
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  getAllData(startDate, endDate) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");

    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    showDialog(
        context: context,
        builder: (BuildContext context) {
          return CustomLoaderDialog();
        });
    var tripSheetData = await ApiCall.getDataWithoutLoader(
        URI.TRIP_SHEET_DATA, workOder, URI.LIVE_URI);

    if (tripSheetData != null) {
      setState(() {
        tripSheetCount = tripSheetData['tripSheetCount'] != null
            ? tripSheetData['tripSheetCount'].toString()
            : "0";
      });
    }

    var fuelData = await ApiCall.getDataWithoutLoader(
        URI.FUEL_COUNT_DATA, workOder, URI.LIVE_URI);

    if (fuelData != null) {
      setState(() {
        fuelCount = fuelData['fuelCount'] != null
            ? fuelData['fuelCount'].toString()
            : "0";
      });
    }

    var woData = await ApiCall.getDataWithoutLoader(
        URI.WORK_ORDER_DATA, workOder, URI.LIVE_URI);

    if (woData != null) {
      setState(() {
        workOrderCounts = woData['workOrderCounts'] != null
            ? woData['workOrderCounts'].toString()
            : "0";
      });
    }

    var srData = await ApiCall.getDataWithoutLoader(
        URI.SERVICE_REM_DATA, workOder, URI.LIVE_URI);

    if (srData != null) {
      setState(() {
        serviceReminderCount = srData['serviceReminderCount'] != null
            ? srData['serviceReminderCount'].toString()
            : "0";
      });
    }

    var rrData = await ApiCall.getDataWithoutLoader(
        URI.RENEWAL_REM_DATA, workOder, URI.LIVE_URI);

    if (rrData != null) {
      setState(() {
        renewalCounts = rrData['renewalCounts'] != null
            ? rrData['renewalCounts'].toString()
            : "0";
      });
    }

    var issueData = await ApiCall.getDataWithoutLoader(
        URI.ISSUE_COUNT_DATA, workOder, URI.LIVE_URI);

    if (issueData != null) {
      setState(() {
        issuesCount = issueData['issuesCount'] != null
            ? issueData['issuesCount'].toString()
            : "0";
      });
    }

    var seData = await ApiCall.getDataWithoutLoader(
        URI.SERVICE_ENTRY_DATA, workOder, URI.LIVE_URI);

    if (seData != null) {
      setState(() {
        serviceEntryCount = seData['serviceEntryCount'].toString();
      });
    }
    Navigator.pop(context);
    listViews.clear();
    setChartData();
    addAllListData();
  }

  setChartData() {
    bardata = [
      SummaryBar(
          summaryName: " Trip Sheet",
          summaryCount: tripSheetCount,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Fuel",
          summaryCount: fuelCount,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Work Order",
          summaryCount: workOrderCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Service Reminder",
          summaryCount: serviceReminderCount,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Renewal",
          summaryCount: renewalCounts,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Issues",
          summaryCount: issuesCount,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
      SummaryBar(
          summaryName: " Service Entry",
          summaryCount: serviceEntryCount,
          barColor: charts.ColorUtil.fromDartColor(Colors.blue)),
    ];
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
          getAllData(startData, endData);
        },
        onCancelClick: () {},
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      backgroundColor: AppTheme.white,
      body: FutureBuilder(
        future: getData(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return SizedBox();
          } else {
            return Padding(
              padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      ClipRRect(
                        borderRadius: BorderRadius.all(Radius.circular(32.0)),
                        child: Material(
                          shadowColor: Colors.transparent,
                          color: Colors.transparent,
                          child: IconButton(
                            icon: Icon(
                              Icons.menu,
                              color: Colors.black,
                            ),
                            onPressed: widget.onMenuPressed,
                          ),
                        ),
                      ),
                      Expanded(
                        child: Center(
                          child: Padding(
                            padding: const EdgeInsets.only(top: 4, right: 20),
                            child: Text(
                              "Work Summary",
                              style: new TextStyle(
                                fontSize: 22,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                  Expanded(
                    child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (!snapshot.hasData) {
                          return SizedBox();
                        } else {
                          return Scaffold(
                            body: Container(
                              child: Stack(
                                children: <Widget>[getMainListViewUI()],
                              ),
                            ),
                          );
                        }
                      },
                    ),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }

  Widget appBar() {
    return SizedBox(
      height: AppBar().preferredSize.height,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(top: 8, left: 8),
            child: Container(
              width: AppBar().preferredSize.height - 8,
              height: AppBar().preferredSize.height - 8,
            ),
          ),
          Expanded(
            child: Center(
              child: Padding(
                padding: const EdgeInsets.only(top: 4, right: 20),
                child: Text(
                  "Dashboard",
                  style: new TextStyle(
                    fontSize: 22,
                    color: AppTheme.darkText,
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
            ),
          ),
        ],
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
            padding: EdgeInsets.all(8),
            controller: scrollController,
            itemCount: listViews.length,
            scrollDirection: Axis.vertical,
            itemBuilder: (context, index) {
              animationController.forward();
              return listViews[index];
            },
          );
        }
      },
    );
  }
}
