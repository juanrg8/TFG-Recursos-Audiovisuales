<template>
  <Navbar style="z-index: 9" />
  <div class="customFormContainer">
    <div class="responsiveForm">
      <form class="form" @submit.prevent="updateUser">
        <p class="title">Editar</p>
        <label>
          <input
            v-model="nombre"
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
            v-model="bio"
            required=""
            placeholder=""
            type="text"
            class="input"
          />
          <span>Biografia</span>
        </label>
        <label>
          <input
            v-model="imagen"
            required=""
            placeholder=""
            type="text"
            class="input"
          />
          <span>Imagen</span>
        </label>
        <button class="submit">Editar</button>
      </form>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "UserForm",
  components: {
    Navbar,
  },
  data() {
    return {
      isAuthenticated: false,
      usuario: {},
      nombre: "",
      email: "",
      bio: "",
      imagen: "",
    };
  },
  mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      this.usuario = this.$cookies.get("user");
      this.nombre = this.usuario.nombre;
      this.bio = this.usuario.bio;
      this.email = this.usuario.email;
      this.imagen = this.usuario.image;
    } else {
      this.redirectToHome();
    }
  },
  methods: {
    checkAuthentication() {
      const token = localStorage.getItem("token");
      this.isAuthenticated = !!token;
    },
    redirectToHome() {
      this.$router.push("/"); // Redirige a la página principal
    },
    updateUser() {
      let request = {
        username: this.usuario.username,
        email: this.email,
        bio: this.bio,
        nombre: this.nombre,
        image: this.imagen,
      };
      fetch("http://localhost:8082/user/updateuser", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
          // Almacena el token de acceso en la variable accessToken
          console.log(data);
          this.$router.push({
            name: "UserDetails",
          });
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
  },
};
</script>

<style>
.customFormContainer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.responsiveForm {
  display: flex;
  align-items: center;
  justify-content: center;
}

@media (max-width: 1400px) {
  .form {
    max-width: 100%; /* Esto hace que el formulario ocupe todo el ancho disponible */
    padding: 20px; /* Ajusta el padding si es necesario */
    margin: 0 20px; /* Añade margen para espaciar el formulario del borde */
    height: auto;
  }
}

.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 800px;
  width: 500px;
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
  position: relative;
  -webkit-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  -moz-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
  box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
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

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
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
