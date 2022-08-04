import fetch from '../../utils/fetch';

export function qryUserOrderList(param) {
    return fetch({
        url: '/api/userApi/userOrder/qryUserOrderList',
        params:param,
        method: 'get'
    });
}


export function saveRefundRecord(param) {
    return fetch({
        url: '/api/userApi/refundRecord/saveRefundRecord',
        params:param,
        method: 'post'
    });
}

export function qryRefundRecordList(param) {
    return fetch({
        url: '/api/userApi/refundRecord/qryRefundRecordList',
        params:param,
        method: 'get'
    });
}

export function upUserOrderProtocol(param) {
    return fetch({
        url: '/api/userApi/userOrder/upUserOrderProtocol',
        params:param,
        method: 'post'
    });
}

