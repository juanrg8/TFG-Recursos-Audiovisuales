module.exports = {
  transpileDependencies: ['vuetify'],
  configureWebpack: {
    // Configuración adicional de webpack si es necesario
  },
  // Agrega o actualiza la configuración para compilerOptions
  chainWebpack: config => {
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        // Agrega o actualiza compilerOptions
        options.compilerOptions = {
          ...options.compilerOptions,
          // Excluye hover-button de la resolución de componentes
          isCustomElement: tag => tag === 'hover-button',
        };
        return options;
      });
  },
};
