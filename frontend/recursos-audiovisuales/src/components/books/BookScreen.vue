<template>
    <div class="background-image">

        <Navbar class="navbar" />

        <div class="main-content">
            <h1>Busca tus libros aquí</h1>
            <div class="search-bar-container">
                <div class="search-bar">
                    <input v-model="searchTerm" type="text" placeholder="Buscar libros..." class="search-input" />
                    <button @click="search" class="search-button">Buscar</button>
                </div>
            </div>


            <div v-if="books.length" class="results-container">
                <h2>Resultados de la búsqueda:</h2>
                <div class="book-grid">
                    <BookCard v-for="book in books" :key="book.id" :book="book" />
                </div>
                <button v-if="showLoadMore" @click="loadMore" class="load-more-button">Cargar más</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Navbar from '@/components/Navbar.vue';
import BookCard from '@/components/books/BookCard.vue';

export default {
    components: {
        Navbar,
        BookCard
    },
    data() {
        return {
            searchTerm: '',
            books: [],
            startIndex: 0,
            showLoadMore: false,
            totalItems: 0,
        };
    },
    methods: {
        async search() {
            if (this.searchTerm.trim() === '') return;

            this.startIndex = 0; // Resetear índice de inicio
            this.books = []; // Limpiar resultados anteriores
            await this.fetchBooks();
        },
        async fetchBooks() {
            const apiKey = process.env.VUE_APP_GOOGLE_BOOKS_API_KEY;
            const query = encodeURIComponent(this.searchTerm);
            const url = `https://www.googleapis.com/books/v1/volumes?q=${query}&startIndex=${this.startIndex}&maxResults=10&key=${apiKey}`;

            try {
                const response = await axios.get(url);
                const newBooks = response.data.items || [];
                this.books = [...this.books, ...newBooks];
                this.totalItems = response.data.totalItems;
                this.showLoadMore = this.books.length < this.totalItems;
                this.startIndex += newBooks.length;
            } catch (error) {
                console.error('Error buscando libros:', error);
            }
        },
        async loadMore() {
            await this.fetchBooks();
        }
    }
};
</script>

<style>
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

.navbar {
    position: absolute;
    top: 10px;
    width: calc(100% - 40px);
    background: rgba(0, 0, 0, 0.7);
    padding: 10px;
    z-index: 1;
    margin: 0 20px;
}

.main-content {
    background: rgba(0, 0, 0, 0.7);
    width: 100%;
    max-width: 1845px;
    margin-top: 150px;
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
}

.search-input {
    flex: 1;
    height: 50px;
    background: rgba(0, 0, 0, 0.7);
    border: 0px solid #ccc;
    border-radius: 5px;
    padding: 15px;
    margin-right: 10px;
    font-size: 28px;
    color: white;
}

.search-button {
    height: 50px;
    background-color: #230745;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    padding: 0 20px;
    font-size: 18px;
}

.results-container {
    background: rgba(0, 0, 0, 0.7);
    padding: 20px;
    border-radius: 10px;
    width: 100%;
    max-width: 1845px;
    box-sizing: border-box;
}

.results-container h2 {
    margin-bottom: 20px;
}

.book-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

.results-container li {
    background: rgba(255, 255, 255, 0.1);
    padding: 10px;
    border-radius: 5px;
    margin-bottom: 10px;
}

.load-more-button {
    background-color: #230745;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    padding: 10px 20px;
    font-size: 18px;
    margin-top: 20px;
}
</style>
