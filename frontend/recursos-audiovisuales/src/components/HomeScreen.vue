<!-- src/components/Home.vue -->

<template>
    <div>
      <h1>Bienvenido a la Aplicación</h1>
      <p>Por favor, <router-link to="/login">inicia sesión</router-link> o <router-link to="/register">regístrate</router-link>.</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'HomeScreen',
  };
  </script>
  