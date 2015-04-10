package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;

import java.util.List;

/**
 * Created by edgeorge on 05/02/15.
 */
public class ExportJsonAsync extends AsyncTask<Void, Void, Boolean>{
    private Context context;
    private AsyncCompleteListener listener;
    private final String JSON_FILENAME = "candidates.json";

    public ExportJsonAsync(Context context, AsyncCompleteListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Dao<Candidate, Integer> dao = DatabaseHelper.getDatabaseHelperInstance(context).getDao(Candidate.class);
            List<Candidate> candidates = dao.queryForAll();

            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

            String fileBody = gson.toJson(candidates);

            FileUtils utils = new FileUtils();
            utils.writeToSDFile(JSON_FILENAME, fileBody);

        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            listener.onAsyncComplete();
        }else{
            listener.onAsyncFailed();
        }
    }
}
