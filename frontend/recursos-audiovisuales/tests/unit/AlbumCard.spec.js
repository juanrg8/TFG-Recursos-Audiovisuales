import { mount } from "@vue/test-utils";
import AlbumCard from "@/components/albums/AlbumCard.vue";
import { createApp } from "vue";
import VueCookies from "vue-cookies";
import { enableFetchMocks } from "jest-fetch-mock";

enableFetchMocks();

// Mockea vue-cookies para evitar errores
jest.mock("vue-cookies", () => ({
  get: jest.fn(),
  set: jest.fn(),
  remove: jest.fn(),
}));

// Configura Vue para usar vue-cookies
const app = createApp(AlbumCard);
app.use(VueCookies);

// Mockea la función $cookies.get para evitar errores
app.config.globalProperties.$cookies = {
  get: () => ({ username: "testUser" }), // Simula un usuario autenticado
};

describe("AlbumCard.vue", () => {
  afterEach(() => {
    jest.clearAllMocks(); // Limpia todos los mocks después de cada prueba
  });

  // Caso de prueba 1: Renderizado básico con datos completos del álbum
  it("renders album name and artist when album data is complete", () => {
    const album = {
      id: "albumId1",
      name: "Album Name",
      artists: [{ name: "Artist Name" }],
      images: [{ url: "http://example.com/image.jpg" }],
      miAlbum: null, // Opcional: Asegurarse de manejar esta condición si se espera
    };

    const wrapper = mount(AlbumCard, {
      props: { album },
    });

    // Verificar que el nombre del álbum y el nombre del artista se rendericen correctamente
    expect(wrapper.find(".card-title").text()).toContain(album.name);
    expect(wrapper.find(".card-text").text()).toContain(album.artists[0].name);
  });

  // Caso de prueba 2: Verificar que se muestra el componente cuando no hay artistas definidos
  it("renders default artist when artists array is undefined", () => {
    const album = {
      id: "albumId2",
      name: "Album Name 2",
      artist: "Default Artist",
      images: [{ url: "http://example.com/image2.jpg" }],
      miAlbum: null, // Opcional: Asegurarse de manejar esta condición si se espera
    };

    const wrapper = mount(AlbumCard, {
      props: { album },
    });

    // Verificar que el nombre del álbum y el nombre del artista predeterminado se rendericen correctamente
    expect(wrapper.find(".card-title").text()).toContain(album.name);
    expect(wrapper.find(".card-text").text()).toContain(album.artist);
  });

  // Caso de prueba 3: Verificar la ejecución de métodos al hacer clic en botones
  it('calls addAlbumPending method when "Pendiente" button is clicked', async () => {
    const album = {
      id: "albumId3",
      name: "Album Name 3",
      artists: [{ name: "Artist Name 3" }],
      images: [{ url: "http://example.com/image3.jpg" }],
      miAlbum: null,
      release_date: "2024-01-01", // Asegúrate de que estos campos estén definidos
      total_tracks: 10, // en el objeto album que se pasa como prop
    };

    const wrapper = mount(AlbumCard, {
      props: { album },
    });

    // Simular autenticación para que se muestre el botón "Pendiente"
    wrapper.setData({ isAuthenticated: true });

    // Mockear la función addAlbumPending
    wrapper.vm.addAlbumPending = jest.fn();

    // Esperar a que se resuelvan las promesas asincrónicas (si las hay)
    await wrapper.vm.$nextTick();

    // Encontrar el botón "Pendiente" y simular un clic
    const pendingButton = wrapper.find(".btn-primary");
    expect(pendingButton.exists()).toBe(true);
    await pendingButton.trigger("click");

    // Verificar que el método addAlbumPending se haya llamado con los argumentos correctos
    expect(wrapper.vm.addAlbumPending).toHaveBeenCalledWith(
      album.id,
      album.name,
      album.artists[0].name,
      album.release_date, // Verifica que estos campos coincidan con el objeto album
      album.total_tracks
    );
  });
});
