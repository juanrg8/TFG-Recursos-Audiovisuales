<template>
    <Navbar />
    <h1>Detalles album </h1>
    <div class="card mb-3">
        <img class="card-img-top portada" :src="album.images" alt="Album Cover">
        <div class="card-body">
            <h5 class="card-title">{{ album.name }}</h5>
            <h5 class="card-title" v-if="userAlbumUser.rating != null && userAlbumUser.rating >= 0">Nota: {{
            userAlbumUser.rating }}/10</h5>
            <h5 class="card-title" v-if="userAlbumUser.rating == null">No hay nota</h5>
            <h5 class="card-title">Reseña:</h5>
            <p class="card-text" v-if="userAlbumUser.review != null && userAlbumUser.review != ''">{{
            userAlbumUser.review }}</p>
            <p class="card-text" v-if="userAlbumUser.review == null || userAlbumUser.review == ''">No ha escrito reseña
                del álbum</p>
            <a class=" btn btn-success"><router-link
                    :to="{ name: 'AlbumReview', params: { spotifyId: this.id } }">Editar</router-link></a>
        </div>
    </div>
</template>



<script>
import Navbar from '@/components/Navbar.vue';

export default {
    name: 'AlbumReview',
    props: ['spotifyId'],
    components: {
        Navbar
    },
    data() {
        return {
            album: {},
            accessToken: '',
            clientId: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
            clientSecret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
            userAlbumUser: {},
            isAuthenticated: false,
        };
    }, async mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            await this.getToken()
            await this.loadUserAlbums()
        } else {
            this.redirectToHome()
        }
    },
    computed: {
        id() {
            console.log(this.$route.params.spotifyId);
            return this.$route.params.spotifyId;
        },
    },
    methods: {
        async loadUserAlbums() {
            let nombre = this.$cookies.get('user').username
            await fetch(`http://localhost:8082/useralbum/findall/${nombre}`)
                .then(response => response.json())
                .then(async data => {
                    let userAlbum = data.filter(a => a.spotifyId == this.id)[0]
                    this.userAlbumUser = userAlbum;
                    let spotifyAlbum = await this.getSpotifyAlbums(userAlbum.spotifyId)
                    if (spotifyAlbum != null && spotifyAlbum != undefined) {
                        this.album = spotifyAlbum
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
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

                // Realiza la solicitud GET para buscar artistas en Spotify
                const response = await fetch(`https://api.spotify.com/v1/albums/${id}`, artistParams);
                const data = await response.json();
                let album = {
                    images: data.images[0].url,
                    artist: data.artists[0].name,
                    name: data.name,
                    id: data.id,
                    miAlbum: true
                }
                return album;
            } catch (error) {
                console.error('Error al buscar albumes:', error);
            }
        }, async getToken() {
            let authParams = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `grant_type=client_credentials&client_id=${this.clientId}&client_secret=${this.clientSecret}`,
            };

            await fetch('https://accounts.spotify.com/api/token', authParams)
                .then(response => response.json())
                .then(data => {
                    // Almacena el token de acceso en la variable accessToken
                    this.accessToken = data.access_token;
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }, checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }, redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }
    }
}
</script>

<style scoped>
.portada {
    max-width: 500px;
    max-height: 500px;
}
</style>
