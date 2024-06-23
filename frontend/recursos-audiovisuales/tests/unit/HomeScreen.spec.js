import { mount } from "@vue/test-utils";
import HomeScreen from "@/components/HomeScreen.vue";
import Navbar from "@/components/Navbar.vue";
import { createRouter, createWebHistory } from "vue-router";

// Definir rutas de ejemplo si es necesario
const routes = [
  // Definir las rutas que tu aplicación use
  { path: "/", component: HomeScreen },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

describe("HomeScreen.vue", () => {
  it("renders the Navbar component", async () => {
    const wrapper = mount(HomeScreen, {
      global: {
        plugins: [router],
      },
    });
    await router.isReady();
    const navbar = wrapper.findComponent(Navbar);
    expect(navbar.exists()).toBe(true);
  });

  it("renders the welcome message correctly", async () => {
    const wrapper = mount(HomeScreen, {
      global: {
        plugins: [router],
      },
    });
    await router.isReady();
    const h2 = wrapper.find("h2.myH2");
    expect(h2.text()).toBe("Bienvenido a GoodTaste!");
  });

  it("renders all paragraphs with correct text", async () => {
    const wrapper = mount(HomeScreen, {
      global: {
        plugins: [router],
      },
    });
    await router.isReady();
    const paragraphs = wrapper.findAll("p.myP");
    expect(paragraphs).toHaveLength(4);
    expect(paragraphs.at(0).text()).toBe(
      "GoodTaste es tu plataforma ideal para explorar y disfrutar de la música y tus libros favoritos. Aquí podrás:"
    );
    expect(paragraphs.at(1).text()).toBe(
      "¡Buscar tus álbumes o libros favoritos y descubrir nuevos!"
    );
    expect(paragraphs.at(2).text()).toBe(
      "¡Añadir álbumes o libros a tu lista personal para un fácil acceso!"
    );
    expect(paragraphs.at(3).text()).toBe(
      "¡Escribir reseñas de tus álbumes o libros para registrar tus opiniones!"
    );
  });

  it("has the correct CSS styles applied", async () => {
    const wrapper = mount(HomeScreen, {
      global: {
        plugins: [router],
      },
    });
    await router.isReady();
    const container = wrapper.find(".cont_container");
    expect(container.exists()).toBe(true);
    expect(container.classes()).toContain("cont_container");
  });
});
