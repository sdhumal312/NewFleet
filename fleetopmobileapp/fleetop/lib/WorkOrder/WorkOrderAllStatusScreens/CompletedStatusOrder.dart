import 'package:fleetop/Driver/GradientCard.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/components/list_tile/gf_list_tile.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../apicall.dart';
import '../../fleetopuriconstant.dart';
import '../../flutteralert.dart';

class CompletedStatusOrder extends StatefulWidget {
  @override
  _CompletedStatusOrderState createState() => _CompletedStatusOrderState();
}

class _CompletedStatusOrderState extends State<CompletedStatusOrder> {
  String companyId;
  String userId;
  String email;
  List completedList = new List();
  double _height;
  double _width;
  Size size;
  Map data = new Map();
  String workOrderCount = "0";
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
    onPressButton();
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;
    _height = MediaQuery.of(context).size.height;
    return Container(
      child: WorkOrderUtility.renderMainBodyNew(WorkOrderUtility.COMPLETED,
          context, completedList, data, onPressButton, workOrderCount),
    );
  }

  onPressButton() async {
    print("vpppppppp");
    data = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'status': '4',
      'pagenumber': WorkOrderUtility.pageNumber.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_AALL_WORK_ORDER_DETAILS, data, URI.LIVE_URI, context);
    setState(() {
      workOrderCount = WorkOrderUtility.getWOCount(response, "WorkOrderCount");
    });
    if (response != null && response["WorkOrder"] != null) {
      if (response["WorkOrder"].length > 0) {
        setState(() {
          completedList = response["WorkOrder"];
        });
      } else {
        setState(() {
          completedList.clear();
        });
        FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
      }
    } else {
      setState(() {
        completedList.clear();
      });
      FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
    }
  }
}
