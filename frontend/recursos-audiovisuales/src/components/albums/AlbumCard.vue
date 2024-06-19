<template>
  <div>
    <div class="card">
      <img
        :src="album.images[0].url"
        class="card-img-top"
        alt="Fissure in Sandstone"
      />
      <div class="card-body">
        <h5
          class="card-title"
          v-if="album.miAlbum == undefined && album.miAlbum == null"
        >
          {{ album.name }}
        </h5>
        <h5
          class="card-title"
          v-if="album.miAlbum != undefined && album.miAlbum != null"
        >
          <router-link
            :to="{ name: 'AlbumDetails', params: { spotifyId: album.id } }"
            class="router-link-custom"
            >{{ album.name }}</router-link
          >
        </h5>
        <!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p> -->
        <div class="card-buttons">
          <a
            v-if="
              this.isAuthenticated &&
              album.miAlbum != undefined &&
              album.miAlbum != null
            "
            @click="addAlbumPendingStatus(album.name)"
            class="btn btn-primary me-2"
            >Pendiente</a
          >
          <a
            v-if="
              this.isAuthenticated &&
              album.miAlbum == undefined &&
              album.miAlbum == null
            "
            @click="addAlbumPending(album.id, album.name)"
            class="btn btn-primary me-2"
            >Pendiente</a
          >
          <a
            v-if="
              this.isAuthenticated &&
              album.miAlbum != undefined &&
              album.miAlbum != null
            "
            class="btn btn-success me-2"
            ><router-link
              :to="{ name: 'AlbumReview', params: { spotifyId: album.id } }"
              class="router-link-custom-white"
              >Escuchado</router-link
            ></a
          >
          <a
            v-if="
              this.isAuthenticated &&
              album.miAlbum != undefined &&
              album.miAlbum != null
            "
            @click="deleteAlbum(album.name)"
            class="btn btn-danger"
            >Eliminar</a
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isAuthenticated: false,
    };
  },
  props: {
    album: {
      type: Object,
      required: true,
    },
  },
  mounted() {
    this.checkAuthentication();
  },
  methods: {
    addAlbumPending(id, name) {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        spotifyId: id,
        status: "PENDING",
        tituloAlbum: name,
      };
      fetch("http://localhost:8082/useralbum/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
          // Almacena el token de acceso en la variable accessToken
          console.log(data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    addAlbumPendingStatus(title) {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        status: "PENDING",
        tituloAlbum: title,
      };
      fetch("http://localhost:8082/useralbum/movetostatus", {
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
          this.$emit("updateView", data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    deleteAlbum(title) {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        tituloAlbum: title,
      };
      fetch("http://localhost:8082/useralbum/delete", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
          // Almacena el token de acceso en la variable accessToken
          this.$emit("deleteEvent", data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    checkAuthentication() {
      const token = localStorage.getItem("token");
      this.isAuthenticated = !!token;
    },
  },
};
</script>

<style scoped>
@media (max-width: 1400px) {
  .card {
    height: 530px !important;
  }
}

.card {
  display: flex !important;
  flex-direction: column !important;
  height: 600px;
}
.card-body {
  display: flex !important;
  flex-direction: column !important;
  justify-content: space-between !important;
  flex-grow: 1;
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.card-buttons {
  margin-top: auto !important;
}

/* Estilos adicionales específicos para el componente */
.custom-card {
  border-color: white;
}

.router-link-custom {
  color: rgb(0, 0, 0);

  text-decoration: none;
}

.router-link-custom:hover,
.router-link-custom:focus {
  text-decoration: none;
  color: rgb(56, 90, 194);
}

.router-link-custom-white {
  color: rgb(255, 255, 255);

  text-decoration: none;
}
</style>
