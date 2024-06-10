// src/router/index.js

import Vue from 'vue';
import VueRouter from 'vue-router';
import UserRegister from '../components/UserRegister.vue';
import UserLogin from '../components/Login.vue';
import HomeScreen from '../components/HomeScreen.vue';

Vue.use(VueRouter);

const routes = [
  { path: '/', component: HomeScreen },
  { path: '/register', component: UserRegister },
  { path: '/login', component: UserLogin },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
