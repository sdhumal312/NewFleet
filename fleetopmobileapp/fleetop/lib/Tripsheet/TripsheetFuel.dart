import 'dart:convert';
import 'dart:io';
import 'dart:math';

//import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:file_picker/file_picker.dart';
import 'package:fleetop/FileUtility/FileUtility.dart';
import 'package:fleetop/Utility/Utility.dart';
import 'package:fleetop/appTheme.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:location/location.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import '../apicall.dart';
import '../fleetopuriconstant.dart';
import '../flutteralert.dart';
import 'package:fleetop/CustomAutoComplete.dart';
import 'TripsheetShow.dart';

class TripSheetFuel extends StatefulWidget {
  final Function tripsheetShowData;

  TripSheetFuel({this.tripsheetShowData, this.tripsheetId});
  final int tripsheetId;

  @override
  _TripSheetFuelState createState() => _TripSheetFuelState();
}

class _TripSheetFuelState extends State<TripSheetFuel> {
  bool showTripDetails = false;
  bool showFuelDetails = false;
  bool creditVisible = false;
  bool validateDriver1 = false;
  bool showDriver2 = false;
  bool showCleaner = false;
  bool showRoot = false;
  String companyId;
  String email;
  String userId;
  int i = 0;
  double _width;
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
  String fuelTypeId;
  String vendorId;
  String driverId = '';
  String driver2Id;
  String cleanerId;
  String routeServiceId;
  String vehicleId;
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

  Map configuration;
  Map tripList = Map();
  Map fuelTypeList = Map();
  List fuelTypeData = List();
  List fuelList = List();
  Map fuelVendorList = Map();
  List fuelVendorData = List();
  Map driverNameList = Map();
  List driverNameData = List();
  Map cleanerNameList = Map();
  List cleanerNameData = List();
  Map routeServiceList = Map();
  List routeServiceData = List();
  Map tripSheetList = Map();
  List tripSheetData = List();

  var myImage;
  var base64Image;
  var uploadedFile;
  String imageData;
  String imageName;
  String imageExt;

  int currentOdometer;
  int vehicle_ExpectedOdameter;
  int finalExpectedOdometer;

  final format = DateFormat("dd-MM-yyyy");
  final timeFormat = DateFormat("HH:mm");
  List<bool> isPaymentSelected = [true, false];
  List<bool> isTankSelected = [true, false];
  TextEditingController vehicleName = new TextEditingController();
  TextEditingController vehicleGroup = new TextEditingController();
  TextEditingController fuelEntryDate = new TextEditingController();
  TextEditingController fuelEntryTime = new TextEditingController();
  TextEditingController openOdoMeter = new TextEditingController();
  TextEditingController odoMeter = new TextEditingController();
  TextEditingController fuelType = new TextEditingController();
  TextEditingController fuelVendor = new TextEditingController();
  TextEditingController fuelLiter = new TextEditingController();
  TextEditingController fuelPrice = new TextEditingController();
  TextEditingController driverName = new TextEditingController();
  TextEditingController driver2name = new TextEditingController();
  TextEditingController cleaner = new TextEditingController();
  TextEditingController routeService = new TextEditingController();
  TextEditingController fuelEntryComments = new TextEditingController();
  TextEditingController gpsOdoMeter = new TextEditingController();
  TextEditingController vehicleNumber = new TextEditingController();
  TextEditingController mileageFrom = new TextEditingController();
  TextEditingController mileageTo = new TextEditingController();

  String latitude = '0';
  String longitude = '0';
  String privousTime = '';
  bool gpsWorking = false;
  bool grpfuelOdometer = true;
  bool selectAutoCredit = false;
  bool validateKMPL = false;
  var validateOdometerInFuel;
  var validateMinOdometerInFuel;
  var selectedTripSheetNumber = '0';
  var selectedTripSheetNumberInt = 0;
  var currentLocation;

  bool allowGPSIntegration = false;
  bool validateTripSheetNumber = false;
  bool doNotValidateBackDateEntries = false;
  bool bindMinMaxOdometerOnTripSheet = false;
  bool allowManualOdometerEntry = false;
  bool firstFuelEntry = false;
  bool creatingBackDateFuelEntry = false;
  bool readOnlyOpenOdometer = false;

  double previousFuelEntryCapacity = 0;
  int nextFuelIdOfBackDate = 0;
  int nextFuelMeterOfBackDate = 0;
  String backdateBetweenPreviousAndNext = '';
  String odometerRange = '';
  int minOdometerVal = 0;
  int maxOdometerVal = 0;
  int actualOdameterReading = 0;
  bool meterNotWorking = false;
  bool showNote = false;
  bool showRange = false;
  bool showActiveTSNote = false;
  String fuelStockDetailsString = "";
  bool allowOwnPetrolPump = false;
  String ownPetrolPump = "0";
  int IS_OWNED_PETROL_PUMP = 1;
  var maximumFuelCapacity = 0;
  double maximumFuelPrice = 0;
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
  bool blockGalleryImageSelection = false;
  bool setFuelEntryDefaultDateAndTime = false;
  bool fileIsValid = true;
  bool onlyReadDateAndTime = false;
  bool blockSaveFuelEntry = false;
  bool getStockFromInventory = false;
  String fuelInvoiceId = "";
  TextEditingController fuelAmount = new TextEditingController();
  @override
  void initState() {
    setExtensionData();
    super.initState();
    getTripsheetShowData(widget.tripsheetId);
  }

  setExtensionData() {
    extensions = new List<String>();
    extensions.add("pdf");
    extensions.add("xlsx");
  }

  calculateFuelPrice() {
    try {
      double totalAMt = 0.0;
      double fuelLiterVal = FileUtility.checkAndSetNumDouble(fuelLiter);
      double fuelPriceVal = FileUtility.checkAndSetNumDouble(fuelPrice);
      totalAMt = fuelLiterVal * fuelPriceVal;
      setState(() {
        fuelAmount.text = Utility.getLimitedData(totalAMt);
      });
    } catch (e) {
      setState(() {
        fuelAmount.text = '0';
      });
    }
  }

  setDefaultDateAndTime() {
    DateTime now = new DateTime.now();
    String act = FileUtility.getStandardDate(now.day);
    String acmonth = FileUtility.getStandardMonth(now.month);
    String hourAndMinute = FileUtility.getStandardTime(now.hour, now.minute);
    String dt = act + "-" + acmonth + "-" + now.year.toString();
    setState(() {
      fuelEntryDate.text = dt;
      fuelEntryTime.text = hourAndMinute;
      onlyReadDateAndTime = true;
    });
  }

  refreshDataNew() {
    if (setFuelEntryDefaultDateAndTime != null &&
        setFuelEntryDefaultDateAndTime) {
      setDefaultDateAndTime();
    }
    setState(() {
      blockSaveFuelEntry = false;
      getStockFromInventory = false;
      fuelInvoiceId = "";
      fuelAmount.text = "";
    });
  }

  getTripsheetShowData(int tripId) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    companyId = prefs.getString("companyId");
    userId = prefs.getString("userId");
    email = prefs.getString("email");

    var data = {
      'companyId': companyId,
      'userId': userId,
      'email': email,
      'tripsheetId': tripId.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.ADD_FUEL_TRIPSHEET, data, URI.LIVE_URI, context);

    configuration = response['configuration'];
    getStockFromInventory =
        Utility.hasKeyInConfig(configuration, "getStockFromInventory");
    tripList = response['TripSheet'];
    fuelList = response['fuel'];
    if (response != null) {
      if (response["defaultFuelPrice"] != null) {
        setState(() {
          maximumFuelPrice = response["defaultFuelPrice"];
        });
      }
      if (response["maxFuelCapacity"] != null) {
        setState(() {
          maximumFuelCapacity = response["maxFuelCapacity"];
        });
      }
      if (configuration != null) {
        if (configuration["blockGalleryImageSelection"] != null) {
          setState(() {
            blockGalleryImageSelection =
                configuration["blockGalleryImageSelection"];
          });
        }
        if (configuration["setFuelEntryDefaultDateAndTime"] != null) {
          setState(() {
            setFuelEntryDefaultDateAndTime =
                configuration["setFuelEntryDefaultDateAndTime"];
          });
        }
      }
    }

    setState(() {
      tripsheetId = tripId;
      vehicleId = tripList['vid'].toString();
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
      vehicleName.text = tripList['vehicle_registration'];
      vehicleGroup.text = tripList['vehicle_Group'];
      openOdoMeter.text = tripOpeningKM;
      odoMeter.text = tripOpeningKM;
      driverId = tripList['tripFristDriverID'].toString();
      driverName.text = tripFristDriverName;
      tripUsageKM = tripList['tripUsageKM'].toString();
      tripFristDriverRoutePoint =
          tripList['tripFristDriverRoutePoint'].toString();
      tripSecDriverRoutePoint = tripList['tripSecDriverRoutePoint'].toString();
      tripCleanerRoutePoint = tripList['tripCleanerRoutePoint'].toString();
      closedBy = tripList['closedBy'];
      cloesdLocation = tripList['cloesdLocation'];
      closedByTime = tripList['closedByTime'];
      loadType = tripList['loadTypeName'];
      totalPOD = tripList['noOfPOD'].toString();
      Remark = tripList['routeRemark'];

      showRoot = configuration['showRoute'];
      showDriver2 = configuration['showDriver2'];
      showCleaner = configuration['showCleaner'];
      validateDriver1 = configuration['validateDriver1'];
      selectAutoCredit = configuration['selectAutoCredit'];
      validateTripSheetNumber = configuration['validateTripSheetNumber'];
      doNotValidateBackDateEntries =
          configuration['doNotValidateBackDateEntries'];
      bindMinMaxOdometerOnTripSheet =
          configuration['bindMinMaxOdometerOnTripSheet'];
      allowManualOdometerEntry = configuration['allowManualOdometerEntry'];

      allowGPSIntegration = response['allowGPSIntegration'];
      validateOdometerInFuel = response['validateOdometerInFuel'];
      validateMinOdometerInFuel = response['validateMinOdometerInFuel'];
    });

