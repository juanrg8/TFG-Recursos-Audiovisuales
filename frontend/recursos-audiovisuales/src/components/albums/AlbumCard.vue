<template>
  <div>
    <!-- Contenido de tu componente AlbumCard aquí -->
    <div class="card custom-card" style="width: 18rem;">
      <img :src="album.images[0].url" alt="Album Cover">
      <div class="card-body">
        <h5 class="card-title" v-if="album.miAlbum == undefined && album.miAlbum == null">{{ album.name }}</h5>
        <h5 class="card-title" v-if="album.miAlbum != undefined && album.miAlbum != null"><router-link
            :to="{ name: 'AlbumDetails', params: { spotifyId: album.id } }" class="router-link-custom">{{ album.name
            }}</router-link></h5>
        <p class="card-text">{{ album.artist }}</p>
        <a v-if="this.isAuthenticated && album.miAlbum != undefined && album.miAlbum != null"
          @click="addAlbumPendingStatus(album.name)" class="btn btn-primary">Pendiente</a>
        <a v-if="this.isAuthenticated && album.miAlbum == undefined || album.miAlbum == null"
          @click="addAlbumPending(album.id, album.name)" class="btn btn-primary">Pendiente</a>
        <a v-if="this.isAuthenticated && album.miAlbum != undefined && album.miAlbum != null"
          class=" btn btn-success"><router-link :to="{ name: 'AlbumReview', params: { spotifyId: album.id } }"
            class="router-link-custom-white">Escuchado</router-link></a>
        <a @click="deleteAlbum(album.name)" class="btn btn-danger"
          v-if="this.isAuthenticated && album.miAlbum != undefined && album.miAlbum != null">Eliminar</a>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      isAuthenticated: false
    }
  },
  props: {
    album: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.checkAuthentication()
  },
  methods: {
    addAlbumPending(id, name) {
      let request = {
        usuarioNombre: this.$cookies.get('user').username,
        spotifyId: id,  // Puedes cambiar esto a album.id si tienes un ID específico del álbum
        status: "PENDING",
        tituloAlbum: name
      };
      fetch('http://localhost:8082/useralbum/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
      })
        .then(response => response.json())
        .then(data => {
          // Almacena el token de acceso en la variable accessToken
          console.log(data);
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }, addAlbumPendingStatus(title) {
      let request = {
        usuarioNombre: this.$cookies.get('user').username,
        status: "PENDING",
        tituloAlbum: title
      };
      fetch('http://localhost:8082/useralbum/movetostatus', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
      })
        .then(response => response.json())
        .then(data => {
          // Almacena el token de acceso en la variable accessToken
          console.log(data);
          this.$emit('updateView', data);
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }, deleteAlbum(title) {
      let request = {
        usuarioNombre: this.$cookies.get('user').username,
        tituloAlbum: title
      };
      fetch('http://localhost:8082/useralbum/delete', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
      })
        .then(response => response.json())
        .then(data => {
          // Almacena el token de acceso en la variable accessToken
          this.$emit('deleteEvent', data);
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }, checkAuthentication() {
      const token = localStorage.getItem('token');
      this.isAuthenticated = !!token;
    }
  }
};
</script>

<style scoped>
/* Estilos adicionales específicos para el componente */
.custom-card {
  border-color: white;
}

/* Estilo personalizado para el router-link */
.router-link-custom {
  color: rgb(0, 0, 0);
  /* Color del texto en blanco */
  text-decoration: none;
  /* Elimina el subrayado */
}

/* Elimina el subrayado en hover y focus */
.router-link-custom:hover,
.router-link-custom:focus {
  text-decoration: none;
  color: rgb(107, 27, 173);
}

.router-link-custom-white {
  color: rgb(255, 255, 255);
  /* Color del texto en blanco */
  text-decoration: none;
  /* Elimina el subrayado */
}
</style>