// src/router/index.js

import Vue from "vue";
import VueRouter from "vue-router";
import UserRegister from "../components/UserRegister.vue";
import UserLogin from "../components/auth/UserLogin.vue";
import HomeScreen from "../components/HomeScreen.vue";
import Albums from "../components/albums/Albums.vue";
import UserAlbums from "../components/albums/UserAlbums.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", component: HomeScreen },
  { path: "/register", component: UserRegister },
  { path: "/login", component: UserLogin },
  { path: "/albums", component: Albums },
  { path: "/useralbums", component: UserAlbums },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
