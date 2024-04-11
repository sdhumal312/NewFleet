import 'package:fleetop/Dashboard/Fuel_Entries_Dashboard/fuelentriesreportdata.dart';
import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:fleetop/Dashboard/summaryListData.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';
import 'package:flutter_money_formatter/flutter_money_formatter.dart';

class FuelEntriesListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map fuelEntryData;

  const FuelEntriesListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.fuelEntryData});
  @override
  _FuelEntriesListViewState createState() => _FuelEntriesListViewState();
}

class _FuelEntriesListViewState extends State<FuelEntriesListView>
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
    FlutterMoneyFormatter totalAmount = FlutterMoneyFormatter( amount: widget.fuelEntryData['todaysTotalFuelCost']);
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Fuel Entries Created',
        count: widget.fuelEntryData['fuelCreatedCount'] != null
            ? widget.fuelEntryData['fuelCreatedCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      // SummaryCountListData(
      //   imagePath: 'assets/img/fuelForDash.png',
      //   titleTxt: 'Active Vehicles',
      //   count: widget.fuelEntryData['activeVehicleCount'] != null
      //       ? widget.fuelEntryData['activeVehicleCount'].toString()
      //       : '0',
      //   startColor: "#738AE6",
      //   endColor: "#5C5EDD",
      // ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Below KM/L',
        count: widget.fuelEntryData['belowRange'] != null
            ? widget.fuelEntryData['belowRange'].toString()
            : '0 KM',
        startColor: "#dfbf9f",
        endColor: "#86592d",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Between KM/L',
        count: widget.fuelEntryData['betweenRange'] != null
            ? widget.fuelEntryData['betweenRange'].toString()
            : '0',
        startColor: "#33ff77",
        endColor: "#006622",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Above KM/L',
        count: widget.fuelEntryData['aboveRange'] != null
            ? widget.fuelEntryData['aboveRange'].toString()
            : '0',
        startColor: "#d9b3ff",
        endColor: "#8c1aff",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Total Amount',
        count: widget.fuelEntryData['todaysTotalFuelCost'] != null
            ? "\u20B9"+totalAmount.output.nonSymbol.toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Total Litres',
        count: widget.fuelEntryData['todaysTotalFuelLiter'] != null
            ? widget.fuelEntryData['todaysTotalFuelLiter'].toStringAsFixed(2)
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Average Price',
        count: widget.fuelEntryData['todaysAverageFuelPrice'] != null
            ? "\u20B9"+widget.fuelEntryData['todaysAverageFuelPrice'].toStringAsFixed(2)
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
    if (index == DashBoardConstant.CREATED_FUEL_ENTRY) {
      if (widget.fuelEntryData['fuelCreatedCount'] == null ||
          widget.fuelEntryData['fuelCreatedCount'] == 0) {
        return;
      }
      getFuelEntriesTableData("4", index);
    } else if (index == DashBoardConstant.BELOW) {
      if (widget.fuelEntryData['belowRange'] == null ||
          widget.fuelEntryData['belowRange'] == "0") {
        return;
      }
      getFuelEntriesTableData("5", index);
    } else if (index == DashBoardConstant.BETWEEN) {
      if (widget.fuelEntryData['betweenRange'] == null ||
          widget.fuelEntryData['betweenRange'] == 0) {
        return;
      }
      getFuelEntriesTableData("6", index);
    } else if (index == DashBoardConstant.ABOVE) {
      if (widget.fuelEntryData['aboveRange'] == null ||
          widget.fuelEntryData['aboveRange'] == 0) {
        return;
      }
      getFuelEntriesTableData("7", index);
    }
    else if (index == DashBoardConstant.TOTAL_PRICE) {
      if (widget.fuelEntryData['todaysTotalFuelCost'] == null ||
          widget.fuelEntryData['todaysTotalFuelCost'] == 0) {
        return;
      }
      getFuelEntriesTableData("4", index);
    }
    else if (index == DashBoardConstant.TOTAL_LITERS) {
      if (widget.fuelEntryData['todaysTotalFuelLiter'] == null ||
          widget.fuelEntryData['todaysTotalFuelLiter'] == 0) {
        return;
      }
      getFuelEntriesTableData("4", index);
    }
    else if (index == DashBoardConstant.TOTAL_AVERAGE) {
      if (widget.fuelEntryData['todaysAverageFuelPrice'] == null ||
          widget.fuelEntryData['todaysAverageFuelPrice'] == 0) {
        return;
      }
      getFuelEntriesTableData("4", index);
    }
  }

  getFuelEntriesTableData(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.FUEL_ENTRY_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.CREATED_FUEL_ENTRY) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Fuel Entries Created",
                index: DashBoardConstant.CREATED_FUEL_ENTRY)));
      }  else if (index == DashBoardConstant.BELOW) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new FuelEntriesReportData(
              listData: data['fuel'],
              titleText: "Below KM/L",
              index: DashBoardConstant.BELOW),
        ));
      } else if (index == DashBoardConstant.BETWEEN) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Between KM/L",
                index: DashBoardConstant.BELOW)));
      } else if (index == DashBoardConstant.ABOVE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Above KM/L",
                index: DashBoardConstant.ABOVE)));
      } else if (index == DashBoardConstant.TOTAL_PRICE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Total Amount",
                index: DashBoardConstant.TOTAL_PRICE)));
      } else if (index == DashBoardConstant.TOTAL_LITERS) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Total Liters",
                index: DashBoardConstant.TOTAL_LITERS)));
      } else if (index == DashBoardConstant.TOTAL_AVERAGE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "Total Average",
                index: DashBoardConstant.TOTAL_AVERAGE)));
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

