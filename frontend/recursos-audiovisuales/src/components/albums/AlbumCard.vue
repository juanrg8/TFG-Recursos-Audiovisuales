<template>
    <div>
      <!-- Contenido de tu componente AlbumCard aquí -->
      <h2>{{ album.name }}</h2>
      <p>{{ album.artist }}</p>
      <img :src="album.imageUrl" alt="Album Cover">
    </div>
  </template>
  
  <script>
  export default {
    props: {
      album: {
        type: Object,
        required: true
      }
    }
  }
  </script>
  