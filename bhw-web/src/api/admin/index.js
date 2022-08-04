import fetch from '../../utils/fetch';

export function qryProvinceList() {
    return fetch({
        url: '/api/adminApi/city/qryProvinceList',
        method: 'get'
    });
}

export function qryCityList(param) {
    return fetch({
        url: '/api/adminApi/city/qryCityList',
        params:param,
        method: 'get'
    });
}


export function sendPhoneCode(param) {
    return fetch({
        url: '/api/adminApi/advisory/sendPhoneCode',
        params:param,
        method: 'get'
    });
}


export function saveServerAdvisory(param) {
    return fetch({
        url: '/api/adminApi/advisory/saveServerAdvisory',
        params:param,
        method: 'post'
    });
}


export function qryBrokerAdvisory(param) {
    return fetch({
        url: '/api/adminApi/brokerAdvisory/qryBrokerAdvisory',
        params:param,
        method: 'get'
    });
}


export function saveBrokerAdvisory(param) {
    return fetch({
        url: '/api/adminApi/brokerAdvisory/saveBrokerAdvisory',
        params:param,
        method: 'post'
    });
}


export function saveBrokerLeaveMsg(param) {
    return fetch({
        url: '/api/adminApi/brokerAdvisory/saveBrokerLeaveMsg',
        params:param,
        method: 'post'
    });
}

export function getLanguageConfig(param) {
    return fetch({
        url: '/api/adminApi/language/getLanguageConfig',
        params:param,
        method: 'get'
    });
}
