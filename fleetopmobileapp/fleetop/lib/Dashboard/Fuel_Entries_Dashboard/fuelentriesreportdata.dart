import 'package:fleetop/Dashboard/dashboardconstant.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shimmer/shimmer.dart';

import '../../appTheme.dart';


class FuelEntriesReportData extends StatefulWidget {
  final List listData;
  final String titleText;
  final int index;
  FuelEntriesReportData({Key key, this.listData , this.titleText , this.index}) : super(key: key);

  @override
  _FuelEntriesReportDataState createState() => _FuelEntriesReportDataState();
}

class _FuelEntriesReportDataState extends State<FuelEntriesReportData> {
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

  showFuelCreatedData(list) {
    Alert(
        context: context,
        title: "F - " + list["fuel_Number"].toString(),
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
                "Amount : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["fuel_amount"].toString(),
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
                "Price : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["fuel_price"].toString(),
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
                "Litre : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["fuel_liters"].toString(),
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
                "Created Date : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["created"].toString(),
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

  showKMPerLitreData(list){
      Alert(
        context: context,
        title: list["vehicle_registration"],
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
                "Expected KM/L : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_ExpectedMileage"].toString() +" - "+list["vehicle_ExpectedMileage_to"].toString(),
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
                "Current KM/L",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["fuel_kml"].toString(),
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
                "Created Date : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["created"].toString(),
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

  showVehiclesWithFE(list){
    Alert(
        context: context,
        title: list["vehicle_registration"],
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
                list["countOfFEOnEachVehicle"].toString(),
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

  showVehiclesWithoutFE(list){
    Alert(
        context: context,
        title: list["vehicle_registration"],
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

  showVehiclesWithoutKMPL(list){
    Alert(
        context: context,
        title: list["vehicle_registration"],
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
                "Expected Mileage From : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_ExpectedMileage"].toString(),
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
                "Expected Mileage To : ",
                style: new TextStyle(
                  fontSize: 15,
                  color: AppTheme.nearlyBlack,
                  fontWeight: FontWeight.w700,
                ),
              ),
              Text(
                list["vehicle_ExpectedMileage_to"].toString(),
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

            if(widget.index == DashBoardConstant.CREATED_FUEL_ENTRY ||
               widget.index == DashBoardConstant.TOTAL_PRICE ||
               widget.index == DashBoardConstant.TOTAL_LITERS ||
               widget.index == DashBoardConstant.TOTAL_AVERAGE ){
            return GestureDetector(
                onTap: () {
                  showFuelCreatedData(myList[i]);
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
                                "F - " +
                                    myList[i]["fuel_Number"].toString(),
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
                                myList[i]["fuel_amount"].toString(),
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: AppTheme.nearlyBlack,
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
              
              if(widget.index == DashBoardConstant.ABOVE || widget.index == DashBoardConstant.BETWEEN || widget.index == DashBoardConstant.BELOW ){
                return GestureDetector(
                onTap: () {
                  showKMPerLitreData(myList[i]);
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
                                    myList[i]["vehicle_registration"],
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
                                myList[i]["vehicle_ExpectedMileage"].toString() +" - "+myList[i]["vehicle_ExpectedMileage_to"].toString(),
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: AppTheme.nearlyBlack,
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                              Text(
                                myList[i]["fuel_kml"].toString(),
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: AppTheme.nearlyBlack,
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

              if(widget.index == DashBoardConstant.VEHICLES_WITH_FE_TABLE){
                return GestureDetector(
                onTap: () {
                  showVehiclesWithFE(myList[i]);
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
                                    myList[i]["vehicle_registration"],
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

              if(widget.index == DashBoardConstant.VEHICLES_WITHOUT_FE_TABLE){
                return GestureDetector(
                onTap: () {
                  showVehiclesWithoutFE(myList[i]);
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
                                    myList[i]["vehicle_registration"],
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

              if(widget.index == DashBoardConstant.VEHICLES_WITHOUT_KMPL_TABLE){
                return GestureDetector(
                onTap: () {
                  showVehiclesWithoutKMPL(myList[i]);
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
                                    myList[i]["vehicle_registration"],
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
          itemCount: myList.length > 19 ? myList.length + 1 : myList.length ,
        ));
  }
}
