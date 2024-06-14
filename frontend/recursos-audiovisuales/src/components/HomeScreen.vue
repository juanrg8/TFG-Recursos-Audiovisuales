<template>
  <div class="background-image">
    <Navbar class="navbar" />
    <div class="container">
      <h1>Bienvenido a GoodTaste!</h1>
      <p class="intro-text">
        GoodTaste es tu plataforma ideal para explorar y disfrutar de la música. Aquí podrás:
      </p>
      <ul class="features-list">
        <li>¡Buscar tus álbumes favoritos y descubrir nueva música!</li>
        <li>¡Añadir álbumes a tu lista personal para un fácil acceso!</li>
        <li>¡Escribir reseñas de tus álbumes para registrar tus opiniones!</li>
      </ul>
    </div>
  </div>
</template>

<script>
import Navbar from './Navbar.vue';

export default {
  name: 'HomeScreen',
  components: {
    Navbar,
  },
};
</script>

<style scoped>
.background-image {
  background-image: url('/public/images/background.jpg');
  /* Asegúrate de usar la ruta correcta de la imagen */
  background-size: cover;
  background-position: center;
  min-height: 100vh;
  /* Asegura que el fondo cubra toda la pantalla */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  /* Asegura que el texto sea legible */
  position: relative;
}

.navbar {
  position: absolute;
  /* Asegura que la Navbar esté siempre en la parte superior */
  top: 20px;
  /* Añade margen superior a la Navbar */
  width: calc(100% - 40px);
  /* Añade margen a los lados de la Navbar */
  background: rgba(0, 0, 0, 0.7);
  /* Fondo semitransparente para la Navbar */
  padding: 10px;
  z-index: 1;
  /* Asegura que la Navbar esté por encima del resto del contenido */
  margin: 0 20px;
  /* Margen de 20px a los lados */
}

.container {
  padding: 50px;
  /* Aumenta el padding para hacer el contenedor más grande */
  max-width: 1200px;
  /* Aumenta el ancho máximo del contenedor */
  margin: 20px;
  /* Añade un margen alrededor del contenedor */
  text-align: center;
  background: rgba(0, 0, 0, 0.6);
  /* Fondo semitransparente para mejorar la legibilidad del texto */
  border-radius: 10px;
  min-height: 60vh;
  /* Asegura que el contenedor ocupe una parte significativa de la pantalla */
}

.intro-text {
  font-size: 24px;
  /* Aumenta el tamaño del texto introductorio */
  margin-bottom: 30px;
  /* Aumenta el margen inferior */
}

.features-list {
  list-style: none;
  padding: 0;
}

.features-list li {
  font-size: 20px;
  /* Aumenta el tamaño del texto de la lista */
  margin: 20px 0;
  /* Aumenta el margen entre los elementos de la lista */
}
</style>
