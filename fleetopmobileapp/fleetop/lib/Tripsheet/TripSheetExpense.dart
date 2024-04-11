import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

//import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'TripsheetShow.dart';

class TripSheetExpense extends StatefulWidget {

  final Function tripsheetShowData;

  TripSheetExpense({this.tripsheetShowData, this.tripsheetId});
  final int tripsheetId;

  @override
  _TripSheetExpenseState createState() => _TripSheetExpenseState();
}

class _TripSheetExpenseState extends State<TripSheetExpense> {
  bool showTripDetails = false;
  bool showExpenseDetails = false;
  bool showAdvanceDriver = false;
  Map configuration;
  String companyId;
  String email;
  String userId;
  int i = 0;
  int tripsheetId;
  String createdDate;
  String tripsheetNumber;
  String vehNumber;
  String route;
  String tripOpenDate;
  String tripCloseDate;
  String vehicle_Group;
  String tripBookref;
  String routeAttendancePoint;
  String routeTotalLiter;
  String tripFristDriverName;
  String tripFristDriverMobile;
  String tripSecDriverName;
  String tripSecDriverMobile;
  String tripCleanerName;
  String tripCleanerMobile;
  String tripOpeningKM;
  String tripClosingKM;
  String dispatchedBy;
  String dispatchedLocation;
  String dispatchedByTime;
  String driverAdvanceId;
  String expenseNameId;
  var defaultSelectedExpense = "0";

  String tripUsageKM;
  String tripFristDriverRoutePoint;
  String tripSecDriverRoutePoint;
  String tripCleanerRoutePoint;
  String closedBy;
  String cloesdLocation;
  String closedByTime;
  String loadType;
  String totalPOD;
  String Remark;
  String vendorId = '';
  bool checkedValue = false;
  bool showCreditAndVendorAtExpense = false;

  TextEditingController otherVendor = new TextEditingController();
  TextEditingController expenseName = new TextEditingController();
  TextEditingController expenseAmount = new TextEditingController();
  TextEditingController expenseRef = new TextEditingController();
  TextEditingController remark = new TextEditingController();

  Map tripList = Map();
  List expenseList = List();
  List expenseDropDownList = List();
  List expenseDropDownData = List();
  Map otherVendorList = Map();
  List otherVendorData = List();

  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  bool showImage = false;

  String base64ImageString;
  bool imageState = false;
  bool imageAvailable = false;

  Uint8List dataFromBase64String(String base64String) {
    return base64Decode(base64String);
  }

