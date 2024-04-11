import 'package:fleetop/SearchScreen/SearchScreen.dart';
import 'package:fleetop/Utility/MyCard.dart';
import 'package:fleetop/apicall.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:kf_drawer/kf_drawer.dart';

import '../fleetopuriconstant.dart';

class WorkOrderProcess extends KFDrawerContent {
  final Map workProcessData;
  WorkOrderProcess({Key key, this.workProcessData});
  @override
  _WorkOrderProcessState createState() => _WorkOrderProcessState();
}

class _WorkOrderProcessState extends State<WorkOrderProcess> {
  double _height;
  double _width;
  Size size;
  String companyId;
  String userId;
  String email;
  String workOrderId = "";
  Map woData = new Map();
  Map config = new Map();
  @override
  void initState() {
    super.initState();
    getSessionData();
  }

  getSessionData() async {
    print(widget.workProcessData);
    if (widget.workProcessData != null && widget.workProcessData.isNotEmpty) {
      setState(() {
        woData = widget.workProcessData["WorkOrder"];
        config = widget.workProcessData["configuration"];
      });
    }
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
  }

  approveWorkOrder() async {
    var jsonObject = {};
    jsonObject["workOrderId"] = workOrderId;
    jsonObject["approvalStatusId"] = "1";
    var response = await ApiCall.getDataFromApi(
        URI.APPROVE_WO, jsonObject, URI.LIVE_URI, context);
  }

  addTaskDetails() async {
    var jsonObject = {};
    jsonObject["workOrderId"] = workOrderId;
    jsonObject["approvalStatusId"] = "1";
    var response = await ApiCall.getDataFromApi(
        URI.SAVE_NEW_TASK_DETAILS, jsonObject, URI.LIVE_URI, context);
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
        body: Container(
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(35.0),
          topRight: const Radius.circular(35.0),
        ),
        gradient: new LinearGradient(
            colors: [
              Color(0xFF4A00E0),
              Color(0xFF4A00E0),
              // Color(0xFF57d0eb), Color(0xFF57d0eb)
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 40),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  IconButton(
                    icon: Icon(
                      Icons.menu,
                      color: Colors.white,
                      size: 25,
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                  Container(
                    margin: EdgeInsets.only(right: _width / 2),
                    child: Text(
                      'Work Order',
                      style: GoogleFonts.openSans(
                          textStyle: TextStyle(
                              color: Colors.white,
                              fontSize: 20,
                              fontWeight: FontWeight.w600)),
                    ),
                  ),
                  IconButton(
                    icon: Icon(
                      Icons.search,
                      color: Colors.white,
                      size: 25,
                    ),
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => SearchScreen(
                                  isWOSearch: true,
                                )),
                      );
                    },
                  ),
                ],
              ),
            ),
          ),
          upperBody()
        ],
      ),
    ));
  }

  Widget upperBody() {
    return Scrollbar(
      child: Container(
        child: createWorkOrderBody(),
      ),
    );
  }

  Widget createWorkOrderBody() {
    return Container(
      margin: EdgeInsets.only(top: 130, left: 2, right: 2, bottom: 0),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height >= 775.0
          ? MediaQuery.of(context).size.height
          : 775.0,
      decoration: new BoxDecoration(
        borderRadius: new BorderRadius.only(
          topLeft: const Radius.circular(45.0),
          topRight: const Radius.circular(45.0),
        ),
        gradient: new LinearGradient(
            colors: [Colors.white, Colors.white],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: ListView(
        scrollDirection: Axis.vertical,
        shrinkWrap: true,
        children: [
          SizedBox(
            height: 40,
          ),
          renderText(),
          SizedBox(
            height: 15,
          ),
          setData("Vehicle No:", woData["vehicle_registration"], false),
          setData("Assignee:", woData["assignee"], false),
          setData("Start date: :", woData["start_date"], false),
          setData("Due date:", woData["due_date"], false),
          setData("Indent | PO No:", woData["indentno"], false),
          Visibility(
              visible: config["TallyCompanyMasterInWO"],
              child: setData(
                  "Tally Company Master:", woData["tallyCompanyName"], false)),
          setData("Assignee:", woData["vaibhav"], false),
          setData("Driver Name:", woData["workorders_drivername"], false),
          setData("Work Location:", woData["workorders_location"], false),
          setData("Odometer :", woData["vehicle_Odometer"].toString(), false),
          setData("GPS Work Location :", woData["vaibhav"], false),
          setData("Initial_Note :", woData["gpsWorkLocation"], false),
        ],
      ),
    );
  }

  Widget renderText() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            margin: EdgeInsets.only(left: 40),
            child: Text(
              'Work Order : ' + woData["workorders_Number"].toString(),
              style: GoogleFonts.openSans(
                  textStyle: TextStyle(
                      color: Colors.black,
                      fontSize: 20,
                      fontWeight: FontWeight.w600)),
            ),
          ),
          Card(
            margin: EdgeInsets.only(right: 40),
            color: Colors.yellow,
            child: Text(
              woData["priority"].toString(),
              style: GoogleFonts.openSans(
                  textStyle: TextStyle(
                      color: Colors.black,
                      fontSize: 20,
                      fontWeight: FontWeight.w600)),
            ),
          )
        ],
      ),
    );
  }

  Widget setData(String key, String value, bool changedColor) {
    return MyCard(
        // onTap: () => ,
        valueColorChanged: false,
        colorData: changedColor ? Colors.yellowAccent : Colors.white,
        valueColor: Colors.cyanAccent,
        name: key,
        value: value == null || value.length == 0 ? "--" : value,
        icon: Icons.info_outline,
        cardColorChanged: changedColor);
  }
}
