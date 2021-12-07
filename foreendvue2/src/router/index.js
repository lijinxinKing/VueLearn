import Vue from 'vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
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
    component: Home
}
]
const router = new VueRouter({
    routes
})
export default router