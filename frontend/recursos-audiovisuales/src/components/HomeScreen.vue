<template>
  <div>
    <Navbar />
    <h1>Bienvenido a la Aplicación</h1>
    <div>
      <div class="card" style="width: 18rem;">
        <img src="/images/logo.png" class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">Card title</h5>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's
            content.</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
    </div>
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
