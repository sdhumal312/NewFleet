import 'package:fleetop/Dashboard/dashboard.dart';
import 'package:fleetop/Driver/createDriverRenewal.dart';
import 'package:fleetop/Driver/showDriverRenewal.dart';
import 'package:fleetop/FuelEntry/fuelentry.dart';
import 'package:fleetop/Home_screen/homescreen.dart';
import 'package:fleetop/Issues/createIssue.dart';
import 'package:fleetop/Issues/editIssue.dart';
import 'package:fleetop/Issues/showIssue.dart';
import 'package:fleetop/PickAndDrop/createPickOrDrop.dart';
import 'package:fleetop/PickAndDrop/editPickOrDrop.dart';
import 'package:fleetop/PickAndDrop/showPickOrDropData.dart';
import 'package:fleetop/RenewalReminder/createRenewalReminder.dart';
import 'package:fleetop/RenewalReminder/searchRenewalByVehicle.dart';
import 'package:fleetop/RenewalReminder/showRenewalReminder.dart';
import 'package:fleetop/SearchScreen/SearchScreen.dart';
import 'package:fleetop/Tripsheet/TripsheetCreate.dart';
import 'package:fleetop/Inventory/FuelInventory/FuelInventory.dart';
import 'package:fleetop/WorkOrder/WorkOrderHomeScreen.dart';

typedef T Constructor<T>();

final Map<String, Constructor<Object>> _constructors =
    <String, Constructor<Object>>{};

void register<T>(Constructor<T> constructor) {
  _constructors[T.toString()] = constructor;
}

class ClassBuilder {
  static void registerClasses() {
    register<HomeScreen>(() => HomeScreen());
    register<FuelEntry>(() => FuelEntry());
    register<Dashboard>(() => Dashboard());
    register<TripsheetCreate>(() => TripsheetCreate());
    register<CreatePickOrDrop>(() => CreatePickOrDrop());
    register<CreateRenewalReminder>(() => CreateRenewalReminder());
    register<CreateDriverRenewal>(() => CreateDriverRenewal());
    register<CreateIssue>(() => CreateIssue());
    register<FuelInventory>(() => FuelInventory());
    register<WorkOrderHomeScreen>(() => WorkOrderHomeScreen());
    register<SearchScreen>(() => SearchScreen());
  }

  static dynamic fromString(String type) {
    return _constructors[type]();
  }
}
