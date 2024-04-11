import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../fleetopuriconstant.dart';
import '../flutteralert.dart';

class SearchFuelEntry extends StatefulWidget {
  SearchFuelEntry({Key key}) : super(key: key);

  @override
  _SearchFuelEntryState createState() => _SearchFuelEntryState();
}

class _SearchFuelEntryState extends State<SearchFuelEntry>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  Size size;
  String companyId;
  String email;
  String userId;

  List<bool> isSelected = [true, false];
  TextEditingController fuelNumber = new TextEditingController();

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  @override
  void initState() {
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
  }

  Future onSearchFuel() async {
    if (fuelNumber.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Enter Fuel Number !", "Error");
      return;
    }
    var fuelData = {'companyId': companyId, 'fuelNumber': fuelNumber.text};
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

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.cyan,
          iconTheme: IconThemeData(
            color: Colors.black,
          ),
          centerTitle: true,
          title: Text(
            "Search Fuel Details",
            style: new TextStyle(
              fontSize: 30,
              color: AppTheme.darkText,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        backgroundColor: AppTheme.white,
        body: Padding(
            padding: new EdgeInsets.all(5.0),
            child: SingleChildScrollView(
                padding: const EdgeInsets.all(8.0),
                child: Center(
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: <Widget>[
                      new Container(
                        child: new Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: <Widget>[
                            new Center(
                                child: new Image.asset(
                              "assets/img/fuel_entry.jpg",
                              width: 350,
                              height: 250,
                            ))
                          ],
                        ),
                      ),
                      SizedBox(height: 30),
                      new Container(
                        child: new Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              new Form(
                                child: new Column(children: <Widget>[
                                  TextField(
                                    keyboardType: TextInputType.number,
                                    maxLines: 1,
                                    controller: fuelNumber,
                                    style: TextStyle(color: Colors.black),
                                    decoration: InputDecoration(
                                        labelText: 'Fuel Number *',
                                        hintText: "Fuel Number",
                                        hintStyle:
                                            TextStyle(color: Colors.black),
                                        border: OutlineInputBorder(
                                            borderRadius:
                                                BorderRadius.circular(5.0)),
                                        icon: Icon(
                                          Icons.local_gas_station,
                                          color: Colors.green,
                                        )),
                                  ),
                                  SizedBox(height: 15),
                                  SizedBox(height: 15),
                                  new Padding(
                                      padding: new EdgeInsets.only(left: 17),
                                      child: Container(
                                        height: 50,
                                        width: 200,
                                        child: DialogButton(
                                          child: Text(
                                            "Search",
                                            style: new TextStyle(
                                              fontSize: 30,
                                              color: AppTheme.darkerText,
                                              fontWeight: FontWeight.w700,
                                            ),
                                          ),
                                          onPressed: () => onSearchFuel(),
                                          gradient: LinearGradient(colors: [
                                            Colors.green,
                                            Colors.lightBlue
                                          ]),
                                        ),
                                      )),
                                ]),
                              ),
                            ]),
                      ),
                    ])))));
  }
}
