import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';
//import 'package:share/share.dart';
//import 'package:image_share/image_share.dart';
//import 'package:share_files_socially/share_files_socially.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'dart:io' as Io;
import '../fleetopuriconstant.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:path_provider/path_provider.dart';
import 'package:open_file/open_file.dart';

class ShowRenewalReminder extends KFDrawerContent {
  final int renewalId;
  ShowRenewalReminder({Key key, this.renewalId});

  @override
  _ShowRenewalReminderState createState() => _ShowRenewalReminderState();
}

class _ShowRenewalReminderState extends State<ShowRenewalReminder>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String renewal_R_Number;
  String vehicle_registration;
  String renewal_type;
  String renewal_subtype;
  String renewal_from;
  String renewal_to;
  String renewal_receipt;
  String vendorName;
  String renewal_paymentType;
  String renewal_PayNumber;
  String renewal_dateofpayment;
  String renewal_paidby;
  String renewal_status;
  String renewal_authorization;
  String renewal_number;

  int renewal_timethreshold;
  int periodThreshold;
  String periodThresholdStr = '';
  double renewal_Amount;

  Map renewalReminder = Map();
  Map configuration;

  bool showDueThreshold = false;
  bool showDueThresholdPeriod = false;
  bool showReceiptNumber = false;
  bool showVendorCol = false;
  bool showCashTranNumber = false;
  bool showPaidDate = false;
  bool showPaidBy = false;
  bool showStateAuthorization = false;
  bool showRemark = false;

  String base64ImageString;
  bool imageState = false;
  bool imageAvailable = false;
  String ext = "";
  String filePath;
  int fileTypeId = 0;
  String shareFileName = "document";
  File pdfFile;
  @override
  void initState() {
    getSessionData(widget.renewalId);
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  Future<void> didPressActionButton() async {
    //var image = base64Decode(base64ImageString);
    //final decodedBytes = base64Decode(base64ImageString);
    //var file = Io.File("temp.png");
    //file.writeAsBytesSync(decodedBytes);
    //print("file...$file");
    //await ImageShare.shareImage(filePath: "assets/images/supportIcon.png");
    // await ImageShare.shareImage(filePath: image);
  }

  void shareFile() {
    //  final decodedBytes = base64Decode(base64ImageString);
    //    var file = Io.File("temp.png");
    //   file.writeAsBytesSync(decodedBytes);
    //  print("file...$file");
    //print("output....${imageFromBase64String(base64ImageString)}");
    //print("outputUint8List....${dataFromBase64String(base64ImageString)}");
    //Image im = imageFromBase64String(base64ImageString);
  }

  Image imageFromBase64String(String base64String) {
    return Image.memory(base64Decode(base64String));
  }

  Uint8List dataFromBase64String(String base64String) {
    return base64Decode(base64String);
  }

  Future<void> _shareImage() async {
    try {
      Uint8List uni = dataFromBase64String(base64ImageString);
      await Share.file('esys image', 'esys.png', uni, 'image/png');
    } catch (e) {
      print('error: $e');
    }
  }

  //   Future<void> didPressActionButton() async {
  //    await ShareFilesSocially.shareFile('assets/images', 'supportIcon.png');
  //  }
  setPDFData() async {
    var pdfFileTemp;
    String fileExt = FileUtility.getExtensionNameFromId(fileTypeId);
    if (fileTypeId == FileUtility.PDF_FILE_ID) {
      await createFileFromString(base64ImageString, fileExt);
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

  getSessionData(int renewalId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'renewalId': renewalId.toString()
    };

    var response = await ApiCall.getDataFromApi(
        URI.SHOW_RR_DATA, data, URI.LIVE_URI, context);

    if (response != null) {
      renewalReminder = response['renewalReminder'];
      configuration = response['configuration'];

      setState(() {
        renewal_R_Number = renewalReminder['renewal_R_Number'].toString();
        vehicle_registration = renewalReminder['vehicle_registration'];
        renewal_type = renewalReminder['renewal_type'];
        renewal_subtype = renewalReminder['renewal_subtype'];
        renewal_from = renewalReminder['renewal_from'];
        renewal_to = renewalReminder['renewal_to'];
        renewal_timethreshold = renewalReminder['renewal_timethreshold'];
        periodThreshold = renewalReminder['renewal_periedthreshold'];

        if (periodThreshold == 0) {
          periodThresholdStr = "Days";
        } else if (periodThreshold == 7) {
          periodThresholdStr = "Weeks";
        } else {
          periodThresholdStr = "Months";
        }

        renewal_Amount = renewalReminder['renewal_Amount'];
        renewal_receipt = renewalReminder['renewal_receipt'];
        vendorName = renewalReminder['vendorName'];
        renewal_paymentType = renewalReminder['renewal_paymentType'];
        renewal_PayNumber = renewalReminder['renewal_PayNumber'];
        renewal_dateofpayment = renewalReminder['renewal_dateofpayment'];
        renewal_paidby = renewalReminder['renewal_paidby'];
        renewal_status = renewalReminder['renewal_status'];
        renewal_authorization = renewalReminder['renewal_authorization'];
        renewal_number = renewalReminder['renewal_number'];

        showReceiptNumber = configuration['receiptnumbershow'];
        showCashTranNumber = configuration['cashtransactionshow'];
        showPaidDate = configuration['paidDateshow'];
        showPaidBy = configuration['paidbyshow'];
        showStateAuthorization = configuration['optionalInformation'];
        showRemark = configuration['optionalInformation'];
        showVendorCol = configuration['showVendorCol'];

        if (renewal_number == null) {
          renewal_number = '-';
        }

        if (vendorName == null) {
          vendorName = '-';
        }
      });

      if (response['renewalImage'] != null) {
        setState(() {
          imageAvailable = true;
          base64ImageString = response['renewalImage'];
          ext = response["imageExt"];
          if (ext != null && ext.isNotEmpty && ext.contains('/')) {
            List fileTEMP = ext.split("/");
            if (fileTEMP != null && fileTEMP.isNotEmpty) {
              ext = fileTEMP[1];
            }
          }
          if (ext != null && ext.isNotEmpty && ext.contains(".sheet")) {
            ext = "xlsx";
          }
          if (ext != null && ext.isNotEmpty && ext.contains(".ms-excel")) {
            ext = "xlsx";
          }
        });
      }
      if (response != null &&
          response.isNotEmpty &&
          response["imageExt"] != null &&
          response["imageExt"] != "jpg" &&
          response["imageExt"] != "pdf" &&
          response["imageExt"] != "xlsx" &&
          !response["imageExt"].contains(".ms-excel")) {
        fileTypeId = FileUtility.getFileExtensionId(response["imageExt"]);
        filePath = await FileUtility.createFilesFromString(
            base64ImageString, response["imageExt"]);
        setState(() {
          shareFileName = renewal_R_Number;
        });
        setPDFData();
      } else {
        if (response != null &&
            response.isNotEmpty &&
            response['renewalImage'] != null) {
          setState(() {
            base64ImageString = response['renewalImage'];
          });
          if (response["imageExt"] == "pdf") {
            fileTypeId = 1;
            setState(() {
              shareFileName = renewal_R_Number;
            });
            createFileFromString(base64ImageString, ".pdf");
          }
          if (response["imageExt"] == "xlsx") {
            fileTypeId = 2;
            setState(() {
              shareFileName = renewal_R_Number;
            });
            createFileFromString(base64ImageString, ".xlsx");
          }
          if (response["imageExt"] == "xlsx" || ext == "xlsx") {
            fileTypeId = 2;
            setState(() {
              shareFileName = renewal_R_Number;
            });
            createFileFromString(base64ImageString, ".xlsx");
          }
        }
      }
    }
  }

  Widget imageContainer() {
    return (Visibility(
        visible: (base64ImageString != null),
        child: Padding(
            padding: new EdgeInsets.all(8.0),
            child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 100,
              child: (base64ImageString != null)
                  ? Image.memory(base64Decode(base64ImageString),
                      fit: BoxFit.fill)
                  : Image.asset("assets/images/supportIcon.png",
                      fit: BoxFit.fill),
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

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Renewal Reminder Details'),
        backgroundColor: Colors.redAccent,
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
                                            "Renewal Reminder : RR - ${renewal_R_Number}",
                                            style: TextStyle(
                                                fontWeight: FontWeight.w700,
                                                color: Colors.pink,
                                                fontSize: 25)),
                                      ),
                                    ),

                                    SizedBox(height: 20),
                                    Container(
                                      child: Align(
                                        alignment: Alignment.center,
                                        child: Text("${vehicle_registration}",
                                            style: TextStyle(
                                                fontWeight: FontWeight.w700,
                                                color: Colors.green,
                                                fontSize: 25)),
                                      ),
                                    ),

                                    SizedBox(height: 40),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text:
                                                  " Renewal Reminder Details ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 21)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.pink,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Renewal Type : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_type}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Renewal Sub Type : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_subtype}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " From Date : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_from}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " To Date : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_to}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Time Threshold : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text:
                                                  " ${renewal_timethreshold} ${periodThresholdStr}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(
                                        height:
                                            showReceiptNumber == true ? 15 : 0),
                                    Visibility(
                                        visible: showReceiptNumber,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Receipt No : ",
                                                  style: new TextStyle(
                                                      color: AppTheme.darkText,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                              new TextSpan(
                                                  text: " ${renewal_receipt}",
                                                  style: new TextStyle(
                                                      color: Colors.purple,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: showReceiptNumber,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Amount : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_Amount}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(
                                        height: showVendorCol == true ? 15 : 0),
                                    Visibility(
                                        visible: showVendorCol,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Vendor Name : ",
                                                  style: new TextStyle(
                                                      color: AppTheme.darkText,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                              new TextSpan(
                                                  text: " ${vendorName}",
                                                  style: new TextStyle(
                                                      color: Colors.purple,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: showVendorCol,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Payment Type : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_paymentType}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(
                                        height: showCashTranNumber == true
                                            ? 15
                                            : 0),
                                    Visibility(
                                        visible: showCashTranNumber,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text:
                                                      " Cash Transaction Number : ",
                                                  style: new TextStyle(
                                                      color: AppTheme.darkText,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                              new TextSpan(
                                                  text: " ${renewal_PayNumber}",
                                                  style: new TextStyle(
                                                      color: Colors.purple,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: showCashTranNumber,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Paid Date : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_dateofpayment}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Paid By : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_paidby}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(height: 15),
                                    RichText(
                                      text: TextSpan(
                                        children: <TextSpan>[
                                          new TextSpan(
                                              text: " Renewal Status : ",
                                              style: new TextStyle(
                                                  color: AppTheme.darkText,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                          new TextSpan(
                                              text: " ${renewal_status}",
                                              style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15)),
                                        ],
                                      ),
                                    ),
                                    Container(
                                      height: 2.0,
                                      width: MediaQuery.of(context).size.width,
                                      color: Colors.black,
                                      margin: const EdgeInsets.only(
                                          left: 5.0, right: 5.0),
                                    ),

                                    SizedBox(
                                        height: showStateAuthorization == true
                                            ? 15
                                            : 0),
                                    Visibility(
                                        visible: showStateAuthorization,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text:
                                                      " State Authorization : ",
                                                  style: new TextStyle(
                                                      color: AppTheme.darkText,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                              new TextSpan(
                                                  text:
                                                      " ${renewal_authorization}",
                                                  style: new TextStyle(
                                                      color: Colors.purple,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: showStateAuthorization,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(
                                        height: showRemark == true ? 15 : 0),
                                    Visibility(
                                        visible: showRemark,
                                        child: RichText(
                                          text: TextSpan(
                                            children: <TextSpan>[
                                              new TextSpan(
                                                  text: " Remark : ",
                                                  style: new TextStyle(
                                                      color: AppTheme.darkText,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                              new TextSpan(
                                                  text: " ${renewal_number}",
                                                  style: new TextStyle(
                                                      color: Colors.purple,
                                                      fontWeight:
                                                          FontWeight.w700,
                                                      fontSize: 15)),
                                            ],
                                          ),
                                        )),
                                    Visibility(
                                        visible: showRemark,
                                        child: Container(
                                          height: 2.0,
                                          width:
                                              MediaQuery.of(context).size.width,
                                          color: Colors.black,
                                          margin: const EdgeInsets.only(
                                              left: 5.0, right: 5.0),
                                        )),

                                    SizedBox(height: 15),
                                    Visibility(
                                      visible: imageAvailable,
                                      child: Card(
                                        elevation: 0.5,
                                        color: Colors.redAccent,
                                        child: Container(
                                          height: 60,
                                          child: Padding(
                                              padding: const EdgeInsets.only(
                                                  left: 10, right: 10),
                                              child: Row(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment
                                                          .spaceBetween,
                                                  children: [
                                                    Text(
                                                      "Document ",
                                                      style: new TextStyle(
                                                        fontSize: 22,
                                                        color: AppTheme.white,
                                                        fontWeight:
                                                            FontWeight.w700,
                                                      ),
                                                    ),
                                                    DialogButton(
                                                      width: 100,
                                                      child: Text(
                                                        imageState
                                                            ? "Close"
                                                            : "Open",
                                                        style: TextStyle(
                                                            color: Colors.white,
                                                            fontSize: 25),
                                                      ),
                                                      color: Colors.amber,
                                                      onPressed: () => {
                                                        setState(() {
                                                          imageState =
                                                              !imageState;
                                                        })
                                                      },
                                                    )
                                                  ])),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 20),
                                    // Visibility(
                                    //   visible: imageState,
                                    //   child: imageContainer(),
                                    // ),
                                    // Visibility(
                                    //   visible: imageState,
                                    //   child: Container(
                                    //     child: Padding(
                                    //         padding:
                                    //             const EdgeInsets.only(left: 10),
                                    //         child: Row(
                                    //             mainAxisAlignment:
                                    //                 MainAxisAlignment
                                    //                     .spaceBetween,
                                    //             children: [
                                    //               FloatingActionButton(
                                    //                 backgroundColor:
                                    //                     Colors.blue,
                                    //                 foregroundColor:
                                    //                     Colors.white,
                                    //                 onPressed: () {
                                    //                   _shareImage();
                                    //                 },
                                    //                 child: Icon(Icons.share),
                                    //               )
                                    //             ])),
                                    //   ),
                                    // ),
                                    Visibility(
                                      visible: imageState,
                                      child: Container(
                                        child: Padding(
                                            padding:
                                                const EdgeInsets.only(left: 10),
                                            child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment
                                                        .spaceBetween,
                                                children: [
                                                  FloatingActionButton(
                                                    backgroundColor:
                                                        Colors.blue,
                                                    foregroundColor:
                                                        Colors.white,
                                                    onPressed: () {
                                                      FileUtility.shareFile(
                                                          fileTypeId,
                                                          base64ImageString,
                                                          shareFileName);
                                                    },
                                                    child: Icon(Icons.share),
                                                  ),
                                                  Visibility(
                                                    visible: fileTypeId ==
                                                            FileUtility
                                                                .PDF_FILE_ID ||
                                                        fileTypeId ==
                                                            FileUtility
                                                                .EXCEL_FILE_ID,
                                                    child: FloatingActionButton(
                                                      backgroundColor:
                                                          Colors.blue,
                                                      foregroundColor:
                                                          Colors.white,
                                                      onPressed: () {
                                                        openFile(filePath);
                                                      },
                                                      child: Icon(Icons
                                                          .read_more_outlined),
                                                    ),
                                                  )
                                                ])),
                                      ),
                                    ),
                                    SizedBox(height: 30),
                                    Visibility(
                                      visible: imageState && fileTypeId <= 0,
                                      child: imageContainer(),
                                    ),
                                    // Visibility(
                                    //   visible: imageState,
                                    //   child: Container(
                                    //     child: Padding(
                                    //         padding:
                                    //             const EdgeInsets.only(left: 10),
                                    //         child: Row(
                                    //             mainAxisAlignment:
                                    //                 MainAxisAlignment
                                    //                     .spaceBetween,
                                    //             children: [
                                    //               DialogButton(
                                    //                 width: 130,
                                    //                 child: Text(
                                    //                   "Share",
                                    //                   style: TextStyle(
                                    //                       color: Colors.white,
                                    //                       fontSize: 25),
                                    //                 ),
                                    //                 color: Colors.purple,
                                    //                 onPressed: (){
                                    //                   _shareImage();
                                    //                 },
                                    //               )
                                    //             ])),
                                    //   ),
                                    // ),
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

  Future<void> openFile(String filePath) async {
    if (filePath != null && filePath.isNotEmpty) {
      final result = await OpenFile.open(filePath);
    }
  }
  // void share(){
  //   print("a..");

  //   String text = "Hello";

  //   Share.share(text);
  // }

}