    var odometerData = {'companyId': companyId, 'vehicleId': vehicleId};
    var ododmeterRresponse = await ApiCall.getDataFromApi(
        URI.GET_ODO_METER_DETAILS, odometerData, URI.LIVE_URI, context);

    if (ododmeterRresponse != null) {
      if (ododmeterRresponse['oddMeterDetails'] != null) {
        var oddMeterDetails = ododmeterRresponse['oddMeterDetails'];
        fuelType.text =
            'Diesel'; /*oddMeterDetails['vehicle_Fuel'].toString();*/
        currentOdometer = oddMeterDetails['vehicle_Odometer'];
        vehicle_ExpectedOdameter = oddMeterDetails['vehicle_ExpectedOdameter'];
        finalExpectedOdometer = currentOdometer + vehicle_ExpectedOdameter;
        fuelTypeId = '1';

        var kmplFrom = oddMeterDetails['vehicle_ExpectedMileage'];
        var kmplTo = oddMeterDetails['vehicle_ExpectedMileage_to'];

        if (kmplFrom == 0 && kmplTo == 0) {
          vehicleNumber.text = oddMeterDetails['vehicle_registration'];
          addKMPL(context);
          setState(() {
            validateKMPL = true;
          });
        }
      }

      if (ododmeterRresponse['vendorName'] != null) {
        setState(() {
          fuelVendor.text = response['vendorName'];
          creditVisible = true;

          if (selectAutoCredit) {
            isPaymentSelected[1] = true;
            isPaymentSelected[0] = false;
          }
        });
      }
    }

