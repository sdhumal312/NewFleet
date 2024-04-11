import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/material.dart';
import 'package:flutter_image_compress/flutter_image_compress.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:math';

class FileUtility {
  static String PDF_FILE = "pdf";
  static String EXCEL_FILE =
      "vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String IMAGE_FILE = "image jpg";
  static int PDF_FILE_ID = 1;
  static int EXCEL_FILE_ID = 2;
  static int IMAGE_FILE_ID = 3;
  static int DOC_FILE_ID = 4;
  static String PDF_FILE_EXTENSION = ".pdf";
  static String EXCEL_FILE_EXTENSION = ".xlsx";
  static String DOC_FILE_EXTENSION = ".docx";
  static List<String> fileExtensions = new List();
  static Map<int, String> fileData = new Map<int, String>();
  static Uint8List dataFromBase64String(String base64String) {
    return base64Decode(base64String);
  }

  static Future<void> shareExcelFile(String pdfString, String fileName) async {
    try {
      String fn = fileName + ".xlsx";
      Uint8List uni = dataFromBase64String(pdfString);
      await Share.file(fileName, fn, uni, 'text/xlsx');
    } catch (e) {
      print('error: $e');
    }
  }

  static Future<void> sharePDFFile(String pdfString, String fileName) async {
    try {
      String fn = fileName + ".pdf";
      Uint8List uni = dataFromBase64String(pdfString);
      await Share.file(fileName, fn, uni, 'text/pdf');
    } catch (e) {
      print('error: $e');
    }
  }

  static Future<void> shareImage(
      String base64ImageString, String fileName) async {
    try {
      Uint8List uni = dataFromBase64String(base64ImageString);
      await Share.file(fileName, "abc.png", uni, 'image/png');
    } catch (e) {
      print('error: $e');
    }
  }

  static Future<String> createFilesFromString(
      String base64Data, String extension) async {
    int fileId = getFileExtensionId(extension);
    String fileExtension = getExtensionNameFromId(fileId);
    Uint8List bytes = base64.decode(base64Data);
    String dir = (await getApplicationDocumentsDirectory()).path;
    File file = File("$dir/" +
        DateTime.now().millisecondsSinceEpoch.toString() +
        fileExtension);
    await file.writeAsBytes(bytes);
    return file.path;
  }

  static int getFileExtensionId(String extension) {
    int fileId = 0;
    if (extension != null && extension.isNotEmpty) {
      fileExtensions = extension.split("/");
      if (fileExtensions != null && fileExtensions.isNotEmpty) {
        if (fileExtensions.length == 2) {
          if (fileExtensions[1] == "pdf") {
            fileId = PDF_FILE_ID;
          } else if (fileExtensions[1] == EXCEL_FILE) {
            fileId = EXCEL_FILE_ID;
          }
        } else {
          if (fileExtensions.length == 1) {
            if (fileExtensions[0] == IMAGE_FILE) {
              fileId = IMAGE_FILE_ID;
            } else {
              fileId = IMAGE_FILE_ID;
            }
          }
        }
      }
    }
    return fileId;
  }

  static int getFileTypeId(String extension) {
    if (extension != null && extension.isNotEmpty) {
      if (extension == "pdf") {
        return 1;
      } else if (extension == "xlsx") {
        return 2;
      } else {
        return 0;
      }
    }
  }

  static void addData() {
    fileData[PDF_FILE_ID] = PDF_FILE_EXTENSION;
    fileData[EXCEL_FILE_ID] = EXCEL_FILE_EXTENSION;
    fileData[DOC_FILE_ID] = DOC_FILE_EXTENSION;
  }

  static String getExtensionNameFromId(int id) {
    addData();
    if (id > 0) {
      return fileData[id];
    } else {
      return "jpg";
    }
  }

  static Future<void> shareFile(
      int fileId, String base64ImageString, String fileName) {
    if (fileId == PDF_FILE_ID) {
      sharePDFFile(base64ImageString, fileName);
    } else if (fileId == EXCEL_FILE_ID) {
      shareExcelFile(base64ImageString, fileName);
    } else {
      shareImage(base64ImageString, fileName);
    }
  }

