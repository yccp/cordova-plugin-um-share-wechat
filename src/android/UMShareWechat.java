package news.chen.yu.ionic;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class UMShareWechat extends CordovaPlugin {
    public static String appKey;
    public static IWXAPI wxAPI;
    public static CallbackContext currentCallbackContext;

    private Boolean __init = false;

    private void init() {
        if(!this.__init) {
            this.__init = true;
            if(!UMShare.mediaList.contains(SHARE_MEDIA.WEIXIN))
                UMShare.mediaList.add(SHARE_MEDIA.WEIXIN);
            if(!UMShare.mediaList.contains(SHARE_MEDIA.WEIXIN_CIRCLE))
                UMShare.mediaList.add(SHARE_MEDIA.WEIXIN_CIRCLE);

            String appKey = preferences.getString("um_share_wechat_key", "");
            String appSecret = preferences.getString("um_share_wechat_secret", "");
            Log.i(UMCommon.TAG, "Wechat key: " + appKey);
            Log.i(UMCommon.TAG, "Wechat secret: " + appSecret);
            PlatformConfig.setWeixin(appKey, appSecret);
            UMShareWechat.appKey = appKey;
            UMShareWechat.wxAPI = WXAPIFactory.createWXAPI(cordova.getActivity(), appKey, true);
            UMShareWechat.wxAPI.registerApp(appKey);
        }
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.init();
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        LOG.d(UMCommon.TAG, String.format("%s is called. Callback ID: %s.", action, callbackContext.getCallbackId()));

        if (action.equals("sendPaymentRequest")) {
                return this.sendPaymentRequest(args, callbackContext);
        }
        callbackContext.error("Unkown action");
        return false;
    }

    protected boolean sendPaymentRequest(CordovaArgs args, CallbackContext callbackContext) {
        final JSONObject params;
        try {
            params = args.getJSONObject(0);
        } catch (JSONException e) {
            callbackContext.error(e.getLocalizedMessage());
            return true;
        }

        PayReq req = new PayReq();

        try {
            req.appId = UMShareWechat.appKey;
            req.partnerId = params.has("mch_id") ? params.getString("mch_id") : params.getString("partnerid");
            req.prepayId = params.has("prepay_id") ? params.getString("prepay_id") : params.getString("prepayid");
            req.nonceStr = params.has("nonce") ? params.getString("nonce") : params.getString("noncestr");
            req.timeStamp = params.getString("timestamp");
            req.sign = params.getString("sign");
            req.packageValue = "Sign=WXPay";
        } catch (Exception e) {
            LOG.e(UMCommon.TAG, e.getMessage());

            callbackContext.error("参数格式错误");
            return true;
        }

        if (UMShareWechat.wxAPI.sendReq(req)) {
            LOG.i(UMCommon.TAG, "Payment request has been sent successfully.");
            sendNoResultPluginResult(callbackContext);
        } else {
            LOG.i(UMCommon.TAG, "Payment request has been sent unsuccessfully.");
            callbackContext.error("发送请求失败");
        }

        return true;
    }

    private void sendNoResultPluginResult(CallbackContext callbackContext) {
        UMShareWechat.currentCallbackContext = callbackContext;
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);
        callbackContext.sendPluginResult(result);
    }
}