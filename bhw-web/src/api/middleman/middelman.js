import fetch from "../../utils/fetch";

//获取经纪人列表
export function queryManList(param){
    return fetch({
        url: '/api/adminApi/broker/qryBrokerList',
        params:param,
        method: 'get'
    });
};

export function saveBrokerEvaluate(param){
    return fetch({
        url: '/api/adminApi/broker/saveBrokerEvaluate',
        params:param,
        method: 'get'
    });
};


export function brokerRegister(param){
    return fetch({
        url: '/api/adminApi/broker/brokerRegister',
        params:param,
        method: 'post'
    });
};

export function backQryBrokerList(param){
    return fetch({
        url: '/api/adminApi/broker/backQryBrokerList',
        params:param,
        method: 'post'
    });
};

export function delBrokerRegister(param){
    return fetch({
        url: '/api/adminApi/broker/delBrokerRegister',
        params:param,
        method: 'post'
    });
};

export function upBrokerAdvisory(param){
    return fetch({
        url: '/api/adminApi/broker/upBrokerAdvisory',
        params:param,
        method: 'post'
    });
};


export function qryServerAdvisoryList(param) {
    return fetch({
        url: '/api/adminApi/advisory/qryServerAdvisoryList',
        params:param,
        method: 'get'
    });
}

export function upServerAdvisory(param) {
    return fetch({
        url: '/api/adminApi/advisory/upServerAdvisory',
        params:param,
        method: 'post'
    });
}

export function delServerAdvisory(param) {
    return fetch({
        url: '/api/adminApi/advisory/delServerAdvisory',
        params:param,
        method: 'get'
    });
}