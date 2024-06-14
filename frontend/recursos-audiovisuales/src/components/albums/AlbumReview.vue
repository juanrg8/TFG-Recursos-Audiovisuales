<template>
    <div class="background-image">
        <Navbar class="navbar" />
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6">
                    <div class="card p-4">
                        <form class="form-horizontal" @submit.prevent="updateAlbumReview">
                            <fieldset>
                                <legend class="text-center header">Reseña Album</legend>

                                <div class="form-group row">
                                    <div class="col-md-10 text-center ms-5">
                                        <input v-model="rating" id="rating" name="rating" type="number" min="0" max="10"
                                            step="0.01" placeholder="Puntuación" class="form-control">
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-md-10 text-center ms-5">
                                        <textarea v-model="review" class="form-control" id="review" name="review"
                                            placeholder="Escribe tu reseña" rows="7"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 text-center">
                                        <button type="submit" class="btn btn-primary btn-lg">Enviar</button>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
    components: {
        Navbar,
    },
    name: 'AlbumReview',
    props: ['spotifyId'],
    data() {
        return {
            album: {},
            rating: null,
            review: '',
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
            this.updateAlbumState()
            console.table(this.album)
            this.rating = this.userAlbumUser.rating
            this.review = this.userAlbumUser.review
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
                    this.userAlbumUser = userAlbum
                    let spotifyAlbum = await this.getSpotifyAlbums(userAlbum.spotifyId)
                    if (spotifyAlbum != null && spotifyAlbum != undefined) {
                        this.album = spotifyAlbum
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }, updateAlbumState() {
            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                status: "CONSUMED",
                tituloAlbum: this.album.name
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
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        updateAlbumReview() {
            console.log(this.id);
            // Obtener los valores de los inputs
            console.log('Rating:', this.rating);
            console.log('Review:', this.review);

            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                tituloAlbum: this.album.name,
                review: this.review
            };
            fetch('http://localhost:8082/useralbum/updatereview', {
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
                    this.updateAlbumRating()
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        updateAlbumRating() {
            console.log(this.id);
            // Obtener los valores de los inputs
            console.log('Rating:', this.rating);
            console.log('Review:', this.review);

            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                tituloAlbum: this.album.name,
                rating: this.rating
            };
            fetch('http://localhost:8082/useralbum/updaterating', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
                .then(response => response.json())
                .then(data => {
                    // Almacena el token de acceso en la variable accessToken
                    console.log(data)
                    this.$router.push({ name: 'AlbumDetails', params: { spotifyId: this.id } });
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
                    name: data.name,
                    id: data.id
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
};
</script>

<style scoped>
.container {
    min-height: 100vh;

}

.card {
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background: rgba(0, 0, 0, 0.7);
}

.header {
    color: #ffffff;
    font-size: 27px;
    padding: 10px;
}

.bigicon {
    font-size: 35px;
    color: #36A0FF;
}

.form-group {
    margin-bottom: 1.5rem;

}

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
</style>
