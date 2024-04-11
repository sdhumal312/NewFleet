import 'package:fleetop/Dashboard/Trip_Sheet_Dashboard/tripsheetreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';
import 'package:flutter_money_formatter/flutter_money_formatter.dart';

class TripSheetListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map tripSheetData;

  const TripSheetListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.tripSheetData});
  @override
  _TripSheetListViewState createState() => _TripSheetListViewState();
}

class _TripSheetListViewState extends State<TripSheetListView>
    with TickerProviderStateMixin {
  AnimationController animationController;
  List<SummaryCountListData> summaryCountListData;
  DateTime startDate;
  DateTime endDate;
  String companyId;
  final formatCurrency = new NumberFormat.simpleCurrency();

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  setListData() {

    FlutterMoneyFormatter tripIncome = FlutterMoneyFormatter( amount: widget.tripSheetData['tripIncome']);
    FlutterMoneyFormatter tripExpense = FlutterMoneyFormatter( amount: widget.tripSheetData['tripExpense']);
    FlutterMoneyFormatter tripAdvance = FlutterMoneyFormatter( amount: widget.tripSheetData['tripAdvance']);

    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Trip Sheet Created',
        count: widget.tripSheetData['tripsheetCreatedCount'] != null
            ? widget.tripSheetData['tripsheetCreatedCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Open Trip Sheet',
        count: widget.tripSheetData['tripSheetCountsInOpenState'] != null
            ? widget.tripSheetData['tripSheetCountsInOpenState'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Closed Trip Sheet',
        count: widget.tripSheetData['tripSheetClosedCount'] != null
            ? widget.tripSheetData['tripSheetClosedCount'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Account Closed',
        count:
            widget.tripSheetData['tripSheetCountsInAccountClosedState'] != null
                ? widget.tripSheetData['tripSheetCountsInAccountClosedState']
                    .toString()
                : '0',
        startColor: "#6F72CA",
        endColor: "#1E1466",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Total Run Count',
        count: widget.tripSheetData['totalRunCount'] != null
            ? widget.tripSheetData['totalRunCount'].toString() + " KM"
            : '0 KM',
        startColor: "#dfbf9f",
        endColor: "#86592d",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Closed Trip Advance',
        count: widget.tripSheetData['tripAdvance'] != null
            ? "\u20B9"+tripAdvance.output.nonSymbol.toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Close Trip Income',
        count: widget.tripSheetData['tripIncome'] != null
            ? "\u20B9"+tripIncome.output.nonSymbol.toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Close Trip Expense',
        count: widget.tripSheetData['tripExpense'] != null
            ? "\u20B9"+tripExpense.output.nonSymbol.toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      // SummaryCountListData(
      //   imagePath: 'assets/img/Tour-Bus.png',
      //   titleTxt: 'Missed Closing Tripsheet',
      //   count: widget.tripSheetData['tripSheetCountsMissedClosing'] != null
      //       ? widget.tripSheetData['tripSheetCountsMissedClosing'].toString()
      //       : '0',
      //   startColor: "#33ff77",
      //   endColor: "#006622",
      // ),
      // SummaryCountListData(
      //   imagePath: 'assets/img/Tour-Bus.png',
      //   titleTxt: 'Oldest Open Tripsheet',
      //   details: widget.tripSheetData['tripSheetNumber'] != null
      //       ? "TS - " + widget.tripSheetData['tripSheetNumber'].toString()
      //       : '',
      //   count: widget.tripSheetData['dayDiff'] != null
      //       ? widget.tripSheetData['dayDiff'].toString() + " Days"
      //       : '0 Days',
      //   startColor: "#d9b3ff",
      //   endColor: "#8c1aff",
      // ),
    ];
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 50));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  getAllData(index) {
    if (index == DashBoardConstant.CREATED_TRIP_SHEET) {
      if (widget.tripSheetData['tripsheetCreatedCount'] == null ||
          widget.tripSheetData['tripsheetCreatedCount'] == 0) {
        return;
      }
      getTripSheetTableData("1", index);
    } else if (index == DashBoardConstant.OPEN_TRIP_SHEET) {
      if (widget.tripSheetData['tripSheetCountsInOpenState'] == null ||
          widget.tripSheetData['tripSheetCountsInOpenState'] == 0) {
        return;
      }
      getTripSheetTableData("2", index);
    } else if (index == DashBoardConstant.CLOSED_TRIP_SHEET) {
      if (widget.tripSheetData['tripSheetClosedCount'] == null ||
          widget.tripSheetData['tripSheetClosedCount'] == 0) {
        return;
      }
      getTripSheetTableData("3", index);
    } else if (index == DashBoardConstant.ACCOUNT_CLOSED) {
      if (widget.tripSheetData['tripSheetCountsInAccountClosedState'] == null ||
          widget.tripSheetData['tripSheetCountsInAccountClosedState'] == 0) {
        return;
      }
      getTripSheetTableData("4", index);
    } else if (index == DashBoardConstant.TOTAL_RUN_COUNT) {
      if (widget.tripSheetData['totalRunCount'] == null ||
          widget.tripSheetData['totalRunCount'] == "0") {
        return;
      }
      getTripSheetTableData("5", index);
    } else if (index == DashBoardConstant.MISSED_CLOSING_TRIP_SHEET) {
      if (widget.tripSheetData['tripSheetCountsMissedClosing'] == null ||
          widget.tripSheetData['tripSheetCountsMissedClosing'] == 0) {
        return;
      }
      getTripSheetTableData("6", index);
    }
  }

  getTripSheetTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.TRIP_SHEET_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.CREATED_TRIP_SHEET) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripSheetCreated'],
                titleText: "Trip Sheet Created",
                index: DashBoardConstant.CREATED_TRIP_SHEET)));
      } else if (index == DashBoardConstant.OPEN_TRIP_SHEET) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripSheetOpen'],
                titleText: "Open Trip Sheet",
                index: DashBoardConstant.OPEN_TRIP_SHEET)));
      } else if (index == DashBoardConstant.CLOSED_TRIP_SHEET) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new TripSheetReportData(
              listData: data['tripSheetClosed'],
              titleText: "Closed Trip Sheet",
              index: DashBoardConstant.CLOSED_TRIP_SHEET),
        ));
      } else if (index == DashBoardConstant.ACCOUNT_CLOSED) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripSheetAccountClosed'],
                titleText: "Account Closed",
                index: DashBoardConstant.ACCOUNT_CLOSED)));
      } else if (index == DashBoardConstant.TOTAL_RUN_COUNT) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripSheetUsageKM'],
                titleText: "Total Run",
                index: DashBoardConstant.TOTAL_RUN_COUNT)));
      } else if (index == DashBoardConstant.MISSED_CLOSING_TRIP_SHEET) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripMissedClosing'],
                titleText: "Missed Closing Trip Sheet",
                index: DashBoardConstant.MISSED_CLOSING_TRIP_SHEET)));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    setListData();
    return AnimatedBuilder(
      animation: widget.mainScreenAnimationController,
      builder: (BuildContext context, Widget child) {
        return FadeTransition(
          opacity: widget.mainScreenAnimation,
          child: new Transform(
            transform: new Matrix4.translationValues(
                0.0, 30 * (1.0 - widget.mainScreenAnimation.value), 0.0),
            child: Container(
              height: 130,
              width: double.infinity,
              child: ListView.builder(
                padding: const EdgeInsets.only(
                    top: 0, bottom: 0, right: 16, left: 16),
                itemCount: summaryCountListData.length,
                scrollDirection: Axis.horizontal,
                itemBuilder: (context, index) {
                  var count = summaryCountListData.length > 10
                      ? 10
                      : summaryCountListData.length;
                   print("count...${count}");   
                  var animation = Tween(begin: 0.0, end: 1.0).animate(
                      CurvedAnimation(
                          parent: animationController,
                          curve: Interval((1 / count) * index, 1.0,
                              curve: Curves.fastOutSlowIn)));
                  animationController.forward();

                  return GestureDetector(
                      onTap: () {
                        getAllData(index);
                      },
                      child: SummaryCountView(
                        summaryCountListData: summaryCountListData[index],
                        animation: animation,
                        animationController: animationController,
                      ));
                },
              ),
            ),
          ),
        );
      },
    );
  }
}

class CloseTripSheetListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map tripSheetData;

  const CloseTripSheetListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.tripSheetData});
  @override
  _CloseTripSheetListViewState createState() => _CloseTripSheetListViewState();
}

class _CloseTripSheetListViewState extends State<CloseTripSheetListView>
    with TickerProviderStateMixin {
  AnimationController animationController;
  List<SummaryCountListData> summaryCountListData;
  DateTime startDate;
  DateTime endDate;
  String companyId;

  @override
  void initState() {
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  setListData() {
   
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;

    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Todays Tripsheet Left To Be Closed ',
        count: widget.tripSheetData['todaysTripOpenStatusCount'] != null
            ? widget.tripSheetData['todaysTripOpenStatusCount'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Total Tripsheet In Dispatched Status',
        count: widget.tripSheetData['tripSheetDispatchedCount'] != null
            ? widget.tripSheetData['tripSheetDispatchedCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Total Tripsheet In Saved Status',
        count: widget.tripSheetData['tripSheetSavedCount'] != null
            ? widget.tripSheetData['tripSheetSavedCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Oldest Open Tripsheet',
        details: widget.tripSheetData['tripSheetNumber'] != null
            ? "TS - " + widget.tripSheetData['tripSheetNumber'].toString()
            : '',
        count: widget.tripSheetData['dayDiff'] != null
            ? widget.tripSheetData['dayDiff'].toString() + " Days"
            : '0 Days',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      
    ];
  }

  getAllDataForPendingWork(index) {
    print("index..${index}");
    if (index == DashBoardConstant.TRIP_SHEET_LEFT_TO_CLOSE) {
      if (widget.tripSheetData['todaysTripOpenStatusCount'] == null ||
          widget.tripSheetData['todaysTripOpenStatusCount'] == 0) {
        return;
      }
      getTripSheetTableDataForPendingWork("6", index);
    } else if (index == DashBoardConstant.TRIP_SHEET_DISPATCH_COUNT) {
      if (widget.tripSheetData['tripSheetDispatchedCount'] == null ||
          widget.tripSheetData['tripSheetDispatchedCount'] == 0) {
        return;
      }
      getTripSheetTableDataForPendingWork("7", index);
    } else if (index == DashBoardConstant.TRIP_SHEET_SAVE_COUNT) {
      if (widget.tripSheetData['tripSheetSavedCount'] == null ||
          widget.tripSheetData['tripSheetSavedCount'] == 0) {
        return;
      }
      getTripSheetTableDataForPendingWork("8", index);
    }  
  }

  getTripSheetTableDataForPendingWork(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.TRIP_SHEET_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.TRIP_SHEET_LEFT_TO_CLOSE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripMissedClosing'],
                titleText: "TripSheet Still Left To Be Closed",
                index: DashBoardConstant.TRIP_SHEET_LEFT_TO_CLOSE)));
      } else if (index == DashBoardConstant.TRIP_SHEET_DISPATCH_COUNT) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new TripSheetReportData(
                listData: data['tripMissedClosing'],
                titleText: "Dispatched Trip Sheet",
                index: DashBoardConstant.TRIP_SHEET_DISPATCH_COUNT)));
      } else if (index == DashBoardConstant.TRIP_SHEET_SAVE_COUNT) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new TripSheetReportData(
              listData: data['tripMissedClosing'],
              titleText: "Saved Trip Sheet",
              index: DashBoardConstant.TRIP_SHEET_SAVE_COUNT),
        ));
      }   
    }
  }  

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 50));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    setListData();
    return AnimatedBuilder(
      animation: widget.mainScreenAnimationController,
      builder: (BuildContext context, Widget child) {
        return FadeTransition(
          opacity: widget.mainScreenAnimation,
          child: new Transform(
            transform: new Matrix4.translationValues(
                0.0, 30 * (1.0 - widget.mainScreenAnimation.value), 0.0),
            child: Container(
              height: 130,
              width: double.infinity,
              child: ListView.builder(
                padding: const EdgeInsets.only(
                    top: 0, bottom: 0, right: 16, left: 16),
                itemCount: summaryCountListData.length,
                scrollDirection: Axis.horizontal,
                itemBuilder: (context, index) {
                  var count = summaryCountListData.length > 10
                      ? 10
                      : summaryCountListData.length;
                  print("2ndrowcount..${count}");    
                  var animation = Tween(begin: 0.0, end: 1.0).animate(
                      CurvedAnimation(
                          parent: animationController,
                          curve: Interval((1 / count) * index, 1.0,
                              curve: Curves.fastOutSlowIn)));
                  animationController.forward();

                  return GestureDetector(
                      onTap: () {
                        getAllDataForPendingWork(index);
                      },
                      child: SummaryCountView(
                        summaryCountListData: summaryCountListData[index],
                        animation: animation,
                        animationController: animationController,
                      ));
                },
              ),
            ),
          ),
        );
      },
    );
  }
}

