<template>
  <div>
    <Navbar />
    <div class="main">
      <div class="login">
        <form class="form" @submit.prevent="handleLogin">
          <label>Inicia sesión</label>
          <input class="input" type="username" v-model="username" placeholder="Username" required />
          <input class="input" type="password" v-model="password" placeholder="Password" required />
          <button type="submit">Login</button>
          <hover-button>
            <div>
              <span>
                <p>No está registrado?</p>
              </span>
            </div>
            <div @click="$router.push('/register')">
              <span>
                <p>Regístrese</p>
              </span>
            </div>
          </hover-button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from '../../services/authService';
import Navbar from '@/components/Navbar.vue'; // Importa el componente Navbar

export default {
  name: 'UserLogin',
  components: {
    Navbar // Registra el componente Navbar en el componente UserLogin
  },
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await login({
          username: this.username,
          password: this.password,
        });

        if (response && response.data && response.data.token) {
          alert('Login successful!');
          localStorage.setItem('token', response.data.token);
          await this.buscarUsuario(response.data.username);
          console.table(response);
          this.$router.push('/'); // Redirige a la página principal
        } else {
          throw new Error('Invalid response from server');
        }
      } catch (error) {
        console.error(error);
        let errorMessage = 'Login failed';
        if (error.response && error.response.data) {
          errorMessage += ': ' + (error.response.data.message || error.response.data);
        } else {
          errorMessage += ': ' + error.message;
        }
        alert(errorMessage);
      }
    },
    async buscarUsuario(username) {
      await fetch(`http://localhost:8082/user/finduser/${username}`)
        .then(response => response.json())
        .then(async data => {
          this.$cookies.set('user', JSON.stringify(data.usuario));

        })
        .catch(error => {
          console.error('Error:', error);
        });
    }
  },
};
</script>

<style scoped>
.main {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #240046;
  padding: 20px;
  border-radius: 12px;
}

.login {
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
  background: #573b8a;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: .2s ease-in;
}

.form button:hover {
  background-color: #6d44b8;
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
  height: 25px;
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
