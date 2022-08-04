import fetch from '../../utils/fetch';

export function qryInvoiceUserList(param) {
    return fetch({
        url: '/api/userApi/invoice/qryInvoiceUserList',
        params:param,
        method: 'get'
    });
}
export function delInvoiceUser(param) {
    return fetch({
        url: '/api/userApi/invoice/delInvoiceUser',
        params:param,
        method: 'get'
    });
}


export function saveInvoiceUser(param) {
    return fetch({
        url: '/api/userApi/invoice/saveInvoiceUser',
        params:param,
        method: 'post'
    });
}

export function saveInvoiceRecord(param) {
    return fetch({
        url: '/api/userApi/invoice/saveInvoiceRecord',
        params:param,
        method: 'post'
    });
}


export function upInvoiceUser(param) {
    return fetch({
        url: '/api/userApi/invoice/upInvoiceUser',
        params:param,
        method: 'post'
    });
}


export function qryInvoicRecordList(param) {
    return fetch({
        url: '/api/userApi/invoice/qryInvoicRecordList',
        params:param,
        method: 'get'
    });
}