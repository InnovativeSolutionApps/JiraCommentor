package main;


import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

public class JiraUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static String getAuthenticationToken(String userName,String password){
        StringBuilder authenticationToken = new StringBuilder();
        authenticationToken.append("Basic ");
        authenticationToken.append(base64(new StringBuilder(userName).append(":").
              append(password).toString()));
        return authenticationToken.toString();
    }

    private static String base64(String s){
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }


    public  static String postCommentOnlyToIssue(String userName,String password,String issueId,String commentText,String serverIp) throws IOException {
        String status = "";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().
                url("https://"+serverIp+"/rest/api/2/issue/"+issueId+"/comment").
                post(RequestBody.create(JSON,getPostCommentRequestJson(commentText))).
                addHeader("Authorization",getAuthenticationToken("sakthi2641998@gmail.com","pUV3hoFlKDftmAz8NqgEA009")).
                addHeader("Accept","application/json").
                addHeader("Content-Type","application/json").
                build();

        Response response = client.newCall(request).execute();
        System.out.println("response "+ response);

        if(response.code() ==  201){

            status =   "Comment Successfully Added \n";

            }else {
            status =   "Comment Request Failed \n";

        }



     /*   client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result

                    System.out.println("onResponse Asynchronos "+ response);

                }
            }
        });*/


     return status;
        }

    private static String getPostCommentRequestJson(String comment){
        JSONObject request = new JSONObject();
        try {
            request.put("body",comment);
        }catch (JOniException | JSONException e)
        {
            e.printStackTrace();
        }

        return request.toString();

    }



      public static String postAttachmentWithComment(String userName,String password,String issueId,String commentText,String attachmentName,String filePath,String serverIp) throws IOException {
String status = "";

            File file = new File(filePath);

          OkHttpClient client = new OkHttpClient().newBuilder()
                  .build();
          MediaType mediaType = MediaType.parse("multipart/form-data");
          RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                  .addFormDataPart("file",file.getName(),
                          RequestBody.create(MediaType.parse("application/octet-stream"),
                                  new File(filePath)))
                  .build();
          Request request = new Request.Builder()
                  .url("https://"+serverIp+"/rest/api/3/issue/"+issueId+"/attachments")
                  .method("POST", body)
                  .addHeader("Content-Type", "multipart/form-data")
                  .addHeader("X-Atlassian-Token", "no-check")
                  .addHeader("charset", "UTF-8")
                  .addHeader("Authorization",getAuthenticationToken("sakthi2641998@gmail.com","pUV3hoFlKDftmAz8NqgEA009"))
                  .build();

          Response response = client.newCall(request).execute();

          System.out.println("response "+ response);

          if(response.code() ==  200){

              status =   " Attachment Successfully added \n";

          }else {
              status =   "Attachment Request Failed \n";

         }


          status = status +  postCommentOnlyToIssue(userName,password,issueId,commentText,serverIp);


          return  status;

      }










}
