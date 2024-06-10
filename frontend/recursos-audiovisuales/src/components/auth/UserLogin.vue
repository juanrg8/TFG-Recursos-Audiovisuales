<template>
    <div>
      <h2>Login</h2>
      <form @submit.prevent="handleLogin">
        <div>
          <label>Username:</label>
          <input v-model="username" required />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" v-model="password" required />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  import { login } from '../../services/authService';
  
  export default {
    name: 'UserLogin',
    data() {
      return {
        username: '',
        password: '',
      };
    },
    methods: {
      async handleLogin() {
        try {
          const response = await login({
            username: this.username,
            password: this.password,
          });
          alert('Login successful!');
          localStorage.setItem('token', response.data.token); // Guarda el token en el local storage
        } catch (error) {
          alert('Login failed: ' + error.response.data);
        }
      },
    },
  };
  </script>
  