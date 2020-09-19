library serializers;

import 'package:built_collection/built_collection.dart';
import 'package:built_value/iso_8601_date_time_serializer.dart';
import 'package:built_value/serializer.dart';
import 'package:built_value/standard_json_plugin.dart';
import 'package:tto_mobile/models/traffic_detail_summary.dart';

part 'serializers.g.dart';

@SerializersFor([TrafficDetailSummary])
final Serializers serializers =
(_$serializers.toBuilder()..addPlugin(StandardJsonPlugin())..add(Iso8601DateTimeSerializer())).build();