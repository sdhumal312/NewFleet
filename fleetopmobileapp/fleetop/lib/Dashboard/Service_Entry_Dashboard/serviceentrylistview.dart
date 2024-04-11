import 'package:fleetop/Dashboard/Service_Entry_Dashboard/serviceentryreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

class ServiceEntryListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map serviceEntryData;

  const ServiceEntryListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.serviceEntryData});
  @override
  _ServiceEntryListViewState createState() => _ServiceEntryListViewState();
}

class _ServiceEntryListViewState extends State<ServiceEntryListView>
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
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'Service Entries Created',
        count: widget.serviceEntryData['SECreatedCounts'] != null
            ? widget.serviceEntryData['SECreatedCounts'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'Open Service Entries',
        count: widget.serviceEntryData['SEOpenCounts'] != null
            ? widget.serviceEntryData['SEOpenCounts'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'In Process Service Entries',
        count: widget.serviceEntryData['SEProcessCounts'] != null
            ? widget.serviceEntryData['SEProcessCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'Complete Service Entries',
        count: widget.serviceEntryData['SECloseCounts'] != null
            ? widget.serviceEntryData['SECloseCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
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
    if (index == DashBoardConstant.CREATED_SERVICE_ENTRIES) {
      if (widget.serviceEntryData['SECreatedCounts'] == null ||
          widget.serviceEntryData['SECreatedCounts'] == 0) {
        return;
      }
      getServiceEntriesTableData("0", index);
    } else if (index == DashBoardConstant.OPEN_SERVICE_ENTRIES) {
      if (widget.serviceEntryData['SEOpenCounts'] == null ||
          widget.serviceEntryData['SEOpenCounts'] == 0) {
        return;
      }
      getServiceEntriesTableData("1", index);
    } else if (index == DashBoardConstant.IN_PROCESS_SERVICE_ENTRIES) {
      if (widget.serviceEntryData['SEProcessCounts'] == null ||
          widget.serviceEntryData['SEProcessCounts'] == 0) {
        return;
      }
      getServiceEntriesTableData("2", index);
    } else if (index == DashBoardConstant.CLOSED_SERVICE_ENTRIES) {
      if (widget.serviceEntryData['SECloseCounts'] == null ||
          widget.serviceEntryData['SECloseCounts'] == 0) {
        return;
      }
      getServiceEntriesTableData("3", index);
    } 
  }

  getServiceEntriesTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_ENTRY_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.CREATED_SERVICE_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceEntryReportData(
                listData: data['serviceEntry'],
                titleText: "Created Service Entries",
                index: DashBoardConstant.CREATED_SERVICE_ENTRIES)));
      } else if (index == DashBoardConstant.OPEN_SERVICE_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceEntryReportData(
                listData: data['serviceEntry'],
                titleText: "Open Service Entries",
                index: DashBoardConstant.OPEN_SERVICE_ENTRIES)));
      } else if (index == DashBoardConstant.IN_PROCESS_SERVICE_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceEntryReportData(
              listData: data['serviceEntry'],
              titleText: "In Process",
              index: DashBoardConstant.IN_PROCESS_SERVICE_ENTRIES),
        ));
      } else if (index == DashBoardConstant.CLOSED_SERVICE_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceEntryReportData(
              listData: data['serviceEntry'],
              titleText: "Closed Service Entries",
              index: DashBoardConstant.CLOSED_SERVICE_ENTRIES),
        ));
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

class DaysWiseServiceEntryListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map serviceEntryData;

  const DaysWiseServiceEntryListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.serviceEntryData});
  @override
  _DaysWiseServiceEntryListViewState createState() => _DaysWiseServiceEntryListViewState();
}

class _DaysWiseServiceEntryListViewState extends State<DaysWiseServiceEntryListView>
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

   getAllData(index) {
    if (index == DashBoardConstant.SE_DUE_PAYMENT) {
      if (widget.serviceEntryData['duePaymentCount'] == null ||
          widget.serviceEntryData['duePaymentCount'] == 0) {
        return;
      }
      getServiceEntriesTableData("4", index);
    } else if (index == DashBoardConstant.All_OPEN_SERVICE_ENTRIES) {
      if (widget.serviceEntryData['SEAllOpenCounts'] == null ||
          widget.serviceEntryData['SEAllOpenCounts'] == 0) {
        return;
      }
      getServiceEntriesTableData("5", index);
    } else if (index == DashBoardConstant.ZERO_TO_SEVEN_S_ENTRIES) {
      if (widget.serviceEntryData['from7Days'] == null ||
          widget.serviceEntryData['from7Days'] == 0) {
        return;
      }
      getServiceEntriesTableData("6", index);
    } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_S_ENTRIES) {
      if (widget.serviceEntryData['from15Days'] == null ||
          widget.serviceEntryData['from15Days'] == 0) {
        return;
      }
      getServiceEntriesTableData("7", index);
    } else if (index == DashBoardConstant.FIFTEEN_PLUS_S_ENTRIES) {
      if (widget.serviceEntryData['from30Days'] == null ||
          widget.serviceEntryData['from30Days'] == 0) {
        return;
      }
      getServiceEntriesTableData("8", index);
    }
  }

  getServiceEntriesTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_ENTRY_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.SE_DUE_PAYMENT) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceEntryReportData(
                listData: data['serviceEntry'],
                titleText: "SE Due Payment Count & Amount",
                index: DashBoardConstant.SE_DUE_PAYMENT)));
      } else if (index == DashBoardConstant.All_OPEN_SERVICE_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceEntryReportData(
                listData: data['serviceEntry'],
                titleText: "All Open",
                index: DashBoardConstant.All_OPEN_SERVICE_ENTRIES)));
      } else if (index == DashBoardConstant.ZERO_TO_SEVEN_S_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceEntryReportData(
                listData: data['serviceEntry'],
                titleText: "0 - 7 Days",
                index: DashBoardConstant.ZERO_TO_SEVEN_S_ENTRIES)));
      } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_S_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceEntryReportData(
              listData: data['serviceEntry'],
              titleText: "8 - 15 Days",
              index: DashBoardConstant.EIGHT_TO_FIFTEEN_S_ENTRIES),
        ));
      } else if (index == DashBoardConstant.FIFTEEN_PLUS_S_ENTRIES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceEntryReportData(
              listData: data['serviceEntry'],
              titleText: "15+ Days",
              index: DashBoardConstant.FIFTEEN_PLUS_S_ENTRIES),
        ));
      } 
    }
  }

  setListData() {
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'SE Due Payment',
        details: widget.serviceEntryData['duePaymentCount'] != null
            ? "Count - "+widget.serviceEntryData['duePaymentCount'].toString()
            : '0',
        count: widget.serviceEntryData['duePaymentAmount'] != null
            ? "\u20B9"+widget.serviceEntryData['duePaymentAmount'].toString()
            : '0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'Total OverDue',
        count: widget.serviceEntryData['SEAllOpenCounts'] != null
            ? widget.serviceEntryData['SEAllOpenCounts'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: '0 - 7 Days',
        count: widget.serviceEntryData['from7Days'] != null
            ? widget.serviceEntryData['from7Days'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: '8 - 15 Days',
        count: widget.serviceEntryData['from15Days'] != null
            ? widget.serviceEntryData['from15Days'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: '15+ Days',
        count: widget.serviceEntryData['from30Days'] != null
            ? widget.serviceEntryData['from30Days'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
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
                                                Shimmer.fromColors(
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
                                                    )),
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

