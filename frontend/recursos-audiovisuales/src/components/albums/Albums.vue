<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <Navbar style="z-index: 9" />
  <!-- Contenedor Principal -->
  <div id="container" class="containerPrin">
    <div>
      <!-- Contenedor del Titulo -->
      <div class="title_container">
        <!-- Titulo -->
        <div class="title">
          <h1>Busqueda de albums</h1>
        </div>
      </div>

      <!-- Contenedor de Barra de busqueda -->
      <div class="search_bar_container">
        <!-- Barra de búsqueda -->
        <div class="search-bar">
          <input
            class="input"
            name="text"
            type="text"
            v-model="searchTerm"
            placeholder="Buscar álbumes..."
          />
          <button class="button" @click="search">
            <span class="span">🔎</span>
          </button>
        </div>
      </div>

      <!-- Contenedor de No Albums -->
      <div v-if="albums.length === 0" class="no_albums-container">
        <!-- No Albums -->
        <div class="no-albums">
          <p class="subtitle">Busca algún álbum y añádelo a tu lista!</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Contenedor de Albums -->
  <div>
    <div v-if="albums.length > 0">
      <!-- Subtitulo Resultados -->
      <div class="resultados-container">
        <div class="resultados">
          <p class="resultados-text">Resultados</p>
        </div>
      </div>
      <div class="barra-container">
        <div class="barra">
          <hr style="width: 1700px" />
        </div>
      </div>

      <!-- Lista de albums -->
      <div class="albums-container">
        <div class="album-card" v-for="album in albums" :key="album.id">
          <AlbumCard :album="album" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
// Importa el componente Navbar
import Navbar from "@/components/Navbar.vue";
import AlbumCard from "@/components/albums/AlbumCard.vue";

import $ from "jquery";

export default {
  components: {
    Navbar,
    AlbumCard,
  },
  setup() {
    const searchTerm = ref(""); // Variable para almacenar el texto de búsqueda del usuario
    const albums = ref([]); // Array para almacenar los álbumes recuperados de Spotify
    const accessToken = ref(""); // Variable para almacenar el token de acceso
    const clientId = process.env.VUE_APP_SPOTIFY_CLIENT_ID;
    const clientSecret = process.env.VUE_APP_SPOTIFY_CLIENT_SECRET;

    // Realiza la solicitud para obtener el token de acceso cuando el componente se crea
    onMounted(() => {
      let authParams = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}`,
      };

      fetch("https://accounts.spotify.com/api/token", authParams)
        .then((response) => response.json())
        .then((data) => {
          // Almacena el token de acceso en la variable accessToken
          accessToken.value = data.access_token;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    });

    // Función asíncrona para buscar artistas en Spotify
    const search = async () => {
      try {
        let artistParams = {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + accessToken.value,
          },
        };

        // Realiza la solicitud GET para buscar artistas en Spotify
        const response = await fetch(
          `https://api.spotify.com/v1/search?q=${searchTerm.value}&type=artist`,
          artistParams
        );
        const data = await response.json();

        // Verifica si hay resultados de artistas
        if (data && data.artists && data.artists.items.length > 0) {
          const artistID = data.artists.items[0].id; // Obtiene el ID del primer artista encontrado
          console.log("Search Input:", searchTerm.value);
          console.log("Artist ID:", artistID);

          // Obtener los álbumes del artista
          const albumResponse = await fetch(
            `https://api.spotify.com/v1/artists/${artistID}/albums?include_groups=album&market=US&limit=50`,
            artistParams
          );
          const albumData = await albumResponse.json();
          albums.value = albumData.items; // Actualizar el valor de albums
          $("#container").addClass("gainHeight");
        } else {
          albums.value = []; // Reiniciar la lista de álbumes si no se encuentran resultados
          console.log("No se encontraron artistas.");
          $("#container").removeClass("gainHeight");
        }
      } catch (error) {
        console.error("Error al buscar artistas:", error);
        $("#container").removeClass("gainHeight");
      }
    };

    return {
      searchTerm,
      albums,
      accessToken,
      search,
    };
  },
};
</script>

<style scoped>
@media (max-width: 800px) {
  .input {
    width: 300px !important;
  }

  .subtitle {
    width: 500px !important;
  }
}

.gainHeight {
  height: 50vh !important;
}

.containerPrin {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.barra-container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
}

.barra {
  display: flex;
  align-items: center;
}

.resultados-container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.resultados {
  display: flex;
  align-items: center;
}

.resultados-text {
  font-size: 40px;
  font-weight: 400;
}

.nav {
  width: 100%;
  height: 90px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.no_albums-container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
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

.search_bar_container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.search-bar {
  display: flex;
  align-items: center;
}

.title_container {
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente si es necesario */
  height: 10vh; /* Esto es opcional, establece la altura del contenedor */
}

.title {
  display: flex;
  align-items: center;
}

.input {
  margin-right: 10px; /* Espacio entre el input y el botón */
}

.input {
  width: 800px;
  max-width: 1000px;
  height: 45px;
  padding: 12px;
  border-radius: 12px;
  border: 1.5px solid lightgrey;
  outline: none;
  transition: all 0.3s cubic-bezier(0.19, 1, 0.22, 1);
  box-shadow: 0px 0px 20px -18px;
}

.input:hover {
  border: 2px solid lightgrey;
  box-shadow: 0px 0px 20px -17px;
}

.input:active {
  transform: scale(0.95);
}

.input:focus {
  border: 2px solid grey;
}

.button {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #e8e8e8;
  background-color: #212121;
  width: 50px;
  height: 50px;
  font-size: 24px;
  text-transform: uppercase;
  border: 2px solid #212121;
  border-radius: 50%;
  transition: all 0.6s cubic-bezier(0.23, 1, 0.32, 1);
  box-shadow: 5px 5px 2px rgba(0, 0, 0, 0.15), 2px 2px 2px rgba(0, 0, 0, 0.1),
    -3px -3px 2px rgba(255, 255, 255, 0.05),
    -2px -2px 1px rgba(255, 255, 255, 0.05);
  overflow: hidden;
  cursor: pointer;
}

.button .span {
  position: relative;
  z-index: 2;
  transition: all 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}

.button::before {
  content: "";
  position: absolute;
  background-color: #e8e8e8;
  width: 150%;
  height: 150%;
  left: 0%;
  bottom: 0%;
  transform: translate(-100%, 100%) rotate(45deg);
  border-radius: 15px;
}

.button:hover::before {
  animation: shakeBack 0.6s forwards 0.6s;
}

.button:hover .span {
  scale: 1.2;
}

.button:hover {
  box-shadow: none;
}

.button:active {
  scale: 0.95;
}

@keyframes shakeBack {
  0% {
    transform: translate(-100%, 100%) rotate(45deg);
  }

  50% {
    transform: translate(15%, -15%) rotate(45deg);
  }

  100% {
    transform: translate(-10%, 10%) rotate(45deg);
  }
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
</style>
