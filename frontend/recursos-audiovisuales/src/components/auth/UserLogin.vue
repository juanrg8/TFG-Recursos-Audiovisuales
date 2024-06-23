<template>
  <Navbar style="z-index: 9" />
  <div>
    <div class="customFormContainer">
      <form class="form_main" action="" @submit.prevent="handleLogin">
        <p class="heading">¡Bienvenido!</p>
        <div class="inputContainer">
          <svg viewBox="0 0 16 16" fill="#2e2e2e" height="16" width="16" xmlns="http://www.w3.org/2000/svg"
            class="inputIcon">
            <path
              d="M13.106 7.222c0-2.967-2.249-5.032-5.482-5.032-3.35 0-5.646 2.318-5.646 5.702 0 3.493 2.235 5.708 5.762 5.708.862 0 1.689-.123 2.304-.335v-.862c-.43.199-1.354.328-2.29.328-2.926 0-4.813-1.88-4.813-4.798 0-2.844 1.921-4.881 4.594-4.881 2.735 0 4.608 1.688 4.608 4.156 0 1.682-.554 2.769-1.416 2.769-.492 0-.772-.28-.772-.76V5.206H8.923v.834h-.11c-.266-.595-.881-.964-1.6-.964-1.4 0-2.378 1.162-2.378 2.823 0 1.737.957 2.906 2.379 2.906.8 0 1.415-.39 1.709-1.087h.11c.081.67.703 1.148 1.503 1.148 1.572 0 2.57-1.415 2.57-3.643zm-7.177.704c0-1.197.54-1.907 1.456-1.907.93 0 1.524.738 1.524 1.907S8.308 9.84 7.371 9.84c-.895 0-1.442-.725-1.442-1.914z">
            </path>
          </svg>
          <input v-model="username" placeholder="Nombre de usuario" id="username" class="inputField" type="text" />
        </div>

        <div class="inputContainer">
          <svg viewBox="0 0 16 16" fill="#2e2e2e" height="16" width="16" xmlns="http://www.w3.org/2000/svg"
            class="inputIcon">
            <path
              d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z">
            </path>
          </svg>
          <input v-model="password" placeholder="Contraseña" id="password" class="inputField"
            :type="showPassword ? 'text' : 'password'" />
          <button type="button" @click="togglePasswordVisibility" class="togglePasswordButton">
            {{ showPassword ? 'Ocultar' : 'Mostrar' }}
          </button>
        </div>

        <button id="button">Iniciar Sesión</button>
        <div class="signupContainer">
          <p>¿No estas registrado?</p>
          <a @click="$router.push('/register')">Registrate</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { login } from "../../services/authService";
import Navbar from "@/components/Navbar.vue"; // Importa el componente Navbar

export default {
  name: "UserLogin",
  components: {
    Navbar, // Registra el componente Navbar en el componente UserLogin
  },
  data() {
    return {
      username: "",
      password: "",
      showPassword: false,
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
          alert("Login successful!");
          localStorage.setItem("token", response.data.token);
          await this.buscarUsuario(response.data.username);
          console.table(response);
          this.$router.push("/"); // Redirige a la página principal
        } else {
          throw new Error("Invalid response from server");
        }
      } catch (error) {
        console.error(error);
        let errorMessage = "Login failed";
        if (error.response && error.response.data) {
          errorMessage +=
            ": " + (error.response.data.message || error.response.data);
        } else {
          errorMessage += ": " + error.message;
        }
        alert(errorMessage);
      }
    },
    async buscarUsuario(username) {
      await fetch(`http://localhost:8082/user/finduser/${username}`)
        .then((response) => response.json())
        .then(async (data) => {
          this.$cookies.set("user", JSON.stringify(data.usuario));
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
  },
};
</script>

<style scoped>
.customFormContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e1e1e1;
  height: 100vh;
}

.form_main {
  width: 480px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgb(255, 255, 255);
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
  -webkit-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  -moz-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
}

.heading {
  font-size: 2.5em;
  color: #2e2e2e;
  font-weight: 700;
  margin: 15px 0 30px 0;
}

.inputContainer {
  width: 100%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.inputIcon {
  position: absolute;
  left: 10px;
}

.inputField {
  width: 100%;
  height: 40px;
  background-color: transparent;
  border: none;
  border-bottom: 2px solid rgb(173, 173, 173);
  border-radius: 30px;
  margin: 10px 0;
  color: black;
  font-size: 0.8em;
  font-weight: 500;
  box-sizing: border-box;
  padding-left: 30px;
}

.inputField:hover {
  border: 2px solid hsl(210deg 100% 44%);
  box-shadow: 0px 0px 20px -17px;
}

.inputField:active {
  transform: scale(0.95);
}

.inputField:focus {
  border: 2px solid hsl(210deg 100% 44%);
}

.inputField:focus {
  outline: none;
  border-bottom: 2px solid hsl(210deg 100% 44%);
}

.inputField::placeholder {
  color: rgb(80, 80, 80);
  font-size: 1em;
  font-weight: 500;
}

#button {
  position: relative;
  width: 100%;
  border: 2px solid hsl(210deg 100% 44%);
  background-color: hsl(210deg 100% 44%);
  height: 40px;
  color: white;
  font-size: 0.8em;
  font-weight: 500;
  letter-spacing: 1px;
  border-radius: 30px;
  margin: 10px;
  cursor: pointer;
  overflow: hidden;
}

#button::after {
  content: "";
  position: absolute;
  background-color: rgba(255, 255, 255, 0.253);
  height: 100%;
  width: 150px;
  top: 0;
  left: -200px;
  border-bottom-right-radius: 100px;
  border-top-left-radius: 100px;
  filter: blur(10px);
  transition-duration: 0.5s;
}

#button:hover::after {
  transform: translateX(600px);
  transition-duration: 0.5s;
}

.signupContainer {
  margin: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px;
}

.signupContainer p {
  font-size: 0.9em;
  font-weight: 500;
  color: black;
}

.signupContainer a {
  font-size: 0.7em;
  font-weight: 500;
  background-color: #2e2e2e;
  color: white !important;
  text-decoration: none !important;
  padding: 8px 15px;
  border-radius: 20px;
}

.signupContainer a:hover {
  cursor: pointer;
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.togglePasswordButton {
  position: absolute;
  right: 10px;
  background: none;
  border: none;
  cursor: pointer;
  color: #2e2e2e;
}
</style>