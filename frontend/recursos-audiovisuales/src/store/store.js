import { createStore } from "vuex";

export default createStore({
  state: {
    sharedVariable: "Valor inicial", // Esta es la variable compartida
  },
  mutations: {
    setSharedVariable(state, nuevoValor) {
      state.sharedVariable = nuevoValor;
    },
  },
  actions: {
    updateSharedVariable({ commit }, nuevoValor) {
      commit("setSharedVariable", nuevoValor);
    },
  },
  getters: {
    getSharedVariable: (state) => state.sharedVariable,
  },
});
