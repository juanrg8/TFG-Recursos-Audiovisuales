import { createRouter, createWebHistory } from "vue-router";
import UserRegister from "../components/auth/UserRegister.vue";
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
import AdminPanel from "../components/user/AdminPanel.vue";

const routes = [
  { path: "/", component: HomeScreen },
  { path: "/register", component: UserRegister },
  { path: "/login", component: UserLogin },
  { path: "/admin", component: AdminPanel },
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

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
