import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:path_provider/path_provider.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shimmer/shimmer.dart';
import 'package:pdf_flutter/pdf_flutter.dart';
import 'package:open_file/open_file.dart';

class ShowFuelDetails extends StatefulWidget {
  ShowFuelDetails({this.fueldata});
  final Map fueldata;
  @override
  _ShowFuelDetailsState createState() =>
      _ShowFuelDetailsState(fueldata: fueldata);
}

class _ShowFuelDetailsState extends State<ShowFuelDetails> {
  _ShowFuelDetailsState({this.fueldata});
  final Map fueldata;
  String companyId;
  String base64ImageString;
  bool vehicleDetailsState = true;
  bool fuelInfoState = false;
  bool vendorInfoState = false;
  bool costDetailsState = false;
  bool refInfoState = false;
  bool driverInfoState = false;
  bool imageState = false;
  bool imageAvailable = false;
  bool tripSheetVisible = false;
  int time = 1000;
  File pdfFile;
  Size size;
  double _height;
  double _width;
  String filePath;
  int fileTypeId = 0;
  String shareFileName = "document";
  @override
  void initState() {
    getSessionData();
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    if (fueldata != null) {
      if (fueldata["fuel_TripsheetNumber"] != null) {
        setState(() {
          tripSheetVisible = true;
        });
      }
      if (fueldata['fuelBase64Document'] != null) {
        setState(() {
          imageAvailable = true;
          base64ImageString = fueldata['fuelBase64Document'];
        });
      }
      if (fueldata != null &&
          fueldata.isNotEmpty &&
          fueldata["fileExtension"] != null &&
          fueldata["fileExtension"] != "jpg" &&
          fueldata["fileExtension"] != "pdf" &&
          fueldata["fileExtension"] != "xlsx" &&
          !fueldata["fileExtension"].contains(".ms-excel")) {
        fileTypeId = FileUtility.getFileExtensionId(fueldata["fileExtension"]);
        filePath = await FileUtility.createFilesFromString(
            base64ImageString, fueldata["fileExtension"]);
        setState(() {
          shareFileName = " F-" + fueldata['fuel_Number'].toString();
        });
        setPDFData();
      } else {
        if (fueldata != null &&
            fueldata.isNotEmpty &&
            fueldata["fuelBase64Document"] != null) {
          setState(() {
            base64ImageString = fueldata["fuelBase64Document"];
          });
          if (fueldata["fileExtension"] == "pdf") {
            fileTypeId = 1;
            setState(() {
              shareFileName = " F-" + fueldata['fuel_Number'].toString();
            });
            createFileFromString(base64ImageString, ".pdf");
          }
          if (fueldata["fileExtension"] == "xlsx") {
            fileTypeId = 2;
            setState(() {
              shareFileName = " F-" + fueldata['fuel_Number'].toString();
            });
            createFileFromString(base64ImageString, ".xlsx");
          }
          if (fueldata["fileExtension"].contains(".ms-excel")) {
            fileTypeId = 2;
            setState(() {
              shareFileName = " F-" + fueldata['fuel_Number'].toString();
            });
            createFileFromString(base64ImageString, ".xlsx");
          }
        }
      }
    }
  }

  Future<void> openFile(String filePath) async {
    if (filePath != null && filePath.isNotEmpty) {
      final result = await OpenFile.open(filePath);
    }
  }

  Future<void> createFileFromString(String baseData, String ext) async {
    Uint8List bytes = base64.decode(baseData);
    String dir = (await getApplicationDocumentsDirectory()).path;
    File file =
    File("$dir/" + DateTime.now().millisecondsSinceEpoch.toString() + ext);
    await file.writeAsBytes(bytes);
    filePath = file.path;
    if (file != null) {
      setState(() {
        pdfFile = file;
      });
    }
  }

  setPDFData() async {
    String fileExt = FileUtility.getExtensionNameFromId(fileTypeId);
    if (fileTypeId == FileUtility.PDF_FILE_ID) {
      await createFileFromString(base64ImageString, fileExt);
    }
  }

