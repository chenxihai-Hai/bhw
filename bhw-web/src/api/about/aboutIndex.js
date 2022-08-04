import fetch from '../../utils/fetch';

export function saveReport(param) {
    return fetch({
        url: '/api/adminApi/report/saveReport',
        params:param,
        method: 'post'
    });
};