import 'dart:convert';
import 'dart:io';

import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:fleetop/FuelEntry/searchFuelEntry.dart';
import 'package:fleetop/FuelEntry/showFuelDetails.dart';
import 'package:fleetop/Issues/showIssue.dart';
import 'package:fleetop/apicall.dart';
import 'package:fleetop/appTheme.dart';
import 'package:fleetop/flutteralert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gradient_app_bar/gradient_app_bar.dart';
import 'package:intl/intl.dart';
import 'package:kf_drawer/kf_drawer.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:fleetop/CustomAutoComplete.dart';
import 'package:fleetop/style/theme.dart' as MyTheme;
import 'dart:io';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:location/location.dart';
import 'package:fleetop/CustomAutoComplete.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/services.dart';

class FuelInventory extends KFDrawerContent {
  FuelInventory({Key key});

  @override
  _FuelInventoryState createState() => _FuelInventoryState();
}

class _FuelInventoryState extends State<FuelInventory>
    with TickerProviderStateMixin {
  AnimationController animationController;
  bool multiple = true;
  double _width;
  Size size;
  double _height;
  String companyId;
  String email;
  String userId;

  String issId;
  List petrolPumpData = new List();
  List vendorListData = new List();
  DateTime mydate;
  TextEditingController petrolpumpName = new TextEditingController();
  List<bool> isDiscountTypeSelected = [true, false];

  TextEditingController totalText = new TextEditingController();
  TextEditingController litersText = new TextEditingController();
  TextEditingController unitCostText = new TextEditingController();
  TextEditingController discountText = new TextEditingController();
  TextEditingController gstText = new TextEditingController();
  TextEditingController vendorName = new TextEditingController();
  TextEditingController invoiceDateController = new TextEditingController();
  TextEditingController invoiceAmt = new TextEditingController();
  TextEditingController invoiceNo = new TextEditingController();
  TextEditingController tallyCompanyName = new TextEditingController();
  TextEditingController cashTxnNo = new TextEditingController();
  TextEditingController poNumber = new TextEditingController();
  TextEditingController description = new TextEditingController();
  FocusNode totalTextFocusNode = new FocusNode();
  FocusNode litersTextFocusNode = new FocusNode();
  FocusNode unitCostTextFocusNode = new FocusNode();
  FocusNode dicountFocusNode = new FocusNode();
  FocusNode gstTextFocusNode = new FocusNode();
  final format = DateFormat("dd-MM-yyyy");
  final TimeOfDay time = null;
  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;
  String fileName;
  String path;
  File fileData;
  Map<String, String> paths;
  List<String> extensions;
  bool isLoadingPath = false;
  bool isMultiPick = false;
  FileType fileType;
  List paymentTypeData = new List();
  int selectedPaymentType = 0;
  @override
  void initState() {
    getSessionData();
    animationController = AnimationController(
        duration: Duration(milliseconds: 2000), vsync: this);
    super.initState();
  }

  getSessionData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");
    makeDropdownData();
  }

  makeDropdownData() {
    var obj = {'id': 1, 'label': 'CASH'};
    paymentTypeData.add(obj);
    setState(() {});
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

  Widget paymnetTypeDropdown() {
    return paymentTypeData == null || paymentTypeData.isEmpty
        ? Container()
        : FittedBox(
            child: Container(
              width: _width - 20,
              margin: EdgeInsets.only(
                top: 10,
              ),
              height: 70,
              child: DropdownButtonHideUnderline(
                child: Card(
                    elevation: 10,
                    color: Colors.white,
                    child: Container(
                        padding: EdgeInsets.all(17),
                        child: DropdownButton<String>(
                            value: selectedPaymentType != 0
                                ? selectedPaymentType.toString()
                                : null,
                            hint: Text("Please Select Payment Type"),
                            isExpanded: true,
                            onChanged: (String newValue) {
                              setState(() {
                                selectedPaymentType = int.parse(newValue);
                              });
                            },
                            items: paymentTypeData.map((item) {
                              return new DropdownMenuItem(
                                child: new Text(item['label'],
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 20.0,
                                        fontFamily: "WorkSansBold"
                                        //color: Colors.white),
                                        )),
                                value: item['id'].toString(),
                              );
                            }).toList()))),
              ),
            ),
          );
  }

  Future _handleSubmitted() async {}

  Future<bool> redirectToDisplay(issueId) async => Alert(
        context: context,
        type: AlertType.success,
        title: "Success",
        desc: "Issue Successfully Rejected !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => issueDisplayData(issueId, context),
            gradient: LinearGradient(
                colors: [Colors.purpleAccent, Colors.deepPurple]),
          ),
        ],
      ).show();

  issueDisplayData(String issueId, context) async {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => ShowIssue(issueId: issueId)),
    );
  }

  Widget renderMandatoryText(String text) {
    return Text(
      "$text *",
      style: TextStyle(
          color: Colors.red,
          fontSize: 14.0,
          fontWeight: FontWeight.bold,
          fontFamily: "WorkSansBold"),
    );
  }

  @override
  Widget build(BuildContext context) {
    print("paymentTypeData =$paymentTypeData");
    size = MediaQuery.of(context).size;
    _width = MediaQuery.of(context).size.width;

    return Scaffold(
        appBar: GradientAppBar(
          gradient:
              LinearGradient(colors: [Color(0xFF8E2DE2), Color(0xFF4A00E0)]),
          centerTitle: true,
          iconTheme: new IconThemeData(color: Colors.white),
          title: Text(
            "Fuel Inventory",
            style: TextStyle(
                color: Colors.white,
                fontSize: 25.0,
                fontWeight: FontWeight.bold,
                fontFamily: "WorkSansBold"),
          ),
        ),

        // backgroundColor: AppTheme.white,
        body: Stack(
          children: [
            SingleChildScrollView(
              child: Column(
                children: [
                  Card(
                    elevation: 2.0,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(20.0)),
                    borderOnForeground: true,
                    child: Column(
                      children: [
                        renderMandatoryText("Petrol Pump"),
                        petrolPumpAutoComplete(),
                        toggleButton(),
                        twoData1(),
                        twoData2(),
                        totalAmtData()
                      ],
                    ),
                  ),
                  Card(
                    elevation: 2.0,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(20.0)),
                    borderOnForeground: true,
                    child: Column(
                      children: [
                        renderMandatoryText("Vendor"),
                        vendorAutoComplete(),
                        renderMandatoryText("Modes of Payment"),
                        paymnetTypeDropdown(),
                        cashAndPo(),
                        renderMandatoryText("Invoice Date"),
                        invoiceData(),
                        renderMandatoryText("Tally Company Name"),
                        tallyCompanyNameAutoComplete(),
                        invoiceAmtData(),
                      ],
                    ),
                  ),
                  Card(
                    elevation: 2.0,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(20.0)),
                    borderOnForeground: true,
                    child: Column(
                      children: [documentWidget(), saveBtn()],
                    ),
                  ),
                ],
              ),
            )
          ],
        ));
  }

  Widget documentWidget() {
    return Container(
      child: Column(
        children: [
          Container(
            child: Padding(
                padding: const EdgeInsets.only(left: 10),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Icon(
                        Icons.file_upload,
                        color: Colors.purpleAccent,
                      ),
                      Text(
                        "Fuel Document ",
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
                          style: TextStyle(color: Colors.white, fontSize: 25),
                        ),
                        gradient: LinearGradient(
                            colors: [Colors.blueGrey, Colors.blueAccent]),
                        onPressed: openBottomSheet,
                      )
                    ])),
          ),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  maxLines: 3,
                  controller: description,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Description',
                      hintText: 'Description',
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.comment,
                        color: Colors.pinkAccent,
                      )),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget saveBtn() {
    return Container(
      child: Center(
        child: Container(
            height: 50,
            width: _width - 120,
            margin: EdgeInsets.only(top: 20.0, left: 10, bottom: 15),
            decoration: new BoxDecoration(
              borderRadius: BorderRadius.all(Radius.circular(5.0)),
              gradient: new LinearGradient(
                  colors: [
                    MyTheme.Colors.loginGradientEnd,
                    MyTheme.Colors.loginGradientStart
                  ],
                  begin: const FractionalOffset(0.2, 0.2),
                  end: const FractionalOffset(1.0, 1.0),
                  stops: [0.0, 1.0],
                  tileMode: TileMode.clamp),
            ),
            child: MaterialButton(
              highlightColor: Colors.transparent,
              splashColor: MyTheme.Colors.loginGradientEnd,
              child: Padding(
                padding: const EdgeInsets.symmetric(
                    vertical: 10.0, horizontal: 42.0),
                child: Text(
                  "Fuel Inventory",
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 25.0,
                      fontFamily: "WorkSansBold"),
                ),
              ),
              onPressed: _handleSubmitted,
            )),
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

  Future getImageGallery() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.gallery, maxHeight: 600, maxWidth: 600);
    if (imageFile == null) {
      return;
    }
    setState(() {
      myImage = imageFile;
    });
    imageName = myImage.path.split("/").last;
    imageExt = imageName.split(".").last;

    testCompressAndGetFile(myImage, myImage.path);
    base64Image = base64Encode(myImage.readAsBytesSync());
    setState(() {
      uploadedFile = base64Image;
    });
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

  Widget invoiceData() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          invoiceNumber(),
          invoiceDate(),
        ],
      ),
    );
  }

  Widget cashAndPo() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          cashTxnNumber(),
          poNumberWidget(),
        ],
      ),
    );
  }

  Widget petrolPumpAutoComplete() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 10),
      child: CustomAutoComplete(
          suggestionList: petrolPumpData,
          controller: petrolpumpName,
          hintLabel: 'Petrol Pump Name',
          label: 'Petrol Pump',
          dataKeyName: 'corporateAccountDisplayName',
          iconData: Icons.pest_control,
          onItemSelected: (suggestion) {},
          onChanged: (pattern) {
            getPetrolPumpDetails(pattern);
          }),
    );
  }

  getPetrolPumpDetails(pattern) {}
  Widget toggleButton() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Container(
          margin: EdgeInsets.only(
            left: 10,
            top: 10,
          ),
          child: Text(
            "Discount/GST Type :*",
            style: TextStyle(
                color: Colors.red,
                fontSize: 15.0,
                fontWeight: FontWeight.bold,
                fontFamily: "WorkSansBold"),
          ),
        ),
        Container(
          margin: EdgeInsets.only(
            right: 10,
            top: 10,
          ),
          child: ToggleButtons(
            borderColor: Colors.black,
            fillColor: Colors.green,
            borderWidth: 0,
            selectedBorderColor: Colors.black,
            selectedColor: Colors.white,
            borderRadius: BorderRadius.circular(10),
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  'Percentage',
                  style: TextStyle(
                    fontSize: 16,
                    color: AppTheme.darkText,
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  'Amount',
                  style: TextStyle(
                    fontSize: 16,
                    color: AppTheme.darkText,
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
            ],
            onPressed: (int index) {
              setState(() {
                for (int i = 0; i < isDiscountTypeSelected.length; i++) {
                  if (i == index) {
                    isDiscountTypeSelected[i] = true;
                  } else {
                    isDiscountTypeSelected[i] = false;
                  }
                }
              });
            },
            isSelected: isDiscountTypeSelected,
          ),
        )
      ],
    );
  }

  Widget twoData1() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          tyreQuantity(),
          unitCost(),
        ],
      ),
    );
  }

  Widget twoData2() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          discount(),
          gstAmt(),
        ],
      ),
    );
  }

  Widget totalAmtData() {
    return Center(child: totalAmt());
  }

  Widget tyreQuantity() {
    return Container(
      width: _width / 2 - 30,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        focusNode: litersTextFocusNode,
        onFieldSubmitted: (term) {
          litersTextFocusNode.unfocus();
          FocusScope.of(context).requestFocus(unitCostTextFocusNode);
        },
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: litersText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Liters',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget unitCost() {
    return Container(
      width: _width / 2 - 30,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        focusNode: unitCostTextFocusNode,
        onFieldSubmitted: (term) {
          unitCostTextFocusNode.unfocus();
          FocusScope.of(context).requestFocus(dicountFocusNode);
        },
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: unitCostText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Unit Cost',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget discount() {
    return Container(
      width: _width / 2 - 30,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        focusNode: dicountFocusNode,
        onFieldSubmitted: (term) {
          unitCostTextFocusNode.unfocus();
          FocusScope.of(context).requestFocus(gstTextFocusNode);
        },
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: discountText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Discount',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget gstAmt() {
    return Container(
      width: _width / 2 - 30,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        focusNode: gstTextFocusNode,
        onFieldSubmitted: (term) {
          unitCostTextFocusNode.unfocus();
        },
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: gstText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Gst',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget totalAmt() {
    return Container(
      width: _width / 2 + 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        enabled: false,
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        focusNode: gstTextFocusNode,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: totalText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Total',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget vendorAutoComplete() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 10),
      child: CustomAutoComplete(
          suggestionList: vendorListData,
          controller: vendorName,
          hintLabel: 'Vendor',
          label: 'Vendor Name',
          dataKeyName: 'corporateAccountDisplayName',
          iconData: Icons.pest_control,
          onItemSelected: (suggestion) {},
          onChanged: (pattern) {
            getVendorDetails(pattern);
          }),
    );
  }

  getVendorDetails(pattern) {}

  Widget modeOfPayment() {
    return Container();
  }

  Widget invoiceNumber() {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(
        top: 20,
      ),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: totalText,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Invoice No',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget invoiceDate() {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(top: 20),
      child: DateTimeField(
        readOnly: true,
        controller: invoiceDateController,
        initialValue:
            invoiceDateController.text != null ? mydate : DateTime.now(),
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        decoration: InputDecoration(
          hintText: DateTime.now().toString(),
          labelText: "Invoice Date",
          hintStyle: TextStyle(color: Color(0xff493240)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
        ),
        format: format,
        onShowPicker: (context, currentValue) async {
          final date = await showDatePicker(
              context: context,
              firstDate: DateTime.now(),
              initialDate: currentValue ?? DateTime.now(),
              lastDate: DateTime.now());
          if (date != null) {
            setState(() {
              invoiceDateController.text = date.day.toString() +
                  '-' +
                  date.month.toString() +
                  '-' +
                  date.year.toString();
            });
            return DateTimeField.combine(date, time);
          } else {
            return currentValue;
          }
        },
      ),
    );
  }

  Widget invoiceAmtData() {
    return Container(
      width: _width / 2 + 20,
      margin: EdgeInsets.only(top: 20, left: 10, bottom: 10, right: 10),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: invoiceAmt,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Invoice Amt',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget tallyCompanyNameAutoComplete() {
    return Container(
      margin: EdgeInsets.only(left: 10, right: 10, top: 10),
      child: CustomAutoComplete(
          suggestionList: vendorListData,
          controller: vendorName,
          hintLabel: 'Tally Company Nmae',
          label: 'Tally Company',
          dataKeyName: 'corporateAccountDisplayName',
          iconData: Icons.pest_control,
          onItemSelected: (suggestion) {},
          onChanged: (pattern) {
            getTallyCompanyName(pattern);
          }),
    );
  }

  getTallyCompanyName(pattern) {}

  Widget cashTxnNumber() {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(
        top: 20,
      ),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: cashTxnNo,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'Cash Txn No',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }

  Widget poNumberWidget() {
    return Container(
      width: _width / 2 - 20,
      margin: EdgeInsets.only(
        top: 20,
      ),
      child: TextFormField(
        onChanged: (String text) {},
        keyboardType: TextInputType.numberWithOptions(decimal: true),
        textInputAction: TextInputAction.done,
        onFieldSubmitted: (term) {},
        style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: "WorkSansBold"),
        controller: poNumber,
        decoration: InputDecoration(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(10.0)),
          suffixIcon: Icon(
            Icons.library_add,
            size: 22.0,
            color: Colors.black,
          ),
          labelText: 'PO No',
          hintText: '0',
        ),
        autofocus: false,
      ),
    );
  }
}