  static Future<String> getBase64Image(File file) async {
    if (file != null) {
      var base64Image = base64Encode(file.readAsBytesSync());
      return base64Image;
    }
  }

  static Future<String> getCompressedFileData(
      File file, String targetPath) async {
    var result = await FlutterImageCompress.compressAndGetFile(
      file.absolute.path,
      targetPath,
      quality: 60,
      rotate: 360,
    );
    return getBase64Image(result);
  }

  static Future<String> getFileSize(String filepath, int decimals) async {
    var file = File(filepath);
    int bytes = await file.length();
    if (bytes <= 0) return "0 B";
    const suffixes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    var i = (log(bytes) / log(1024)).floor();
    String ab =
        ((bytes / pow(1024, i)).toStringAsFixed(decimals)) + ' ' + suffixes[i];
    return ((bytes / pow(1024, i)).toStringAsFixed(decimals)) +
        ' ' +
        suffixes[i];
  }

  static bool validateFileExtension(
      List<String> extensionList, String extension) {
    try {
      if (extensionList != null && extensionList.isNotEmpty) {
        if (extensionList.contains(extension)) {
          return true;
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

  static String getStandardDate(int day) {
    String str = "";
    if (day <= 9) {
      return str = "0" + day.toString();
    } else {
      return str + day.toString();
    }
  }

  static String getStandardMonth(int month) {
    String str = "";
    if (month <= 9) {
      return str = "0" + month.toString();
    } else {
      return str + month.toString();
    }
  }

  static String getStandardTime(int hour, int min) {
    String hh = "";
    String mm = "";
    if (hour <= 9) {
      hh = "0" + hour.toString();
    } else {
      hh = hour.toString();
    }
    if (min <= 9) {
      mm = "0" + min.toString();
    } else {
      mm = min.toString();
    }
    String str = "";
    str = hh.toString() + ":" + mm.toString();
    return str;
  }

  static String getTime(String actTime) {
    String ab = "";
    List sub = actTime.split("(");
    if (sub != null && sub.isNotEmpty) {
      String a = sub[1];
      ab = a.substring(0, a.length - 1);
    }
    return ab;
  }

  static int checkAndSetNum(TextEditingController controller) {
    if (controller == null ||
        controller.text == '' ||
        controller.text.length == 0) {
      return 0;
    } else {
      return int.parse(controller.text);
    }
  }

  static double checkAndSetNumDouble(TextEditingController controller) {
    try {
      if (controller == null ||
          controller.text == '' ||
          controller.text.length == 0) {
        return 0;
      } else {
        return double.parse(controller.text);
      }
    } catch (e) {
      return 0;
    }
  }

  static String checkAndSet(TextEditingController controller) {
    if (controller == null ||
        controller.text == '' ||
        controller.text.length == 0) {
      return "";
    } else {
      return controller.text;
    }
  }

  static bool checkFlagInConfig(String key, Map config) {
    bool isFlag = false;
    try {
      if (config == null || config.isEmpty) {
        isFlag = false;
      } else if (config.containsKey(key)) {
        if (config[key]) {
          isFlag = true;
        } else {
          isFlag = false;
        }
      }
      return isFlag;
    } catch (e) {
      isFlag = false;
    }
    return isFlag;
  }

  static bool checkObjIsEmpty(Map obj) {
    if (obj != null && obj.isNotEmpty) {
      return true;
    } else {
      return false;
    }
  }

  static String setStringData(String str) {
    if (str == null ||
        str.contains("null") ||
        str == "null" ||
        str == 'null' ||
        str.length == 0) {
      return "";
    } else {
      return str;
    }
  }

  static int checkAndSetNumInt(String value) {
    try {
      if (value == null || value == '' || value.length == 0) {
        return 0;
      } else {
        return int.parse(value);
      }
    } catch (e) {
      return 0;
    }
  }
}
