<template>
  <Navbar style="z-index: 9" />

  <div>
    <div>
      <!-- Contenedor del Titulo -->
      <div class="title_container">
        <!-- Titulo -->
        <div class="title">
          <h1>Mis Albums</h1>
        </div>
      </div>

      <!-- Contenedor de Barra de busqueda -->
      <div class="search_bar_container">
        <!-- Barra de búsqueda -->
        <div class="search-bar">
          <select
            @change="filtrarEstado"
            v-model="albumStatus"
            class="form-select form-select-lg mb-3 filter_select paddingTopCustom"
            aria-label="Large select example"
          >
            <option value="All" selected>Todos</option>
            <option value="PENDING">Pendiente</option>
            <option value="CONSUMED">Escuchado</option>
          </select>
        </div>
      </div>
    </div>
  </div>

  <!-- Contenedor Albums -->
  <div>
    <div v-if="albums.length > 0" class="albums-container">
      <div class="album-card" v-for="album in albums" :key="album.id">
        <AlbumCard
          :album="album"
          @deleteEvent="handleDeleteEvent"
          @updateView="handleDeleteEvent"
        />
      </div>
    </div>
    <!-- Contenedor de No Albums -->
    <div v-else class="no_albums-container">
      <!-- No Albums -->
      <div class="no-albums">
        <p class="subtitle">No se encontraron álbumes.</p>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import AlbumCard from "./AlbumCard.vue";

export default {
  components: {
    Navbar,
    AlbumCard,
  },
  data() {
    return {
      albums: [],
      accessToken: "",
      clientId: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
      clientSecret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
      albumStatus: "All",
      userAlbums: [],
      albumsCopy: [],
      isAuthenticated: false,
    };
  },
  async mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      await this.getToken();
    } else {
      this.redirectToHome();
    }
  },
  methods: {
    loadUserAlbums() {
      let nombre = this.$cookies.get("user").username;
      fetch(`http://localhost:8082/useralbum/findall/${nombre}`)
        .then((response) => response.json())
        .then(async (data) => {
          this.userAlbums = data;
          if (data.length > 0) {
            await this.getUserAlbums(data);
            this.albumsCopy = this.albums;
          } else {
            this.albums = [];
            this.albumsCopy = this.albums;
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    async handleDeleteEvent() {
      this.albumStatus = "All";
      this.albums = [];
      this.albumsCopy = [];
      await this.getToken();
    },
    async getToken() {
      let authParams = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `grant_type=client_credentials&client_id=${this.clientId}&client_secret=${this.clientSecret}`,
      };

      fetch("https://accounts.spotify.com/api/token", authParams)
        .then((response) => response.json())
        .then((data) => {
          this.accessToken = data.access_token;
          this.loadUserAlbums();
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    async getUserAlbums(data) {
      data.forEach(async (album) => {
        if (album.spotifyId != null) {
          let albumspotify = await this.getSpotifyAlbums(album.spotifyId);
          if (albumspotify != undefined && albumspotify != null) {
            this.albums.push(albumspotify);
          }
        }
      });
    },
    async getSpotifyAlbums(id) {
      try {
        let artistParams = {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + this.accessToken,
          },
        };

        const response = await fetch(
          `https://api.spotify.com/v1/albums/${id}`,
          artistParams
        );
        const data = await response.json();
        let album = {
          images: data.images,
          artist: data.artists[0].name,
          name: data.name,
          id: data.id,
          miAlbum: true,
        };
        return album;
      } catch (error) {
        console.error("Error al buscar albumes:", error);
      }
    },
    filtrarEstado() {
      if (this.albumStatus == "All") {
        this.albums = this.albumsCopy;
      } else {
        this.albums = [];
        let filterUserAlbums = this.userAlbums.filter(
          (album) => album.status == this.albumStatus
        );
        this.getUserAlbums(filterUserAlbums);
      }
    },
    checkAuthentication() {
      const token = localStorage.getItem("token");
      this.isAuthenticated = !!token;
    },
    redirectToHome() {
      this.$router.push("/"); // Redirige a la página principal
    },
  },
};
</script>

<style scoped>
@media (max-width: 850px) {
  .filter_select {
    width: 400px !important;
  }
}

@media (max-width: 400px) {
  .albums-container {
    margin-left: 20px !important;
  }

  .title_container {
    height: 40vh !important;
  }

  .filter_select {
    width: 300px !important;
  }
}

.search_bar_container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.search-bar {
  display: flex;
  align-items: center;
  justify-content: center;
}

.no_albums-container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.paddingTopCustom {
  margin-top: 0px !important;
}

.no-albums {
  display: flex;
  align-items: center;
  background-color: rgb(221 221 221);
  border-radius: 5px;
}

.subtitle {
  padding: 10px 10px 10px 10px;
  text-align: center;
  margin-bottom: 0px;
  width: 900px;
  font-weight: 500;
  font-size: 20px;
}

.albums-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  /* Distribuye las tarjetas de álbumes en 4 columnas */
  gap: 20px;
  /* Espacio entre las tarjetas de álbumes */
  margin-top: 20px;
  margin-right: 60px;
  margin-left: 60px;
  /* Ajusta el margen superior */
}

.album-card {
  background: rgb(255, 255, 255);
  /* Fondo semitransparente para mejorar la legibilidad */
  background-size: contain;
  padding: 10px;
  border-radius: 10px;
}

.title_container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 40vh; /* Esto es opcional, establece la altura del contenedor */
}

.filter_container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.filter_select {
  width: 800px;
}
</style>
