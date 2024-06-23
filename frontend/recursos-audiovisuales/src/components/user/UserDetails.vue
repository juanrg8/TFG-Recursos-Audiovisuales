<template>
  <Navbar style="z-index: 9" />
  <section class="vh-100 containerDetail" style="background-color: #f4f5f7">
    <div class="py-5 h-100">
      <div class="row d-flex justify-content-center align-items-center h-100 customDiv">
        <div class="col col-lg-6 mb-4 mb-lg-0">
          <div class="card mb-3" style="border-radius: 0.5rem">
            <div class="row g-0">
              <div class="col-md-4 gradient-custom text-center text-white" style="
                  border-top-left-radius: 0.5rem;
                  border-bottom-left-radius: 0.5rem;
                ">
                <img v-if="usuario.image != null && usuario.image != ''" :src="usuario.image" alt="Avatar"
                  class="img-fluid my-5 imgCustom" />
                <img v-else
                  src="https://static.vecteezy.com/system/resources/previews/005/544/718/non_2x/profile-icon-design-free-vector.jpg"
                  alt="Avatar" class="img-fluid my-5 imgCustom" />
                <h5>{{ usuario.nombre }}</h5>
                <router-link :to="{ name: 'UserForm' }" class="routerLinkCustom">Editar <i
                    class="bi bi-pencil-square"></i></router-link>
              </div>
              <div class="col-md-8">
                <div class="card-body p-4">
                  <h6>Información</h6>
                  <hr class="mt-0 mb-4" />
                  <div class="row pt-1" style="width: 300px">
                    <div class="col-6 mb-3 customCol">
                      <h6>Email</h6>
                      <p class="text-muted">{{ usuario.email }}</p>
                    </div>
                  </div>
                  <h6>Biografía</h6>
                  <hr class="mt-0 mb-4" />
                  <div class="row pt-1" style="width: 360px">
                    <div class="col-6 mb-3 customCol">
                      <p class="text-muted">{{ usuario.bio }}</p>
                    </div>
                  </div>
                  <button class="btn btn-primary mt-3" @click="toggleChangePasswordForm">
                    {{ showChangePasswordForm ? 'Cancelar' : 'Cambiar Contraseña' }}
                  </button>
                  <div v-if="showChangePasswordForm" class="mt-3">
                    <h6>Cambiar Contraseña</h6>
                    <form @submit.prevent="changePassword">
                      <div class="mb-3">
                        <label for="currentPassword" class="form-label">Contraseña Actual</label>
                        <div class="input-group">
                          <input :type="showCurrentPassword ? 'text' : 'password'" v-model="currentPassword"
                            class="form-control" id="currentPassword" required>
                          <button type="button" class="btn btn-outline-secondary" @click="toggleShowCurrentPassword">
                            {{ showCurrentPassword ? 'Ocultar' : 'Mostrar' }}
                          </button>
                        </div>
                      </div>
                      <div class="mb-3">
                        <label for="newPassword" class="form-label">Nueva Contraseña</label>
                        <div class="input-group">
                          <input :type="showNewPassword ? 'text' : 'password'" v-model="newPassword"
                            class="form-control" id="newPassword" required>
                          <button type="button" class="btn btn-outline-secondary" @click="toggleShowNewPassword">
                            {{ showNewPassword ? 'Ocultar' : 'Mostrar' }}
                          </button>
                        </div>
                      </div>
                      <div class="mb-3">
                        <label for="confirmNewPassword" class="form-label">Confirmar Nueva Contraseña</label>
                        <div class="input-group">
                          <input :type="showConfirmNewPassword ? 'text' : 'password'" v-model="confirmNewPassword"
                            class="form-control" id="confirmNewPassword" required>
                        </div>
                      </div>
                      <button type="submit" class="btn btn-success">Guardar Cambios</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
  name: "UserDetails",
  components: {
    Navbar,
  },
  data() {
    return {
      isAuthenticated: false,
      usuario: {},
      showChangePasswordForm: false,
      currentPassword: "",
      newPassword: "",
      confirmNewPassword: "",
      showCurrentPassword: false,
      showNewPassword: false,
      showConfirmNewPassword: false,
    };
  },
  async mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      this.usuario = this.$cookies.get("user");
      await this.getUsuario();
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
    async getUsuario() {
      await fetch(
        `http://localhost:8082/user/finduser/${this.usuario.username}`
      )
        .then((response) => response.json())
        .then(async (data) => {
          this.$cookies.remove("user");
          this.$cookies.set("user", JSON.stringify(data.usuario));
          this.usuario = data.usuario;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    toggleChangePasswordForm() {
      this.showChangePasswordForm = !this.showChangePasswordForm;
    },
    toggleShowCurrentPassword() {
      this.showCurrentPassword = !this.showCurrentPassword;
    },
    toggleShowNewPassword() {
      this.showNewPassword = !this.showNewPassword;
      this.showConfirmNewPassword = this.showNewPassword;
    },
    toggleShowConfirmNewPassword() {
      this.showConfirmNewPassword = !this.showConfirmNewPassword;
      this.showNewPassword = this.showConfirmNewPassword;
    },
    async changePassword() {
      if (this.newPassword !== this.confirmNewPassword) {
        alert("Las nuevas contraseñas no coinciden");
        return;
      }

      let request = {
        username: this.usuario.username,
        currentPassword: this.currentPassword,
        newPassword: this.newPassword,
      };

      try {
        await fetch("http://localhost:8082/user/changePassword", {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(request),
        });

        alert("Contraseña cambiada con éxito");
        this.showChangePasswordForm = false;
        this.currentPassword = "";
        this.newPassword = "";
        this.confirmNewPassword = "";
      } catch (error) {
        console.error("Error:", error);
        alert("Error al cambiar la contraseña");
      }
    }
  }
};
</script>

<style scoped>
.containerDetail {
  display: flex;
  align-items: center;
  justify-content: center;
}

.customCol {
  width: 100%;
}

.routerLinkCustom {
  text-decoration: none;
  color: white;
  font-weight: bolder;
}

.vh-90 {
  height: 90.1vh !important;
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

@media (max-width: 1400px) {
  .customDiv {
    margin-left: 0px !important;
  }
}

@media (max-width: 1000px) {
  .customDiv {
    width: 400px !important;
  }
}

@media (max-width: 770px) {
  .customHeight {
    height: 650px !important;
  }
}

.customDiv {
  width: 1200px;
  margin-right: 0px !important;
}

.imgCustom {
  width: 150px;
  border-radius: 20px;
}

.my-5 {
  margin-bottom: 2rem !important;
}

.gradient-custom {
  background: #f6d365;

  background: linear-gradient(90deg,
      rgba(0, 212, 255, 1) 0%,
      rgba(27, 27, 204, 1) 100%,
      rgba(2, 0, 36, 1) 100%);
  background: -webkit-linear-gradient(to right bottom,
      rgba(0, 212, 255, 1),
      rgba(27, 27, 204, 1));
}
</style>
