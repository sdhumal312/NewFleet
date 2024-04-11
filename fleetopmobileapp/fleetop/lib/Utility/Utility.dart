import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';

class Utility {
  static List listDataMaptoObject = new List();
  static var ListTemp = new List();
  final format = DateFormat("dd-MM-yyyy");

  static List getAllValuesFromList(Map data) {
    List values = new List();
    if (data != null && data.isNotEmpty) {
      data.forEach((key, value) => values.add(value));
      return values;
    } else {
      return null;
    }
  }

  static List mapToListAndReversed(Map data) {
    List reversedData = new List();
    if (data != null && data.isNotEmpty) {
      if (data != null && data.isNotEmpty) {
        data.forEach((key, value) => reversedData.add(value));
        return reversedData.reversed.toList();
      }
    } else {
      return null;
    }
  }

  static List mapToList(Map data) {
    List listData = new List();
    if (data != null && data.isNotEmpty) {
      if (data != null && data.isNotEmpty) {
        data.forEach((key, value) => listData.add(value));
        return listData.toList();
      }
    } else {
      return null;
    }
  }

  static void makeMaptoListObj(var key, var val) {
    ListTemp.add(val);
    listDataMaptoObject.add(val);
  }

  static List mapToListObject(Map data) {
    if (data != null && data.isNotEmpty) {
      if (data != null && data.isNotEmpty) {
        data.forEach((key, value) => ListTemp.add(value));
        return ListTemp;
      }
    } else {
      return null;
    }
  }

  static List mapToListObjectData(Map data) {
    if (data != null && data.isNotEmpty) {
      if (data != null && data.isNotEmpty) {
        var obj = {};
        data.forEach((key, value) => {
              obj.clear(),
              obj = {'id': key, 'label': value},
              ListTemp.add(obj)
            });
        return ListTemp.toSet().toList();
      }
    } else {
      return null;
    }
  }

  static List getAllKeysFromList(Map data) {
    List keys = new List();
    if (data != null && data.isNotEmpty) {
      data.forEach((key, value) => keys.add(key));
      return keys;
    } else {
      return null;
    }
  }

  static List getUniqueListData(List data) {
    List All_Images_Data = new List();
    if (data != null && data.isNotEmpty) {
      data = data.toSet().toList();
      data.forEach((element) => All_Images_Data.add(element));
      return All_Images_Data;
    } else {
      return null;
    }
  }

  static String getUniqueValueFromListAndJoin(List data) {
    String str = "";
    if (data != null && data.isNotEmpty) {
      data = data.toSet().toList();
      for (int i = 0; i < data.length; i++) {
        str = str + "," + data[i].toString();
      }
      if (str != null && str.length > 0 && str.startsWith(",")) {
        str = str.substring(1, str.length);
      } else if (str != null && str.length > 0 && str.endsWith(",")) {
        str = str.substring(str.length, str.length - 1);
      }
      return str;
    } else {
      return null;
    }
  }

  static bool isNaN(String str) {
    try {
      var value = double.parse(str);
    } on FormatException {
      return false;
    } finally {
      return true;
    }
  }

  static String timestampToString(int timestamp) {
    if (timestamp != null && timestamp > 0) {
      var date = DateTime.fromMillisecondsSinceEpoch(timestamp);
      var formattedDate = DateFormat.yMMMd().format(date);
      return formattedDate.toString();
    } else {
      return "";
    }
  }

  static List mapToListNew(Map data) {
    List listData = new List();
    if (data != null && data.isNotEmpty) {
      if (data != null && data.isNotEmpty) {
        data.forEach((key, value) => listData.add(value));
        return listData;
      }
    } else {
      return null;
    }
  }

  static String getNumberInIndianRupees(double amt) {
    var format = NumberFormat.currency(locale: 'HI');
    if (amt != null) {
      return (format.format(amt)).replaceAll('INR', '');
    } else {
      return "";
    }
  }

  static String getReadableFormatAmount(String amt) {
    String collection = "";
    if (amt != null && amt.isNotEmpty) {
      collection = amt.replaceAllMapped(
          new RegExp(r'(\d{1,3})(?=(\d{3})+(?!\d))'), (Match m) => '${m[1]},');
      return collection;
    } else {
      return collection = "";
    }
  }

  static String timestampToStringWithDateFromat(int timestamp) {
    final DateFormat formatter = DateFormat('dd-MM-yyyy');

    if (timestamp != null && timestamp > 0) {
      var date = DateTime.fromMillisecondsSinceEpoch(timestamp);
      final String formatted = formatter.format(date);
      var formattedDate = DateFormat().format(date);
      return formatted.toString();
    } else {
      return "";
    }
  }

  static String getNotificationData(String notiData) {
    List sptData = new List();
    String str = "";
    if (notiData != null && notiData.length > 0 && notiData.contains("~")) {
      sptData = notiData.split("~");
      if (sptData != null && sptData.isNotEmpty) {}
      str = sptData[0];
      return str;
    } else if (int.parse(notiData) == 4) {
      return "";
    } else {
      return notiData;
    }
  }

  static String getDateFromTimestamp(var timeStamp) {
    var dd = '0';
    var dm = '0';
    var yy = '0';
    String actDate = "";
    if (timeStamp != null) {
      var date = new DateTime.fromMicrosecondsSinceEpoch(timeStamp * 1000);
      dd = date.day.toString();
      dm = date.month.toString();
      if (dd.length == 1) {
        dd = '0' + dd;
      }
      if (dm.length == 1) {
        dm = '0' + dm;
      }

      yy = date.year.toString();
      actDate = yy + '-' + dm + '-' + dd.toString();
    } else {
      actDate = DateTime.now().toString();
    }
    return actDate;
  }

  static List getListToUnqueList(List data) {
    Map unData = new Map();
    List unDataList = new List();
    try {
      if (data != null && data.isNotEmpty) {
        for (var dd in data) {
          unData[dd["branchId"]] = dd;
        }
        if (unData != null && unData.isNotEmpty) {
          unDataList = mapToListNew(unData);
        }
      }
      return unDataList;
    } catch (e) {}
  }

  static bool hasKeyInConfig(Map connfiguration, String key) {
    try {
      if (connfiguration != null && connfiguration.isNotEmpty) {
        if (connfiguration.containsKey(key)) {
          return connfiguration[key];
        } else {
          return false;
        }
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }

  static String checkAndSet(String str) {
    if (str == null || str == "null") {
      return "";
    }
    return str;
  }

  static String getLimitedData(var data) {
    try {
      if (data != null && data.toString().length > 0) {
        double st = data;
        return st.toStringAsFixed(2);
      } else {
        return "0.0";
      }
    } catch (e) {
      return "0.0";
    }
  }
}
