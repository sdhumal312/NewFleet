import 'package:data_connection_checker/data_connection_checker.dart';
import 'dart:async';
class ConnectionChecker
{

static Future<int>checkInternetConnection() async
{
DataConnectionStatus status = await checkInternet();
print(status);
if (status == DataConnectionStatus.connected) {
return 1;
}
else
{
return 0;
}
}


static checkInternet() async {
StreamSubscription<DataConnectionStatus> listener;
listener = DataConnectionChecker().onStatusChange.listen((status) {
});
return await DataConnectionChecker().connectionStatus;
}

}