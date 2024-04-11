import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shimmer/shimmer.dart';

import '../../appTheme.dart';


class TripSheetReportData extends StatefulWidget {
  final List listData;
  final String titleText;
  final int index;
  TripSheetReportData({Key key, this.listData , this.titleText , this.index}) : super(key: key);

  @override
  _TripSheetReportDataState createState() => _TripSheetReportDataState();
}

class _TripSheetReportDataState extends State<TripSheetReportData> {
  List myList;
  ScrollController _scrollController = ScrollController();
  int currentmax = 20;
  int time = 2000;
  @override
  void initState() {
    myList = List.generate(widget.listData.length >= 20 ? 20 : widget.listData.length, (i) => widget.listData[i]);
    if(widget.listData.length > 19){
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

  getColor(status) {
    if (status == "CLOSED") {
      return Colors.cyan;
    } else if (status == "DISPATCHED") {
      return Colors.deepPurpleAccent;
    } else {
      return Colors.redAccent;
    }
  }

  showData(list) {
    Alert(
        context: context,
        title: "TS - " + list["tripSheetNumber"].toString(),
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
                  fontWeight: FontWeight.bold,
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
                "Route : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["routeName"],
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
                "Open Date : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["tripOpenDate"],
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
                "Close Date : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["closetripDate"],
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
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Status : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["tripSheetCurrentStatus"],
                style: new TextStyle(
                  fontSize: 15,
                  color: getColor(list["tripSheetCurrentStatus"]),
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            SizedBox(
              height: widget.index == DashBoardConstant.TOTAL_RUN_COUNT ? 10 : 0,
            ),
            Visibility(
              visible: widget.index == DashBoardConstant.TOTAL_RUN_COUNT,
              child : Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Open Odometer : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["tripOpeningKM"].toString() + " KM",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.amber,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            ),
                        SizedBox(
              height: widget.index == DashBoardConstant.TOTAL_RUN_COUNT ? 10 : 0,
            ),
            Visibility(
              visible: widget.index == DashBoardConstant.TOTAL_RUN_COUNT,
              child : Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Closed Odometer : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["tripClosingKM"].toString() + " KM",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.amber,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            ),
                        SizedBox(
              height: widget.index == DashBoardConstant.TOTAL_RUN_COUNT ? 10 : 0,
            ),
            Visibility(
              visible: widget.index == DashBoardConstant.TOTAL_RUN_COUNT,
              child : Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Text(
                "Trip Usage KM : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["tripUsageKM"].toString() + " KM",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.amber,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ]),
            )
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
            if(myList.length > 19){
              if (i == myList.length) {
              return CupertinoActivityIndicator();
            }
            }
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
                                "TS - " +
                                    myList[i]["tripSheetNumber"].toString(),
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
                                myList[i]["tripSheetCurrentStatus"],
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: getColor(
                                      myList[i]["tripSheetCurrentStatus"]),
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
          },
          itemCount: myList.length > 19 ? myList.length + 1 : myList.length ,
        ));
  }
}
