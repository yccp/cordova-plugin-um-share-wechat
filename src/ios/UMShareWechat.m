#import "UMShareWechat.h"

@implementation UMShareWechat
- (void)pluginInitialize
{
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(finishLaunching:) name:UIApplicationDidFinishLaunchingNotification object:nil];
    
}

- (void)finishLaunching:(NSNotification *)notification
{
    // AppKey
    NSString *appKey = [[self.commandDelegate settings] objectForKey:@"um_share_wechat_key"];
    // AppSecret
    NSString *appSecret = [[self.commandDelegate settings] objectForKey:@"um_share_wechat_secret"];
    
    /*
     设置微信的appKey和appSecret
     */
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_WechatSession appKey:appKey appSecret:appSecret redirectURL:nil];
    
    /*
     * 移除相应平台的分享，如微信收藏
     */
    [[UMSocialManager defaultManager] removePlatformProviderWithPlatformTypes:@[@(UMSocialPlatformType_WechatFavorite)]];
}

@end
