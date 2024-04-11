import 'package:fleetop/Utility/payment_methods.dart';
import 'package:fleetop/WorkOrder/EditWorkOrder/EditWorkOrder.dart';
import 'package:fleetop/WorkOrder/WorkOrderStepProcess.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fleetop/appTheme.dart';
import 'WorkOrderDetailsByVehicleId.dart';
import 'package:nice_button/nice_button.dart';

class WorkOrderUtility {
  static String OPEN_WO = "OPEN";
  static String IN_PROCESS = "IN PROCESS";
  static String ON_HOLD = "ON HOLD";
  static String COMPLETED = "COMPLETED";
  static double _width = 0.0;
  static double _height = 0.0;
  static BuildContext buildContextObj;
  static Size size;
  // static String OPEN_WO_ID = "1";
  // static String IN_PROCESS_ID = "2";
  // static String ON_HOLD_ID = "3";
  // static String COMPLETED_ID = "4";
  static int OPEN_WO_ID = 1;
  static int IN_PROCESS_ID = 2;
  static int ON_HOLD_ID = 3;
  static int COMPLETED_ID = 4;
  static String companyId;
  static String userId;
  static String email;
  static int pageNumber = 1;
  static List workorData = new List();
  static String workOrderId = "";
  static String vid = "";
  static List<String> permission = new List();
  static List<Widget> setElementInList(
      Map fundData, Map sessionData, BuildContext context) {
    buildContextObj = context;
    getSessionData();
    List<Widget> wlist = new List();
    wlist.add(renderCard("WO-No", fundData["workorders_Number"].toString(), 1,
        fundData, sessionData, context));
    wlist.add(renderCard("Start Date	", fundData["start_date"], 0, fundData,
        sessionData, context));
    wlist.add(renderCard(
        "Due Date", fundData["due_date"], 0, fundData, sessionData, context));
    wlist.add(renderCard("Vehicle", fundData["vehicle_registration"], 2,
        fundData, sessionData, context));
    wlist.add(renderCard("Assigned To", fundData["assignee"], 0, fundData,
        sessionData, context));
    wlist.add(renderCard("Location", fundData["workorders_location"], 0,
        fundData, sessionData, context));
    wlist.add(renderEditAndDeleteButton(fundData, context));
    if (wlist != null && wlist.isNotEmpty) {
      return wlist;
    } else {
      wlist.add(Container());
    }
  }

