import { createApp } from 'vue'
import Login from './views/Login.vue'
import router from './router'
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'
createApp(Login).use(router).use(ElementUI).mount('#app')
