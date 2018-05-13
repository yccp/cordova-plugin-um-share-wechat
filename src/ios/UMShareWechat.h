#import <Cordova/CDVPlugin.h>
#import <UMShare/UMShare.h>
#import <WXApi.h>

@interface UMShareWechat : CDVPlugin<WXApiDelegate>

@property (nonatomic, strong) NSString *currentCallbackId;
@property (nonatomic, strong) NSString *appKey;

- (void)auth:(CDVInvokedUrlCommand *)command;

@end
