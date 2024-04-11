import 'package:fleetop/Driver/GradientCard.dart';
import 'package:fleetop/WorkOrder/WorkOrderUtility.dart';
import 'package:flutter/material.dart';
import 'package:getflutter/components/list_tile/gf_list_tile.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../apicall.dart';
import '../../fleetopuriconstant.dart';
import '../../flutteralert.dart';

class OnHoldStatusOrder extends StatefulWidget {
  @override
  _OnHoldStatusOrderState createState() => _OnHoldStatusOrderState();
}

class _OnHoldStatusOrderState extends State<OnHoldStatusOrder> {
  String companyId;
  String userId;
  String email;
  double _height;
  double _width;
  Size size;
  List onHoldDataList = new List();
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
      'status': '3',
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
          onHoldDataList = response["WorkOrder"];
        });
      } else {
        setState(() {
          onHoldDataList.clear();
        });
        FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
      }
    } else {
      setState(() {
        onHoldDataList.clear();
      });
      FlutterAlert.onErrorAlert(context, "No Data Found!", "Info");
    }
  }

  @override
  Widget build(BuildContext context) {
    return WorkOrderUtility.renderMainBodyNew(WorkOrderUtility.ON_HOLD, context,
        onHoldDataList, data, onPressButton, workOrderCount);
  }
}
