<template>
    <div>
        <!-- Barra de navegación -->
        <!-- Importa el componente Navbar -->
        <Navbar />
        <!-- Lista de álbumes -->
        <select @change="filtrarEstado" v-model="albumStatus" class="form-select form-select-lg mb-3"
            aria-label="Large select example">
            <option value="All" selected>Estado</option>
            <option value="PENDING">Pendiente</option>
            <option value="CONSUMED">Escuchado</option>
        </select>

        <div v-if="this.albums.length > 0">
            <div v-for="album in this.albums" :key="album.id">
                <!-- Define el componente AlbumCard -->
                <AlbumCard :album="album" @deleteEvent="handleDeleteEvent" @updateView="handleDeleteEvent" />
            </div>
        </div>
        <div v-else>
            <p>No se encontraron álbumes.</p>
        </div>


    </div>
</template>

<script>

// Importa el componente Navbar
import Navbar from '@/components/Navbar.vue';
import AlbumCard from './AlbumCard.vue';
// Define el componente AlbumCard
export default {
    components: {
        Navbar,
        AlbumCard
    },
    data() {
        return {
            albums: [],
            accessToken: '',
            clientId: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
            clientSecret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
            albumStatus: 'All',
            userAlbums: [],
            albumsCopy: [],
            isAuthenticated: false,
        }
    },
    async mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            await this.getToken();
        } else {
            this.redirectToHome()
        }
    },
    methods: {
        loadUserAlbums() {
            let nombre = this.$cookies.get('user').username
            fetch(`http://localhost:8082/useralbum/findall/${nombre}`)
                .then(response => response.json())
                .then(async data => {
                    // Almacena el token de acceso en la variable accessToken
                    this.userAlbums = data;
                    if (data.length > 0) {
                        await this.getUserAlbums(data);
                        this.albumsCopy = this.albums;
                    } else {
                        this.albums = [];
                        this.albumsCopy = this.albums;
                    }
                    console.log("Albumes: " + this.albums);

                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        async handleDeleteEvent() {
            this.albumStatus = 'All'
            this.albums = []
            this.albumsCopy = []
            await this.getToken();
        },
        async getToken() {
            let authParams = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `grant_type=client_credentials&client_id=${this.clientId}&client_secret=${this.clientSecret}`,
            };

            fetch('https://accounts.spotify.com/api/token', authParams)
                .then(response => response.json())
                .then(data => {
                    // Almacena el token de acceso en la variable accessToken
                    this.accessToken = data.access_token;
                    this.loadUserAlbums();
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }, getUserAlbums(data) {
            data.forEach(async album => {
                if (album.spotifyId != null) {
                    let albumspotify = await this.getSpotifyAlbums(album.spotifyId)
                    if (albumspotify != undefined && albumspotify != null) {
                        this.albums.push(albumspotify);
                    }
                }
            });
        }, async getSpotifyAlbums(id) {
            try {
                let artistParams = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.accessToken,
                    },
                };

                // Realiza la solicitud GET para buscar artistas en Spotify
                const response = await fetch(`https://api.spotify.com/v1/albums/${id}`, artistParams);
                const data = await response.json();
                let album = {
                    images: data.images,
                    artist: data.artists[0].name,
                    name: data.name,
                    id: data.id,
                    miAlbum: true
                }
                return album;
            } catch (error) {
                console.error('Error al buscar albumes:', error);
            }
        }, filtrarEstado() {
            if (this.albumStatus == "All") {
                this.albums = this.albumsCopy
            } else {
                this.albums = []
                let filterUserAlbums = this.userAlbums.filter(album => album.status == this.albumStatus)
                console.log("Albumes filtrados: " + filterUserAlbums)
                this.getUserAlbums(filterUserAlbums);
            }
        }, checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }, redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }



    }

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