<template>
    <div class="background-image">
        <Navbar class="navbar" />
        <div class="main-content">
            <h1>Editar Perfil </h1>
            <form @submit.prevent="updateUser" class="form">
                <div class="form-group">
                    <label for="exampleInputEmail1">Nombre</label>
                    <input v-model='nombre' type="text" class="form-control" id="exampleInputEmail1"
                        aria-describedby="emailHelp" placeholder="Introduce tu nombre">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Email</label>
                    <input v-model='email' type="email" class="form-control" id="exampleInputPassword1"
                        placeholder="Introduce tu email">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Bio</label>
                    <textarea v-model='bio' class="form-control" id="exampleFormControlTextarea1"
                        placeholder="Introduce tu bio" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Imagen</label>
                    <input v-model='imagen' type="text" class="form-control" id="exampleInputEmail1"
                        aria-describedby="emailHelp" placeholder="Copia el link de la imagen">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
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

<style>
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
    top: 0px;
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
    background: rgba(0, 0, 0, 0.7);
    background-size: 0px;
    width: 100%;
    max-width: 500px;
    /* Ancho máximo del contenido principal */
    margin-top: 150px;
    /* Ajusta el margen superior para dejar espacio para la Navbar */
    padding: 10px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 15px;
}

.btn {
    margin-top: 10px;
    background-color: rgb(33, 13, 53);
}
</style>