<template>
    <Navbar style="z-index: 9" />
    <section class="vh-100" style="background-color: #f4f5f7">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col col-lg-10 mb-4 mb-lg-0">
                    <div class="card mb-3" style="border-radius: .5rem;">
                        <div class="row g-0">
                            <div class="col-md-12">
                                <div class="card-body p-4">
                                    <h6>Panel de Administración</h6>
                                    <hr class="mt-0 mb-4">
                                    <div v-if="isAdmin">
                                        <div class="table-responsive">
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                        <th>Username</th>
                                                        <th>Email</th>
                                                        <th>Biografía</th>
                                                        <th>Rol</th>
                                                        <th>Acciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr v-for="user in usuarios" :key="user.username">
                                                        <td>{{ user.nombre }}</td>
                                                        <td>{{ user.username }}</td>
                                                        <td>{{ user.email }}</td>
                                                        <td>{{ user.bio }}</td>
                                                        <td>{{ user.rol }}</td>
                                                        <td>
                                                            <button @click="editUser(user)"
                                                                class="btn btn-primary btn-sm">Editar</button>
                                                            <button @click="deleteUser(user.username)"
                                                                class="btn btn-danger btn-sm">Eliminar</button>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <button @click="createUser" class="btn btn-success">Crear Usuario</button>
                                        <div v-if="showUserForm" class="customFormContainer">
                                            <div class="responsiveForm">
                                                <form class="form" @submit.prevent="saveUser">
                                                    <p class="title">{{ selectedUser.username ? 'Editar' : 'Crear' }}
                                                        Usuario</p>
                                                    <label>
                                                        <input v-model="username" required placeholder="" type="text"
                                                            class="input" />
                                                        <span>Username</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="nombre" required placeholder="" type="text"
                                                            class="input" />
                                                        <span>Nombre</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="email" required placeholder="" type="email"
                                                            class="input" />
                                                        <span>Email</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="bio" placeholder="" type="text" class="input" />
                                                        <span>Biografía</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="password" required placeholder=""
                                                            type="password" class="input" />
                                                        <span>Contraseña</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="rol" required placeholder="" type="text"
                                                            class="input" />
                                                        <span>Rol</span>
                                                    </label>
                                                    <label>
                                                        <input v-model="imagen" placeholder="" type="text"
                                                            class="input" />
                                                        <span>Imagen</span>
                                                    </label>
                                                    <button class="submit">{{ selectedUser.username ? 'Editar' : 'Crear'
                                                        }}</button>
                                                    <button @click="closeUserForm"
                                                        class="btn btn-secondary">Cancelar</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Navbar from "@/components/Navbar.vue";

export default {
    name: "AdminPanel",
    components: {
        Navbar,
    },
    data() {
        return {
            isAuthenticated: false,
            isAdmin: false,
            usuarios: [],
            showUserForm: false,
            selectedUser: {},
            username: "",
            nombre: "",
            email: "",
            bio: "",
            password: "",
            rol: "",
            imagen: "",
        };
    },
    async mounted() {
        this.checkAdmin();
        if (this.isAdmin != false) {
            await this.getUsuarios();
        } else {
            this.redirectToHome();
        }
    },
    methods: {
        checkAdmin() {
            const user = this.$cookies.get("user");
            if (user && user.rol === "ADMIN") {
                this.isAdmin = true;
            }
        },
        redirectToHome() {
            this.$router.push("/");
        },
        async getUsuarios() {
            try {
                const response = await fetch("http://localhost:8082/user/findusers");
                const data = await response.json();
                this.usuarios = data.listaUsuarios;
            } catch (error) {
                console.error("Error:", error);
            }
        },
        editUser(user) {
            this.selectedUser = { ...user };
            this.username = user.username;
            this.nombre = user.nombre;
            this.email = user.email;
            this.bio = user.bio;
            this.rol = user.rol;
            this.imagen = user.image;
            this.showUserForm = true;
        },
        createUser() {
            this.selectedUser = {};
            this.username = "";
            this.nombre = "";
            this.email = "";
            this.bio = "";
            this.password = "";
            this.rol = "";
            this.imagen = "";
            this.showUserForm = true;
        },
        closeUserForm() {
            this.showUserForm = false;
            this.selectedUser = {};
        },
        async saveUser() {
            const user = {
                username: this.username,
                nombre: this.nombre,
                email: this.email,
                bio: this.bio,
                password: this.password,
                rol: this.rol,
                image: this.imagen,
            };


            let url = "";
            let method = "";

            if (this.selectedUser.username) {

                url = "http://localhost:8082/user/updateuser";
                method = "PUT";
                user.id = this.selectedUser.id;
            } else {

                url = "http://localhost:8082/user/adduser";
                method = "POST";
            }

            try {
                const response = await fetch(url, {
                    method: method,
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(user),
                });
                const data = await response.json();
                console.log(data);


                this.showUserForm = false;
                await this.getUsuarios();
            } catch (error) {
                console.error("Error:", error);
            }
        }
        ,
        async deleteUser(username) {
            const confirmation = confirm(`¿Estás seguro de que deseas eliminar al usuario ${username}?`);
            if (!confirmation) return;

            try {
                const userRequest = {
                    username: username
                };

                const response = await fetch(`http://localhost:8082/user/deleteuser`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(userRequest),
                });

                const data = await response.json();
                console.log(data);


                await this.getUsuarios();
            } catch (error) {
                console.error("Error:", error);
            }
        },
    },
};
</script>

