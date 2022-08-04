 import Cookies from 'js-cookie'

const TokenKey = 'bhwWeb-Token';

const WxCodeKey = "bhwWx-cdoe";

const QQUser = "bhwQq-user";

const walletPay="bhwWalletPay";

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function setWxToken(token) {
    return Cookies.set(WxCodeKey, token)
}

export function getWxToken() {
    return Cookies.get(WxCodeKey)
}

export function removeWxToken() {
    return Cookies.remove(WxCodeKey)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setQqToken(info) {
    return Cookies.set(QQUser,info)
}

export function getQqToken() {
    return Cookies.get(QQUser)
}

export function removeQqToken() {
    return Cookies.remove(QQUser)
}


 export function setWtPayToken(token) {
     return Cookies.set(walletPay, token)
 }

 export function getWtPayToken() {
     return Cookies.get(walletPay)
 }

 export function removeWtPayToken() {
     return Cookies.remove(walletPay)
 }

 export function setTempInfo(token,info,expiresTime) {
     let seconds = expiresTime;
     let expires = new Date(new Date() * 1 + seconds * 1000);
     return Cookies.set(token,info,{ expires: expires });
 }

 export function getTempInfo(token) {
     return Cookies.get(token);
 }

 export function removeTempInfo(token) {
     return Cookies.remove(token);
 }