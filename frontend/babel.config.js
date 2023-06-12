module.exports = function (api) {
  api.cache(true);

  return {
    presets: [['@babel/preset-react', { runtime: 'automatic' }], '@babel/preset-env', '@babel/preset-typescript'],
    plugins: ['macros'],
  };
};
