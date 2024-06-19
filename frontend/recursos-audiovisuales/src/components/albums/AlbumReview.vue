<template>
  <Navbar style="z-index: 9" />
  <!-- Container Principal -->
  <div class="containerCard">
    <div class="responsive">
      <div class="card">
        <span class="titulo">¡Dejanos tu opinión!</span>
        <form class="formulario" @submit.prevent="updateAlbumReview">
          <div class="ratingContainer">
            <span class="labbel">Puntuación</span>
          </div>
          <div class="rating">
            <input
              v-model="rating"
              value="5"
              name="rating"
              id="star5"
              type="radio"
            />
            <label for="star5"></label>
            <input
              v-model="rating"
              value="4"
              name="rating"
              id="star4"
              type="radio"
            />
            <label for="star4"></label>
            <input
              v-model="rating"
              value="3"
              name="rating"
              id="star3"
              type="radio"
            />
            <label for="star3"></label>
            <input
              v-model="rating"
              value="2"
              name="rating"
              id="star2"
              type="radio"
            />
            <label for="star2"></label>
            <input
              v-model="rating"
              value="1"
              name="rating"
              id="star1"
              type="radio"
            />
            <label for="star1"></label>
          </div>
          <div class="group">
            <textarea
              v-model="review"
              placeholder=""
              id="rese"
              name="rese"
              rows="5"
              required=""
            ></textarea>
            <label for="comment">Reseña</label>
          </div>
          <button type="submit">Enviar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import $ from "jquery";

export default {
  components: {
    Navbar,
  },
  name: "AlbumReview",
  props: ["spotifyId"],
  data() {
    return {
      album: {},
      rating: null,
      review: "",
      accessToken: "",
      clientId: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
      clientSecret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
      userAlbumUser: {},
      isAuthenticated: false,
    };
  },
  async mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      await this.getToken();
      await this.loadUserAlbums();
      this.updateAlbumState();
      console.table(this.album);
      this.rating = this.userAlbumUser.rating;
      this.review = this.userAlbumUser.review;

      if (
        this.userAlbumUser.rating != null ||
        this.userAlbumUser.rating != undefined
      ) {
        $("#star" + this.userAlbumUser.rating).prop("checked", true);
      }
    } else {
      this.redirectToHome();
    }
  },
  computed: {
    id() {
      console.log(this.$route.params.spotifyId);
      return this.$route.params.spotifyId;
    },
  },
  methods: {
    async loadUserAlbums() {
      let nombre = this.$cookies.get("user").username;
      await fetch(`http://localhost:8082/useralbum/findall/${nombre}`)
        .then((response) => response.json())
        .then(async (data) => {
          let userAlbum = data.filter((a) => a.spotifyId == this.id)[0];
          this.userAlbumUser = userAlbum;
          let spotifyAlbum = await this.getSpotifyAlbums(userAlbum.spotifyId);
          if (spotifyAlbum != null && spotifyAlbum != undefined) {
            this.album = spotifyAlbum;
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    updateAlbumState() {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        status: "CONSUMED",
        tituloAlbum: this.album.name,
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
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    updateAlbumReview() {
      console.log(this.id);
      // Obtener los valores de los inputs
      console.log("Rating:", this.rating);
      console.log("Review:", this.review);

      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        tituloAlbum: this.album.name,
        review: this.review,
      };
      fetch("http://localhost:8082/useralbum/updatereview", {
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
          this.updateAlbumRating();
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    updateAlbumRating() {
      console.log(this.id);
      // Obtener los valores de los inputs
      console.log("Rating:", this.rating);
      console.log("Review:", this.review);

      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        tituloAlbum: this.album.name,
        rating: this.rating,
      };
      fetch("http://localhost:8082/useralbum/updaterating", {
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
            name: "AlbumDetails",
            params: { spotifyId: this.id },
          });
        })
        .catch((error) => {
          console.error("Error:", error);
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

        // Realiza la solicitud GET para buscar artistas en Spotify
        const response = await fetch(
          `https://api.spotify.com/v1/albums/${id}`,
          artistParams
        );
        const data = await response.json();
        let album = {
          name: data.name,
          id: data.id,
        };
        return album;
      } catch (error) {
        console.error("Error al buscar albumes:", error);
      }
    },
    async getToken() {
      let authParams = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `grant_type=client_credentials&client_id=${this.clientId}&client_secret=${this.clientSecret}`,
      };

      await fetch("https://accounts.spotify.com/api/token", authParams)
        .then((response) => response.json())
        .then((data) => {
          // Almacena el token de acceso en la variable accessToken
          this.accessToken = data.access_token;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
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
.ratingContainer {
  display: flex;
  justify-content: center;
}

.card {
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  width: 350px;
  display: flex;
  flex-direction: column;
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

.titulo {
  font-size: 24px;
  font-weight: 600;
  text-align: center;
}

.labbel {
  font-size: 18px;
  font-weight: 600;
  text-align: center;
}

.formulario {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}

.group {
  position: relative;
}

.formulario .group label {
  font-size: 14px;
  color: rgb(99, 102, 102);
  position: absolute;
  top: -10px;
  left: 10px;
  background-color: #fff;
  transition: all 0.3s ease;
}

.formulario .group input,
.formulario .group textarea {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
  outline: 0;
  width: 100%;
  background-color: transparent;
}

.formulario .group input:placeholder-shown + label,
.formulario .group textarea:placeholder-shown + label {
  top: 10px;
  background-color: transparent;
}

.formulario .group input:focus,
.formulario .group textarea:focus {
  border-color: #3366cc;
}

.formulario .group input:focus + label,
.formulario .group textarea:focus + label {
  top: -10px;
  left: 10px;
  background-color: #fff;
  color: #3366cc;
  font-weight: 600;
  font-size: 14px;
}

.formulario .group textarea {
  resize: none;
  height: 100px;
}

.formulario button {
  background-color: #3366cc;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.formulario button:hover {
  background-color: #27408b;
}

.rating {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  flex-direction: row-reverse;
}

.rating input {
  display: none;
}

.rating label {
  float: right;
  cursor: pointer;
  color: #ccc;
  transition: color 0.3s;
}

.rating label:before {
  content: "\2605";
  font-size: 30px;
}

.rating input:checked ~ label,
.rating label:hover,
.rating label:hover ~ label {
  color: hsl(210deg 100% 44%);
  transition: color 0.3s;
}

.containerCard {
  display: flex;
  justify-content: center;
  height: 100vh;
}

.responsive {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
