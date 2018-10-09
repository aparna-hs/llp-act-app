package in.llpactapp.llpact;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;

public class Downloader {

    static String fileURL = "https://github.com/aparna-hs/llpact-repo/archive/v0.4.zip";
    static String fileName = "dump.zip";

    public static void download(Context context) {
        try {
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
