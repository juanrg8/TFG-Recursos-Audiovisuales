<template>
  <Navbar style="z-index: 9" />
  <!-- Contenedor Principal -->
  <div id="container" class="containerPrin">
    <div>
      <!-- Contenedor del Titulo -->
      <div class="title_container" id="titleContainer">
        <!-- Titulo -->
        <div class="title">
          <h1>Busca tus libros aquí</h1>
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
            placeholder="Buscar libros..."
          />
          <button class="button" @click="search">
            <span class="span">🔎</span>
          </button>
        </div>
      </div>

      <!-- Contenedor de No Albums -->
      <div v-if="books.length === 0" class="no_albums-container">
        <!-- No Albums -->
        <div class="no-albums">
          <p class="subtitle">Busca algún libro y añádelo a tu lista!</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Contenedor de Albums -->
  <div class="books-container">
    <div v-if="books.length > 0">
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
        <div class="album-card" v-for="book in books" :key="book.id">
          <BookCard :book="book" />
        </div>
      </div>
    </div>
  </div>

  <button
    v-if="showLoadMore"
    @click="loadMore"
    class="btn btn-primary buttonLoadMore"
  >
    Cargar más
  </button>
</template>

<script>
import axios from "axios";
import Navbar from "@/components/Navbar.vue";
import BookCard from "@/components/books/BookCard.vue";
import $ from "jquery";

export default {
  components: {
    Navbar,
    BookCard,
  },
  data() {
    return {
      searchTerm: "",
      books: [],
      startIndex: 0,
      showLoadMore: false,
      totalItems: 0,
    };
  },
  methods: {
    async search() {
      $("#titleContainer").addClass("title_containerCustom");
      if (this.searchTerm.trim() === "") return;

      this.startIndex = 0; // Resetear índice de inicio
      this.books = []; // Limpiar resultados anteriores
      await this.fetchBooks();
    },
    async fetchBooks() {
      const apiKey = process.env.VUE_APP_GOOGLE_BOOKS_API_KEY;
      const query = encodeURIComponent(this.searchTerm);
      const url = `https://www.googleapis.com/books/v1/volumes?q=${query}&startIndex=${this.startIndex}&maxResults=10&key=${apiKey}`;

      try {
        const response = await axios.get(url);
        const newBooks = response.data.items || [];
        this.books = [...this.books, ...newBooks];
        this.totalItems = response.data.totalItems;
        this.showLoadMore = this.books.length < this.totalItems;
        this.startIndex += newBooks.length;
      } catch (error) {
        console.error("Error buscando libros:", error);
      }
    },
    async loadMore() {
      await this.fetchBooks();
    },
  },
};
</script>

<style>
.buttonLoadMore {
  margin-left: 70px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.books-container {
  margin-top: -400px !important;
}

@media (max-width: 1400px) {
  .books-container {
    margin-top: -200px !important;
  }

  .title_containerCustom {
    margin-top: -270px !important;
  }
}

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

.title_containerCustom {
  margin-top: -370px;
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
