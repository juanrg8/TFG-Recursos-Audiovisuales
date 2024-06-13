<template>
    <Navbar />
    <div class="card mb-3">
        <img class="card-img-top portada" :src="this.usuario.image" alt="UserProfile">
        <div class="card-body">
            <h5 class="card-title">Nombre: {{ this.usuario.nombre }}</h5>
            <h5 class="card-title">Email: {{ this.usuario.email }}</h5>
            <h5 class="card-title">Bio: {{ this.usuario.bio }}</h5>
            <a class=" btn btn-success"><router-link
                    :to="{ name: 'UserForm', params: { userName: this.userNombre } }">Editar</router-link></a>
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
    max-width: 250px;
    max-height: 250px;
}
</style>
