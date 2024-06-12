<!-- src/components/SpotifySearch.vue -->
<template>
    <div>
      <input v-model="query" @keyup.enter="search" placeholder="Buscar álbumes, artistas, canciones..." />
      <button @click="search">Buscar</button>
      <div v-if="results">
        <div v-for="result in results" :key="result.id" class="result-item">
          <img :src="result.images[0]?.url" alt="Album Art" />
          <div class="info">
            <p>{{ result.name }}</p>
            <p>{{ result.artists.map(artist => artist.name).join(', ') }}</p>
          </div>
          <button @click="saveAlbum(result.id)">Guardar Álbum</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { searchSpotify } from '../services/spotifyService';
  
  export default {
    data() {
      return {
        query: '',
        results: null,
      };
    },
    methods: {
      async search() {
        if (this.query) {
          const data = await searchSpotify(this.query, 'album');
          this.results = data.albums.items.map(album => ({
            id: album.id,
            name: album.name,
            artists: album.artists,
            images: album.images
          }));
        }
      },
      saveAlbum(albumId) {
        // Implementa la lógica para guardar el álbum en la lista del usuario
        console.log('Guardando álbum con ID:', albumId);
        // Aquí puedes llamar a un servicio o método para guardar el álbum
      },
    },
  };
  </script>
  
  <style scoped>
  .result-item {
    display: flex;
    align-items: center;
    gap: 1em;
    margin-bottom: 1em;
  }
  
  .result-item img {
    width: 100px;
    height: 100px;
    object-fit: cover;
  }
  
  .result-item .info {
    flex-grow: 1;
  }
  </style>
  