import 'package:fleetop/Dashboard/Service_Reminder_Dashboard/servicereminderreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

class ServiceReminderListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map serviceReminderData;

  const ServiceReminderListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.serviceReminderData});
  @override
  _ServiceReminderListViewState createState() => _ServiceReminderListViewState();
}

class _ServiceReminderListViewState extends State<ServiceReminderListView>
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
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: 'Vehicles With SR',
        count: widget.serviceReminderData['serviceReminderCreated'] != null
            ? widget.serviceReminderData['serviceReminderCreated'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: 'Vehicles Without SR',
        count: widget.serviceReminderData['serviceReminderNotCreated'] != null
            ? widget.serviceReminderData['serviceReminderNotCreated'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: 'Due Soon',
        count: widget.serviceReminderData['totalDueSoonCount'] != null
            ? widget.serviceReminderData['totalDueSoonCount'].toString()
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
    if (index == DashBoardConstant.VEHICLES_WITH_SR) {
      if (widget.serviceReminderData['serviceReminderCreated'] == null ||
          widget.serviceReminderData['serviceReminderCreated'] == 0) {
        return;
      }
      getServiceReminderTableData("1", index);
    } else if (index == DashBoardConstant.VEHICLES_WITHOUT_SR) {
      if (widget.serviceReminderData['serviceReminderNotCreated'] == null ||
          widget.serviceReminderData['serviceReminderNotCreated'] == 0) {
        return;
      }
      getServiceReminderTableData("2", index);
    } else if (index == DashBoardConstant.DUE_SOON_SR) {
      if (widget.serviceReminderData['totalDueSoonCount'] == null ||
          widget.serviceReminderData['totalDueSoonCount'] == 0) {
        return;
      }
      getServiceReminderTableData("3", index);
    }
  }

  getServiceReminderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_REMINDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.VEHICLES_WITH_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceReminderReportData(
                listData: data['serviceReminder'],
                titleText: "Vehicles With Service Reminder",
                index: DashBoardConstant.VEHICLES_WITH_SR_TABLE)));
      } else if (index == DashBoardConstant.VEHICLES_WITHOUT_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceReminderReportData(
                listData: data['serviceReminder'],
                titleText: "Vehicles Without Service Reminder",
                index: DashBoardConstant.VEHICLES_WITHOUT_SR_TABLE)));
      } else if (index == DashBoardConstant.DUE_SOON_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceReminderReportData(
              listData: data['serviceReminder'],
              titleText: "Due Soon Service Reminder",
              index: DashBoardConstant.DUE_SOON_AND_OVERDUE_TABLE),
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

class DaysWiseServiceReminderListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map serviceReminderData;

  const DaysWiseServiceReminderListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.serviceReminderData});
  @override
  _DaysWiseServiceReminderListViewState createState() => _DaysWiseServiceReminderListViewState();
}

class _DaysWiseServiceReminderListViewState extends State<DaysWiseServiceReminderListView>
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
    if (index == DashBoardConstant.OVER_DUE_SR) {
      if (widget.serviceReminderData['totalOverDueCount'] == null ||
          widget.serviceReminderData['totalOverDueCount'] == 0) {
        return;
      }
      getServiceReminderTableData("4", index);
    } else if (index == DashBoardConstant.ZERO_TO_SEVEN_SR) {
      if (widget.serviceReminderData['totalSevenDaysCount'] == null ||
          widget.serviceReminderData['totalSevenDaysCount'] == 0) {
        return;
      }
      getServiceReminderTableData("5", index);
    } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_SR) {
      if (widget.serviceReminderData['totalFifteenDaysCount'] == null ||
          widget.serviceReminderData['totalFifteenDaysCount'] == 0) {
        return;
      }
      getServiceReminderTableData("6", index);
    } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
      if (widget.serviceReminderData['totalFifteenPlusDaysCount'] == null ||
          widget.serviceReminderData['totalFifteenPlusDaysCount'] == 0) {
        return;
      }
      getServiceReminderTableData("7", index);
    }
  }

  getServiceReminderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_REMINDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.OVER_DUE_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceReminderReportData(
                listData: data['serviceReminder'],
                titleText: "Over Due SR",
                index: DashBoardConstant.DUE_SOON_AND_OVERDUE_TABLE)));
      } else if (index == DashBoardConstant.ZERO_TO_SEVEN_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new ServiceReminderReportData(
                listData: data['serviceReminder'],
                titleText: "0 - 7 Days",
                index: DashBoardConstant.DUE_SOON_AND_OVERDUE_TABLE)));
      } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_SR) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceReminderReportData(
              listData: data['serviceReminder'],
              titleText: "8 - 15 Days",
              index: DashBoardConstant.DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new ServiceReminderReportData(
              listData: data['serviceReminder'],
              titleText: "15+ Days",
              index: DashBoardConstant.DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } 
    }
  }

  setListData() {
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: 'Over Due',
        count: widget.serviceReminderData['totalOverDueCount'] != null
            ? widget.serviceReminderData['totalOverDueCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: '0 - 7 Days',
        count: widget.serviceReminderData['totalSevenDaysCount'] != null
            ? widget.serviceReminderData['totalSevenDaysCount'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: '8 - 15 Days',
        count: widget.serviceReminderData['totalFifteenDaysCount'] != null
            ? widget.serviceReminderData['totalFifteenDaysCount'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: '15+ Days',
        count: widget.serviceReminderData['totalFifteenPlusDaysCount'] != null
            ? widget.serviceReminderData['totalFifteenPlusDaysCount'].toString()
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

