import 'package:fleetop/CalenderPopUp/calendertheem.dart';
import 'package:fleetop/Dashboard/Fuel_Entries_Dashboard/fuelentries_dashboard.dart';
import 'package:fleetop/Dashboard/Issues_Dashboard/issues_dashboard.dart';
import 'package:fleetop/Dashboard/Renewal_Dashboard/renewal_dashboard.dart';
import 'package:fleetop/Dashboard/Service_Entry_Dashboard/serviceentry_dashboard.dart';
import 'package:fleetop/Dashboard/Service_Reminder_Dashboard/servicereminder_dashboard.dart';
import 'package:fleetop/Dashboard/Trip_Sheet_Dashboard/tripsheet_dashboard.dart';
import 'package:fleetop/Dashboard/Work_Order_Dashboard/workorder_dashboard.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryAppTheme.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../apicall.dart';
import '../fleetopuriconstant.dart';

class SummaryListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final String tripSheetCount;
  final String fuelCount;
  final String workOrderCounts;
  final String serviceReminderCount;
  final String renewalCounts;
  final String issuesCount;
  final String serviceEntryCount;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;

  const SummaryListView(
      {Key key,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.tripSheetCount,
      this.fuelCount,
      this.workOrderCounts,
      this.serviceReminderCount,
      this.renewalCounts,
      this.issuesCount,
      this.serviceEntryCount,
      this.startSelectedDate,
      this.endSelectedDate})
      : super(key: key);
  @override
  _SummaryListViewState createState() => _SummaryListViewState();
}

