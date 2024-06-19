<template>
    <Navbar style="z-index: 9" />
    <section class="vh-100 containerDetail" style="background-color: #f4f5f7">
        <div class="py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100 customDiv">
                <div class="col col-lg-6 mb-4 mb-lg-0">
                    <div class="card mb-3" style="border-radius: 0.5rem">
                        <div class="row g-0 customHeight">
                            <div class="col-md-4 gradient-custom text-center text-white" style="
                  border-top-left-radius: 0.5rem;
                  border-bottom-left-radius: 0.5rem;
                ">
                                <img :src="album.images" alt="Avatar" class="img-fluid my-5 imgCustom" />
                                <h5 class="albumName">{{ album.name }}</h5>
                                <router-link :to="{ name: 'AlbumReview', params: { spotifyId: this.id } }"
                                    class="routerLinkCustom mb-5">Editar <i
                                        class="bi bi-pencil-square"></i></router-link>
                            </div>
                            <div class="col-md-8">
                                <div class="card-body p-4">
                                    <h6>Puntuación</h6>
                                    <hr class="mt-0 mb-4" />
                                    <div class="row pt-1" style="width: 300px">
                                        <div class="col-6 mb-3">
                                            <div class="rating">
                                                <input value="5" name="rating" id="star5" type="radio" disabled />
                                                <label for="star5"></label>
                                                <input value="4" name="rating" id="star4" type="radio" disabled />
                                                <label for="star4"></label>
                                                <input value="3" name="rating" id="star3" type="radio" disabled />
                                                <label for="star3"></label>
                                                <input value="2" name="rating" id="star2" type="radio" disabled />
                                                <label for="star2"></label>
                                                <input value="1" name="rating" id="star1" type="radio" disabled />
                                                <label for="star1"></label>
                                            </div>
                                        </div>
                                    </div>
                                    <h6>Reseña</h6>
                                    <hr class="mt-0 mb-4" />
                                    <div class="row pt-1" style="width: 360px">
                                        <div class="col-6 mb-3 customCol">
                                            <p v-if="userAlbumUser.review != null &&
                                    userAlbumUser.review != ''
                                    " class="text-muted">
                                                {{ userAlbumUser.review }}
                                            </p>
                                            <p v-if="userAlbumUser.review == null ||
                                    userAlbumUser.review == ''
                                    " class="text-muted">
                                                No has añadido una reseña al albúm todavia.
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import $ from "jquery";

export default {
    name: "AlbumReview",
    props: ["spotifyId"],
    components: {
        Navbar,
    },
    data() {
        return {
            album: {},
            accessToken: "",
            clientId: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
            clientSecret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
            userAlbumUser: {},
            isAuthenticated: false,
        };
    },
    async mounted() {
        this.checkAuthentication();
        if (this.isAuthenticated) {
            await this.getToken();
            await this.loadUserAlbums();
            if (
                this.userAlbumUser.rating != null ||
                this.userAlbumUser.rating != undefined
            ) {
                $("#star" + this.userAlbumUser.rating).prop("checked", true);
            }
        } else {
            this.redirectToHome();
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
            let nombre = this.$cookies.get("user").username;
            await fetch(`http://localhost:8082/useralbum/findall/${nombre}`)
                .then((response) => response.json())
                .then(async (data) => {
                    let userAlbum = data.filter((a) => a.spotifyId == this.id)[0];
                    this.userAlbumUser = userAlbum;
                    let spotifyAlbum = await this.getSpotifyAlbums(userAlbum.spotifyId);
                    if (spotifyAlbum != null && spotifyAlbum != undefined) {
                        this.album = spotifyAlbum;
                    }
                })
                .catch((error) => {
                    console.error("Error:", error);
                });
        },
        async getSpotifyAlbums(id) {
            try {
                let artistParams = {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: "Bearer " + this.accessToken,
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
                    date: data.release_date,
                    tracks: data.total_tracks,
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

            await fetch("https://accounts.spotify.com/api/token", authParams)
                .then((response) => response.json())
                .then((data) => {
                    // Almacena el token de acceso en la variable accessToken
                    this.accessToken = data.access_token;
                })
                .catch((error) => {
                    console.error("Error:", error);
                });
        },
        checkAuthentication() {
            const token = localStorage.getItem("token");
            this.isAuthenticated = !!token;
        },
        redirectToHome() {
            this.$router.push("/"); // Redirige a la página principal
        },
    },
};
</script>

<style scoped>
.containerDetail {
    display: flex;
    align-items: center;
    justify-content: center;
}

.customCol {
    width: 100%;
}

.customHeight {
    height: 310px;
}

.nav {
    width: 100%;
    height: 90px;
    position: fixed;
    line-height: 65px;
    text-align: center;
}

.albumName {
    font-size: 1rem !important;
}

.rating {
    display: inline-block;
}

.rating input {
    display: none;
}

.rating label {
    float: right;
    cursor: default;
    color: #ccc;
    transition: color 0.3s;
}

.rating label:before {
    content: "\2605";
    font-size: 30px;
}

.rating input:checked~label {
    color: hsl(210deg 100% 44%);
    transition: color 0.3s;
}

.routerLinkCustom {
    text-decoration: none;
    color: white;
    font-weight: bolder;
}

.vh-90 {
    height: 90.1vh !important;
}

@media (max-width: 1400px) {
    .customDiv {
        margin-left: 0px !important;
    }
}

@media (max-width: 770px) {
    .customDiv {
        width: 400px !important;
    }

    .customHeight {
        height: 650px !important;
    }
}

.customDiv {
    width: 1200px;
    margin-right: 0px !important;
}

.imgCustom {
    width: 150px;
    border-radius: 20px;
}

.my-5 {
    margin-bottom: 1rem !important;
    margin-top: 2rem !important;
}

.gradient-custom {
    /* fallback for old browsers */
    background: rgb(0, 212, 255);
    background: linear-gradient(90deg,
            rgba(0, 212, 255, 1) 0%,
            rgba(27, 27, 204, 1) 100%,
            rgba(2, 0, 36, 1) 100%);
    background: -webkit-linear-gradient(to right bottom,
            rgba(0, 212, 255, 1),
            rgba(27, 27, 204, 1));
}
</style>
