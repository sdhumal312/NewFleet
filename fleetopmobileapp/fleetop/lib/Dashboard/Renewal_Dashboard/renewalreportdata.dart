import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shimmer/shimmer.dart';

import '../../appTheme.dart';
import '../dashboardconstant.dart';

class RenewalReminderReportData extends StatefulWidget {
  final List listData;
  final String titleText;
  final int index;
  RenewalReminderReportData(
      {Key key, this.listData, this.titleText, this.index})
      : super(key: key);

  @override
  _RenewalReminderReportDataState createState() =>
      _RenewalReminderReportDataState();
}

class _RenewalReminderReportDataState extends State<RenewalReminderReportData> {
  List myList;
  ScrollController _scrollController = ScrollController();
  int currentmax = 20;
  int time = 2000;
  var NOT_APPROVED = 1;
  var APPROVED = 2;
  var OPEN = 3;
  var IN_PROGRESS = 4;
  var CANCELLED = 5;
  var REJECTED = 6;
  @override
  void initState() {
    myList = List.generate(
        widget.listData.length >= 20 ? 20 : widget.listData.length,
        (i) => widget.listData[i]);
    if (widget.listData.length > 19) {
      _scrollController.addListener(() {
        if (_scrollController.position.pixels ==
            _scrollController.position.maxScrollExtent) {
          getMoreData();
        } else {
          print("no more data !");
        }
      });
    }
    super.initState();
  }

  getMoreData() {
    for (int i = currentmax; i < currentmax + 20; i++) {
      myList.add(widget.listData[i]);
    }
    currentmax = currentmax + 20;
    setState(() {});
  }

