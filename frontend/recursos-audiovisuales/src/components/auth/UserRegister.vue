<template>
  <div class="background-image">
    <Navbar class="navbar" />
    <div class="main-content">
      <div class="main">
        <div class="register">
          <form class="form" @submit.prevent="handleRegister">
            <label>Regístrate</label>
            <input class="input" type="username" v-model="username" placeholder="Username" required />
            <input class="input" type="email" v-model="email" placeholder="Email" required />
            <input class="input" type="password" v-model="password" placeholder="Password" required />
            <input class="input" type="name" v-model="name" placeholder="Name" required />
            <input class="input" type="bio" v-model="bio" placeholder="Bio" />
            <button type="submit">Register</button>
            <hover-button>
              <div>
                <span>
                  <p>Ya está registrado?</p>
                </span>
              </div>
              <div @click="$router.push('/login')">
                <span>
                  <p>Inicie sesión</p>
                </span>
              </div>
            </hover-button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from '../../services/authService';
import Navbar from '@/components/Navbar.vue'; // Importa el componente Navbar

export default {
  name: 'UserRegister',
  components: {
    Navbar, // Registra el componente Navbar para usarlo en esta página
  },
  data() {
    return {
      username: '',
      password: '',
      email: '',
      name: '',
      bio: '',
    };
  },
  methods: {
    async handleRegister() {
      try {
        const response = await register({
          username: this.username,
          password: this.password,
          email: this.email,
          name: this.name,
          bio: this.bio,
        });

        alert('Register successful!');
        this.$router.push('/');
        console.log(response.data);
      } catch (error) {
        console.error(error);
        let errorMessage = 'Register failed';
        if (error.response && error.response.data) {
          errorMessage += ': ' + (error.response.data.message || JSON.stringify(error.response.data));
        } else {
          errorMessage += ': ' + error.message;
        }
        alert(errorMessage);
      }
    },
  },
};
</script>

<style scoped>
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
  border-radius: 15px;
  /* Bordes redondeados */

  font-size: 28px;
}

.main-content {
  background: rgba(0, 0, 0, 0);
}

.main {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #240046;
  padding: 20px;
  border-radius: 12px;
}

.register {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

label {
  color: #ffffff;
  font-size: 2rem;
  font-weight: bold;
  cursor: pointer;
  transition: .5s ease-in-out;
  text-align: center;
}

.input {
  width: 100%;
  height: 40px;
  background: #e0dede;
  padding: 4px;
  border: none;
  outline: none;
  border-radius: 4px;
}

/*Button*/
.form button {
  width: 100%;
  height: 40px;
  color: #fff;
  background: rgb(87, 59, 138);
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: .2s ease-in;
}

.form button:hover {
  background-color: rgb(109, 68, 184);
}

p {
  margin-top: 10px;
  color: #fff;
}

hover-button {
  outline: 0;
  border: 0;
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 200px;
  height: 40px;
  border-radius: 0.5em;
  box-shadow: 0 0.625em 1em 0 rgba(87, 59, 138, 0.35);
  overflow: hidden;
}

hover-button div {
  transform: translateY(0px);
  width: 100%;
}

hover-button,
hover-button div {
  transition: 0.6s cubic-bezier(.16, 1, .3, 1);
}

hover-button div span {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  padding: 0.75em 1.125em;
}

hover-button div:nth-child(1) {
  background-color: rgb(87, 59, 138);
}

hover-button div:nth-child(2) {
  background-color: rgb(109, 68, 184);
}

hover-button:hover {
  box-shadow: 0 0.625em 1em 0 rgba(109, 68, 184, 0.35);
}

hover-button:hover div {
  transform: translateY(-50px);
}

hover-button p {
  font-size: 17px;
  font-weight: bold;
  color: #ffffff;
}

hover-button:active {
  transform: scale(0.95);
}
</style>
