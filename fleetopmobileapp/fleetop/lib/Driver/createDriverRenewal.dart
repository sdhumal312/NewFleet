import 'dart:convert';
import 'dart:io';
import 'dart:math';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:file_picker/file_picker.dart';
import 'package:fleetop/Driver/showDriverRenewal.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';

import '../fleetopuriconstant.dart';

class CreateDriverRenewal extends KFDrawerContent {
  CreateDriverRenewal({Key key});

  @override
  _CreateDriverRenewalState createState() => _CreateDriverRenewalState();
}

class _CreateDriverRenewalState extends State<CreateDriverRenewal>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String driverId = '';

  var driverRenewalType = '';
  var thresholdPeriodType = '';

  Map driverNameList = Map();
  List driverNameData = List();

  List driverRenewalData = List();
  List thresholdPeriodData = List();

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("H:mm");

  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  bool showImage = false;

  TextEditingController driverName = new TextEditingController();
  TextEditingController validityFrom = new TextEditingController();
  TextEditingController validityTo = new TextEditingController();
  TextEditingController dlNumber = new TextEditingController();
  TextEditingController dueThreshold = new TextEditingController();
  TextEditingController imageUpload = new TextEditingController();
  TextEditingController renewalSearch = new TextEditingController();
  String fileName;
  String path;
  File fileData;
  Map<String, String> paths;
  List<String> extensions;
  bool isLoadingPath = false;
  bool isMultiPick = false;
  FileType fileType;
  double fileSize = 0;
  double expectedFileSize = 512.0;

  @override
  void initState() {
    setExtensionData();
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  setExtensionData() {
    extensions = new List<String>();
    extensions.add(".pdf");
    extensions.add(".xlsx");
  }

  Future _openFileExplorer() async {
    Navigator.pop(context, 'Cancel');
    setState(() {
      fileName = "";
      path = "";
      fileData = null;
      paths = null;
    });
    setState(() => isLoadingPath = true);
    try {
      fileData = await FilePicker.getFile(
          type: FileType.custom, allowedExtensions: extensions);
      getFileSize(fileData.path, 1);
      var ext = fileData.path.split(".").last;
      paths = null;
      imageName = fileData.path.split("/").last;
      imageExt = ext;
      base64Image = base64Encode(fileData.readAsBytesSync());
      imageData = base64Image;
    } on PlatformException catch (e) {
      print("Unsupported operation" + e.toString());
    }
    if (!mounted) return;
  }

  Future<String> getFileSize(String filepath, int decimals) async {
    var file = File(filepath);
    int bytes = await file.length();
    if (bytes <= 0) return "0 B";
    const suffixes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    var i = (log(bytes) / log(1024)).floor();
    String ab = ((bytes / pow(1024, i)).toStringAsFixed(decimals));
    print("ab =$ab");
    fileSize = double.parse(ab);
    return ((bytes / pow(1024, i)).toStringAsFixed(decimals)) +
        ' ' +
        suffixes[i];
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId};
    var response = await ApiCall.getDataFromApi(
        URI.INITIALIZE_FUEL_ENTRY_DATA, data, URI.LIVE_URI, context);

    if (response != null) {
      setState(() {});
    }
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

  Widget showselectedImage() {
    return Container(
      child: Stack(
        children: <Widget>[
          Container(
            child: CircleAvatar(
              radius: 120,
              child: ClipOval(
                  child: new SizedBox(
                      width: 230.0,
                      height: 230.0,
                      child: (base64Image != null && base64Image.length > 0)
                          ? Image.memory(base64Decode(base64Image),
                              fit: BoxFit.fill)
                          : Image.asset("assets/img/signUp.png"))),
            ),
          ),
        ],
      ),
    );
  }

  openBottomSheet() {
    void containerForSheet<T>({BuildContext context, Widget child}) {
      showCupertinoModalPopup<T>(
        context: context,
        builder: (BuildContext context) => child,
      ).then<void>((T value) {});
    }

    containerForSheet<String>(
      context: context,
      child: CupertinoActionSheet(
          title: const Text(
            'Select Image 😊',
          ),
          actions: <Widget>[
            CupertinoActionSheetAction(
                child: const Text(" ▦ Select Image From Gallery"),
                onPressed: getImageGallery),
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Image From Camera"),
              onPressed: getImageFromCamera,
            ),
            CupertinoActionSheetAction(
              child: const Text("⟃ ⟄ Select Documents"),
              onPressed: _openFileExplorer,
            ),
          ],
          cancelButton: CupertinoActionSheetAction(
            child: const Text('Cancel'),
            isDefaultAction: true,
            onPressed: () {
              Navigator.pop(context, 'Cancel');
            },
          )),
    );
  }

  Future getImageGallery() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.gallery, maxHeight: 600, maxWidth: 600);
    if (imageFile == null) {
      return;
    }
    setState(() {
      myImage = imageFile;
      showImage = true;
    });
    imageName = myImage.path.split("/").last;
    imageExt = imageName.split(".").last;

    testCompressAndGetFile(myImage, myImage.path);
    base64Image = base64Encode(myImage.readAsBytesSync());
    setState(() {
      uploadedFile = base64Image;
    });
  }

  Future getImageFromCamera() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.camera, maxHeight: 500, maxWidth: 500);
    if (imageFile == null) {
      return;
    }
    imageName = imageFile.path.split("/").last;
    imageExt = imageName.split(".").last;

    setState(() {
      myImage = imageFile;
      showImage = true;
    });
    testCompressAndGetFile(myImage, myImage.path);
    base64Image = base64Encode(myImage.readAsBytesSync());
    setState(() {
      uploadedFile = base64Image;
    });
  }

  void testCompressAndGetFile(File file, String targetPath) async {
    var result = await FlutterImageCompress.compressAndGetFile(
      file.absolute.path,
      targetPath,
      quality: 100,
      rotate: 360,
    );
    Navigator.pop(context, 'Cancel');
    getBase64Image(result);
  }

  Future<String> getBase64Image(File file) async {
    if (file != null) {
      base64Image = base64Encode(file.readAsBytesSync());
      setState(() {
        imageData = base64Image;
      });
    }
  }

  Future _handleSubmitted() async {
    if (!fieldValidation()) {
      return;
    } else {
      var driverRenewalDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'driverId': driverId,
        'driverRenewalType': driverRenewalType,
        'dlNumber': dlNumber.text,
        'validityFrom': validityFrom.text,
        'validityTo': validityTo.text,
        'timeThreshold': dueThreshold.text,
        'renewalPeriodThreshold': thresholdPeriodType,
        'base64String': imageData,
        'imageName': imageName,
        'imageExt': imageExt,
      };

      final jsonEncoder = JsonEncoder();
      var finalrenewalData = {
        'driverRenewalDetails': jsonEncoder.convert(driverRenewalDetails)
      };

      print("finalrenewalData...${finalrenewalData}");

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_DRIVER_RENEWAL, finalrenewalData, URI.LIVE_URI, context);

      print("output.....${data}");

      if (data != null) {
        if (data['addDriverReminder'] == false) {
          FlutterAlert.onInfoAlert(context, "Renewal Not Created", "Info");
        } else {
          refreshData();
          print("Id......${data['driverId']}");
          redirectToDisplay(data['driverId']);
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool fieldValidation() {
    if (driverId == '' || driverId == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Driver !", "Error");
      return false;
    }

    if (driverRenewalType == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Renewal Type !", "Error");
      return false;
    }

    if (dlNumber.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select DL Number !", "Error");
      return false;
    }

    if (validityFrom.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Validity From Date !", "Error");
      return false;
    }

    if (validityTo.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Validity To Date !", "Error");
      return false;
    }

    if (dueThreshold.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Due Threshold !", "Error");
      return false;
    }

    if (thresholdPeriodType == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Threshold Period Type !", "Error");
      return false;
    }

    return true;
  }

  refreshData() {
    driverId = '';
    driverName.text = '';
    driverRenewalType = '';
    dlNumber.text = '';
    validityFrom.text = '';
    validityTo.text = '';
    dueThreshold.text = '';
    thresholdPeriodType = '';
    imageData = '';
    showImage = false;
  }

  Future<bool> redirectToDisplay(driverId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Data Successfully Saved !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => getPickAndDropData(driverId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  getPickAndDropData(int driverId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) => ShowDriverRenewal(driverId: driverId)),
    );
  }

  searchRenewalByNumber(String drvEmpNumber) async {
    print("drvEmpNumber...${drvEmpNumber}");

    if (drvEmpNumber != '') {
      var data = {
        'companyId': companyId,
        'driverEmpNumber': drvEmpNumber,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.SEARCH_DRIVER_BY_EMPNUMBER, data, URI.LIVE_URI);
      print("response........${response}");
      if (response != null) {
        if (response['driverFound'] == true) {
          int driverId = response['driverId'];
          print("driverId........${driverId}");
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => ShowDriverRenewal(driverId: driverId)),
          );
        } else {
          FlutterAlert.onErrorAlert(
              context, "Please Enter Valid Driver Employee Number !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Driver Employee Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Driver Employee Number !", "Error");
    }
  }

  Widget appBarTitle = new Text("Driver Renewal Reminder");
  Icon actionIcon = new Icon(Icons.search);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.purple,
          centerTitle: true,
          title: appBarTitle,
          leading: new IconButton(
            icon: new Icon(Icons.menu, color: Colors.white),
            onPressed: widget.onMenuPressed,
          ),
          actions: <Widget>[
            new IconButton(
              icon: actionIcon,
              onPressed: () {
                setState(() {
                  if (this.actionIcon.icon == Icons.search) {
                    this.actionIcon = new Icon(Icons.close);
                    this.appBarTitle = new TextField(
                      controller: renewalSearch,
                      style: new TextStyle(
                        color: Colors.white,
                      ),
                      //focusNode: myFocusNode,
                      decoration: new InputDecoration(
                        labelText: 'Driver Emp No',
                        labelStyle: TextStyle(color: Colors.white),
                        icon: new RaisedButton(
                          padding: const EdgeInsets.all(2.0),
                          textColor: Colors.white,
                          color: Colors.pink,
                          onPressed: () {
                            searchRenewalByNumber(renewalSearch.text);
                          },
                          child: Padding(
                            padding: const EdgeInsets.symmetric(
                                vertical: 0.5, horizontal: 0.5),
                            child: Text(
                              "Search",
                              style: TextStyle(
                                  fontSize: 15,
                                  color: Colors.white,
                                  fontWeight: FontWeight.w500),
                            ),
                          ),
                        ),
                      ),
                    );
                  } else {
                    this.actionIcon =
                        new Icon(Icons.search, color: Colors.white);
                    this.appBarTitle = new Text("Driver Renewal Reminder");
                  }
                });
              },
            ),
          ]),
      backgroundColor: AppTheme.white,
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
                                    SizedBox(height: 10),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: driverName,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.person,
                                                      color: Colors.cyan,
                                                    ),
                                                    hintText: 'Driver Name',
                                                    labelText: 'Driver Name'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getDriverNameSuggestions(
                                                    pattern, 1);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(Icons.person),
                                                  title: Text(
                                                      suggestion['driverName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                driverName.text =
                                                    suggestion['driverName'];
                                                driverId =
                                                    suggestion['driver_id'];
                                                getDriverDLList(driverId);
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: driverRenewalData.length > 0
                                            ? 15
                                            : 0),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Visibility(
                                      visible: driverRenewalData.length > 0,
                                      child: Container(
                                        height: 70,
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: DropdownButtonHideUnderline(
                                              child: Card(
                                                  elevation: 0.5,
                                                  color: Colors.white70,
                                                  child: Container(
                                                      padding:
                                                          EdgeInsets.all(17),
                                                      child: DropdownButton<
                                                          String>(
                                                        hint: Text(
                                                            "Select Renewal Type"),
                                                        value: driverRenewalType !=
                                                                ''
                                                            ? driverRenewalType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            driverRenewalType =
                                                                newValue;
                                                          });
                                                        },
                                                        items: driverRenewalData
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'dri_DocType'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value:
                                                                item['dri_id']
                                                                    .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: dlNumber,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'DL Number',
                                                hintText: 'DL Number',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.confirmation_number,
                                                  color: Colors.amber,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 10),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: validityFrom,
                                            readOnly: true,
                                            initialValue: DateTime.now(),
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Validity From",
                                                labelText: "Validity From",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.blue,
                                                )),
                                            //format: format,
                                            onShowPicker:
                                                (context, currentValue) {
                                              return showDatePicker(
                                                  context: context,
                                                  firstDate: DateTime(1950),
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime(2030));
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 3),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: DateTimeField(
                                            format: format,
                                            controller: validityTo,
                                            readOnly: true,
                                            initialValue: DateTime.now(),
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                hintText: "Validity To",
                                                labelText: "Validity To",
                                                hintStyle: TextStyle(
                                                    color: Color(0xff493240)),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.date_range,
                                                  color: Colors.brown,
                                                )),
                                            //format: format,
                                            onShowPicker:
                                                (context, currentValue) {
                                              return showDatePicker(
                                                  context: context,
                                                  firstDate: DateTime(1950),
                                                  initialDate: currentValue ??
                                                      DateTime.now(),
                                                  lastDate: DateTime(2030));
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 15),
                                    Align(
                                      alignment: Alignment.centerRight,
                                      child: Container(
                                        child: Icon(Icons.stars,
                                            color: Colors.red, size: 12),
                                      ),
                                    ),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: dueThreshold,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Due Threshold',
                                                hintText: 'Due Threshold',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.rate_review,
                                                  color: Colors.green,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: thresholdPeriodData.length > 0
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: thresholdPeriodData.length > 0,
                                      child: Container(
                                        height: 70,
                                        child: Padding(
                                          padding: const EdgeInsets.only(
                                              top: 5.0, left: 10),
                                          child: Container(
                                            child: DropdownButtonHideUnderline(
                                              child: Card(
                                                  elevation: 0.5,
                                                  color: Colors.white70,
                                                  child: Container(
                                                      padding:
                                                          EdgeInsets.all(17),
                                                      child: DropdownButton<
                                                          String>(
                                                        hint: Text(
                                                            "Select Threshold Period Time "),
                                                        value: thresholdPeriodType !=
                                                                ''
                                                            ? thresholdPeriodType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            thresholdPeriodType =
                                                                newValue;
                                                          });
                                                        },
                                                        items:
                                                            thresholdPeriodData
                                                                .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'renewalPrdThresholdStr'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'renewalPrdThreshold']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(height: 25),
                                    Container(
                                      child: Padding(
                                          padding:
                                              const EdgeInsets.only(left: 10),
                                          child: Row(
                                              mainAxisAlignment:
                                                  MainAxisAlignment
                                                      .spaceBetween,
                                              children: [
                                                Icon(
                                                  Icons.file_upload,
                                                  color: Colors.purpleAccent,
                                                ),
                                                Text(
                                                  "Renewal Document ",
                                                  style: new TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.darkText,
                                                    fontWeight: FontWeight.w700,
                                                  ),
                                                ),
                                                DialogButton(
                                                  width: 130,
                                                  child: Text(
                                                    "Browse",
                                                    style: TextStyle(
                                                        color: Colors.white,
                                                        fontSize: 25),
                                                  ),
                                                  color: Colors.blueAccent,
                                                  onPressed: openBottomSheet,
                                                )
                                              ])),
                                    ),
                                    SizedBox(height: 10),
                                    Visibility(
                                      visible: showImage,
                                      child: showselectedImage(),
                                    ),
                                    SizedBox(height: 30),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                            color: Colors.purple,
                                          ),
                                          child: MaterialButton(
                                            highlightColor: Colors.transparent,
                                            splashColor: Colors.purpleAccent,
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 10.0,
                                                      horizontal: 42.0),
                                              child: Text(
                                                "Submit",
                                                style: TextStyle(
                                                    fontSize: 22,
                                                    color: AppTheme.white,
                                                    fontWeight:
                                                        FontWeight.w700),
                                              ),
                                            ),
                                            onPressed: _handleSubmitted,
                                          )),
                                    ),
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

  Future<List> getDriverNameSuggestions(String query, int checkDriver) async {
    getDriverNameDetails(query, checkDriver);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(driverNameData.length, (index) {
      return driverNameData[index];
    });
  }

  getDriverNameDetails(String str, int checkDriver) async {
    driverNameList = new Map();
    driverId = '';

    setState(() {
      driverNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
          print(response["driverList"].length);
          for (int i = 0; i < response["driverList"].length; i++) {
            var obj = {
              "driver_id": response['driverList'][i]['driver_id'].toString(),
              "driverName":
                  response['driverList'][i]['driver_empnumber'].toString() +
                      '-' +
                      response['driverList'][i]['driver_firstname'].toString() +
                      ' ' +
                      response['driverList'][i]['driver_Lastname'].toString()
            };
            driverNameList[obj['driver_id']] = obj;
            setState(() {
              driverNameData = driverNameList.values.toList();
            });
          }
        } else {
          setState(() {
            driverNameData = [];
          });
        }
      }
    } else {
      setState(() {
        driverNameData = [];
      });
    }
  }

  getDriverDLList(String driverId) async {
    print("driverId....${driverId}");

    setState(() {
      driverRenewalData = [];
      driverRenewalType = '';
    });
    if (driverId != null) {
      var data = {
        'companyId': companyId,
        'driverId': driverId,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_DRIVER_RENEWAL_LIST, data, URI.LIVE_URI);

      if (response != null) {
        if (response["driverDocType"] != null) {
          print(response["driverDocType"].length);
          setState(() {
            driverRenewalData = response['driverDocType'];
            thresholdPeriodData = response['periodThreshold'];
            dlNumber.text = response['driver']['driver_dlnumber'];
          });
        } else {
          setState(() {
            driverRenewalData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          driverRenewalData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        driverRenewalData = [];
      });
    }
  }
}