class _SummaryListViewState extends State<SummaryListView>
    with TickerProviderStateMixin {
  AnimationController animationController;
  List<SummaryCountListData> summaryCountListData;
  DateTime startDate;
  DateTime endDate;
  String companyId;
  List locationWiseWOCount = List();

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
        titleTxt: 'Trip Sheet',
        count: widget.tripSheetCount != null ? widget.tripSheetCount : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Fuel Entry',
        count: widget.fuelCount != null ? widget.fuelCount : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/workForDash.png',
        titleTxt: 'Work Order',
        count: widget.workOrderCounts != null ? widget.workOrderCounts : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceForDash.png',
        titleTxt: 'Service Reminders',
        count: widget.serviceReminderCount != null
            ? widget.serviceReminderCount
            : '0',
        startColor: "#6F72CA",
        endColor: "#1E1466",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/TM-clock.png',
        titleTxt: 'Renewal',
        count: widget.renewalCounts != null ? widget.renewalCounts : '0',
        startColor: "#dfbf9f",
        endColor: "#86592d",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Issues',
        count: widget.issuesCount != null ? widget.issuesCount : '0',
        startColor: "#33ff77",
        endColor: "#006622",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/serviceEntryForDash.png',
        titleTxt: 'Service Entry',
        count:
            widget.serviceEntryCount != null ? widget.serviceEntryCount : '0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
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

  getAllData(index) async {
    if (index == DashBoardConstant.TRIP_SHEET) {
      getTripSheetData();
    } else if (index == DashBoardConstant.FUEL_ENTRY) {
      getFuelEntryData();
    } else if (index == DashBoardConstant.WORK_ORDER) {
      await getWorkShopData();
      getWorkOrderData();
    } else if (index == DashBoardConstant.SERVICE_REMINDER) {
      getServiceReminderData();
    } else if (index == DashBoardConstant.RENEWAL) {
      getRenewalReminderData();
    } else if (index == DashBoardConstant.ISSUES) {
      getIssuesData();
    } else if (index == DashBoardConstant.SERVICE_ENTRY) {
      getServiceEntryData();
    }
  }

    getServiceEntryData() async {
    // if (num.parse(widget.serviceEntryCount) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_ENTRY_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new ServiceEntryDashboard(
            serviceEntryData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }

  getIssuesData() async {
    //     if (num.parse(widget.issuesCount) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.ISSUES_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new IssuesDashboard(
            issuesData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }

  getRenewalReminderData() async{
    // if (num.parse(widget.renewalCounts) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.RENEWAL_REMINDER_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new RenewalReminderDashboard(
            renewalReminderData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }

  getServiceReminderData() async {
    // if (num.parse(widget.serviceReminderCount) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.SERVICE_REMINDER_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new ServiceReminderDashboard(
            serviceReminderData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }

  getTripSheetData() async {
    // if (num.parse(widget.tripSheetCount) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.TRIP_SHEET_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new TripSheetDashboard(
            tripSheetData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }
  getFuelEntryData() async {
    // if (num.parse(widget.fuelCount) <= 0) {
    //   return;
    // }
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.FUEL_ENTRY_DATA_COUNT, workOder, URI.LIVE_URI, context);

    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new FuelEntryDashboard(
            fuelEntryData : data,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
    }
  }

  getWorkShopData() async {
    var workOder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_LOCATION_DATA, workOder, URI.LIVE_URI, context);

    if (data != null) {
      setState(() {
        locationWiseWOCount = data["locationWiseWOCount"];
      });

    }
  }

  getWorkOrderData() async {
    // if (num.parse(widget.workOrderCounts) <= 0) {
    //   return;
    // }
    var workOrder = {
      'compId': companyId,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.WORK_ORDER_DATA_COUNT, workOrder, URI.LIVE_URI, context);

    
    if (data != null) {
      Navigator.of(context).push(
          new MaterialPageRoute(builder: (context) => new WorkOrderDashboard(
            workOrderData : data,
            locationWiseWOCount : locationWiseWOCount,
            startSelectedDate : startDate,
            endSelectedDate : endDate,
            mainScreenAnimationController : widget.mainScreenAnimationController,
            mainScreenAnimation : widget.mainScreenAnimation
            )));
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
              height: 216,
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
            child: SizedBox(
              width: 130,
              child: Stack(
                children: <Widget>[
                  Padding(
                    padding: const EdgeInsets.only(
                        top: 32, left: 8, right: 8, bottom: 16),
                    child: Container(
                      decoration: BoxDecoration(
                        boxShadow: <BoxShadow>[
                          BoxShadow(
                              color: HexColor(summaryCountListData.endColor)
                                  .withOpacity(0.6),
                              offset: Offset(1.1, 4.0),
                              blurRadius: 8.0),
                        ],
                        gradient: LinearGradient(
                          colors: [
                            HexColor(summaryCountListData.startColor),
                            HexColor(summaryCountListData.endColor),
                          ],
                          begin: Alignment.topLeft,
                          end: Alignment.bottomRight,
                        ),
                        borderRadius: BorderRadius.only(
                          bottomRight: Radius.circular(8.0),
                          bottomLeft: Radius.circular(8.0),
                          topLeft: Radius.circular(8.0),
                          topRight: Radius.circular(54.0),
                        ),
                      ),
                      child: Padding(
                        padding: const EdgeInsets.only(
                            top: 54, left: 16, right: 16, bottom: 8),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              summaryCountListData.titleTxt,
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                fontFamily: SummaryAppTheme.fontName,
                                fontWeight: FontWeight.bold,
                                fontSize: 16,
                                letterSpacing: 0.4,
                                color: SummaryAppTheme.white,
                              ),
                            ),
                            Expanded(
                              child: Padding(
                                padding:
                                    const EdgeInsets.only(top: 8, bottom: 8),
                              ),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: <Widget>[
                                Text(
                                  summaryCountListData.count,
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                    fontFamily: SummaryAppTheme.fontName,
                                    fontWeight: FontWeight.w500,
                                    fontSize: 22,
                                    letterSpacing: 0.2,
                                    color: SummaryAppTheme.white,
                                  ),
                                ),
                              ],
                            )
                          ],
                        ),
                      ),
                    ),
                  ),
                  Positioned(
                    top: 0,
                    left: 0,
                    child: Container(
                      width: 84,
                      height: 84,
                      decoration: BoxDecoration(
                        color: SummaryAppTheme.nearlyWhite.withOpacity(0.2),
                        shape: BoxShape.circle,
                      ),
                    ),
                  ),
                  Positioned(
                    top: 0,
                    left: 8,
                    child: SizedBox(
                      width: 80,
                      height: 80,
                      child: Image.asset(summaryCountListData.imagePath),
                    ),
                  )
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
