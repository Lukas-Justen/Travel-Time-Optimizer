import 'package:built_collection/built_collection.dart';
import 'package:built_value/built_value.dart';
import 'package:built_value/serializer.dart';
import 'package:tto_mobile/models/serializers.dart';

part 'traffic_detail_summary.g.dart';

abstract class TrafficDetailSummary implements Built<TrafficDetailSummary, TrafficDetailSummaryBuilder> {
  TrafficDetailSummary._();

  factory TrafficDetailSummary([void Function(TrafficDetailSummaryBuilder) updates]) = _$TrafficDetailSummary;


  static Serializer<TrafficDetailSummary> get serializer => _$trafficDetailSummarySerializer;

  String get origin;
  String get destination;

  BuiltList<DateTime> get timestamps;

  BuiltList<int> get durations;

  static TrafficDetailSummary deserializeSummary(Map<String, dynamic> json) {
    TrafficDetailSummary summary = serializers.deserializeWith(TrafficDetailSummary.serializer, json);
    return summary;
  }

  static Object serializeSummary(TrafficDetailSummary summary) {
    Object serialized = serializers.serializeWith(TrafficDetailSummary.serializer, summary);
    return serialized;
  }

}