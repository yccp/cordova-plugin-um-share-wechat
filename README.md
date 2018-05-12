# 友盟社会化分享 cordova 插件 微信支持

> 请确保已安装 [友盟社会化分享 cordova 插件](https://github.com/yccp/cordova-plugin-um-share.git)

## 安装

```
cordova plugin add cordova-plugin-um-share-wechat --variable UM_SHARE_WECHAT_KEY=你的KEY --variable UM_SHARE_WECHAT_SECRET=你的SECRET --save
```
或
```
ionic cordova plugin add cordova-plugin-um-share-wechat --variable UM_SHARE_WECHAT_KEY=你的KEY --variable UM_SHARE_WECHAT_SECRET=你的SECRET
```

## 登录

```js
window.UMShareWechat.auth(res => {
  console.log(res);
}, e => {
  console.error(e);
});

```

## 支付

```js
window.UMShareWechat.sendPaymentRequest({
  partnerid: string; // merchant id
  prepayid: string; // prepay id
  noncestr: string; // nonce
  timestamp: string; // timestamp
  sign: string; // signed string
}, () => {
  console.log('success');
}, e => {
  console.error(e);
});

```