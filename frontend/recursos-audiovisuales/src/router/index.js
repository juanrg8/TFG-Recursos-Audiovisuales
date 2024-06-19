import Vue from "vue";
import VueRouter from "vue-router";
import UserRegister from "../components/UserRegister.vue";
import UserLogin from "../components/auth/UserLogin.vue";
import HomeScreen from "../components/HomeScreen.vue";
import Albums from "../components/albums/Albums.vue";
import UserAlbums from "../components/albums/UserAlbums.vue";
import AlbumReview from "../components/albums/AlbumReview.vue";
import AlbumDetails from "../components/albums/AlbumDetails.vue";
import UserDetails from "../components/user/UserDetails.vue";
import UserForm from "../components/user/UserForm.vue";
import Books from "../components/books/BookScreen.vue";
import UserBooks from "../components/books/UserBooks.vue";
import BookDetails from "../components/books/BookDetails.vue";
import BookReview from "../components/books/BookReview.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", component: HomeScreen },
  { path: "/register", component: UserRegister },
  { path: "/login", component: UserLogin },
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

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