  showData(list) {
    Alert(
        context: context,
        title: "RR - " + list["renewal_R_Number"].toString(),
        content: Column(
          children: <Widget>[
            SizedBox(
              height: 15,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Vehicle : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_registration"],
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Renewal From : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["renewal_from"],
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Renewal To : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["renewal_to"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Renewal Type : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["renewal_type"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Amount : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["renewal_Amount"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Status : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["renewal_status"],
                style: new TextStyle(
                  fontSize: 15,
                  color: getColor(list["renewal_staus_id"]),
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
          ],
        ),
        buttons: [
          DialogButton(
            onPressed: () => Navigator.pop(context),
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
          )
        ]).show();
  }

  getColor(id) {
    if (id == NOT_APPROVED) return Colors.pink;

    if (id == APPROVED) return Colors.green;

    if (id == OPEN) return Colors.cyan;

    if (id == IN_PROGRESS) return Colors.yellow;

    if (id == CANCELLED) return Colors.brown;

    if (id == REJECTED) return Colors.red;
  }

  showVehiclesWithRR(list) {
    Alert(
        context: context,
        title: list["vehicle_registration"].toString(),
        content: Column(
          children: <Widget>[
            SizedBox(
              height: 15,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Vehicle : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_registration"],
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Count : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["countOfSROnEachVehicle"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            
          ],
        ),
        buttons: [
          DialogButton(
            onPressed: () => Navigator.pop(context),
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
          )
        ]).show();
  }

  showVehiclesWithoutRR(list) {
    Alert(
        context: context,
        title: list["vehicle_registration"].toString(),
        content: Column(
          children: <Widget>[
            SizedBox(
              height: 15,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Vehicle : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_registration"],
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Status : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_Status"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            
          ],
        ),
        buttons: [
          DialogButton(
            onPressed: () => Navigator.pop(context),
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
          )
        ]).show();
  }

  mandatoryVehicles(list) {
    Alert(
        context: context,
        title: list["vehicle_registration"].toString(),
        content: Column(
          children: <Widget>[
            SizedBox(
              height: 15,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Vehicle : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_registration"],
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),

            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Mandatory Count : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["countOfSROnEachVehicle"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),

            SizedBox(
              height: 10,
            ),
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Total Count : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["countOfSROnEachVehicle"].toString(),
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.blueAccent,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            
          ],
        ),
        buttons: [
          DialogButton(
            onPressed: () => Navigator.pop(context),
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
          )
        ]).show();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.cyan,
          iconTheme: IconThemeData(
            color: Colors.black,
          ),
          centerTitle: true,
          title: Text(
            widget.titleText,
            style: new TextStyle(
              fontSize: 20,
              color: AppTheme.darkText,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        backgroundColor: AppTheme.white,
        body: ListView.builder(
          controller: _scrollController,
          itemExtent: 70,
          itemBuilder: (context, i) {
            if (myList.length > 19) {
              if (i == myList.length) {
                return CupertinoActivityIndicator();
              }
            }

            if(widget.index == DashBoardConstant.RR_DUE_SOON_AND_OVERDUE_TABLE){
              return GestureDetector(
                onTap: () {
                  showData(myList[i]);
                },
                child: Container(
                  height: 30,
                  child: Padding(
                    padding: const EdgeInsets.only(
                        left: 8, right: 8, top: 10, bottom: 4),
                    child: Column(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.center,
                          child: Shimmer.fromColors(
                              highlightColor: Colors.black,
                              baseColor: Colors.lightBlueAccent,
                              period: Duration(milliseconds: time),
                              child: Text(
                                "RR - " +
                                    myList[i]["renewal_R_Number"].toString(),
                                style: new TextStyle(
                                  fontSize: 17,
                                  color: Colors.blue,
                                  fontWeight: FontWeight.w700,
                                ),
                              )),
                        ),
                        SizedBox(
                          height: 8,
                        ),
                        Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Text(
                                myList[i]["vehicle_registration"],
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: AppTheme.nearlyBlack,
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                              Text(
                                myList[i]["renewal_status"],
                                style: new TextStyle(
                                  fontSize: 15,
                                  color:
                                      getColor(myList[i]["renewal_staus_id"]),
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                            ]),
                        SizedBox(
                          height: 8,
                        ),
                      ],
                    ),
                  ),
                  decoration: new BoxDecoration(
                    boxShadow: [
                      new BoxShadow(
                        color: Colors.grey,
                        blurRadius: 100.0,
                      ),
                    ],
                    gradient: LinearGradient(
                      colors: [Colors.white54, Colors.white],
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                    ),
                    borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(10.0),
                      bottomLeft: Radius.circular(10.0),
                      topLeft: Radius.circular(10.0),
                      topRight: Radius.circular(10.0),
                    ),
                  ),
                ));
            }

            if(widget.index == DashBoardConstant.VEHICLES_WITH_RR_TABLE){
              return GestureDetector(
                onTap: () {
                  showVehiclesWithRR(myList[i]);
                },
                child: Container(
                  height: 30,
                  child: Padding(
                    padding: const EdgeInsets.only(
                        left: 8, right: 8, top: 10, bottom: 4),
                    child: Column(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.center,
                          child: Shimmer.fromColors(
                              highlightColor: Colors.black,
                              baseColor: Colors.lightBlueAccent,
                              period: Duration(milliseconds: time),
                              child: Text(
                                    myList[i]["vehicle_registration"].toString(),
                                style: new TextStyle(
                                  fontSize: 17,
                                  color: Colors.blue,
                                  fontWeight: FontWeight.w700,
                                ),
                              )),
                        ),
                      ],
                    ),
                  ),
                  decoration: new BoxDecoration(
                    boxShadow: [
                      new BoxShadow(
                        color: Colors.grey,
                        blurRadius: 100.0,
                      ),
                    ],
                    gradient: LinearGradient(
                      colors: [Colors.white54, Colors.white],
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                    ),
                    borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(10.0),
                      bottomLeft: Radius.circular(10.0),
                      topLeft: Radius.circular(10.0),
                      topRight: Radius.circular(10.0),
                    ),
                  ),
                ));
            }

            if(widget.index == DashBoardConstant.VEHICLES_WITHOUT_RR_TABLE){
              return GestureDetector(
                onTap: () {
                  showVehiclesWithoutRR(myList[i]);
                },
                child: Container(
                  height: 30,
                  child: Padding(
                    padding: const EdgeInsets.only(
                        left: 8, right: 8, top: 10, bottom: 4),
                    child: Column(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.center,
                          child: Shimmer.fromColors(
                              highlightColor: Colors.black,
                              baseColor: Colors.lightBlueAccent,
                              period: Duration(milliseconds: time),
                              child: Text(
                                    myList[i]["vehicle_registration"].toString(),
                                style: new TextStyle(
                                  fontSize: 17,
                                  color: Colors.blue,
                                  fontWeight: FontWeight.w700,
                                ),
                              )),
                        ),
                      ],
                    ),
                  ),
                  decoration: new BoxDecoration(
                    boxShadow: [
                      new BoxShadow(
                        color: Colors.grey,
                        blurRadius: 100.0,
                      ),
                    ],
                    gradient: LinearGradient(
                      colors: [Colors.white54, Colors.white],
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                    ),
                    borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(10.0),
                      bottomLeft: Radius.circular(10.0),
                      topLeft: Radius.circular(10.0),
                      topRight: Radius.circular(10.0),
                    ),
                  ),
                ));
            }

            if(widget.index == DashBoardConstant.RR_MANDATORY_TABLE){
              return GestureDetector(
                onTap: () {
                  mandatoryVehicles(myList[i]);
                },
                child: Container(
                  height: 30,
                  child: Padding(
                    padding: const EdgeInsets.only(
                        left: 8, right: 8, top: 10, bottom: 4),
                    child: Column(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.center,
                          child: Shimmer.fromColors(
                              highlightColor: Colors.black,
                              baseColor: Colors.lightBlueAccent,
                              period: Duration(milliseconds: time),
                              child: Text(
                                    myList[i]["vehicle_registration"].toString(),
                                style: new TextStyle(
                                  fontSize: 17,
                                  color: Colors.blue,
                                  fontWeight: FontWeight.w700,
                                ),
                              )),
                        ),
                      ],
                    ),
                  ),
                  decoration: new BoxDecoration(
                    boxShadow: [
                      new BoxShadow(
                        color: Colors.grey,
                        blurRadius: 100.0,
                      ),
                    ],
                    gradient: LinearGradient(
                      colors: [Colors.white54, Colors.white],
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                    ),
                    borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(10.0),
                      bottomLeft: Radius.circular(10.0),
                      topLeft: Radius.circular(10.0),
                      topRight: Radius.circular(10.0),
                    ),
                  ),
                ));
            }
            
          },
          itemCount: myList.length > 19 ? myList.length + 1 : myList.length,
        ));
  }
}
