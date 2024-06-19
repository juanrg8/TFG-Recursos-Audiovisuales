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
                <img v-if="this.usuario.image != null && this.usuario.image != ''" :src="this.usuario.image"
                  alt="Avatar" class="img-fluid my-5 imgCustom" />
                <img v-if="this.usuario.image == null || this.usuario.image == ''"
                  src="https://static.vecteezy.com/system/resources/previews/005/544/718/non_2x/profile-icon-design-free-vector.jpg"
                  alt="Avatar" class="img-fluid my-5 imgCustom" />
                <h5>{{ this.usuario.nombre }}</h5>
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
                      <p class="text-muted">{{ this.usuario.email }}</p>
                    </div>
                  </div>
                  <h6>Biografía</h6>
                  <hr class="mt-0 mb-4" />
                  <div class="row pt-1" style="width: 360px">
                    <div class="col-6 mb-3 customCol">
                      <p class="text-muted">{{ this.usuario.bio }}</p>
                    </div>
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
  },
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
