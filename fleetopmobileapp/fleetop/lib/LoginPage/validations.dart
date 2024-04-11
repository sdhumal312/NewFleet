import 'dart:async';
import 'package:fleetop/fleetopuriconstant.dart';
import 'package:fleetop/apicall.dart';
import 'package:flutter/cupertino.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../flutteralert.dart';

class UserData {
  String companyCode;
  String userName;
  String password;
  

  UserData({this.companyCode, this.userName, this.password});
}

class UserAuth {
  List permission = new List();
  //To verify new User
  Future<Map> verifyUser(UserData userData, BuildContext context) async {
    var resultData;
    var data = {
      'email': userData.userName,
      'companyCode': userData.companyCode,
      'password': userData.password
    };
    resultData =
        await ApiCall.loginApi(URI.VALIDATE_USER_URI, data, URI.LIVE_URI,context,userData.companyCode);

    print(resultData);
    if (resultData != null) {
      if (resultData['message'] != null) {
        FlutterAlert.onErrorAlert(
            context, resultData['message'].toString(), "Error");
        return null;
      } else if (resultData['mobileUser'] != null) {

        if(resultData['permission'] != null){
          permission = resultData['permission'].cast<String>();
        } else {
          permission = null;
        }  

        SharedPreferences prefs = await SharedPreferences.getInstance();
        
        await prefs.setBool(
            'sendOTP', resultData['sendOTP']);
        await prefs.setString(
            'userId', resultData['mobileUser']['userId'].toString());
        await prefs.setString(
            'email', resultData['mobileUser']['email']);
        await prefs.setString(
            'companyCode', resultData['mobileUser']['companyCode']);
        await prefs.setString(
            'companyId', resultData['mobileUser']['companyId'].toString());
        await prefs.setString(
            'firstName', resultData['mobileUser']['firstName']);
        await prefs.setString(
            'lastName', resultData['mobileUser']['lastName']);
        await prefs.setString(
            'companyName', resultData['mobileUser']['companyName']);
        await prefs.setStringList(
            'permission', permission);        
        return resultData;
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support", "Error");
        return null;
      }
    }
  }
}
