<template>
    <div class="book-card">
        <img v-if="!book.volumeInfo" :src="getFormattedImage(book.imageLinks.thumbnail)" alt="Portada del libro"
            class="book-cover" />
        <img v-else-if="book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail"
            :src="getFormattedImage(book.volumeInfo.imageLinks.thumbnail)" alt="Portada del libro" class="book-cover" />
        <img v-else
            src="https://ih1.redbubble.net/image.1756098780.0530/fpp,small,lustre,wall_texture,product,750x1000.jpg"
            alt="Portada no encontrada" class="book-cover" />
        <div class="book-details">
            <h3 class="book-title" v-if="book.miLibro == undefined && book.miLibro == null">
                {{ book.volumeInfo.title }}</h3>
            <h5 class="book-title" v-if="book.miLibro != undefined && book.miLibro != null">
                <router-link :to="{ name: 'BookDetails', params: { libroId: book.id } }" class="router-link-custom">{{
            book.title }}
                </router-link>
            </h5>
            <p class="book-authors" v-if="book.miLibro != undefined && book.miLibro != null">
                {{ book.authors[0] }}
            </p>
            <p class="book-authors" v-else>
                {{ book.volumeInfo.authors ? book.volumeInfo.authors[0] : 'Autor no especificado' }}
            </p>
            <p class="book-date" v-if="book.miLibro != undefined && book.miLibro != null">
                {{ book.publishedDate }}
            </p>
            <p class="book-date" v-else>
                {{ book.volumeInfo.publishedDate }}
            </p>
            <a v-if="isAuthenticated && book.miLibro != undefined && book.miLibro != null"
                @click="addBookPendingStatus(book.title)" class="btn btn-primary">Pendiente</a>
            <a v-if="isAuthenticated && book.miLibro == undefined && book.miLibro == null"
                @click="addBookPending(book.id, book.volumeInfo.title, book.volumeInfo.publishedDate, book.volumeInfo.pageCount, book.volumeInfo.categories ? book.volumeInfo.categories[0] : 'No especificado', book.volumeInfo.authors ? book.volumeInfo.authors[0] : 'Autor no')"
                class="btn btn-primary">Pendiente</a>
            <a @click="deleteAlbum(book.title)" class="btn btn-danger"
                v-if="isAuthenticated && book.miLibro != undefined && book.miLibro != null">Eliminar</a>
            <a v-if="isAuthenticated && book.miLibro != undefined && book.miLibro != null" class="btn btn-success">
                <router-link :to="{ name: 'BookReview', params: { libroId: book.id, status: 'READING' } }"
                    class="router-link-custom-white">Leyendo</router-link>
            </a>
            <a v-if="isAuthenticated && book.miLibro != undefined && book.miLibro != null" class="btn btn-success">
                <router-link :to="{ name: 'BookReview', params: { libroId: book.id, status: 'READED' } }"
                    class="router-link-custom-white">Leído</router-link>
            </a>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        book: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            isAuthenticated: false
        };
    },
    mounted() {
        this.checkAuthentication();
    },
    methods: {
        getFormattedImage(url) {
            if (url) {
                return url.replace(/zoom=1$/, 'zoom=2');
            }
            return '';
        },
        addBookPending(id, name, date, pages, genre, author) {
            let formattedDate = date;

            // Verificar si la fecha es solo el año
            if (/^\d{4}$/.test(date)) {
                formattedDate = date + "-01-01";  // Añadir '01-01' si es solo el año
            }

            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                libroId: id,
                status: "PENDING",
                tituloLibro: name,
                publishDate: formattedDate,
                paginas: pages,
                genre: genre,
                author: author
            };

            fetch('http://localhost:8082/userbook/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }, addBookPendingStatus(title) {
            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                status: "PENDING",
                tituloLibro: title
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
                    console.log(data);
                    this.$emit('updateView', data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        deleteAlbum(title) {
            let request = {
                usuarioNombre: this.$cookies.get('user').username,
                tituloLibro: title
            };
            fetch('http://localhost:8082/userbook/delete', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(request)
            })
                .then(response => response.json())
                .then(data => {
                    this.$emit('deleteEvent', data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }
    }
};
</script>

<style>
.book-card {
    width: 100%;
    max-width: 250px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin: 10px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    color: #333;
}

.book-cover {
    width: 100%;
    height: auto;
    border-radius: 8px;
    object-fit: cover;
}

.book-details {
    flex: 1;
    padding: 10px;
}

.book-title {
    margin: 0;
    font-size: 18px;
    color: #333;
    text-align: center;
    margin-bottom: 10px;
}

.book-authors,
.book-date {
    margin: 5px 0;
    font-size: 14px;
    color: #666;
    text-align: center;
}

.btn {
    margin-top: 10px;
}

.router-link-custom {
    color: rgb(0, 0, 0);
    text-decoration: none;
}

.router-link-custom:hover,
.router-link-custom:focus {
    text-decoration: none;
    color: rgb(107, 27, 173);
}

.router-link-custom-white {
    color: rgb(255, 255, 255);
    text-decoration: none;
}
</style>
