import 'package:fleetop/Dashboard/Work_Order_Dashboard/workorderreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

class WorkOrderListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map workOrderData;

  const WorkOrderListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.workOrderData});
  @override
  _WorkOrderListViewState createState() => _WorkOrderListViewState();
}

class _WorkOrderListViewState extends State<WorkOrderListView>
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
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Work Order Created',
        count: widget.workOrderData['workOrderCreatedCounts'] != null
            ? widget.workOrderData['workOrderCreatedCounts'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Open',
        count: widget.workOrderData['workOrderOpenCounts'] != null
            ? widget.workOrderData['workOrderOpenCounts'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'In Process',
        count: widget.workOrderData['workOrderProcessCounts'] != null
            ? widget.workOrderData['workOrderProcessCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Hold',
        count:
            widget.workOrderData['workOrderHoldCounts'] != null
                ? widget.workOrderData['workOrderHoldCounts']
                    .toString()
                : '0',
        startColor: "#6F72CA",
        endColor: "#1E1466",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Completed',
        count: widget.workOrderData['workOrderCloseCounts'] != null
            ? widget.workOrderData['workOrderCloseCounts'].toString()
            : '0',
        startColor: "#dfbf9f",
        endColor: "#86592d",
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
    if (index == DashBoardConstant.CREATED_WORK_ORDER) {
      if (widget.workOrderData['workOrderCreatedCounts'] == null ||
          widget.workOrderData['workOrderCreatedCounts'] == 0) {
        return;
      }
      getWorkOrderTableData("0", index);
    } else if (index == DashBoardConstant.OPEN_WORK_ORDER) {
      if (widget.workOrderData['workOrderOpenCounts'] == null ||
          widget.workOrderData['workOrderOpenCounts'] == 0) {
        return;
      }
      getWorkOrderTableData("1", index);
    } else if (index == DashBoardConstant.IN_PROCESS_WORK_ORDER) {
      if (widget.workOrderData['workOrderProcessCounts'] == null ||
          widget.workOrderData['workOrderProcessCounts'] == 0) {
        return;
      }
      getWorkOrderTableData("2", index);
    } else if (index == DashBoardConstant.HOLD_WORK_ORDER) {
      if (widget.workOrderData['workOrderHoldCounts'] == null ||
          widget.workOrderData['workOrderHoldCounts'] == 0) {
        return;
      }
      getWorkOrderTableData("3", index);
    } else if (index == DashBoardConstant.COMPLETED_WORK_ORDER) {
      if (widget.workOrderData['workOrderCloseCounts'] == null ||
          widget.workOrderData['workOrderCloseCounts'] == "0") {
        return;
      }
      getWorkOrderTableData("4", index);
    } 
  }

  getWorkOrderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.CREATED_WORK_ORDER) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "Work Order Created",
                index: DashBoardConstant.CREATED_WORK_ORDER)));
      } else if (index == DashBoardConstant.OPEN_WORK_ORDER) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "Open Work Order",
                index: DashBoardConstant.OPEN_WORK_ORDER)));
      } else if (index == DashBoardConstant.IN_PROCESS_WORK_ORDER) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new WorkOrderReportData(
              listData: data['workOrder'],
              titleText: "In Process Work Order",
              index: DashBoardConstant.IN_PROCESS_WORK_ORDER),
        ));
      } else if (index == DashBoardConstant.HOLD_WORK_ORDER) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "Hold Work Order",
                index: DashBoardConstant.HOLD_WORK_ORDER)));
      } else if (index == DashBoardConstant.COMPLETED_WORK_ORDER) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "Completed Work Order",
                index: DashBoardConstant.COMPLETED_WORK_ORDER)));
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

class WorkShopListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map locationWiseWOCount;

  const WorkShopListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.locationWiseWOCount});
  @override
  _WorkShopListViewListViewState createState() => _WorkShopListViewListViewState();
}

class _WorkShopListViewListViewState extends State<WorkShopListView>
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
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Open & InProcess Count',
        count: widget.locationWiseWOCount['workOrderOpenCount'] != null
            ? widget.locationWiseWOCount['workOrderOpenCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Completed Count',
        count: widget.locationWiseWOCount['workOrderCloseCount'] != null 
            ? widget.locationWiseWOCount['workOrderCloseCount'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
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

                  return  SummaryCountView(
                        summaryCountListData: summaryCountListData[index],
                        animation: animation,
                        animationController: animationController,
                      );
                },
              ),
            ),
          ),
        );
      },
    );
  }
}

class WoOverdueListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map workOrderData;

  const WoOverdueListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.workOrderData});
  @override
  _WoOverdueListViewState createState() => _WoOverdueListViewState();
}

class _WoOverdueListViewState extends State<WoOverdueListView>
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
    if (index == DashBoardConstant.ALL_OPEN) {
      if (widget.workOrderData['workOrderAllOpenCounts'] == null ||
          widget.workOrderData['workOrderAllOpenCounts'] == 0) {
        return;
      }
      getWorkOrderTableData("5", index);
    } else if (index == DashBoardConstant.SEVEN_DAYS) {
      if (widget.workOrderData['from7Days'] == null ||
          widget.workOrderData['from7Days'] == 0) {
        return;
      }
      getWorkOrderTableData("6", index);
    } else if (index == DashBoardConstant.FIFTEEN_DAYS) {
      if (widget.workOrderData['from15Days'] == null ||
          widget.workOrderData['from15Days'] == 0) {
        return;
      }
      getWorkOrderTableData("7", index);
    } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
      if (widget.workOrderData['from30Days'] == null ||
          widget.workOrderData['from30Days'] == 0) {
        return;
      }
      getWorkOrderTableData("8", index);
    }
  }

  getWorkOrderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.ALL_OPEN) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "All Open Work Order",
                index: DashBoardConstant.ALL_OPEN)));
      } else if (index == DashBoardConstant.SEVEN_DAYS) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "0 to 7 Days",
                index: DashBoardConstant.SEVEN_DAYS)));
      } else if (index == DashBoardConstant.FIFTEEN_DAYS) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "8 to 15 Days",
                index: DashBoardConstant.FIFTEEN_DAYS)));
      } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new WorkOrderReportData(
                listData: data['workOrder'],
                titleText: "15+ Days",
                index: DashBoardConstant.FIFTEEEN_PLUS_DAYS)));
      }
    }
  }

  setListData() {
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'All Open',
        count: widget.workOrderData['workOrderAllOpenCounts'] != null
            ? widget.workOrderData['workOrderAllOpenCounts'].toString()
            : '0',
        startColor: "#33ff77",
        endColor: "#006622",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: '0 - 7 Days',
        count: widget.workOrderData['from7Days'] != null
            ? widget.workOrderData['from7Days'].toString()
            : '0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: '8 - 15 Days',
        count: widget.workOrderData['from15Days'] != null
            ? widget.workOrderData['from15Days'].toString()
            : '0',
        startColor: "#33ff77",
        endColor: "#006622",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: '15+ Days',
        count: widget.workOrderData['from30Days'] != null
            ? widget.workOrderData['from30Days'].toString()
            : '0',
        startColor: "#33ff77",
        endColor: "#006622",
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

