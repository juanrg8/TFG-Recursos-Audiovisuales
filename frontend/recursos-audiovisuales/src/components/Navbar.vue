<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <nav>
    <div class="logo" @click="redirectToHome">
      <img src="/images/logo.png" alt="App Logo" />
    </div>
    <ul>
      <li><router-link to="/albums">Álbumes</router-link></li>
      <li v-if="isAuthenticated"><router-link to="/useralbums">Mis álbumes</router-link></li>
      <li v-if="!isAuthenticated"><button @click="navigateTo('/login')"><span class="text">Inicia
            sesión/Regístrate</span></button></li>
      <li v-if="isAuthenticated"><router-link
          :to="{ name: 'UserDetails', params: { userName: this.$cookies.get('user').username } }">{{
      nombreUsuario }}</router-link></li>
      <li v-if="isAuthenticated"><a href="#" @click="logout">Logout</a></li>
    </ul>
  </nav>
</template>

<script>

export default {
  data() {
    return {
      isAuthenticated: false,
      nombreUsuario: ''
    };
  },
  mounted() {
    if (this.isAuthenticated) {
      let userInfo = this.$cookies.get('user');
      this.nombreUsuario = userInfo.nombre
    }

  },
  created() {
    this.checkAuthentication();
  },
  methods: {
    checkAuthentication() {
      const token = localStorage.getItem('token');
      this.isAuthenticated = !!token;
    },
    logout() {
      localStorage.removeItem('token');
      this.$cookies.remove('user')
      this.isAuthenticated = false;
      this.$router.push('/'); // Redirige a la página principal
    },
    navigateTo(route) {
      this.$router.push(route);
    },
    redirectToHome() {
      this.$router.push('/'); // Redirige a la página principal
    }
  },
  watch: {
    '$route'() {
      this.checkAuthentication(); // Vuelve a comprobar la autenticación cuando la ruta cambie
    },
  },
};
</script>

<style scoped>
nav {
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
  border-radius: 15px;
  /* Bordes redondeados */

  font-size: 28px;
}

nav .logo img {
  height: 100px;
  /* Ajusta el tamaño del logo según sea necesario */
}

nav ul {
  list-style: none;
  display: flex;
  gap: 1em;
  margin: 0;
  padding: 0;
}

nav ul li {
  display: inline;
}

nav ul li a {
  color: white;
  /* Cambia el color del texto a blanco */
  text-decoration: none;
  /* Elimina el subrayado del enlace */
}

nav ul li a:hover {
  color: #ddd;
  /* Color del texto al pasar el mouse */
}

nav ul li a:visited {
  color: white;
  /* Mantén el color blanco para los enlaces visitados */
}

nav ul li a:active {
  color: #ddd;
  /* Color del texto cuando el enlace está activo */
}

button {
  align-items: center;
  background-image: linear-gradient(144deg, #AF40FF, #5B42F3 50%, #00DDEB);
  border: 0;
  border-radius: 8px;
  box-shadow: rgba(151, 65, 252, 0.2) 0 15px 30px -5px;
  box-sizing: border-box;
  color: #FFFFFF;
  display: flex;
  font-family: Phantomsans, sans-serif;
  font-size: 18px;
  justify-content: center;
  line-height: 1em;
  max-width: 100%;
  min-width: 140px;
  padding: 3px;
  text-decoration: none;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  white-space: nowrap;
  cursor: pointer;
  transition: all .3s;
}

button:active,
button:hover {
  outline: 0;
}

button span {
  background-color: rgb(5, 6, 45);
  padding: 6px 14px;
  border-radius: 6px;
  width: 100%;
  height: 100%;
  transition: 300ms;
}

button:hover span {
  background: none;
}

button:active {
  transform: scale(0.9);
}
</style>
