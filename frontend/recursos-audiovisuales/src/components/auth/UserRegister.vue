<template>
  <Navbar style="z-index: 9" />
  <div class="customFormContainer">
    <form class="form" @submit.prevent="handleRegister">
      <p class="title">Registro</p>
      <p class="message">Registrate y obten todo el acceso a la aplicación.</p>
      <label>
        <input
          v-model="name"
          required=""
          placeholder=""
          type="text"
          class="input"
        />
        <span>Nombre</span>
      </label>

      <label>
        <input
          v-model="email"
          required=""
          placeholder=""
          type="email"
          class="input"
        />
        <span>Email</span>
      </label>

      <label>
        <input
          v-model="username"
          required=""
          placeholder=""
          type="text"
          class="input"
        />
        <span>Nombre de usuario</span>
      </label>

      <label>
        <input
          v-model="password"
          required=""
          placeholder=""
          type="password"
          class="input"
        />
        <span>Contraseña</span>
      </label>

      <label>
        <input
          v-model="bio"
          required=""
          placeholder=""
          type="text"
          class="input"
        />
        <span>Biografia</span>
      </label>
      <button class="submit">Registrarse</button>
      <p class="signin">
        ¿Tienes ya una cuenta?
        <a><router-link to="/login">Inicia Sesión</router-link></a>
      </p>
    </form>
  </div>
</template>

<script>
import { register } from "../../services/authService";
import Navbar from "@/components/Navbar.vue"; // Importa el componente Navbar

export default {
  name: "UserRegister",
  components: {
    Navbar, // Registra el componente Navbar para usarlo en esta página
  },
  data() {
    return {
      username: "",
      password: "",
      email: "",
      name: "",
      bio: "",
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

        alert("Register successful!");
        this.$router.push("/");
        console.log(response.data);
      } catch (error) {
        console.error(error);
        let errorMessage = "Register failed";
        if (error.response && error.response.data) {
          errorMessage +=
            ": " +
            (error.response.data.message ||
              JSON.stringify(error.response.data));
        } else {
          errorMessage += ": " + error.message;
        }
        alert(errorMessage);
      }
    },
  },
};
</script>

<style scoped>
@media (max-width: 1400px) {
  .form {
    max-width: 100%; /* Esto hace que el formulario ocupe todo el ancho disponible */
    padding: 20px; /* Ajusta el padding si es necesario */
    margin: 0 20px; /* Añade margen para espaciar el formulario del borde */
    height: auto;
  }
}

.customFormContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e1e1e1;
  height: 100vh;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 600px;
  width: 600px;
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
  position: relative;
  -webkit-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  -moz-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.title {
  font-size: 28px;
  color: royalblue;
  font-weight: 600;
  letter-spacing: -1px;
  position: relative;
  display: flex;
  align-items: center;
  padding-left: 30px;
}

.title::before,
.title::after {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  border-radius: 50%;
  left: 0px;
  background-color: royalblue;
}

.title::before {
  width: 18px;
  height: 18px;
  background-color: royalblue;
}

.title::after {
  width: 18px;
  height: 18px;
  animation: pulse 1s linear infinite;
}

.message,
.signin {
  color: rgba(88, 87, 87, 0.822);
  font-size: 14px;
}

.signin {
  text-align: center;
}

.signin a {
  color: royalblue;
}

.signin a:hover {
  text-decoration: underline royalblue;
}

.flex {
  display: flex;
  width: 100%;
  gap: 6px;
}

.form label {
  position: relative;
}

.form label .input {
  width: 100%;
  padding: 10px 10px 20px 10px;
  outline: 0;
  border: 1px solid rgba(105, 105, 105, 0.397);
  border-radius: 10px;
}

.form label .input + span {
  position: absolute;
  left: 10px;
  top: 15px;
  color: grey;
  font-size: 0.9em;
  cursor: text;
  transition: 0.3s ease;
}

.form label .input:placeholder-shown + span {
  top: 15px;
  font-size: 0.9em;
}

.form label .input:focus + span,
.form label .input:valid + span {
  top: 30px;
  font-size: 0.7em;
  font-weight: 600;
}

.form label .input:valid + span {
  color: green;
}

.submit {
  border: none;
  outline: none;
  background-color: royalblue;
  padding: 10px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: 0.3s ease;
}

.submit:hover {
  background-color: rgb(56, 90, 194);
}

@keyframes pulse {
  from {
    transform: scale(0.9);
    opacity: 1;
  }

  to {
    transform: scale(1.8);
    opacity: 0;
  }
}
</style>
