<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <nav class="nav">
    <div class="container">
      <div class="logo">
        <a href="#"><router-link to="/"><img src="/images/logo.png" class="logoNavbar" /></router-link></a>
      </div>
      <div id="mainListDiv" class="main_list">
        <ul class="navlinks">
          <li>
            <div class="dropdown">
              <a>Libros</a>
              <div class="dropdown-content" style="z-index: 10">
                <a><router-link to="/books">Buscar Libros</router-link></a>
                <a v-if="isAuthenticated"><router-link to="/userbooks">Mis Libros</router-link></a>
              </div>
            </div>
          </li>
          <li>
            <div class="dropdown">
              <a>Álbumes</a>
              <div class="dropdown-content" style="z-index: 9">
                <a><router-link to="/albums">Buscar Álbumes</router-link></a>
                <a v-if="this.isAuthenticated"><router-link to="/useralbums">Mis álbumes</router-link></a>
              </div>
            </div>
          </li>
          <li>
            <a v-if="this.isAuthenticated && this.rol == 'ADMIN'"><router-link to="/admin">Admin</router-link></a>
          </li>
          <li v-if="this.isAuthenticated">
            <div class="dropdown">
              <a><img v-if="imgUser != null && imgUser != ''" class="imgIconNav" :src="imgUser" />
                <img v-if="imgUser == null || imgUser == ''" class="imgIconNav"
                  src="https://imgs.search.brave.com/NsCoYsr-Ce_T2Nb7SsSJvFyA16_MwV90qm-RuC_YTLU/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/cGl4YWJheS5jb20v/cGhvdG8vMjAxOC8x/MS8xMy8yMS80My9h/dmF0YXItMzgxNDA0/OV82NDAucG5n" />
                ></a>
              <div class="dropdown-content-user">
                <a><router-link :to="{
                  name: 'UserDetails',
                  params: { userName: this.nombreUsuario },
                }">Editar Perfil</router-link></a>
                <a @click="logout">Cerrar Sesión</a>
              </div>
            </div>
          </li>
          <li v-if="!this.isAuthenticated">
            <button class="customButton" alt="Inicia Sesión" @click="navigateTo('/login')">
              <i>I</i>
              <i>n</i>
              <i>i</i>
              <i>c</i>
              <i>i</i>
              <i>a</i>
              <i>&nbsp;</i>
              <i>s</i>
              <i>e</i>
              <i>s</i>
              <i>i</i>
              <i>ó</i>
              <i>n</i>
            </button>
          </li>
        </ul>
      </div>
      <div id="mainListDivMobile" class="main_list hide">
        <ul class="navlinks">
          <li class="liMobile liTitle">Libros</li>
          <li class="liMobile">
            <a><router-link to="/books">Buscar Libros</router-link></a>
          </li>
          <li class="liMobile"><a v-if="isAuthenticated"><router-link to="/userbooks">Mis Libros</router-link></a></li>
          <li class="liMobile liTitle mt-4">Álbumes</li>
          <li class="liMobile">
            <a><router-link to="/albums">Buscar Álbumes</router-link></a>
          </li>
          <li class="liMobile">
            <a v-if="this.isAuthenticated"><router-link to="/useralbums">Mis álbumes</router-link></a>
          </li>
          <li class="liMobile liTitle mt-4" v-if="this.isAuthenticated">
            <img v-if="imgUser != null && imgUser != ''" class="imgIconNav" :src="imgUser" />
            <img v-if="imgUser == null || imgUser == ''" class="imgIconNav"
              src="https://imgs.search.brave.com/NsCoYsr-Ce_T2Nb7SsSJvFyA16_MwV90qm-RuC_YTLU/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jZG4u/cGl4YWJheS5jb20v/cGhvdG8vMjAxOC8x/MS8xMy8yMS80My9h/dmF0YXItMzgxNDA0/OV82NDAucG5n" />
            {{ this.nombreUsuario }}
          </li>
          <li class="liMobile" v-if="this.isAuthenticated && this.rol == 'ADMIN'">
            <a><router-link to="/admin">Admin</router-link></a>
          </li>
          <li class="liMobile" v-if="this.isAuthenticated">
            <a><router-link :to="{
                  name: 'UserDetails',
                  params: { userName: this.nombreUsuario },
                }">Editar Perfil</router-link></a>
          </li>
          <li class="liMobile" v-if="this.isAuthenticated">
            <a @click="logout">Cerrar Sesión</a>
          </li>
          <li class="liMobile" v-if="!this.isAuthenticated">
            <button class="customButton" alt="Inicia Sesión" @click="navigateTo('/login')">
              <i>I</i>
              <i>n</i>
              <i>i</i>
              <i>c</i>
              <i>i</i>
              <i>a</i>
              <i>&nbsp;</i>
              <i>s</i>
              <i>e</i>
              <i>s</i>
              <i>i</i>
              <i>ó</i>
              <i>n</i>
            </button>
          </li>
        </ul>
      </div>
      <span class="navTrigger">
        <i></i>
        <i></i>
        <i></i>
      </span>
    </div>
  </nav>
