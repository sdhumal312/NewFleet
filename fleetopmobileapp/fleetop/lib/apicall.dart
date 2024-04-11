import 'package:fleetop/checkinternetconnection.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'AppAutoUpdate/AppUpdateConstant.dart';
import 'custom_dialogue/custom_loader_dialogue.dart';
import 'fleetopuriconstant.dart';

class ApiCall {
  static getDataFromApi(key, data, baseURI, context) async {
    bool isDev = false;
    assert(isDev = true);

    SharedPreferences prefs = await SharedPreferences.getInstance();
    String companyCode = prefs.getString("companyCode");

    if(companyCode.toLowerCase() == 'srs'){
      baseURI = URI.SRS_LIVE_URI;
    }

    if (isDev) {
      baseURI = URI.DEVELOPEMENT_URI;
    }

    var webServiceURI = baseURI + key;
    try {
      var value = await ConnectionChecker.checkInternetConnection();
      if (value == 1) {
        showDialog(
            context: context,
            builder: (BuildContext context) {
              return CustomLoaderDialog();
            });
        var response = await http.post(Uri.encodeFull(webServiceURI),
            headers: {"Accept": "application/json"}, body: data);
        if (response.statusCode != 200) {
          Navigator.pop(context);
          return null;
        } else {
          var mydata = json.decode(response.body);
          Navigator.pop(context);
          return mydata;
        }
      } else {
        FlutterAlert.onInfoAlert(
            context, "Plase check your internet connection !", "Info");
      }
    } catch (e) {
      Navigator.pop(context);
      FlutterAlert.onErrorAlert(
          context, "Some Error Occur Please call on Support !", "Error");
      print(e);
    }
  }

  static getDataWithoutLoader(key, data, baseURI) async {
    bool isDev = false;
    assert(isDev = true);

    SharedPreferences prefs = await SharedPreferences.getInstance();
    String companyCode = prefs.getString("companyCode");

    if(companyCode.toLowerCase() == 'srs'){
      baseURI = URI.SRS_LIVE_URI;
    }

    if (isDev) {
      baseURI = URI.DEVELOPEMENT_URI;
    }
    var webServiceURI = baseURI + key;
    try {
      var response = await http.post(Uri.encodeFull(webServiceURI),
          headers: {"Accept": "application/json"}, body: data);
      print(response);
      if (response.statusCode != 200) {
        return null;
      } else {
        var mydata = json.decode(response.body);
        return mydata;
      }
    } catch (e) {
      print(e);
    }
  }

  static loginApi(key, data, baseURI, context, companyCode) async {
    bool isDev = false;
    assert(isDev = true);
    String code = companyCode.replaceAll(new RegExp(r"\s+\b|\b\s"), "");
    if(code.toLowerCase() == 'srs'){
      baseURI = URI.SRS_LIVE_URI;
    }
    if (isDev) {
      baseURI = URI.DEVELOPEMENT_URI;
    }
    print("baseURI......$baseURI");
    var webServiceURI = baseURI + key;
    try {
      var value = await ConnectionChecker.checkInternetConnection();
      if (value == 1) {
        showDialog(
            context: context,
            builder: (BuildContext context) {
              return CustomLoaderDialog();
            });
        var response = await http.post(Uri.encodeFull(webServiceURI),
            headers: {"Accept": "application/json"}, body: data);
        if (response.statusCode != 200) {
          Navigator.pop(context);
          return null;
        } else {
          var mydata = json.decode(response.body);
          Navigator.pop(context);
          return mydata;
        }
      } else {
        FlutterAlert.onInfoAlert(
            context, "Plase check your internet connection !", "Info");
      }
    } catch (e) {
      FlutterAlert.onErrorAlert(
          context, "Some Error Occur Please call on Support !", "Error");
      Navigator.pop(context);
     
    }
  }

  static versionCheckWithoutLoader(key, data, baseURI) async {

    var webServiceURI = baseURI + key;
    try {
      var response = await http.post(Uri.encodeFull(webServiceURI),
          headers: {"Accept": "application/json"}, body: data);
      print(response);
      if (response.statusCode != 200) {
        return null;
      } else {
        var mydata = json.decode(response.body);
        return mydata;
      }
    } catch (e) {
      print(e);
    }
  }

  static Future<double> getPlayStoreVersion(double data) async {
    try {
      // Await the http get response, then decode the json-formatted response.
      var response = await http.get(AppUpdateConstant.APP_URL);
      if (response.statusCode == 200) {
        var jsonResponse = response.body;
        if (jsonResponse.toString().contains(AppUpdateConstant.APP_DATA)) {
          int res =
              jsonResponse.toString().lastIndexOf(AppUpdateConstant.APP_DATA);
          String actString = jsonResponse.toString().substring(res - 100);
          actString = actString.substring(1, 120);
          int fcode = actString.indexOf("data");
          String finalqry =
              actString.substring(fcode + 6, actString.length - 9);
          List versionDetails = finalqry.split(",");

          if (versionDetails.isNotEmpty) {
            String fv = versionDetails[1]
                .toString()
                .substring(1, versionDetails[1].toString().length - 1);
            data = double.parse(fv);
            return data;
          } else {
            data = 0.0;
            return data;
          }
        } else {
          data = 0.0;
          return data;
        }
      } else {
        data = 0.0;
        return data;
      }
    } catch (e) {
      data = 0.0;
      return data;
    }
  }
}
