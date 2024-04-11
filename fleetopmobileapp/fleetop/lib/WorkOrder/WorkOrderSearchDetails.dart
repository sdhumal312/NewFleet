import 'package:fleetop/Driver/GradientCard.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/components/list_tile/gf_list_tile.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../appTheme.dart';
import 'WorkOrderUtility.dart';

class WorkOrderSearchDetails extends StatefulWidget {
  final List workOrderList;
  WorkOrderSearchDetails({
    Key key,
    this.workOrderList,
  }) : super(key: key);
  @override
  _WorkOrderSearchDetailsState createState() => _WorkOrderSearchDetailsState();
}

class _WorkOrderSearchDetailsState extends State<WorkOrderSearchDetails> {
  String companyId;
  String userId;
  @override
  void initState() {
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: GradientAppBar(
          centerTitle: true,
          gradient: LinearGradient(colors: [
            Color(0xff7a6fe9),
            AppTheme.col2,
          ]),
          iconTheme: new IconThemeData(color: Colors.white),
          title: const Text(
            "Work Order Details",
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
    if (widget.workOrderList != null && widget.workOrderList.isNotEmpty) {
      for (int i = 0; i < widget.workOrderList.length; i++) {
        list.add(WorkOrderUtility.renderMainMenu(
            "WO-No : " +
                widget.workOrderList[i]["workorders_Number"].toString(),
            setElementInList(widget.workOrderList[i])));
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

  List<Widget> setElementInList(Map fundData) {
    List<Widget> wlist = new List();
    wlist.add(
      tapableCard(
          "WO-No :", fundData["workorders_Number"].toString(), 1, fundData),
    );
    wlist.add(
      tapableCard(
          "Start Date:", fundData["start_date"].toString(), 0, fundData),
    );
    wlist.add(
      tapableCard("Due Date:", fundData["due_date"].toString(), 0, fundData),
    );
    wlist.add(tapableCard(
        "Vehicle :", fundData["vehicle_registration"].toString(), 0, fundData));
    wlist.add(tapableCard(
        "Assigned To:", fundData["assignee"].toString(), 0, fundData));
    wlist.add(
      tapableCard(
          "Location:", fundData["workorders_location"].toString(), 0, fundData),
    );
    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
  }

  closeScreenAndRefreshData(var workOrderId) {
    var jsonObject = {};
    jsonObject["isFromMob"] = 'true';
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    WorkOrderUtility.getWorkOrderDetailsByWorkOrderIdData(jsonObject, context);
  }

  Widget tapableCard(String title, String val, int id, Map data) {
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
      margin: EdgeInsets.only(left: 10, top: 2, right: 10),
      child: ListTile(
        // isThreeLine: true,
        onTap: () {},
        leading: Icon(
          Icons.info_outline,
          color: Colors.white,
        ),
        subtitle: Text(
          val,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
        trailing: id == 1
            ? ButtonTheme(
                buttonColor: Colors.white,
                minWidth: 100.0,
                height: 100.0,
                child: RaisedButton(
                  shape: new RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(25.0),
                  ),
                  onPressed: () {
                    closeScreenAndRefreshData(data["workorders_id"]);
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

  Widget showBigData(String key, String val) {
    return GFListTile(
      margin: EdgeInsets.only(left: 7, top: 5, right: 5),
      color: Color(0xff2f317c),
      avatar: IconButton(
        icon: Icon(Icons.info_outline),
        color: Colors.white,
        onPressed: () {},
      ),
      subTitle: Text(
        val != null ? val : "",
        style: TextStyle(
            fontFamily: "WorkSansSemiBold",
            fontSize: 17.0,
            fontWeight: FontWeight.w900,
            color: Colors.white),
      ),
      title: Text(
        key != null ? key : "",
        style: TextStyle(
            fontFamily: "WorkSansSemiBold",
            fontSize: 14.0,
            fontWeight: FontWeight.w900,
            color: Colors.white),
      ),
    );
  }
}