  Future<void> _shareImage(String base64ImageString) async {
    try {
      Uint8List uni = dataFromBase64String(base64ImageString);
      await Share.file(
          'esys image', 'esys.png', uni, 'image/png');
    } catch (e) {
      print('error: $e');
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
                  : Image.asset("assets/img/signUp.png", fit: BoxFit.fill),
            ))));
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

  @override
  void initState() {
    super.initState();
    getTripsheetShowData(widget.tripsheetId);
  }

  getTripsheetShowData(int tripId) async {
    print("data is  = $tripId");

    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {'companyId': companyId, 'userId' : userId, 'email' : email, 'tripsheetId' : tripId.toString()};
    var response = await ApiCall.getDataFromApi(
        URI.ADD_EXPENSE_TRIPSHEET, data, URI.LIVE_URI, context);

    tripList = response['TripSheet'];
    expenseList = response['TripSheetExpense'];
    configuration = response['configuration'];

    setState(() {
      tripsheetId = tripId;
      createdDate = tripList['created'];
      tripsheetNumber = tripList['tripSheetNumber'].toString();
      vehNumber = tripList['vehicle_registration'];
      route = tripList['routeName'];
      tripOpenDate = tripList['tripOpenDate'];
      tripCloseDate = tripList['closetripDate'];
      vehicle_Group = tripList['vehicle_Group'];
      tripBookref = tripList['tripBookref'];
      routeAttendancePoint = tripList['routeAttendancePoint'].toString();
      routeTotalLiter = tripList['routeTotalLiter'].toString();
      tripFristDriverName = tripList['tripFristDriverName'];
      tripFristDriverMobile = tripList['tripFristDriverMobile'];
      tripSecDriverName = tripList['tripSecDriverName'];
      tripSecDriverMobile = tripList['tripSecDriverMobile'];
      tripCleanerName = tripList['tripCleanerName'];
      tripCleanerMobile = tripList['tripCleanerMobile'];
      tripOpeningKM = tripList['tripOpeningKM'].toString();
      tripClosingKM = tripList['tripClosingKM'].toString();
      dispatchedBy = tripList['dispatchedBy'];
      dispatchedLocation = tripList['dispatchedLocation'];
      dispatchedByTime = tripList['dispatchedByTime'];
      showAdvanceDriver = configuration['showAdvanceDriver'];
      tripUsageKM = tripList['tripUsageKM'].toString();
      tripFristDriverRoutePoint = tripList['tripFristDriverRoutePoint'].toString();
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadType = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];
      showCreditAndVendorAtExpense = configuration['showCreditAndVendorAtExpense'];
      print("credit...${showCreditAndVendorAtExpense}");
    });

    getTripExpenseList();

  }

  Future addExpense() async {
    if (!fieldValidation()) {
      return;
    } else {

      var expenseData;

      if(showCreditAndVendorAtExpense){

        String creditValue = "false";
        if(checkedValue){
          creditValue = "true";
        }

        if(imageData != null){
          expenseData = {
          'email': email,
          'userId': userId,
          'companyId': companyId,
          'tripsheetId': tripsheetId.toString(),
          'expenseNameId': defaultSelectedExpense,
          'expenseAmount': expenseAmount.text,
          'expenseRef': expenseRef.text,
          'Credit': creditValue,
          'vendorId': vendorId,
          'description': remark.text,
          'tripExpenseDocument':'file',
          'base64String': imageData,
          'imageName': imageName,
          'imageExt': imageExt, 
          };
        } else {
          expenseData = {
          'email': email,
          'userId': userId,
          'companyId': companyId,
          'tripsheetId': tripsheetId.toString(),
          'expenseNameId': defaultSelectedExpense,
          'expenseAmount': expenseAmount.text,
          'expenseRef': expenseRef.text,
          'Credit': creditValue,
          'vendorId': vendorId,
          'description': remark.text,
          };
        }

      } else {
        expenseData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripsheetId': tripsheetId.toString(),
        'expenseNameId': defaultSelectedExpense,
        'expenseAmount': expenseAmount.text,
        'expenseRef': expenseRef.text,
        };
      }

      print("expenseDatasss...${expenseData}");

      var response = await ApiCall.getDataFromApi(
          URI.SAVE_EXPENSE_TRIPSHEET, expenseData, URI.LIVE_URI, context);

      if(response != null){
        if(response['alreadyExpense'] != null && response['alreadyExpense'] == true){
          FlutterAlert.onErrorAlert(context, "Expense Already Added !", "Error");
        } else {
          FlutterAlert.onSuccessAlert(context, " Tripsheet Expense Added Successfully !", " Tripsheet Expense ");
        }
      }
      refreshData();
      getTripsheetShowData(tripsheetId);
    }

  }

  bool fieldValidation() {

    if (defaultSelectedExpense == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Expense Name !", "Error");
      return false;
    }

    if (expenseAmount.text == '' || expenseAmount.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Select Expense Amount Greater Than 0 !", "Error");
      return false;
    }

    if(showCreditAndVendorAtExpense){
      if(checkedValue){
        if (vendorId == '' || vendorId == '0') {
          FlutterAlert.onErrorAlert(
              context, "Please Select Vendor", "Error");
          return false;
        }
      }
    }

    return true;
  }

  refreshData() {
    expenseNameId = '';
    expenseAmount.text = '';
    expenseRef.text = '';
    checkedValue = false;
    vendorId = '';
    remark.text = '';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Add Expense",
          style: new TextStyle(
            fontSize: 22,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () =>
          {
           /* Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetShow (tripsheetId :tripsheetId)))*/
            Navigator.pop(context),
            Navigator.pop(context),
            widget.tripsheetShowData()
          },
        ),
      ),


      backgroundColor: AppTheme.white,
      body: Container(
          child: SingleChildScrollView(
              child: Center(
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[

                        SizedBox(height: 5),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Created Date : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),

                              new TextSpan(
                                  text: " ${createdDate} ",
                                  style: new TextStyle(
                                    color: Colors.blue,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 17,
                                  )
                              ),
                            ],
                          ),
                        ),
                        /*Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Created Date : ${createdDate} ",
                                style: TextStyle(fontWeight: FontWeight.bold)
                            ),
                          ),
                        ),*/

                        SizedBox(height: 25),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Trip Number : TS - ${tripsheetNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.red, fontSize: 19)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${vehNumber}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 5),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "${route}",
                                style: TextStyle(fontWeight: FontWeight.w700, color: Colors.green, fontSize: 15)
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Card(
                          color: Colors.blue,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Tripsheet Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showTripDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                                        onPressed: () => {
                                          setState(() {
                                            showTripDetails =
                                            !showTripDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showTripDetails,
                            child:Stack(
                              children: <Widget>[
                                showTripInfo(context),
                                /*ola(context),*/
                              ],
                            )
                        ),


                        SizedBox(height: 15),
                        Card(
                          color: Colors.pink,
                          child: Container(
                            height: 50,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10, right: 10),
                                child: Row(
                                    mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        "Expense Details ",
                                        style: new TextStyle(
                                          fontSize: 18,
                                          color: Colors.white,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),

                                      DialogButton(
                                        width: 95,
                                        height: 33,
                                        color: Colors.white,
                                        child: Text(
                                          showExpenseDetails ? "Close" : "Open",
                                          style: TextStyle(
                                              color: Colors.purpleAccent,
                                              fontSize: 20,
                                              fontWeight:FontWeight.w500
                                          ),
                                        ),
                                        /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                                        onPressed: () => {
                                          setState(() {
                                            showExpenseDetails =
                                            !showExpenseDetails;
                                          })
                                        },
                                      )
                                    ])),
                          ),
                        ),
                        Visibility(
                            visible: showExpenseDetails,
                            child:Column(
                              children: <Widget>[

                                if(expenseList != null)
                                  for(int i = 0;i<expenseList.length;i++)
                                    showExpensePaymentDetails(expenseList[i],context),

                              ],
                            )
                        ),
