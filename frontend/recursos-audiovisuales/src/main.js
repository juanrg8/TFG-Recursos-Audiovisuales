import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router'; // Importa createRouter y createWebHistory desde vue-router
import App from './App.vue';
import HomeScreen from './components/HomeScreen.vue'; // Importa tus componentes de pantalla aquí
import Login from './components/auth/UserLogin.vue';
import Register from './components/auth/UserRegister.vue';

// Define tus rutas
const routes = [
  { path: '/', component: HomeScreen }, // Ruta para la pantalla principal
  { path: '/login', component: Login }, // Ruta para la pantalla de inicio de sesión
  { path: '/register', component: Register } // Ruta para la pantalla de registro
];

// Crea el enrutador con la historia de navegación basada en la ubicación web
const router = createRouter({
  history: createWebHistory(),
  routes
});

// Crea la aplicación y monta el enrutador y la aplicación en el elemento con el ID 'app'
createApp(App)
  .use(router) // Usa el enrutador
  .mount('#app');
