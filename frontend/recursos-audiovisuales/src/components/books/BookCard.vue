<!-- BookCard.vue -->
<template>
    <div class="book-card">
        <img v-if="book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail"
            :src="getFormattedImage(book.volumeInfo.imageLinks.thumbnail)" alt="Portada del libro" class="book-cover" />
        <img v-else
            src="https://ih1.redbubble.net/image.1756098780.0530/fpp,small,lustre,wall_texture,product,750x1000.jpg"
            alt="Portada no encontrada" class="book-cover" />
        <div class="book-details">
            <h3 class="book-title">{{ book.volumeInfo.title }}</h3>
            <p class="book-authors">{{ book.volumeInfo.authors ? book.volumeInfo.authors.join(', ') : 'Desconocido' }}
            </p>
            <p class="book-date">{{ book.volumeInfo.publishedDate }}</p>
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
    methods: {
        getFormattedImage(url) {
            if (url) {
                return url.replace(/zoom=1$/, 'zoom=2');
            }
            return '';
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
</style>