class FuelEntriesAmountListView extends StatefulWidget {
  final AnimationController mainScreenAnimationController;
  final Animation mainScreenAnimation;
  final DateTime startSelectedDate;
  final DateTime endSelectedDate;
  final Map fuelEntryData;

  const FuelEntriesAmountListView(
      {Key key,
      this.startSelectedDate,
      this.endSelectedDate,
      this.mainScreenAnimationController,
      this.mainScreenAnimation,
      this.fuelEntryData});
  @override
  _FuelEntriesAmountListViewState createState() => _FuelEntriesAmountListViewState();
}

class _FuelEntriesAmountListViewState extends State<FuelEntriesAmountListView>
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
    FlutterMoneyFormatter totalAmount = FlutterMoneyFormatter( amount: widget.fuelEntryData['todaysTotalFuelCost']);
   
    startDate = widget.startSelectedDate;
    endDate = widget.endSelectedDate;
    summaryCountListData = [
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'Total Vehicles',
        count: widget.fuelEntryData['totalVehicleCount'] != null
            ? widget.fuelEntryData['totalVehicleCount'].toString()
            : '0',
        startColor: "#FA7D82",
        endColor: "#FFB295",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'No. Of Vehicles With Fuel Entries',
        count: widget.fuelEntryData['feCreatedOnVehicles'] != null
            ? widget.fuelEntryData['feCreatedOnVehicles'].toString()
            : '0',
        startColor: "#738AE6",
        endColor: "#5C5EDD",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'No. Of Vehicles Without Fuel Entries',
        count: widget.fuelEntryData['feNotCreatedOnVehicles'] != null
            ? widget.fuelEntryData['feNotCreatedOnVehicles'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      SummaryCountListData(
        imagePath: 'assets/img/fuelForDash.png',
        titleTxt: 'No. Of Vehicles Without Expected Mileage (KM/L)',
        count: widget.fuelEntryData['countOfvehiclesWithoutKMPL'] != null
            ? widget.fuelEntryData['countOfvehiclesWithoutKMPL'].toString()
            : '0',
        startColor: "#FE95B6",
        endColor: "#FF5287",
      ),
      
    ];
  }

  getAllDataForPendingWork(index) {
    if (index == DashBoardConstant.VEHICLES_WITH_FE) {
      if (widget.fuelEntryData['feCreatedOnVehicles'] == null ||
          widget.fuelEntryData['feCreatedOnVehicles'] == 0) {
        return;
      }
      getFuelEntriesTableDataForPendingWork("1", index);
    } else if (index == DashBoardConstant.VEHICLES_WITHOUT_FE) {
      if (widget.fuelEntryData['feNotCreatedOnVehicles'] == null ||
          widget.fuelEntryData['feNotCreatedOnVehicles'] == "0") {
        return;
      }
      getFuelEntriesTableDataForPendingWork("2", index);
    } else if (index == DashBoardConstant.VEHICLES_WITHOUT_KMPL) {
      if (widget.fuelEntryData['countOfvehiclesWithoutKMPL'] == null ||
          widget.fuelEntryData['countOfvehiclesWithoutKMPL'] == 0) {
        return;
      }
      getFuelEntriesTableDataForPendingWork("3", index);
    } 
  }

  getFuelEntriesTableDataForPendingWork(type, index) async {
    var tableData = {
      'compId': companyId,
      'type': type,
      'startDate': DateFormat("dd-MM-yyyy").format(startDate),
      'endDate': DateFormat("dd-MM-yyyy").format(endDate)
    };

    var data = await ApiCall.getDataFromApi(
        URI.FUEL_ENTRY_TABLE_DATA, tableData, URI.LIVE_URI, context);

    if (data != null) {
      if (index == DashBoardConstant.VEHICLES_WITH_FE) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "No. Of Vehicles With Fuel Entries",
                index: DashBoardConstant.VEHICLES_WITH_FE_TABLE)));
      }  else if (index == DashBoardConstant.VEHICLES_WITHOUT_FE) {
        Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) => new FuelEntriesReportData(
              listData: data['fuel'],
              titleText: "No. Of Vehicles Without Fuel Entries",
              index: DashBoardConstant.VEHICLES_WITHOUT_FE_TABLE),
        ));
      } else if (index == DashBoardConstant.VEHICLES_WITHOUT_KMPL) {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) => new FuelEntriesReportData(
                listData: data['fuel'],
                titleText: "No. Of Vehicles Without Expected Mileage (KM/L)",
                index: DashBoardConstant.VEHICLES_WITHOUT_KMPL_TABLE)));
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
                                                  child: 
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
                                                        overflow: TextOverflow.clip,
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
                            top: 25, bottom: 25, left: 16),
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

