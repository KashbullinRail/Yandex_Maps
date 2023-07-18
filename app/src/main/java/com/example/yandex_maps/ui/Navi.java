package com.example.yandex_maps.ui;

//import android.content.Intent;
//import android.net.Uri;
//import android.util.Base64;
//
//import java.math.BigInteger;
//import java.security.KeyFactory;
//import java.security.Signature;
//import java.security.spec.EncodedKeySpec;
//import java.security.spec.PKCS8EncodedKeySpec;
//
//
//public class YaNaviStarter {
//
//    private final String PRIVATE_KEY = "MIIBOgIBAAJBANFK2xYHiOxKNAf8lnJNXmTrg63qhNOiO4j5Laz5Df9VezafneLx\n" +
//            "tvuuqdvnd88bxY/uIKf8PaNubkzCvy8oJaMCAwEAAQJAWYEUvpAMW1jEdaHsyQcT\n" +
//            "Vj9t+eNwWH1pzoMZqH5+IPIr+5nXA1ODBkzQ00qOpiI48myOQMoJUXK97gqqbwxG\n" +
//            "4QIhAPqU4csJJRAnNMNE59OhNmy1sYTp/v2DTktzTzvhoaPRAiEA1dFp6aOCzw7c\n" +
//            "y0AqtXtj7Q5AJWz3tIQpPURedF8MEzMCIHZ1x8B6VEEsp67gdk//2IDP7FoPXPJw\n" +
//            "DqVwn7aJVibhAiAYaDw30FNpQWAQz3VQnBIvCDZoT0UDVSbD+J7zl7nwIwIhAN/u\n" +
//            "l7sTJc31t8d59ySSBArQA1KjAqhCtiaVQoZTWlRW";
//
//    // Формирует подпись с помощью ключа.
//    public String sha256rsa(String key, String data) throws SecurityException {
//        String trimmedKey = key.replaceAll("-----\\w+ PRIVATE KEY-----", "")
//                .replaceAll("\\s", "");
//
//        try {
//            byte[]         result    = Base64.decode(trimmedKey, Base64.DEFAULT);
//            KeyFactory     factory   = KeyFactory.getInstance("RSA");
//            EncodedKeySpec keySpec   = new PKCS8EncodedKeySpec(result);
//            Signature      signature = Signature.getInstance("SHA256withRSA");
//            signature.initSign(factory.generatePrivate(keySpec));
//            signature.update(data.getBytes());
//
//            byte[] encrypted = signature.sign();
//            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
//        } catch (Exception e) {
//            throw new SecurityException("Error calculating cipher data. SIC!");
//        }
//    }
//
//    // Формирует URI с подписью и запускает Яндекс.Навигатор.
//    public void buildRoute() {
//        Uri uri = Uri.parse("yandexnavi://build_route_on_map").buildUpon()
//                .appendQueryParameter("lat_to", "55.680559")
//                .appendQueryParameter("lon_to", "37.549246")
//                .appendQueryParameter("client", "007").build();
//
//        uri = uri.buildUpon()
//                .appendQueryParameter("signature", sha256rsa(PRIVATE_KEY, uri.toString()))
//                .build();
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setPackage("ru.yandex.yandexnavi");
//        startActivity(intent);
//    }
//}