//

                        SizedBox(height: 25),
                        Container(
                          child: Align(
                            alignment: Alignment.center,
                            child: Text(
                                "Add Expense Details ",
                                style: new TextStyle(
                                  color: Colors.blueAccent,
                                  fontWeight: FontWeight.w700,
                                  fontSize: 15,
                                )
                            ),
                          ),
                        ),
                        Container(
                          height: 2.0,
                          width: MediaQuery.of(context).size.width,
                          color: Colors.blueAccent,
                          margin: const EdgeInsets.only(left: 50.0, right: 50.0),
                        ),

                        SizedBox(height:
                                showCreditAndVendorAtExpense == true ? 15 : 0),
                        Visibility(
                            visible: showCreditAndVendorAtExpense,
                            child:CheckboxListTile(
                          title: Text("Is Credit"),
                          value: checkedValue,
                          onChanged: (newValue) { 
                                      setState(() {
                                        checkedValue = newValue; 
                                      }); 
                                    },
                          controlAffinity: ListTileControlAffinity.leading,  //  <-- leading Checkbox
                        )),

                        SizedBox(height:
                                showCreditAndVendorAtExpense == true ? 10 : 0),
                        Visibility(
                            visible: showCreditAndVendorAtExpense,
                            child:Container(
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
                                                    .circular(5.0)),
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
                                      leading: Icon(Icons.person),
                                      title: Text(
                                          suggestion['vendorName']),
                                      // subtitle: Text('\$${suggestion['vehicleId']}'),
                                    );
                                  },
                                  onSuggestionSelected:
                                      (suggestion) {
                                    otherVendor.text =
                                        suggestion['vendorName'];
                                    setState(() {
                                      vendorId =
                                          suggestion['vendorId'];
                                    });
                                  },
                                ),
                              ],
                            ),
                          ),
                        )),

                        SizedBox(height: 10),
                        Container(
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
                                              "Expense Name"),
                                          value:
                                          defaultSelectedExpense,
                                          isExpanded: true,
                                          onChanged:
                                              (String newValue) {
                                            setState(() {
                                              defaultSelectedExpense =
                                                  newValue;
                                            });
                                          },
                                          items: expenseDropDownData
                                              .map((item) {
                                            return new DropdownMenuItem(
                                              child: new Text(
                                                  item[
                                                  'expenseName'],
                                                  style: TextStyle(
                                                      color: Colors
                                                          .black,
                                                      fontSize:
                                                      20.0,
                                                      fontFamily:
                                                      "WorkSansBold")),
                                              value: item[
                                              'expenseId']
                                                  .toString(),
                                            );
                                          }).toList(),
                                        ))),
                              ),
                            ),
                          ),
                        ),


                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 1.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: expenseAmount,
                                keyboardType: TextInputType.number,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                  labelText: 'Expense Amount',
                                  hintText: "",
                                  hintStyle: TextStyle(
                                      color: Colors.black),
                                  border: OutlineInputBorder(
                                      borderRadius:
                                      BorderRadius.circular(
                                          5.0)),
                                  icon: Icon(
                                    Icons.credit_card,
                                    color: Colors.blueAccent,
                                  ),

                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: expenseRef,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Expense Reference',
                                    hintText: "",
                                    hintStyle: TextStyle(
                                        color: Colors.black),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.library_books,
                                      color: Colors.amber,
                                    )),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height:
                                showCreditAndVendorAtExpense == true ? 15 : 0),
                        Visibility(
                            visible: showCreditAndVendorAtExpense,
                            child:Container(
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
                                      "Expense Document ",
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
                        )),

                        SizedBox(height:
                                showCreditAndVendorAtExpense == true ? 15 : 0),
                        Visibility(
                            visible: showCreditAndVendorAtExpense,
                            child:Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child: Container(
                              child: TextField(
                                maxLines: 1,
                                controller: remark,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Remark',
                                    hintText: "Remark",
                                    hintStyle: TextStyle(
                                        color: Colors.black),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.library_books,
                                      color: Colors.amber,
                                    )),
                              ),
                            ),
                          ),
                        )),

                        SizedBox(height: 15),
                        Container(
                          child: Padding(
                            padding: const EdgeInsets.only(
                                top: 5.0, left: 10),
                            child:
                            new  RaisedButton(
                              padding: const EdgeInsets.all(8.0),
                              textColor: Colors.white,
                              color: Colors.pink,
                              onPressed: addExpense,
                              child: Padding(
                                padding:
                                const EdgeInsets.symmetric(
                                    vertical: 2.0,
                                    horizontal: 15.0),
                                child: Text(
                                  "Add Expense",
                                  style: TextStyle(
                                      fontSize: 18,
                                      color: Colors.white,
                                      fontWeight:
                                      FontWeight.w500),
                                ),
                              ),
                            ),
                          ),
                        ),

                        SizedBox(height: 25),

                      ]
                  )
              )
          )
      ),
    );
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
              "vendorName": response['otherVendorList'][i]['vendorName'].toString()
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


  Widget showExpensePaymentDetails(data,context)
  {
    Map expenseData = new Map();
    expenseData = data;

    if (data['tripSheetExpense_document'] != null && data['tripSheetExpense_document'] == true){
      print("111.....${data['tripSheetExpense_document']}");
      print("222.....${data['expenseAmount']}");
      setState(() {
          imageAvailable = true;
          base64ImageString = data['tripsheetExpenseBase64Document'];
        });
    } 

    if(expenseList.isNotEmpty)
    {
      return  Column(
        children: <Widget>[
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10,top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.spaceBetween,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showExpensePaidDetails(context, expenseData, "Expense Details");
                          },
                        ),

                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " Expense : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),

                              new TextSpan(
                                  text: " \u20B9${data['expenseAmount'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )
                              ),
                            ],
                          ),
                        ),

                        new IconButton(
                          icon: new Icon(
                            Icons.delete,
                            color: Colors.redAccent,
                          ),
                          onPressed: () {
                            deleteExpense(expenseData);
                          },
                        ),

                      ]
                  )
              ),
            ),
          ) ,

          SizedBox(height:15),
          Visibility(
          visible: imageAvailable,
          child: Card(
            elevation: 0.5,
            color: Colors.redAccent,
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
                            color: AppTheme.white,
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
                          color: Colors.amber,
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

        SizedBox(height:20),
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
                        backgroundColor: Colors.blue,
                        foregroundColor: Colors.white,
                        onPressed: () {
                          _shareImage(base64ImageString);
                        },
                        child: Icon(Icons.share),
                      )
                    ])
                    ),
          ),
        ),

        ],
      );
    }
    else
    {
      return Container();
    }
  }


  void showExpensePaidDetails(BuildContext context, Map data , String title) {

    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Expense Name : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseName']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Type : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseFixed']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Place : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expensePlace']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Reference : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseRefence']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Amount : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${data['expenseAmount']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Expense Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: (DateFormat("dd-MM-yyyy").format( new DateTime.fromMicrosecondsSinceEpoch(data['created'] * 1000)) ).toString(),
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  Future<bool> deleteExpense(expenseData){
    Alert(
      context: context,
      type: AlertType.info,
      title: "Expense Info",
      desc: "Do you want to Delete Expense Details ?",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => deleteRequest(expenseData),
          gradient: LinearGradient(colors: [
            Colors.green,
            Colors.deepPurple
          ]),
        ),
        DialogButton(
          child: Text(
            "No",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.pop(context),
          gradient: LinearGradient(colors: [
            Colors.red,
            Colors.black
          ]),
        )
      ],
    ).show();
  }

  deleteRequest(expenseData) async{
    Navigator.pop(context);

    var deleteExpenseData = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'tripsheetId': tripsheetId.toString(),
      'tripExpenseID' : '${expenseData['tripExpenseID']}',
    };

    var response =  await ApiCall.getDataFromApi(
        URI.REMOVE_EXPENSE_TRIPSHEET, deleteExpenseData, URI.LIVE_URI, context);

    FlutterAlert.onSuccessAlert(
        context, " Tripsheet Expense Deleted Successfully !", " Tripsheet Expense ");

    getTripsheetShowData(tripsheetId);
  }

  getTripExpenseList() async {
    defaultSelectedExpense = "0";
     setState(() {
       expenseDropDownList = [];
     });
    try {
      var data = {'companyId': companyId};
      var result = await ApiCall.getDataFromApi(
          URI.EXPENSE_LIST_TRIPSHEET, data, URI.LIVE_URI, context);
      if (result != null) {
        if (result['TripExpenseList'] != null &&
            result['TripExpenseList'].length > 0) {
          var obj1 = {
            "expenseId": "0",
            "expenseName": "Select Expense",
          };
          expenseDropDownList.add(obj1);
          for (int i = 0; i < result['TripExpenseList'].length; i++) {
            var obj = {
              "expenseId": result['TripExpenseList'][i]['expenseID'].toString(),
              "expenseName": result['TripExpenseList'][i]['expenseName'],
            };
            expenseDropDownList.add(obj);
          }
          setState(() {
            expenseDropDownData = expenseDropDownList;
          });
        }
      } else {
        setState(() {
          expenseDropDownList = [];
        });
      }
    } catch (e) {
      throw e;
    }
  }

  Widget showTripInfo(context)
  {
    return  Column(
      children: <Widget>[

        GestureDetector(
          onTap: (){
            showTripExtraDetails(context, "Trip Details");
          },
          child :Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.directions_bus,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showTripExtraDetails(context, "Trip Details");
                          },
                        ),

                        Text(
                            " Trip Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 16,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 125.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showTripExtraDetails(context, "Trip Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ),
        ),

        GestureDetector(
          onTap: (){
            showDriverExtraDetails(context, "Driver Details");
          },
          child :
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.start,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.people,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDriverExtraDetails(context, "Driver Details");
                          },
                        ),

                        Text(
                            " Driver Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 118.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDriverExtraDetails(context, "Driver Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

        GestureDetector(
          onTap: (){
            showDispatchExtraDetails(context, "Dispatch Details");
          },
          child :
          Card(
            color:Colors.white70,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(30),
                  topRight: Radius.circular(30),
                  bottomLeft: Radius.circular(30),
                  topLeft: Radius.circular(30)),
              //side: BorderSide(width: 5, color: Colors.green)
            ),
            child: Container(
              height: 50,
              child: Padding(
                  padding: const EdgeInsets.only(left: 10, right: 10, top: 10),
                  child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.start,
                      children: [

                        new IconButton(
                          icon: new Icon(
                            Icons.access_time,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDispatchExtraDetails(context, "Dispatch Details");
                          },
                        ),

                        Text(
                            " Dispatch Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )
                        ),

                        Container(
                          margin: const EdgeInsets.only(left: 94.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDispatchExtraDetails(context, "Dispatch Details");
                            },
                          ),
                        ),

                      ]
                  )
              ),
            ),
          ) ,
        ),

      ],
    );

  }

  void showTripExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " DOJ : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpenDate} to ${tripCloseDate} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: routeAttendancePoint != null ? routeAttendancePoint.toString() : "-",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Trip Route Volume : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${routeTotalLiter} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Booking No : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,

                    )
                ),

                new TextSpan(
                    text: tripBookref != null ? tripBookref.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Group Service : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${vehicle_Group} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Opening KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripOpeningKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closing KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripClosingKM.toString() != 'null' ? tripClosingKM.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Usage KM : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripUsageKM.toString() != 'null' ? tripUsageKM.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),


        ],
      ),

    ).show();
  }

  void showDriverExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${tripFristDriverName} / ${tripFristDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:   tripSecDriverName.toString() != 'null null'  ? tripSecDriverName.toString() +"/"+ tripSecDriverMobile.toString()  : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerName.toString() != 'null null' ? tripCleanerName.toString() +"/"+ tripCleanerMobile.toString()  : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 1 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripFristDriverRoutePoint != null ? tripFristDriverRoutePoint.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Driver 2 Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  tripSecDriverRoutePoint != null ? tripSecDriverRoutePoint.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Cleaner Route Point : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: tripCleanerRoutePoint != null ? tripCleanerRoutePoint.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }

  void showDispatchExtraDetails(BuildContext context, String title) {
    var alertStyle = AlertStyle(
      animationType: AnimationType.fromBottom,
      isCloseButton: false,
      isOverlayTapDismiss: true,
      descStyle: TextStyle(fontWeight: FontWeight.w700),
      animationDuration: Duration(milliseconds: 400),
      alertBorder: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
        side: BorderSide(
          color: Colors.grey,
        ),
      ),
      titleStyle: TextStyle(
        color: Colors.red,
      ),
    );

    Alert(
      context: context,
      style: alertStyle,
      type: AlertType.info,
      title: title,
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Dispatch Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: " ${dispatchedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed By : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: closedBy != null ? closedBy.toString() : "--" ,
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Location : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: cloesdLocation != null ? cloesdLocation.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Closed Time : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text:  closedByTime != null ? closedByTime.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),


          SizedBox(height: 10),
          RichText(
            text: TextSpan(
              children: <TextSpan>[
                new TextSpan(
                    text: " Route Remark : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )
                ),

                new TextSpan(
                    text: Remark != null ? Remark.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )
                ),
              ],
            ),
          ),
          Container(
            height: 2.0,
            width: MediaQuery.of(context).size.width,
            color: Colors.black,
            margin: const EdgeInsets.only(left: 5.0, right: 5.0),
          ),

        ],
      ),

    ).show();
  }


/*Widget tripDetailsInfoContainer() {
    *//*var routeName =
    (fueldata['fuelRouteName'] != null ? fueldata['fuelRouteName'] : "");*//*
    return (Container(
      child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
            ],
          )),
    ));
  }*/

}

