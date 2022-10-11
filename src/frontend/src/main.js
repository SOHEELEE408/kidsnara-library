import { createApp } from 'vue'
import { router } from './router/index'
import App from './App.vue'
import mitt from 'mitt';

import BootstrapVue3 from "bootstrap-vue-3";
import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

const emitter = mitt();

const app = createApp(App)
app.use(router)
app.use(BootstrapVue3)

app.config.globalProperties.axios=axios
app.config.globalProperties.emitter = emitter

app.mount('#app')
