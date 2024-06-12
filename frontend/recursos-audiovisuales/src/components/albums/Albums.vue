<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div>
        <!-- Barra de navegación -->
        <!-- Importa el componente Navbar -->
        <Navbar />

        <!-- Barra de búsqueda -->
        <div class="search-bar-container">
            <div class="search-bar">
                <input v-model="searchTerm" type="text" placeholder="Buscar álbumes..." class="search-input" />
                <button @click="search" class="search-button">Buscar</button>
            </div>
        </div>

        <!-- Lista de álbumes -->
        <div v-if="albums.length > 0">
            <div v-for="album in albums" :key="album.id">
                <!-- Define el componente AlbumCard -->
                <AlbumCard :album="album" />
            </div>
        </div>
        <div v-else>
            <p>No se encontraron álbumes.</p>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
// Importa el componente Navbar
import Navbar from '@/components/Navbar.vue';
// Define el componente AlbumCard
import AlbumCard from '@/components/albums/AlbumCard.vue';

export default {
    components: {
        Navbar,
        AlbumCard,
    },
    setup() {
        const searchTerm = ref(''); // Variable para almacenar el texto de búsqueda del usuario
        const albums = ref([]); // Array para almacenar los álbumes recuperados de Spotify
        const accessToken = ref(''); // Variable para almacenar el token de acceso
        const clientId = process.env.VUE_APP_SPOTIFY_CLIENT_ID;
        const clientSecret = process.env.VUE_APP_SPOTIFY_CLIENT_SECRET;

        // Realiza la solicitud para obtener el token de acceso cuando el componente se crea
        onMounted(() => {
            let authParams = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}`,
            };

            fetch('https://accounts.spotify.com/api/token', authParams)
                .then(response => response.json())
                .then(data => {
                    // Almacena el token de acceso en la variable accessToken
                    accessToken.value = data.access_token;
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });

        // Función asíncrona para buscar artistas en Spotify
        const search = async () => {
            try {
                let artistParams = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + accessToken.value,
                    },
                };

                // Realiza la solicitud GET para buscar artistas en Spotify
                const response = await fetch(`https://api.spotify.com/v1/search?q=${searchTerm.value}&type=artist`, artistParams);
                const data = await response.json();

                // Verifica si hay resultados de artistas
                if (data && data.artists && data.artists.items.length > 0) {
                    const artistID = data.artists.items[0].id; // Obtiene el ID del primer artista encontrado
                    console.log('Search Input:', searchTerm.value);
                    console.log('Artist ID:', artistID);

                    // Obtener los álbumes del artista
                    const albumResponse = await fetch(`https://api.spotify.com/v1/artists/${artistID}/albums?include_groups=album&market=US&limit=50`, artistParams);
                    const albumData = await albumResponse.json();
                    albums.value = albumData.items; // Actualizar el valor de albums
                } else {
                    console.log('No se encontraron artistas.');
                }
            } catch (error) {
                console.error('Error al buscar artistas:', error);
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
/* Estilos específicos para esta vista */

.search-bar-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    /* Agrega un poco de espacio entre la barra de navegación y la barra de búsqueda */
}

.search-bar {
    display: flex;
    align-items: center;
}

.search-input {
    width: 300px;
    height: 35px;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding-left: 10px;
    margin-right: 10px;
}

.search-button {
    height: 35px;
    background-color: #230745;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    padding: 0 15px;
}
</style>
