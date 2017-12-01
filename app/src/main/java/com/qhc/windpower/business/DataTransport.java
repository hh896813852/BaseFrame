package com.qhc.windpower.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.qhc.windpower.BuildConfig;
import com.qhc.windpower.bean.base.Message;
import com.qhc.windpower.utils.HttpUtil;
import com.qhc.windpower.utils.JsonUtil;
import com.qhc.windpower.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 与后台进行数据传入的工具
 */
public class DataTransport {

    private ProgressDialog mProgressDialog;
    private Context context;
    private String progressContent;
    private boolean cancelable = true;
    private String url;
    private Map<String, Object> params = new HashMap<>();
    private Object dataType = String.class;

    DataTransport() {

    }

    public DataTransport addUrl(String url) {
        this.url = url;
        return this;
    }

    public DataTransport addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public DataTransport addParams(Map<String, Object> params) {
        for (String key : params.keySet()) {
            this.params.put(key, params.get(key));
        }
        return this;
    }

    /**
     * 如果你需要一个默认的进度条
     *
     * @return
     */
    public DataTransport addDefaultProgressing(Context context) {
        this.context = context;
        this.progressContent = "正在加载,请稍等...";
        return this;
    }

    /**
     * 如果你需要一个自定义文字的进度条
     *
     * @return
     */
    public DataTransport addProgressing(Context context, String progressContent) {
        this.context = context;
        this.progressContent = progressContent;
        return this;
    }

    public void execute(DataListener dataListener) {
        execute(dataListener, Object.class);
    }

    public void execute(DataListener dataListener, Class dataClass) {
        this.dataType = dataClass;
        executeNow(dataListener);
    }

    public void execute(DataListener dataListener, TypeToken dataListToken) {
        this.dataType = dataListToken;
        executeNow(dataListener);
    }

    private void showProgress() {
        if (progressContent != null) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                if (Build.VERSION.SDK_INT >= 21) {
                    mProgressDialog.setProgressStyle(android.R.style.Theme_Material_Light_Dialog);
                }
            } else {
                mProgressDialog.cancel();
            }
            mProgressDialog.setMessage(progressContent);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.show();
        }
    }

    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void executeNow(DataListener dataListener) {
        showProgress();
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String result = decode(HttpUtil.httpPost(url, params));
                if (BuildConfig.DEBUG) {
                    LogUtil.i("HTTP-URL", url);
                    LogUtil.i("HTTP-PARAMS", JsonUtil.toJson(params));
                    if (result == null) {
                        LogUtil.i("HTTP-RESULT", "null");
                    } else {
                        LogUtil.i("HTTP-RESULT", result);
                    }
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                dismissProgress();
                Message message = new Message();
                if (TextUtils.isEmpty(result)) {
                    message.status = -1;
                    message.msg = "网络异常";
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        message.status = jsonObject.getInt("status");
                        message.msg = jsonObject.getString("msg");
                        if (message.status == 1) {
                            String dataString = jsonObject.getString("data");
                            if (!TextUtils.isEmpty(dataString)) {
                                if (dataType.getClass().equals(Class.class)) { // 返回的是一个实体(非List)
                                    if (dataType.equals(String.class)) {
                                        message.data = dataString;
                                    } else {
                                        Class typeClass = (Class) dataType;
                                        message.data = JsonUtil.fromJson(dataString, typeClass);
                                    }
                                } else {                                       // 返回的是一个List
                                    TypeToken typeToken = ((TypeToken) dataType);
                                    message.data = JsonUtil.fromJson(dataString, typeToken);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        message.status = -2;
                        message.data = "数据解析异常";
                    }
                }
                if (dataListener != null) {
                    dataListener.onMessage(message);
                }
            }
        }.execute();
    }

    private static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
    private static final String decode(String s) {
        if (s != null) {
            Matcher m = reUnicode.matcher(s);
            StringBuffer sb = new StringBuffer(s.length());
            while (m.find()) {
                m.appendReplacement(sb,
                        Character.toString((char) Integer.parseInt(m.group(1), 16)));
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return null;
    }

}
