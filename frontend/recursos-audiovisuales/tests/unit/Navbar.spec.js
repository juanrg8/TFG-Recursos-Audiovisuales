// tests/unit/Navbar.spec.js
import { createApp } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import { mount } from "@vue/test-utils";
import Navbar from "@/components/Navbar.vue";

// Mock Vue Router
const router = createRouter({
  history: createWebHistory(),
  routes: [],
});

// Mock Vue Cookies
jest.mock("vue-cookies", () => ({
  get: jest.fn(),
  set: jest.fn(),
  remove: jest.fn(),
}));

describe("Navbar.vue", () => {
  it("renders correctly", async () => {
    const app = createApp({});
    app.use(router);

    router.push("/");
    await router.isReady();

    const wrapper = mount(Navbar, {
      global: {
        plugins: [router],
        mocks: {
          $cookies: {
            get: jest.fn(),
            set: jest.fn(),
            remove: jest.fn(),
          },
        },
      },
    });
    expect(wrapper.exists()).toBe(true);
  });
});
