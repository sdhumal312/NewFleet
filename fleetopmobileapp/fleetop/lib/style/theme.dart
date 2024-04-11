import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

ThemeData appTheme() {
  return ThemeData(
    primarySwatch: Colors.loginGradientEnd,
    textTheme: TextTheme(
      title: TextStyle(
        fontFamily: 'Montserrat',
        fontSize: 24,
        fontWeight: FontWeight.bold,
        color: Color(0xFF425398),
      ),
      caption: TextStyle(
        fontFamily: 'Montserrat',
        fontSize: 24,
        fontWeight: FontWeight.bold,
        color: Color(0xFF425398),
      ),
      subhead: TextStyle(
        fontFamily: 'Montserrat',
        fontSize: 24,
        fontWeight: FontWeight.bold,
        color: Color(0xFF425398),
      ),
      body1: TextStyle(
        fontFamily: 'Montserrat',
        fontSize: 17,
        fontWeight: FontWeight.normal,
        color: Color(0xFF425398),
      ),
    ),
  );
}



class Colors {

  const Colors();

  static const Color loginGradientStart = const Color(0xff4776E4);
  static const Color loginGradientEnd = const Color(0xff8E54E9);

  static const primaryGradient = const LinearGradient(
    colors: const [loginGradientStart, loginGradientEnd],
    stops: const [0.0, 1.0],
    begin: Alignment.topCenter,
    end: Alignment.bottomCenter,
  );

}
