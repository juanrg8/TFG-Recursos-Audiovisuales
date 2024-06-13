<template>
  <div>
    <Navbar />
    <h1>Bienvenido a la Aplicación</h1>
  </div>
</template>

<script>
import Navbar from './Navbar.vue';
export default {
  name: 'HomeScreen',
  components: {
    Navbar,
  },
};
</script>
