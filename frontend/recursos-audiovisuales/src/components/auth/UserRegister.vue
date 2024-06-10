<template>
    <div>
      <h2>Register</h2>
      <form @submit.prevent="handleRegister">
        <div>
          <label>Username:</label>
          <input v-model="username" required />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" v-model="password" required />
        </div>
        <div>
          <label>Email:</label>
          <input v-model="email" required />
        </div>
        <div>
          <label>Nombre:</label>
          <input v-model="nombre" required />
        </div>
        <div>
          <label>Bio:</label>
          <input v-model="bio" />
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  </template>
  
  <script>
  import { register } from '../../services/authService';
  
  export default {
    data() {
      return {
        username: '',
        password: '',
        email: '',
        nombre: '',
        bio: '',
      };
    },
    methods: {
      async handleRegister() {
        try {
          const response = await register({
            username: this.username,
            password: this.password,
            email: this.email,
            nombre: this.nombre,
            bio: this.bio,
          });
          alert(response.data);
        } catch (error) {
          alert(error.response.data);
        }
      },
    },
  };
  </script>
  