<style scoped>
.customFormContainer {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.responsiveForm {
    display: flex;
    align-items: center;
    justify-content: center;
}

@media (max-width: 1400px) {
    .form {
        max-width: 100%;

        padding: 20px;

        margin: 0 20px;

        height: auto;
    }
}

.form {
    display: flex;
    flex-direction: column;
    gap: 10px;
    max-width: 800px;
    width: 500px;
    background-color: #fff;
    padding: 20px;
    border-radius: 20px;
    position: relative;
    -webkit-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
    -moz-box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
    box-shadow: -4px 5px 59px 10px rgba(0, 0, 0, 0.73);
}

.title {
    font-size: 28px;
    color: royalblue;
    font-weight: 600;
    letter-spacing: -1px;
    position: relative;
    display: flex;
    align-items: center;
    padding-left: 30px;
}

.title::before,
.title::after {
    position: absolute;
    content: "";
    height: 16px;
    width: 16px;
    border-radius: 50%;
    left: 0px;
    background-color: royalblue;
}

.title::before {
    width: 18px;
    height: 18px;
    background-color: royalblue;
}

.title::after {
    width: 18px;
    height: 18px;
    animation: pulse 1s linear infinite;
}

.message,
.signin {
    color: rgba(88, 87, 87, 0.822);
    font-size: 14px;
}

.signin {
    text-align: center;
}

.signin a {
    color: royalblue;
}

.signin a:hover {
    text-decoration: underline royalblue;
}

.flex {
    display: flex;
    width: 100%;
    gap: 6px;
}

.form label {
    position: relative;
}

.form label .input {
    width: 100%;
    padding: 10px 10px 20px 10px;
    outline: 0;
    border: 1px solid rgba(105, 105, 105, 0.397);
    border-radius: 5px;
    background: transparent;
    z-index: 1;
}

.form label .input+span {
    position: absolute;
    left: 11px;
    top: 12px;
    padding: 0 4px;
    background-color: white;
    color: rgba(122, 122, 122, 0.822);
    transition: 0.3s;
}

.form label .input:placeholder-shown+span {
    top: 10px;
    font-size: 13px;
}

.form label .input:focus+span,
.form label .input:not(:placeholder-shown)+span {
    top: -10px;
    left: 10px;
    font-size: 13px;
    font-weight: 600;
    z-index: 10;
}

.submit {
    border: none;
    outline: none;
    background-color: royalblue;
    color: white;
    padding: 8px 0;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
    transition: background-color 0.3s ease;
}

.submit:hover {
    background-color: #4169e1c7;
}

.btn-secondary {
    border: none;
    outline: none;
    background-color: #6c757d;
    color: white;
    padding: 8px 0;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
    transition: background-color 0.3s ease;
    margin-top: 10px;
}

.btn-secondary:hover {
    background-color: #5a6268;
}
</style>
