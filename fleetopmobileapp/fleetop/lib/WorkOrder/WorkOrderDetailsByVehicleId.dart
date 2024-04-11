import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class WorkOrderDetailsByVehicleId extends StatefulWidget {
  final List workorderList;
  WorkOrderDetailsByVehicleId({
    Key key,
    this.workorderList,
  }) : super(key: key);
  @override
  _WorkOrderDetailsByVehicleIdState createState() =>
      _WorkOrderDetailsByVehicleIdState();
}

class _WorkOrderDetailsByVehicleIdState
    extends State<WorkOrderDetailsByVehicleId> {
  String companyId;
  String userId;
  String email;
  double _width;
  int currentIndex = 0;
  double _height = 0.0;
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

  Widget build(BuildContext context) {
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
              Color(0xff7a6fe9),
              Color(0xff7a6fe9),
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      child: Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 30),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  IconButton(
                    icon: Icon(
                      Icons.arrow_back_ios,
                      color: Colors.white,
                      size: 25,
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                  Container(
                    margin: EdgeInsets.only(right: 120),
                    child: Text(
                      "vehicle No : " +
                          "\n" +
                          widget.workorderList[0]["vehicle_registration"],
                      style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                        fontSize: 24,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          dataBody(context, widget.workorderList),
          // Visibility(
          //   // visible: workorderList != null && workorderList.isNotEmpty,
          //   child: Container(
          //     margin: EdgeInsets.only(
          //       top: _height - 140,
          //     ),
          //     child: Row(
          //       mainAxisAlignment: MainAxisAlignment.spaceAround,
          //       children: <Widget>[
          //         Container(
          //           margin: EdgeInsets.only(left: _width / 2 - 100, bottom: 10),
          //           child: RaisedButton(
          //             elevation: 5.0,
          //             onPressed: () {},
          //             shape: RoundedRectangleBorder(
          //               borderRadius: BorderRadius.circular(30.0),
          //             ),
          //             color: Colors.greenAccent,
          //             child: IconButton(
          //               onPressed: () {},
          //               icon: Icon(
          //                 Icons.arrow_back_ios,
          //                 size: 30,
          //                 color: Colors.white,
          //               ),
          //             ),
          //           ),
          //         ),
          //         Container(
          //           margin:
          //               EdgeInsets.only(right: _width / 2 - 100, bottom: 10),
          //           child: RaisedButton(
          //             elevation: 5.0,
          //             onPressed: () {},
          //             shape: RoundedRectangleBorder(
          //               borderRadius: BorderRadius.circular(30.0),
          //             ),
          //             color: Colors.greenAccent,
          //             child: IconButton(
          //               onPressed: () {},
          //               icon: Icon(
          //                 Icons.arrow_forward_ios,
          //                 size: 30,
          //                 color: Colors.white,
          //               ),
          //             ),
          //           ),
          //         ),
          //       ],
          //     ),
          //   ),
          // )
        ],
      ),
    ));
  }

  Widget dataBody(BuildContext context, List workorderList) {
    return Container(
      margin: EdgeInsets.only(top: 120, left: 2, right: 2, bottom: 0),
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
        children: <Widget>[
          Column(
            children: renderData(workorderList, context),
          ),
        ],
      ),
    );
  }

  static Widget renderMainMenu(String title, List<Widget> wdList) {
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

  List<Widget> renderData(List dataList, BuildContext context) {
    List<Widget> list = new List();
    if (dataList != null && dataList.isNotEmpty) {
      for (int i = 0; i < dataList.length; i++) {
        list.add(renderMainMenu(
            "WO-No: " + dataList[i]["workorders_Number"].toString(),
            setElementInList(dataList[i], context)));
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  List<Widget> setElementInList(Map fundData, BuildContext context) {
    List<Widget> wlist = new List();
    wlist.add(renderCard("WO-No", fundData["workorders_Number"].toString(), 5,
        fundData, context));
    wlist.add(renderCard(
        "Start Date	", fundData["start_date"], 0, fundData, context));
    wlist.add(
        renderCard("Due Date", fundData["due_date"], 0, fundData, context));
    wlist.add(
        renderCard("Assigned To", fundData["assignee"], 0, fundData, context));
    wlist.add(renderCard(
        "Location", fundData["workorders_location"], 0, fundData, context));
    wlist.add(renderCard("Status", fundData["workorders_status"],
        fundData["workorders_statusId"], fundData, context));

    wlist.add(renderCard("Total Cost",
        fundData["totalworkorder_cost"].toString(), 0, fundData, context));

    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
  }

  static Color getColor(int status) {
    switch (status) {
      case 1:
        return Colors.blue;
        break;
      case 2:
        return Colors.yellow;
        break;
      case 3:
        return Colors.red;
        break;
      case 4:
        return Colors.green;
        break;
      default:
        return Colors.white;
        break;
    }
  }

  static bool check(int id) {
    if (id == 0 || id == 5) {
      return false;
    } else {
      return true;
    }
  }

  redirectToProcessScreen(Map data, BuildContext context) {
    Map sessionData = new Map();
    sessionData['pagenumber'] = '';
    sessionData['status'] = '';
    sessionData["isFromMob"] = 'true';
    sessionData["companyId"] = companyId.toString();
    sessionData["userId"] = userId.toString();
    WorkOrderUtility.getWorkOrderDetailsByWorkOrderId(
        data, sessionData, context);
  }

  Widget renderCard(
      String title, String val, int id, Map data, BuildContext context) {
    Color textColor = getColor(id);
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
              Color(0xff7a6fe9),
              Color(0xff050859),
            ],
            begin: const FractionalOffset(0.0, 0.0),
            end: const FractionalOffset(1.0, 1.0),
            stops: [0.0, 1.0],
            tileMode: TileMode.clamp),
      ),
      margin: EdgeInsets.only(left: 10, top: 2, right: 10),
      child: ListTile(
        // isThreeLine: true,
        onTap: () {},
        leading: Icon(
          Icons.info_outline,
          color: Colors.white,
        ),
        subtitle: check(id) == true
            ? Card(
                color: textColor,
                child: Text(
                  val,
                  style: TextStyle(
                      fontSize: 22.0,
                      fontWeight: FontWeight.w500,
                      fontFamily: "WorkSansBold"),
                ),
              )
            : Text(
                val,
                style: TextStyle(
                    color: textColor,
                    fontSize: 17.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold"),
              ),
        trailing: id == 5
            ? ButtonTheme(
                buttonColor: Colors.white,
                minWidth: 100.0,
                height: 100.0,
                child: RaisedButton(
                  shape: new RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(25.0),
                  ),
                  onPressed: () {
                    redirectToProcessScreen(data, context);
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
