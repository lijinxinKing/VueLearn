import Vue from 'vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
import AdminInfo from '../views/AdminInfo'
Vue.use(VueRouter)
Vue.use(Vuex)

const routes = [
{
    path: '/',
    name: 'Login',
    component: Login
},
{
    path: '/home',
    name: 'Home',
    component: Home,
    children:[
        {
            path: '/userinfo',
            name: '个人中心',
            component: AdminInfo,
        }
    ]
}
]
const router = new VueRouter({
    routes
})
//解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;
//push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch(err => err);
};
//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch(err => err);
};

export default router