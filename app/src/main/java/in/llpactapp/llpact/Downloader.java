package in.llpactapp.llpact;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader {

    static String fileURL = "";
    static String fileName = "dump.zip";
    static SharedPreferences sharedpreferences;

    static OkHttpClient client;
    static MediaType JSON;
    static boolean shouldDownload;

    public static void download(Context context) {

        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");

        try {
            Request request = new Request.Builder().
                    url("https://api.github.com/repos/aparna-hs/llpact-repo/releases/latest").build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = new JSONObject(result);
            fileURL = jsonObject.getString("zipball_url");
            Log.d("OKGETTING",result);
            sharedpreferences = context.getSharedPreferences("downloadpref", Context.MODE_PRIVATE);
            String testUrl ="";
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if(sharedpreferences.contains("url"))
            {
                testUrl= sharedpreferences.getString("url", null);
                if(testUrl.equals(fileURL))
                {
                    Log.d("Sharedpref","i am working");
                    return;
                }
            }
            Log.d("Sharedpref","i am not working");
            editor.putString("url",fileURL);
            editor.commit();
            File zipFilePath = new File(context.getFilesDir(), fileName);
            Log.d("HERE", zipFilePath.getAbsolutePath());

            URL url = new URL(fileURL);

            URLConnection uconn = url.openConnection();
            uconn.setReadTimeout(5000);
            uconn.setConnectTimeout(5000);

            InputStream inputStream = uconn.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            int current;
            while((current = bufferedInputStream.read()) != -1){
                outStream.write((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(zipFilePath);
            fos.write(outStream.toByteArray());
            fos.flush();
            fos.close();

            File docsFolder = new File(context.getFilesDir(), Constants.SOURCE_DIRECTORY);

            if (docsFolder.exists()) {
                FileUtils.deleteDirectory(docsFolder);
                docsFolder.mkdirs();
            } else {
                docsFolder.mkdirs();
            }

            ZipManager.unzip(zipFilePath.getAbsolutePath(), docsFolder.getAbsolutePath());

            Collection files = FileUtils.listFiles(docsFolder, new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);

            Iterator iter = files.iterator();

            for (Object c: files)
                Log.i("HERE", c.toString());


        } catch(Exception e) {
            Log.e("Downloader", e.getLocalizedMessage());
        }
    }




}
