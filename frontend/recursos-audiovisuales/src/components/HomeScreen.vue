<template>
  <Navbar style="z-index: 9" />
  <!-- <section class="home"></section> -->
  <div class="cont_container">
    <div class="textContainer">
      <div>
        <h2 class="myH2">Bienvenido a GoodTaste!</h2>
        <p class="myP">
          GoodTaste es tu plataforma ideal para explorar y disfrutar de la
          música y tus libros favoritos. Aquí podrás:
        </p>
        <p class="myP">
          ¡Buscar tus álbumes o libros favoritos y descubrir nuevos!
        </p>
        <p class="myP">
          ¡Añadir álbumes o libros a tu lista personal para un fácil acceso!
        </p>
        <p class="myP">
          ¡Escribir reseñas de tus álbumes o libros para registrar tus
          opiniones!
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "./Navbar.vue";

export default {
  name: "HomeScreen",
  components: {
    Navbar,
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

.cont_container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("/public/images/pikaso_texttoimage_A-vintage-record-player-on-a-wooden-table-in-a-coz.jpeg");
  background-position: center top;
  background-size: cover;
  height: 100vh;
}

.textContainer {
  background: rgba(255, 255, 255, 0.38);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(6.5px);
  -webkit-backdrop-filter: blur(6.5px);
  color: black;
}

.cont {
  display: flex;
  align-items: center;
}

.nav {
  width: 100%;
  height: 90px;
  line-height: 65px;
  text-align: center;
}

.myH2 {
  text-align: center;
  font-size: 4rem;
}
.myP {
  text-align: center;
  padding-left: 15%;
  padding-right: 15%;
  font-size: 20px;
}
@media all and (max-width: 700px) {
  .myP {
    padding: 2%;
  }
}
</style>