  Widget imageContainer() {
    return (Visibility(
        visible: (base64ImageString != null && fileTypeId == 0),
        child: Padding(
            padding: new EdgeInsets.all(8.0),
            child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 100,
              child: (base64ImageString != null)
                  ? Image.memory(base64Decode(base64ImageString),
                  fit: BoxFit.fill)
                  : Image.asset("assets/img/signUp.png", fit: BoxFit.fill),
            ))));
  }

  Widget pdfContainer() {
    return (Visibility(
        visible: (pdfFile != null && fileTypeId == FileUtility.PDF_FILE_ID),
        child: SingleChildScrollView(
          child: Padding(
              padding: new EdgeInsets.all(8.0),
              child: Container(
                width: MediaQuery.of(context).size.width,
                height: MediaQuery.of(context).size.height - 100,
                child: PDF.file(
                  pdfFile,
                  height: _height,
                  width: _width,
                  placeHolder: Image.asset("assets/images/pdf.png",
                      height: 300, width: 300),
                ),
              )),
        )));
  }

  Widget fuelInfoContainer() {
    var fuelTank = (fueldata['fuel_tank'] == 0 ? 'Full' : 'Partial');
    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Fuel Type  :  " + fueldata['fuel_type'].toString(),
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Litre  :  " +
                                  fueldata['fuel_liters'].toString() +
                                  "/Litre",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Opening Odo  :  " +
                                  fueldata['fuel_meter_old'].toString() +
                                  "/KM",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Price/Unit  :  " +
                                  fueldata['fuel_price'].toString() +
                                  " Rupees",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Fuel Tank  :  " + fuelTank,
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Visibility(
                visible: tripSheetVisible,
                child: Card(
                  elevation: 0.5,
                  color: Colors.white70,
                  child: Container(
                    height: 50,
                    child: Padding(
                        padding: const EdgeInsets.only(left: 10, right: 10),
                        child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Text(
                                "Trip Sheet  :  TS - " +
                                    fueldata["fuel_TripsheetNumber"].toString(),
                                style: new TextStyle(
                                  fontSize: 15,
                                  color: AppTheme.darkText,
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                            ])),
                  ),
                ),
              )
            ],
          )),
    ));
  }

  Widget driverInfoContainer() {
    var driverName =
    (fueldata['driver_name'] != null ? fueldata['driver_name'] : "");
    var driver2Name = (fueldata['fuelSecDriverName'] != null
        ? fueldata['fuelSecDriverName']
        : "");
    var cleanerName = (fueldata['fuelCleanerName'] != null
        ? fueldata['fuelCleanerName']
        : "");
    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Driver Name  :  " + driverName,
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Driver2 name  :  " + driver2Name,
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Cleaner Name  :  " + cleanerName,
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
            ],
          )),
    ));
  }

  Widget referenceInfoContainer() {
    var ref =
    (fueldata['fuel_reference'] != null ? fueldata['fuel_reference'] : "");
    var comments =
    (fueldata['fuel_comments'] != null ? fueldata['fuel_comments'] : "");
    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Reference  :  " + ref,
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Comments  :  " + comments,
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
            ],
          )),
    ));
  }

  Widget fuelVehicleInfoContainer() {
    var fuelDate = '';
    var routeName =
    (fueldata['fuelRouteName'] != null ? fueldata['fuelRouteName'] : "");

    if (fueldata['fuel_D_date'] != null) {
      var dateTime = new DateTime.fromMicrosecondsSinceEpoch(
          fueldata['fuel_D_date'] * 1000);
      fuelDate = (DateFormat("dd-MM-yyyy").format(dateTime)).toString();
    }

    // var fuelDate =
    //     (fueldata['fuel_date'] != null ? fueldata['fuel_date'] : "");
    // var fuelTime =
    //     (fueldata['fuelTime'] != null ? fueldata['fuelTime'] : "");

    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Vehicle Number  :  " +
                                  fueldata['vehicle_registration'],
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Date/Time  :  " +
                                  fueldata['fuelDateTimeStr'].toString(),
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Opening Odo  :  " +
                                  fueldata['fuel_meter_old'].toString() +
                                  "/KM",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Closing Odo  :  " +
                                  fueldata['fuel_meter'].toString() +
                                  "/KM",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Total Usage KM  :  " +
                                  fueldata['fuel_usage'].toString() +
                                  "KM",
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Route  :  " + routeName,
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
            ],
          )),
    ));
  }

  Widget costdetailsContainer() {
    String kmpl =
    (fueldata["fuel_kml"] != null ? fueldata["fuel_kml"].toString() : "-");
    String costperkmpl = (fueldata["fuel_cost"] != null
        ? fueldata["fuel_cost"].toString()
        : "-");
    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Amount  :  " + fueldata['fuel_amount'].toString(),
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "KM/L  :  " + kmpl,
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Cost Per Km  :  " + costperkmpl,
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
            ],
          )),
    ));
  }

  Widget vendorInfoContainer() {
    String location =
    (fueldata['vendor_name'] != null ? fueldata['vendor_name'] : "");
    return (Padding(
      padding: new EdgeInsets.all(8.0),
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Vendor Name  :  " + location,
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Flexible(
                                child: Text(
                                  "Location  :  " + fueldata['vendor_location'],
                                  overflow: TextOverflow.clip,
                                  style: new TextStyle(
                                    fontSize: 15,
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                  ),
                                )),
                          ])),
                ),
              ),
              Card(
                elevation: 0.5,
                color: Colors.white70,
                child: Container(
                  height: 50,
                  child: Padding(
                      padding: const EdgeInsets.only(left: 10, right: 10),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "Payment Mode  :  " + fueldata['fuel_vendor_paymode'],
                              style: new TextStyle(
                                fontSize: 15,
                                color: AppTheme.darkText,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ])),
                ),
              ),
            ],
          )),
    ));
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.cyan,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Fuel Details",
          style: new TextStyle(
            fontSize: 30,
            color: AppTheme.darkText,
            fontWeight: FontWeight.w700,
          ),
        ),
      ),
      backgroundColor: AppTheme.white,
      body: Padding(
          padding: new EdgeInsets.all(5.0),
          child: SingleChildScrollView(
              padding: const EdgeInsets.all(8.0),
              child: Center(
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[
                        Center(
                          child: Shimmer.fromColors(
                              highlightColor: Colors.black,
                              baseColor: Colors.cyan,
                              period: Duration(milliseconds: time),
                              child: Text(
                                "Fuel Number : F-" +
                                    fueldata['fuel_Number'].toString(),
                                style: new TextStyle(
                                  fontSize: 25,
                                  fontWeight: FontWeight.w700,
                                ),
                              )),
                        ),
                        SizedBox(height: 15),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Vehicle Details ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          vehicleDetailsState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            vehicleDetailsState =
                                            !vehicleDetailsState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: vehicleDetailsState,
                          child: fuelVehicleInfoContainer(),
                        ),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Fuel Information ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          fuelInfoState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            fuelInfoState = !fuelInfoState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: fuelInfoState,
                          child: fuelInfoContainer(),
                        ),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Vendor Information ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          vendorInfoState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            vendorInfoState = !vendorInfoState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: vendorInfoState,
                          child: vendorInfoContainer(),
                        ),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Cost Details ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          costDetailsState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            costDetailsState = !costDetailsState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: costDetailsState,
                          child: costdetailsContainer(),
                        ),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Reference Details ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          refInfoState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            refInfoState = !refInfoState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: refInfoState,
                          child: referenceInfoContainer(),
                        ),
                        Card(
                          elevation: 0.5,
                          color: Colors.cyan,
                          child: Container(
                            height: 60,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Driver Info ",
                                        style: new TextStyle(
                                          fontSize: 22,
                                          color: AppTheme.darkText,
                                          fontWeight: FontWeight.w700,
                                        ),
                                      ),
                                      DialogButton(
                                        width: 100,
                                        child: Text(
                                          driverInfoState ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.white, fontSize: 25),
                                        ),
                                        gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),
                                        onPressed: () => {
                                          setState(() {
                                            driverInfoState = !driverInfoState;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                          visible: driverInfoState,
                          child: driverInfoContainer(),
                        ),
                        Visibility(
                          visible: imageAvailable,
                          child: Card(
                            elevation: 0.5,
                            color: Colors.cyan,
                            child: Container(
                              height: 60,
                              child: Padding(
                                  padding:
                                  const EdgeInsets.only(left: 10, right: 10),
                                  child: Row(
                                      mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                      children: [
                                        Text(
                                          "Document ",
                                          style: new TextStyle(
                                            fontSize: 22,
                                            color: AppTheme.darkText,
                                            fontWeight: FontWeight.w700,
                                          ),
                                        ),
                                        DialogButton(
                                          width: 100,
                                          child: Text(
                                            imageState ? "Close" : "Open",
                                            style: TextStyle(
                                                color: Colors.white, fontSize: 25),
                                          ),
                                          gradient: LinearGradient(colors: [
                                            Colors.greenAccent,
                                            Colors.blueAccent
                                          ]),
                                          onPressed: () => {
                                            setState(() {
                                              imageState = !imageState;
                                            })
                                          },
                                        )
                                      ])),
                            ),
                          ),
                        ),
                        Visibility(
                          visible: imageState,
                          child: imageContainer(),
                        ),
                        // Visibility(
                        //   visible: imageState,
                        //   child: pdfContainer(),
                        // ),
                        SizedBox(height: 20),
                        Visibility(
                          visible: imageState,
                          child: Container(
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      FloatingActionButton(
                                        backgroundColor: Colors.blue,
                                        foregroundColor: Colors.white,
                                        onPressed: () {
                                          FileUtility.shareFile(fileTypeId,
                                              base64ImageString, shareFileName);
                                        },
                                        child: Icon(Icons.share),
                                      ),
                                      Visibility(
                                        visible: fileTypeId ==
                                            FileUtility.PDF_FILE_ID ||
                                            fileTypeId == FileUtility.EXCEL_FILE_ID,
                                        child: FloatingActionButton(
                                          backgroundColor: Colors.blue,
                                          foregroundColor: Colors.white,
                                          onPressed: () {
                                            openFile(filePath);
                                          },
                                          child: Icon(Icons.read_more_outlined),
                                        ),
                                      )
                                    ])),
                          ),
                        ),
                        SizedBox(height: 30),
                      ])))),
    );
  }
}