    setLocationData();
  }

  getFuelStockDetails(bool saveData) async {
    var fstock;
    var price;
    String str = "";
    String avgrate = "";
    var data = {
      'companyId': companyId,
      'petrolPumpId': vendorId.toString(),
      'getStockFromInventoryConfig': getStockFromInventory.toString()
    };
    var response = await ApiCall.getDataFromApi(
        URI.GET_FUEL_STOCK_DETAILS, data, URI.LIVE_URI, context);
    if (response != null && response["stockDetails"] != null) {
      if (getStockFromInventory) {
        fuelInvoiceId = response["stockDetails"]["fuelInvoiceId"].toString();
        fstock = response["stockDetails"]["balanceStock"];
        price = response["stockDetails"]["rate"];
        fstock = Utility.getLimitedData(fstock);
        price = Utility.getLimitedData(price);
        str = "Fuel Stock : $fstock ltr" + "& Price :$price";
        setState(() {
          fuelStockDetailsString = str;
          fuelPrice.text = price;
        });
      } else {
        fstock = response["stockDetails"]["stockQuantity"];
        price = response["stockDetails"]["avgFuelRate"];
        fstock = Utility.getLimitedData(fstock);
        price = Utility.getLimitedData(price);
        str = "Fuel Stock : $fstock ltr" + "& Price :$price";
        avgrate = response["stockDetails"]["avgFuelRate"].toString();
        setState(() {
          fuelStockDetailsString = str;
          fuelPrice.text = avgrate;
        });
      }
      if (saveData) {
        addFuel();
      }
    } else {
      setState(() {
        fuelStockDetailsString = "No stock available at this petrol pump";
        fuelPrice.text = "0";
      });
      FlutterAlert.onInfoAlert(
          context,
          "No stock available at this petrol pump please add stock before continue !",
          "Info");
      return false;
    }
  }

  setLocationData() async {
    var location = new Location();
    try {
      if (await location.hasPermission()) {
        var currentLocation = await location.getLocation();
        setState(() {
          latitude = currentLocation.latitude.toString();
          longitude = currentLocation.longitude.toString();
        });
      } else {
        await location.requestPermission();
      }
    } catch (e) {
      print(e);
      location = null;
    }
  }

  Future<bool> switchONGPSAlert() async => Alert(
        context: context,
        type: AlertType.info,
        title: "INFO",
        desc: "Please switch ON the GPS first to do Fuel Entry !",
        buttons: [
          DialogButton(
            child: Text(
              "OK",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
            onPressed: () => setLocation(),
            gradient: LinearGradient(colors: [Colors.grey, Colors.black]),
          ),
        ],
      ).show();

  setLocation() {
    Navigator.pop(context);
    setLocationData();
  }

  Future addFuel() async {
    if (!fieldValidation()) {
      return;
    } else {
      var saveFuelData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'tripSheetId': tripsheetId.toString(),
        'FuelSelectVehicle': vehicleId,
        'vehicle_group': vehicleGroup.text,
        'fuelDate': fuelEntryDate.text,
        'fuelTime': fuelEntryTime.text,
        'fuel_meter_old': openOdoMeter.text,
        'fuel_meter': odoMeter.text,
        'fuel_type': fuelTypeId,
        'selectVendor': vendorId.toString(),
        'fuel_liters': fuelLiter.text,
        'fuel_price': fuelPrice.text,
        'VehicleTODriverFuel': driverId,
        'VehicleTODriver2Fuel': driver2Id,
        'VehicleTOCleanerFuel': cleanerId,
        'TripRouteList': routeServiceId,
        'fuel_tank': isTankSelected[0] == true ? 0 : 1,
        "fuel_comments": fuelEntryComments.text,
        'base64String': imageData,
        'imageName': imageName,
        'imageExt': imageExt,
        'latitude': latitude,
        'longitude': longitude,
        'ownPetrolPump': ownPetrolPump.toString(),
      };
      if (getStockFromInventory != null && getStockFromInventory) {
        saveFuelData["getStockFromInventoryConfig"] =
            getStockFromInventory.toString();
        saveFuelData["fuelInvoiceId"] = fuelInvoiceId.toString();
        saveFuelData["currentFuelInvoiceId"] = fuelInvoiceId.toString();
        saveFuelData["fuelAmount"] = fuelAmount.text;
      }

      if (creditVisible == true) {
        saveFuelData['paymentTypeId'] = isPaymentSelected[0] == true ? 1 : 2;
      } else {
        saveFuelData['paymentTypeId'] = 1;
      }
      final jsonEncoder = JsonEncoder();
      var finalData = {'FuelData': jsonEncoder.convert(saveFuelData)};

      var data = await ApiCall.getDataFromApi(
          URI.SAVE_FUEL_ENTRY_DETAILS, finalData, URI.LIVE_URI, context);

      if (data != null) {
        if (data['alreadyExist'] == true) {
          FlutterAlert.onInfoAlert(context, "Already Exist!", "Info");
        } else if (data['contactAdmin'] == true) {
          FlutterAlert.onInfoAlert(
              context, "Sequence Not Found. Please contact Admin !", "Info");
        } else if (data['odoMeter'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['odoMeter'], "Info");
        } else if (data['vehicleStatus'] != null) {
          FlutterAlert.onInfoAlert(context, "" + data['vehicleStatus'], "Info");
        } else {
          refreshDataNew();
          Alert(
            context: context,
            type: AlertType.success,
            title: "Info",
            desc:
                " Tripsheet Fuel Added Successfully ! You can view Fuel Entry In Expense Details. ",
            buttons: [
              DialogButton(
                child: Text(
                  "OK",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
                onPressed: () {
                  Navigator.pop(context);
                  Navigator.pop(context);
                  Navigator.pop(context);
                  widget.tripsheetShowData();
                },
                gradient:
                    LinearGradient(colors: [Colors.green, Colors.deepPurple]),
              ),
            ],
          ).show();

          /*refreshData();
            Navigator.pop(context);
            Navigator.pop(context);
            widget.tripsheetShowData();*/
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool fieldValidation() {
    if (blockSaveFuelEntry != null && blockSaveFuelEntry) {
      FlutterAlert.onErrorAlert(context,
          "Please Select Different Time or Wait for a Minute!", "Error");
      return false;
    }
    if (fileIsValid != null && !fileIsValid) {
      FlutterAlert.onErrorAlert(
          context,
          "Invalid File Selected From Documents PDF,EXCEL allowed only !",
          "Error");
      return false;
    }
    if (latitude == '0' && longitude == '0') {
      switchONGPSAlert();
      return false;
    }
    if (validateKMPL) {
      addKMPL(context);
      return false;
    }
    if (fuelEntryDate.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Date !", "Error");
      return false;
    }
    if (fuelEntryTime.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Valid Time !", "Error");
      return false;
    }
    if (openOdoMeter.text == '' || openOdoMeter.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter Open Odometer !", "Error");
      return false;
    }
    if (odoMeter.text == '' || odoMeter.text == '0') {
      FlutterAlert.onErrorAlert(context, "Please Enter Odometer !", "Error");
      return false;
    }
    if (fuelTypeId == '' || fuelTypeId == null) {
      FlutterAlert.onErrorAlert(context, "Please Select Fuel Type !", "Error");
      return false;
    }
    if (fuelType.text == '') {
      FlutterAlert.onErrorAlert(context, "Please Select Fuel Type !", "Error");
      return false;
    }
    if (!validateMaxOdameter()) {
      return false;
    }
    if (fuelVendor.text == '') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Vendor Name !", "Error");
      return false;
    }
    if (fuelLiter.text == '' || fuelLiter.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Fuel Liter !", "Error");
      return false;
    }
    if (fuelPrice.text == '' || fuelPrice.text == '0') {
      FlutterAlert.onErrorAlert(
          context, "Please Enter a Fuel Price !", "Error");
      return false;
    }

    if (validateDriver1 == true) {
      if (driverId == '' || driverId == '0') {
        FlutterAlert.onErrorAlert(
            context, "Please Enter a Fuel Price !", "Error");
        return false;
      }
    }
    if (fuelLiter.text != '' && fuelLiter.text.length > 0) {
      String fl = fuelLiter.text;
      if (fl.contains('.')) {
        var val = double.parse(fuelPrice.text);
        if (val > maximumFuelCapacity) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Liter should not be grater than  $maximumFuelCapacity",
              "Error");
          return false;
        }
      } else {
        int val = int.parse(fuelLiter.text);
        if (val > maximumFuelCapacity) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Liter should not be grater than  $maximumFuelCapacity",
              "Error");
          return false;
        }
      }
    }
    if (fuelPrice.text != '' && fuelPrice.text.length > 0) {
      String fp = fuelPrice.text;

      if (fp.contains('.')) {
        var val = double.parse(fuelPrice.text);
        if (val >= maximumFuelPrice) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Price should not be grater than  $maximumFuelPrice",
              "Error");
          return false;
        }
      } else {
        var val = int.parse(fuelPrice.text);
        if (val >= maximumFuelPrice) {
          FlutterAlert.onErrorAlert(
              context,
              "Fuel Price should not be grater than  $maximumFuelPrice",
              "Error");
          return false;
        }
      }
    }
    return true;
  }

  validateMaxOdameter() {
    if (meterNotWorking) {
      return true;
    }

    bool validateFuel = false;

    if (validateOdometerInFuel == true) {
      validateFuel = true;
    }

    if (num.parse(odoMeter.text) <= 0) {
      FlutterAlert.onErrorAlert(context, "Odometer cannot be zero !", "Error");
      return false;
    }

    if (creatingBackDateFuelEntry && doNotValidateBackDateEntries == true) {
      validateFuel = false;
    }

    if (allowManualOdometerEntry == true) {
      validateFuel = false;

      if (num.parse(odoMeter.text) <= num.parse(openOdoMeter.text)) {
        FlutterAlert.onErrorAlert(context,
            "Odometer should be greater than Open Odometer !", "Error");
        return false;
      }

      if ((num.parse(odoMeter.text) - num.parse(openOdoMeter.text)) >
          vehicle_ExpectedOdameter) {
        FlutterAlert.onErrorAlert(
            context,
            "Difference between Open Odometer and Current Odometer cannot be greater then" +
                vehicle_ExpectedOdameter.toString(),
            "Error");
        return false;
      }
    }

    if (num.parse(odoMeter.text) <= num.parse(openOdoMeter.text)) {
      FlutterAlert.onErrorAlert(
          context, "Odometer should be greater than Open Odometer !", "Error");
      return false;
    }

    if (validateFuel) {
      if (num.parse(odoMeter.text) < minOdometerVal ||
          num.parse(odoMeter.text) > maxOdometerVal) {
        FlutterAlert.onErrorAlert(
            context,
            "Please Enter Odometer in Range Of :" +
                minOdometerVal.toString() +
                " - " +
                maxOdometerVal.toString(),
            "Error");
        return false;
      }
    }

    return true;
  }

  refreshData() {
    fuelEntryDate.text = '';
    fuelEntryTime.text = '';
    openOdoMeter.text = '';
    odoMeter.text = '';
    fuelType.text = '';
    fuelVendor.text = '';
    fuelLiter.text = '';
    fuelPrice.text = '';
    driverId = '';
    driverName.text = '';
    driver2Id = '';
    driver2name.text = '';
    cleaner.text = '';
    cleanerId = '';
    routeService.text = '';
    routeServiceId = '';
    imageData = '';
    fuelEntryComments.text = '';
    gpsOdoMeter.text = '';
    setState(() {
      getStockFromInventory = false;
      fuelInvoiceId = "";
      fuelAmount.text = "";
      tripSheetData = [];
      meterNotWorking = false;
      showNote = false;
      showRange = false;
      showActiveTSNote = false;
      fuelStockDetailsString = "";
      allowOwnPetrolPump = false;
      ownPetrolPump = "0";
    });
  }

  openBottomSheet() {
    if (blockGalleryImageSelection != null && !blockGalleryImageSelection) {
      openBottomSheetForAll();
    } else {
      openBottomSheetForCameraAndDoc();
    }
  }

  openBottomSheetForCameraAndDoc() {
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

  openBottomSheetForAll() {
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

      setState(() {
        imageName = fileData.path.split("/").last;
        imageExt = ext;
        base64Image = base64Encode(fileData.readAsBytesSync());
        imageData = base64Image;
      });
      fileIsValid = FileUtility.validateFileExtension(extensions, imageExt);
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
    fileSize = double.parse(ab);
    return ((bytes / pow(1024, i)).toStringAsFixed(decimals)) +
        ' ' +
        suffixes[i];
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
      fileIsValid = true;
    });
  }

  Future getImageFromCamera() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.camera,
        maxHeight: 800,
        maxWidth: 800,
        imageQuality: 100);
    if (imageFile == null) {
      return;
    }
    imageName = imageFile.path.split("/").last;
    imageExt = imageName.split(".").last;

    setState(() {
      myImage = imageFile;
      fileIsValid = true;
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

  getVehicleGPSDataAtTime() async {
    if (vehicleId == '' || vehicleId == '0') {
      return;
    }
    if (fuelEntryDate.text == '') {
      return;
    }
    var gpsData = {
      'vehicleId': vehicleId,
      'companyId': companyId,
      'dispatchedByTime': fuelEntryDate.text,
      'dispatchTime': fuelEntryTime.text
    };
    var data = await ApiCall.getDataFromApi(
        URI.VEHICLE_GPS_DATA_AT_TIME, gpsData, URI.LIVE_URI, context);
    if (data != null) {
      if (data['VEHICLE_TOTAL_KM'] != null) {
        gpsOdoMeter.text = data['VEHICLE_TOTAL_KM'].toString();
        odoMeter.text = data['VEHICLE_TOTAL_KM'].toString();
        setState(() {
          gpsWorking = true;
          grpfuelOdometer = true;
        });
      } else {
        setState(() {
          gpsWorking = false;
          grpfuelOdometer = true;
          gpsOdoMeter.text = '';
        });
      }
    }
  }

  Future saveKMPLDetails() async {
    if (!validateKMPLData()) {
      return;
    } else {
      var kmplData = {
        'email': email,
        'userId': userId,
        'companyId': companyId,
        'vehicleKmplId': vehicleId,
        'expectedMileageFrom': mileageFrom.text,
        'expectedMileageTo': mileageTo.text
      };

      var data = await ApiCall.getDataFromApi(
          URI.UPDATE_VEHICLE_KMPL_DETAILS, kmplData, URI.LIVE_URI, context);

      if (data != null) {
        if (data['kmplUpdated'] == true) {
          Navigator.pop(context);
          setState(() {
            mileageFrom.text = '';
            mileageTo.text = '';
            validateKMPL = false;
          });
          FlutterAlert.onSuccessAlert(
              context, "KMPL Data Updated Successfully !", "Success");
        } else {
          FlutterAlert.onErrorAlert(context,
              "Some Problem Occur,Please contact on Support !", "Error");
        }
      } else {
        FlutterAlert.onErrorAlert(
            context, "Some Problem Occur,Please contact on Support !", "Error");
      }
    }
  }

  bool validateKMPLData() {
    if (mileageFrom.text == '' || mileageFrom.text == '0') {
      FlutterAlert.onErrorAlert(context,
          "Please Enter Expected Mileage From Greater Than 0 !", "Error");
      return false;
    }

    if (mileageTo.text == '' || mileageTo.text == '0') {
      FlutterAlert.onErrorAlert(context,
          "Please Enter Expected Mileage To Greater Than 0 !", "Error");
      return false;
    }
    return true;
  }

  checkFinalValidation() {
    if (allowOwnPetrolPump) {
      getFuelStockDetails(true);
    } else {
      addFuel();
    }
  }

  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    if (fuelEntryTime.text != privousTime) {
      setState(() {
        meterNotWorking = false;
        showNote = false;
        showRange = false;
        showActiveTSNote = false;
      });

      if (vehicleId != '') {
        getActiveTripSheetAndBackDateData(false);
      }

      privousTime = fuelEntryTime.text;
      if (allowGPSIntegration == true) {
        getVehicleGPSDataAtTime();
      }
    }

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.amber,
        iconTheme: IconThemeData(
          color: Colors.black,
        ),
        centerTitle: true,
        title: Text(
          "Add Fuel Entry",
          style: new TextStyle(
            fontSize: 22,
            color: Colors.white,
            fontWeight: FontWeight.w700,
          ),
        ),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () => {
            /*Navigator.push(context,new MaterialPageRoute(builder: (context) => new TripsheetShow (tripsheetId :tripsheetId)))*/
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
                      )),
                  new TextSpan(
                      text: " ${createdDate} ",
                      style: new TextStyle(
                        color: Colors.blue,
                        fontWeight: FontWeight.w700,
                        fontSize: 17,
                      )),
                ],
              ),
            ),

            SizedBox(height: 25),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("Trip Number : TS - ${tripsheetNumber}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.red,
                        fontSize: 19)),
              ),
            ),

            SizedBox(height: 5),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("${vehNumber}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.green,
                        fontSize: 15)),
              ),
            ),

            SizedBox(height: 5),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("${route}",
                    style: TextStyle(
                        fontWeight: FontWeight.w700,
                        color: Colors.green,
                        fontSize: 15)),
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
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
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
                                  fontWeight: FontWeight.w500),
                            ),
                            /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                            onPressed: () => {
                              setState(() {
                                showTripDetails = !showTripDetails;
                              })
                            },
                          )
                        ])),
              ),
            ),
            Visibility(
                visible: showTripDetails,
                child: Stack(
                  children: <Widget>[
                    showTripInfo(context),
                    /*ola(context),*/
                  ],
                )),

            SizedBox(height: 15),
            Card(
              color: Colors.pink,
              child: Container(
                height: 50,
                child: Padding(
                    padding: const EdgeInsets.only(left: 10, right: 10),
                    child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            "Fuel Details ",
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
                              showFuelDetails ? "Close" : "Open",
                              style: TextStyle(
                                  color: Colors.purpleAccent,
                                  fontSize: 20,
                                  fontWeight: FontWeight.w500),
                            ),
                            /*gradient: LinearGradient(colors: [
                                          Colors.greenAccent,
                                          Colors.blueAccent
                                        ]),*/
                            onPressed: () => {
                              setState(() {
                                showFuelDetails = !showFuelDetails;
                              })
                            },
                          )
                        ])),
              ),
            ),
            Visibility(
                visible: showFuelDetails,
                child: Column(
                  children: <Widget>[
                    if (fuelList != null)
                      for (int i = 0; i < fuelList.length; i++)
                        showFuelPaymentDetails(fuelList[i], context),
                  ],
                )),
