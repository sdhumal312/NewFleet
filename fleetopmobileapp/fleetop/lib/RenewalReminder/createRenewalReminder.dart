import 'dart:convert';
import 'dart:io';
import 'dart:math';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:file_picker/file_picker.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/NEW_KF_DRAWER/kf_drawer.dart';
import 'package:fleetop/PickAndDrop/showPickOrDropData.dart';
import 'package:fleetop/RenewalReminder/searchRenewalByVehicle.dart';
import 'package:fleetop/RenewalReminder/showRenewalReminder.dart';
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

import '../CustomAutoComplete.dart';
import '../fleetopuriconstant.dart';

class CreateRenewalReminder extends KFDrawerContent {
  CreateRenewalReminder({Key key});

  @override
  _CreateRenewalReminderState createState() => _CreateRenewalReminderState();
}

class _CreateRenewalReminderState extends State<CreateRenewalReminder>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String vehicleId = '';
  String vendorId = '';
  String paidById = '';
  String tallyCompanyId;
  var selectedRenewalType = '';
  var selectedRenewalSubType = '';
  var thresholdPeriodType = '';
  var paymentTpe = '';

  Map pickOrDropDetails = Map();
  List vehicleData = List();
  Map vehicleList = Map();
  Map otherVendorList = Map();
  List otherVendorData = List();
  Map tallyServiceList = Map();
  List tallyServiceData = List();
  Map mandatoryList = Map();
  List mandatoryData = List();

  List renewalTypeData = List();
  List renewalSubTypeData = List();
  List thresholdPeriodData = List();
  List paymentTypeData = List();

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
  bool showTally = false;

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("H:mm");

  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  bool showImage = false;

  TextEditingController vehicleName = new TextEditingController();
  TextEditingController validityFrom = new TextEditingController();
  TextEditingController validityTo = new TextEditingController();
  TextEditingController dueThreshold = new TextEditingController();
  TextEditingController receiptNo = new TextEditingController();
  TextEditingController draftAmount = new TextEditingController();
  TextEditingController otherVendor = new TextEditingController();
  TextEditingController cashTransactionNumber = new TextEditingController();
  TextEditingController paidDate = new TextEditingController();
  TextEditingController paidBy = new TextEditingController();
  TextEditingController imageUpload = new TextEditingController();
  TextEditingController authorizationStates = new TextEditingController();
  TextEditingController remarks = new TextEditingController();
  TextEditingController renewalSearch = new TextEditingController();
  TextEditingController tallyCompany = new TextEditingController();
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

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    var data = {'companyId': companyId, 'userId': userId, 'email': email};
    var response = await ApiCall.getDataFromApi(
        URI.GET_RR_CONFIG_DATA, data, URI.LIVE_URI, context);

    print(response);

    if (response != null) {
      configuration = response['configuration'];

      setState(() {
        renewalTypeData = response['renewalTypeList'];
        thresholdPeriodData = response['periodThreshold'];
        paymentTypeData = response['PaymentType'];
        paidBy.text = response['userName'];
        paidById = response['userId'].toString();

        showDueThreshold = configuration['showDueThreshold'];
        showDueThresholdPeriod = configuration['showDueThresholdPeriod'];
        showReceiptNumber = configuration['receiptnumbershow'];
        showCashTranNumber = configuration['cashtransactionshow'];
        showPaidDate = configuration['paidDateshow'];
        showPaidBy = configuration['paidbyshow'];
        showStateAuthorization = configuration['optionalInformation'];
        showRemark = configuration['optionalInformation'];
        showVendorCol = configuration['showVendorCol'];
        showTally = configuration['tallyIntegrationRequired'];

        print("showDueThresholdPeriod...........${showDueThresholdPeriod}");
      });
    }
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
      var renewalDetails = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vehicleId': vehicleId,
        'renewalTypeId': selectedRenewalType,
        'renewalSubTypeId': selectedRenewalSubType,
        'validityFrom': validityFrom.text,
        'validityTo': validityTo.text,
        'timeThreshold': dueThreshold.text,
        'renewalPeriodThreshold': thresholdPeriodType,
        'renewalReceiptNo': receiptNo.text,
        'renewalAmount': draftAmount.text,
        'vendorId': vendorId,
        'tallyCompanyId': tallyCompanyId,
        'paymentTypeId': paymentTpe,
        'renewalPayNo': cashTransactionNumber.text,
        'paidDate': paidDate.text,
        'renewalPaidById': paidById,
        'renewalAuthorization': authorizationStates.text,
        'remark': remarks.text,
        'base64String': imageData,
        'imageName': imageName,
        'imageExt': imageExt,
      };

      //print("renewalDetails....${renewalDetails}");

      final jsonEncoder = JsonEncoder();
      var finalrenewalData = {
        'renewalData': jsonEncoder.convert(renewalDetails)
      };

      print("finalrenewalData...${finalrenewalData}");

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_RENEWAL_REMINDER, finalrenewalData, URI.LIVE_URI, context);

      print("output.....${data}");

      if (data != null) {
        if (data['sequenceNotFound'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (data['renewalRemindeAlready'] == true) {
          FlutterAlert.onInfoAlert(
              context,
              "Renewal Reminder Already Exists Hence Cannot Create New RR",
              "Info");
        } else if (data['renewalReceiptAlready'] == true) {
          FlutterAlert.onInfoAlert(
              context,
              "Renewal Reminder Compliance Issue Hence Cannot Create New RR",
              "Info");
        } else if (data['documentIsCompulsory'] == true) {
          FlutterAlert.onInfoAlert(
              context,
              "Renewal Reminder cannot be created. Upload of Document is Mandatory",
              "Info");
        } else if (data['saveRenewalReminder'] == true) {
          refreshData();
          print("Id......${data['renewalId']}");
          redirectToDisplay(data['renewalId']);
        } else {
          FlutterAlert.onInfoAlert(context,
              "Renewal Not Created, Please contact on Support !", "Info");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool fieldValidation() {
    if (vehicleId == '' || vehicleId == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Valid Vehicle !", "Error");
      return false;
    }

    if (selectedRenewalType == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Renewal Type !", "Error");
      return false;
    }

    if (selectedRenewalSubType == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Renewal Sub Type !", "Error");
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

    if (showDueThreshold) {
      if (dueThreshold.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Due Threshold !", "Error");
        return false;
      }
    }

    if (showDueThresholdPeriod) {
      if (thresholdPeriodType == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Threshold Period Type !", "Error");
        return false;
      }
    }

    if (showReceiptNumber) {
      if (receiptNo.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Receipt No !", "Error");
        return false;
      }
    }

    if (draftAmount.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Amount !", "Error");
      return false;
    }

    if (showVendorCol) {
      if (vendorId == '' || vendorId == '0') {
        FlutterAlert.onErrorAlert(context, "Please Select Vendor !", "Error");
        return false;
      }
    }

    if (paymentTpe == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Select payment Type !", "Error");
      return false;
    }

    if (showPaidDate) {
      if (paidDate.text == '') {
        FlutterAlert.onErrorAlert(
            context, "Please Select Paid Date !", "Error");
        return false;
      }
    }

    if (showTally == true) {
      if (tallyCompanyId == '' || tallyCompanyId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Tally Company !", "Error");
        return false;
      }
    }

    return true;
  }

  refreshData() {
    vehicleId = '';
    vehicleName.text = '';
    selectedRenewalType = '';
    selectedRenewalSubType = '';
    validityFrom.text = '';
    validityTo.text = '';
    dueThreshold.text = '';
    thresholdPeriodType = '';
    receiptNo.text = '';
    draftAmount.text = '';
    vendorId = '';
    otherVendor.text = '';
    paymentTpe = '';
    cashTransactionNumber.text = '';
    paidDate.text = '';
    paidBy.text = '';
    paidById = '';
    imageData = '';
    authorizationStates.text = '';
    remarks.text = '';
    showImage = false;
    tallyCompanyId = '';
    tallyCompany.text = '';
  }

  Future<bool> redirectToDisplay(renewalId) async => Alert(
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
            onPressed: () => getPickAndDropData(renewalId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  getPickAndDropData(int renewalReminderId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) =>
              ShowRenewalReminder(renewalId: renewalReminderId)),
    );
  }

  searchRenewalByNumber(String rrNumber) async {
    print("rrNumber...${rrNumber}");

    if (rrNumber != '') {
      var data = {
        'companyId': companyId,
        'renewalNumber': rrNumber,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.SEARCH_RR_BY_NUMBER, data, URI.LIVE_URI);
      print("response........${response}");
      if (response != null) {
        if (response['renewalFound'] == true) {
          int renewalRemId = response['renewalId'];
          print("renewalRemId........${renewalRemId}");
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) =>
                    ShowRenewalReminder(renewalId: renewalRemId)),
          );
        } else {
          FlutterAlert.onErrorAlert(
              context, "Please Enter Valid Renewal Number !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Please Enter Valid Renewal Number !", "Error");
      }
    } else {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Valid Renewal Number !", "Error");
    }
  }

  Widget appBarTitle = new Text("Renewal Reminder");
  Icon actionIcon = new Icon(Icons.search);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.redAccent,
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
                      keyboardType: TextInputType.number,
                      decoration: new InputDecoration(
                        labelText: 'RR - Number',
                        labelStyle: TextStyle(color: Colors.white),
                        icon: new RaisedButton(
                          padding: const EdgeInsets.all(2.0),
                          textColor: Colors.white,
                          color: Colors.amber,
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
                    this.appBarTitle = new Text("Renewal Reminder");
                  }
                });
              },
            ),
          ]),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          _settingModalBottomSheet(context);
        },
        child: new Icon(Icons.add),
      ),
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
                                    Container(
                                      child: Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10),
                                        child: Column(
                                          children: <Widget>[
                                            SizedBox(
                                              height: 10.0,
                                            ),
                                            Align(
                                              alignment: Alignment.centerRight,
                                              child: Container(
                                                child: Icon(Icons.stars,
                                                    color: Colors.red,
                                                    size: 12),
                                              ),
                                            ),
                                            TypeAheadField(
                                              hideSuggestionsOnKeyboardHide:
                                                  false,
                                              hideOnEmpty: true,
                                              textFieldConfiguration:
                                                  TextFieldConfiguration(
                                                controller: vehicleName,
                                                keyboardType:
                                                    TextInputType.number,
                                                decoration: InputDecoration(
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.directions_bus,
                                                      color: Colors.black,
                                                    ),
                                                    hintText: 'Vehicle Number',
                                                    labelText:
                                                        'Vehicle Number'),
                                              ),
                                              suggestionsCallback:
                                                  (pattern) async {
                                                return await getVehicleNumberSuggestions(
                                                    pattern);
                                              },
                                              itemBuilder:
                                                  (context, suggestion) {
                                                return ListTile(
                                                  leading: Icon(
                                                      Icons.local_shipping),
                                                  title: Text(suggestion[
                                                      'vehicleName']),
                                                  // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                );
                                              },
                                              onSuggestionSelected:
                                                  (suggestion) {
                                                vehicleName.text =
                                                    suggestion['vehicleName'];
                                                vehicleId =
                                                    suggestion['vehicleId'];
                                                getMandatoryList(vehicleId);
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height:
                                            mandatoryData.length > 0 ? 15 : 0),
                                    Visibility(
                                        visible: mandatoryData.length > 0
                                            ? true
                                            : false,
                                        child: Container(
                                          child: Align(
                                            alignment: Alignment.center,
                                            child: Text(
                                                "List Of Pending Mandatory Renewals",
                                                style: new TextStyle(
                                                  color: Colors.purple,
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 15,
                                                )),
                                          ),
                                        )),
                                    SizedBox(
                                        height:
                                            mandatoryData.length > 0 ? 5 : 0),
                                    Visibility(
                                      visible: mandatoryData.length > 0,
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
                                                            "Mandatory Renewals"),
                                                        value: selectedRenewalType !=
                                                                ''
                                                            ? selectedRenewalType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedRenewalType =
                                                                newValue;
                                                          });
                                                        },
                                                        items: mandatoryData
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'mandatoryName'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'mandatoryTypeId']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: renewalTypeData.length > 0
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: renewalTypeData.length > 0,
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
                                                        value: selectedRenewalType !=
                                                                ''
                                                            ? selectedRenewalType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedRenewalType =
                                                                newValue;
                                                          });
                                                          getRenewalSubTypeList(
                                                              selectedRenewalType);
                                                        },
                                                        items: renewalTypeData
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'renewal_Type'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'renewal_id']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: renewalSubTypeData.length > 0
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: renewalSubTypeData.length > 0,
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
                                                            "Select Renewal Sub Type"),
                                                        value: selectedRenewalSubType !=
                                                                ''
                                                            ? selectedRenewalSubType
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            selectedRenewalSubType =
                                                                newValue;
                                                          });
                                                        },
                                                        items:
                                                            renewalSubTypeData
                                                                .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'renewal_SubType'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'renewal_Subid']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
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
                                    SizedBox(
                                        height:
                                            showDueThreshold == true ? 5 : 0),
                                    Visibility(
                                        visible: showDueThreshold,
                                        child: Align(
                                          alignment: Alignment.centerRight,
                                          child: Container(
                                            child: Icon(Icons.stars,
                                                color: Colors.red, size: 12),
                                          ),
                                        )),
                                    Visibility(
                                        visible: showDueThreshold,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                keyboardType:
                                                    TextInputType.number,
                                                maxLines: 1,
                                                controller: dueThreshold,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    labelText: 'Due Threshold',
                                                    hintText: 'Due Threshold',
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.rate_review,
                                                      color: Colors.greenAccent,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: thresholdPeriodData.length > 0
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: thresholdPeriodData.length > 0 &&
                                          showDueThresholdPeriod,
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
                                    SizedBox(
                                        height:
                                            showReceiptNumber == true ? 10 : 0),
                                    Visibility(
                                        visible: showReceiptNumber,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                keyboardType:
                                                    TextInputType.number,
                                                maxLines: 1,
                                                controller: receiptNo,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    labelText: 'Receipt No',
                                                    hintText: 'Receipt No',
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.confirmation_number,
                                                      color: Colors.pink,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 10),
                                    Container(
                                      child: Padding(
                                        padding: const EdgeInsets.only(
                                            top: 5.0, left: 10),
                                        child: Container(
                                          child: TextField(
                                            keyboardType: TextInputType.number,
                                            maxLines: 1,
                                            controller: draftAmount,
                                            style:
                                                TextStyle(color: Colors.black),
                                            decoration: InputDecoration(
                                                labelText: 'Draft Amount',
                                                hintText: 'Draft Amount',
                                                hintStyle: TextStyle(
                                                    color: Colors.black),
                                                border: OutlineInputBorder(
                                                    borderRadius:
                                                        BorderRadius.circular(
                                                            5.0)),
                                                icon: Icon(
                                                  Icons.attach_money,
                                                  color: Colors.brown,
                                                )),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: showVendorCol == true ? 10 : 0),
                                    Visibility(
                                        visible: showVendorCol,
                                        child: Container(
                                          child: Padding(
                                            padding:
                                                const EdgeInsets.only(left: 10),
                                            child: Column(
                                              children: <Widget>[
                                                SizedBox(
                                                  height: 5.0,
                                                ),
                                                TypeAheadField(
                                                  hideSuggestionsOnKeyboardHide:
                                                      false,
                                                  hideOnEmpty: true,
                                                  textFieldConfiguration:
                                                      TextFieldConfiguration(
                                                    controller: otherVendor,
                                                    decoration: InputDecoration(
                                                        border: OutlineInputBorder(
                                                            borderRadius:
                                                                BorderRadius
                                                                    .circular(
                                                                        5.0)),
                                                        icon: Icon(
                                                          Icons.directions_bus,
                                                          color: Colors.black,
                                                        ),
                                                        hintText: 'Vendor',
                                                        labelText: 'Vendor'),
                                                  ),
                                                  suggestionsCallback:
                                                      (pattern) async {
                                                    return await getotherVendorSuggestions(
                                                        pattern);
                                                  },
                                                  itemBuilder:
                                                      (context, suggestion) {
                                                    return ListTile(
                                                      leading:
                                                          Icon(Icons.person),
                                                      title: Text(suggestion[
                                                          'vendorName']),
                                                      // subtitle: Text('\$${suggestion['vehicleId']}'),
                                                    );
                                                  },
                                                  onSuggestionSelected:
                                                      (suggestion) {
                                                    otherVendor.text =
                                                        suggestion[
                                                            'vendorName'];
                                                    setState(() {
                                                      vendorId = suggestion[
                                                          'vendorId'];
                                                    });
                                                  },
                                                ),
                                              ],
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: paymentTypeData.length > 0
                                            ? 15
                                            : 0),
                                    Visibility(
                                      visible: paymentTypeData.length > 0,
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
                                                            "Select Payment Type "),
                                                        value: paymentTpe != ''
                                                            ? paymentTpe
                                                            : null,
                                                        isExpanded: true,
                                                        onChanged:
                                                            (String newValue) {
                                                          setState(() {
                                                            paymentTpe =
                                                                newValue;
                                                          });
                                                        },
                                                        items: paymentTypeData
                                                            .map((item) {
                                                          return new DropdownMenuItem(
                                                            child: new Text(
                                                                item[
                                                                    'paymentTypeName'],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black,
                                                                    fontSize:
                                                                        15.0,
                                                                    fontFamily:
                                                                        "WorkSansBold")),
                                                            value: item[
                                                                    'paymentTypeId']
                                                                .toString(),
                                                          );
                                                        }).toList(),
                                                      ))),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                    SizedBox(
                                        height: showCashTranNumber == true
                                            ? 10
                                            : 0),
                                    Visibility(
                                        visible: showCashTranNumber,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                keyboardType:
                                                    TextInputType.number,
                                                maxLines: 1,
                                                controller:
                                                    cashTransactionNumber,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    labelText:
                                                        'Cash Transaction Number',
                                                    hintText:
                                                        'Cash Transaction Number',
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.credit_card,
                                                      color: Colors.purple,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: showPaidDate == true ? 10 : 0),
                                    Visibility(
                                        visible: showPaidDate,
                                        child: Align(
                                          alignment: Alignment.centerRight,
                                          child: Container(
                                            child: Icon(Icons.stars,
                                                color: Colors.red, size: 12),
                                          ),
                                        )),
                                    Visibility(
                                        visible: showPaidDate,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: DateTimeField(
                                                format: format,
                                                controller: paidDate,
                                                readOnly: true,
                                                initialValue: DateTime.now(),
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText: "Paid Date",
                                                    labelText: "Paid Date",
                                                    hintStyle: TextStyle(
                                                        color:
                                                            Color(0xff493240)),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
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
                                                      initialDate:
                                                          currentValue ??
                                                              DateTime.now(),
                                                      lastDate: DateTime.now());
                                                },
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: showPaidBy == true ? 10 : 0),
                                    Visibility(
                                        visible: showPaidBy,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                readOnly: true,
                                                keyboardType:
                                                    TextInputType.number,
                                                maxLines: 1,
                                                controller: paidBy,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText: "Paid By",
                                                    labelText: "Paid By",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.people,
                                                      color: Colors.blueGrey,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: showTally == true ? 15 : 0),
                                    Visibility(
                                        visible: showTally,
                                        child: Container(
                                          child: CustomAutoComplete(
                                              suggestionList: tallyServiceData,
                                              controller: tallyCompany,
                                              hintLabel: 'Tally Company',
                                              label: 'Tally Company',
                                              dataKeyName: 'tallyName',
                                              iconData: Icons.directions,
                                              onItemSelected: (suggestion) {
                                                setState(() {
                                                  tallyCompany.text =
                                                      suggestion['tallyName'];
                                                  tallyCompanyId =
                                                      suggestion['tallyID'];
                                                });
                                              },
                                              onChanged: (pattern) {
                                                //method for getting data from server
                                                getTallyCompanyDetails(pattern);
                                              }),
                                        )),
                                    SizedBox(height: 15),
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
                                                  color: Colors.purple,
                                                  onPressed: openBottomSheet,
                                                )
                                              ])),
                                    ),
                                    SizedBox(height: 10),
                                    Visibility(
                                      visible: showImage,
                                      child: showselectedImage(),
                                    ),
                                    SizedBox(
                                        height: showStateAuthorization == true
                                            ? 15
                                            : 0),
                                    Visibility(
                                        visible: showStateAuthorization,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                maxLines: 1,
                                                controller: authorizationStates,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText:
                                                        "State Authorization",
                                                    labelText:
                                                        "State Authorization",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.location_city,
                                                      color: Colors.amber,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(
                                        height: showRemark == true ? 10 : 0),
                                    Visibility(
                                        visible: showRemark,
                                        child: Container(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: 5.0, left: 10),
                                            child: Container(
                                              child: TextField(
                                                maxLines: 1,
                                                controller: remarks,
                                                style: TextStyle(
                                                    color: Colors.black),
                                                decoration: InputDecoration(
                                                    hintText: "Remark",
                                                    labelText: "Remark",
                                                    hintStyle: TextStyle(
                                                        color: Colors.black),
                                                    border: OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(5.0)),
                                                    icon: Icon(
                                                      Icons.comment,
                                                      color: Colors.green,
                                                    )),
                                              ),
                                            ),
                                          ),
                                        )),
                                    SizedBox(height: 15),
                                    Center(
                                      child: Container(
                                          height: 50,
                                          width: _width - 100,
                                          margin: EdgeInsets.only(
                                              top: 20.0, left: 10),
                                          decoration: new BoxDecoration(
                                            color: Colors.redAccent,
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(5.0)),
                                            //   gradient: new LinearGradient(
                                            //       colors: [
                                            //         Colors.cyanAccent,
                                            //         Colors.blueAccent,
                                            //       ],
                                            //       begin: const FractionalOffset(
                                            //           0.2, 0.2),
                                            //       end: const FractionalOffset(
                                            //           1.0, 1.0),
                                            //       stops: [0.0, 1.0],
                                            //       tileMode: TileMode.clamp),
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

  Future<List> getVehicleNumberSuggestions(String query) async {
    getVehicleDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(vehicleData.length, (index) {
      return vehicleData[index];
    });
  }

  getVehicleDetails(String str) async {
    vehicleList = new Map();
    //fuelEntryTime.text = '';
    setState(() {
      vehicleData = [];
    });
    if (str != null && str.length >= 1) {
      var data = {'companyId': companyId, 'term': str, 'userId': userId};
      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_VEHICLE_DETAILS, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vehicleList"] != null) {
          print(response["vehicleList"].length);
          for (int i = 0; i < response["vehicleList"].length; i++) {
            var obj = {
              "vehicleId": response['vehicleList'][i]['vid'].toString(),
              "vehicleName":
                  response['vehicleList'][i]['vehicle_registration'].toString()
            };
            vehicleList[obj['vehicleId']] = obj;
            setState(() {
              vehicleData = vehicleList.values.toList();
            });
          }
        } else {
          setState(() {
            vehicleData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          vehicleData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        vehicleData = [];
      });
    }
  }

  getRenewalSubTypeList(String renewalId) async {
    print("renewalId....${renewalId}");

    setState(() {
      renewalSubTypeData = [];
      selectedRenewalSubType = '';
    });
    if (renewalId != null) {
      var data = {
        'companyId': companyId,
        'renewalId': renewalId,
        'userId': userId
      };

      var response = await ApiCall.getDataWithoutLoader(
          URI.GET_RR_SUBTYPE_LIST, data, URI.LIVE_URI);
      print(response["renewalSubTypeList"]);
      if (response != null) {
        if (response["renewalSubTypeList"] != null) {
          print(response["renewalSubTypeList"].length);
          setState(() {
            renewalSubTypeData = response['renewalSubTypeList'];
          });
        } else {
          setState(() {
            renewalSubTypeData = [];
          });
          FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
        }
      } else {
        setState(() {
          renewalSubTypeData = [];
        });
        FlutterAlert.onInfoAlert(context, "No Record Found", "Info");
      }
    } else {
      setState(() {
        renewalSubTypeData = [];
      });
    }
  }

  Future<List> getotherVendorSuggestions(String query) async {
    getOtherVendorDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(otherVendorData.length, (index) {
      return otherVendorData[index];
    });
  }

  getOtherVendorDetails(String str) async {
    otherVendorList = new Map();
    setState(() {
      otherVendorData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.OTHER_VENDOR_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["otherVendorList"] != null) {
          print(response["otherVendorList"].length);
          for (int i = 0; i < response["otherVendorList"].length; i++) {
            var obj = {
              "vendorId": response['otherVendorList'][i]['vendorId'].toString(),
              "vendorName":
                  response['otherVendorList'][i]['vendorName'].toString()
            };
            otherVendorList[obj['vendorId']] = obj;
            setState(() {
              otherVendorData = otherVendorList.values.toList();
            });
          }
        } else {
          setState(() {
            otherVendorData = [];
          });
        }
      }
    } else {
      setState(() {
        otherVendorData = [];
      });
    }
  }

  getTallyCompanyDetails(String str) async {
    tallyServiceList = new Map();
    tallyCompanyId = '';
    setState(() {
      tallyServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TALLY_COMPANY_LIST, data, URI.LIVE_URI);

      if (response != null) {
        print("tally...${response["TallyCmpnyList"]}");
        if (response["TallyCmpnyList"] != null) {
          print(response["TallyCmpnyList"].length);
          for (int i = 0; i < response["TallyCmpnyList"].length; i++) {
            var obj = {
              "tallyID":
                  response['TallyCmpnyList'][i]['tallyCompanyId'].toString(),
              "tallyName":
                  response['TallyCmpnyList'][i]['companyName'].toString()
            };
            tallyServiceList[obj['tallyID']] = obj;
            setState(() {
              tallyServiceData = tallyServiceList.values.toList();
            });
          }
        } else {
          setState(() {
            tallyServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        tallyServiceData = [];
      });
    }
  }

  Future getMandatoryList(String id) async {
    print("id..${id}");
    setState(() {
      mandatoryData = [];
    });
    var data = {'companyId': companyId, 'vid': id};

    var response = await ApiCall.getDataFromApi(
        URI.GET_MANDATORY_RENEWAL_LIST, data, URI.LIVE_URI, context);

    print("response..${response}");

    if (response != null) {
      if (response["mandatoryList"] != null) {
        print(response["mandatoryList"].length);
        for (int i = 0; i < response["mandatoryList"].length; i++) {
          var obj = {
            "mandatoryTypeId":
                response['mandatoryList'][i]['renewalTypeId'].toString(),
            "mandatorySubTypeId":
                response['mandatoryList'][i]['renewal_Subid'].toString(),
            "mandatoryName":
                response['mandatoryList'][i]['renewal_type'].toString() +
                    '-' +
                    response['mandatoryList'][i]['renewal_subtype'].toString()
          };
          mandatoryList[obj['mandatoryTypeId']] = obj;
          setState(() {
            mandatoryData = mandatoryList.values.toList();
          });
        }
      } else {
        setState(() {
          mandatoryData = [];
        });
      }
    }
  }

  void _settingModalBottomSheet(context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return ListView(
            children: <Widget>[
              Card(
                elevation: 5,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.only(
                      bottomRight: Radius.circular(30),
                      topRight: Radius.circular(30)),
                  //side: BorderSide(width: 5, color: Colors.green)
                ),
                child: ListTile(
                  trailing: Icon(Icons.keyboard_arrow_right),
                  onTap: () => {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => SearchRenewalByVehicle()),
                    )
                  },
                  leading: new Icon(
                    Icons.alarm,
                    color: Colors.lightBlueAccent,
                  ),
                  title: Text(
                    "Search Renewal Reminder By Vehicle",
                    style: new TextStyle(
                      fontSize: 18,
                      color: Colors.black,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ],
          );
        });
  }
}
