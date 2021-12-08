import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css'
import { initMenu } from './utils/menus';
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
// import {initMenu} from "./utils/menus";
import {downloadRequest} from "./utils/download";

Vue.config.productionTip = false
Vue.use(ElementUI, {size: 'small'});
//插件形式使用请求
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.downloadRequest = downloadRequest;

router.beforeEach((to, from, next) => {
    if (window.sessionStorage.getItem("tokenStr")) {
        initMenu(router,store)
        console.log(window.sessionStorage.getItem("user"))
        if(!window.sessionStorage.getItem("user")) {
            return getRequest('/admin/info').then(resp=>{
                if(resp){
                    window.sessionStorage.setItem("user",JSON.stringify(resp))
                    console.log(window.sessionStorage.getItem("user"))
                    next()
                }
            });
        }
        next()
    }else {
        if(to.path === '/'){
            next()
        } 
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
