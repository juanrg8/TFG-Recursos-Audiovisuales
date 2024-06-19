import VueCookies from "vue-cookies";
import { createApp } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import App from "./App.vue";
import HomeScreen from "./components/HomeScreen.vue";
import Login from "./components/auth/UserLogin.vue";
import Register from "./components/auth/UserRegister.vue";
import Albums from "./components/albums/Albums.vue";
import UserAlbums from "./components/albums/UserAlbums.vue";
import AlbumReview from "./components/albums/AlbumReview.vue";
import AlbumDetails from "./components/albums/AlbumDetails.vue";
import UserDetails from "./components/user/UserDetails.vue";
import UserForm from "./components/user/UserForm.vue";
import Books from "./components/books/BookScreen.vue";
import UserBooks from "./components/books/UserBooks.vue";
import BookDetails from "./components/books/BookDetails.vue";
import BookReview from "./components/books/BookReview.vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";

// Define tus rutas
const routes = [
  { path: "/", component: HomeScreen },
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/albums", component: Albums },
  { path: "/useralbums", component: UserAlbums },
  {
    path: "/useralbums/update/:spotifyId",
    name: "AlbumReview",
    component: AlbumReview,
  },
  {
    path: "/useralbums/details/:spotifyId",
    name: "AlbumDetails",
    component: AlbumDetails,
  },
  {
    path: "/user/details",
    name: "UserDetails",
    component: UserDetails,
  },
  {
    path: "/user/edit",
    name: "UserForm",
    component: UserForm,
  },
  { path: "/books", component: Books },
  { path: "/userbooks", component: UserBooks },
  {
    path: "/userbooks/details/:libroId",
    name: "BookDetails",
    component: BookDetails,
  },
  {
    path: "/userbooks/update/:libroId",
    name: "BookReview",
    component: BookReview,
  },
];

// Crea el enrutador con la historia de navegación basada en la ubicación web
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Crea la aplicación y monta el enrutador y la aplicación en el elemento con el ID 'app'
createApp(App)
  .use(router) // Usa el enrutador
  .use(VueCookies)
  .mount("#app");

//createApp.$cookies.config("1d");
