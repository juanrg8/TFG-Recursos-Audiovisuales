<template>
  <div>
    <div class="card">
      <!-- IMG -->
      <img
        v-if="!book.volumeInfo"
        :src="getFormattedImage(book.imageLinks.thumbnail)"
        alt="Portada del libro"
        class="card-img-top"
      />
      <img
        v-else-if="
          book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail
        "
        :src="getFormattedImage(book.volumeInfo.imageLinks.thumbnail)"
        alt="Portada del libro"
        class="card-img-top"
      />
      <img
        v-else
        src="https://ih1.redbubble.net/image.1756098780.0530/fpp,small,lustre,wall_texture,product,750x1000.jpg"
        alt="Portada no encontrada"
        class="card-img-top"
      />
      <!-- Body -->
      <div class="card-body">
        <h5
          class="card-title"
          v-if="book.miLibro == undefined && book.miLibro == null"
        >
          {{ book.volumeInfo.title }}
        </h5>
        <h5
          class="card-title"
          v-if="book.miLibro != undefined && book.miLibro != null"
        >
          <router-link
            :to="{ name: 'BookDetails', params: { libroId: book.id } }"
            class="router-link-custom"
            >{{ book.title }}
          </router-link>
        </h5>
        <!-- Autor -->
        <p
          class="card-text"
          v-if="book.miLibro != undefined && book.miLibro != null"
        >
          {{ book.authors[0] }}
        </p>
        <p class="card-text" v-else>
          {{
            book.volumeInfo.authors
              ? book.volumeInfo.authors[0]
              : "Autor no especificado"
          }}
        </p>
        <!-- Fecha libro -->
        <p
          class="card-text"
          v-if="book.miLibro != undefined && book.miLibro != null"
        >
          {{ book.publishedDate }}
        </p>
        <p class="card-text" v-else>
          {{ book.volumeInfo.publishedDate }}
        </p>
        <!-- Botones -->
        <div class="card-buttons">
          <a
            v-if="
              isAuthenticated &&
              book.miLibro != undefined &&
              book.miLibro != null
            "
            @click="addBookPendingStatus(book.title)"
            class="btn btn-primary"
            >Pendiente</a
          >
          <a
            v-if="
              isAuthenticated &&
              book.miLibro == undefined &&
              book.miLibro == null
            "
            @click="
              addBookPending(
                book.id,
                book.volumeInfo.title,
                book.volumeInfo.publishedDate,
                book.volumeInfo.pageCount,
                book.volumeInfo.categories
                  ? book.volumeInfo.categories[0]
                  : 'No especificado',
                book.volumeInfo.authors
                  ? book.volumeInfo.authors[0]
                  : 'Autor no'
              )
            "
            class="btn btn-primary"
            >Pendiente</a
          >
          <a
            @click="deleteBook(book.title)"
            class="btn btn-danger"
            v-if="
              isAuthenticated &&
              book.miLibro != undefined &&
              book.miLibro != null
            "
            >Eliminar</a
          >
          <a
            v-if="
              isAuthenticated &&
              book.miLibro != undefined &&
              book.miLibro != null
            "
            class="btn btn-success"
          >
            <router-link
              :to="{
                name: 'BookReview',
                params: { libroId: book.id, status: 'READING' },
              }"
              class="router-link-custom-white"
              >Leyendo</router-link
            >
          </a>
          <a
            v-if="
              isAuthenticated &&
              book.miLibro != undefined &&
              book.miLibro != null
            "
            class="btn btn-success"
          >
            <router-link
              :to="{
                name: 'BookReview',
                params: { libroId: book.id, status: 'READED' },
              }"
              class="router-link-custom-white"
              >Leído</router-link
            >
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      isAuthenticated: false,
    };
  },
  mounted() {
    this.checkAuthentication();
  },
  methods: {
    getFormattedImage(url) {
      if (url) {
        return url.replace(/zoom=1$/, "zoom=2");
      }
      return "";
    },
    addBookPending(id, name, date, pages, genre, author) {
      let formattedDate = date;

      // Verificar si la fecha es solo el año
      if (/^\d{4}$/.test(date)) {
        formattedDate = date + "-01-01"; // Añadir '01-01' si es solo el año
      }

      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        libroId: id,
        status: "PENDING",
        tituloLibro: name,
        publishDate: formattedDate,
        paginas: pages,
        genre: genre,
        author: author,
      };

      fetch("http://localhost:8082/userbook/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    addBookPendingStatus(title) {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        status: "PENDING",
        tituloLibro: title,
      };
      fetch("http://localhost:8082/userbook/movetostatus", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          this.$emit("updateView", data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    deleteBook(title) {
      let request = {
        usuarioNombre: this.$cookies.get("user").username,
        tituloLibro: title,
      };
      fetch("http://localhost:8082/userbook/delete", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      })
        .then((response) => response.json())
        .then((data) => {
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

<style>
@media (max-width: 1400px) {
}

.card {
  display: flex !important;
  flex-direction: column !important;
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
