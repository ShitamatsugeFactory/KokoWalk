package shitamatsuge.haifuri.network;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;

public class HttpSendDoujouRanking extends AsyncTask<String, String, String> {
    private final String TAG = "HttpSendDoujouRanking";
    public interface onCompleteHandler{
        void successHandler(String result);
        void errorHandler(String result);
    }
    private int mIndex = -1;
    private onCompleteHandler mCompleteHandler;
    private static final String ACCESS_KEY = "09110921331094";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute()");
        // doInBackground前処理
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground() : " + params + " , " + params != null && params.length > 0 ? params[0] : null);
        HttpURLConnection con = null;
        URL url = null;
        String result = null;
        String urlSt = "http://shitamatsuge.sakura.ne.jp/api/ranking/?accesskey=" + ACCESS_KEY;

        // csrf_token はexemptしてるので今回はGETしてからtokenをキャッシュに云々はまだない
        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(true);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(true);
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            OutputStream outputStream = con.getOutputStream();

            // 接続
            con.connect(); // ①
            //OutputStreamWriter out = new OutputStreamWriter(outputStream);
            //out.write(params[0]);

            PrintStream ps = new PrintStream(outputStream);
            ps.print(params[0]);
            ps.flush();
            ps.close();
            outputStream.close();

            Log.d(TAG, "params = " + params[0]);

            try {
                InputStream inputStream = con.getInputStream();
                StringBuffer sb = new StringBuffer();
                String st = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while((st = br.readLine()) != null) {
                    sb.append(st);
                }
                inputStream.close();
                result = sb.toString();


            } catch (NullPointerException e) {
                Log.e(TAG, "con.getInputStreamError");
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "con.getInputStreamError" + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "con.getInputStreamError" + e);
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecute(result), result = " + result);
        mCompleteHandler.successHandler(result);
    }

    public void send(String part, String name, Long score, onCompleteHandler completionHandler) {
        mCompleteHandler = completionHandler;
        String [] params = new String[1];
        JSONObject param = new JSONObject();
        try {
            param.put("part", part);
            param.put("name", name);
            param.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params[0] = param.toString();
        params[0] = "";
        Iterator<String> keys = param.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if(params[0].length() > 0) params[0] += '&';
            try {
                params[0] += key + "=" + param.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        execute(params);
    }
}
