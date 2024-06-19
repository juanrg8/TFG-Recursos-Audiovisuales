<template>
    <div class="background-image">
        <Navbar class="navbar" />

        <!-- Selector de estado de libro -->
        <select @change="filtrarEstado" v-model="bookStatus" class="form-select form-select-lg mb-3"
            aria-label="Large select example">
            <option value="All" selected>Estado</option>
            <option value="PENDING">Pendiente</option>
            <option value="READING">Leyendo</option>
            <option value="READED">Leído</option>
        </select>

        <!-- Lista de libros -->
        <div class="main-content">
            <div v-if="books.length > 0" class="books-container">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <!-- Utiliza row-cols-md-4 para mostrar 4 columnas en dispositivos grandes -->
                    <div v-for="book in books" :key="book.id" class="col">
                        <div class="book-grid">
                            <BookCard :book="book" @deleteEvent="handleDeleteEvent" @updateView="handleDeleteEvent" />
                        </div>
                    </div>
                </div>
            </div>
            <div v-else class="no-books">
                <p>No se encontraron libros.</p>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import BookCard from '@/components/books/BookCard.vue';

export default {
    components: {
        Navbar,
        BookCard
    },
    data() {
        return {
            books: [],
            apiKey: process.env.VUE_APP_GOOGLE_BOOKS_API_KEY,
            bookStatus: 'All',
            userBooks: [],
            booksCopy: [],
            isAuthenticated: false,
        }
    },
    async mounted() {
        this.checkAuthentication();
        if (this.isAuthenticated) {
            await this.loadUserBooks();
        } else {
            this.redirectToHome();
        }
    },
    methods: {
        async loadUserBooks() {
            let nombre = this.$cookies.get('user').username;
            fetch(`http://localhost:8082/userbook/findall/${nombre}`)
                .then(response => response.json())
                .then(async data => {
                    this.userBooks = data;
                    if (data.length > 0) {
                        await this.getUserBooks(data);
                        this.booksCopy = [...this.books];
                        this.filtrarEstado();
                    } else {
                        this.books = [];
                        this.booksCopy = [];
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        async handleDeleteEvent() {
            this.bookStatus = 'All';
            this.books = [];
            this.booksCopy = [];
            await this.loadUserBooks();
        },
        async getUserBooks(data) {
            this.books = [];
            for (const book of data) {
                if (book.libroId != null) {
                    let booksGoogle = await this.getGoogleBooks(book.libroId);
                    if (booksGoogle != undefined && booksGoogle != null) {
                        booksGoogle.status = book.status;
                        this.books.push(booksGoogle);
                    }
                }
            }
        },
        async getGoogleBooks(id) {
            try {
                const response = await fetch(`https://www.googleapis.com/books/v1/volumes/${id}?key=${this.apiKey}`);
                const data = await response.json();
                return {
                    id: data.id,
                    title: data.volumeInfo.title,
                    authors: data.volumeInfo.authors,
                    description: data.volumeInfo.description,
                    imageLinks: data.volumeInfo.imageLinks,
                    publishedDate: data.volumeInfo.publishedDate,
                    pages: data.volumeInfo.pageCount,
                    miLibro: true
                };
            } catch (error) {
                console.error('Error al buscar libro:', error);
            }
        },
        filtrarEstado() {
            if (this.bookStatus === 'All') {
                this.books = [...this.booksCopy];
            } else {
                this.books = this.booksCopy.filter(book => book.status === this.bookStatus);
            }
        },
        checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        },
        redirectToHome() {
            this.$router.push('/');
        }
    }
};
</script>

<style>
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

/* Estilo para los libros en la cuadrícula */
.book-grid {
    height: 100%;
    /* Asegura que todos los contenedores de libro tengan la misma altura */
    display: flex;
    /* Utiliza flexbox para alinear verticalmente */
    flex-direction: column;
    /* Alinea los elementos en columnas */
    justify-content: space-between;
    /* Distribuye el espacio verticalmente entre los elementos */
}

/* Estilo para los libros en la cuadrícula */
.book-grid .col {
    padding: 15px;
    /* Espaciado interno de cada libro */
}
</style>
