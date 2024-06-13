<template>
    <Navbar />
    <form @submit.prevent="updateUser">
        <div class="form-group">
            <label for="exampleInputEmail1">Nombre</label>
            <input v-model='nombre' type="text" class="form-control" id="exampleInputEmail1"
                aria-describedby="emailHelp" placeholder="Introduce tu nombre">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Email</label>
            <input v-model='email' type="email" class="form-control" id="exampleInputPassword1"
                placeholder="Introduce tu email">
            <small id="emailHelp" class="form-text text-muted">No compartiremos tu email con nadie.</small>
        </div>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Bio</label>
            <textarea v-model='bio' class="form-control" id="exampleFormControlTextarea1" placeholder="Introduce tu bio"
                rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="exampleInputEmail1">Imagen</label>
            <input v-model='imagen' type="text" class="form-control" id="exampleInputEmail1"
                aria-describedby="emailHelp" placeholder="Copia el link de la imagen">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

export default {
    name: 'UserForm',
    props: ['userName'],
    components: {
        Navbar
    }, data() {
        return {
            isAuthenticated: false,
            usuario: {},
            nombre: '',
            email: '',
            bio: '',
            imagen: ''
        };
    },
    computed: {
        nombreUsuario() {
            console.log(this.$route.params.userName);
            return this.$route.params.userName;
        }
    }, mounted() {
        this.checkAuthentication()
        if (this.isAuthenticated) {
            this.usuario = this.$cookies.get('user');
            this.nombre = this.usuario.nombre
            this.bio = this.usuario.bio
            this.email = this.usuario.email
            this.imagen = this.usuario.image
        } else {
            this.redirectToHome()
        }

    }, methods: {
        checkAuthentication() {
            const token = localStorage.getItem('token');
            this.isAuthenticated = !!token;
        }, redirectToHome() {
            this.$router.push('/'); // Redirige a la página principal
        }, updateUser() {
            let request = {
                username: this.nombreUsuario,
                email: this.email,
                bio: this.bio,
                nombre: this.nombre,
                image: this.imagen
            };
            fetch('http://localhost:8082/user/updateuser', {
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
                    this.$router.push({ name: 'UserDetails', params: { userName: this.nombreUsuario } });
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }
}
</script>