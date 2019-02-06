package com.lmu.warungdana.Api;

public class UtilsApi {
    //    10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://116.197.129.148:59/warnatrial/public/api/";
    public static final String BASE_URL_API = "http://192.168.139.59/warnadev/public/api/";
//    public static final String BASE_URL_API = "http://digitalnetworkasia.com:59/warnadev/public/api/";
//    public static final String BASE_URL_API = "http://116.197.129.148:58/warnaprod/public/api/";
//    public static final String BASE_URL_API = "http://116.197.129.148:66/warnadev/public/api/";
//    public static final String BASE_URL_API = "http://192.168.139.58/warnaprod2/public/api/";


    // Mendeklarasikan Interface BaseApiService
    public static ApiEndPoint getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(ApiEndPoint.class);
    }

}
