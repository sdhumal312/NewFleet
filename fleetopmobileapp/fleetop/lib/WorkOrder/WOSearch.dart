import 'package:fleetop/apicall.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:nice_button/nice_button.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fleetop/appTheme.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'WorkOrderSearchDetails.dart';

class WOSearch extends StatefulWidget {
  @override
  _WOSearchState createState() => _WOSearchState();
}

class _WOSearchState extends State<WOSearch> {
  double _height;
  double _width;
  Size size;
  int typeSelected = 0;
  List searchOption = new List();
  TextEditingController numController = new TextEditingController();

  String companyId;
  String userId;
  String email;
  List workOrderList = new List();
  @override
  void initState() {
    super.initState();
    getSessionData();
    makeSearchDropDown();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: GradientAppBar(
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back_ios,
            color: Colors.white,
            size: 25,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        centerTitle: true,
        gradient: LinearGradient(colors: [
          AppTheme.col1,
          AppTheme.col2,
        ]),
        iconTheme: new IconThemeData(color: Colors.white),
        title: const Text(
          "Search",
          style: TextStyle(
              color: Colors.white,
              fontSize: 20.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
      ),
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              showDropdwon(),
              Container(
                margin: EdgeInsets.only(top: 10, left: 5, right: 5),
                width: _width,
                height: 70,
                child: TextFormField(
                  controller: numController,
                  style: TextStyle(
                      fontFamily: "WorkSansSemiBold",
                      fontSize: 16.0,
                      color: Colors.black),
                  decoration: InputDecoration(
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(10.0)),
                    ),
                    prefixIcon: Icon(
                      Icons.format_list_numbered,
                      size: 22.0,
                      color: Colors.black,
                    ),
                    labelText: 'Enter Number',
                    hintText: 'Enter Number',
                    hintStyle: TextStyle(
                        color: Colors.black,
                        fontFamily: "WorkSansSemiBold",
                        fontSize: 17.0),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  margin: EdgeInsets.only(
                    top: 15,
                  ),
                  child: NiceButton(
                    radius: 40,
                    text: "Search",
                    icon: Icons.search,
                    gradientColors: [
                      AppTheme.col1,
                      AppTheme.col2,
                    ],
                    onPressed: () {
                      searchNumber();
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget showDropdwon() {
    return searchOption.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              margin: EdgeInsets.only(top: 10, left: 5, right: 5),
              width: _width,
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 0.5,
                    color: Colors.white70,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            hint: Text("Please Select Search Type"),
                            value: typeSelected != 0
                                ? typeSelected.toString()
                                : null,
                            isExpanded: true,
                            onChanged: (String newValue) {
                              FocusScope.of(context)
                                  .requestFocus(new FocusNode());
                              setState(() {
                                typeSelected = int.parse(newValue);
                              });
                              //onTypeValueChange(typeSelected);
                            },
                            items: searchOption.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['label'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  void makeSearchDropDown() {
    var objWO = {"id": 1, "label": 'WORK ORDER-VEHICLE SEARCH'};
    searchOption.add(objWO);
    setState(() {
      typeSelected = 1;
    });
  }

  searchNumber() {
    if (typeSelected == 0) {
      FlutterAlert.onInfoAlert(context, "Please Select Search Type !", "Info");
      return false;
    }
    if (numController.text == '' || numController.text.trim().length == 0) {
      FlutterAlert.onInfoAlert(context, "Please Enter Number !", "Info");
      return false;
    }
    if (typeSelected == 1) {
      getWorkOrderSearchDetails();
    }
  }

  getWorkOrderSearchDetails() async {
    var woSerachData = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'searchTerm': numController.text,
    };
    var response = await ApiCall.getDataFromApi(
        URI.SEARCH_WORK_ORDER, woSerachData, URI.LIVE_URI, context);
    print("response");

    if (response != null && response["WorkOrder"] != null) {
      setState(() {
        workOrderList = response["WorkOrder"];
      });
      Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => WorkOrderSearchDetails(
                  workOrderList: workOrderList,
                )),
      );
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
    print(response["WorkOrder"]);
  }
}
