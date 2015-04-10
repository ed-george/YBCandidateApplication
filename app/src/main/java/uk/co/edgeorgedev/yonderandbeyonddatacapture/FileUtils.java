package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by edgeorge on 05/02/15.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    protected FileUtils(){}

    public boolean checkExternalMedia() {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWritable;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
            Log.w(TAG, "External Storage is READ ONLY");
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWritable = false;
            Log.w(TAG, "External Storage is NON-READ-WRITE");
        }
        return mExternalStorageAvailable && mExternalStorageWritable;
    }

    protected boolean writeToSDFile(String fileName, String fileBody){

        if(!checkExternalMedia()){
            return false;
        }

        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/candidates");
        dir.mkdirs();
        File file = new File(dir, fileName);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.print(fileBody);
            pw.flush();
            pw.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getAssetContent(Context ctx, String filename){
        AssetManager assetManager = ctx.getResources().getAssets();
        InputStream inputStream;

        try {
            inputStream = assetManager.open(filename);
            if (inputStream != null) {

                StringBuilder buf = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String str;

                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }

                in.close();

                return buf.toString();
            } else {
                throw new Exception("Input Stream was null");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "[]";
    }
}

