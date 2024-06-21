<template>
  <Navbar style="z-index: 9" />

  <div>
    <div>
      <!-- Contenedor del Titulo -->
      <div class="title_container">
        <!-- Titulo -->
        <div class="title">
          <h1>Mis Libros</h1>
        </div>
      </div>

      <!-- Contenedor de Barra de busqueda -->
      <div class="search_bar_container">
        <!-- Barra de búsqueda -->
        <div class="search-bar">
          <select
            @change="filtrarEstado"
            v-model="bookStatus"
            class="form-select form-select-lg mb-3 filter_select paddingTopCustom"
            aria-label="Large select example"
          >
            <option value="All" selected>Estado</option>
            <option value="PENDING">Pendiente</option>
            <option value="READING">Leyendo</option>
            <option value="READED">Leído</option>
          </select>
        </div>
      </div>
    </div>
  </div>

  <!-- Contenedor Books -->
  <div>
    <div v-if="books.length > 0" class="albums-container">
      <div class="album-card" v-for="book in books" :key="book.id">
        <BookCard
          :book="book"
          @deleteEvent="handleDeleteEvent"
          @updateView="handleDeleteEvent"
        />
      </div>
    </div>
    <!-- Contenedor de No Books -->
    <div v-else class="no_albums-container">
      <!-- No Books -->
      <div class="no-albums">
        <p class="subtitle">No se encontraron libros.</p>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import BookCard from "@/components/books/BookCard.vue";

export default {
  components: {
    Navbar,
    BookCard,
  },
  data() {
    return {
      books: [],
      apiKey: process.env.VUE_APP_GOOGLE_BOOKS_API_KEY,
      bookStatus: "All",
      userBooks: [],
      booksCopy: [],
      isAuthenticated: false,
    };
  },
  async mounted() {
    this.checkAuthentication();
    if (this.isAuthenticated) {
      await this.loadUserBooks();
    } else {
      this.redirectToHome();
    }
  },
  methods: {
    async loadUserBooks() {
      let nombre = this.$cookies.get("user").username;
      fetch(`http://localhost:8082/userbook/findall/${nombre}`)
        .then((response) => response.json())
        .then(async (data) => {
          this.userBooks = data;
          if (data.length > 0) {
            await this.getUserBooks(data);
            this.booksCopy = [...this.books];
            this.filtrarEstado();
          } else {
            this.books = [];
            this.booksCopy = [];
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    async handleDeleteEvent() {
      this.bookStatus = "All";
      this.books = [];
      this.booksCopy = [];
      await this.loadUserBooks();
    },
    async getUserBooks(data) {
      this.books = [];
      for (const book of data) {
        if (book.libroId != null) {
          let booksGoogle = await this.getGoogleBooks(book.libroId);
          if (booksGoogle != undefined && booksGoogle != null) {
            booksGoogle.status = book.status;
            this.books.push(booksGoogle);
          }
        }
      }
    },
    async getGoogleBooks(id) {
      try {
        const response = await fetch(
          `https://www.googleapis.com/books/v1/volumes/${id}?key=${this.apiKey}`
        );
        const data = await response.json();
        return {
          id: data.id,
          title: data.volumeInfo.title,
          authors: data.volumeInfo.authors,
          description: data.volumeInfo.description,
          imageLinks: data.volumeInfo.imageLinks,
          publishedDate: data.volumeInfo.publishedDate,
          pages: data.volumeInfo.pageCount,
          miLibro: true,
        };
      } catch (error) {
        console.error("Error al buscar libro:", error);
      }
    },
    filtrarEstado() {
      if (this.bookStatus === "All") {
        this.books = [...this.booksCopy];
      } else {
        this.books = this.booksCopy.filter(
          (book) => book.status === this.bookStatus
        );
      }
    },
    checkAuthentication() {
      const token = localStorage.getItem("token");
      this.isAuthenticated = !!token;
    },
    redirectToHome() {
      this.$router.push("/");
    },
  },
};
</script>

<style>
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
  width: 800px !important;
}
</style>
