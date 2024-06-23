import { mount } from "@vue/test-utils";
import UserLogin from "@/components/auth/UserLogin.vue";
import { login } from "@/services/authService";
import { createRouter, createWebHistory } from "vue-router";
// Mockear la función login
jest.mock("@/services/authService", () => ({
  login: jest.fn(),
}));

// Mock Vue Cookies
jest.mock("vue-cookies", () => ({
  get: jest.fn(),
  set: jest.fn(),
  remove: jest.fn(),
}));

describe("UserLogin.vue", () => {
  let wrapper;
  let router;

  beforeEach(() => {
    router = createRouter({
      history: createWebHistory(),
      routes: [
        {
          path: "/",
          name: "Home",
        },
      ],
    });

    wrapper = mount(UserLogin, {
      global: {
        plugins: [router],
        mocks: {
          $router: router,
        },
      },
    });
  });

  afterEach(() => {
    wrapper.unmount();
  });

  it("renders login form", () => {
    expect(wrapper.find(".customFormContainer").exists()).toBe(true);
  });

  it("sets data when inputs change", async () => {
    const usernameInput = wrapper.find("#username");
    await usernameInput.setValue("testuser");
    const passwordInput = wrapper.find("#password");
    await passwordInput.setValue("testpassword");

    expect(wrapper.vm.username).toBe("testuser");
    expect(wrapper.vm.password).toBe("testpassword");
  });

  it("calls login method on form submit", async () => {
    const usernameInput = wrapper.find("#username");
    await usernameInput.setValue("testuser");
    const passwordInput = wrapper.find("#password");
    await passwordInput.setValue("testpassword");

    await wrapper.find("form").trigger("submit.prevent");

    expect(login).toHaveBeenCalledWith({
      username: "testuser",
      password: "testpassword",
    });
  });
});
