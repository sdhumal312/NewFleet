import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:flutter/services.dart';
import 'package:getflutter/getflutter.dart';

typedef void SuggestionSelectionCallback<Map>(Map suggestion);

class CustomAutoComplete extends StatefulWidget {
  final List suggestionList;
  final TextEditingController controller;
  final ValueChanged<String> onChanged;
  final ValueChanged<String> onSubmeet;
  final IconData iconData;
  final String label;
  final String hintLabel;
  final String dataKeyName;
  final OnItemSelected<Map> onItemSelected;
  final bool enabled;
  final FocusNode focusNode;
  final TextInputType keyboardType;
  final TextStyle hintStyle;
  final int textLimit;
  final VoidCallback resetData;
  CustomAutoComplete(
      {Key key,
      @required this.suggestionList,
      this.focusNode,
      @required this.controller,
      @required this.onChanged,
      @required this.iconData,
      @required this.label,
      @required this.hintLabel,
      @required this.dataKeyName,
      @required this.onItemSelected,
      this.enabled,
      this.keyboardType,
      this.hintStyle,
      this.onSubmeet,
      this.textLimit,
      this.resetData})
      : super(key: key);

  @override
  _CustomAutoCompleteState createState() => _CustomAutoCompleteState();
}

class _CustomAutoCompleteState extends State<CustomAutoComplete> {
  bool showList = false;
  FocusNode focus;
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 6),
      child: Column(
        children: <Widget>[
          Container(
            child: TextFormField(
              maxLength: widget.textLimit != null ? widget.textLimit : 30,
              enabled: widget.enabled,
              textInputAction: TextInputAction.next,
              onFieldSubmitted: (term) {
                widget.onSubmeet(term);
                focus.unfocus();
              },
              onChanged: (text) {
                widget.onChanged(text);
                if (text != null && text.length == 0) {
                  setState(() {
                    showList = false;
                  });
                } else {
                  setState(() {
                    showList = true;
                  });
                }
              },
              style: TextStyle(
                  fontSize: 16.0,
                  fontWeight: FontWeight.bold,
                  fontFamily: "WorkSansBold"),
              controller: widget.controller,
              decoration: InputDecoration(
                  counterText: '',
                  prefixIcon: Icon(widget.iconData),
                  suffixIcon: IconButton(
                    icon: Icon(
                      Icons.clear,
                      size: 22.0,
                      color: Colors.black,
                    ),
                    onPressed: () {
                      widget.controller.text = "";
                      if (widget.resetData != null) {
                        widget.resetData();
                      }
                      setState(() {
                        showList = false;
                      });
                    },
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                  labelText: widget.label,
                  hintText: widget.hintLabel,
                  hintStyle: widget.hintStyle
                  // hintStyle: widget.hintStyle
                  ),
              autofocus: false,
            ),
          ),
          Card(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10.0)),
            borderOnForeground: true,
            elevation: 2.0,
            child: Column(children: renderCardList()),
          )
        ],
      ),
    );
  }

  List<Widget> renderCardList() {
    List<Widget> list = new List();

    if (widget.suggestionList != null &&
        widget.suggestionList.isNotEmpty &&
        showList) {
      for (int i = 0; i < widget.suggestionList.length; i++) {
        list.add(renderCard(widget.suggestionList[i]));
      }
      return list;
    } else {
      list.add(Container());
      return list;
    }
  }

  Widget renderCard(Map acData) {
    return Container(
      child: ListTile(
          onTap: () {
            FocusScope.of(context).requestFocus(FocusNode());
            setState(() {
              showList = false;
            });
            Map data = acData;
            widget.onItemSelected(data);
          },
          leading: Icon(widget.iconData),
          title: Text(acData[widget.dataKeyName])),
    );
  }
}