//

            SizedBox(height: 60),
            Container(
              child: Align(
                alignment: Alignment.center,
                child: Text("Add Fuel Details ",
                    style: new TextStyle(
                      color: Colors.blueAccent,
                      fontWeight: FontWeight.w700,
                      fontSize: 15,
                    )),
              ),
            ),
            Container(
              height: 2.0,
              width: MediaQuery.of(context).size.width,
              color: Colors.blueAccent,
              margin: const EdgeInsets.only(left: 80.0, right: 80.0),
            ),

            SizedBox(height: showNote == true ? 5 : 0),
            Visibility(
                visible: showNote,
                child: Container(
                  child: Align(
                    alignment: Alignment.center,
                    child: Text(backdateBetweenPreviousAndNext,
                        style: new TextStyle(
                          color: Colors.blueAccent,
                          fontWeight: FontWeight.w700,
                          fontSize: 15,
                        )),
                  ),
                )),

            SizedBox(height: 15),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    enabled: false,
                    maxLines: 1,
                    controller: vehicleName,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'Vehicle',
                        hintText: "",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.directions_bus,
                          color: Colors.lightGreen,
                        )),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    enabled: false,
                    maxLines: 1,
                    controller: vehicleGroup,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'Vehicle Group',
                        hintText: "",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.group,
                          color: Colors.lightBlueAccent,
                        )),
                  ),
                ),
              ),
            ),

            SizedBox(height: tripSheetData.length > 0 ? 15 : 0),
            Visibility(
              visible: tripSheetData.length > 0,
              child: Container(
                height: 70,
                child: Padding(
                  padding: const EdgeInsets.only(top: 5.0, left: 10),
                  child: Container(
                    child: DropdownButtonHideUnderline(
                      child: Card(
                          elevation: 0.5,
                          color: Colors.white70,
                          child: Container(
                              padding: EdgeInsets.all(17),
                              child: DropdownButton<String>(
                                hint: Text("Trip Sheet Number"),
                                value: selectedTripSheetNumberInt != 0
                                    ? selectedTripSheetNumberInt.toString()
                                    : null,
                                isExpanded: true,
                                onChanged: (String newValue) {
                                  setState(() {
                                    selectedTripSheetNumberInt =
                                        int.parse(newValue);
                                    selectedTripSheetNumber = newValue;
                                  });
                                },
                                items: tripSheetData.map((item) {
                                  return new DropdownMenuItem(
                                    child: new Text(item['tripSheetNumber'],
                                        style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 15.0,
                                            fontFamily: "WorkSansBold")),
                                    value: item['tripSheetID'].toString(),
                                  );
                                }).toList(),
                              ))),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: showActiveTSNote == true ? 5 : 0),
            Visibility(
                visible: showActiveTSNote,
                child: Container(
                  child: Align(
                    alignment: Alignment.center,
                    child: Text("Active Tripsheet Found",
                        style: new TextStyle(
                          color: Colors.purple,
                          fontWeight: FontWeight.w700,
                          fontSize: 15,
                        )),
                  ),
                )),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
                child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: DateTimeField(
                  enabled: !onlyReadDateAndTime,
                  format: format,
                  controller: fuelEntryDate,
                  initialValue: DateTime.now(),
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      hintText: "Select Date",
                      labelText: "Select Date*",
                      hintStyle: TextStyle(color: Color(0xff493240)),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.date_range,
                        color: Colors.amber,
                      )),
                  //format: format,
                  onShowPicker: (context, currentValue) {
                    return showDatePicker(
                        context: context,
                        firstDate: DateTime(1950),
                        initialDate: currentValue ?? DateTime.now(),
                        lastDate: DateTime.now());
                  },
                ),
              ),
            )),

            SizedBox(height: 3),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: DateTimeField(
                    enabled: !onlyReadDateAndTime,
                    readOnly: true,
                    format: timeFormat,
                    controller: fuelEntryTime,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        hintText: "Select Time",
                        labelText: "Select Time",
                        hintStyle: TextStyle(color: Color(0xff493240)),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.access_time,
                          color: Colors.blue,
                        )),
                    onShowPicker: (context, currentValue) async {
                      final time = await showTimePicker(
                        context: context,
                        initialTime: TimeOfDay.fromDateTime(
                            currentValue ?? DateTime.now()),
                        builder: (BuildContext context, Widget child) {
                          return MediaQuery(
                            data: MediaQuery.of(context)
                                .copyWith(alwaysUse24HourFormat: true),
                            child: child,
                          );
                        },
                      );
                      setState(() {
                        fuelEntryTime.text =
                            FileUtility.getTime(time.toString());
                      });
                      return DateTimeField.convert(time);
                    },
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 1.0, left: 10),
                child: Container(
                  child: TextField(
                    maxLines: 1,
                    controller: openOdoMeter,
                    enabled: readOnlyOpenOdometer,
                    keyboardType: TextInputType.number,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                      labelText: 'Open Odometer',
                      hintText: "",
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.departure_board,
                        color: Colors.brown,
                      ),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: grpfuelOdometer == true ? 5 : 0),
            Visibility(
                visible: grpfuelOdometer,
                child: Align(
                  alignment: Alignment.centerRight,
                  child: Container(
                    child: Icon(Icons.stars, color: Colors.red, size: 12),
                  ),
                )),
            Visibility(
                visible: grpfuelOdometer,
                child: Container(
                  child: Padding(
                    padding: const EdgeInsets.only(top: 5.0, left: 10),
                    child: Container(
                      child: TextField(
                        maxLines: 1,
                        controller: odoMeter,
                        keyboardType: TextInputType.number,
                        style: TextStyle(color: Colors.black),
                        decoration: InputDecoration(
                            labelText: 'Odometer',
                            hintText: "Odometer",
                            hintStyle: TextStyle(color: Colors.black),
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0)),
                            icon: Icon(
                              Icons.departure_board,
                              color: Colors.black,
                            )),
                      ),
                    ),
                  ),
                )),

            SizedBox(height: showRange == true ? 5 : 0),
            Visibility(
                visible: showRange,
                child: Container(
                  child: Align(
                    alignment: Alignment.center,
                    child: Text(odometerRange,
                        style: new TextStyle(
                          color: Colors.red,
                          fontWeight: FontWeight.w700,
                          fontSize: 15,
                        )),
                  ),
                )),

            SizedBox(height: allowGPSIntegration == true ? 15 : 0),
            Visibility(
              visible: allowGPSIntegration,
              child: Container(
                  child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    enabled: false,
                    maxLines: 1,
                    controller: gpsOdoMeter,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'GPS Odometer',
                        hintText: "GPS Odometer",
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.departure_board,
                          color: Colors.brown,
                        )),
                  ),
                ),
              )),
            ),

            SizedBox(height: 5),
            CheckboxListTile(
              title: Text("Meter Not Working"),
              value: meterNotWorking,
              onChanged: (newValue) {
                if (!meterNotWorking) {
                  setState(() {
                    readOnlyOpenOdometer = true;
                    meterNotWorking = true;
                  });
                } else {
                  setState(() {
                    readOnlyOpenOdometer = false;
                    meterNotWorking = false;
                  });
                }
              },
              controlAffinity:
                  ListTileControlAffinity.leading, //  <-- leading Checkbox
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Column(
                  children: <Widget>[
                    SizedBox(
                      height: 10.0,
                    ),
                    TypeAheadField(
                      hideSuggestionsOnKeyboardHide: false,
                      hideOnEmpty: true,
                      textFieldConfiguration: TextFieldConfiguration(
                        controller: fuelType,
                        decoration: InputDecoration(
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0)),
                            icon: Icon(
                              Icons.person_outline,
                              color: Colors.black,
                            ),
                            hintText: 'Fuel Type',
                            labelText: 'Fuel Type'),
                      ),
                      suggestionsCallback: (pattern) async {
                        return await getFuelTypeSuggestions(pattern);
                      },
                      itemBuilder: (context, suggestion) {
                        return ListTile(
                          leading: Icon(Icons.person),
                          title: Text(suggestion['fuelTypeName']),
                          // subtitle: Text('\$${suggestion['vehicleId']}'),
                        );
                      },
                      onSuggestionSelected: (suggestion) {
                        fuelType.text = suggestion['fuelTypeName'];
                        fuelTypeId = suggestion['fuelTypeId'];
                      },
                    ),
                  ],
                ),
              ),
            ),

            /*SizedBox(height: 15),
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
                                maxLines: 1,
                                controller: fuelType,
                                style:
                                TextStyle(color: Colors.black),
                                decoration: InputDecoration(
                                    labelText: 'Fuel Type*',
                                    hintText: 'Fuel Type',
                                    hintStyle: TextStyle(
                                        color: Colors.black),
                                    border: OutlineInputBorder(
                                        borderRadius:
                                        BorderRadius.circular(
                                            5.0)),
                                    icon: Icon(
                                      Icons.local_gas_station,
                                      color: Colors.red,
                                    )),
                              ),
                            ),
                          ),
                        ),*/

            // SizedBox(height: 15),
            // Align(
            //   alignment: Alignment.centerRight,
            //   child: Container(
            //     child: Icon(Icons.stars,
            //         color: Colors.red, size: 12),
            //   ),
            // ),
            // Container(
            //   child: Padding(
            //     padding: const EdgeInsets.only(
            //         top: 5.0, left: 10),
            //     child: Column(
            //       children: <Widget>[
            //         SizedBox(
            //           height: 10.0,
            //         ),
            //         TypeAheadField(
            //           hideSuggestionsOnKeyboardHide:
            //           false,
            //           hideOnEmpty: true,
            //           textFieldConfiguration:
            //           TextFieldConfiguration(
            //             controller: fuelVendor,
            //             decoration: InputDecoration(
            //                 border: OutlineInputBorder(
            //                     borderRadius:
            //                     BorderRadius
            //                         .circular(5.0)),
            //                 icon: Icon(
            //                   Icons.person_outline,
            //                   color: Colors.black,
            //                 ),
            //                 hintText: 'Fuel Vendor',
            //                 labelText: 'Fuel Vendor'),
            //           ),
            //           suggestionsCallback:
            //               (pattern) async {
            //             return await getFuelVendorSuggestions(
            //                 pattern);
            //           },
            //           itemBuilder:
            //               (context, suggestion) {
            //             return ListTile(
            //               leading: Icon(
            //                   Icons.person),
            //               title: Text(suggestion[
            //               'vendorName']),
            //               // subtitle: Text('\$${suggestion['vehicleId']}'),
            //             );
            //           },
            //           onSuggestionSelected:
            //               (suggestion) {
            //                 fuelVendor.text =
            //             suggestion['vendorName'];
            //                 vendorId = suggestion['vendorId'];
            //                 creditVisible = true;
            //                 isPaymentSelected[1] = true;
            //                 isPaymentSelected[0] = false;
            //           },
            //         ),
            //       ],
            //     ),
            //   ),
            // ),

            SizedBox(height: 5),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              margin: EdgeInsets.only(left: 50),
              child: CustomAutoComplete(
                  suggestionList: fuelVendorData,
                  controller: fuelVendor,
                  hintLabel: 'Fuel Vendor',
                  label: 'Fuel Vendor',
                  dataKeyName: 'vendorName',
                  iconData: Icons.person,
                  onItemSelected: (suggestion) {
                    fuelVendor.text = suggestion['vendorName'];
                    setState(() {
                      vendorId = suggestion['vendorId'].toString();
                      ;
                      creditVisible = true;
                      isPaymentSelected[1] = true;
                      isPaymentSelected[0] = false;
                      ownPetrolPump = suggestion["ownPetrolPump"].toString();
                    });

                    if (suggestion["ownPetrolPump"] == IS_OWNED_PETROL_PUMP) {
                      setState(() {
                        allowOwnPetrolPump = true;
                      });
                    } else {
                      setState(() {
                        allowOwnPetrolPump = false;
                      });
                    }
                    if (allowOwnPetrolPump) {
                      setState(() {
                        ownPetrolPump = suggestion["ownPetrolPump"].toString();
                      });
                      getFuelStockDetails(false);
                    }
                  },
                  onChanged: (pattern) {
                    //method for getting data from server
                    getFuelVendorDetails(pattern);
                  }),
            ),
            Visibility(
              visible: allowOwnPetrolPump,
              child: Text(
                fuelStockDetailsString,
                style: TextStyle(
                    color: Colors.blue,
                    fontSize: 14.0,
                    fontWeight: FontWeight.bold,
                    fontFamily: "WorkSansBold"),
              ),
            ),
            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    onChanged: (String text) {
                      calculateFuelPrice();
                      if (fuelLiter.text != '' && fuelLiter.text.length > 0) {
                        double val = double.parse(fuelLiter.text);
                        calculateFuelPrice();
                        if (val > maximumFuelCapacity) {
                          FlutterAlert.onErrorAlert(
                              context,
                              "Fuel Liter should not be grater than  $maximumFuelCapacity",
                              "Error");
                          setState(() {
                            fuelLiter.text = '0';
                          });
                          return false;
                        }
                      }
                    },
                    inputFormatters: [
                      WhitelistingTextInputFormatter(
                          RegExp(r'^(\d+)?\.?\d{0,2}')),
                    ],
                    keyboardType:
                        TextInputType.numberWithOptions(decimal: true),
                    maxLines: 1,
                    controller: fuelLiter,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'Liter',
                        hintText: 'Liter',
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.invert_colors,
                          color: Colors.greenAccent,
                        )),
                  ),
                ),
              ),
            ),

            SizedBox(height: 15),
            Align(
              alignment: Alignment.centerRight,
              child: Container(
                child: Icon(Icons.stars, color: Colors.red, size: 12),
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    onChanged: (String text) {
                      calculateFuelPrice();
                      if (fuelPrice.text != '' && fuelPrice.text.length > 0) {
                        double val = double.parse(fuelPrice.text);
                        calculateFuelPrice();
                        if (val >= maximumFuelPrice) {
                          FlutterAlert.onErrorAlert(
                              context,
                              "Fuel Price should not be grater than  $maximumFuelPrice",
                              "Error");
                          setState(() {
                            fuelPrice.text = '0';
                          });
                          return false;
                        }
                      }
                    },
                    enabled: !allowOwnPetrolPump,
                    inputFormatters: [
                      WhitelistingTextInputFormatter(
                          RegExp(r'^(\d+)?\.?\d{0,2}')),
                    ],
                    keyboardType:
                        TextInputType.numberWithOptions(decimal: true),
                    maxLines: 1,
                    controller: fuelPrice,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'Price/Unit',
                        hintText: 'Price/Unit',
                        hintStyle: TextStyle(color: Colors.black),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)),
                        icon: Icon(
                          Icons.attach_money,
                          color: Colors.blueGrey,
                        )),
                  ),
                ),
              ),
            ),
            SizedBox(height: 20),
            renderFuelPrice(),
            SizedBox(height: 10),
            Container(
              child: Padding(
                  padding: const EdgeInsets.only(left: 10),
                  child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Icon(
                          Icons.monetization_on,
                          color: Colors.blue,
                        ),
                        Text(
                          "Pay Method ",
                          style: new TextStyle(
                            fontSize: 17,
                            color: Colors.black,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Visibility(
                            visible: !creditVisible,
                            child: DialogButton(
                              width: 90,
                              height: 30,
                              child: Text(
                                "Cash",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 16,
                                    fontWeight: FontWeight.w700),
                              ),
                              gradient: LinearGradient(colors: [
                                Colors.greenAccent,
                                Colors.blueAccent
                              ]),
                              onPressed: null,
                            )),
                        Visibility(
                          visible: creditVisible,
                          child: ToggleButtons(
                            borderColor: Colors.black,
                            fillColor: Colors.greenAccent,
                            borderWidth: 0,
                            selectedBorderColor: Colors.black,
                            selectedColor: Colors.white,
                            borderRadius: BorderRadius.circular(10),
                            children: <Widget>[
                              Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: Text(
                                  'Cash',
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
                                  'Credit',
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
                                for (int i = 0;
                                    i < isPaymentSelected.length;
                                    i++) {
                                  if (i == index) {
                                    isPaymentSelected[i] = true;
                                  } else {
                                    isPaymentSelected[i] = false;
                                  }
                                }
                              });
                            },
                            isSelected: isPaymentSelected,
                          ),
                        )
                      ])),
            ),

            // SizedBox(height: 20),
            // Container(
            //   child: Padding(
            //     padding: const EdgeInsets.only(
            //         left: 10),
            //     child: Column(
            //       children: <Widget>[
            //         TypeAheadField(
            //           hideSuggestionsOnKeyboardHide:
            //           false,
            //           hideOnEmpty: true,
            //           textFieldConfiguration:
            //           TextFieldConfiguration(
            //             controller: driverName,
            //             decoration: InputDecoration(
            //                 border: OutlineInputBorder(
            //                     borderRadius:
            //                     BorderRadius
            //                         .circular(5.0)),
            //                 icon: Icon(
            //                   Icons.person,
            //                   color: Colors.cyan,
            //                 ),
            //                 hintText: 'Driver Name',
            //                 labelText: 'Driver Name'),
            //           ),
            //           suggestionsCallback:
            //               (pattern) async {
            //             return await getDriverNameSuggestions(
            //                 pattern, 1);
            //           },
            //           itemBuilder:
            //               (context, suggestion) {
            //             return ListTile(
            //               leading: Icon(Icons.person),
            //               title: Text(
            //                   suggestion['driverName']),
            //               // subtitle: Text('\$${suggestion['vehicleId']}'),
            //             );
            //           },
            //           onSuggestionSelected:
            //               (suggestion) {
            //             driverName.text =
            //             suggestion['driverName'];
            //             driverId =
            //             suggestion['driver_id'];
            //           },
            //         ),
            //       ],
            //     ),
            //   ),
            // ),

            SizedBox(height: 15),
            Visibility(
              visible: validateDriver1,
              child: Align(
                alignment: Alignment.centerRight,
                child: Container(
                  child: Icon(Icons.stars, color: Colors.red, size: 12),
                ),
              ),
            ),
            Container(
              child: CustomAutoComplete(
                  suggestionList: driverNameData,
                  controller: driverName,
                  hintLabel: 'Driver Name',
                  label: 'Driver Name',
                  dataKeyName: 'driverName',
                  iconData: Icons.person,
                  onItemSelected: (suggestion) {
                    setState(() {
                      driverName.text = suggestion['driverName'];
                      driverId = suggestion['driver_id'];
                    });
                  },
                  onChanged: (pattern) {
                    //method for getting data from server
                    getDriverNameDetails(pattern, 1);
                  }),
            ),

            // SizedBox(height: 15),
            // Visibility(
            //   visible: showDriver2,
            //   child: Container(
            //     child: Padding(
            //       padding: const EdgeInsets.only(
            //           top: 5.0, left: 10),
            //       child: Column(
            //         children: <Widget>[
            //           SizedBox(
            //             height: 10.0,
            //           ),
            //           TypeAheadField(
            //             hideSuggestionsOnKeyboardHide:
            //             false,
            //             hideOnEmpty: true,
            //             textFieldConfiguration:
            //             TextFieldConfiguration(
            //               controller: driver2name,
            //               decoration: InputDecoration(
            //                   border:
            //                   OutlineInputBorder(
            //                       borderRadius:
            //                       BorderRadius
            //                           .circular(
            //                           5.0)),
            //                   icon: Icon(
            //                     Icons.person,
            //                     color:
            //                     Colors.greenAccent,
            //                   ),
            //                   hintText: 'Driver2 Name',
            //                   labelText:
            //                   'Driver2 Name'),
            //             ),
            //             suggestionsCallback:
            //                 (pattern) async {
            //               return await getDriverNameSuggestions(
            //                   pattern, 2);
            //             },
            //             itemBuilder:
            //                 (context, suggestion) {
            //               return ListTile(
            //                 leading: Icon(Icons.person),
            //                 title: Text(suggestion[
            //                 'driverName']),
            //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
            //               );
            //             },
            //             onSuggestionSelected:
            //                 (suggestion) {
            //               driver2name.text =
            //               suggestion['driverName'];
            //               driver2Id =
            //               suggestion['driver_id'];
            //             },
            //           ),
            //         ],
            //       ),
            //     ),
            //   ),
            // ),

            SizedBox(height: showDriver2 == true ? 15 : 0),
            Visibility(
                visible: showDriver2,
                child: Container(
                  child: CustomAutoComplete(
                      suggestionList: driverNameData,
                      controller: driver2name,
                      hintLabel: 'Driver 2 Name',
                      label: 'Driver 2 Name',
                      dataKeyName: 'driverName',
                      iconData: Icons.person,
                      onItemSelected: (suggestion) {
                        setState(() {
                          driver2name.text = suggestion['driverName'];
                          driver2Id = suggestion['driver_id'];
                        });
                      },
                      onChanged: (pattern) {
                        //method for getting data from server
                        getDriverNameDetails(pattern, 2);
                      }),
                )),

            // SizedBox(height: 15),
            // Visibility(
            //   visible: showCleaner,
            //   child: Container(
            //     child: Padding(
            //       padding: const EdgeInsets.only(
            //           top: 5.0, left: 10),
            //       child: Column(
            //         children: <Widget>[
            //           SizedBox(
            //             height: 10.0,
            //           ),
            //           TypeAheadField(
            //             hideSuggestionsOnKeyboardHide:
            //             false,
            //             hideOnEmpty: true,
            //             textFieldConfiguration:
            //             TextFieldConfiguration(
            //               controller: cleaner,
            //               decoration: InputDecoration(
            //                   border:
            //                   OutlineInputBorder(
            //                       borderRadius:
            //                       BorderRadius
            //                           .circular(
            //                           5.0)),
            //                   icon: Icon(
            //                     Icons.person,
            //                     color: Colors.amber,
            //                   ),
            //                   hintText: 'Cleaner Name',
            //                   labelText:
            //                   'Cleaner Name'),
            //             ),
            //             suggestionsCallback:
            //                 (pattern) async {
            //               return await getCleanerNameSuggestions(
            //                   pattern);
            //             },
            //             itemBuilder:
            //                 (context, suggestion) {
            //               return ListTile(
            //                 leading: Icon(Icons.person),
            //                 title: Text(suggestion[
            //                 'driverName']),
            //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
            //               );
            //             },
            //             onSuggestionSelected:
            //                 (suggestion) {
            //               cleaner.text =
            //               suggestion['driverName'];
            //               cleanerId =
            //               suggestion['driver_id'];
            //             },
            //           ),
            //         ],
            //       ),
            //     ),
            //   ),
            // ),

            SizedBox(height: showCleaner == true ? 15 : 0),
            Visibility(
                visible: showCleaner,
                child: Container(
                  child: CustomAutoComplete(
                      suggestionList: cleanerNameData,
                      controller: cleaner,
                      hintLabel: 'Cleaner Name',
                      label: 'Cleaner Name',
                      dataKeyName: 'driverName',
                      iconData: Icons.person,
                      onItemSelected: (suggestion) {
                        setState(() {
                          cleaner.text = suggestion['driverName'];
                          cleanerId = suggestion['driver_id'];
                        });
                      },
                      onChanged: (pattern) {
                        //method for getting data from server
                        getCleanerNameDetails(pattern);
                      }),
                )),

            // SizedBox(height: 15),
            // Visibility(
            //   visible: showRoot,
            //   child: Container(
            //     child: Padding(
            //       padding: const EdgeInsets.only(
            //           top: 5.0, left: 10),
            //       child: Column(
            //         children: <Widget>[
            //           SizedBox(
            //             height: 10.0,
            //           ),
            //           TypeAheadField(
            //             hideSuggestionsOnKeyboardHide:
            //             false,
            //             hideOnEmpty: true,
            //             textFieldConfiguration:
            //             TextFieldConfiguration(
            //               controller: routeService,
            //               decoration: InputDecoration(
            //                   border:
            //                   OutlineInputBorder(
            //                       borderRadius:
            //                       BorderRadius
            //                           .circular(
            //                           5.0)),
            //                   icon: Icon(
            //                     Icons.directions,
            //                     color: Colors.black,
            //                   ),
            //                   hintText: 'Route Service',
            //                   labelText:
            //                   'Route Service'),
            //             ),
            //             suggestionsCallback:
            //                 (pattern) async {
            //               return await getRouteServiceSuggestions(
            //                   pattern);
            //             },
            //             itemBuilder:
            //                 (context, suggestion) {
            //               return ListTile(
            //                 leading:
            //                 Icon(Icons.directions),
            //                 title: Text(suggestion[
            //                 'routeName']),
            //                 // subtitle: Text('\$${suggestion['vehicleId']}'),
            //               );
            //             },
            //             onSuggestionSelected:
            //                 (suggestion) {
            //               routeService.text =
            //               suggestion['routeName'];
            //               routeServiceId =
            //               suggestion['routeID'];
            //             },
            //           ),
            //         ],
            //       ),
            //     ),
            //   ),
            // ),

            SizedBox(height: showRoot == true ? 15 : 0),
            Visibility(
                visible: showRoot,
                child: Container(
                  child: CustomAutoComplete(
                      suggestionList: routeServiceData,
                      controller: routeService,
                      hintLabel: 'Route Service',
                      label: 'Route Service',
                      dataKeyName: 'routeName',
                      iconData: Icons.directions,
                      onItemSelected: (suggestion) {
                        setState(() {
                          routeService.text = suggestion['routeName'];
                          routeServiceId = suggestion['routeID'];
                        });
                      },
                      onChanged: (pattern) {
                        //method for getting data from server
                        getRouteServiceDetails(pattern);
                      }),
                )),

            SizedBox(height: 15),
            Container(
              child: Padding(
                  padding: const EdgeInsets.only(left: 10),
                  child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Icon(
                          Icons.local_gas_station,
                          color: Colors.blueAccent,
                        ),
                        Text(
                          "Fuel Tank ",
                          style: new TextStyle(
                              fontSize: 17,
                              color: Colors.black,
                              fontWeight: FontWeight.w600),
                        ),
                        ToggleButtons(
                          borderColor: Colors.black,
                          fillColor: Colors.greenAccent,
                          borderWidth: 1,
                          selectedBorderColor: Colors.black,
                          selectedColor: Colors.white,
                          borderRadius: BorderRadius.circular(5),
                          children: <Widget>[
                            Padding(
                              padding: const EdgeInsets.all(5.0),
                              child: Text(
                                'Full',
                                style: TextStyle(
                                    fontSize: 17,
                                    color: Colors.black,
                                    fontWeight: FontWeight.w600),
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Text(
                                'Partial',
                                style: TextStyle(
                                    fontSize: 17,
                                    color: Colors.black,
                                    fontWeight: FontWeight.w600),
                              ),
                            ),
                          ],
                          onPressed: (int index) {
                            setState(() {
                              for (int i = 0; i < isTankSelected.length; i++) {
                                if (i == index) {
                                  isTankSelected[i] = true;
                                } else {
                                  isTankSelected[i] = false;
                                }
                              }
                            });
                          },
                          isSelected: isTankSelected,
                        ),
                      ])),
            ),

            SizedBox(height: 20),
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
                              fontSize: 17,
                              color: Colors.black,
                              fontWeight: FontWeight.w600),
                        ),
                        DialogButton(
                          width: 120,
                          height: 30,
                          child: Text(
                            "Browse",
                            style: TextStyle(color: Colors.white, fontSize: 17),
                          ),
                          gradient: LinearGradient(
                              colors: [Colors.greenAccent, Colors.blueAccent]),
                          onPressed: openBottomSheet,
                        )
                      ])),
            ),

            SizedBox(height: 20),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: Container(
                  child: TextField(
                    maxLines: 3,
                    controller: fuelEntryComments,
                    style: TextStyle(color: Colors.black),
                    decoration: InputDecoration(
                        labelText: 'Comments',
                        hintText: 'Comments',
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

            SizedBox(height: 15),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 5.0, left: 10),
                child: new RaisedButton(
                  padding: const EdgeInsets.all(8.0),
                  textColor: Colors.white,
                  color: Colors.pink,
                  onPressed: checkFinalValidation,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 2.0, horizontal: 15.0),
                    child: Text(
                      "Add Fuel",
                      style: TextStyle(
                          fontSize: 18,
                          color: Colors.white,
                          fontWeight: FontWeight.w500),
                    ),
                  ),
                ),
              ),
            ),

            SizedBox(height: 25),
          ])))),
    );
  }

  Widget showFuelPaymentDetails(data, context) {
    Map fuelData = new Map();
    fuelData = data;
    if (fuelList.isNotEmpty) {
      return Column(
        children: <Widget>[
          Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        new IconButton(
                          icon: new Icon(
                            Icons.info_outline,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showFuelPaidDetails(
                                context, fuelData, "Fuel Details");
                          },
                        ),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                              new TextSpan(
                                  text: " FuelAmount : ",
                                  style: new TextStyle(
                                    color: AppTheme.darkText,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )),
                              new TextSpan(
                                  text:
                                      " \u20B9${data['fuel_amount'].toString()} ",
                                  style: new TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.w700,
                                    fontSize: 16,
                                  )),
                            ],
                          ),
                        ),
                        new IconButton(
                          icon: new Icon(
                            Icons.delete,
                            color: Colors.redAccent,
                          ),
                          onPressed: () {
                            deleteFuelDetails(fuelData);
                          },
                        ),
                      ])),
            ),
          ),
        ],
      );
    } else {
      return Container();
    }
  }

  Future<List> getFuelTypeSuggestions(String query) async {
    getFuelTypeDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(fuelTypeData.length, (index) {
      return fuelTypeData[index];
    });
  }

  getFuelTypeDetails(String str) async {
    fuelTypeList = new Map();
    setState(() {
      fuelTypeData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.FUEL_TYPE_LIST, data, URI.LIVE_URI);

      if (response != null) {
        if (response["fuelTypesList"] != null) {
          for (int i = 0; i < response["fuelTypesList"].length; i++) {
            var obj = {
              "fuelTypeId": response['fuelTypesList'][i]['fid'].toString(),
              "fuelTypeName": response['fuelTypesList'][i]['vFuel'].toString()
            };
            fuelTypeList[obj['fuelTypeId']] = obj;

            setState(() {
              fuelTypeData = fuelTypeList.values.toList();
            });
          }
        } else {
          setState(() {
            fuelTypeData = [];
          });
        }
      }
    } else {
      setState(() {
        fuelTypeData = [];
      });
    }
  }

  check(response) {
    Map resp = response;
    if (resp != null &&
        resp.containsKey("isAlreadyExist") &&
        !response["isAlreadyExist"]) {
      return true;
    } else {
      return false;
    }
  }

  getActiveTripSheetAndBackDateData(bool isCalledOnLoad) async {
    if (vehicleId != null && vehicleId.isNotEmpty && int.parse(vehicleId) > 0) {
      var activeTSAndBackDateData = {
        'fromMob': 'true',
        'companyId': companyId,
        'userId': userId,
        'vid': vehicleId,
        'fuelDate': fuelEntryDate.text,
        'fuelTime': fuelEntryTime.text,
        'vehicle_ExpectedOdameter': vehicle_ExpectedOdameter.toString(),
        'bindMinMaxOdometerOnTripSheet':
            bindMinMaxOdometerOnTripSheet.toString()
      };

      var response = await ApiCall.getDataFromApi(
          URI.GET_ACTIVE_TS_AND_BACKDATE,
          activeTSAndBackDateData,
          URI.LIVE_URI,
          context);

      if (check(response)) {
        blockSaveFuelEntry = false;
        if (allowManualOdometerEntry == false) {
          if (response['nextFuelDeatils'] != null) {
            if (response['previousFuelDeatils'] != null) {
              setState(() {
                creatingBackDateFuelEntry = true;
                previousFuelEntryCapacity =
                    response['previousFuelDeatils']['fuel_liters'];
                nextFuelIdOfBackDate = response['nextFuelDeatils']['fuel_id'];

                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['previousFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                  odoMeter.text =
                      response['nextFuelDeatils']['fuelMeter'].toString();
                }

                nextFuelMeterOfBackDate =
                    response['nextFuelDeatils']['fuelMeter'];
                if (!isCalledOnLoad) {
                  backdateBetweenPreviousAndNext =
                      'Note : You are Creating Back Date Fuel Entry. ' +
                          'Previous Fuel Entry Is FT - ${response['previousFuelDeatils']['fuel_Number']} ' +
                          'And Next Fuel Entry Is FT - ${response['nextFuelDeatils']['fuel_Number']}. ' +
                          'Odameter Deatils Of Next Fuel Entry - ${response['nextFuelDeatils']['fuel_Number']} ' +
                          'Will Be Updated Automatically With Respect To This Entry.';
                  showNote = true;
                }

                int minVal =
                    response['previousFuelDeatils']['lastFuelOdometer'] + 1;
                int maxVal = response['nextFuelDeatils']['fuelMeter'] - 1;
                odometerRange =
                    'Odometer Range : ${minVal} - ${maxVal} with respect ' +
                        'to fuel entries FT - ${response['previousFuelDeatils']['fuel_Number']} ' +
                        'and FT - ${response['nextFuelDeatils']['fuel_Number']} ';
                showRange = true;

                minOdometerVal = minVal;
                maxOdometerVal = maxVal;
              });
            } else {
              setState(() {
                readOnlyOpenOdometer = true;
                creatingBackDateFuelEntry = true;
                firstFuelEntry = true;
                nextFuelIdOfBackDate = response['nextFuelDeatils']['fuel_id'];

                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['nextFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                  odoMeter.text =
                      response['nextFuelDeatils']['fuelMeter'].toString();
                }

                nextFuelMeterOfBackDate =
                    response['nextFuelDeatils']['fuelMeter'];
                if (!isCalledOnLoad) {
                  backdateBetweenPreviousAndNext =
                      'Note : You are Creating Back Date Fuel Entry. ' +
                          'There is no Previous Entry ' +
                          'And Next Fuel Entry Is FT - ${response['nextFuelDeatils']['fuel_Number']}. ' +
                          'Odameter Deatils Of Next Fuel Entry - ${response['nextFuelDeatils']['fuel_Number']} ' +
                          'Will Be Updated Automatically With Respect To This Entry.';
                  showNote = true;
                }

                odometerRange = 'Odometer Range : Please Add Odometer below ' +
                    '${response['nextFuelDeatils']['fuelMeter']} Range with respect ' +
                    'to fuel entry FT - ${response['nextFuelDeatils']['fuel_Number']}. ';
                showRange = true;

                if (response['nextFuelDeatils']['lastFuelOdometer'] -
                        response['nextFuelDeatils']
                            ['vehicle_ExpectedOdameter'] >
                    0) {
                  minOdometerVal = response['nextFuelDeatils']
                          ['lastFuelOdometer'] -
                      response['nextFuelDeatils']['vehicle_ExpectedOdameter'];
                } else {
                  minOdometerVal = 0;
                }

                int maxVal = response['nextFuelDeatils']['fuelMeter'] - 1;
                maxOdometerVal = maxVal;
              });
            }
          } else {
            setState(() {
              if (response['previousFuelDeatils'] != null) {
                if (!isCalledOnLoad) {
                  openOdoMeter.text = response['previousFuelDeatils']
                          ['lastFuelOdometer']
                      .toString();
                }

                odometerRange = 'Odometer Range : Please Enter Odometer Above ' +
                    '${response['previousFuelDeatils']['lastFuelOdometer']} Range with respect ' +
                    'to fuel entry FT - ${response['previousFuelDeatils']['fuel_Number']}. ';
                showRange = true;

                int minVal =
                    response['previousFuelDeatils']['lastFuelOdometer'] + 1;
                int maxVal = response['previousFuelDeatils']
                        ['lastFuelOdometer'] +
                    response['previousFuelDeatils']['vehicle_ExpectedOdameter'];

                minOdometerVal = minVal;
                maxOdometerVal = maxVal;
              }

              if (actualOdameterReading > 0) {
                if (!isCalledOnLoad) {
                  odoMeter.text = actualOdameterReading.toString();
                  // This will reset back to current odometer when user changes time from backdate to non backdate
                }
              }

              nextFuelIdOfBackDate = 0;
              nextFuelMeterOfBackDate = 0;
            });
          }
        }

        if (response['tripActive'] != null) {
          tripSheetList = new Map();
          setState(() {
            tripSheetData = [];
          });
          var obj = {
            "tripSheetID": response['tripActive']['tripSheetID'].toString(),
            "tripSheetNumber": "TS - " +
                response['tripActive']['tripSheetNumber'].toString() +
                " : " +
                response['tripActive']['tripOpenDate'].toString() +
                " - " +
                response['tripActive']['closetripDate'].toString(),
          };
          tripSheetList[obj['tripSheetID']] = obj;

          setState(() {
            showActiveTSNote = true;
            tripSheetData = tripSheetList.values.toList();
            selectedTripSheetNumberInt = response['tripActive']['tripSheetID'];
            selectedTripSheetNumber = selectedTripSheetNumberInt.toString();
          });
        }
      } else {
        if (response != null &&
            response["isAlreadyExist"] != null &&
            response["isAlreadyExist"]) {
          if (setFuelEntryDefaultDateAndTime != null &&
              setFuelEntryDefaultDateAndTime) {
            setDefaultDateAndTime();
          }
          blockSaveFuelEntry = true;
          FlutterAlert.onErrorAlert(
              context, "Please Select Different Time !", "Error");
        }
      }
    }
  }

  void addKMPL(BuildContext context) {
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
      title:
          "Vehicle Km/L Details Haven't Been Added. Please Update Km/L Details to Continue With Fuel Entries.",
      content: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          SizedBox(height: 15),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  enabled: false,
                  maxLines: 1,
                  controller: vehicleNumber,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Vehicle Number',
                      hintText: "Vehicle Number",
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.directions_bus,
                        color: Colors.pink,
                      )),
                ),
              ),
            ),
          ),
          SizedBox(height: 5),
          Align(
            alignment: Alignment.centerRight,
            child: Container(
              child: Icon(Icons.stars, color: Colors.red, size: 12),
            ),
          ),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  keyboardType: TextInputType.number,
                  maxLines: 1,
                  controller: mileageFrom,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Expected Mileage From',
                      hintText: 'Expected Mileage From',
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.invert_colors,
                        color: Colors.greenAccent,
                      )),
                ),
              ),
            ),
          ),
          SizedBox(height: 5),
          Align(
            alignment: Alignment.centerRight,
            child: Container(
              child: Icon(Icons.stars, color: Colors.red, size: 12),
            ),
          ),
          Container(
            child: Padding(
              padding: const EdgeInsets.only(top: 5.0, left: 10),
              child: Container(
                child: TextField(
                  keyboardType: TextInputType.number,
                  maxLines: 1,
                  controller: mileageTo,
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                      labelText: 'Expected Mileage To',
                      hintText: 'Expected Mileage To',
                      hintStyle: TextStyle(color: Colors.black),
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0)),
                      icon: Icon(
                        Icons.invert_colors,
                        color: Colors.purpleAccent,
                      )),
                ),
              ),
            ),
          ),
          Center(
            child: Container(
                height: 50,
                width: _width - 130,
                margin: EdgeInsets.only(top: 20.0, left: 10),
                decoration: new BoxDecoration(
                  color: Colors.pinkAccent,
                  borderRadius: BorderRadius.all(Radius.circular(5.0)),
                ),
                child: MaterialButton(
                  highlightColor: Colors.transparent,
                  splashColor: Colors.purpleAccent,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 10.0, horizontal: 42.0),
                    child: Text(
                      "Add Km/L",
                      style: TextStyle(
                          fontSize: 22,
                          color: AppTheme.white,
                          fontWeight: FontWeight.w700),
                    ),
                  ),
                  onPressed: saveKMPLDetails,
                )),
          ),
        ],
      ),
    ).show();
  }

  Future<List> getFuelVendorSuggestions(String query) async {
    getFuelVendorDetails(query);
    await Future.delayed(Duration(seconds: 4));

    return List.generate(fuelVendorData.length, (index) {
      return fuelVendorData[index];
    });
  }

  getFuelVendorDetails(String str) async {
    fuelVendorList = new Map();
    setState(() {
      creditVisible = false;
      fuelVendorData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.FUEL_VENDOR_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["vendorList"] != null) {
          setState(() {
            fuelVendorData = response["vendorList"];
          });
        } else {
          setState(() {
            fuelVendorData = [];
          });
        }
      }
    } else {
      setState(() {
        fuelVendorData = [];
      });
    }
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
    if (checkDriver == 1) {
      driverId = '';
    } else {
      driver2Id = '';
    }
    setState(() {
      driverNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.DRIVER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["driverList"] != null) {
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

  Future<List> getCleanerNameSuggestions(String query) async {
    getCleanerNameDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(cleanerNameData.length, (index) {
      return cleanerNameData[index];
    });
  }

  getCleanerNameDetails(String str) async {
    cleanerNameList = new Map();
    cleanerId = '';
    setState(() {
      cleanerNameData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.CLEANER_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["cleanerList"] != null) {
          for (int i = 0; i < response["cleanerList"].length; i++) {
            var obj = {
              "driver_id": response['cleanerList'][i]['driver_id'].toString(),
              "driverName": response['cleanerList'][i]['driver_empnumber']
                      .toString() +
                  '-' +
                  response['cleanerList'][i]['driver_firstname'].toString() +
                  ' ' +
                  response['cleanerList'][i]['driver_Lastname'].toString()
            };
            cleanerNameList[obj['driver_id']] = obj;
            setState(() {
              cleanerNameData = cleanerNameList.values.toList();
            });
          }
        } else {
          setState(() {
            cleanerNameData = [];
          });
        }
      }
    } else {
      setState(() {
        cleanerNameData = [];
      });
    }
  }

  Future<List> getRouteServiceSuggestions(String query) async {
    getRouteServiceDetails(query);
    await Future.delayed(Duration(seconds: 2));

    return List.generate(routeServiceData.length, (index) {
      return routeServiceData[index];
    });
  }

  getRouteServiceDetails(String str) async {
    routeServiceList = new Map();
    routeServiceId = '';
    setState(() {
      routeServiceData = [];
    });
    if (str != null && str.length >= 2) {
      var data = {'userId': userId, 'companyId': companyId, 'term': str};
      var response = await ApiCall.getDataWithoutLoader(
          URI.TRIP_ROUTE_NAME_LIST, data, URI.LIVE_URI);
      if (response != null) {
        if (response["TripRouteList"] != null) {
          for (int i = 0; i < response["TripRouteList"].length; i++) {
            var obj = {
              "routeID": response['TripRouteList'][i]['routeID'].toString(),
              "routeName": response['TripRouteList'][i]['routeName'].toString()
            };
            routeServiceList[obj['routeID']] = obj;
            setState(() {
              routeServiceData = routeServiceList.values.toList();
            });
          }
        } else {
          setState(() {
            routeServiceData = [];
          });
        }
      }
    } else {
      setState(() {
        routeServiceData = [];
      });
    }
  }

  void showFuelPaidDetails(BuildContext context, Map data, String title) {
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
                    text: " Fuel Id : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " FT - ${data['fuel_Number'].toString()} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Vehicle : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['vehicle_registration']} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Usage : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['fuel_usage'].toString()} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Volume : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['fuel_liters'].toString()} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${data['fuel_amount'].toString()} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Cost Per/Liter : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: " ${data['fuel_price'].toString()} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    text: " Date : ",
                    style: new TextStyle(
                      color: AppTheme.darkText,
                      fontWeight: FontWeight.w700,
                    )),
                new TextSpan(
                    text: "${data['fuel_date']}",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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

  Future<bool> deleteFuelDetails(fuelData) {
    Alert(
      context: context,
      type: AlertType.info,
      title: "Fuel Info",
      desc: "Do you want to Delete Fuel Details ?",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => deleteRequest(fuelData),
          gradient: LinearGradient(colors: [Colors.green, Colors.deepPurple]),
        ),
        DialogButton(
          child: Text(
            "No",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.pop(context),
          gradient: LinearGradient(colors: [Colors.red, Colors.black]),
        )
      ],
    ).show();
  }

  deleteRequest(fuelData) async {
    Navigator.pop(context);

    var deleteFuelData = {
      'email': email,
      'userId': userId,
      'companyId': companyId,
      'tripsheetId': tripsheetId.toString(),
      'fuelId': '${fuelData['fuel_id']}',
    };

    var response = await ApiCall.getDataFromApi(
        URI.REMOVE_FUEL_TRIPSHEET, deleteFuelData, URI.LIVE_URI, context);

    FlutterAlert.onSuccessAlert(
        context, " Tripsheet Fuel Deleted Successfully !", " Tripsheet Fuel ");

    getTripsheetShowData(tripsheetId);
  }

  Widget showTripInfo(context) {
    return Column(
      children: <Widget>[
        GestureDetector(
          onTap: () {
            showTripExtraDetails(context, "Trip Details");
          },
          child: Card(
            color: Colors.white70,
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
                  child: Row(children: [
                    new IconButton(
                      icon: new Icon(
                        Icons.directions_bus,
                        color: Colors.green,
                      ),
                      onPressed: () {
                        showTripExtraDetails(context, "Trip Details");
                      },
                    ),
                    Text(" Trip Details ",
                        style: new TextStyle(
                          color: Colors.purple,
                          fontWeight: FontWeight.w700,
                          fontSize: 16,
                        )),
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
                  ])),
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            showDriverExtraDetails(context, "Driver Details");
          },
          child: Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.start,
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
                        Text(" Driver Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )),
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
                      ])),
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            showDispatchExtraDetails(context, "Dispatch Details");
          },
          child: Card(
            color: Colors.white70,
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
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        new IconButton(
                          icon: new Icon(
                            Icons.access_time,
                            color: Colors.green,
                          ),
                          onPressed: () {
                            showDispatchExtraDetails(
                                context, "Dispatch Details");
                          },
                        ),
                        Text(" Dispatch Details ",
                            style: new TextStyle(
                              color: Colors.purple,
                              fontWeight: FontWeight.w700,
                              fontSize: 15,
                            )),
                        Container(
                          margin: const EdgeInsets.only(left: 94.0),
                          child: IconButton(
                            icon: new Icon(
                              Icons.arrow_forward,
                              color: Colors.green,
                            ),
                            onPressed: () {
                              showDispatchExtraDetails(
                                  context, "Dispatch Details");
                            },
                          ),
                        ),
                      ])),
            ),
          ),
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
                    )),
                new TextSpan(
                    text: " ${tripOpenDate} to ${tripCloseDate} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: routeAttendancePoint != null
                        ? routeAttendancePoint.toString()
                        : "-",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${routeTotalLiter} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripBookref != null ? tripBookref.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${vehicle_Group} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${tripOpeningKM} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripClosingKM.toString() != 'null'
                        ? tripClosingKM.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripUsageKM.toString() != 'null'
                        ? tripUsageKM.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${tripFristDriverName} / ${tripFristDriverMobile} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripSecDriverName.toString() != 'null null'
                        ? tripSecDriverName.toString() +
                            "/" +
                            tripSecDriverMobile.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripCleanerName.toString() != 'null null'
                        ? tripCleanerName.toString() +
                            "/" +
                            tripCleanerMobile.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripFristDriverRoutePoint != null
                        ? tripFristDriverRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripSecDriverRoutePoint != null
                        ? tripSecDriverRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: tripCleanerRoutePoint != null
                        ? tripCleanerRoutePoint.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedBy} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedLocation}  ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: " ${dispatchedByTime} ",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: closedBy != null ? closedBy.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: cloesdLocation != null
                        ? cloesdLocation.toString()
                        : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: closedByTime != null ? closedByTime.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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
                    )),
                new TextSpan(
                    text: Remark != null ? Remark.toString() : "--",
                    style: new TextStyle(
                      color: Colors.purple,
                      fontWeight: FontWeight.w700,
                    )),
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

  Widget renderFuelPrice() {
    return Container(
        margin: EdgeInsets.only(left: 10),
        child: TextField(
          onChanged: (String text) {},
          enabled: false,
          keyboardType: TextInputType.number,
          maxLines: 1,
          controller: fuelAmount,
          style: TextStyle(color: Colors.black),
          decoration: InputDecoration(
              labelText: 'Total Amount : ',
              hintText: 'Total Amount : ',
              hintStyle: TextStyle(color: Colors.black),
              border:
                  OutlineInputBorder(borderRadius: BorderRadius.circular(5.0)),
              icon: Icon(
                Icons.attach_money,
                color: Colors.blueGrey,
              )),
        ));
  }
}
