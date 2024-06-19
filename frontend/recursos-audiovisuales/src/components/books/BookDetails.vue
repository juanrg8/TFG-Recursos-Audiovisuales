<template>

    <div class="background-image">

        <Navbar class="navbar" />

        <div class="main-content">
            <h1>Detalles de libro</h1>
            <div class="card mb-3">
                <img class="card-img-top portada" :src="book.imageLinks" alt="Book Cover">
                <div class="card-body">
                    <h5 class="card-title">{{ book.title }}</h5>
                    <h5 class="card-title">Autor: {{ book.authors }}</h5>
                    <h5 class="card-title">Fecha de publicación: {{ book.publishedDate }}</h5>
                    <h5 class="card-title">Páginas: {{ book.pages }}</h5>
                    <h5 class="card-title">Estado: </h5>
                    <h5 class="card-title" v-if="userBookUser.status == 'PENDING'"> Pendiente</h5>
                    <h5 class="card-title" v-if="userBookUser.status == 'READING'"> Leyendo</h5>
                    <h5 class="card-title" v-if="userBookUser.status == 'READED'"> Leído</h5>
                    <h5 class="card-title" v-if="userBookUser.dateStarted != null">Inicio de la lectura: {{
                    userBookUser.dateStarted }}
                    </h5>
                    <h5 class="card-title" v-if="userBookUser.dateRead != null">Fin de la lectura: {{
                    userBookUser.dateRead }}
                    </h5>
                    <h5 class="card-title">Descripción:</h5>
                    <div class="card-text" v-html="book.description"></div> <!-- Usando v-html para renderizar HTML -->
                    <h5 class="card-title" v-if="userBookUser.rating != null && userBookUser.rating >= 0">Nota: {{
                    userBookUser.rating }}/10</h5>
                    <h5 class="card-title" v-if="userBookUser.rating == null">No hay nota</h5>
                    <h5 class="card-title">Reseña:</h5>
                    <p class="card-text" v-if="userBookUser.review != null && userBookUser.review != ''">{{
                    userBookUser.review }}</p>
                    <p class="card-text" v-if="userBookUser.review == null || userBookUser.review == ''">No ha escrito
                        reseña
                        del libro</p>
                    <a class=" btn btn-success"><router-link :to="{ name: 'BookReview', params: { libroId: this.id } }"
                            class="router-link-custom-white">Editar</router-link></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
    name: 'BookReview',
    props: ['libroId'],
    components: {
        Navbar
    },
    data() {
        return {
            book: {},
            apiKey: process.env.VUE_APP_GOOGLE_BOOKS_API_KEY,
            userBookUser: {},
            isAuthenticated: false,
        };
    }, async mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            await this.loadUserBooks()
        } else {
            this.redirectToHome()
        }
    },
    computed: {
        id() {
            console.log(this.$route.params.libroId);
            return this.$route.params.libroId;
        },
    },
    methods: {
        async loadUserBooks() {
            let nombre = this.$cookies.get('user').username
            await fetch(`http://localhost:8082/userbook/findall/${nombre}`)
                .then(response => response.json())
                .then(async data => {
                    let userBook = data.filter(a => a.libroId == this.id)[0]
                    this.userBookUser = userBook;
                    let googleBook = await this.getGoogleBooks(userBook.libroId)
                    if (googleBook != null && googleBook != undefined) {
                        this.book = googleBook
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        async getGoogleBooks(id) {
            try {
                const response = await fetch(`https://www.googleapis.com/books/v1/volumes/${id}?key=${this.apiKey}`);
                const data = await response.json();
                return {
                    id: data.id,
                    title: data.volumeInfo.title,
                    authors: data.volumeInfo.authors[0],
                    description: data.volumeInfo.description,
                    imageLinks: data.volumeInfo.imageLinks.thumbnail,
                    publishedDate: data.volumeInfo.publishedDate,
                    pages: data.volumeInfo.pageCount,
                    miLibro: true
                };
            } catch (error) {
                console.error('Error al buscar libro:', error);
            }
        }, checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }, redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }
    }
}
</script>

<style>
.portada {
    max-width: 250px;
    max-height: 500px;
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
    margin: 20px;
    /* Margen de 20px a los lados */
}

.main-content {
    background: rgba(0, 0, 0, 0.7);
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

.router-link-custom-white {
    color: rgb(255, 255, 255);
    /* Color del texto en blanco */
    text-decoration: none;
    /* Elimina el subrayado */
}
</style>