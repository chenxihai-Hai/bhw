import fetch from '../../utils/fetch';

export function listedOnNumberReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedOnNumber',
        params:param,
        method: 'get'
    });
};

export function listedMessageReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedMessage',
        params:param,
        method: 'get'
    });
};

export function listedResultReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedResult',
        params:param,
        method: 'get'
    });
};

export function listedSuccessReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedResult',
        params:param,
        method: 'get'
    });
};

export function birefNoticeReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/birefNotice',
        params:param,
        method: 'get'
    });
};

export function noticeInfoReq(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/noticeInfo',
        params:param,
        method: 'get'
    });
};

export function getcaptchaCode(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/getcaptchaCode',
        params:param,
        method: 'get'
    });
};

export function listedAdvisory(param) {
    return fetch({
        url: '/api/adminApi/listedTranstion/listedAdvisory',
        params:param,
        method: 'post'
    });
};
