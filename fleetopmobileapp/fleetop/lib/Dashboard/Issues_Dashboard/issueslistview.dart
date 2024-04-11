import 'package:fleetop/Dashboard/Issues_Dashboard/issuesreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';

class IssuesListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map issuesData;

  const IssuesListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.issuesData});
  @override
  _IssuesListViewState createState() => _IssuesListViewState();
}

class _IssuesListViewState extends State<IssuesListView>
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
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Issues Created',
        count: widget.issuesData['issueCreatedCounts'] != null
            ? widget.issuesData['issueCreatedCounts'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Open Issues',
        count: widget.issuesData['issueOpenCounts'] != null
            ? widget.issuesData['issueOpenCounts'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'In Process Issues',
        count: widget.issuesData['issueProcessCounts'] != null
            ? widget.issuesData['issueProcessCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Closed Issues',
        count: widget.issuesData['issueCloseCounts'] != null
            ? widget.issuesData['issueCloseCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Resolved Issues',
        count: widget.issuesData['issueResolveCounts'] != null
            ? widget.issuesData['issueResolveCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Rejected Issues',
        count: widget.issuesData['issueRejectedCounts'] != null
            ? widget.issuesData['issueRejectedCounts'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      // SummaryCountListData(
      //   imagePath: 'assets/img/issuesForDash.png',
      //   titleTxt: 'Over Due Issues',
      //   count: widget.issuesData['issueOverDueCounts'] != null
      //       ? widget.issuesData['issueOverDueCounts'].toString()
      //       : '0',
      //   startColor: "#FE95B6",
      //   endColor: "#FF5287",
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
    if (index == DashBoardConstant.CREATED_ISSUES) {
      if (widget.issuesData['issueCreatedCounts'] == null ||
          widget.issuesData['issueCreatedCounts'] == 0) {
        return;
      }
      getIssuesTableData("0", index);
    } else if (index == DashBoardConstant.OPEN_ISSUES) {
      if (widget.issuesData['issueOpenCounts'] == null ||
          widget.issuesData['issueOpenCounts'] == 0) {
        return;
      }
      getIssuesTableData("1", index);
    } else if (index == DashBoardConstant.IN_PROCESS_ISSUES) {
      if (widget.issuesData['issueProcessCounts'] == null ||
          widget.issuesData['issueProcessCounts'] == 0) {
        return;
      }
      getIssuesTableData("3", index);
    } else if (index == DashBoardConstant.CLOSED_ISSUES) {
      if (widget.issuesData['issueCloseCounts'] == null ||
          widget.issuesData['issueCloseCounts'] == 0) {
        return;
      }
      getIssuesTableData("2", index);
    } else if (index == DashBoardConstant.RESOLVED_ISSUES) {
      if (widget.issuesData['issueResolveCounts'] == null ||
          widget.issuesData['issueResolveCounts'] == 0) {
        return;
      }
      getIssuesTableData("4", index);
    } else if (index == DashBoardConstant.REJECTED_ISSUES) {
      if (widget.issuesData['issueRejectedCounts'] == null ||
          widget.issuesData['issueRejectedCounts'] == 0) {
        return;
      }
      getIssuesTableData("5", index);
     } //else if (index == DashBoardConstant.OVERDUE_ISSUES) {
    //   if (widget.issuesData['issueCloseCounts'] == null ||
    //       widget.issuesData['issueCloseCounts'] == 0) {
    //     return;
    //   }
    //   getIssuesTableData("6", index);
    // }
  }

  getIssuesTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.ISSUES_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.CREATED_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new IssuesReportData(
                listData: data['issue'],
                titleText: "Created Issues",
                index: DashBoardConstant.CREATED_ISSUES)));
      } else if (index == DashBoardConstant.OPEN_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new IssuesReportData(
                listData: data['issue'],
                titleText: "Open Issues",
                index: DashBoardConstant.OPEN_ISSUES)));
      } else if (index == DashBoardConstant.IN_PROCESS_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "In Process Issues",
              index: DashBoardConstant.IN_PROCESS_ISSUES),
        ));
      } else if (index == DashBoardConstant.CLOSED_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "Closed Issues",
              index: DashBoardConstant.CLOSED_ISSUES),
        ));
      } else if (index == DashBoardConstant.RESOLVED_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "Resolved Issues",
              index: DashBoardConstant.RESOLVED_ISSUES),
        ));
      } else if (index == DashBoardConstant.REJECTED_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "Rejected Issues",
              index: DashBoardConstant.REJECTED_ISSUES),
        ));
      } //else if (index == DashBoardConstant.OVERDUE_ISSUES) {
      //   Navigator.of(context).push(new MaterialPageRoute(
      //     builder: (context) => new IssuesReportData(
      //         listData: data['issue'],
      //         titleText: "Over Due Issues",
      //         index: DashBoardConstant.OVERDUE_ISSUES),
      //   ));
      // } 
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

class DaysWiseIssuesListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map issuesData;

  const DaysWiseIssuesListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.issuesData});
  @override
  _DaysWiseIssuesListViewState createState() => _DaysWiseIssuesListViewState();
}

class _DaysWiseIssuesListViewState extends State<DaysWiseIssuesListView>
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
    if (index == DashBoardConstant.TOTAL_OVERDUE) {
      if (widget.issuesData['issueAllOpenCounts'] == null ||
          widget.issuesData['issueAllOpenCounts'] == 0) {
        return;
      }
      getIssuesTableData("6", index);
    } else if (index == DashBoardConstant.ZERO_TO_SEVEN_ISSUES) {
      if (widget.issuesData['from7Days'] == null ||
          widget.issuesData['from7Days'] == 0) {
        return;
      }
      getIssuesTableData("8", index);
    } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_ISSUES) {
      if (widget.issuesData['from15Days'] == null ||
          widget.issuesData['from15Days'] == 0) {
        return;
      }
      getIssuesTableData("9", index);
    } else if (index == DashBoardConstant.FIFTEEN_PLUS_ISSUES) {
      if (widget.issuesData['from30Days'] == null ||
          widget.issuesData['from30Days'] == 0) {
        return;
      }
      getIssuesTableData("10", index);
    }
  }

  getIssuesTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("yyyy-MM-dd").format(startDate),
      'endDate': DateFormat("yyyy-MM-dd").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.ISSUES_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.TOTAL_OVERDUE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new IssuesReportData(
                listData: data['issue'],
                titleText: "Total OverDue",
                index: DashBoardConstant.TOTAL_OVERDUE)));
      } else if (index == DashBoardConstant.ZERO_TO_SEVEN_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new IssuesReportData(
                listData: data['issue'],
                titleText: "0 - 7 Days",
                index: DashBoardConstant.ZERO_TO_SEVEN_ISSUES)));
      } else if (index == DashBoardConstant.EIGHT_TO_FIFTEEN_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "8 - 15 Days",
              index: DashBoardConstant.EIGHT_TO_FIFTEEN_ISSUES),
        ));
      } else if (index == DashBoardConstant.FIFTEEN_PLUS_ISSUES) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new IssuesReportData(
              listData: data['issue'],
              titleText: "15+ Days",
              index: DashBoardConstant.FIFTEEN_PLUS_ISSUES),
        ));
      } 
    }
  }

  setListData() {
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: 'Total Overdue Issues',
        count: widget.issuesData['issueAllOpenCounts'] != null
            ? widget.issuesData['issueAllOpenCounts'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: '0 - 7 Days',
        count: widget.issuesData['from7Days'] != null
            ? widget.issuesData['from7Days'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: '8 - 15 Days',
        count: widget.issuesData['from15Days'] != null
            ? widget.issuesData['from15Days'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/issuesForDash.png',
        titleTxt: '15+ Days',
        count: widget.issuesData['from30Days'] != null
            ? widget.issuesData['from30Days'].toString()
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

