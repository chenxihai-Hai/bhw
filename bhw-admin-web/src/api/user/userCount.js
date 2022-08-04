import fetch from '../../util/fetch';

export function getCount() {
    return fetch({
        url: '/api/userApi/userCount/getCount',
        method: 'get'
    });
};

export function getAddUser(param) {
    return fetch({
        url: '/api/userApi/userCount/getAddUser',
        params:param,
        method: 'get'
    });
};