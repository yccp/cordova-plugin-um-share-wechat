package news.chen.yu.ionic;

import org.apache.cordova.*;
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
}