  static Widget renderEditAndDeleteButton(Map woData, BuildContext context) {
    return Container(
      margin: EdgeInsets.only(
        top: 10,
        bottom: 10,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Visibility(
            visible: woData["workorders_statusId"] == OPEN_WO_ID,
            child: NiceButton(
              width: _width / 2 - 40,
              fontSize: 15,
              radius: 40,
              text: "Edit",
              icon: Icons.edit,
              gradientColors: [
                Color(0xff7a6fe9),
                AppTheme.col2,
              ],
              onPressed: () {
                workOrderId = woData["workorders_id"].toString();
                getWorkOrderInitialDetails();
              },
            ),
          ),
          NiceButton(
            width: woData["workorders_statusId"] == OPEN_WO_ID
                ? _width / 2 - 60
                : _width - 50,
            fontSize: 15,
            radius: 40,
            text: "Delete",
            icon: Icons.delete,
            gradientColors: [
              Color(0xff7a6fe9),
              AppTheme.col2,
            ],
            onPressed: () {
              workOrderId = woData["workorders_id"].toString();
              vid = woData["vehicle_vid"].toString();
              FlutterAlert.confirmationAlertWithMethod(
                  context,
                  'Are you sure you want to Delete this Workorder  ?',
                  'Delete Workorder',
                  deleteWorkOrder);
            },
          )
        ],
      ),
    );
  }

  static bool checkAllowToEditWorkOrder(Map woData) {
    bool isAuthenticated = false;
    try {
      if (woData["workorders_statusId"] == OPEN_WO_ID) {
        var res = WorkOrderUtility.hasAuthority(permission, "ADD_WORKORDER");
        if (res != null && res) {
          isAuthenticated = true;
        } else {
          isAuthenticated = false;
        }
      } else {
        isAuthenticated = false;
      }
    } catch (e) {
      isAuthenticated = false;
      return isAuthenticated;
    }
  }

  static getWorkOrderInitialDetails() async {
    var jsonObject = {};
    jsonObject["woId"] = workOrderId.toString();
    jsonObject["vid"] = vid.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["isFromMob"] = 'true';
    var data = await ApiCall.getDataFromApi(URI.GET_INITIAL_DETAILS_OF_WO,
        jsonObject, URI.LIVE_URI, buildContextObj);
    Navigator.of(buildContextObj).push(new MaterialPageRoute(
        builder: (context) => new EditWorkOrder(woData: data)));
  }

  static deleteWorkOrder() async {
    var jsonObject = {};
    jsonObject["workOrderId"] = workOrderId.toString();
    jsonObject["vid"] = vid.toString();
    jsonObject["companyId"] = companyId.toString();
    jsonObject["userId"] = userId.toString();
    jsonObject["isFromMob"] = 'true';
    var data = await ApiCall.getDataFromApi(
        URI.DELETE_WO_FROM_MOB, jsonObject, URI.LIVE_URI, buildContextObj);
    if (data != null) {
      if (data["woCannotBeDeleted"] != null) {
        showMessage(
            'info',
            'Cannot Delete Work Order Please Remove Task Details!',
            buildContextObj);
      } else if (data["woNotFound"] != null) {
        showMessage('info', 'WorkOrder not found to delete!', buildContextObj);
      }
      if (data["woDeleted"] != null && data["woDeleted"]) {
        Navigator.pop(buildContextObj);
      }
    }
  }

  static void showMessage(String tit, String descn, BuildContext context) {
    FlutterAlert.onInfoAlert(context, descn, tit);
  }

  static getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    permission = prefs.getStringList("permission");
  }

  static Widget renderCard(String title, String val, int id, Map data,
      Map sessionData, BuildContext context) {
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
                      getWorkOrderDetailsByWorkOrderId(
                          data, sessionData, context);
                    }
                    if (id == 2) {
                      getWorkOrderDetailsByVehicleId(
                          data, sessionData, context);
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

  static getWorkOrderDetailsByWorkOrderId(
      Map wodata, Map sessionData, BuildContext context) async {
    sessionData.remove('pagenumber');
    sessionData.remove('status');
    sessionData["workOrderId"] = wodata["workorders_id"].toString();
    var response = await ApiCall.getDataFromApi(
        URI.SHOW_WORK_ORDER_DETAILS_FOR_PROCESS,
        sessionData,
        URI.LIVE_URI,
        context);
    if (response != null && response["WorkOrder"] != null) {
      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new WorkOrderStepProcess(workProcessData: response)));
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  static getWorkOrderDetailsByWorkOrderIdData(
      Map wodata, BuildContext context) async {
    var response = await ApiCall.getDataFromApi(
        URI.SHOW_WORK_ORDER_DETAILS_FOR_PROCESS, wodata, URI.LIVE_URI, context);
    if (response != null && response["WorkOrder"] != null) {
      Navigator.of(context).pushReplacement(new MaterialPageRoute(
          builder: (context) =>
              new WorkOrderStepProcess(workProcessData: response)));
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
  }

  static getWorkOrderDetailsByVehicleId(
      Map wodata, Map sessionData, BuildContext context) async {
    List workOrderList = new List();
    sessionData.remove('pagenumber');
    sessionData.remove('status');
    sessionData["vehicleId"] = wodata["vehicle_vid"].toString();
    var response = await ApiCall.getDataFromApi(
        URI.GET_WORK_ORDER_DETAILS_BY_VEHICLE_ID,
        sessionData,
        URI.LIVE_URI,
        context);
    if (response != null && response["WorkOrder"] != null) {
      workOrderList = response["WorkOrder"];

      Navigator.of(context).push(new MaterialPageRoute(
          builder: (context) =>
              new WorkOrderDetailsByVehicleId(workorderList: workOrderList)));
    } else {
      FlutterAlert.onInfoAlert(context, "No Records Found !", "Info");
    }
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

  static Widget upperBody(
      BuildContext context, List workorderList, Map data, String woOrderCount) {
    return Scrollbar(
      child: Container(
        child: dataBody(context, workorderList, data, woOrderCount),
      ),
    );
  }

  static List<Widget> renderData(
      List dataList, Map data, BuildContext context, String woOrderCount) {
    List<Widget> list = new List();
    list.add(renderTextData(woOrderCount));
    if (dataList != null && dataList.isNotEmpty) {
      for (int i = 0; i < dataList.length; i++) {
        list.add(renderMainMenu(
            "WO-No: " + dataList[i]["workorders_Number"].toString(),
            setElementInList(dataList[i], data, context)));
      }

      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  static Widget dataBody(
      BuildContext context, List workorderList, Map data, String woOrderCount) {
    return Container(
      margin: EdgeInsets.only(top: 80, left: 2, right: 2, bottom: 0),
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
            children: WorkOrderUtility.renderData(
                workorderList, data, context, woOrderCount),
          ),
        ],
      ),
    );
  }

  static Widget renderTextData(String text) {
    return Container(
      margin: EdgeInsets.only(top: 02, left: 2, right: 2, bottom: 0),
      child: Text(
        "Open Work Orders :" + text,
        style: TextStyle(
            color: Colors.black,
            fontSize: 17.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  static Widget renderMainBodyNew(String title, BuildContext context,
      List workorderList, Map data, VoidCallback method, String woOrderCount) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    _height = MediaQuery.of(context).size.height;
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
                    onPressed: () {},
                  ),
                  Container(
                    margin: EdgeInsets.only(right: _width / 2 - 50),
                    child: Text(
                      title,
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
          WorkOrderUtility.upperBody(
              context, workorderList, data, woOrderCount),
          Visibility(
            // visible: workorderList != null && workorderList.isNotEmpty,
            child: Container(
              margin: EdgeInsets.only(
                top: _height - 140,
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.only(left: _width / 2 - 100, bottom: 10),
                    child: RaisedButton(
                      elevation: 5.0,
                      onPressed: () {
                        if (pageNumber != 1) {
                          pageNumber--;
                          method();
                        }
                      },
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                      color: Colors.greenAccent,
                      child: IconButton(
                        onPressed: () {
                          if (pageNumber != 1) {
                            pageNumber--;
                            method();
                          }
                        },
                        icon: Icon(
                          Icons.arrow_back_ios,
                          size: 30,
                          color: Colors.white,
                        ),
                      ),
                    ),
                  ),
                  Container(
                    margin:
                        EdgeInsets.only(right: _width / 2 - 100, bottom: 10),
                    child: RaisedButton(
                      elevation: 5.0,
                      onPressed: () {
                        pageNumber++;
                        method();
                      },
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                      color: Colors.greenAccent,
                      child: IconButton(
                        onPressed: () {
                          pageNumber++;
                          method();
                        },
                        icon: Icon(
                          Icons.arrow_forward_ios,
                          size: 30,
                          color: Colors.white,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          )
        ],
      ),
    ));
  }

  static Widget renderDataCard(String key, String val) {
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
        trailing: Text(""),

        title: Text(
          key,
          style: TextStyle(
              color: Colors.white,
              fontSize: 17.0,
              fontWeight: FontWeight.bold,
              fontFamily: "WorkSansBold"),
        ),
      ),
    );
  }

  static Widget renderMyData(String key, String val, BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return Container(
      width: _width / 2 - 25,
      margin: EdgeInsets.only(top: 3, left: 1, right: 1),
      child: PaymentMethods(
        name: key,
        amount: val != null ? val : "--",
        icon: Icons.info,
      ),
    );
  }

  static Widget renderMyDataFullWidth(String key, String val) {
    return Container(
      width: _width,
      margin: EdgeInsets.only(top: 3, left: 1, right: 1),
      child: PaymentMethods(
        name: key,
        amount: val != null ? val : "--",
        icon: Icons.info,
      ),
    );
  }

  static bool hasAuthority(List<String> permissionList, String key) {
    try {
      if (permissionList != null && permissionList.isNotEmpty) {
        if (permissionList.contains(key)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }

  static Widget emptyWidget() {
    return Container();
  }

  static Widget renderText(String text) {
    return Center(
      child: Text(
        text + " * ",
        style: TextStyle(
            color: Colors.red,
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
      ),
    );
  }

  static String getWOCount(Map response, String key) {
    try {
      if (response == null || response.isEmpty || !response.containsKey(key)) {
        return "0";
      } else {
        return response[key].toString();
      }
    } catch (e) {
      print(e);
      return "0";
    }
  }
}
