<template>
    <div class="background-image">
        <Navbar class="navbar" />
        <div class="main-content">
            <h1>Perfil </h1>
            <div class="card mb-3">
                <img class="card-img-top portada" :src="this.usuario.image" alt="UserProfile">
                <div class="card-body">
                    <h5 class="card-title">Nombre: {{ this.usuario.nombre }}</h5>
                    <h5 class="card-title">Email: {{ this.usuario.email }}</h5>
                    <h5 class="card-title">Bio: {{ this.usuario.bio }}</h5>
                    <a class=" btn btn-success"><router-link
                            :to="{ name: 'UserForm', params: { userName: this.userNombre } }"
                            class="router-link-custom-white">Editar</router-link></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
    name: 'UserDetails',
    props: ['userName'],
    components: {
        Navbar
    }, data() {
        return {
            isAuthenticated: false,
            usuario: {}
        };
    },
    computed: {
        userNombre() {
            console.log(this.$route.params.userName);
            return this.$route.params.userName;
        }
    }, async mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            this.usuario = this.$cookies.get('user');
            await this.getUsuario()
        } else {
            this.redirectToHome()
        }

    }, methods: {
        checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }, redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }, async getUsuario() {
            await fetch(`http://localhost:8082/user/finduser/${this.userNombre}`)
                .then(response => response.json())
                .then(async data => {
                    this.$cookies.remove('user')
                    this.$cookies.set('user', JSON.stringify(data.usuario));
                    this.usuario = data.usuario

                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }
}
</script>
<style scoped>
.portada {
    max-width: 500px;
    max-height: 500px;
}

.card {
    background: rgba(0, 0, 0, 0.7);
    color: rgb(255, 255, 255);
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
    background: rgba(0, 0, 0, 0);
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
