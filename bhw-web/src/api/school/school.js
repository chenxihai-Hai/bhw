import fetch from "../../utils/fetch";

//获取高校数量
export function getCollegeCount(param) {
    return fetch({
        url: '/api/adminApi/college/getCollegeCount',
        params: param,
        method: 'get'
    });
}

//获取高校列表、
export function getCollege(param){
    return fetch({
        url: '/api/adminApi/college/getCollege',
        params:param,
        method: 'get'
    });
};

//获取高校详情成果列表、
export function getPatentList(param) {
    return fetch({
        url: '/api/adminApi/patent/qryPatentList',
        params: param,
        method: 'get'
    });
}

//按类型查找资源总数
export function getResourceCount(param){
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceCount',
        params:param,
        method: 'get'
    });
};
//查询各省份资源数
export function getResourceProvince(param){
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceProvince',
        params:param,
        method: 'get'
    });
};
//联动查询
export function getResourceAreaRange(param) {
    return fetch({
        url: '/api/adminApi/resourceArea/getResourceAreaRange',
        params: param,
        method: 'get'
    });
};
