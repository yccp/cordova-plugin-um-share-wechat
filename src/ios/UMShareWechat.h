#import <Cordova/CDVPlugin.h>
#import <UMSocialCore/UMSocialCore.h>
#import <WXApi.h>

@interface UShareWechat : CDVPlugin<WXApiDelegate>

@property (nonatomic, strong) NSString *currentCallbackId;
@property (nonatomic, strong) NSString *appKey;

- (void)sendPaymentRequest:(CDVInvokedUrlCommand *)command;

@end
