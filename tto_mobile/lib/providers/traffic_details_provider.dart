import 'dart:convert';

import 'package:built_collection/built_collection.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:tto_mobile/models/traffic_detail_summary.dart';

class TrafficDetailsProvider with ChangeNotifier {
  TrafficDetailSummary _summary;

  TrafficDetailSummary get summary => _summary;

  TrafficDetailsProvider() {
    requestEndpoint();
  }

  void requestTrafficDetails() async{
    final builder = TrafficDetailSummaryBuilder();

    builder.origin = "Waldalgesheim";
    builder.destination = "Mainz";
    builder.timestamps = ListBuilder<DateTime>()..add(DateTime.now())..add(DateTime.now().add(Duration(minutes: 2)));
    builder.durations = ListBuilder<int>()..add(1)..add(2);

    _summary = builder.build();
    notifyListeners();
  }

  void requestEndpoint() async {
    Uri uri = Uri.http("192.168.178.50:8080", "/traffic", {
      "origin": "Frankenstraße 11, 55425 Waldalgesheim, Germany",
      "destination": "Rheinallee 169, 55120 Mainz, Germany"
    });
    print(uri.toString());
    final http.Response response = await http.get(uri);
    print("GOT RESPONSE");
    if (response.statusCode == 200) {
      Map<String, dynamic> jsonBody = json.decode(response.body);
      _summary = TrafficDetailSummary.deserializeSummary(jsonBody);

      notifyListeners();
    } else {
      print("FAIL");
    }
  }
}
