import fetch from '../../util/fetch';

export function disposeReport(param) {
    return fetch({
        url: '/api/adminApi/report/disposeReport',
        params:param,
        method: 'post'
    });
};


export function qryReportList(param) {
    return fetch({
        url: '/api/adminApi/report/qryReportList',
        params:param,
        method: 'get'
    });
};