class SummaryCountView extends StatelessWidget {
  final SummaryCountListData summaryCountListData;
  final AnimationController animationController;
  final Animation animation;

  const SummaryCountView(
      {Key key,
      this.summaryCountListData,
      this.animationController,
      this.animation})
      : super(key: key);
  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: animationController,
      builder: (BuildContext context, Widget child) {
        return FadeTransition(
          opacity: animation,
          child: new Transform(
            transform: new Matrix4.translationValues(
                100 * (1.0 - animation.value), 0.0, 0.0),
            child: InkWell(
              splashColor: Colors.transparent,
              child: SizedBox(
                width: 280,
                child: Stack(
                  children: <Widget>[
                    Container(
                      child: Row(
                        children: <Widget>[
                          SizedBox(
                            width: 40,
                            height: 30,
                          ),
                          Expanded(
                            child: Container(
                              decoration: new BoxDecoration(
                                gradient: LinearGradient(
                                  colors: [Colors.blueGrey, Colors.white],
                                  begin: Alignment.topLeft,
                                  end: Alignment.bottomRight,
                                ),
                                borderRadius: BorderRadius.only(
                                  bottomRight: Radius.circular(8.0),
                                  bottomLeft: Radius.circular(8.0),
                                  topLeft: Radius.circular(8.0),
                                  topRight: Radius.circular(8.0),
                                ),
                              ),
                              child: Row(
                                children: <Widget>[
                                  SizedBox(
                                    width: 48,
                                  ),
                                  Expanded(
                                    child: Container(
                                      child: Column(
                                        children: <Widget>[
                                          Padding(
                                            padding:
                                                const EdgeInsets.only(top: 16),
                                            child: Shimmer.fromColors(
                                                highlightColor: Colors.black,
                                                baseColor: Colors.blueGrey,
                                                period: Duration(
                                                    milliseconds: 2000),
                                                child: Text(
                                                  summaryCountListData.titleTxt,
                                                  style: new TextStyle(
                                                    fontSize: 19,
                                                    color: Colors.blue,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                )),
                                          ),
                                          Expanded(
                                            child: Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 15, bottom: 8, left: 18),
                                              child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment.start,
                                                crossAxisAlignment:
                                                    CrossAxisAlignment.start,
                                                children: <Widget>[
                                                  Shimmer.fromColors(
                                                      highlightColor:
                                                          Colors.black,
                                                      baseColor: Colors
                                                          .blueGrey,
                                                      period: Duration(
                                                          milliseconds: 2000),
                                                      child: Text(
                                                        summaryCountListData
                                                                    .details !=
                                                                null
                                                            ? summaryCountListData
                                                                .details
                                                                .toString()
                                                            : "",
                                                        style: new TextStyle(
                                                          fontSize: 22,
                                                          color: Colors.blue,
                                                          fontWeight:
                                                              FontWeight.w700,
                                                        ),
                                                      )),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Padding(
                                            padding: const EdgeInsets.only(
                                                bottom: 20,
                                                right: 16,
                                                left: 25),
                                            child: Row(
                                              mainAxisAlignment:
                                                  MainAxisAlignment
                                                      .spaceBetween,
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.start,
                                              children: <Widget>[
                                                Flexible(
                                                  child: Shimmer.fromColors(
                                                    highlightColor:
                                                        Colors.blueGrey,
                                                    baseColor:
                                                        Colors.black,
                                                    period: Duration(
                                                        milliseconds: 2000),
                                                    child: Text(
                                                      summaryCountListData
                                                          .count,
                                                      style: new TextStyle(
                                                        fontSize: 22,
                                                        color: Colors.blue,
                                                        fontWeight:
                                                            FontWeight.w700,
                                                      ),
                                                    ))),
                                              ],
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          )
                        ],
                      ),
                    ),
                    Container(
                      child: Padding(
                        padding: const EdgeInsets.only(
                            top: 30, bottom: 30, left: 16),
                        child: Row(
                          children: <Widget>[
                            ClipRRect(
                              borderRadius:
                                  BorderRadius.all(Radius.circular(16.0)),
                              child: AspectRatio(
                                  aspectRatio: 1.0,
                                  child: Image.asset(
                                      summaryCountListData.imagePath)),
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}

