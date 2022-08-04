import fetch from "../../util/fetch";

export function brokerRegister(param){
    return fetch({
        url: '/api/adminApi/broker/brokerRegister',
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



export function backQryBrokerList(param){
    return fetch({
        url: '/api/adminApi/broker/backQryBrokerList',
        params:param,
        method: 'get'
    });
};

export function backQryBrokerTurncverList(param){
    return fetch({
        url: '/api/adminApi/broker/backQryBrokerTurncverList',
        params:param,
        method: 'get'
    });
};


export function delBrokerRegister(param){
    return fetch({
        url: '/api/adminApi/broker/delBrokerRegister',
        params:param,
        method: 'post'
    });
};