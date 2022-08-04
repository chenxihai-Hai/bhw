import fetch from '../../utils/fetch';

export function newsListPageReq(param) {
    return fetch({
        url: '/api/adminApi/news/newsListPage',
        params:param,
        method: 'get'
    });
};

export function newsContentPageReq(param) {
    return fetch({
        url: '/api/adminApi/news/newsContentPage',
        params:param,
        method: 'get'
    });
};