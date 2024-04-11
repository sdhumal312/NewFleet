class SelectOptions {
  final int regionId;
  final int subRegionId;
  final int branchId;
  final String name;
  final int selectType;
  SelectOptions({this.regionId, this.subRegionId, this.branchId, this.name, this.selectType});


  factory SelectOptions.fromJson(Map<String, dynamic> json) {
    print(json);
    if (json == null) return null;
    return SelectOptions(
      branchId: json["branchId"],
      subRegionId: json["subRegionId"],
      regionId: json["regionId"],
      name: json["name"],
      selectType: json["selectType"],
    );
  }
  
  @override
  String toString() => name;
}