import fetch from '../../utils/fetch';

export function captcha(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/captcha',
        params: param,
        method: 'get'
    });
};

export function captchaVi(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/captchaVi',
        params: param,
        method: 'get'
    });
};


export function phoneCaptcha(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/phoneCaptcha',
        params: param,
        method: 'get'
    });
};

export function verifyPhonecaptcha(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/verifyPhonecaptcha',
        params: param,
        method: 'post'
    });
};
export function getAccountStatus(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/getAccountStatus',
        params: param,
        method: 'get'
    });
};
export function changeAccountStatus(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/changeAccountStatus',
        params: param,
        method: 'post'
    });
};
export function setPayPwd(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/setPayPwd',
        params: param,
        method: 'post'
    });
};
export function verifyIdentity(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/verifyIdentity',
        params: param,
        method: 'post'
    });
};
export function changeAccountPassword(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/changeAccountPassword',
        params: param,
        method: 'post'
    });
};

export function accountAppealverify(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/accountAppealverify',
        params: param,
        method: 'post'
    });
};

export function accountPwdAppeal(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/accountPwdAppeal',
        params: param,
        method: 'post'
    });
};

export function accountPhoneAppeal(param) {
    return fetch({
        url: '/api/userApi/payAccountMsg/accountPhoneAppeal',
        params: param,
        method: 'post'
    });
};

