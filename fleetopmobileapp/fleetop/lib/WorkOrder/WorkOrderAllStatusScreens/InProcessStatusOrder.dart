import 'package:fleetop/Driver/GradientCard.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/components/list_tile/gf_list_tile.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../apicall.dart';
import '../../appTheme.dart';
import '../../fleetopuriconstant.dart';
import '../../flutteralert.dart';
import '../WorkOrderUtility.dart';

class InProcessStatusOrder extends StatefulWidget {
  @override
  _InProcessStatusOrderState createState() => _InProcessStatusOrderState();
}

class _InProcessStatusOrderState extends State<InProcessStatusOrder> {
  String companyId;
  String userId;
  String email;
  List inProcessDataList = new List();
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

  onPressButton() async {
    data = {
      'isFromMob': 'true',
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'status': '2',
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
          inProcessDataList = response["WorkOrder"];
        });
      } else {
        setState(() {
          inProcessDataList.clear();
        });
        FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
      }
    } else {
      setState(() {
        inProcessDataList.clear();
      });
      FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
    }
  }

  @override
  Widget build(BuildContext context) {
    return WorkOrderUtility.renderMainBodyNew(WorkOrderUtility.IN_PROCESS,
        context, inProcessDataList, data, onPressButton, workOrderCount);
  }
}
