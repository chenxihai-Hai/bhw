import fetch from '../../util/fetch';

export function backQryRefundRecordList(param) {
    return fetch({
        url: '/api/serverManage/refundRecord/backQryRefundRecordList',
        params:param,
        method: 'get'
    });
}