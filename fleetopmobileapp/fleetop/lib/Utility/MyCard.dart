import 'package:flutter/material.dart';

class MyCard extends StatelessWidget {
  final String name;
  final String value;
  final IconData icon;
  final Color colorData;
  final bool valueColorChanged;
  final Color valueColor;
  final VoidCallback onTap;
  final bool cardColorChanged;
  Map mydata = new Map();
  double _width;
  MyCard({
    Key key,
    this.name,
    this.value,
    this.icon,
    this.colorData,
    this.valueColorChanged,
    this.onTap,
    this.valueColor,
    this.cardColorChanged,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    _width = MediaQuery.of(context).size.width;
    return GestureDetector(
      onTap: () {
        onTap();
      },
      child: Container(
        decoration: new BoxDecoration(
          color: Colors.cyanAccent,
          borderRadius: new BorderRadius.only(
            topLeft: const Radius.circular(10.0),
            bottomLeft: const Radius.circular(10.0),
          ),
          gradient: new LinearGradient(
              colors: [
                Color(0xFF6200EE),
                Color(0xFF4A00E0),
              ],
              begin: const FractionalOffset(0.0, 0.0),
              end: const FractionalOffset(1.0, 1.0),
              stops: [0.0, 1.0],
              tileMode: TileMode.clamp),
        ),
        margin: EdgeInsets.only(top: 8, left: 12, right: 10),
        child: Row(
          children: <Widget>[
            Container(
              width: 13,
              height: 60,
              decoration: new BoxDecoration(
                color: Colors.cyanAccent,
                borderRadius: new BorderRadius.only(
                  topLeft: const Radius.circular(10.0),
                  bottomLeft: const Radius.circular(10.0),
                ),
                gradient: new LinearGradient(
                    colors: [
                      Color(0xFF6200EE),
                      Color(0xFF4A00E0),
                    ],
                    begin: const FractionalOffset(0.0, 0.0),
                    end: const FractionalOffset(1.0, 1.0),
                    stops: [0.0, 1.0],
                    tileMode: TileMode.clamp),
              ),
            ),
            Container(
              width: _width - 40,
              height: 60,
              decoration: new BoxDecoration(
                borderRadius: new BorderRadius.only(
                  topRight: const Radius.circular(20.0),
                  bottomRight: const Radius.circular(20.0),
                ),
                gradient: new LinearGradient(
                    colors: [
                      Colors.white,
                      Colors.white,
                    ],
                    begin: const FractionalOffset(0.0, 0.0),
                    end: const FractionalOffset(1.0, 1.0),
                    stops: [0.0, 1.0],
                    tileMode: TileMode.clamp),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.only(left: 10),
                    child: Text(
                      name,
                      style: TextStyle(
                          color: Colors.black,
                          fontSize: 18.0,
                          fontFamily: "WorkSansBold"),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(right: 20),
                    child: Text(
                      value,
                      style: TextStyle(
                          color: Colors.black,
                          fontSize: 18.0,
                          fontWeight: FontWeight.bold,
                          fontFamily: "WorkSansBold"),
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
