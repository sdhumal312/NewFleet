import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:path_provider/path_provider.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';

import '../fleetopuriconstant.dart';
import 'GradientCard.dart';
import 'package:pdf_flutter/pdf_flutter.dart';
import 'package:open_file/open_file.dart';

class ShowDriverRenewal extends KFDrawerContent {
  final int driverId;
  ShowDriverRenewal({Key key, this.driverId});

  @override
  _ShowDriverRenewalState createState() => _ShowDriverRenewalState();
}

class _ShowDriverRenewalState extends State<ShowDriverRenewal>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;
  String driverFirstname;

  int renewal_timethreshold;
  int periodThreshold;
  String periodThresholdStr = '';

  Map driverDetails = Map();
  List driverRenewalList = List();

  String base64ImageString;
  bool imageState = false;
  bool imageAvailable = false;
  List documentDetailsList = new List();
  String shareFileName = "Driver Renewal Docs";
  Map expenseDoc = new Map();
  Uint8List dataFromBase64String(String base64String) {
    return base64Decode(base64String);
  }

  Future<void> _shareImage(base64ImageString) async {
    try {
      Uint8List uni = dataFromBase64String(base64ImageString);
      await Share.file('esys image', 'esys.png', uni, 'image/png');
    } catch (e) {
      print('error: $e');
    }
  }

  @override
  void initState() {
    getSessionData(widget.driverId);
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData(int driverId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'driverId': driverId.toString()
    };

    var response = await ApiCall.getDataFromApi(
        URI.GET_DRIVER_RENEWAL_DATA, data, URI.LIVE_URI, context);

    if (response != null) {
      driverDetails = response['driver'];
      driverRenewalList = response['driverReminder'];
      documentDetailsList = driverRenewalList;

      setState(() {
        driverFirstname = driverDetails['driver_firstname'];
      });
    }
  }

  Widget docsContainer(Map data) {
    String expenseId = data["driver_remid"].toString();
    String fileExtension = data["fileExtension"];
    if (fileExtension != null &&
        fileExtension.isNotEmpty &&
        fileExtension.contains('/')) {
      List fileTEMP = fileExtension.split("/");
      if (fileTEMP != null && fileTEMP.isNotEmpty) {
        fileExtension = fileTEMP[1];
      }
    }
    if (fileExtension != null &&
        fileExtension.isNotEmpty &&
        fileExtension.contains(".sheet")) {
      fileExtension = "xlsx";
    }
    createFileFromString(
        expenseId, data["renewalBase64Document"], fileExtension);
    String filePath = expenseDoc[expenseId];
    int fileTypeid = 0;
    fileTypeid = FileUtility.getFileTypeId(fileExtension);
    if (fileTypeid == FileUtility.PDF_FILE_ID ||
        fileTypeid == FileUtility.EXCEL_FILE_ID) {
      return Visibility(
          visible: (fileTypeid == FileUtility.PDF_FILE_ID ||
              fileTypeid == FileUtility.EXCEL_FILE_ID),
          child: Container(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                renderOpenButton(filePath),
                renderShareButton(
                    fileTypeid, data["renewalBase64Document"], shareFileName)
              ],
            ),
          ));
    } else if (fileTypeid == 0) {
      return imageContainer(data["renewalBase64Document"]);
    } else {
      return Container();
    }
  }

  Future<void> openFile(String filePath) async {
    if (filePath != null && filePath.isNotEmpty) {
      final result = await OpenFile.open(filePath);
    }
  }

  Widget renderShareButton(int fileTypeid, String base64, String fname) {
    return Container(
      child: FloatingActionButton(
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
        onPressed: () {
          FileUtility.shareFile(fileTypeid, base64, fname);
        },
        child: Icon(Icons.share),
      ),
    );
  }

  Widget renderOpenButton(String filePath) {
    return Container(
      child: FloatingActionButton(
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
        onPressed: () {
          openFile(filePath);
        },
        child: Icon(Icons.read_more_outlined),
      ),
    );
  }

  Widget imageContainer(String base64) {
    return (Visibility(
        visible: (base64 != null),
        child: Padding(
            padding: new EdgeInsets.all(8.0),
            child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 100,
              child: Column(
                children: [
                  Image.memory(base64Decode(base64), fit: BoxFit.fill),
                  FloatingActionButton(
                    backgroundColor: Colors.blue,
                    foregroundColor: Colors.white,
                    onPressed: () {
                      _shareImage(base64);
                    },
                    child: Icon(Icons.share),
                  )
                ],
              ),
            ))));
  }

  Future<bool> getData() async {
    await Future.delayed(const Duration(milliseconds: 0));
    return true;
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  void checkTypeofDocument() {
    if (documentDetailsList != null && documentDetailsList.isNotEmpty) {}
  }

  Future<void> createFileFromString(
      String expenseId, String baseData, String ext) async {
    if (ext != null && ext.isNotEmpty && !ext.contains(".")) {
      ext = "." + ext;
    }
    Uint8List bytes = base64.decode(baseData);
    String dir = (await getApplicationDocumentsDirectory()).path;
    File file =
        File("$dir/" + DateTime.now().millisecondsSinceEpoch.toString() + ext);
    await file.writeAsBytes(bytes);
    if (file != null) {
      expenseDoc[expenseId] = file.path;
    }
  }

  Widget renderFiles(Map obj) {
    int fileTypeId = 0;
    fileTypeId = FileUtility.getFileExtensionId(obj["fileExtension"]);
    if (obj != null && obj.isNotEmpty) {
      return Container();
    } else {
      return Container(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            FloatingActionButton(
              backgroundColor: Colors.blue,
              foregroundColor: Colors.white,
              onPressed: () {
                FileUtility.shareFile(
                    fileTypeId, obj["driver_content"], shareFileName);
              },
              child: Icon(Icons.share),
            ),
            FloatingActionButton(
              backgroundColor: Colors.blue,
              foregroundColor: Colors.white,
              onPressed: () {
                FileUtility.shareFile(
                    fileTypeId, obj["driver_content"], shareFileName);
              },
              child: Icon(Icons.share),
            ),
          ],
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Driver Renewal Details'),
        backgroundColor: Colors.purple,
      ),
      // backgroundColor: AppTheme.white,
      body: FutureBuilder(
        future: getData(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return SizedBox();
          } else {
            return Padding(
              padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Expanded(
                    child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (!snapshot.hasData) {
                          return SizedBox();
                        } else {
                          return Scaffold(
                            body: SingleChildScrollView(
                              padding: const EdgeInsets.all(8.0),
                              child: Center(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    SizedBox(height: 25),
                                    Container(
                                      child: Align(
                                        alignment: Alignment.center,
                                        child: Text(
                                            "Driver Name : ${driverFirstname} ",
                                            style: TextStyle(
                                                fontWeight: FontWeight.w700,
                                                color: Colors.red,
                                                fontSize: 25)),
                                      ),
                                    ),
                                    SizedBox(height: 25),
                                    for (int i = 0;
                                        i < driverRenewalList.length;
                                        i++)
                                      ExpansionTile(
                                        backgroundColor: Colors.cyanAccent,
                                        title: Text("Renewal Details"),
                                        leading: Icon(Icons.info),
                                        children: <Widget>[
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Renewal Type",
                                            value: driverRenewalList[i]
                                                    ["driver_remindertype"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "DL Number",
                                            value: driverRenewalList[i]
                                                    ["driver_dlnumber"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "From Date",
                                            value: driverRenewalList[i]
                                                    ["driver_dlfrom_show"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "To Date",
                                            value: driverRenewalList[i]
                                                    ["driver_dlto_show"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          GradientCard(
                                            colorData: Colors.cyanAccent,
                                            valueColorChanged: false,
                                            name: "Time Threshold",
                                            value: driverRenewalList[i]
                                                    ["driver_timethreshold"]
                                                .toString(),
                                            icon: Icons.format_list_numbered,
                                          ),
                                          SizedBox(height: 10),
                                          if (driverRenewalList[i]
                                                  ["renewalBase64Document"] !=
                                              null)
                                            docsContainer(driverRenewalList[i]),
                                          // SizedBox(height: 20),
                                          // if (driverRenewalList[i]
                                          //         ["renewalBase64Document"] !=
                                          //     null)
                                          //   FloatingActionButton(
                                          //     backgroundColor: Colors.blue,
                                          //     foregroundColor: Colors.white,
                                          //     onPressed: () {
                                          //       _shareImage(driverRenewalList[i]
                                          //           ["renewalBase64Document"]);
                                          //     },
                                          //     child: Icon(Icons.share),
                                          //   ),
                                          SizedBox(height: 20),
                                        ],
                                      )
                                  ],
                                ),
                              ),
                            ),
                          );
                        }
                      },
                    ),
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }
}
