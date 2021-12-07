import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
    state: { // 全局State对象 用于保存所有组件的公共数据
        routes: []
    },
    mutations: {
        initRoutes(state, data) {
            state.routes = data
        }
    },
    actions: {}
})