package com.bhw.pay.config;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//    public static String app_id = "2021001156628457";//测试环境

    public static String app_id = "2021001164667316";//商用环境

    // 商户私钥，您的PKCS8格式RSA2私钥
    //测试
    //public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCZO/pktNQRbftfGytWzERgDKMH1yZcbjdfoA14C5o4sBMZh/NChX+3i8ijBeg4ssg5oXSz0GOQmQ9KxxKpMRkT7kf7BTKCQsCNEdUCrBjuASI+MY/OSFyFeqU323KRiwjuL2mYxjhm7gO7Y+CZyY1wNVpnp2gO9WRRUQlPGGIMjPEK8DfSi0bF+khowT1rxgWRJHxUw4o1O4pBMdTjXkgZObjEq5vlW8nUOOam9LOF/vd+GYjsS/GC1dwybI+REb76xEZPiILraw5EKKadgZIBkMzkSwbod9FQo2U26wMC5YZY4uaGUSpgIVLfbna9Hd49+938W1bF2xPjNcoZi4NvAgMBAAECggEBAJAJ1SD86atImtWXkHGSqWMv/b/M+f5AjESTCpYhMeK/f/Sr2npZSuiZuQuk7dj+FUA3CKBN7olplvpPjrcDs/MMNRU8mXHglWcFsBlG9FglMK8Nn4PkeTPvSYbr/RHag5aBct1s1m/KpC9tfx6QWydyv3xX3HTTLm1Ro1J6NRBdnVBrS6bkQCVOppMk5BxlhvZQqKLZrhKVwrxhkXyht46isIbb/eLdKRbvUxD+GP4jQX/neH7tPLx4/2I3oQL7sgHin1hy2JbkE90Q7UmTJuyZZNTVldBAD469HypCuOJSsE90lGbQUpKA6tEPCw5lfifrmcQ4ey7ahK/xGhYE8+ECgYEA+kHjvyrL7GvvU9dD0+tiIAM8iK+dJ0Jc/BsvJjAC2/4Cfq01wMc6EgDY9OPm2CDT+BZoDmVIhxwOvpfRp6vbflIvGu+kMlHOzW4F74a9TWBcl8TgqYKv2kKQsRF2B+dHKSAlpHX26N353UNaYk6YI8HfKIK/Yr4Zsl7870gKEksCgYEAnMAi+kRQNCFuqAgkXpvLKDXb/PBjs8xn8x66iK0gh1lKaZu9C88lghXcvHx0U0HxvEEkYwv3yAtdVPnfIIADL0xcDNOfsZV8Oi7LsAwcJemowdI6ZgRVWuj8Y39b70S6Mg+tsz9kAeZWvo6GBnJG2BEOp/H6gNtu62vlb4ZtPO0CgYEA7jLG/s9SWE8H9mpfwhc6R7Qs6wEUeSi+bqvFPfjWz+Q/JaniDwLSMA6kcrt1nuPoPiu9kW4wEYHQOvMweAKpIdxBv8a3J7ocOAZwGePKzRpaJ48aM94TWZCgPhmbmbw2TFuv2JD7mfymdkdEgWvDfc1rhhik5+x/cvrcjzindW8CgYEAgQBDCPIVi4GEHvdsbwwKGtKQdE6lzv/HALdmTjskzR739Yf9Ae39cVxqqBhIZ4ia2BJITtc3mUN5EQj+zzFq7Y8LJmc3Qp/Cd+OxL/YXaEAu6SwJcZHZaRLbO0tCLtzB25c5nY3q0QFunC6RMFb15LXM+PxU650rYMYQMUNRPVUCgYEA68ddJb38ch2jVoDM1J8w5C3t3j2Yh/Dd0BizdjRsiminFPFK/L0fN7an3cAwbMAYjXaI/YOijEG33aA2rRtz8VPUtylqghCvJ7oAVk2UiLED+EwBOZmIKh5wehD7iunHxms3fby0iUvG5yTLBM+ktOYDB6xXSIT4CiJOBncSuts=";
    //商用
    public static String merchant_private_key ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCk3P9mpAq/5ViNRabME/kTsqFl+F74v0e0vKYfT+lM2+/hzx3x9ZfIEpJNtIb4GwuvyLPjR4VHRxJDo117nNTRsZ7pMw1lUxF2hPj/dDWJV3nZwDr4Qy9mc6xipvbc4fCc4svVxqNFH44fvnT8z4wuextZrEA+kkt/RkbsUxgwdQFzkQWKIxfw+045PigEAWjZQjQlWsp4mTLf8bpDljTbsIjjCrSmAtJnN76hwDrj3ehWA/+BMY3NZZrx1hwrjSb7lfwemiam8PXeER2n/Wbz7F6Fbz/5CN5VpGO+mOCVyKfgKd3vVqL8uQRfXd5H+Bf98WYGmbwYeVqNwQCIyTr3AgMBAAECggEAZd7pMlNLESh2f1T6rD/rOPC4Kno+tRX9CQFI5TBLyOJeZ/nDVy+ailZvcPSMMeMEZapu/ZHEGTchYgogSbGvD6Phvdd9/45EzCd+dNsp+aPD93mA9scNExGHA66H/BTgyRCbQ41qddv5xkO5pj3ZMR+A1vhI+mELpamXSZBO8iFcxH528IR1fK8+P4iE5pKtqCnsb59UcWdakGjE7Csrn5FPHUB2SsPDbNEKCI3hcSiUh3kIgnSSpI2ncht4GlHdxfEhpqqJaZhwpERHXSIZaXTIBc2JlFzR9w7tDMgtOdy/NVX+lSU6Pxck5bCr7yzKGGwP8Ded696oIntSVqJUAQKBgQDRcdBI5//+sIBmS9drnaOxs91+X9TyD+ejZyW345CnYP+27Fd8jPjLrJPlkFArnCbMZcRqcCJJIlxOFZl/MRt4R5udDTLFMTMYiJjzN0rctrlDDR1Wr7Ps7MQiMuSQ5+iFI58Y+kcFDg85MH0sjPdTfSyzvWe3rFXJWesY8n4KoQKBgQDJglbdtuatOI+SXaFfWfp283sIwH4IC0Lus75sCo2LY3JSBfnL5uiO+AHexdTwo5frlyeCe/07Z/mL+g5tGYixSJFvZ4DEjoK7EPZsYNveBBBNxhDg4Rx2sf+vuHqYSftJXGuxXZ8lip8MCZ7wcRyGKgwnVcp0rBWvVjQKeFM2lwKBgQCgeMhyI7djR46JUlCSNqzmCX6KRhidAoyjCPPASg5aABrM55Y8arJxMx/6ArE6SKD9eHeeaZ4LVNVOSoRaDKEUbiEPmr4skf6pLHer2dB0/VdCxXiWI3cC74FzBnVThSwFXn6Wn6PsfpIxajJuAO+6uyyV5mfiNZtg85EKgzkJIQKBgA9reOg4cch5lor1vT6FrSPjF9wglsRY0ENh38V2vUQYVQwvxuCyh8NA+Sup6dSMQDjX9T8H6+Xa2/65INd6Z7phOjfTLMhdu2VDNAgxFUIU/4ZkDLCYqUvv7QfuTvtOVftUJi2JY39E70zFg7MUhMFDgHCWIQryjCpBp3PRp1R7AoGAURMVYdacr1n9m28i8Ng6e+hGcR6jhDj4CHMS72APfr10iI+fdh4ViathP5ynJdpKbEiAuq3bOZ4nfU33RartM9p8rDLBlrQ8WEv1S5EIoNBr96C2AGytdaCTAIActMclYMmnOU2IHBoTfMHGLoCTHgZFEoTL1TC+yxmjffsH7eE=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    //测试
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5CP4GLJpXcbsUjgukvXXcE92/98p0oKbacOp0u6RAb9jLeuCSZQIK+2oVm8vWlIy8fjhi3CyPpiI6bjmVqbuUzb14Gvr0w6z3ZmpH0WurukKHDTR9lIG6C+0v2JVazWuESMCFaUyE2p06NKSH0ITLXPEKxZq6GcUHI44AdP042j3rhCmOsh4Qcm6WFN6cPoBEMJTQsTkzMeBaMeYktKXzmWd1vLiKbK1Cfn7xmiXtFFKJC+vIF/f+X0mKztw4mB313BGpb++pUBk2Hmicm5yVVthlghRjs0VNY6Z0bS5nG+YV8gulj0jXquu7w0TPqRhp6KUxTXOdAB3F/5xAtPRywIDAQAB";
    //商用
    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgXmdmEj1PBCzgrQheUAHRBDiXoEVhCQ3eC8SbYv+YkL6g1zSNXeCWfnuw7RKHJTXbQyrwa+NQGB9drvZYZUK/Jdbyvlp503G8GvkLKz/T0SJRxknLc3HPMvzpFCxBhrfWSnUYlPq1W7ovbUwhnwPIrYW3AJRfxtbbToQoeu/9wtbvXWP9fzTzytswrhtiHxI3PJUcMqWk3AxM028bsoXudmBsEDIyHwffKStVybn1YN9xmiMqPiTj5vQS28SYrg8GHwpG5bGgQ0G4yKh/iUXK+D0ykElOn49l5QoKnE0eZrUgxZbgTWgJEaQaYp4AsWTVcNPpNDmsSHr3kojAX7p5QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String notify_url = "http://bhw.traup.net:8760/api/payApi/aliPay/notify_url";
//     public static String notify_url = "http://www.traup.net:8760/api/payApi/aliPay/notify_url";
    public static String notify_url = "http://www.traup.com:8760/api/payApi/aliPay/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "http://bhw.traup.net/return_url";
//    public static String return_url = "http://www.traup.net/return_url";
    public static String return_url = "http://www.traup.com/return_url";

//    public static String wt_return_url = "http://bhw.traup.net/wt_return_url";
//    public static String wt_return_url = "http://www.traup.net/wt_return_url";
    public static String wt_return_url = "http://www.traup.com/wt_return_url";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
}