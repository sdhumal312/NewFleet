import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Tripsheet/searchTripsheetByVehicle.dart';
import 'package:flutter/material.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../apicall.dart';
import '../appTheme.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';

class FuelSearchByDateAndVehicle extends StatefulWidget {
  final List fuelDataList;
  FuelSearchByDateAndVehicle({
    Key key,
    this.fuelDataList,
  }) : super(key: key);
  @override
  _FuelSearchByDateAndVehicleState createState() =>
      _FuelSearchByDateAndVehicleState();
}

class _FuelSearchByDateAndVehicleState
    extends State<FuelSearchByDateAndVehicle> {
  String companyId;
  String userId;
  String email;
  double _width;
  int currentIndex = 0;

  @override
  void initState() {
    super.initState();
    getSessionData();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
  }

  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        appBar: GradientAppBar(
          centerTitle: true,
          gradient: LinearGradient(colors: [
            Color(0xff7a6fe9),
            AppTheme.col2,
          ]),
          iconTheme: new IconThemeData(color: Colors.white),
          title: const Text(
            "Fuel Details",
            style: TextStyle(
                color: Colors.white,
                fontSize: 20.0,
                fontWeight: FontWeight.bold,
                fontFamily: "WorkSansBold"),
          ),
        ),
        body: Stack(children: <Widget>[
          SingleChildScrollView(
            child: Column(
              children: renderData(),
            ),
          )
        ]));
  }

  List<Widget> renderData() {
    List<Widget> list = new List();
    if (widget.fuelDataList != null && widget.fuelDataList.isNotEmpty) {
      for (int i = 0; i < widget.fuelDataList.length; i++) {
        list.add(renderMainMenu(
            "Fuel No: " + widget.fuelDataList[i]["fuel_Number"].toString(),
            setElementInList(widget.fuelDataList[i])));
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  Widget renderMainMenu(String title, List<Widget> wdList) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
      margin: EdgeInsets.only(left: 7, top: 8, right: 5),
      color: Color(0xff050859),
      child: ExpansionTile(
          trailing: Icon(Icons.keyboard_arrow_down, color: Colors.white),
          backgroundColor: Color(0xff050859),
          title: Text(
            title,
            style: TextStyle(
                fontFamily: "WorkSansSemiBold",
                fontSize: 17.0,
                fontWeight: FontWeight.w600,
                color: Colors.white),
          ),
          leading: Icon(Icons.info, color: Colors.white),
          children: wdList),
    );
  }

  Widget renderStatus() {
    return Container();
  }

  Color getColor(int id) {
    if (id == 1) {
      return Colors.red;
    } else {
      return Colors.green;
    }
  }

  List<Widget> setElementInList(Map fundData) {
    List<Widget> wlist = new List();
    wlist.add(tapableCard(
        "Fuel No:", fundData["fuel_Number"].toString(), 1, fundData));

    wlist.add(
      tapableCard(
          "Vehicle:", fundData["vehicle_registration"].toString(), 0, fundData),
    );
    wlist.add(tapableCard(
        "Ownership:",
        fundData["vehicle_Ownership"].toString() +
            " " +
            fundData["vehicle_group"].toString(),
        0,
        fundData));
    wlist.add(tapableCard(
        "Driver:",
        fundData["driver_name"].toString() +
            " " +
            fundData["driver_empnumber"].toString(),
        0,
        fundData));

    wlist.add(tapableCard(
        "Date /Vendor:",
        fundData["fuel_date"].toString() +
            " " +
            fundData["vendor_name"].toString(),
        0,
        fundData));
    wlist.add(
      tapableCard(
          "Openning:", fundData["fuel_meter_old"].toString(), 0, fundData),
    );
    wlist.add(
      tapableCard("Closing:", fundData["fuel_meter"].toString(), 0, fundData),
    );
    wlist.add(
      tapableCard(
          "Volume:",
          fundData["fuel_liters"].toString() +
              " " +
              fundData["fuel_type"].toString() +
              "DIESEL",
          0,
          fundData),
    );
    wlist.add(
      tapableCard(
          "Amount:",
          fundData["fuel_amount"].toString() +
              fundData["fuel_price"].toString() +
              "liters",
          0,
          fundData),
    );

    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
  }

  Future onSearchFuel(String fuelNumber) async {
    if (fuelNumber == '') {
      FlutterAlert.onErrorAlert(context, "Please Enter Fuel Number !", "Error");
      return;
    }
    var fuelData = {'companyId': companyId, 'fuelNumber': fuelNumber};
    var data = await ApiCall.getDataFromApi(
        URI.GET_FUEL_DETAILS, fuelData, URI.LIVE_URI, context);
    if (data != null) {
      if (data['message'] != null) {
        FlutterAlert.onInfoAlert(context, data['message'].toString(), "Info");
      } else {
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) =>
                new ShowFuelDetails(fueldata: data['fuelDetails'])));
      }
    }
  }

  searchFuelEntryByVehicleId(String vehicleId) async {
    var tripDetails = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'vid': vehicleId,
    };
    var data = await ApiCall.getDataFromApi(
        URI.SEARCH_TRIPSHEET_BY_VEHICLE, tripDetails, URI.LIVE_URI, context);
    if (data != null) {
      if (data['tripSheet'] == null) {
        FlutterAlert.onInfoAlert(context, "Tripsheet Not Found !", "Info");
      } else if (data['tripSheet'] != null) {
        List tripSheetList = new List();
        setState(() {
          tripSheetList = data['tripSheet'];
        });
        Navigator.of(context).push(new MaterialPageRoute(
            builder: (context) =>
                new SearchTripsheetByVehicle(fuelDataList: tripSheetList)));
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Some Problem Occur,Please contact on Support !", "Error");
    }
  }

  Widget tapableCard(String title, String val, int id, Map data) {
    String vid = data["vid"].toString();
    return Container(
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(10.0),
          topRight: const Radius.circular(10.0),
          bottomLeft: const Radius.circular(10.0),
          bottomRight: const Radius.circular(10.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xff050859),
              Color(0xff7a6fe9),
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      margin: EdgeInsets.only(left: 7, top: 2, right: 5),
      child: ListTile(
        // isThreeLine: true,
        onTap: () {
          if (id == 1) {
            onSearchFuel(val);
          }
          if (id == 2) {
            searchFuelEntryByVehicleId(vid);
          }
        },
        leading: Icon(
          Icons.info_outline,
          color: Colors.white,
        ),
        trailing: id == 1 || id == 2
            ? ButtonTheme(
                buttonColor: Colors.white,
                minWidth: 100.0,
                height: 100.0,
                child: RaisedButton(
                  shape: new RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(25.0),
                  ),
                  onPressed: () {
                    if (id == 1) {
                      onSearchFuel(val);
                    }
                    if (id == 2) {
                      searchFuelEntryByVehicleId(vid);
                    }
                  },
                  child: Text(
                    "Click",
                    style: TextStyle(
                        color: Colors.black,
                        fontSize: 14.0,
                        fontWeight: FontWeight.bold,
                        fontFamily: "WorkSansBold"),
                  ),
                ),
              )
            : Text(""),
        subtitle: Text(
          val,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
        title: Text(
          title,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
      ),
    );
  }
}
