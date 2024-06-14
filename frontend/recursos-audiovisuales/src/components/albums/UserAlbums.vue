<template>
    <div class="background-image">
        <!-- Barra de navegación -->
        <Navbar class="navbar" />

        <!-- Selector de estado de álbum -->
        <select @change="filtrarEstado" v-model="albumStatus" class="form-select form-select-lg mb-3"
            aria-label="Large select example">
            <option value="All" selected>Estado</option>
            <option value="PENDING">Pendiente</option>
            <option value="CONSUMED">Escuchado</option>
        </select>

        <!-- Lista de álbumes -->
        <div class="main-content">
            <div v-if="albums.length > 0" class="albums-container">
                <div v-for="album in albums" :key="album.id" class="album-card">
                    <!-- Define el componente AlbumCard -->
                    <AlbumCard :album="album" @deleteEvent="handleDeleteEvent" @updateView="handleDeleteEvent" />
                </div>
            </div>
            <div v-else class="no-albums">
                <p>No se encontraron álbumes.</p>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import AlbumCard from './AlbumCard.vue';

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
                    this.userAlbums = data;
                    if (data.length > 0) {
                        await this.getUserAlbums(data);
                        this.albumsCopy = this.albums;
                    } else {
                        this.albums = [];
                        this.albumsCopy = this.albums;
                    }
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
                    this.accessToken = data.access_token;
                    this.loadUserAlbums();
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        async getUserAlbums(data) {
            data.forEach(async album => {
                if (album.spotifyId != null) {
                    let albumspotify = await this.getSpotifyAlbums(album.spotifyId)
                    if (albumspotify != undefined && albumspotify != null) {
                        this.albums.push(albumspotify);
                    }
                }
            });
        },
        async getSpotifyAlbums(id) {
            try {
                let artistParams = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.accessToken,
                    },
                };

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
        },
        filtrarEstado() {
            if (this.albumStatus == "All") {
                this.albums = this.albumsCopy
            } else {
                this.albums = []
                let filterUserAlbums = this.userAlbums.filter(album => album.status == this.albumStatus)
                this.getUserAlbums(filterUserAlbums);
            }
        },
        checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        },
        redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }
    }
};
</script>

<style scoped>
.background-image {
    background-image: url('/public/images/background.jpg');
    background-size: cover;
    background-position: center;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
    position: relative;
}

.main-content {
    background: rgba(0, 0, 0, 0);
    width: 100%;
    max-width: 1200px;
    margin-top: 100px;
    padding: 20px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
}


.form-select {
    width: 1300px;
    margin-bottom: 0px;
    margin-top: 200px;
    background-color: rgba(0, 0, 0, 0.7);
    /* Fondo semitransparente */
    color: white;
    /* Color del texto */
    border: none;
    /* Quita el borde */
    padding: 10px;
    /* Ajusta el padding */
    border-radius: 5px;
    /* Añade borde redondeado */
}

.form-select option {
    background-color: rgba(0, 0, 0, 0.7);
    /* Fondo semitransparente */
    color: white;
    /* Color del texto */
    border: none;
    /* Quita el borde */
    padding: 10px;
    /* Ajusta el padding */
}

.albums-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-top: 20px;
}

.album-card {
    background: rgba(0, 0, 0, 0.6);
    padding: 10px;
    border-radius: 10px;
}

.no-albums {
    background: rgba(0, 0, 0, 0.7);
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    width: calc(100% - 40px);
    margin: 20px;
    font-size: 24px;
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
</style>
