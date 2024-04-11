import 'package:fleetop/Dashboard/Renewal_Dashboard/renewalreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

class RenewalReminderListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map renewalReminderData;

  const RenewalReminderListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.renewalReminderData});
  @override
  _RenewalReminderListViewState createState() => _RenewalReminderListViewState();
}

class _RenewalReminderListViewState extends State<RenewalReminderListView>
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
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Vehicles With RR',
        count: widget.renewalReminderData['renewalCreatedOnVehicle'] != null
            ? widget.renewalReminderData['renewalCreatedOnVehicle'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Vehicles Without RR',
        count: widget.renewalReminderData['renewalNotCreatedOnVehicle'] != null
            ? widget.renewalReminderData['renewalNotCreatedOnVehicle'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Due Soon',
        count: widget.renewalReminderData['totalDueSoonCount'] != null
            ? widget.renewalReminderData['totalDueSoonCount'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Crnt Month Expense',
        details: widget.renewalReminderData['thisMonthRenewalCount'] != null
            ? widget.renewalReminderData['thisMonthRenewalCount'].toString()
            : '0',
        count: widget.renewalReminderData['thisMonthRenewalAmount'] != null
            ? "\u20B9"+widget.renewalReminderData['thisMonthRenewalAmount'].toString()
            : "\u20B9"+'0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/Tour-Bus.png',
        titleTxt: 'Next Month Expense',
        details: widget.renewalReminderData['nextMonthRenewalCount'] != null
            ? widget.renewalReminderData['nextMonthRenewalCount'].toString()
            : '0',
        count: widget.renewalReminderData['nextMonthRenewalAmount'] != null
            ? "\u20B9"+widget.renewalReminderData['nextMonthRenewalAmount'].toString()
            : "\u20B9"+'0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Mandatory & NonMandatory Renewal',
        count: widget.renewalReminderData['mandateAndNonMandate'] != null
            ? widget.renewalReminderData['mandateAndNonMandate'].toString()
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

  getAllData(index) {
    if (index == DashBoardConstant.VEHICLES_WITH_RR) {
      if (widget.renewalReminderData['renewalCreatedOnVehicle'] == null ||
          widget.renewalReminderData['renewalCreatedOnVehicle'] == 0) {
        return;
      }
      getRenewalReminderTableData("1", index);
    } else if (index == DashBoardConstant.VEHICLES_WITHOUT_RR) {
      if (widget.renewalReminderData['renewalNotCreatedOnVehicle'] == null ||
          widget.renewalReminderData['renewalNotCreatedOnVehicle'] == 0) {
        return;
      }
      getRenewalReminderTableData("2", index);
    } else if (index == DashBoardConstant.DUE_SOON_RR) {
      if (widget.renewalReminderData['totalDueSoonCount'] == null ||
          widget.renewalReminderData['totalDueSoonCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("3", index);
    } else if (index == DashBoardConstant.CRNT_MONTH_RR_EXPENSE) {
      if (widget.renewalReminderData['thisMonthRenewalCount'] == null ||
          widget.renewalReminderData['thisMonthRenewalCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("5", index);
    } else if (index == DashBoardConstant.NEXT_MONTH_RR_EXPENSE) {
      if (widget.renewalReminderData['nextMonthRenewalCount'] == null ||
          widget.renewalReminderData['nextMonthRenewalCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("6", index);
    } else if (index == DashBoardConstant.MANDATORY_NON_MANDATORY) {
      if (widget.renewalReminderData['nextMonthRenewalCount'] == null ||
          widget.renewalReminderData['nextMonthRenewalCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("11", index);
    }
  }

  getRenewalReminderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.RENEWAL_REMINDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.VEHICLES_WITH_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new RenewalReminderReportData(
                listData: data['renewalReminder'],
                titleText: "Vehicles With RR",
                index: DashBoardConstant.VEHICLES_WITH_RR_TABLE)));
      } else if (index == DashBoardConstant.VEHICLES_WITHOUT_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new RenewalReminderReportData(
                listData: data['renewalReminder'],
                titleText: "Vehicles Without RR",
                index: DashBoardConstant.VEHICLES_WITHOUT_RR_TABLE)));
      } else if (index == DashBoardConstant.DUE_SOON_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "Due Soon RR",
              index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } else if (index == DashBoardConstant.CRNT_MONTH_RR_EXPENSE) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "Current Month Expense",
              index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } else if (index == DashBoardConstant.NEXT_MONTH_RR_EXPENSE) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "Next Month Renewal Expense",
              index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } else if (index == DashBoardConstant.MANDATORY_NON_MANDATORY) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "Next Month Renewal Expense",
              index: DashBoardConstant.RR_MANDATORY_TABLE),
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

class DaysWiseRenewalReminderListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map renewalReminderData;

  const DaysWiseRenewalReminderListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.renewalReminderData});
  @override
  _DaysWiseRenewalReminderListViewState createState() => _DaysWiseRenewalReminderListViewState();
}

class _DaysWiseRenewalReminderListViewState extends State<DaysWiseRenewalReminderListView>
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
    if (index == DashBoardConstant.OVER_DUE_RR) {
      if (widget.renewalReminderData['totalOverDueCount'] == null ||
          widget.renewalReminderData['totalOverDueCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("7", index);
    } else if (index == DashBoardConstant.ZERO_TO_SEVEN_RR) {
      if (widget.renewalReminderData['totalSevenDaysCount'] == null ||
          widget.renewalReminderData['totalSevenDaysCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("8", index);
    } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_RR) {
      if (widget.renewalReminderData['totalFifteenDaysCount'] == null ||
          widget.renewalReminderData['totalFifteenDaysCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("9", index);
    } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
      if (widget.renewalReminderData['totalFifteenPlusDaysCount'] == null ||
          widget.renewalReminderData['totalFifteenPlusDaysCount'] == 0) {
        return;
      }
      getRenewalReminderTableData("10", index);
    }
  }

  getRenewalReminderTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.RENEWAL_REMINDER_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.OVER_DUE_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new RenewalReminderReportData(
                listData: data['renewalReminder'],
                titleText: "Over Due RR",
                index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE)));
      } else if (index == DashBoardConstant.ZERO_TO_SEVEN_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new RenewalReminderReportData(
                listData: data['renewalReminder'],
                titleText: "0 - 7 Days",
                index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE)));
      } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_RR) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "8 - 15 Days",
              index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } else if (index == DashBoardConstant.FIFTEEEN_PLUS_DAYS) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new RenewalReminderReportData(
              listData: data['renewalReminder'],
              titleText: "15+ Days",
              index: DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE),
        ));
      } 
    }
  }

  setListData() {
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Over Due',
        count: widget.renewalReminderData['totalOverDueCount'] != null
            ? widget.renewalReminderData['totalOverDueCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: '0 - 7 Days',
        count: widget.renewalReminderData['totalSevenDaysCount'] != null
            ? widget.renewalReminderData['totalSevenDaysCount'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: '8 - 15 Days',
        count: widget.renewalReminderData['totalFifteenDaysCount'] != null
            ? widget.renewalReminderData['totalFifteenDaysCount'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: '15+ Days',
        count: widget.renewalReminderData['totalFifteenPlusDaysCount'] != null
            ? widget.renewalReminderData['totalFifteenPlusDaysCount'].toString()
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

