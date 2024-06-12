

<script setup>
const CLIENT_ID = process.env.VUE_APP_SPOTIFY_CLIENT_ID;
const CLIENT_SECRET = process.env.VUE_APP_SPOTIFY_CLIENT_SECRET;

console.log('Client ID:', CLIENT_ID);
console.log('Client Secret:', CLIENT_SECRET);

</script>

<template>
  <router-view></router-view>
</template>