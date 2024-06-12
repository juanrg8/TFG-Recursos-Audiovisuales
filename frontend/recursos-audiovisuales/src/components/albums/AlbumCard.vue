<template>
  <div>
    <!-- Contenido de tu componente AlbumCard aquí -->
    <div class="card" style="width: 18rem;">
      <img :src="album.images[0].url" alt="Album Cover">
      <div class="card-body">
        <h5 class="card-title">{{ album.name }}</h5>
        <p class="card-text">{{ album.artist }}</p>
        <a @click="addAlbumPending(album.id, album.name)" class="btn btn-primary">Pendiente</a>
        <a href="#" class="btn btn-success">Escuchado</a>
        <a href="#" class="btn btn-danger" v-if="album.miAlbum != undefined && album.miAlbum != null">Eliminar</a>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  props: {
    album: {
      type: Object,
      required: true
    }
  }, computed: {
    ...mapState(['sharedVariable']) // Mapea el estado 'sharedVariable' a una propiedad computada
  },
  methods: {
    addAlbumPending(id, name) {
      let request = {
        usuarioNombre: this.sharedVariable,
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
    }
  }
};
</script>

<style scoped>
/* Aquí puedes agregar estilos para tu componente */
</style>