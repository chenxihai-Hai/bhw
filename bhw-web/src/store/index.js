import Vue from "vue";
import Vuex from "vuex";
import user from './modules/user';
import getters from './getters';
Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        user,
    },
    global:{
        navbarFlag:true
    },
    getters
});

export default store
