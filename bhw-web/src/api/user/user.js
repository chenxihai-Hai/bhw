import fetch from '../../utils/fetch';

export function upBhwUser(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upBhwUser',
        params:param,
        method: 'post'
    });
}

export function upUserPhone(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upUserPhone',
        params:param,
        method: 'post'
    });
}


export function upBhwCompanyCardInfo(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upBhwCompanyCardInfo',
        params:param,
        method: 'post'
    });
}


export function getUserInfoByToken(param) {
    return fetch({
        url: '/api/userApi/bhwUser/getUserInfoByToken',
        params:param,
        method: 'post'
    });
}

export function getUserDetailByToken(param) {
    return fetch({
        url: '/api/userApi/bhwUser/getUserDetailByToken',
        params:param,
        method: 'post'
    });
}
export function getUserSubsInfo(param) {
    return fetch({
        url: '/api/userApi/bhwUser/getUserSubsInfo',
        params:param,
        method: 'post'
    });
}

export function userIdCardverify(param) {
    return fetch({
        url: '/api/userApi/bhwUser/userIdCardverify',
        params:param,
        method: 'post'
    });
}

export function compayVerify(param) {
    return fetch({
        url: '/api/userApi/bhwUser/compayVerify',
        params:param,
        method: 'post'
    });
}


export function getBhwCompanyCardInfo(param) {
    return fetch({
        url: '/api/userApi/bhwUser/getBhwCompanyCardInfo',
        params:param,
        method: 'get'
    });
}

export function upUserPw(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upUserPw',
        params:param,
        method: 'post'
    });
}

export function upUserPayPw(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upUserPayPw',
        params:param,
        method: 'post'
    });
}


export function upUserMemberByPay(param) {
    return fetch({
        url: '/api/userApi/bhwUser/upUserMemberByPay',
        params:param,
        method: 'post'
    });
}

export function saveUserMember(param) {
    return fetch({
        url: '/api/userApi/bhwUser/saveUserMember',
        params:param,
        method: 'post'
    });
}


export function getUserMember(param) {
    return fetch({
        url: '/api/userApi/bhwUser/getUserMember',
        params:param,
        method: 'get'
    });
}

