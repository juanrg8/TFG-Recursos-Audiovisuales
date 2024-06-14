<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div class="background-image">
        <!-- Barra de navegación -->
        <Navbar class="navbar" />

        <!-- Contenedor principal -->
        <div class="main-content">
            <!-- Barra de búsqueda -->
            <div class="search-bar-container">
                <div class="search-bar">
                    <input v-model="searchTerm" type="text" placeholder="Buscar álbumes..." class="search-input" />
                    <button @click="search" class="search-button">Buscar</button>
                </div>
            </div>

            <!-- Lista de álbumes -->
            <div v-if="albums.length > 0" class="albums-container">
                <div class="album-card" v-for="album in albums" :key="album.id">
                    <!-- Define el componente AlbumCard -->
                    <AlbumCard :album="album" />
                </div>
            </div>
            <div v-else class="no-albums">
                <p>Busca algún álbum y añádelo a tu lista!</p>
            </div>
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
                    albums.value = []; // Reiniciar la lista de álbumes si no se encuentran resultados
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

.background-image {
    background-image: url('/public/images/background.jpg');
    /* Asegúrate de usar la ruta correcta de la imagen */
    background-size: cover;
    background-position: center;
    min-height: 100vh;
    /* Asegura que el fondo cubra toda la pantalla */
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
    /* Asegura que el texto sea legible */
    position: relative;
}

.navbar {
    position: absolute;
    /* Asegura que la Navbar esté siempre en la parte superior */
    top: 20px;
    /* Añade margen superior a la Navbar */
    width: calc(100% - 40px);
    /* Añade margen a los lados de la Navbar */
    background: rgba(0, 0, 0, 0.7);
    /* Fondo semitransparente para la Navbar */
    padding: 10px;
    z-index: 1;
    /* Asegura que la Navbar esté por encima del resto del contenido */
    margin: 0 20px;
    /* Margen de 20px a los lados */
}

.main-content {
    background: rgba(0, 0, 0, 0);
    width: 100%;
    max-width: 1845px;
    /* Ancho máximo del contenido principal */
    margin-top: 150px;
    /* Ajusta el margen superior para dejar espacio para la Navbar */
    padding: 10px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.search-bar-container {
    width: 100%;
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.search-bar {
    display: flex;
    align-items: center;
    width: 100%;
    max-width: 2800px;
    /* Ajusta el ancho máximo de la barra de búsqueda */
}

.search-input {
    flex: 1;
    height: 50px;
    background: rgba(0, 0, 0, 0.7);
    /* Aumenta la altura del input de búsqueda */
    border: 0px solid #ccc;
    border-radius: 5px;
    padding: 15px;
    /* Ajusta el padding del input */
    margin-right: 10px;
    font-size: 28px;
    color: white;
    /* Ajusta el tamaño de fuente */
}

.search-button {
    height: 50px;
    /* Aumenta la altura del botón de búsqueda */
    background-color: #230745;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    padding: 0 20px;
    /* Ajusta el padding del botón */
    font-size: 18px;
    /* Ajusta el tamaño de fuente */
}

.albums-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    /* Distribuye las tarjetas de álbumes en 4 columnas */
    gap: 20px;
    /* Espacio entre las tarjetas de álbumes */
    margin-top: 20px;
    /* Ajusta el margen superior */
}

.album-card {
    background: rgba(0, 0, 0, 0.6);
    /* Fondo semitransparente para mejorar la legibilidad */
    padding: 10px;
    border-radius: 10px;
}

.no-albums {
    background: rgba(0, 0, 0, 0.7);
    /* Fondo semitransparente para mejorar la legibilidad */
    padding: 50px;
    border-radius: 10px;
    text-align: center;
    width: 1825px;
    /* Ajusta el ancho para que sea igual al de la Navbar */
    margin: 0px;
    /* Añade margen alrededor */
    font-size: 34px;
}
</style>
