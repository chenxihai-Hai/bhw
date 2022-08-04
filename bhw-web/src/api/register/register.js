import fetch from '../../utils/fetch';

export function userLoginReq(param) {
    return fetch({
        url: '/api/userApi/login/userLogin',
        params:param,
        method: 'get'
    });
};
export function captchaReq(param) {
    return fetch({
        url: '/api/userApi/register/captcha',
        params:param,
        method: 'get'
    });
};
export function checkCaptchaReq(param) {
    return fetch({
        url: '/api/userApi/register/checkCaptcha',
        params:param,
        method: 'get'
    });
};
export function viCaptchaCheckByCode(param) {
    return fetch({
        url: '/api/userApi/register/viCaptchaCheckByCode',
        params:param,
        method: 'get'
    });
};


export function personalRegisterReq(param) {
    return fetch({
        url: '/api/userApi/register/personalRegister',
        params:param,
        method: 'post'
    });
};
export function enterpriseRegisterReq(param) {
    return fetch({
        url: '/api/userApi/register/enterpriseRegister',
        params:param,
        method: 'post'
    });
}
export function forgetPassword(param) {
    return fetch({
        url: '/api/userApi/register/forgetPassword',
        params:param,
        method: 'post'
    });
}
export function getUserByPhone(param) {
    return fetch({
        url: '/api/userApi/bhwUser/qryUser',
        params:param,
        method: 'post'
    });
}