</template>

<script>
import $ from "jquery";
export default {
  data() {
    return {
      isAuthenticated: false,
      nombreUsuario: "",
      dropdownOpen: false,
      imgUser: null,
      username: "",
      rol: ""
    };
  },
  mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      let userInfo = this.$cookies.get("user");
      this.nombreUsuario = userInfo.nombre;
      this.imgUser = userInfo.image;
      this.username = userInfo.username;
      this.rol = userInfo.rol;
    }
    $(".navTrigger").on("click", this.handleClick);
    $(window).resize(this.handleResize);
  },
  created() {
    this.checkAuthentication();
  },
  methods: {
    handleResize() {
      if ($(window).width() > 650) {
        $("#mainListDivMobile").addClass("hide");
      }
    },
    handleClick() {
      $(this).toggleClass("active");
      console.log("Clicked menu");
      $("#mainListDivMobile").toggleClass("show_list");
      $("#mainListDivMobile").fadeIn();
      $("#mainListDivMobile").removeClass("hide");
    },
    toggleDropdown() {
      if (this.dropdownOpen) {
        this.dropdownOpen = false;
      } else {
        this.dropdownOpen = true;
      }
    },
    checkAuthentication() {
      const token = localStorage.getItem("token");
      this.isAuthenticated = !!token;
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("usuario");
      this.$cookies.remove("user");
      this.isAuthenticated = false;
      this.$router.push("/"); // Redirige a la página principal
    },
    navigateTo(route) {
      this.$router.push(route);
    },
    redirectToHome() {
      this.$router.push("/"); // Redirige a la página principal
    },
  },
  watch: {
    $route() {
      this.checkAuthentication(); // Vuelve a comprobar la autenticación cuando la ruta cambie
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Quicksand:400,500,700");

html,
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Quicksand", sans-serif;
  font-size: 62.5%;
  font-size: 10px;
}

.hide {
  display: none !important;
}

.liTitle {
  border-bottom: 1px solid white;
  font-size: 3rem;
  color: white;
}

/*-- Inspiration taken from abdo steif -->
/* --> https://codepen.io/abdosteif/pen/bRoyMb?editors=1100*/

/* Navbar section */
.dropdown {
  display: inline-block;
  position: relative;
}

.dropdown-content {
  display: none;
  position: absolute;
  width: 200px;
  overflow: auto;
  box-shadow: 0px 10px 10px 0px rgba(0, 0, 0, 0.4);
}

.liMobile {
  text-align: center !important;
  padding-right: 0px !important;
}

.dropdown-content-user {
  display: none;
  position: absolute;
  width: 200px;
  overflow: auto;
  box-shadow: 0px 10px 10px 0px rgba(0, 0, 0, 0.4);
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown-content a {
  display: block;
  color: white;
  background-color: black;
  z-index: 9999 !important;
  padding: 5px;
  text-decoration: none;
  font-size: 25px !important;
}

.dropdown:hover .dropdown-content-user {
  display: block;
}

.dropdown-content-user a {
  display: block;
  color: white;
  background-color: black;
  z-index: 9999 !important;
  padding: 5px;
  text-decoration: none;
  font-size: 25px !important;
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  background-color: black;
  text-align: center;
}

.nav div.logo {
  float: left;
  width: auto;
  height: auto;
  padding-left: 3rem;
}

.nav div.logo a {
  text-decoration: none;
  color: #fff;
  font-size: 2.5rem;
}

.nav div.logo a:hover {
  color: #00e676;
}

.nav div.main_list {
  height: 65px;
  float: right;
}

.nav div.main_list ul {
  width: 100%;
  height: 65px;
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav div.main_list ul li {
  width: auto;
  height: 65px;
  padding: 0;
  padding-right: 3rem;
}

.nav div.main_list ul li a {
  text-decoration: none;
  color: #fff;
  line-height: 65px;
  font-size: 2.4rem;
}

.nav div.main_list ul li a:hover {
  color: rgb(56, 90, 194);
}

/* Home section */

.home {
  width: 100%;
  height: 100vh;
  background-image: url(https://images.unsplash.com/photo-1498550744921-75f79806b8a7?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=b0f6908fa5e81286213c7211276e6b3d&auto=format&fit=crop&w=1500&q=80);
  background-position: center top;
  background-size: cover;
}

.navTrigger {
  display: none;
}

.nav {
  padding-top: 20px;
  padding-bottom: 20px;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
}

/* Media qurey section */

@media screen and (min-width: 768px) and (max-width: 1024px) {
  .container {
    margin: 0;
  }
}

@media screen and (max-width: 768px) {
  .navTrigger {
    display: block;
  }

  .nav div.logo {
    margin-left: 15px;
  }

  button {
    display: initial !important;
  }

  .nav div.main_list {
    width: 100%;
    height: 0;
    overflow: hidden;
  }

  .nav div.show_list {
    height: auto;
    display: none;
  }

  .nav div.main_list ul {
    flex-direction: column;
    width: 100%;
    height: 100vh;
    right: 0;
    left: 0;
    bottom: 0;
    background-color: #111;
    /*same background color of navbar*/
    background-position: center top;
  }

  .nav div.main_list ul li {
    width: 100%;
    text-align: right;
  }

  .nav div.main_list ul li a {
    text-align: center;
    width: 100%;
    font-size: 2rem;
    padding: 20px;
  }

  .nav div.media_button {
    display: block;
  }
}

/* Animation */
/* Inspiration taken from Dicson https://codemyui.com/simple-hamburger-menu-x-mark-animation/ */

.navTrigger {
  cursor: pointer;
  width: 30px;
  height: 25px;
  margin: auto;
  position: absolute;
  right: 30px;
  top: 0;
  bottom: 0;
}

.navTrigger i {
  background-color: #fff;
  border-radius: 2px;
  content: "";
  display: block;
  width: 100%;
  height: 4px;
}

.navTrigger i:nth-child(1) {
  -webkit-animation: outT 0.8s backwards;
  animation: outT 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger i:nth-child(2) {
  margin: 5px 0;
  -webkit-animation: outM 0.8s backwards;
  animation: outM 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger i:nth-child(3) {
  -webkit-animation: outBtm 0.8s backwards;
  animation: outBtm 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger.active i:nth-child(1) {
  -webkit-animation: inT 0.8s forwards;
  animation: inT 0.8s forwards;
}

.navTrigger.active i:nth-child(2) {
  -webkit-animation: inM 0.8s forwards;
  animation: inM 0.8s forwards;
}

.navTrigger.active i:nth-child(3) {
  -webkit-animation: inBtm 0.8s forwards;
  animation: inBtm 0.8s forwards;
}

.imgIconNav {
  width: 50px !important;
  border-radius: 30px !important;
}

@-webkit-keyframes inM {
  50% {
    -webkit-transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(45deg);
  }
}

@keyframes inM {
  50% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(45deg);
  }
}

@-webkit-keyframes outM {
  50% {
    -webkit-transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(45deg);
  }
}

@keyframes outM {
  50% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(45deg);
  }
}

@-webkit-keyframes inT {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }

  50% {
    -webkit-transform: translateY(9px) rotate(0deg);
  }

  100% {
    -webkit-transform: translateY(9px) rotate(135deg);
  }
}

@keyframes inT {
  0% {
    transform: translateY(0px) rotate(0deg);
  }

  50% {
    transform: translateY(9px) rotate(0deg);
  }

  100% {
    transform: translateY(9px) rotate(135deg);
  }
}

@-webkit-keyframes outT {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }

  50% {
    -webkit-transform: translateY(9px) rotate(0deg);
  }

  100% {
    -webkit-transform: translateY(9px) rotate(135deg);
  }
}

@keyframes outT {
  0% {
    transform: translateY(0px) rotate(0deg);
  }

  50% {
    transform: translateY(9px) rotate(0deg);
  }

  100% {
    transform: translateY(9px) rotate(135deg);
  }
}

@-webkit-keyframes inBtm {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }

  50% {
    -webkit-transform: translateY(-9px) rotate(0deg);
  }

  100% {
    -webkit-transform: translateY(-9px) rotate(135deg);
  }
}

@keyframes inBtm {
  0% {
    transform: translateY(0px) rotate(0deg);
  }

  50% {
    transform: translateY(-9px) rotate(0deg);
  }

  100% {
    transform: translateY(-9px) rotate(135deg);
  }
}

@-webkit-keyframes outBtm {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }

  50% {
    -webkit-transform: translateY(-9px) rotate(0deg);
  }

  100% {
    -webkit-transform: translateY(-9px) rotate(135deg);
  }
}

@keyframes outBtm {
  0% {
    transform: translateY(0px) rotate(0deg);
  }

  50% {
    transform: translateY(-9px) rotate(0deg);
  }

  100% {
    transform: translateY(-9px) rotate(135deg);
  }
}

.affix {
  padding: 0;
  height: 65px;
  background-color: #111;
}

.myH2 {
  text-align: center;
  font-size: 4rem;
}

.myP {
  text-align: justify;
  padding-left: 15%;
  padding-right: 15%;
  font-size: 20px;
}

@media all and (max-width: 700px) {
  .myP {
    padding: 2%;
  }
}

button {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  position: relative;
  padding: 0 20px;
  font-size: 18px;
  text-transform: uppercase;
  border: 0;
  box-shadow: hsl(210deg 87% 36%) 0px 7px 0px 0px;
  background-color: hsl(210deg 100% 44%);
  border-radius: 12px;
  overflow: hidden;
  transition: 31ms cubic-bezier(0.5, 0.7, 0.4, 1);
}

button:before {
  content: attr(alt);
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  inset: 0;
  font-size: 15px;
  font-weight: bold;
  color: white;
  letter-spacing: 4px;
  opacity: 1;
}

button:active {
  box-shadow: none;
  transform: translateY(7px);
  transition: 35ms cubic-bezier(0.5, 0.7, 0.4, 1);
}

button:hover:before {
  transition: all 0s;
  transform: translateY(100%);
  opacity: 0;
}

button i {
  color: white;
  font-size: 15px;
  font-weight: bold;
  letter-spacing: 4px;
  font-style: normal;
  transition: all 2s ease;
  transform: translateY(-20px);
  opacity: 0;
}

button:hover i {
  transition: all 0.2s ease;
  transform: translateY(0px);
  opacity: 1;
}

button:hover i:nth-child(1) {
  transition-delay: 0.045s;
}

button:hover i:nth-child(2) {
  transition-delay: calc(0.045s * 3);
}

button:hover i:nth-child(3) {
  transition-delay: calc(0.045s * 4);
}

button:hover i:nth-child(4) {
  transition-delay: calc(0.045s * 5);
}

button:hover i:nth-child(5) {
  transition-delay: calc(0.045s * 7);
}

button:hover i:nth-child(6) {
  transition-delay: calc(0.045s * 6);
}

button:hover i:nth-child(8) {
  transition-delay: calc(0.045s * 8);
}

button:hover i:nth-child(9) {
  transition-delay: calc(0.045s * 9);
}

button:hover i:nth-child(10) {
  transition-delay: calc(0.045s * 10);
}

button:hover i:nth-child(11) {
  transition-delay: calc(0.045s * 10);
}

button:hover i:nth-child(12) {
  transition-delay: calc(0.045s * 10);
}

button:hover i:nth-child(13) {
  transition-delay: calc(0.045s * 10);
}

.logoNavbar {
  width: 60px;
}
</style>
