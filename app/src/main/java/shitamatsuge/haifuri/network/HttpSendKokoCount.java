package shitamatsuge.haifuri.network;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpSendKokoCount extends AsyncTask<String, String, String> {
    private final String TAG = "HttpSendKokoCount";
    public interface onCompleteHandler{
        void successHandler(String counter);
        void errorHandler(String counter);
    }
    private int mIndex = -1;
    private onCompleteHandler mCompleteHandler;
    private static final String ACCESS_KEY = "ココだけの秘密";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute()");
        // doInBackground前処理
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground()");
        HttpURLConnection con = null;
        URL url = null;
        String result = null;
        String urlSt = "http://shitamatsuge.sakura.ne.jp/api/v1/counter/?name="+ params[0] +"&add=" + params[1] + "&access_key=" + ACCESS_KEY;

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(false);
            // リクエストメソッドの設定
            con.setRequestMethod("GET");
            // 接続
            con.connect(); // ①

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

    public void send(String[] params, onCompleteHandler completeHandler) {
        mCompleteHandler = completeHandler;
        execute(params);
    }
}
