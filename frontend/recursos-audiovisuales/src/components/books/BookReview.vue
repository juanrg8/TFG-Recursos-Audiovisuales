<template>

    <div class="background-image">

        <Navbar class="navbar" />
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6">
                    <div class="card p-4">
                        <form class="form-horizontal" @submit.prevent="updateBookReview">
                            <fieldset>
                                <legend class="text-center header">Reseña Libro</legend>

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
    name: 'BookReview',
    props: ['libroId', 'status'],
    data() {
        return {
            book: {},
            rating: null,
            review: '',
            apiKey: process.env.VUE_APP_GOOGLE_BOOKS_API_KEY,
            userBookUser: {},
            isAuthenticated: false
        };
    }, async mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            await this.loadUserBooks()
            this.updateBookState()
            console.table(this.book)
            this.rating = this.userBookUser.rating
            this.review = this.userBookUser.review
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
        }, updateBookState() {
            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                status: this.$route.params.status,
                tituloLibro: this.book.title
            };
            fetch('http://localhost:8082/userbook/movetostatus', {
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
        updateBookReview() {
            console.log(this.id);
            // Obtener los valores de los inputs
            console.log('Rating:', this.rating);
            console.log('Review:', this.review);

            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                tituloLibro: this.book.title,
                review: this.review
            };
            fetch('http://localhost:8082/userbook/updatereview', {
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
                    this.updateBookRating()
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        updateBookRating() {
            console.log(this.id);
            // Obtener los valores de los inputs
            console.log('Rating:', this.rating);
            console.log('Review:', this.review);

            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                tituloLibro: this.book.title,
                rating: this.rating
            };
            fetch('http://localhost:8082/userbook/updaterating', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    this.$router.push({ name: 'BookDetails', params: { libroId: this.id } });
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
            this.$router.push('/');
        }
    }
};

</script>

<style></style>